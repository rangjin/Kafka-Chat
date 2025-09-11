export const parseJsonSafe = <T>(raw: Buffer | string): T | null => {
    try {
        const s = Buffer.isBuffer(raw) ? raw.toString('utf8') : raw;
        const once = JSON.parse(s);

        if (typeof once === 'string') return JSON.parse(once) as T;
        return once as T;
    } catch {
        return null;
    }
};
