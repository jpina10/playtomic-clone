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
        addUserWithTwoRoles();
    }

    private void addAdminUser() {
        User user = new User();
        user.setEmail("admin");
        user.setPassword("admin");
        user.setName("admin");
        user.setPhoneNumber("123456789");
        user.setRoles(Set.of(Role.ADMIN));

        userRepository.save(user);
    }

    private void addDefaultUser() {
        User user = new User();
        user.setEmail("user");
        user.setPassword("password");
        user.setName("user");
        user.setPhoneNumber("12345678");
        user.setRoles(Set.of(Role.USER));

        userRepository.save(user);
    }

    private void addUserWithTwoRoles() {
        User user = new User();
        user.setEmail("userTwoRoles");
        user.setPassword("password");
        user.setName("two roles");
        user.setPhoneNumber("1234567");
        user.setRoles(Set.of(Role.USER, Role.ADMIN));

        userRepository.save(user);
    }
}
