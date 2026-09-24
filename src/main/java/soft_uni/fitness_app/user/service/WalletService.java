package soft_uni.fitness_app.user.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft_uni.fitness_app.transaction.model.Transaction;
import soft_uni.fitness_app.transaction.model.TransactionStatus;
import soft_uni.fitness_app.transaction.model.TransactionType;
import soft_uni.fitness_app.transaction.service.TransactionService;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.repository.UserRepository;
import soft_uni.fitness_app.web.dtos.TopUpBalanceDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class WalletService {

    private final UserRepository userRepository;
    private final TransactionService transactionService;
    private final String TOP_UP_BALANCE_MESSAGE = "Top up %.2f €";
    @Autowired
    public WalletService(UserRepository userRepository, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.transactionService = transactionService;
    }


    public BigDecimal spentMoneyOnTransactionType(User user, TransactionType transactionType) {

        BigDecimal amount = this.userRepository.sumAmountByUserAndType(user, transactionType);

        return amount;

    }

   @Transactional
    public Transaction topUpBalance(@Valid TopUpBalanceDto topUpBalanceDto, User user,TransactionStatus status) {

        user.setWalletBalance(user.getWalletBalance().add(topUpBalanceDto.getTopUpBalance()));
        this.userRepository.save(user);

        Transaction topUpTransaction = Transaction.builder()
                .user(user)
                .type(TransactionType.TOP_UP)
                .amount(topUpBalanceDto.getTopUpBalance())
                .status(status)
                .timestamp(Instant.now())
                .note(TOP_UP_BALANCE_MESSAGE.formatted(topUpBalanceDto.getTopUpBalance()))
                .build();
        Transaction transaction = this.transactionService.save(topUpTransaction);

        return transaction;
    }
}
