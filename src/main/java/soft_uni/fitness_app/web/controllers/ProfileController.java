package soft_uni.fitness_app.web.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.service.UserService;

import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ModelAndView getProfile(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");
        User user = this.userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ModelAndView mav = new ModelAndView("profile");
        mav.addObject("user", user);
        return mav;

    }
    @GetMapping("/{id}")
    public ModelAndView getProfile(@PathVariable UUID id) {
        ModelAndView mav = new ModelAndView("profile");
        User user = this.userService.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/{id}/logout")
    public ModelAndView logOut(@PathVariable UUID id,HttpSession session) {
        ModelAndView mav = new ModelAndView("redirect:/");
        session.invalidate();
        return mav;
    }

}

