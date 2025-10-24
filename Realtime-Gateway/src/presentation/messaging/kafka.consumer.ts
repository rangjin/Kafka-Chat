import { Kafka, logLevel } from 'kafkajs';
import type { OnMembershipUseCase } from '../../application/port/inbound/on-membership.usecase.js';
import type { OnMessagingUsecase } from '../../application/port/inbound/on-messaging.usecase.js';
import type { EventEnvelope, MembershipPayload, MessagePayload } from '../../domain/events.js';
import { parseJsonSafe } from '../../common/json.util.js';

export class KafkaConsumer {
    constructor(
        private readonly onMessagingUseCase: OnMessagingUsecase,
        private readonly onMembershipUseCase: OnMembershipUseCase,
    ) {}

    private readonly topic = process.env.TOPIC_EVENT!;
    private readonly groupId = process.env.GID_EVENT!;
    private readonly brokers = process.env.BROKERS!.split(',');

    async startConsume() {
        await this.consumerConfig(async (json: string) => {
            const event = parseJsonSafe<EventEnvelope>(json);
            if (!event || !event.className || !event.type) {
                console.warn('[Kafka][event] invalid envelope, skip');
                return;
            }

            switch (event.className) {
                case 'Message':
                    if (event.type === 'CREATE')
                        await this.onMessagingUseCase.onMessaging(event.payload as MessagePayload);
                    break;
                case 'Membership':
                    if (event.type === 'CREATE')
                        await this.onMembershipUseCase.onJoin(event.payload as MembershipPayload);
                    else if (event.type === 'DELETE')
                        await this.onMembershipUseCase.onLeave(event.payload as MembershipPayload);
                    break;
            }
        });
    }

    async consumerConfig(onEvent: (e: string) => Promise<void> | void): Promise<void> {
        const kafka = new Kafka({
            brokers: this.brokers,
            clientId: 'realtime-gateway',
            logLevel: logLevel.INFO,
        });
        const consumer = kafka.consumer({
            groupId: this.groupId,
        });
        await consumer.connect();
        await consumer.subscribe({
            topic: this.topic,
            fromBeginning: false,
        });

        await consumer.run({
            autoCommit: true,
            eachMessage: async ({ message }) => {
                try {
                    const json = message.value?.toString();
                    if (!json) return;

                    await onEvent(json);
                } catch (e) {
                    console.error('[Kafka] parse error:', e);
                }
            },
        });
    }
}
