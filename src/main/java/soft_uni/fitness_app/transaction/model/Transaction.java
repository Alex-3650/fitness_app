package soft_uni.fitness_app.transaction.model;

import jakarta.persistence.*;
import lombok.*;
import soft_uni.fitness_app.shared.BaseEntity;
import soft_uni.fitness_app.user.model.User;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TransactionType type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(length = 255)
    private String note;
}
