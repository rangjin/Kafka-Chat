import type { MembershipPayload, MessagePayload } from "../../presentation/messaging/kafka-events.js";
import { roomOf } from "../../domain/room.js";
import type { UserId, ChannelId } from "../../domain/types.js";
import type { OnMembershipUseCase } from "../port/inbound/on-membership.usecase.js";
import type { OnMessagingUsecase } from "../port/inbound/on-messaging.usecase.js";
import type { OnSocketUseCase } from "../port/inbound/on-socket.usecase.js";
import type { OnTypingUsecase } from "../port/inbound/on-typing.usecase.js";
import type { RealtimeGateway } from "../port/outbound/realtime.gateway.js";
import type { SubscriptionRegistry } from "../port/outbound/subscription.registry.js";

export class SocketService implements OnSocketUseCase, OnMessagingUsecase, OnMembershipUseCase, OnTypingUsecase {
    constructor (
        private readonly realtimeGateway: RealtimeGateway, 
        private readonly subscriptionRegistry: SubscriptionRegistry
    ) {}

    registerSocket(userId: UserId, socketId: string): void {
        this.subscriptionRegistry.register(userId, socketId);
    }

    unregisterSocket(userId: UserId, socketId: string): void {
        this.subscriptionRegistry.unregister(userId, socketId);
    }

    joinInitialChannels(socketId: string, channels: ChannelId[]): void {
        for (const ch of channels) {
            this.realtimeGateway.joinSocketsToRoom([socketId], roomOf(ch));
        }
    }

    onMessaging(message: MessagePayload): Promise<void> | void {
        this.realtimeGateway.emitToRoom(roomOf(message.channelId), 'message', message);
    }

    onJoin(mem: MembershipPayload): Promise<void> | void {
        const ids = this.subscriptionRegistry.getSocketIdsByUser(mem.userId);
        if (ids.length === 0) return;
        this.realtimeGateway.joinSocketsToRoom(ids, roomOf(mem.channelId));
        this.realtimeGateway.emitToRoom(roomOf(mem.channelId), 'member:join', mem);
    }

    onLeave(mem: MembershipPayload): Promise<void> | void {
        const ids = this.subscriptionRegistry.getSocketIdsByUser(mem.userId);
        if (ids.length === 0) return;
        this.realtimeGateway.leaveSocketsFromRoom(ids, roomOf(mem.channelId));
        this.realtimeGateway.emitToRoom(roomOf(mem.channelId), 'member:leave', mem);
    }

    onTyping(channelId: ChannelId, userId: UserId): Promise<void> | void {
        this.realtimeGateway.emitToRoom(roomOf(channelId), 'typing', userId);
    }

    onStopTyping(channelId: ChannelId, userId: UserId): Promise<void> | void {
        this.realtimeGateway.emitToRoom(roomOf(channelId), 'stop-typing', userId);
    }

} 