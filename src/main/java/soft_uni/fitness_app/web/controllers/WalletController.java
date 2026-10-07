package soft_uni.fitness_app.web.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import soft_uni.fitness_app.transaction.model.Transaction;
import soft_uni.fitness_app.transaction.model.TransactionStatus;
import soft_uni.fitness_app.transaction.model.TransactionType;
import soft_uni.fitness_app.transaction.service.TransactionService;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.service.UserService;
import soft_uni.fitness_app.user.service.WalletService;
import soft_uni.fitness_app.web.dtos.TopUpBalanceDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/wallet")
public class WalletController {
    private final UserService userService;
    private final WalletService walletService;
    private final TransactionService transactionService;



    public WalletController(UserService userService, WalletService walletService, TransactionService transactionService) {
        this.userService = userService;
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public ModelAndView showWallet(HttpSession session) {
        ModelAndView mav = new ModelAndView("wallet");

        UUID userId = (UUID) session.getAttribute("userId");
        User user = this.userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        BigDecimal topUpMoney = this.walletService.spentMoneyOnTransactionType(user, TransactionType.TOP_UP);
        BigDecimal subscriptionMoney = this.walletService.spentMoneyOnTransactionType(user, TransactionType.SUBSCRIPTION_PURCHASE);
        BigDecimal registerBonusMoney = this.walletService.spentMoneyOnTransactionType(user, TransactionType.SIGNUP_BONUS);
        List<Transaction> userTransactions = this.transactionService.findUserTransactions(user);


        mav.addObject("userTransactions", userTransactions);
        mav.addObject("topUpMoney", topUpMoney);
        mav.addObject("subscriptionMoney", subscriptionMoney);
        mav.addObject("registerBonusMoney", registerBonusMoney);
        mav.addObject("user", user);
        mav.addObject("topUpDto",new TopUpBalanceDto());
        return mav;
    }

    @PostMapping("/topUp")
    public ModelAndView topUpWallet(@Valid TopUpBalanceDto topUpBalanceDto, BindingResult bindingResult, HttpSession session ) {
         ModelAndView mav = new ModelAndView("topUpCard");
         UUID userId = (UUID) session.getAttribute("userId");
         User user = this.userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
         if (bindingResult.hasErrors()) {
          ModelAndView errorMav =new ModelAndView("topUpCard");
          errorMav.addObject("topUpBalanceDto", topUpBalanceDto);
          errorMav.addObject("isFailed", "YES");
          return errorMav;
         }
         mav.addObject("isFailed", "NO");
         mav.addObject("topUpBalanceDto", topUpBalanceDto);
        Transaction transaction = this.walletService.topUpBalance(topUpBalanceDto, user, TransactionStatus.SUCCESSFUL);

       return mav;


    }
}
