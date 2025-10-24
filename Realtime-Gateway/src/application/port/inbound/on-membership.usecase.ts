import type { MembershipPayload } from "../../../presentation/messaging/kafka-events.js";

export interface OnMembershipUseCase {
  onJoin(mem: MembershipPayload): Promise<void> | void;
  onLeave(mem: MembershipPayload): Promise<void> | void;
}