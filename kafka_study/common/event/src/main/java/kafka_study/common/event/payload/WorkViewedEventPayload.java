package kafka_study.common.event.payload;

import kafka_study.common.event.EventPayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class WorkViewedEventPayload implements EventPayload {

    private long workNo;
    private LocalDate referenceDate;
    private long workViewCount;

    @Builder
    private WorkViewedEventPayload(long workNo, LocalDate referenceDate, long workViewCount) {
        this.workNo = workNo;
        this.referenceDate = referenceDate;
        this.workViewCount = workViewCount;
    }

    public static WorkViewedEventPayload of(Long workNo, LocalDate referenceDate, Long workViewCount) {
        return WorkViewedEventPayload.builder()
                .workNo(workNo)
                .referenceDate(referenceDate)
                .workViewCount(workViewCount)
                .build();
    }
}
