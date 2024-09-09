package az.company.elibrary;

import az.company.elibrary.models.entity.User;
import az.company.elibrary.repository.UserRepository;
import az.company.elibrary.service.password.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static az.company.elibrary.models.enums.Role.ROLE_SUPER_ADMIN;

@Component
@RequiredArgsConstructor
public class SuperAdminInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Override
    public void run(ApplicationArguments args) {
        // init super admin
        User user = User.builder()
                .id(1L)
                .firstName("Name")
                .lastName("Family")
                .password(passwordService.encodePassword("12345"))
                .email("super-admin@e-library.az")
                .role(ROLE_SUPER_ADMIN)
                .enabled(true)
                .build();
        userRepository.save(user);
    }

}
