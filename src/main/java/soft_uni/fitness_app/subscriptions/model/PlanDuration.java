package soft_uni.fitness_app.subscriptions.model;

import lombok.Getter;

@Getter
public enum PlanDuration {
    ONE_MONTH("1 Month"),
    THREE_MONTHS("3 Month"),
    SIX_MONTHS("6 Month"),
    TWELVE_MONTHS("12 Month");

    private String displayName;

    PlanDuration(String displayName) {
        this.displayName = displayName;
    }
}
