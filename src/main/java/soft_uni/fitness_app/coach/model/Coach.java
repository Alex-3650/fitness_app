package soft_uni.fitness_app.coach.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import soft_uni.fitness_app.shared.BaseEntity;

@Entity
@Table(name = "coaches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coach extends BaseEntity {


    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 150)
    private String specialty;

    @Column(nullable = false, length = 150)
    private String location;

    @Column(length = 1000)
    private String bio;
}
