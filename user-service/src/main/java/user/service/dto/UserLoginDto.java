package user.service.dto;

import user.service.model.Role;

import java.util.Set;

public record UserLoginDto(String email, Set<Role> roles) {

}
