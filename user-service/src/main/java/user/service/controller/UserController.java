package user.service.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.service.dto.CreateUserDto;
import user.service.dto.LoginDto;
import user.service.dto.UserDto;
import user.service.dto.UserLoginDto;
import user.service.dto.UserSearchCriteriaDto;
import user.service.security.Secured;
import user.service.service.UserService;

import javax.json.JsonPatch;
import java.util.List;

@RestController
@Tag(name = "User", description = "Users")
@Tag(name = "User", description = "Users")
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Secured
public class UserController {

    private final UserService userService;

    @GetMapping("/{email}")
    public UserDto findUserByEmail(@Parameter(name = "email") @PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @Hidden
    @PostMapping("/login")
    public UserLoginDto login(@RequestBody LoginDto loginDto) {
        return userService.findUserByEmailAndPassword(loginDto.email(), loginDto.password());
    }

    @Operation(summary = "Retrieves a User given a search criteria")
    @GetMapping("/search")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The User have been returned", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "The User has not been found", content = @Content)})
    public List<UserDto> findUserByCriteria(@ParameterObject UserSearchCriteriaDto searchCriteria, @ParameterObject Pageable pageable) {
        return userService.findUsersByCriteria(searchCriteria, pageable);
    }

    @Operation(summary = "Creates a User")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The User has been created"),
    })
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserDto> findAllUsers(@ParameterObject Pageable pageable) {
        return userService.findAllUsers(pageable);
    }

    @PreAuthorize("hasRole('ADMIN') || hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{email}")
    public void deleteUser(@Parameter(name = "email") @PathVariable String email) {
        userService.deleteUser(email);
    }

    @PatchMapping(value = "/{email}", consumes = "application/json-patch+json")
    public void updateUser(@Parameter(name = "email") @PathVariable String email, @RequestBody JsonPatch jsonPatch) {
        userService.updateUser(email, jsonPatch);
    }

}
