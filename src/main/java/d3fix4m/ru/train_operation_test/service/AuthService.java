package d3fix4m.ru.train_operation_test.service;

import d3fix4m.ru.train_operation_test.payload.auth.AuthenticationRequest;
import d3fix4m.ru.train_operation_test.payload.auth.RegistrationRequest;

public interface AuthService {

    void signIn(AuthenticationRequest request);
    void signUp(RegistrationRequest request);
}
