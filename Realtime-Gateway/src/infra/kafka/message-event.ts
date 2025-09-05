export interface MessageEvent {
    messageId: string;

    channelId: number;

    senderId: number;

    content: string;

    sentAt: string;
}
