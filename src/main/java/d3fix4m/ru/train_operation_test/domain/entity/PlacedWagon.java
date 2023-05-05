package d3fix4m.ru.train_operation_test.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "placed_wagons")
public class PlacedWagon {

    // Номер вагона
    @Id
    @Column(name = "wagon_id")
    private Long id;


    // Вагон
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "wagon_id")
    private Wagon wagon;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_path_id")
    private StationPath stationPath;

    // Позиция вагона
    private Long position = 0L;

    // Груз вагона
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;


}
