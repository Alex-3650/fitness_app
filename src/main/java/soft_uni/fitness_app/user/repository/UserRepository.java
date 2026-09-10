package soft_uni.fitness_app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soft_uni.fitness_app.user.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
