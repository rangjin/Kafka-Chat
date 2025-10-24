import http from 'http';
import type { Express } from 'express';
import { Server } from 'socket.io';
import type { RealtimeGateway } from '../../application/port/outbound/realtime.gateway.js';
import type { SubscriptionRegistry } from '../../application/port/outbound/subscription.registry.js';
import type { UserId } from '../../domain/types.js';

export const createSocketServer = (app: Express) => {
    const server = http.createServer(app);
    const io = new Server(server, {
        path: '/realtime',
        cors: {
            origin: '*',
            methods: ['GET', 'POST'],
        },
    });
    return { server, io };
};

export class SocketAdapter implements SubscriptionRegistry, RealtimeGateway {
    constructor(private readonly io: Server) {}

    private readonly map = new Map<UserId, Set<string>>();

    register(userId: UserId, socketId: string): void {
        const set = this.map.get(userId) ?? new Set<string>();
        set.add(socketId);
        this.map.set(userId, set);
    }

    unregister(userId: UserId, socketId: string): void {
        const set = this.map.get(userId);
        if (!set) return;
        set.delete(socketId);
        if (set.size === 0) this.map.delete(userId);
    }

    getSocketIdsByUser(userId: UserId): string[] {
        return [...(this.map.get(userId) ?? [])];
    }

    emitToRoom<T>(room: string, event: string, payload: T): void {
        this.io.to(room).emit(event, payload);
    }

    joinSocketsToRoom(socketIds: string[], room: string): void {
        if (!room || socketIds.length === 0) return;
        this.io.in(socketIds).socketsJoin(room);
    }

    leaveSocketsFromRoom(socketIds: string[], room: string): void {
        if (!room || socketIds.length === 0) return;
        this.io.in(socketIds).socketsLeave(room);
    }
}
