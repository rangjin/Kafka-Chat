type ChannelActivityType = 'JOIN' | 'LEAVE';

export interface ChannelActivityEvent {
    type: ChannelActivityType;

    userId: number;

    channelId: number;

    occurredAt: string;

    role?: 'OWNER' | 'MEMBER';
}
