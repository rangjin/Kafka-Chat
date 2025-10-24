export interface EventConsumer {
    startConsumer(onEvent: (e: string) => Promise<void> | void): Promise<void>
}