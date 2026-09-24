package soft_uni.fitness_app.user.service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft_uni.fitness_app.subscriptions.model.Subscription;
import soft_uni.fitness_app.transaction.model.Transaction;
import soft_uni.fitness_app.transaction.model.TransactionStatus;
import soft_uni.fitness_app.transaction.model.TransactionType;
import soft_uni.fitness_app.transaction.service.TransactionService;
import soft_uni.fitness_app.user.model.Role;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.repository.UserRepository;
import soft_uni.fitness_app.web.dtos.LoginRequest;
import soft_uni.fitness_app.web.dtos.RegisterRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService {
    private static final BigDecimal SIGNUP_BONUS = new BigDecimal("100");

    private final UserRepository userRepository;
    private final TransactionService transactionService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TransactionService transactionService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.transactionService = transactionService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(RegisterRequest registerRequest) {

        Optional<User> authUser = this.userRepository
                .findUserByEmail((registerRequest.getEmail()));

        if (authUser.isPresent()) {
            throw new RuntimeException("User with this email already exists!");
        }



        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .walletBalance(SIGNUP_BONUS)
                .role(Role.USER)
                .build();

             this.userRepository.save(user);

        Transaction bonus = Transaction.builder()
                .user(user)
                .note("Register bonus")
                .status(TransactionStatus.SUCCESSFUL)
                .timestamp(Instant.now())
                .amount(SIGNUP_BONUS)
                .type(TransactionType.SIGNUP_BONUS)
                .build();


        this.transactionService.save(bonus);




        log.info(
                "New user profile was registered in the system for user [{}]",
                user.getFirstName() + " " + user.getLastName()
        );
    return user;
    }

    public User saveUser(User user){
        return this.userRepository.save(user);
    }


    public Optional<User> authenticateUser(@Valid LoginRequest loginRequest) {

        Optional<User> userByEmail = this.userRepository.findUserByEmail(loginRequest.getEmail());

        if (userByEmail.isEmpty()) {
          return Optional.empty();
        }

        User user = userByEmail.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public Optional<User> findById(UUID userId) {

        Optional<User> user = this.userRepository.findById(userId);

        return user;
    }

}
