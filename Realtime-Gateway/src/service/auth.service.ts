import { getMychannelsId } from '../infra/client/chat-api.client.js';

export const authorizeAndLoadChanenls = async (authHeader: string) => {
    if (!authHeader.startsWith('Bearer ')) {
        console.log("Invalid authorization header'");
        throw new Error('Invalid authorization header');
    }
    return await getMychannelsId(authHeader);
};
