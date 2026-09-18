package soft_uni.fitness_app.web.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController {


    @GetMapping()
    public ModelAndView getProfile(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");
        ModelAndView mav = new ModelAndView("redirect:/" + userId + "/profile");
        return mav;

    }
}
