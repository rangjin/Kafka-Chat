import { Kafka, logLevel } from "kafkajs";
import type { EventConsumer } from "../../application/port/outbound/event.consumer.js";

export class KafkaConsumerAdapter implements EventConsumer {
    constructor(
      private readonly topic = process.env.TOPIC_EVENT!,
      private readonly groupId = process.env.GID_EVENT!,
      private readonly brokers = process.env.BROKERS!.split(','),
    ) {}

    async startConsumer(onEvent: (e: string) => Promise<void> | void): Promise<void> {
        const kafka = new Kafka({
            brokers: this.brokers, 
            clientId: 'realtime-gateway', 
            logLevel: logLevel.INFO
        });
        const consumer = kafka.consumer({ 
            groupId: this.groupId
        });
        await consumer.connect();
        await consumer.subscribe({
            topic: this.topic, 
            fromBeginning: false
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
            }
        });
    }

}