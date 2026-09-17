package soft_uni.fitness_app.utils;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtils {


    public static LocalDateTime startOfTheMonth( LocalDateTime dateTime) {

        return  dateTime.
                withDayOfMonth(1).
                toLocalDate()
                .atStartOfDay();
    }
    public static LocalDateTime startOfNextMonth(LocalDateTime dateTime) {
        return startOfTheMonth(dateTime).plusMonths(1);
    }
}
