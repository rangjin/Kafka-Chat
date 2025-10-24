import type { MembershipPayload } from '../../../domain/events.js';

export interface OnMembershipUseCase {
    onJoin(mem: MembershipPayload): Promise<void> | void;
    onLeave(mem: MembershipPayload): Promise<void> | void;
}
