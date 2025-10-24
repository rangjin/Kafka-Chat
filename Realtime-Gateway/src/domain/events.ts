export type EventType = 'CREATE' | 'UPDATE' | 'DELETE';
export type ClassName = 'Message' | 'Membership';

export interface EventEnvelope<T = unknown> {
    type: EventType;

    className: ClassName;

    aggregateId: string;

    timestamp: string;

    payload: T;
}

export interface MessagePayload {
    id: number;

    seq: number;

    sentAt: string;

    content: string;

    senderId: number;

    channelId: number;

    createdAt: string;

    updatedAt: string;

    messageId: string;
}

export interface MembershipPayload {
    id: number;

    role: 'MEMBER' | 'ADMIN' | string;

    userId: number;

    channelId: number;

    joinedAt: string;

    createdAt: string;

    updatedAt: string;
}
