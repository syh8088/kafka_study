package kafka_study.producer.model.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertBoardReq {

    private String title;
    private String content;
}
