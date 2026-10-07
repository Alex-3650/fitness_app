package soft_uni.fitness_app.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.fitness_app.transaction.model.Transaction;
import soft_uni.fitness_app.transaction.repository.TransactionRepository;
import soft_uni.fitness_app.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {


    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> findById(UUID id) {
        return transactionRepository.findById(id);
    }
    public List<Transaction> findUserTransactions(User user) {
        if (user==null) {
            throw new RuntimeException("User not found!");
        }
       return this.transactionRepository.findTop10ByUserOrderByTimestampDesc(user);
    }
}
