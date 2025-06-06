package kafka_study.producer.service;

import kafka_study.common.event.EventType;
import kafka_study.common.event.payload.InsertBoardEventPayload;
import kafka_study.common.outboxmessagerelay.OutboxEventPublisher;
import kafka_study.producer.model.entity.Board;
import kafka_study.producer.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public void insertBoard(String title, String content) {

        Board board = Board.createBoard(title, content);
        Board savedBoard = boardRepository.save(board);

        InsertBoardEventPayload payload = InsertBoardEventPayload.of(
                savedBoard.getBoardNo(),
                savedBoard.getTitle(),
                savedBoard.getContent(),
                savedBoard.getCreatedDateTime(),
                savedBoard.getUpdatedDateTime()
        );

        outboxEventPublisher.publish(
                EventType.INSERT_BOARD,
                payload,
                String.valueOf(savedBoard.getBoardNo())
        );

    }
}
