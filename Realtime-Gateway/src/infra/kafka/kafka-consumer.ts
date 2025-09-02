import { Kafka, logLevel } from 'kafkajs';
import type { Server } from 'socket.io';
import type { MessageEvent } from './message-event.js';
import { emitToChannel } from '../../service/subscription.service.js';

export const startKafkaConsumer = async (io: Server) => {
    const kafka = new Kafka({
        brokers: process.env.BROKERS!.split(','),
        clientId: 'realtime-gateway',
        logLevel: logLevel.INFO,
    });

    const consumer = kafka.consumer({
        groupId: process.env.GROUP_ID!,
    });

    await consumer.connect();
    await consumer.subscribe({
        topic: process.env.TOPIC!,
        fromBeginning: false,
    });

    await consumer.run({
        autoCommit: true,
        eachMessage: async ({ message }) => {
            const event = safeParse<MessageEvent>(message.value ?? null);
            if (!event) {
                console.warn('[Kafka] Invalid JSON payload, skip');
                return;
            }

            const { channelId } = event;
            if (!Number.isFinite(channelId)) {
                console.warn('[Kafka] Missing/invalid channelId, skip', event);
                return;
            }

            emitToChannel(io, channelId, 'message', event);
        },
    });

    const shutdown = async () => {
        try {
            await consumer.disconnect();
        } catch {
            // ignore
        }
    };
    process.on('SIGINT', shutdown);
    process.on('SIGTERM', shutdown);

    console.log(
        `[Kafka] consumer started. topic=${process.env.TOPIC!}, brokers=${process.env.BROKERS!}, group=${process.env.GROUP_ID!}`,
    );
};

function safeParse<T>(buf: Buffer | null): T | null {
    if (!buf) return null;
    try {
        return JSON.parse(buf.toString('utf8')) as T;
    } catch {
        return null;
    }
}
