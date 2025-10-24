import type { UserId } from "../../../domain/types.js";

export interface SubscriptionRegistry {
    register(userId: UserId, socketId: string): void;
    unregister(userId: UserId, socketId: string): void;
    getSocketIdsByUser(userId: UserId): string[];
}