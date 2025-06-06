package kafka_study.common.outboxmessagerelay;

public interface OutBoxStatusUpdatePort {

    void updateStatusByIdempotencyKey(String orderId, OutBoxStatus outBoxStatus);
}
