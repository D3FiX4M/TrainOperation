package d3fix4m.ru.train_operation_test.controller;

import d3fix4m.ru.train_operation_test.payload.CargoDTO;
import d3fix4m.ru.train_operation_test.service.CargoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Cargo", description = "Груз")
@SecurityRequirement(name = "auth")
@RestController
@RequiredArgsConstructor
public class CargoController {
    private final CargoService service;

    private static final String ROOT  = "/cargo";
    private static final String ID = ROOT+"/{id}";

    @Operation(summary = "Получить груз по ID")
    @GetMapping(ID)
    public CargoDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @Operation(summary = "Получить список груза")
    @GetMapping(ROOT)
    public List<CargoDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Создать груз")
    @PostMapping(ROOT)
    public CargoDTO create(@RequestBody CargoDTO dto) {
        return service.create(dto);
    }

    @Operation(summary = "Обновить груз")
    @PutMapping(ID)
    public CargoDTO update(@PathVariable Long id,
                           @RequestBody CargoDTO dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Удалить груз")
    @DeleteMapping(ID)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
