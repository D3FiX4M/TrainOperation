package d3fix4m.ru.train_operation_test.domain.repository;

import d3fix4m.ru.train_operation_test.domain.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StationRepository extends JpaRepository<Station, Long> {
}
