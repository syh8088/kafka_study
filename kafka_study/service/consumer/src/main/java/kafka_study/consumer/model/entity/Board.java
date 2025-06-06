package kafka_study.consumer.model.entity;

import jakarta.persistence.*;
import kafka_study.consumer.model.enums.UseYn;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "boards")
@Getter
@NoArgsConstructor
public class Board extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNo;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private UseYn useYn;

    public static Board createBoard(String title, String content) {
        Board board = new Board();
        board.title = title;
        board.content = content;
        board.useYn = UseYn.N;

        return board;
    }

}
