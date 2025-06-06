package kafka_study.common.outboxmessagerelay;

import jakarta.persistence.*;
import kafka_study.common.event.EventType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "outbox")
public class Outbox extends CommonEntity {

    @Id
    @Column(name = "outbox_no")
    private Long outboxNo;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OutBoxStatus status;

    @Column(name = "type")
    private String type;

    @Column(name = "partition_key")
    private Integer partitionKey;

    @Column(name = "idempotency_key")
    private String idempotencyKey;

    @Column(name = "payload", columnDefinition = "LONGTEXT")
    private String payload;

    @Column(name = "metadata", columnDefinition = "LONGTEXT")
    private String metadata;

    @Column(name = "shard_key")
    private Long shardKey;

    @Builder
    private Outbox(Long outboxNo, EventType eventType, OutBoxStatus status, String type, Integer partitionKey, String idempotencyKey, String payload, String metadata, Long shardKey) {
        this.outboxNo = outboxNo;
        this.eventType = eventType;
        this.status = status;
        this.type = type;
        this.partitionKey = partitionKey;
        this.idempotencyKey = idempotencyKey;
        this.payload = payload;
        this.metadata = metadata;
        this.shardKey = shardKey;
    }

    public static Outbox of(Long outboxNo, EventType eventType, String payload, String idempotencyKey) {

        Outbox outbox = new Outbox();

        outbox.outboxNo = outboxNo;
        outbox.eventType = eventType;
        outbox.payload = payload;
//        outbox.shardKey = ShardKeyUtil.createShardKey(idempotencyKey.hashCode());
        outbox.partitionKey = PartitionKeyUtil.createPartitionKey(idempotencyKey.hashCode());
        outbox.idempotencyKey = idempotencyKey;
        outbox.status = OutBoxStatus.INIT;
        return outbox;
    }
}
