import type { Server, Socket } from 'socket.io';

export const roomOf = (channelId: number) => `channel:${channelId}`;

export const joinInitialChannels = (io: Server, socket: Socket, channels: number[]) => {
    channels.forEach((ch) => socket.join(roomOf(ch)));
    socket.data.channels = channels;
};

export const emitToChannel = <T>(io: Server, channelId: number, event: string, payload: T) => {
    io.to(roomOf(channelId)).emit(event, payload);
};
