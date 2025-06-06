package kafka_study.common.event.payload;

import kafka_study.common.event.EventPayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class InsertBoardEventPayload implements EventPayload {

    private long boardNo;
    private String title;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    @Builder
    private InsertBoardEventPayload(long boardNo, String title, String content, LocalDateTime createdDateTime, LocalDateTime updatedDateTime) {
        this.boardNo = boardNo;
        this.title = title;
        this.content = content;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    public static InsertBoardEventPayload of(long boardNo, String title, String content, LocalDateTime createdDateTime, LocalDateTime updatedDateTime) {
        return InsertBoardEventPayload.builder()
                .boardNo(boardNo)
                .title(title)
                .content(content)
                .createdDateTime(createdDateTime)
                .updatedDateTime(updatedDateTime)
                .build();
    }
}
