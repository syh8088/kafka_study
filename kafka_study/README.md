# Kafka 알아보기

## 로컬 환경 실행 방법

### Skill & Tools - Backend
- Java 17
- Spring boot 3.5.0
- JPA
- Mysql 9.x
- docker
- zookeeper
- kafka
- kafka-ui

### Service Endpoint

- order-service (주문 서비스)
  - http://localhost:8080

- catalog-service (상품 서비스)
  - http://localhost:8081

### FrontEnd Service Endpoint
- 게시판 글 등록하기
  - GET http://localhost:8080/?title=제목TEST&content=내용TEST

### Server Execution
```
// 1. 해당 프로젝트 루트 디렉토리 까지 이동 합니다. 

// 2. docker-compose 를 실행 합니다.
docker-compose up -d
```

## Kafka 주요 구성 요소

![Kafka 주요 구성 요소](./md_resource/Kafka_mainComponent.PNG)

- Topic: Kafka 안에서 메시지가 저장되는 장소 (논리적인 표현)
- Producer : 메시지를 생산(Produce) 해서 Kafka 의 `Topic` 으로 메시지를 보내는 애플리케이션
- Consumer : `Topic` 의 메시지를 가저와서 소비(Consume) 하는 애플리케이션
- Consumer Group : `Topic` 의 메시지를 사용하기 위해 협력하는 `Consumer` 들의 집합 

 하나의 `Consumer` 는 하나의 `Consumer Group` 에 포함되며, `Consumer Group` 내의 `Consumer` 들은 협력하여 `Topic` 의 메시지를 분산 병렬 처리함

### Kafka 기본 동작 방식 - `Producer` 와 `Consumer` 는 `Decoupling` 되어 있다.

![`Producer` 와 `Consumer` 는 `Decoupling`](./md_resource/ProducerConsumerDecoupling.PNG)

- 각각 `Producer` 와 `Consumer` 는 서로 알지 못하며 (알 필요도 없음), `Producer` 와 `Consumer` 는 각자 고유의 속도로 `Commit Log` 에 `Write` 및 `Read` 를 수행 합니다.
- 다른 `Consumer Group` 에 속한 `Consumer` 들은 서로 관련이 없으며, `Commit Log` 에 있는 `Event(Message)` 를 동시에 다른 위치에서 `Read` 할 수 있습니다.

### Kafka 기본 동작 방식 - `Kafka Commit Log` 는 추가만 가능하고 변경 불가능한 데이터 스트럭처

![Kafka Commit Log](./md_resource/KafkaCommitLog.PNG)

- Commit Log : 추가만 가능하고 변경 불가능한 데이터 스트럭처 데이터(Event)는 항상 로그 끝에 추가되고 변경되지 않습니다.
- Offset : `Commit Log` 의 `Event` 위치 
- CUREENT-OFFSET: `Consumer` 가 지금 현재 내가 어디까지 읽었는지 나타내는 용어
- LOG-END-OFFSET: `Producer` 가 지금 현재 어디까지 쓰기 를 했는지 나타내는 용어
- Consumer Lag: `CUREENT-OFFSET` 하고 `LOG-END-OFFSET` 차이

### Kafka 논리적 표현 - `Topic`, `Partition`, `Segment`

![Kafka 논리적 표현](./md_resource/KafkaLogicalRepresentation.PNG)

- Topic : Kafka 안에서 메시지가 저장되는 장소, 논리적인 표현
- Partition : `Commit Log`, 하나의 `Topic`은 하나 이상의 `Partition`으로 구성
- 병렬처리(Throughput 향상)를 위해서 Multi Partition 사용 권장
- Segment : 메시지(데이터)가 저장되는 실제 물리 File `Segment File`이 지정된 크기보다 크거나 지정된 기간보다 오래되면 새 파일이 열리고 메시지는 새 파일에 추가됨

### Kafka 물리적 표현 - `Topic`, `Partition`, `Segment`

![Kafka 물리적 표현](./md_resource/KafkaPhysicalRepresentation.PNG)

