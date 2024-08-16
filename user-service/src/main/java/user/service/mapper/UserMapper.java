package user.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import user.service.dto.CreateUserDto;
import user.service.dto.UserDto;
import user.service.model.User;
import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "createdOn", expression = "java(getNow())")
    @Mapping(target = "updatedOn", expression = "java(getNow())")
    User toEntity(CreateUserDto createUserDto);

    default LocalDateTime getNow() {
        return LocalDateTime.now();
    }

}