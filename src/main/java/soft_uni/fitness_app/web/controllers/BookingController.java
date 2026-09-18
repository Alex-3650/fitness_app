package soft_uni.fitness_app.web.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import soft_uni.fitness_app.booking.model.Booking;
import soft_uni.fitness_app.booking.service.BookingService;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.service.UserService;

import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final UserService userService;
    private final BookingService bookingService;

    public BookingController(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public String showBookings() {
        return "redirect:/bookings/upcoming";

    }

    @GetMapping("/upcoming")
    public ModelAndView showUpcomingSessions(HttpSession session) {
        return buildBookingsView(session, "upcoming");
    }

    @GetMapping("/past")
    public ModelAndView showPastSessions(HttpSession session) {
        return buildBookingsView(session, "past");
    }

    private ModelAndView buildBookingsView(HttpSession session, String tab) {
        ModelAndView modelAndView = new ModelAndView("booking");
        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {

           return new ModelAndView("redirect:/login");
        }

        List<Booking> booking = tab.equals("upcoming")
                                ? bookingService.getUpcomingSessions(user.get())
                                : bookingService.getPastSessions(user.get());

        modelAndView.addObject("bookings", booking);
        modelAndView.addObject("activeTab", tab);
        return modelAndView;
    }


}



//ModelAndView mav = new ModelAndView("booking");
//UUID userId = (UUID) session.getAttribute("userId");
//Optional<User> userOpt = this.userService.findById(userId);
//        if (userOpt.isEmpty()) {
//        redirectAttributes.addFlashAttribute("message", "User not found!");
//            return new ModelAndView("redirect:/login");
//        }
//List<Booking> bookings = this.bookingService.findAllByUser(userOpt.get());
//
//        mav.addObject("bookings",bookings);
//
//       return mav;