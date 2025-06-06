package kafka_study.common.event.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PaymentOrderStatus {

    NOT_STARTED("NOT_STARTED", "결제 승인 시작 전"),
    EXECUTING("EXECUTING", "결제 승인 중"),
    SUCCESS("SUCCESS", "결제 승인 성공"),
    FAILURE("FAILURE", "결제 승인 실패"),
    UNKNOWN("UNKNOWN", "결제 승인 알 수 없는 상태"),
    ;

    private final String paymentOrderStatus;
    private final String name;

    PaymentOrderStatus(String paymentOrderStatus, String name) {
        this.paymentOrderStatus = paymentOrderStatus;
        this.name = name;
    }

    public String getPaymentOrderStatus() {
        return this.paymentOrderStatus;
    }

    public PaymentOrderStatus getByPaymentEventType(String paymentOrderStatus) {
        return Arrays.stream(PaymentOrderStatus.values())
                .filter(data -> data.getPaymentOrderStatus().equals(paymentOrderStatus))
                .findFirst()
                .orElse(null);
    }

    public boolean isNotStarted() {
        return this == PaymentOrderStatus.NOT_STARTED;
    }

    public boolean isExecuting() {
        return this == PaymentOrderStatus.EXECUTING;
    }

    public boolean isSuccess() {
        return this == PaymentOrderStatus.SUCCESS;
    }

    public boolean isFailure() {
        return this == PaymentOrderStatus.FAILURE;
    }

    public boolean isUnknown() {
        return this == PaymentOrderStatus.UNKNOWN;
    }
}