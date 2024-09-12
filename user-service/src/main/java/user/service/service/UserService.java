package user.service.service;

import org.springframework.data.domain.Pageable;
import user.service.dto.CreateUserDto;
import user.service.dto.UserDto;
import user.service.dto.UserLoginDto;
import user.service.dto.UserSearchCriteriaDto;

import javax.json.JsonPatch;
import java.util.List;

public interface UserService {
    void createUser(CreateUserDto createUserDto);

    void deleteUser(String email);

    List<UserDto> findAllUsers(Pageable pageable);

    void updateUser(String email, JsonPatch jsonPatch);

    List<UserDto> findUsersByCriteria(UserSearchCriteriaDto searchCriteria, Pageable pageable);

    UserDto findUserByEmail(String email);

    UserLoginDto findUserByEmailAndPassword(String email, String password);
}
