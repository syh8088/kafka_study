package kafka_study.common.outboxmessagerelay;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PartitionKeyUtil {

	// 파티션 키 카운트가 3인 이유는 카프카의 페이먼트 토픽 파티션 값이 3이라고 그렇다.
	private static final int PARTITION_KEY_COUNT = 3;

	public static int createPartitionKey(int number) {
		return Math.abs(number) % PARTITION_KEY_COUNT;
	}
}