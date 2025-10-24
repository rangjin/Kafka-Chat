import dotenv from 'dotenv';
import { createApp } from './presentation/http/app.js';
import { createSocketServer, SocketAdapter } from './infrastructure/socket/socket.adapter.js';
import type { UserRepository } from './application/port/outbound/user.repository.js';
import { UserRepositoryAdapter } from './infrastructure/http/user-repository.adapter.js';
import { SocketService } from './application/service/socket.service.js';
import type { SubscriptionRegistry } from './application/port/outbound/subscription.registry.js';
import type { RealtimeGateway } from './application/port/outbound/realtime.gateway.js';
import type { UserUsecase } from './application/port/inbound/user.usecase.js';
import { UserService } from './application/service/user.service.js';
import type { OnMembershipUseCase } from './application/port/inbound/on-membership.usecase.js';
import type { OnMessagingUsecase } from './application/port/inbound/on-messaging.usecase.js';
import type { OnSocketUseCase } from './application/port/inbound/on-socket.usecase.js';
import type { OnTypingUsecase } from './application/port/inbound/on-typing.usecase.js';
import { SocketController } from './presentation/socket/socket.controller.js';
import { KafkaConsumer } from './presentation/messaging/kafka.consumer.js';

dotenv.config();

async function bootstrap() {
    const app = createApp();
    const { server, io } = createSocketServer(app);

    // outbound
    const userRepository: UserRepository = new UserRepositoryAdapter();
    const socketAdapter: SocketAdapter = new SocketAdapter(io);
    const realtimeGateway: RealtimeGateway = socketAdapter;
    const SubscriptionRegistry: SubscriptionRegistry = socketAdapter;

    // inbound
    const userUseCase: UserUsecase = new UserService(userRepository);
    const socketService: SocketService = new SocketService(realtimeGateway, SubscriptionRegistry);
    const onMembershipUseCase: OnMembershipUseCase = socketService;
    const onMessagingUseCase: OnMessagingUsecase = socketService;
    const onSocketUseCase: OnSocketUseCase = socketService;
    const onTypingUsecase: OnTypingUsecase = socketService;

    // controller
    const socketController: SocketController = new SocketController(
        io,
        userUseCase,
        onSocketUseCase,
        onTypingUsecase,
    );
    socketController.registerMiddleWare();
    socketController.registerHandlers();
    const kafkaConsumer: KafkaConsumer = new KafkaConsumer(onMessagingUseCase,onMembershipUseCase,);
    await kafkaConsumer.startConsume();

    const port = process.env.PORT ?? '3000';
    server.listen(port, () => {
        console.log(`Realtime-Gateway (Socket.IO) listening on : ${port}`);
    });
}

bootstrap().catch((err) => {
    console.error('Fatal bootstrap error:', err);
    process.exit(1);
});
