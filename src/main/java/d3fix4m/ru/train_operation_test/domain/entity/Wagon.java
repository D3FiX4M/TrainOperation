package d3fix4m.ru.train_operation_test.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wagons")
public class Wagon {
    // Номер вагона
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Вес вагона
    @Column(nullable = false)
    private Long weight;

    //Грузоподъемность вагона
    @Column(nullable = false)
    private Long capacity;
}
