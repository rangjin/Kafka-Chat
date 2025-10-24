import type { User } from "../../../domain/user.js";

export interface UserRepository {
    findByToken(token: string): Promise<User>;
};