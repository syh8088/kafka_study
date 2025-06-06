package kafka_study.consumer.service;

import kafka_study.consumer.model.enums.UseYn;
import kafka_study.consumer.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void updateBoard(long boardNo) {
        boardRepository.updateBoardUseYnByBoardNo(boardNo, UseYn.Y);
    }
}
