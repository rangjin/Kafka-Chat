import type { ChannelId } from "./types.js";

export const roomOf = (channelId: ChannelId) => `channel:${channelId}`;