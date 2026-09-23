package soft_uni.fitness_app.subscriptions.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum SubscriptionStatus {
    ACTIVE("Active"),
    EXPIRED("Expired"),
    CANCELLED("Cancelled"),;

    private String displayName;

    SubscriptionStatus(String displayName) {
        this.displayName = displayName;
    }
}
