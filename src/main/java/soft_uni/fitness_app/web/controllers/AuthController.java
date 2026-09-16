package soft_uni.fitness_app.web.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/login")
    public ModelAndView login(@Valid LoginRequest loginRequest, BindingResult bindingResult, HttpSession session) {


        if (bindingResult.hasErrors()) {
            return new ModelAndView("login");
        }
        Optional<User> user = this.userService.authenticateUser(loginRequest);
        if (user.isEmpty()) {
            ModelAndView mav = new ModelAndView("login");
            mav.addObject("errorMessage", "Wrong email or password");
            mav.addObject("loginRequest", new LoginRequest());
            mav.addObject("registerRequest", new RegisterRequest());
            return mav;
        }

        User autheticatedUser = user.get();
        session.setAttribute("userId",autheticatedUser.getId());

        return new ModelAndView("redirect:/dashboard");
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid RegisterRequest registerRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            return new ModelAndView("login");
        }

        this.userService.registerUser(registerRequest);
        redirectAttributes.addFlashAttribute("message", "You have successfully registered!");
        return new ModelAndView("redirect:/auth");
    }
}
