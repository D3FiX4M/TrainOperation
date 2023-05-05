package d3fix4m.ru.train_operation_test.controller;

import d3fix4m.ru.train_operation_test.payload.WagonDTO;
import d3fix4m.ru.train_operation_test.service.WagonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Tag(name = "Wagon", description = "Вагон")
@SecurityRequirement(name = "auth")
@RestController
@RequiredArgsConstructor
public class WagonController {

    private final WagonService service;

    private static final String ROOT  = "/wagon";
    private static final String ID = ROOT+"/{id}";

    @Operation(summary = "Получить вагон по ID")
    @GetMapping(ID)
    public WagonDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @Operation(summary = "Получить список вагонов")
    @GetMapping(ROOT)
    public List<WagonDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Создать вагон")
    @PostMapping(ROOT)
    public WagonDTO create(@RequestBody WagonDTO dto) {
        return service.create(dto);
    }

    @Operation(summary = "Обновить вагон")
    @PutMapping(ID)
    public WagonDTO update(@PathVariable Long id,
                           @RequestBody WagonDTO dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Удалить вагон")
    @DeleteMapping(ID)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}


