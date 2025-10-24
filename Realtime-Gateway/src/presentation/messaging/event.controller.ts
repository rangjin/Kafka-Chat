import type { OnMembershipUseCase } from "../../application/port/inbound/on-membership.usecase.js";
import type { OnMessagingUsecase } from "../../application/port/inbound/on-messaging.usecase.js";
import type { EventEnvelope, MembershipPayload, MessagePayload } from "./kafka-events.js";
import { parseJsonSafe } from "../../common/json.util.js";
import type { EventConsumer } from "../../application/port/outbound/event.consumer.js";

export class EventController {
    constructor (
        private readonly eventConsumer: EventConsumer, 
        private readonly onMessagingUseCase: OnMessagingUsecase, 
        private readonly onMembershipUseCase: OnMembershipUseCase
    ) {}

    async start() {
        await this.eventConsumer.startConsumer(async (json: string) => {
            const event = parseJsonSafe<EventEnvelope>(json);
            if (!event || !event.className || !event.type) {
                console.warn('[Kafka][event] invalid envelope, skip');
                return;
            }

            switch (event.className) {
              case 'Message':
                if (event.type === 'CREATE') await this.onMessagingUseCase.onMessaging(event.payload as MessagePayload);
                break;
              case 'Membership':
                if (event.type === 'CREATE') await this.onMembershipUseCase.onJoin(event.payload as MembershipPayload);
                else if (event.type === 'DELETE') await this.onMembershipUseCase.onLeave(event.payload as MembershipPayload);
                break;
            }
        });
    }

}