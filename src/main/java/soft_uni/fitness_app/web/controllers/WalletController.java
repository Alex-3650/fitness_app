package soft_uni.fitness_app.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    @GetMapping
    public ModelAndView showWallet() {
        ModelAndView mav = new ModelAndView("wallet");
        return mav;
    }
}
