package soft_uni.fitness_app.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import soft_uni.fitness_app.booking.model.Booking;
import soft_uni.fitness_app.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {


    List<Booking> findAllByUser(User user);

    @Query("SELECT b FROM Booking b WHERE b.user = :user AND b.trainingSession.scheduledAt >= :now " +
            "ORDER BY b.trainingSession.scheduledAt ASC")
    List<Booking> findUpcoming(@Param("user") User user, @Param("now") LocalDateTime now);


    @Query("SELECT b FROM Booking b WHERE b.user = :user AND b.trainingSession.scheduledAt < :now " +
            "ORDER BY b.trainingSession.scheduledAt DESC")
    List<Booking> findPast(@Param("user") User user, @Param("now") LocalDateTime now);
}
