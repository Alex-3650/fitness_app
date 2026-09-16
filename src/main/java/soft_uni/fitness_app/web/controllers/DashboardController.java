package soft_uni.fitness_app.web.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.service.UserService;

import java.util.Optional;
import java.util.UUID;

@Controller
public class DashboardController {

    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public ModelAndView showDashboard( HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("dashboard");

        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> user = userService.findById(userId);

       if (user.isEmpty()) {
           return new ModelAndView("redirect:/auth");
       }

       modelAndView.addObject("user", user.get());
       return modelAndView;
    }
}
