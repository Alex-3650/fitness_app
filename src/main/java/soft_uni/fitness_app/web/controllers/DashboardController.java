package soft_uni.fitness_app.web.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.propertyeditors.CurrencyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import soft_uni.fitness_app.subscriptions.model.Subscription;
import soft_uni.fitness_app.subscriptions.model.SubscriptionStatus;
import soft_uni.fitness_app.subscriptions.service.SubscriptionService;
import soft_uni.fitness_app.trainingSession.model.TrainingSession;
import soft_uni.fitness_app.trainingSession.service.TrainingSessionService;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.service.UserService;
import soft_uni.fitness_app.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
public class DashboardController {

    private final UserService userService;
    private final TrainingSessionService trainingSessionService;
    private final SubscriptionService subscriptionService;

    public DashboardController(UserService userService, TrainingSessionService trainingSessionService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.trainingSessionService = trainingSessionService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/dashboard")
    public ModelAndView showDashboard(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("dashboard");

        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> user = userService.findById(userId);

       if (user.isEmpty()) {
           return new ModelAndView("redirect:/auth");
       }
        Subscription currentPlan = this.subscriptionService.findCurrentPlan(user.get(), SubscriptionStatus.ACTIVE).orElse(null);
        modelAndView.addObject("currentPlan", currentPlan);
        modelAndView.addObject("user", user.get());
        long userMonthlySessionsCount = this.trainingSessionService.findUserSessionsByCurrentMonth(userId);
        modelAndView.addObject("monthlySessions",userMonthlySessionsCount);
        return modelAndView;
    }
}
