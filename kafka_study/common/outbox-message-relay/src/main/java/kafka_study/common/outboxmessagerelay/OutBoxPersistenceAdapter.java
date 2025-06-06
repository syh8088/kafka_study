package kafka_study.common.outboxmessagerelay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class OutBoxPersistenceAdapter implements OutBoxStatusUpdatePort {

    private final OutboxRepository outboxRepository;

    @Transactional
    @Override
    public void updateStatusByIdempotencyKey(String orderId, OutBoxStatus outBoxStatus) {
        outboxRepository.updateStatusByIdempotencyKey(orderId, outBoxStatus);
    }
}
