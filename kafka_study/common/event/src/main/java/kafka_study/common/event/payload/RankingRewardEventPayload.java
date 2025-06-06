package kafka_study.common.event.payload;

import kafka_study.common.event.EventPayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class RankingRewardEventPayload implements EventPayload {

    private long rankingRewardNo;
    private LocalDate referenceDate;
    private List<RankingRewardDetailEventPayload> rankingRewardDetailList;

    @Builder
    private RankingRewardEventPayload(long rankingRewardNo, LocalDate referenceDate, List<RankingRewardDetailEventPayload> rankingRewardDetailList) {
        this.rankingRewardNo = rankingRewardNo;
        this.referenceDate = referenceDate;
        this.rankingRewardDetailList = rankingRewardDetailList;
    }

    public static RankingRewardEventPayload of(long rankingRewardNo, LocalDate referenceDate,  List<RankingRewardDetailEventPayload> rankingRewardDetailEventPayloads) {

        return RankingRewardEventPayload.builder()
                .rankingRewardNo(rankingRewardNo)
                .referenceDate(referenceDate)
                .rankingRewardDetailList(rankingRewardDetailEventPayloads)
                .build();
    }
}
