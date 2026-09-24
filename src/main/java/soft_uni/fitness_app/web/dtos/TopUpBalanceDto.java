package soft_uni.fitness_app.web.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopUpBalanceDto {

    @NotNull(message = "Top up amount should not be null!")
    @DecimalMin(value = "5.00", message = "Minimum top-up amount is 5€")
    @DecimalMax(value = "500.00", message = "Maximum top-up amount is 500€")
    private BigDecimal topUpBalance;
}
