package kafka_study.common.outboxmessagerelay;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OutBoxStatus {

    INIT("INIT"),
    FAILURE("FAILURE"),
    SUCCESS("SUCCESS"),
    ;

    private final String status;

    OutBoxStatus(String status) {
        this.status = status;
    }

    public String getOutBoxStatus() {
        return this.status;
    }

    public static OutBoxStatus getByOutBoxStatus(String status) {
        return Arrays.stream(OutBoxStatus.values())
                .filter(data -> data.getOutBoxStatus().equals(status))
                .findFirst()
                .orElse(null);
    }

}