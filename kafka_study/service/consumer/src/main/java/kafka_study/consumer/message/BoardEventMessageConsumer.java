package kafka_study.consumer.message;

import kafka_study.common.event.Event;
import kafka_study.common.event.EventPayload;
import kafka_study.common.event.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardEventMessageConsumer {

    private final List<EventHandler> eventHandlers;

    @KafkaListener(
            topics = {
                EventType.Topic.BOARD
            },
            groupId = "consumer-service"
    )
    public void listen(String message, Acknowledgment ack) {

        log.info("[BoardEventMessageConsumer.listen] message={}", message);

        // throw new Exception("일부러 Exception 발생 시키기");
        Event<EventPayload> event = Event.fromJson(message);
        if (!Objects.isNull(event)) {
            this.handleEvent(event);
        }

        ack.acknowledge();
    }

    private void handleEvent(Event<EventPayload> event) {
        for (EventHandler eventHandler : eventHandlers) {
            if (eventHandler.supports(event)) {
                eventHandler.handle(event);
            }
        }
    }
}
