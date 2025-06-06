package kafka_study.common.event.domain;

import kafka_study.common.event.domain.PaymentEventMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentEventWithOrderOutPut {

    private long paymentEventNo;
    private long memberNo;
    private String orderId;
    private String paymentKey;
    private String orderName;
    private PaymentEventMethod method;
    private PaymentEventType type;
    private LocalDateTime approvedDateTime;
    private boolean isPaymentDone;

    private long paymentOrderNo;
    private long productNo;
    private long sellerNo;

    private BigDecimal amount;
    private PaymentOrderStatus status;
    private String productName;

}
