package d3fix4m.ru.train_operation_test.controller;

import d3fix4m.ru.train_operation_test.domain.entity.EChooseSide;
import d3fix4m.ru.train_operation_test.payload.PlacedWagonsRequest;
import d3fix4m.ru.train_operation_test.payload.StationDTO;
import d3fix4m.ru.train_operation_test.payload.StationPathDTO;
import d3fix4m.ru.train_operation_test.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Station", description = "Станция")
@SecurityRequirement(name = "auth")
@RestController
@RequiredArgsConstructor
public class StationController {

    private final StationService service;

    private static final String STATION = "/station";
    private static final String STATION_ID = STATION + "/{id}";
    private static final String STATION_PATH = STATION_ID + "/station-path";
    private static final String STATION_PATH_ID = STATION_PATH + "/{pathId}";
    private static final String PLACED_WAGONS = STATION_PATH_ID + "/placed-wagons";

    @Operation(summary = "Получить станцию по ID")
    @GetMapping(STATION_ID)
    public StationDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @Operation(summary = "Получить список станций")
    @GetMapping(STATION)
    public List<StationDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Создать станцию")
    @PostMapping(STATION)
    public StationDTO create(@RequestBody StationDTO dto) {
        return service.create(dto);
    }

    @Operation(summary = "Обновить станцию")
    @PutMapping(STATION_ID)
    public StationDTO update(@PathVariable Long id,
                             @RequestBody StationDTO dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Удалить станцию")
    @DeleteMapping(STATION_ID)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(summary = "Получить путь станции по ID")
    @GetMapping(STATION_PATH_ID)
    public StationPathDTO getStationPathById(@PathVariable Long id,
                                             @PathVariable Long pathId) {
        return service.getStationPathById(id, pathId);
    }

    @Operation(summary = "Добавить путь станции")
    @PostMapping(STATION_PATH)
    public void addStationPath(@PathVariable Long id) {
        service.addStationPath(id);
    }

    @Operation(summary = "Удалить путь станции")
    @DeleteMapping(STATION_PATH_ID)
    public void deleteStationPath(@PathVariable Long id,
                                  @PathVariable Long pathId) {
        service.deleteStationPath(id, pathId);
    }

    @Operation(summary = "Добавить загруженные вагоны к пути станции")
    @PostMapping(PLACED_WAGONS)
    public void addPlacedWagons(@PathVariable Long id,
                                @PathVariable Long pathId,
                                @RequestBody PlacedWagonsRequest request) {
        service.addPlacedWagons(id, pathId, request);
    }

    @Operation(summary = "Удалить загруженные вагоны у пути станции")
    @DeleteMapping(PLACED_WAGONS)
    public void deletePlacedWagons(@PathVariable Long id,
                                   @PathVariable Long pathId,
                                   @RequestParam Long count) {
        service.deletePlacedWagons(id, pathId, count);
    }

    @Operation(summary = "Переместить загруженные вагоны к пути станции")
    @PutMapping(PLACED_WAGONS)
    public void permutationPlacedWagons(@PathVariable Long id,
                                        @PathVariable Long pathId,
                                        @RequestBody PlacedWagonsRequest wagons,
                                        @RequestParam EChooseSide side) {
        service.permutationPlacedWagons(id, pathId, wagons, side);
    }
}
