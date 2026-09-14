package soft_uni.fitness_app.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import soft_uni.fitness_app.web.dtos.LoginRequest;
import soft_uni.fitness_app.web.dtos.RegisterRequest;

@Controller
public class IndexController {




    @GetMapping("/auth")
    public ModelAndView showAuthPage() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("userRegister", new RegisterRequest());
        modelAndView.addObject("loginRequest", new LoginRequest());
        return modelAndView;
    }

    @GetMapping("/")
    public String showLandingPage() {

        return "index";
    }

}
