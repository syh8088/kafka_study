package kafka_study.common.outboxmessagerelay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableAsync
@Configuration
@ComponentScan("kafka_study.common.outboxmessagerelay")
@EnableScheduling
public class MessageRelayConfig {

    /**
     * 트랜잭션이 끝날 때마다 이벤트 전송을 비동기로 전송하기 위해서 처리하기 위한 스레드풀 설정
     */
    @Bean
    public Executor messageRelayPublishEventExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("mr-pub-event-");
        return executor;
    }

    /**
     * 이벤트 전송이 아직 되지 않는 것들 10초 간격으로 주기적으로 보낸다고 했을때 스레드 풀 설정
     */
    @Bean
    public Executor messageRelayPublishPendingEventExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}
