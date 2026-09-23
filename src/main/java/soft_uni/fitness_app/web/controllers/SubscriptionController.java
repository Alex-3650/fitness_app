package soft_uni.fitness_app.web.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import soft_uni.fitness_app.subscriptions.model.Subscription;
import soft_uni.fitness_app.subscriptions.model.SubscriptionStatus;
import soft_uni.fitness_app.subscriptions.service.SubscriptionService;
import soft_uni.fitness_app.transaction.model.Transaction;
import soft_uni.fitness_app.transaction.model.TransactionStatus;
import soft_uni.fitness_app.transaction.service.TransactionService;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.service.UserService;
import soft_uni.fitness_app.web.dtos.UpgradeRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final TransactionService transactionService;



    public SubscriptionController(UserService userService, SubscriptionService subscriptionService, TransactionService transactionService) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;

        this.transactionService = transactionService;
    }

    @GetMapping
    public ModelAndView showSubscriptions(HttpSession session) {
        ModelAndView mav = new ModelAndView("subscriptions");
        UUID userId = (UUID) session.getAttribute("userId");
        User user = this.userService.findById(userId).orElseThrow();
        Subscription currentPlan = this.subscriptionService.findCurrentPlan(user, SubscriptionStatus.ACTIVE).orElse(null);
        List<Subscription> pastSubscriptions = this.subscriptionService.getPastSubscriptions(user);
        mav.addObject("pastSubscriptions", pastSubscriptions);
        mav.addObject("user", user);
        mav.addObject("currentPlan", currentPlan);
        mav.addObject("upgradeRequest", new UpgradeRequest());
        return mav;
    }

    @PostMapping
    public ModelAndView subscribe(@Valid UpgradeRequest upgradeRequest,  BindingResult bindingResult,HttpSession session, @RequestParam(name = "subscriptionType") String subscriptionType) {

        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("subscriptions");
            mav.addObject("upgradeRequest", upgradeRequest);
            return mav;
        }
        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> user = this.userService.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found ");
        }


       Transaction subscriptionTransaction = this.subscriptionService.subscribe(user.get(), upgradeRequest, subscriptionType);

        return new ModelAndView("redirect:/subscriptions/result/" + subscriptionTransaction.getId());
    }

    @GetMapping("/result/{id}")
    public ModelAndView showResult(@PathVariable UUID id,HttpSession session) {
        ModelAndView mav = new ModelAndView("subscriptionCard");
        UUID userId = (UUID) session.getAttribute("userId");
        User user = userService.findById(userId).orElseThrow();
        Transaction transaction = this.transactionService.findById(id).orElseThrow();

        mav.addObject("user", user);
        mav.addObject("transaction", transaction);

        if (transaction.getStatus() == TransactionStatus.SUCCESSFUL){
            Subscription subscription = this.subscriptionService.findCurrentPlan(user, SubscriptionStatus.ACTIVE).orElseThrow();
            mav.addObject("subscription", subscription);

        }
        return mav;

    }

    @PatchMapping("/cancel/{id}")
    public String deleteMapping(@PathVariable UUID id) {
        this.subscriptionService.cancelSubscription(id);

        return "redirect:/subscriptions";
    }


}
