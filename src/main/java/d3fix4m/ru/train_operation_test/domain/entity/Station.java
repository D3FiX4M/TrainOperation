package d3fix4m.ru.train_operation_test.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stations")
public class Station {
    // Номер станции
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Название станции
    @Column(nullable = false)
    private String name;

    // Пути станции
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "station")
    private List<StationPath> stationPaths = new ArrayList<>();

}
