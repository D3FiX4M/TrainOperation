package d3fix4m.ru.train_operation_test.service.impl;

import d3fix4m.ru.train_operation_test.domain.entity.auth.Role;
import d3fix4m.ru.train_operation_test.domain.entity.auth.User;
import d3fix4m.ru.train_operation_test.domain.repository.RoleRepository;
import d3fix4m.ru.train_operation_test.domain.repository.UserRepository;
import d3fix4m.ru.train_operation_test.exception.MyError;
import d3fix4m.ru.train_operation_test.exception.MyException;
import d3fix4m.ru.train_operation_test.payload.auth.AuthenticationRequest;
import d3fix4m.ru.train_operation_test.payload.auth.RegistrationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void SignInShouldSuccessful() {

        AuthenticationRequest request = new AuthenticationRequest(
                "test_username",
                "test_password"
        );

        Authentication authentication = mock(Authentication.class);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword());

        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authentication);

        authService.signIn(request);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void SignInShouldThrowExceptionBAD_USERNAME_OR_PASSWORD() {

        AuthenticationRequest request = new AuthenticationRequest(
                "test_username",
                "test_password"
        );

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword());

        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenThrow(new MyException(MyError.BAD_USERNAME_OR_PASSWORD));

        MyException exception = assertThrows(MyException.class,
                () -> authService.signIn(request));

        Assertions.assertEquals(MyError.BAD_USERNAME_OR_PASSWORD, exception.getError());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void SignUPShouldSuccessful() {

        RegistrationRequest request = new RegistrationRequest(
                "test_username",
                "test_password"
        );

        Role role = mock(Role.class);
        User user = mock(User.class);

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        authService.signUp(request);

        verify(userRepository, times(1)).existsByUsername(request.getUsername());
        verify(roleRepository, times(1)).findById(1L);
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, times(1)).save(argThat(u -> u.getUsername().equals(request.getUsername())
                && u.getPassword().equals("encoded_password")
                && u.getRoles().contains(role)));
    }


    @Test
    public void SignUPShouldThrowExceptionUSER_ALREADY_EXIST() {

        RegistrationRequest request = new RegistrationRequest(
                "test_username",
                "test_password"
        );

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        MyException exception = assertThrows(MyException.class,
                () -> authService.signUp(request));

        Assertions.assertEquals(MyError.USER_ALREADY_EXIST, exception.getError());

        verify(userRepository, times(1)).existsByUsername(request.getUsername());
    }

    @Test
    public void SignUPShouldThrowExceptionROLE_NOT_FOUND_WITH_ID() {

        RegistrationRequest request = new RegistrationRequest(
                "test_username",
                "test_password"
        );

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(roleRepository.findById(1L)).thenThrow(new MyException(MyError.ROLE_NOT_FOUND_WITH_ID));

        MyException exception = assertThrows(MyException.class,
                () -> authService.signUp(request));

        Assertions.assertEquals(MyError.ROLE_NOT_FOUND_WITH_ID, exception.getError());

        verify(userRepository, times(1)).existsByUsername(request.getUsername());
        verify(roleRepository, times(1)).findById(1L);
    }
}