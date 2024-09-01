package az.company.elibrary.security.config;

import az.company.elibrary.security.filter.AuthFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] PUBLIC_URLS = {"/error", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"};
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_SUPER_ADMIN = "SUPER_ADMIN";

    private final AuthFilter authFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> response
                                .sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage())))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request ->
                        request
                                /*
                                    Some methods are overridden with 'method security'
                                 */

                                // swagger ui
                                .requestMatchers(PUBLIC_URLS).permitAll()

                                //auth
                                .requestMatchers("/auth/logout").authenticated()
                                .requestMatchers("/auth/**").anonymous()

                                //users
                                .requestMatchers(HttpMethod.GET, "/users/{id}").authenticated()
                                .requestMatchers(HttpMethod.GET, "/users/email/{email}").hasAnyRole(ROLE_ADMIN, ROLE_SUPER_ADMIN)
                                .requestMatchers(HttpMethod.GET, "/users/search").authenticated()
                                .requestMatchers(HttpMethod.GET, "/users/role").hasAnyRole(ROLE_ADMIN, ROLE_SUPER_ADMIN)
                                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole(ROLE_SUPER_ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/users/{id}").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/users/change-password").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/users/assign-admin/{userId}").hasRole(ROLE_SUPER_ADMIN)
                                .requestMatchers(HttpMethod.PATCH, "/users/{userId}/deactivate").hasRole(ROLE_SUPER_ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/users/{Id}").authenticated()

                                //books
                                .requestMatchers(HttpMethod.GET, "/books/{id}/inventory").hasAnyRole(ROLE_ADMIN, ROLE_SUPER_ADMIN)
                                .requestMatchers(HttpMethod.GET, "/books/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/books/**").hasAnyRole(ROLE_ADMIN, ROLE_SUPER_ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/books/**").hasAnyRole(ROLE_ADMIN, ROLE_SUPER_ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/books/**").hasAnyRole(ROLE_ADMIN, ROLE_SUPER_ADMIN)

                                //authors
                                .requestMatchers(HttpMethod.GET, "/authors/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/authors/**").hasAnyAuthority(ROLE_ADMIN, ROLE_SUPER_ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/authors/**").hasAnyRole(ROLE_ADMIN, ROLE_SUPER_ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/authors/**").hasAnyRole(ROLE_ADMIN, ROLE_SUPER_ADMIN)

                                //ratings
                                .requestMatchers("/ratings/**").authenticated()

                                //reviews
                                .requestMatchers("/reviews/**").authenticated()

                                //reservations
                                .requestMatchers(HttpMethod.GET, "/reservations").hasAnyRole(ROLE_ADMIN, ROLE_SUPER_ADMIN)
                                .requestMatchers(HttpMethod.GET, "/reservations/expired").hasAnyRole(ROLE_ADMIN, ROLE_SUPER_ADMIN)
                                .requestMatchers(HttpMethod.PATCH, "/reservations/{reservationId}").hasAnyRole(ROLE_ADMIN, ROLE_SUPER_ADMIN)
                                .requestMatchers("/reservations/**").authenticated()

                                //borrowings
                                .requestMatchers(HttpMethod.GET, "/borrowings/me").authenticated()
                                .requestMatchers("/borrowings/**").hasAnyRole(ROLE_ADMIN, ROLE_SUPER_ADMIN)

                                .anyRequest().denyAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
