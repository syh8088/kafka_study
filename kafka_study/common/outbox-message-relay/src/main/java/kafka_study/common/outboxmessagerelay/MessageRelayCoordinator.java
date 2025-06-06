package kafka_study.common.outboxmessagerelay;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MessageRelayCoordinator {

//    private final StringRedisTemplate redisTemplate;

    @Value("${spring.application.name}")
    private String applicationName;

    private final String APP_ID = UUID.randomUUID().toString();

    private final int PING_INTERVAL_SECONDS = 3;
    private final int PING_FAILURE_THRESHOLD = 3;

//    public AssignedShard assignShards() {
//        return AssignedShard.of(APP_ID, this.findAppIds(), MessageRelayConstants.SHARD_COUNT);
//    }
//
//    private List<String> findAppIds() {
//        return redisTemplate.opsForZSet()
//                .reverseRange(this.generateKey(), 0, -1)
//                .stream()
//                .sorted()
//                .toList();
//    }

//    @Scheduled(fixedDelay = PING_INTERVAL_SECONDS, timeUnit = TimeUnit.SECONDS)
//    public void ping() {
//
//        /**
//         * executePipelined() -> 레디스 서버에 네트워크 한번만 연결하면서 여러번 연산을 수행 할 수 있다.
//         */
//        redisTemplate.executePipelined((RedisCallback<?>) action -> {
//            StringRedisConnection conn = (StringRedisConnection) action;
//            String key = generateKey();
//
//            /**
//             * Sorted Sets SET
//             * ex> ZADD ${key}(KEY) ${Instant.now().toEpochMilli()}(SCORE) ${APP_ID}(MEMBER)
//             */
//            conn.zAdd(key, Instant.now().toEpochMilli(), APP_ID);
//
//            /**
//             * zremrangebyscore key min max 예) zremrangebyscore usersorted 11 20,
//             * score로 범위를 지정해서 member 삭제 합니다. 기본적으로 min, max를 포함한 범위에서 삭제 합니다.
//             * 모두 삭제 하려면 min max에 -inf +inf를 사용하면 됩니다.
//             */
//            conn.zRemRangeByScore( // zremrangebyscore : 정렬된 셋에서 가중치에 해당하는 범위의 데이터 제거
//                    key,
//                    Double.NEGATIVE_INFINITY, //  double 유형의 음의 무한대 값
//                    Instant.now().minusSeconds(PING_INTERVAL_SECONDS * PING_FAILURE_THRESHOLD).toEpochMilli()
//            );
//            return null;
//        });
//    }
//
//    @PreDestroy
//    public void leave() {
//        redisTemplate.opsForZSet().remove(generateKey(), APP_ID);
//    }
//
//    private String generateKey() {
//        return "message-relay-coordinator::app-list::%s".formatted(applicationName);
//    }
}
