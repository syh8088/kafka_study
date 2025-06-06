package kafka_study.common.event.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RankingRewardDetailEventPayload {

    private long rankingRewardDetailNo;
    private long workNo;
    private int rankingOrder;

    @Builder
    private RankingRewardDetailEventPayload(long rankingRewardDetailNo, long workNo, int rankingOrder) {
        this.rankingRewardDetailNo = rankingRewardDetailNo;
        this.workNo = workNo;
        this.rankingOrder = rankingOrder;
    }

}