- `Topic` 생성시 `Partition` 수를 Custom 해서 설정 할 수 있습니다. 
- `Topic` 으로 구성 된 각각의 `Partition` 은 `Broker` 들에 분산되며 `Segment File` 들로 저장 및 구성 하게 됩니다.
- `Topic` 생성시 `Kafka` 가 자동으로 `Topic`을 구성하는 전체 `Partition`들을 모든 `Broker`에게 할당해주고 분배 해줍니다.
- 각각의 `Topic` 은 `Partition` 들을 서로 독립적 입니다.
- `Partition` 내에 저장된 `Message (데이터)` 는 수정이 불가능 합니다.
- `Offset` 값은 처음에 0 부터 시작 되며 다시 0 으로 돌아가지 않습니다.


### Kafka Serializer & Deserializer
![Serializer&Deserializer](./md_resource/KafkaSerializer&Deserializer.PNG)

- `Kafka` 는 Record(데이터) 를 `Byte Array` 형식으로 저장 됩니다.
- `Producer` 는 `Serializer` 사용하고 반대로 `Consumer` 는 `Deserializer` 를 사용하게 됩니다.

```markdown
    @Bean
    @Primary
    public ProducerFactory<String, Object> producerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "-1");
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    @Primary
    public ConsumerFactory<String, Object> consumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, "true");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return new DefaultKafkaConsumerFactory<>(props);
    }
```

### Kafka Producer Application 내부 동작

![Kafka Producer Application 내부 동작](./md_resource/InternalWorkingsOfAKafkaProducerApplication.PNG)

우선 `Producer Application` 내에 전송 하고자 하는 `Producer Record` 데이터를 만듭니다. `Send()` 를 하게 된다면 `Serializer` 에서 미리 지정한 어떤 데이터 형식으로 Serializer 할 것인지 변환 되고 이전에 `Send()` 할때 `Partition` 을 지정을 했다면 어떤 파티션에 저장 할 것인지 지정을 하게 됩니다.

그리고 `Compress` 압축을 할 것인지 선택하게 되고 `RecordAccumulator` 이라는 곳 에서 미리 지정했던 `Partition` 0 번으로 보낼 것인지 1 번으로 보낼 것 인지 에 따라 해당 되는 `Partition` 에 가서 데이터을 모아서 실질적으로 카프카 서버에 전송 하게 됩니다.

카프카로 부터 응답이 오는데 성공적으로 저장 되었는지 아니면 실패 했는지에 따라 실패를 했다고 `재시도` 설정에 따라 다시 시도 하게 되고 성공이면 성공에 대한 응답을 하게 됩니다.


![Partition 역활](./md_resource/PartitionRole.PNG)
`Producer Application` 내에 `Producer Record` 데이터를 만들고 카프카 서버로 전송시 `Partition Key` 값을 지정 하고 전송시 `Partitioner` 에서는 Hash 알고리즘으로 어떤 `Partition` 으로 보낼지 값을 계산해서 카프카에 전송 하게 됩니다.


### Kafka Consumer Offset 알아보기

`Consumer Offset` 이란 지금까지 `Consumer Group` 에서 읽은 위치를 마크 한 Offset 을 말합니다. 즉 특정 `Consumer` 에서 해당 `Offset` 을 읽었다고 하면 수동이나 자동으로 데이터를 읽은 위치를 `commit` 해서 다시 읽음을 방지 하게 됩니다. 

`__ consumer_offsets` 라는 `Internal Topic`에서 `Consumer Offset`을 저장하여 관리 합니다.


### 멀티 Partition 과 Consumer 관계

![멀티 Partition 과 싱글 Consumer 관계](./md_resource/Multi-PartitionAndConsumerRelationship.PNG)

3개의 `Partition` 으로 구성된 `Topic A`를 데이터를 사용하는 단 1개의 `Consumer`가 있는 경우 해당 `Consumer` 는 Topic 의 모든 `Partition` 에서 모든 `Record` 를 `Consume` 하게 됩니다.


![멀티 Partition 과 멀티 Consumer 관계](./md_resource/Multi-PartitionAndMulti-ConsumerRelationship.PNG)
![멀티 Partition 과 멀티 Consumer 관계2](./md_resource/Multi-PartitionAndMulti-ConsumerRelationship2.PNG)

