package soft_uni.fitness_app.web.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.service.UserService;
import soft_uni.fitness_app.web.dtos.LoginRequest;
import soft_uni.fitness_app.web.dtos.RegisterRequest;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

@Controller
public class IndexController {


private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String showLandingPage() {

        return "index";
    }

    @GetMapping("/auth")
    public ModelAndView showAuthPage() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("registerRequest", new RegisterRequest());
        modelAndView.addObject("loginRequest", new LoginRequest());
        return modelAndView;
    }





}
