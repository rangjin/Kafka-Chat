import type { ChannelId, UserId } from "../../../domain/types.js";

export interface OnTypingUsecase {
    onTyping(channelId: ChannelId, userId: UserId): Promise<void> | void;
    onStopTyping(channelId: ChannelId, userId: UserId): Promise<void> | void;
}