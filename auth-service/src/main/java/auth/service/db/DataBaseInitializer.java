package auth.service.db;


import auth.service.model.Role;
import auth.service.model.User;
import auth.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


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
        addUserMultipleRoles();
    }

    private void addAdminUser() {
        User user = new User();
        user.setEmail("admin");
        user.setPassword("admin");
        user.setRoles(Set.of(Role.ADMIN));

        userRepository.save(user);
    }

    private void addDefaultUser() {
        User user = new User();
        user.setEmail("user");
        user.setPassword("password");
        user.setRoles(Set.of(Role.USER));

        userRepository.save(user);
    }

    private void addUserMultipleRoles() {
        User user = new User();
        user.setEmail("both");
        user.setPassword("password");
        user.setRoles(Set.of(Role.USER, Role.ADMIN));

        userRepository.save(user);
    }
}
