package kafka_study.common.event;

import kafka_study.common.event.payload.RankingRewardEventPayload;
import kafka_study.common.event.payload.WorkLikedEventPayload;
import kafka_study.common.event.payload.WorkUnLikedEventPayload;
import kafka_study.common.event.payload.WorkViewedEventPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {

    WORK_LIKED(WorkLikedEventPayload.class, Topic.WORK_LIKE),
    WORK_UNLIKED(WorkUnLikedEventPayload.class, Topic.WORK_LIKE),
    WORK_VIEWED(WorkViewedEventPayload.class, Topic.WORK_VIEW),
    RANKING_REWARD(RankingRewardEventPayload.class, Topic.RANKING_REWARD),


    ;

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        }
        catch (Exception e) {
            log.error("[EventType.from] type={}", type, e);
            return null;
        }
    }

    public static class Topic {
        public static final String WORK_LIKE = "work-like";
        public static final String WORK_VIEW = "work-review";
        public static final String RANKING_REWARD = "ranking-reward";
    }
}
