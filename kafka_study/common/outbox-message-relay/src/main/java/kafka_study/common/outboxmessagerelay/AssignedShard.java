package kafka_study.common.outboxmessagerelay;

import lombok.Getter;

import java.util.List;
import java.util.stream.LongStream;

/**
 * 샤드를 각 애플리케이션에 균등하게 할당하기 위한 클래스 역활
 */
@Getter
public class AssignedShard {

    private List<Long> shards;

    /**
     * @param appId: 실행되는 애플리케이션 아이디 값
     * @param appIds: 코디네이터 의해서 지금 실행되어 있는 애플리케이션 목록
     * @param shardCount: 샤드의 갯수 값
     */
    public static AssignedShard of(String appId, List<String> appIds, long shardCount) {
        AssignedShard assignedShard = new AssignedShard();
        assignedShard.shards = assign(appId, appIds, shardCount);
        return assignedShard;
    }

    private static List<Long> assign(String appId, List<String> appIds, long shardCount) {
        int appIndex = findAppIndex(appId, appIds);
        if (appIndex == -1) {
            return List.of();
        }

        /**
         * 범위를 구한다.
         *
         * 인덱스를 통해서 Start 와 End 사이에 있는 범위가 이 애플리케이션이 할당된 샤드가 된다.
         */
        long start = appIndex * shardCount / appIds.size();
        long end = (appIndex + 1) * shardCount / appIds.size() - 1;

        return LongStream.rangeClosed(start, end).boxed().toList();
    }

    /**
     * 지금 실행된 애플리케이션 목록을 정렬된 상태로 가지고 있다. 그래서 이 애플리케이션 아이디가 몇번째 있는지 반환
     *
     * @param appId
     * @param appIds
     * @return
     */
    private static int findAppIndex(String appId, List<String> appIds) {

        for (int i = 0; i < appIds.size(); i++) {
            if (appIds.get(i).equals(appId)) {
                return i;
            }
        }
        return -1;
    }
}
