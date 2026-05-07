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
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService service) {
        this.usersService = service;
    }

    @PostMapping("api/users/register")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users users) {
        Users newUser = usersService.registerUser(users);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping("api/users")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Users>> all() {
        List<Users> users = usersService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("api/users/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Users> one(@PathVariable Long id) {
        Users users = usersService.getUser(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    /**
     *
     * TODO: Auth klären
     *
     * Only if id matches, user will be able to change its credentials
     * @param users user
     * @param id user id
     * @return Response with edited User und 200 Status (success)
     */
    @PutMapping("api/users/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Users> editUser(@Valid @RequestBody Users users, @PathVariable Long id) {
        Users editedUser = usersService.updateUser(users, id);
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
            return ResponseEntity.ok(usersService.deleteUser(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
