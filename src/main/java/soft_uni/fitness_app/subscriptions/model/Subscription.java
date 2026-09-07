package soft_uni.fitness_app.subscriptions.model;

import jakarta.persistence.*;
import lombok.*;
import soft_uni.fitness_app.shared.BaseEntity;
import soft_uni.fitness_app.trainingSession.model.WorkoutType;
import soft_uni.fitness_app.user.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String planName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PlanDuration duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WorkoutType workoutType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SubscriptionStatus status;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;
}
