package soft_uni.fitness_app.exercise.model;

import jakarta.persistence.*;
import lombok.*;
import soft_uni.fitness_app.shared.BaseEntity;
import soft_uni.fitness_app.trainingSession.model.TrainingSession;

@Entity
@Table(name = "exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_session_id", nullable = false)
    private TrainingSession trainingSession;

    @Column(nullable = false, length = 100)
    private String name;

    @Column
    private Integer sets;

    @Column
    private Integer reps;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;
}
