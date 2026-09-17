package soft_uni.fitness_app.trainingSession.service;

import org.springframework.stereotype.Service;
import soft_uni.fitness_app.trainingSession.model.TrainingSession;
import soft_uni.fitness_app.trainingSession.repository.TrainingSessionRepository;
import soft_uni.fitness_app.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;

    public TrainingSessionService(TrainingSessionRepository trainingSessionRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
    }

    public long findUserSessionsByCurrentMonth(UUID userId) {
        LocalDateTime startOfCurrentMonth = DateTimeUtils.startOfTheMonth(LocalDateTime.now());
        LocalDateTime startOfNextMonth = DateTimeUtils.startOfNextMonth(LocalDateTime.now());
        return this.trainingSessionRepository.findUserTrainingSessionsForMonth(userId, startOfCurrentMonth, startOfNextMonth);
    }

    public Optional<TrainingSession> findById(UUID id) {
       return this.trainingSessionRepository.findById(id);
    }
}
