package d3fix4m.ru.train_operation_test.controller;

import d3fix4m.ru.train_operation_test.payload.PlacedWagonDTO;
import d3fix4m.ru.train_operation_test.payload.PlacedWagonsFullInfo;
import d3fix4m.ru.train_operation_test.service.PlacedWagonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "PlacedWagon", description = "Загруженные/Используемые вагоны")
@SecurityRequirement(name = "auth")
@RestController
@RequiredArgsConstructor
public class PlacedWagonController {
    private final PlacedWagonService service;

    private static final String ROOT = "/placed-wagon";
    private static final String ID = ROOT + "/{id}";
    private static final String FULL_INFO = ROOT + "/full-info";

    @Operation(summary = "Получить загруженный вагон по ID")
    @GetMapping(ID)
    public PlacedWagonDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @Operation(summary = "Получить список загруженных вагонов")
    @GetMapping(ROOT)
    public List<PlacedWagonDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Создать загруженный вагон")
    @PostMapping(ROOT)
    public PlacedWagonDTO create(@RequestBody PlacedWagonDTO dto) {
        return service.create(dto);
    }

    @Operation(summary = "Обновить загруженный вагон")
    @PutMapping(ID)
    public PlacedWagonDTO update(@PathVariable Long id,
                                 @RequestBody PlacedWagonDTO dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Удалить загруженный вагон")
    @DeleteMapping(ID)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(summary = "Получить список полной информации о загруженных вагонах")
    @GetMapping(FULL_INFO)
    public List<PlacedWagonsFullInfo> getFullInfo() {
        return service.getFullInfo();
    }
}
