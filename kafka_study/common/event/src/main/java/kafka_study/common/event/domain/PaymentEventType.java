package kafka_study.common.event.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PaymentEventType {

    NORMAL("NORMAL")
    ;

    private final String paymentEventType;

    PaymentEventType(String paymentEventType) {
        this.paymentEventType = paymentEventType;
    }

    public String getPaymentEventType() {
        return this.paymentEventType;
    }

    public static PaymentEventType getByPaymentEventType(String paymentEventType) {
        return Arrays.stream(PaymentEventType.values())
                .filter(data -> data.getPaymentEventType().equals(paymentEventType))
                .findFirst()
                .orElse(null);
    }

    public boolean isNormal() {
        return this == PaymentEventType.NORMAL;
    }
}