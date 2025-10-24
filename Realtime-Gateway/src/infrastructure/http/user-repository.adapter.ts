import fetch from "node-fetch";
import type { UserRepository } from "../../application/port/outbound/user.repository.js";
import { User } from "../../domain/user.js";
import { parseJsonSafe } from "../../common/json.util.js";

export class UserRepositoryAdapter implements UserRepository {
    constructor(
        private readonly baseUrl = process.env.CHAT_API_BASE!!, 
        private readonly channelsEndpoint = process.env.CHANNELS_ENDPOINT!
    ) {}

    async findByToken(token: string): Promise<User> {
        const res = await fetch(`${this.baseUrl}${this.channelsEndpoint}`, {
            headers: {
                Authorization: token
            }
        });
        if (res.status != 200) throw new Error('Authorization Failed');
        const json = await res.json() as {
            userId: number, 
            channelIds: number[]
        };
        return new User(json.userId, json.channelIds)
    }
}