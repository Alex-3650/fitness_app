package soft_uni.fitness_app.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.repository.UserRepository;
import soft_uni.fitness_app.web.dtos.RegisterRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    private User registerUser(RegisterRequest registerRequest) {


        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .role(registerRequest.getRole())
                .walletBalance(new BigDecimal("20"))
                 .build();

             this.userRepository.save(user);

        log.info(
                "New user profile was registered in the system for user [{}]",
                user.getFirstName() + " " + user.getLastName()
        );


    }
}
