export interface RealtimeGateway {
    emitToRoom<T>(room: string, event: string, payload: T): void;
    joinSocketsToRoom(socketIds: string[], room: string): void;
    leaveSocketsFromRoom(socketIds: string[], room: string): void;
};