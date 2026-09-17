package soft_uni.fitness_app.trainingSession.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import soft_uni.fitness_app.trainingSession.model.TrainingSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, UUID> {

    @Query("""
        SELECT COUNT(ts)
        FROM TrainingSession ts
        JOIN Booking b ON b.trainingSession = ts
        WHERE b.user.id = :userId
        AND ts.scheduledAt >= :startOfMonth
        AND ts.scheduledAt < :startOfNextMonth
        """)
    long  findUserTrainingSessionsForMonth(
            @Param("userId") UUID userId,
            @Param("startOfMonth") LocalDateTime startOfMonth,
            @Param("startOfNextMonth") LocalDateTime startOfNextMonth
    );
}
