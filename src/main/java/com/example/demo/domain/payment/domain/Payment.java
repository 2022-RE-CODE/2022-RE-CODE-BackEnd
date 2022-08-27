package com.example.demo.domain.payment.domain;

import com.example.demo.domain.payment.type.PaymentMethod;
import com.example.demo.domain.payment.type.PaymentStatus;
import com.example.demo.domain.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Setter
@Table(name = "payments", indexes = @Index(name = "index_payments_order_id", columnList = "orderId"))
@ToString
public class Payment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "buyer_id")
    @ManyToOne
    private User buyer; // 구매자

    @Column(nullable = false, unique = true)
    private String receiptId; // PG 사에서 생성한 주문 번호

    @Column(nullable = false, unique = true)
    private String orderId; // 우리가 생성한 주문 번호

    @Enumerated(EnumType.STRING)
    private PaymentMethod method; // 결제 수단

    private String name; // 결제 이름

    @Column(nullable = false)
    private BigDecimal amount; // 결제 금액

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.READY; // 상태

    @CreatedDate
    private LocalDateTime createAt; // 결제 요청 일시

    private LocalDateTime paidAt; // 결제 완료 일시

    private LocalDateTime failedAt; // 결제 실패 일시

    @Builder.Default
    private BigDecimal cancelledAmount = BigDecimal.ZERO; // 취소된 금액

    private LocalDateTime cancelledAt; // 결제 취소 일시

    @Builder
    public Payment(Long id, User buyer, String receiptId, String orderId, PaymentMethod method, String name, BigDecimal amount, PaymentStatus status, LocalDateTime createAt, LocalDateTime paidAt, LocalDateTime failedAt, BigDecimal cancelledAmount, LocalDateTime cancelledAt) {
        this.id = id;
        this.buyer = buyer;
        this.receiptId = receiptId;
        this.orderId = orderId;
        this.method = method;
        this.name = name;
        this.amount = amount;
        this.status = status;
        this.createAt = createAt;
        this.paidAt = paidAt;
        this.failedAt = failedAt;
        this.cancelledAmount = cancelledAmount;
        this.cancelledAt = cancelledAt;
    }
}