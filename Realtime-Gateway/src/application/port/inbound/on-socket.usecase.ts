import type { ChannelId, UserId } from "../../../domain/types.js";

export interface OnSocketUseCase {
    registerSocket(userId: UserId, socketId: string): void;
    unregisterSocket(userId: UserId, socketId: string): void;
    joinInitialChannels(socketId: string, channels: ChannelId[]): void;
}