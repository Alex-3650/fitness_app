package soft_uni.fitness_app.subscriptions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soft_uni.fitness_app.subscriptions.model.Subscription;
import soft_uni.fitness_app.subscriptions.model.SubscriptionStatus;
import soft_uni.fitness_app.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {


   Optional<Subscription> findByUserAndStatus(User user, SubscriptionStatus status);

    List<Subscription> findByUserAndStatusNot(User user, SubscriptionStatus status);
}
