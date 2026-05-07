package ch.oberemok.marharyta.learnitself.user;


import ch.oberemok.marharyta.learnitself.base.MessageResponse;
import ch.oberemok.marharyta.learnitself.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    @PostMapping("api/users/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User newUser = userService.registerUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping("api/users")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<User>> all() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("api/users/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<User> one(@PathVariable Long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    /**
     *
     * TODO: Auth klären
     *
     * Only if id matches, user will be able to change its credentials
     * @param user user
     * @param id user id
     * @return Response with edited User und 200 Status (success)
     */
    @PutMapping("api/users/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<User> editUser(@Valid @RequestBody User user, @PathVariable Long id) {
        User editedUser = userService.updateUser(user, id);
        return new ResponseEntity<>(editedUser, HttpStatus.OK);
    }


    /**
     *
     * TODO: Auth klären
     *
     * @param id
     * @return
     */
    @DeleteMapping("api/users/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser (@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
