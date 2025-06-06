package kafka_study.consumer.message;


import kafka_study.common.event.Event;
import kafka_study.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {

    void handle(Event<T> event);

    boolean supports(Event<T> event);
}
