import { Kafka, logLevel, type Consumer } from 'kafkajs';
import type { Server } from 'socket.io';
import {
    emitToChannel,
    forceJoinChannelByUser,
    forceLeaveChannelByUser,
} from '../../service/subscription.service.js';
import { parseJsonSafe } from '../../util/json.js';
import type { EventEnvelope, MembershipPayload, MessagePayload } from './event.js';

export const startKafkaConsumers = async (io: Server) => {
    const kafka = new Kafka({
        brokers: process.env.BROKERS!.split(','),
        clientId: 'realtime-gateway',
        logLevel: logLevel.INFO,
    });

    await startEventConsumer(kafka, process.env.TOPIC_EVENT!, process.env.GID_EVENT!, io);
};

const startEventConsumer = async (
    kafka: Kafka,
    topic: string,
    groupId: string,
    io: Server,
): Promise<Consumer> => {
    const consumer = kafka.consumer({ groupId });
    await consumer.connect();
    await consumer.subscribe({ topic, fromBeginning: false });

    await consumer.run({
        autoCommit: true,
        eachMessage: async ({ message }) => {
            try {
                const env = parseJsonSafe<EventEnvelope>(message.value!);
                if (!env || !env.className || !env.type) {
                    console.warn('[Kafka][event] invalid envelope, skip');
                    return;
                }

                switch (env.className) {
                    case 'Message': {
                        if (env.type !== 'CREATE') return;
                        const m = env.payload as MessagePayload;

                        if (!Number.isFinite(Number(m.channelId)))
                            return console.warn('[Kafka][msg] invalid channelId');

                        emitToChannel(io, m.channelId, 'message', m);
                        break;
                    }
                    case 'Membership': {
                        const mem = env.payload as MembershipPayload;

                        if (
                            !Number.isFinite(Number(mem.channelId)) ||
                            !Number.isFinite(Number(mem.userId))
                        )
                            return console.warn('[Kafka][membership] invalid ids');

                        if (env.type === 'CREATE') {
                            forceJoinChannelByUser(io, mem.userId, mem.channelId);
                            emitToChannel(io, mem.channelId, 'member:join', mem);
                        } else if (env.type === 'DELETE') {
                            forceLeaveChannelByUser(io, mem.userId, mem.channelId);
                            emitToChannel(io, mem.channelId, 'member:leave', mem);
                        }
                        break;
                    }
                    default:
                        return;
                }
            } catch (err) {
                console.error(`[Kafka][${topic}] handler error:`, err);
            }
        },
    });

    console.log(
        `[Kafka] consumer started. topic=${topic}, brokers=${process.env.BROKERS}, group=${groupId}`,
    );

    const shutdown = async () => {
        try {
            await consumer.disconnect();
        } catch {
            // ignore
        }
    };
    process.on('SIGINT', shutdown);
    process.on('SIGTERM', shutdown);

    return consumer;
};
