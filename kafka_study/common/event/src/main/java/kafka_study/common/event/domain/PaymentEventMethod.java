package kafka_study.common.event.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PaymentEventMethod {

    CARD("CARD"),
    ;

    private final String paymentEventMethod;

    PaymentEventMethod(String paymentEventMethod) {
        this.paymentEventMethod = paymentEventMethod;
    }

    public String getPaymentEventMethod() {
        return this.paymentEventMethod;
    }

    public static PaymentEventMethod getByPaymentEventMethod(String paymentEventMethod) {
        return Arrays.stream(PaymentEventMethod.values())
                .filter(data -> data.getPaymentEventMethod().equals(paymentEventMethod))
                .findFirst()
                .orElse(null);
    }

    public boolean isCard() {
        return this == PaymentEventMethod.CARD;
    }
}