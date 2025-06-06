package kafka_study.producer.controller;

import kafka_study.producer.model.req.InsertBoardReq;
import kafka_study.producer.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<?> saveBoard(@ModelAttribute InsertBoardReq request) {

        boardService.insertBoard(request.getTitle(), request.getContent());
        return ResponseEntity.noContent().build();
    }
}
