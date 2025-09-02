import fetch from 'node-fetch';

export const getMychannelsId = async (authorization: string) => {
    try {
        const res = await fetch(`${process.env.CHAT_API_BASE}${process.env.CHANNELS_ENDPOINT}`, {
            headers: {
                Authorization: authorization,
            },
        });
        if (res.status !== 200) throw new Error('Failed to Authorization');
        const arr = (await res.json()) as { id: number }[];
        return arr.map((it) => it.id);
    } catch (error) {
        console.error(error);
        throw error;
    }
};
