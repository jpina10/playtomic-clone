package user.service.db;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import user.service.model.Role;
import user.service.model.User;
import user.service.repository.UserRepository;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataBaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        addAdminUser();
        addDefaultUser();
    }

    private void addAdminUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setRoles(Set.of(Role.ADMIN));

        userRepository.save(user);
    }

    private void addDefaultUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRoles(Set.of(Role.USER));

        userRepository.save(user);
    }
}
