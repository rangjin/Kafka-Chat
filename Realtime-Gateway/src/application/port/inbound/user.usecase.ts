import type { User } from "../../../domain/user.js";

export interface UserUsecase {
    getUser(authorization: String): Promise<User>;
}