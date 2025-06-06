package kafka_study.producer.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UseYn {

    Y("Y"),
    N("N");

    private final String useYn;

    UseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getUseYn() {
        return this.useYn;
    }

    public static UseYn getByMemberType(String useYn) {
        return Arrays.stream(values())
                .filter(data -> data.getUseYn().equals(useYn))
                .findFirst()
                .orElse(UseYn.N);
    }
}
