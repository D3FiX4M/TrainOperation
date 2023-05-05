package d3fix4m.ru.train_operation_test.service.impl;


import d3fix4m.ru.train_operation_test.domain.entity.auth.Role;
import d3fix4m.ru.train_operation_test.domain.entity.auth.User;
import d3fix4m.ru.train_operation_test.domain.repository.RoleRepository;
import d3fix4m.ru.train_operation_test.domain.repository.UserRepository;
import d3fix4m.ru.train_operation_test.exception.MyError;
import d3fix4m.ru.train_operation_test.exception.MyException;
import d3fix4m.ru.train_operation_test.payload.auth.AuthenticationRequest;
import d3fix4m.ru.train_operation_test.payload.auth.RegistrationRequest;
import d3fix4m.ru.train_operation_test.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private void userExist(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new MyException(MyError.USER_ALREADY_EXIST);
        }
    }

    @Override
    public void signIn(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Service - AuthService," +
                " Method - signIn," +
                " Status: Complete");
    }

    @Override
    public void signUp(RegistrationRequest request) {
        userExist(request.getUsername());

        Role role = roleRepository.findById(1L)
                .orElseThrow(
                        () -> new MyException(MyError.ROLE_NOT_FOUND_WITH_ID)
                );

        User user = new User(
                UUID.randomUUID(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                Set.of(role)
        );

        userRepository.save(user);

        log.info("Service - AuthService," +
                " Method - signUp," +
                " Status: Complete");

    }
}
