package soft_uni.fitness_app.subscriptions.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft_uni.fitness_app.subscriptions.model.PlanDuration;
import soft_uni.fitness_app.subscriptions.model.Subscription;
import soft_uni.fitness_app.subscriptions.model.SubscriptionStatus;
import soft_uni.fitness_app.subscriptions.repository.SubscriptionRepository;
import soft_uni.fitness_app.trainingSession.model.WorkoutType;
import soft_uni.fitness_app.transaction.model.Transaction;
import soft_uni.fitness_app.transaction.model.TransactionStatus;
import soft_uni.fitness_app.transaction.model.TransactionType;
import soft_uni.fitness_app.transaction.service.TransactionService;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.service.UserService;
import soft_uni.fitness_app.web.dtos.UpgradeRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final TransactionService transactionService;
    private final UserService userService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, TransactionService transactionService, UserService userService) {
        this.subscriptionRepository = subscriptionRepository;
        this.transactionService = transactionService;
        this.userService = userService;
    }
    private final static String SUBSCRIPTION_SUCCESS_MESSAGE="Bought : \" %s \" subscription.";
    private final static String SUBSCRIPTION_FAILURE_MESSAGE="Not enough funds for \" %s \" subscription.";

    private static final Map<WorkoutType, BigDecimal> PLAN_PRICES = Map.of(
            WorkoutType.WEIGHTLIFTING, new BigDecimal("45.00"),
            WorkoutType.HYROX, new BigDecimal("60.00"),
            WorkoutType.CROSSFIT, new BigDecimal("55.00"),
            WorkoutType.CARDIO, new BigDecimal("35.00")
    );

    @Transactional
    public Transaction subscribe(User user, UpgradeRequest upgradeRequest, String subscriptionType) {

        if (user == null ) {
            throw new RuntimeException("User not found!");
        }

        Optional<Subscription> currentSubscriptionOpt = this.subscriptionRepository.findByUserAndStatus(user, SubscriptionStatus.ACTIVE);
        WorkoutType workoutType = WorkoutType.valueOf(subscriptionType.toUpperCase()); //HYROX,YOGA.....
        BigDecimal totalPrice = getMonthlyPrice(workoutType, upgradeRequest.getPlanDuration());

        boolean isActive = currentSubscriptionOpt.isPresent();
        boolean insufficientFunds = user.getWalletBalance().compareTo(totalPrice) < 0;

        if (isActive || insufficientFunds) {
            String note = isActive
                    ? "You already have an active subscription!"
                    : String.format(SUBSCRIPTION_FAILURE_MESSAGE, subscriptionType);

            Transaction failedTransaction = buildTransaction(user,totalPrice,TransactionStatus.FAILED, note);

            return this.transactionService.save(failedTransaction);

        }


        Transaction purchaseTransaction = buildTransaction(user,totalPrice,TransactionStatus.SUCCESSFUL,SUBSCRIPTION_SUCCESS_MESSAGE.formatted(subscriptionType));




         Subscription subscription = Subscription.builder()
                 .user(user)
                .price(totalPrice)
                .duration(upgradeRequest.getPlanDuration())
                .planName(subscriptionType + "plan")
                .status(SubscriptionStatus.ACTIVE)
                .workoutType(workoutType)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(getMonthsToAdd(upgradeRequest.getPlanDuration())))
                .build();

        this.subscriptionRepository.save(subscription);



        user.setWalletBalance(user.getWalletBalance().subtract(totalPrice));
        this.userService.saveUser(user);


        Transaction savedTransaction  = transactionService.save(purchaseTransaction);


        log.info("User [{}] subscribed to {} ({}) for {}€",
                user.getEmail(), subscriptionType, upgradeRequest.getPlanDuration(),totalPrice);

        return savedTransaction;
    }



    private BigDecimal getMonthlyPrice(WorkoutType workoutType, PlanDuration planDuration) {

        int monthsToAdd = getMonthsToAdd(planDuration);

        return PLAN_PRICES.get(workoutType).multiply(new BigDecimal(monthsToAdd));
    }

    private static int getMonthsToAdd(PlanDuration planDuration) {
        int monthsToAdd = switch (planDuration) {
            case ONE_MONTH -> 1;
            case THREE_MONTHS -> 3;
            case SIX_MONTHS -> 6;
            case TWELVE_MONTHS -> 12;
        };
        return monthsToAdd;
    }
    private Transaction buildTransaction(User user, BigDecimal amount, TransactionStatus status, String note) {
        return Transaction.builder()
                .type(TransactionType.SUBSCRIPTION_PURCHASE)
                .amount(amount)
                .timestamp(Instant.now())
                .status(status)
                .note(note)
                .user(user)
                .build();
    }

    public Optional<Subscription> findCurrentPlan(User user, SubscriptionStatus subscriptionStatus) {

     return   this.subscriptionRepository.findByUserAndStatus(user,subscriptionStatus);
    }
}
