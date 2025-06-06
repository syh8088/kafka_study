package kafka_study.common.outboxmessagerelay;

import kafka_study.common.event.Event;
import kafka_study.common.event.EventPayload;
import kafka_study.common.event.EventType;
import kafka_study.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {

    private final Snowflake outboxIdSnowflake = new Snowflake();
    private final Snowflake eventIdSnowflake = new Snowflake();

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(EventType type, EventPayload payload, String idempotencyKey) {

        Outbox outbox = Outbox.of(
                outboxIdSnowflake.nextId(),
                type,
                Event.of(
                        eventIdSnowflake.nextId(), type, payload
                ).toJson(),
                idempotencyKey
        );

        applicationEventPublisher.publishEvent(OutboxEvent.of(outbox));
    }

}
