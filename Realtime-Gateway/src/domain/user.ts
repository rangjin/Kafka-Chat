import type { ChannelId, UserId } from "./types.js";

export class User {
    constructor(
        public readonly id: UserId,
        public readonly channelIds: ChannelId[],
    ) {}
}