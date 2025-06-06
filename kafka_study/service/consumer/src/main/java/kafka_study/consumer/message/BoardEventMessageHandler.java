package kafka_study.consumer.message;

import kafka_study.common.event.Event;
import kafka_study.common.event.EventType;
import kafka_study.common.event.payload.InsertBoardEventPayload;
import kafka_study.consumer.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardEventMessageHandler implements EventHandler<InsertBoardEventPayload> {

    private final BoardService boardService;

    @Override
    public void handle(Event<InsertBoardEventPayload> event) {

        InsertBoardEventPayload payload = event.getPayload();
        boardService.updateBoard(payload.getBoardNo());
    }

    @Override
    public boolean supports(Event<InsertBoardEventPayload> event) {
        return EventType.INSERT_BOARD == event.getType();
    }
}
