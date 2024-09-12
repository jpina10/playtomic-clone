package user.service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.service.dto.CreateUserDto;
import user.service.dto.UserCriteriaSpecification;
import user.service.dto.UserDto;
import user.service.dto.UserLoginDto;
import user.service.dto.UserSearchCriteriaDto;
import user.service.exception.model.AccessException;
import user.service.exception.model.UserAlreadyExistsException;
import user.service.exception.model.UserNotFoundException;
import user.service.exception.model.UserWithPasswordNotFoundException;
import user.service.mapper.UserMapper;
import user.service.model.User;
import user.service.repository.UserRepository;

import javax.json.JsonPatch;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import static user.service.util.log.LoggingMessages.DELETING;
import static user.service.util.log.LoggingMessages.ORIGINAL_USER;
import static user.service.util.log.LoggingMessages.PATCHED_USER;
import static user.service.util.log.LoggingMessages.RETRIEVING;
import static user.service.util.log.LoggingMessages.SAVING;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    @Override
    public UserDto findUserByEmail(String email) {
        return userMapper.toDto(getUserByEmail(email));
    }

    @Override
    public UserLoginDto findUserByEmailAndPassword(String email, String password) {
        User user = getUserByEmailAndPassword(email, password);

        return new UserLoginDto(user.getEmail(), user.getRoles());
    }

    @Override
    public List<UserDto> findAllUsers(Pageable pageable) {
        var users = userRepository.findAll(pageable);

        return users.map(userMapper::toDto).toList();
    }

    @Override
    public List<UserDto> findUsersByCriteria(UserSearchCriteriaDto searchCriteria, Pageable pageable) {
        var specification = ifSearchingByFieldAddToSpecification(searchCriteria);

        Page<User> userPage = userRepository.findAll(specification, pageable);

        return userPage.map(userMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        User user = this.getUserByEmail(email);

        log.info(DELETING, user.getEmail());
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void updateUser(String email, JsonPatch jsonPatch) {
        User originalUser = this.getUserByEmail(email);
        log.info(ORIGINAL_USER, originalUser);

        JsonStructure target = objectMapper.convertValue(originalUser, JsonStructure.class);
        JsonValue patched = jsonPatch.apply(target);

        var patchedUser = objectMapper.convertValue(patched, User.class);

        log.info(PATCHED_USER, patchedUser);
        userRepository.save(patchedUser);
    }

    @Override
    @Transactional
    public void createUser(CreateUserDto createUserDto) {
        existsUser(createUserDto.getEmail());

        log.info(SAVING, createUserDto.getEmail());
        userRepository.save(userMapper.toEntity(createUserDto));
    }

    private User getUserByEmail(String email) {
        log.info(RETRIEVING, email);

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    private User getUserByEmailAndPassword(String email, String password) {
        log.info(RETRIEVING, email, password);

        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(UserWithPasswordNotFoundException::new);
    }

    private void existsUser(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new UserAlreadyExistsException(email);
        });
    }

    private Specification<User> ifSearchingByFieldAddToSpecification(UserSearchCriteriaDto userSearchCriteriaDto) {
        var fields = userSearchCriteriaDto.getClass().getDeclaredFields();

        Specification<User> spec = Specification.where(null);

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (Objects.nonNull(field.get(userSearchCriteriaDto))) {
                    spec = spec.and(UserCriteriaSpecification.addField(field.getName(), field.get(userSearchCriteriaDto).toString()));
                }
            } catch (IllegalAccessException exception) {
                throw new AccessException(field.getName());
            }
        }

        return spec;
    }
}
