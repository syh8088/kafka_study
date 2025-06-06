package kafka_study.common.outboxmessagerelay;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    @Query("select o from Outbox AS o where o.shardKey = :shardKey and o.status = :outBoxStatus and o.createdDateTime <= :createdDateTime order by o.createdDateTime asc limit :limit")
    List<Outbox> selectOutboxListByShardKeyAndStatusAndCreatedDateTimeLessThanEqualOrderByCreatedDateTimeAsc(
            @Param("shardKey") long shardKey,
            @Param("outBoxStatus") OutBoxStatus outBoxStatus,
            @Param("createdDateTime") LocalDateTime createdDateTime,
            @Param("limit") int limit
    );

    @Query("select o from Outbox AS o where o.status = :outBoxStatus and o.createdDateTime <= :createdDateTime order by o.createdDateTime asc limit :limit")
    List<Outbox> selectOutboxListByStatusAndCreatedDateTimeLessThanEqualOrderByCreatedDateTimeAsc(
            @Param("outBoxStatus") OutBoxStatus outBoxStatus,
            @Param("createdDateTime") LocalDateTime createdDateTime,
            @Param("limit") int limit
    );

    List<Outbox> findAllByShardKeyAndCreatedDateTimeLessThanEqualOrderByCreatedDateTimeAsc(
            Long shardKey,
            LocalDateTime from,
            Pageable pageable
    );

    @Modifying
    @Query("UPDATE Outbox AS o SET o.status = :outBoxStatus WHERE o.idempotencyKey = :idempotencyKey")
    void updateStatusByIdempotencyKey(@Param("idempotencyKey") String orderId, @Param("outBoxStatus") OutBoxStatus outBoxStatus);

    @Modifying
    @Query("UPDATE Outbox AS o SET o.status = :outBoxStatus WHERE o.outboxNo = :outboxNo")
    void updateStatusByOutboxNo(@Param("outboxNo") long outboxNo, @Param("outBoxStatus") OutBoxStatus outBoxStatus);

}
