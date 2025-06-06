package kafka_study.common.outboxmessagerelay;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 샤딩이 되어 있는 상황으로 가정하고 만들시 샤드 갯수를 임의로 4개로 가정하고
 * 이걸 통해서 각각 애플리케이션 마다 적절하게 샤드가 분산되어서 이벤트 전송 수행하는 것
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageRelayConstants {

    public static final int SHARD_COUNT = 4; // 임의의 값
}
