package soft_uni.fitness_app.web.dtos;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import soft_uni.fitness_app.subscriptions.model.PlanDuration;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpgradeRequest {

    @NotNull
    private PlanDuration planDuration;



}
