import http from 'http';
import dotenv from 'dotenv';
import { createApp } from './app.js';
import { Server as IOServer } from 'socket.io';
import { authorizeAndLoadChannels } from './service/auth.service.js';
import {
    joinInitialChannels,
    registerUserSocket,
    unregisterUserSocket,
} from './service/subscription.service.js';
import { startKafkaConsumers } from './infra/kafka/kafka-consumer.js';

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
        if (typeof auth !== 'string') throw new Error('Invalid authorization header');

        const { userId, channelIds } = await authorizeAndLoadChannels(auth);

        socket.data.userId = userId;
        socket.data.channels = channelIds;
        return next();
    } catch (e: unknown) {
        return next(e instanceof Error ? e : new Error('Unknown error during socket auth'));
    }
});

io.on('connection', (socket) => {
    const channels: number[] = socket.data.channels ?? [];
    const userId: number | undefined = socket.data.userId;

    if (Number.isFinite(userId)) {
        registerUserSocket(userId as number, socket.id);
    }

    joinInitialChannels(io, socket, channels);

    socket.on('disconnect', () => {
        if (Number.isFinite(userId)) {
            unregisterUserSocket(userId as number, socket.id);
        }
    });
});

startKafkaConsumers(io).catch((err) => {
    console.error('[Kafka] failed to start consumer:', err);
    process.exit(1);
});

const port = process.env.PORT;
server.listen(port, () => {
    console.log(`Realtime-Gateway (Socket.IO) listening on : ${port}`);
});
