package d3fix4m.ru.train_operation_test.domain.repository;

import d3fix4m.ru.train_operation_test.domain.entity.Cargo;
import d3fix4m.ru.train_operation_test.domain.entity.PlacedWagon;
import d3fix4m.ru.train_operation_test.domain.entity.Wagon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlacedWagonRepository extends JpaRepository<PlacedWagon, Long> {

    List<PlacedWagon> findAllByCargo(Cargo cargo);
    boolean existsByCargo(Cargo cargo);
}
