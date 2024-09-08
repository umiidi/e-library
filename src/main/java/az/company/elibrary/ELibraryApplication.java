package az.company.elibrary;

import az.company.elibrary.models.entity.User;
import az.company.elibrary.repository.UserRepository;
import az.company.elibrary.service.password.PasswordService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static az.company.elibrary.models.enums.Role.ROLE_SUPER_ADMIN;

@SpringBootApplication
public class ELibraryApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    /*
    todo:
        fix check availability reservation feature
    */

    public ELibraryApplication(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ELibraryApplication.class, args);
    }

    @Override
    public void run(String... args) {
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
