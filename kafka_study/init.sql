CREATE DATABASE kafka_study;

create table kafka_study.outbox
(
    outbox_no       bigint                              not null
        primary key,
    shard_key       bigint                              null,
    event_type      varchar(100)                        not null,
    idempotency_key varchar(128)                        null,
    status          enum ('INIT', 'FAILURE', 'SUCCESS') not null,
    type            varchar(50)                         null,
    partition_key   int                                 null,
    payload         longtext                            null,
    metadata        longtext                            null,
    created_at      datetime default CURRENT_TIMESTAMP  not null,
    updated_at      datetime default CURRENT_TIMESTAMP  not null on update CURRENT_TIMESTAMP,
    constraint idempotency_key
        unique (idempotency_key)
);

create table kafka_study.boards
(
    board_no   bigint auto_increment
        primary key,
    title      varchar(255)                not null,
    content    text                        not null,
    use_yn     enum ('Y', 'N') default 'N' not null,
    created_at datetime                    not null,
    updated_at datetime                    null
);