- 동일한 `group 아이디` 로 구성된 각각의 `Consumer` 들은 하나의 `Consumer Group` 을 형성 하게 됩니다.
- 만약 3개의 파티션이 존재하고 있는 `Topic A` 있다고 하면 `consume` 하는 `3개의 Consumer` 가 하나의 `Consumer Group (Group B)`에 있다면, 각 `Consumer` 는 정확히 하나의 `Partition`에서 `Record`를 `onsume` 하게 됩니다.
- (중요!!) `Partition`은 항상 `Consumer Group` 내의 하나의 `Consumer` 에 의해서만 사용 됩니다.
- `Consumer` 는 주어진 `Topic` 에서 0개 이상의 많은 `Partition` 을 사용할 수 있습니다.

### Kafka Message Ordering (순서)

![Kafka Message Ordering](./md_resource/KafkaMessageOrdering.PNG)

- `Partition` 이 2개 이상일 경우 메시지 순서는 보장은 못 합니다. 만약 `Partition` 이 1개 일 경우는 순서는 보장 받을 수 있겠지만 처리량이 저하 될 수 있습니다.
- `Partition` 2개 이상으로 순서 보장을 받고 싶으면 (유저 별로) 유저 식별자키 값을 이용해서 `Partition Key` 값을 만든 다음에 순서 보장 받아야 합니다.

### Consumer Failure

![ConsumerFailure](./md_resource/ConsumerFailure.PNG)

3개의 `Partition`이 있는 `Topic` 을 하나의 `Consumer Group` 내에 `consume` 하는 3 개의 `Consumer`가 존재 한다고 가정 하겠습니다. 각 `Consumer` 는 정확히 하나의 `Partition` 에서 `Record` 를 `consume` 하고 있습니다.

참고로 `Partition`은 항상 `Consumer Group` 내의 하나의 `Consumer` 에 의해서만 사용됩니다. 여기서 `Consumer 3` 번이 서버가 불능 상태가 빠지게 된다면 Kafka 내에서 `Rebalancing(리밸런시)` 한 다음에 `Consumer 2` 번이 대신 처리 하게 됩니다.

### Broker Replication 기능

![BrokerReplication1](./md_resource/BrokerReplication1.PNG)


`Producer` 와 `Consumer` 는 `Leader Partition` 만 통신 하게 됩니다. `Follower Partition` 는 복제만 역활 하게 됩니다.  즉 `Follower Partition` 는 `Broker` 장애시 안정성을 제공하기 위해서만 존재 합니다.

그리고 `Follower Partition` 는 `Leader Partition` 의 `Commit Log` 에서 데이터를 가져오기 `요청(Fetch Request)` 으로 복제 통해 동기화 하게 됩니다.


![BrokerReplication2](./md_resource/BrokerReplication2.PNG)

만약 기존 `Leader Partition` 장애 발생시 `Kafka Controller` 인해서 `Follower Partition` 이 `Leader Partition` 으로 승격 처리 됩니다. 이후 `Producer` 는 새로 선출 된 `Leader` 에게만 `Write` 하고 `Consumer` 또한 `Leader` 로부터만 `Read` 하게 됩니다.


### Producer Acks 전략 

Kafka Producer 의 `acks` 설정은 `message` 가 `Brober` 에 잘 전송되었는지 확인하는 방식으로 데이터의 안전성과 성능을 조절하는 데 사용됩니다.

#### Acks=0

![Producer Acks=0](./md_resource/ProducerAcks=0.PNG)

`Producer` 는 `Broker`의 응답을 기다리지 않으며 메시지 전송이 즉시 완료된 것으로 간주합니다. 데이터 손실 가능성이 가장 높지만 가장 높은 전송 속도를 제공합니다. (보통 이 방식은 사용 되지 않음)

#### Acks=1

![Producer Acks=1](./md_resource/ProducerAcks=1.PNG)

`Producer` 는 `Broker Leader Partition` 에게 메시지가 저장되었다는 응답을 기다리게 됩니다. `Broker Leader Partition`에 메시지가 저장되었으므로 데이터 손실 가능성이 낮지만, `Leader Partition`이 실패하면 데이터가 유실될 가능성이 있습니다.

#### Acks=-1 or all

![Producer Acks=-1 or all](./md_resource/ProducerAcks=-1ORall.PNG)

`Producer`는 `Broker Leader Partition` 과 모든 `Broker Follower Partition`가 저장되었다는 응답을 기다립니다. (모두 `Commit` 이 완료 되면) 데이터 손실 가능성이 가장 낮지만 가장 낮은 전송 속도를 제공합니다. 