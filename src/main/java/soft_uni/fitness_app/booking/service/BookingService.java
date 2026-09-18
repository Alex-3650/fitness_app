package soft_uni.fitness_app.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.fitness_app.booking.model.Booking;
import soft_uni.fitness_app.booking.repository.BookingRepository;
import soft_uni.fitness_app.user.model.User;
import soft_uni.fitness_app.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
    }

    public List<Booking> findAllByUser(User user) {

        List<Booking> allByUser = this.bookingRepository.findAllByUser(user);

        return allByUser;
    }

    public List<Booking> getUpcomingSessions(User user ) {
       return this.bookingRepository.findUpcoming(user, LocalDateTime.now());
    }
    public List<Booking> getPastSessions(User user ) {
        return this.bookingRepository.findPast(user,LocalDateTime.now());
    }
}
