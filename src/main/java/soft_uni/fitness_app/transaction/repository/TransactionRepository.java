package soft_uni.fitness_app.transaction.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import soft_uni.fitness_app.transaction.model.Transaction;
import soft_uni.fitness_app.user.model.User;

import java.util.List;
import java.util.UUID;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {





    List<Transaction> findTop10ByUserOrderByTimestampDesc(User user);
}
