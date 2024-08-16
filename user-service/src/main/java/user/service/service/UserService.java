package user.service.service;

import org.springframework.data.domain.Pageable;
import user.service.dto.CreateUserDto;
import user.service.dto.UserDto;
import user.service.dto.UserSearchCriteriaDto;

import javax.json.JsonPatch;
import java.util.List;

public interface UserService {
    UserDto findUserByUserName(String username);

    UserDto createUser(CreateUserDto createUserDto);

    void enableUser(String username);

    void deleteUser(String username);

    List<UserDto> findAllUsers(Pageable pageable);

    void updateUser(String username, JsonPatch jsonPatch);

    List<UserDto> findUsersByCriteria(UserSearchCriteriaDto searchCriteria, Pageable pageable);

}
