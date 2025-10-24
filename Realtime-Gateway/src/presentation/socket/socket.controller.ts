import type { Server, Socket } from 'socket.io';
import type { ChannelId, UserId } from '../../domain/types.js';
import type { OnSocketUseCase } from '../../application/port/inbound/on-socket.usecase.js';
import type { OnTypingUsecase } from '../../application/port/inbound/on-typing.usecase.js';
import type { UserUsecase } from '../../application/port/inbound/user.usecase.js';
import { roomOf } from '../../domain/room.js';

export class SocketController {
    constructor(
        private readonly io: Server,
        private readonly userUseCase: UserUsecase,
        private readonly onSocketConnectedUseCase: OnSocketUseCase,
        private readonly onTypingUseCase: OnTypingUsecase,
    ) {}

    registerMiddleWare() {
        this.io.use(async (socket, next) => {
            try {
                const auth = socket.handshake.headers['authorization'];
                if (typeof auth !== 'string') throw new Error('Invalid authorization header');

                const user = await this.userUseCase.getUser(auth);
                socket.data.userId = user.id;
                socket.data.channels = user.channelIds;
                next();
            } catch (e) {
                next(e instanceof Error ? e : new Error('Unknown error during socket auth'));
            }
        });
    }

    registerHandlers() {
        this.io.on('connection', (socket: Socket) => {
            const userId: UserId = socket.data.userId;
            const channels: ChannelId[] = socket.data.channels ?? [];

            if (Number.isFinite(userId)) {
                this.onSocketConnectedUseCase.registerSocket(userId, socket.id);
            }

            channels.forEach((ch) => socket.join(roomOf(ch)));
            socket.data.channels = channels;

            socket.on('disconnect', () => {
                this.onSocketConnectedUseCase.unregisterSocket(userId, socket.id);
            });

            socket.on('typing', (channelId: ChannelId) => {
                this.onTypingUseCase.onTyping(channelId, userId);
            });

            socket.on('stop-typing', (channelId: ChannelId) => {
                this.onTypingUseCase.onStopTyping(channelId, userId);
            });
        });
    }
}
