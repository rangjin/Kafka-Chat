import type { MessagePayload } from '../../../domain/events.js';

export interface OnMessagingUsecase {
    onMessaging(message: MessagePayload): Promise<void> | void;
}
