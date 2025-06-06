package kafka_study.common.outboxmessagerelay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageRelay {

    private final OutboxRepository outboxRepository;
    private final MessageRelayCoordinator messageRelayCoordinator;
    private final KafkaTemplate<String, String> messageRelayKafkaTemplate;

    private final TransactionTemplate transactionTemplate;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void createOutbox(OutboxEvent outboxEvent) {
        log.info("[MessageRelay.createOutbox] outboxEvent={}", outboxEvent);
        outboxRepository.save(outboxEvent.getOutbox());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("messageRelayPublishEventExecutor")
    public void publishEvent(OutboxEvent outboxEvent) {
        publishEvent(outboxEvent.getOutbox());
    }

    private void publishEvent(Outbox outbox) {

        try {
            messageRelayKafkaTemplate.send(
                    outbox.getEventType().getTopic(),
                    outbox.getPartitionKey(),
                    String.valueOf(outbox.getShardKey()),
                    outbox.getPayload()
            ).get(1, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            log.error("[MessageRelay.publishEvent] outbox={}", outbox, e);
        }
        finally {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    outboxRepository.updateStatusByOutboxNo(outbox.getOutboxNo(), OutBoxStatus.SUCCESS);
                }
            });


        }
    }

    @Scheduled(
            fixedDelay = 10,
            initialDelay = 5,
            timeUnit = TimeUnit.SECONDS,
            scheduler = "messageRelayPublishPendingEventExecutor"
    )
    public void publishPendingEvent() {

//        AssignedShard assignedShard = messageRelayCoordinator.assignShards();

//        log.info("[MessageRelay.publishPendingEvent] assignedShard size={}", assignedShard.getShards().size());

        List<Outbox> outboxes = outboxRepository.selectOutboxListByStatusAndCreatedDateTimeLessThanEqualOrderByCreatedDateTimeAsc(
                OutBoxStatus.INIT,
                LocalDateTime.now().minusSeconds(10),
                100
        );

        for (Outbox outbox : outboxes) {
            publishEvent(outbox);
        }

//        for (Long shard : assignedShard.getShards()) {
//            List<Outbox> outboxes = outboxRepository.selectOutboxListByShardKeyAndStatusAndCreatedDateTimeLessThanEqualOrderByCreatedDateTimeAsc(
//                    shard,
//                    OutBoxStatus.INIT,
//                    LocalDateTime.now().minusSeconds(10),
//                    100
//            );
//
//            for (Outbox outbox : outboxes) {
//                publishEvent(outbox);
//            }
//        }
    }
}
