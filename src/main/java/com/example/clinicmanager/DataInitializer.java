package com.example.clinicmanager;

import com.example.clinicmanager.model.UserEntity;
import com.example.clinicmanager.repository.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Initializes default data in the database.
 */
@Component
@Hidden // Hides this class from Swagger documentation as it's an internal initializer
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            UserEntity admin = new UserEntity(
                    "admin",
                    passwordEncoder.encode("admin123"),
                    UserEntity.Role.ADMIN,
                    "admin@example.com",
                    "System Administrator"
            );
            userRepository.save(admin);
        }
    }
}
