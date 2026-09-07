package soft_uni.fitness_app.trainingSession.model;

import jakarta.persistence.*;
import lombok.*;
import soft_uni.fitness_app.coach.model.Coach;
import soft_uni.fitness_app.shared.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "trainingSessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingSession extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id", nullable = false)
    private Coach coach;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WorkoutType workoutType;

    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    @Column(nullable = false)
    private Integer capacity;
}
