import { Kafka, logLevel, type EachMessagePayload, type Consumer } from 'kafkajs';
import type { Server } from 'socket.io';
import type { MessageEvent } from './message-event.js';
import type { ChannelActivityEvent } from './channel-activity-event.js';
import {
    emitToChannel,
    forceJoinChannelByUser,
    forceLeaveChannelByUser,
} from '../../service/subscription.service.js';

export function parseJsonDeep<T = unknown>(input: Buffer | string | null | undefined): T | null {
    if (!input) return null;
    const text = typeof input === 'string' ? input : input.toString('utf8');
    try {
        let v: unknown = JSON.parse(text);

        if (typeof v === 'string' && (v.startsWith('{') || v.startsWith('['))) {
            v = JSON.parse(v);
        }

        return v as T;
    } catch {
        return null;
    }
}

async function startTopicConsumer(
    kafka: Kafka,
    topic: string,
    groupId: string,
    handle: (io: Server, payload: EachMessagePayload) => Promise<void> | void,
    io: Server,
): Promise<Consumer> {
    const consumer = kafka.consumer({ groupId });
    await consumer.connect();
    await consumer.subscribe({ topic, fromBeginning: false });

    await consumer.run({
        autoCommit: true,
        eachMessage: async (payload) => {
            try {
                await handle(io, payload);
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
}

export const startKafkaConsumers = async (io: Server) => {
    const kafka = new Kafka({
        brokers: process.env.BROKERS!.split(','),
        clientId: 'realtime-gateway',
        logLevel: logLevel.INFO,
    });

    const TOPIC_MESSAGE = process.env.TOPIC_MESSAGE ?? 'chat.message';
    const TOPIC_ACTIVITY = process.env.TOPIC_ACTIVITY ?? 'chat.channel-activity';
    const GID_MESSAGE = process.env.GID_MESSAGE ?? 'chat-message-consumer';
    const GID_ACTIVITY = process.env.GID_ACTIVITY ?? 'chat-activity-consumer';

    await startTopicConsumer(
        kafka,
        TOPIC_MESSAGE,
        GID_MESSAGE,
        (io, { message }) => {
            const evt = parseJsonDeep<MessageEvent>(message.value);
            if (!evt) return void console.warn('[Kafka][message] invalid JSON, skip');
            if (!Number.isFinite(Number(evt.channelId))) {
                return void console.warn('[Kafka][message] invalid channelId, skip', evt);
            }
            emitToChannel(io, evt.channelId, 'message', evt);
        },
        io,
    );

    await startTopicConsumer(
        kafka,
        TOPIC_ACTIVITY,
        GID_ACTIVITY,
        (io, { message }) => {
            const evt = parseJsonDeep<ChannelActivityEvent>(message.value);
            if (!evt) return void console.warn('[Kafka][activity] invalid JSON, skip');
            if (!Number.isFinite(evt.channelId)) {
                return void console.warn('[Kafka][activity] invalid channelId, skip', evt);
            }

            if (evt.type === 'JOIN') {
                forceJoinChannelByUser(io, evt.userId, evt.channelId);
                emitToChannel(io, evt.channelId, 'member:join', evt);
                return;
            }
            if (evt.type === 'LEAVE') {
                forceLeaveChannelByUser(io, evt.userId, evt.channelId);
                emitToChannel(io, evt.channelId, 'member:leave', evt);
                return;
            }

            console.warn('[Kafka][activity] unknown type, skip', evt);
        },
        io,
    );
};
