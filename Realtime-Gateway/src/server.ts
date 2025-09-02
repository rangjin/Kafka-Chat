import http from 'http';
import dotenv from 'dotenv';
import { createApp } from './app.js';
import { Server as IOServer } from 'socket.io';
import { authorizeAndLoadChanenls } from './service/auth.service.js';
import { joinInitialChannels } from './service/subscription.service.js';
import { startKafkaConsumer } from './infra/kafka/kafka-consumer.js';

dotenv.config();

const app = createApp();
const server = http.createServer(app);

const io = new IOServer(server, {
    path: '/realtime',
    cors: {
        origin: '*',
        methods: ['GET', 'POST'],
    },
});

io.use(async (socket, next) => {
    try {
        const auth = socket.handshake.headers['authorization'];
        if (typeof auth != 'string') throw new Error('Invalid authorization header');
        const channels = await authorizeAndLoadChanenls(auth);
        socket.data.channels = channels;
        return next();
    } catch (e: unknown) {
        if (e instanceof Error) {
            return next(e);
        }
        return next(new Error('Unknown error during socket auth'));
    }
});

io.on('connection', (socket) => {
    const channels: number[] = socket.data.channels ?? [];
    joinInitialChannels(io, socket, channels);

    socket.on('join', ({ channelId }: { channelId: number }) => {
        if (Number.isFinite(channelId)) socket.join(`channel:${channelId}`);
    });

    socket.on('leave', ({ channelId }: { channelId: number }) => {
        if (Number.isFinite(channelId)) socket.leave(`channel:${channelId}`);
    });

    socket.on('disconnect', () => {});
});

startKafkaConsumer(io).catch((err) => {
    console.error('[Kafka] failed to start consumer:', err);
    process.exit(1);
});

const port = process.env.PORT;
server.listen(port, () => {
    console.log(`Realtime-Gateway (Socket.IO) listening on : ${port}`);
});
