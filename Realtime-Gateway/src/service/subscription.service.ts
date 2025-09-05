import type { Server, Socket } from 'socket.io';

export const roomOf = (channelId: number) => `channel:${channelId}`;

const userSockets = new Map<number, Set<string>>();

export const registerUserSocket = (userId: number, socketId: string) => {
    const set = userSockets.get(userId) ?? new Set<string>();
    set.add(socketId);
    userSockets.set(userId, set);
};

export const unregisterUserSocket = (userId: number, socketId: string) => {
    const set = userSockets.get(userId);
    if (!set) return;
    set.delete(socketId);
    if (set.size === 0) userSockets.delete(userId);
};

export const joinInitialChannels = (io: Server, socket: Socket, channels: number[]) => {
    channels.forEach((ch) => socket.join(roomOf(ch)));
    socket.data.channels = channels;
};

export const emitToChannel = <T>(io: Server, channelId: number, event: string, payload: T) => {
    io.to(roomOf(channelId)).emit(event, payload);
};

export const forceJoinChannelByUser = (io: Server, userId: number, channelId: number) => {
    if (!Number.isFinite(channelId)) return;
    const ids = userSockets.get(userId);
    if (!ids || ids.size === 0) return;
    io.in([...ids]).socketsJoin(roomOf(channelId));
};

export const forceLeaveChannelByUser = (io: Server, userId: number, channelId: number) => {
    if (!Number.isFinite(channelId)) return;
    const ids = userSockets.get(userId);
    if (!ids || ids.size === 0) return;
    io.in([...ids]).socketsLeave(roomOf(channelId));
};
