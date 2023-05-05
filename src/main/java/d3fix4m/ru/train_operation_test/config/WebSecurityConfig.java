package d3fix4m.ru.train_operation_test.config;

import d3fix4m.ru.train_operation_test.exception.MyError;
import d3fix4m.ru.train_operation_test.exception.MyException;
import d3fix4m.ru.train_operation_test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> {
            final String username = authentication.getPrincipal()
                    .toString();
            final String password = authentication.getCredentials()
                    .toString();

            final UserDetails userDetails = userService.loadUserByUsername(username);

            if (passwordEncoder().matches(
                    password,
                    userDetails.getPassword()
            )) {
                return authentication;
            }

            throw new MyException(MyError.BAD_USERNAME_OR_PASSWORD);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/auth/**")
                        .permitAll()
                        .requestMatchers("/v3/api-docs/**",
                                "/swagger-ui/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .httpBasic()
                .and()
                .build();
    }

}