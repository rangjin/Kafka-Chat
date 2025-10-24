import type { MessagePayload } from "../../../presentation/messaging/kafka-events.js";

export interface OnMessagingUsecase {
    onMessaging(message: MessagePayload): Promise<void> | void;
}