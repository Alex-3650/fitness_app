package soft_uni.fitness_app.web.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.service.UserService;
import soft_uni.fitness_app.web.dtos.ChangePasswordRequest;
import soft_uni.fitness_app.web.dtos.ProfileUpdateDto;

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
        mav.addObject("profileUpdateDto", new ProfileUpdateDto(user.getFirstName(), user.getLastName(), user.getEmail()));
        mav.addObject("changePasswordRequest", new ChangePasswordRequest());
        return mav;
    }

    @PostMapping("/{id}/logout")
    public ModelAndView logOut(@PathVariable UUID id,HttpSession session) {
        ModelAndView mav = new ModelAndView("redirect:/");
        session.invalidate();
        return mav;
    }

    @PutMapping("/{id}")
    public ModelAndView updateProfile(@Valid ProfileUpdateDto profileUpdateDto, BindingResult bindingResult,HttpSession session,@PathVariable UUID id) {

        User user = authenticateUser(session, id);
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("profile");
            mav.addObject("changePasswordRequest", new ChangePasswordRequest());
            mav.addObject("profileUpdateDto", profileUpdateDto);
            mav.addObject("user", user);
            return mav;

        }
        User updatedUser = this.userService.updateUserData(profileUpdateDto, user);

        ModelAndView mav = new ModelAndView("userDataChangeCard");
        mav.addObject("updatedUser", updatedUser);
        return mav;

    }


    // <form th:action="@{/profile/{id}(id=${user.id})/password}" th:method="PATCH">

    @PatchMapping("/{id}/password")
    public ModelAndView updatePassword(@Valid ChangePasswordRequest changePasswordRequest, BindingResult bindingResult,HttpSession session,@PathVariable UUID id) {
        User user = authenticateUser(session, id);
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("profile");
            mav.addObject("changePasswordRequest", changePasswordRequest);
            mav.addObject("user", user);
            mav.addObject("profileUpdateDto", new ProfileUpdateDto(user.getFirstName(), user.getLastName(), user.getEmail()));
            return mav;
        }
        User updatedUser = this.userService.changePassword(user, changePasswordRequest);
        ModelAndView mav = new ModelAndView("passwordUpdateCard");
        mav.addObject("user", updatedUser);
        return mav;


    }


    private User authenticateUser(HttpSession session, UUID id) {
        User user = this.userService.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        UUID sessionUserId = (UUID) session.getAttribute("userId");
        if (!id.equals(sessionUserId)) {
            throw new RuntimeException("Cannot edit another user's profile");
        }
        return user;
    }

}

