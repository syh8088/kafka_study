package kafka_study.common.event.payload;

import kafka_study.common.event.EventPayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class WorkUnLikedEventPayload implements EventPayload {

    private long workLikeNo;
    private long workNo;
    private long consumerNo;

    private long workLikeCountNo;
    private LocalDate referenceDate;
    private long likeCount;

    @Builder
    private WorkUnLikedEventPayload(long workLikeNo, long workNo, long consumerNo, long workLikeCountNo, LocalDate referenceDate, long likeCount) {
        this.workLikeNo = workLikeNo;
        this.workNo = workNo;
        this.consumerNo = consumerNo;
        this.workLikeCountNo = workLikeCountNo;
        this.referenceDate = referenceDate;
        this.likeCount = likeCount;
    }

    public static WorkUnLikedEventPayload of(long workLikeNo, long workNo, long consumerNo, long workLikeCountNo, LocalDate referenceDate, long likeCount) {
        return WorkUnLikedEventPayload.builder()
                .workLikeNo(workLikeNo)
                .workNo(workNo)
                .consumerNo(consumerNo)
                .workLikeCountNo(workLikeCountNo)
                .referenceDate(referenceDate)
                .likeCount(likeCount)
                .build();
    }
}
