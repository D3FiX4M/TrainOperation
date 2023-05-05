package d3fix4m.ru.train_operation_test.controller;

import d3fix4m.ru.train_operation_test.payload.auth.AuthenticationRequest;
import d3fix4m.ru.train_operation_test.payload.auth.RegistrationRequest;
import d3fix4m.ru.train_operation_test.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Аутентификация")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    private final static String SIGN_IN = "/auth/sign-in";
    private final static String SIGN_UP = "/auth/sign-up";

    @Operation(summary = "Аутентификация пользователя")
    @PostMapping(SIGN_IN)
    public void signIn(@RequestBody AuthenticationRequest request) {
        service.signIn(request);
    }

    @Operation(summary = "Регистрация пользователя")
    @PostMapping(SIGN_UP)
    public void signUp(@RequestBody RegistrationRequest request) {
        service.signUp(request);
    }
}
