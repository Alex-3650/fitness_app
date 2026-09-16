package soft_uni.fitness_app.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.fitness_app.transaction.model.Transaction;
import soft_uni.fitness_app.transaction.repository.TransactionRepository;

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
}
