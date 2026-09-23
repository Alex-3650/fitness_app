package soft_uni.fitness_app.trainingSession.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum WorkoutType {
    WEIGHTLIFTING("Weightlifting"),
    HYROX("Hyrox"),
    CROSSFIT("Crossfit"),
    CARDIO("Cardio"),
    YOGA("Yoga");

    private  String displayName;

    // Constructor
    WorkoutType(String name) {
        this.displayName = name;
    }
}
