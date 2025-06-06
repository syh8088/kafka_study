package kafka_study.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "kafka_study")
@EnableJpaRepositories(basePackages = "kafka_study")
@EnableJpaAuditing
@SpringBootApplication
public class KafkaStudyProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStudyProducerApplication.class, args);
    }

}
