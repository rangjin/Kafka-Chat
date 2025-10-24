import type { User } from "../../domain/user.js";
import type { UserUsecase } from "../port/inbound/user.usecase.js";
import type { UserRepository } from "../port/outbound/user.repository.js";

export class UserService implements UserUsecase {
    constructor(
        private readonly userRepository: UserRepository,
    ) {}

    async getUser(authorization: string): Promise<User> {
        return await this.userRepository.findByToken(authorization);
    }
}