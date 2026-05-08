package ch.oberemok.marharyta.learnitself.user;


import ch.oberemok.marharyta.learnitself.base.MessageResponse;
import ch.oberemok.marharyta.learnitself.security.Roles;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Users", description = "User management operations")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService service) {
        this.usersService = service;
    }

    @PostMapping("api/users/register")
    @RolesAllowed(Roles.Read)
    @Operation(summary = "Register a new user", description = "Creates a new user account")
    @ApiResponse(responseCode = "200", description = "User successfully created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<Users> createUser(
            @Parameter(description = "User object to be registered", required = true)
            @Valid @RequestBody Users users) {
        Users newUser = usersService.registerUser(users);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("api/users")
    @RolesAllowed(Roles.Read)
    @Operation(summary = "Get all users", description = "Returns a list of all registered users")
    @ApiResponse(responseCode = "200", description = "Users successfully retrieved")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<List<Users>> all() {
        List<Users> users = usersService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("api/users/{id}")
    @RolesAllowed(Roles.Read)
    @Operation(summary = "Get a user by ID", description = "Returns a single user by their ID")
    @ApiResponse(responseCode = "200", description = "User successfully retrieved")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<Users> one(@Parameter(description = "ID of the user", required = true) @PathVariable Long id) {
        Users users = usersService.getUser(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    /**
     *
     * TODO: Authentification
     * Only if id matches, user will be able to change its credentials
     * @param users user
     * @param id user id
     * @return Response with edited User und 200 Status (success)
     */
    @PutMapping("api/users/{id}")
    @RolesAllowed(Roles.Read)
    //@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @Operation(summary = "Update a user", description = "Updates an existing user – only the user themselves or an admin can do this")
    @ApiResponse(responseCode = "200", description = "User successfully updated")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<Users> editUser(
            @Parameter(description = "Updated user object", required = true)
            @Valid @RequestBody Users users,
            @Parameter(description = "ID of the user to update", required = true)
            @PathVariable Long id) {
        Users editedUser = usersService.updateUser(users, id);
        return new ResponseEntity<>(editedUser, HttpStatus.OK);
    }


    /**
     *
     * TODO: Authhentication
     *
     * @param id
     * @return
     */
    @DeleteMapping("api/users/{id}")
    @RolesAllowed(Roles.Read)
    //@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @Operation(summary = "Delete a user", description = "Deletes a user – only the user themselves or an admin can do this")
    @ApiResponse(responseCode = "200", description = "User successfully deleted")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<MessageResponse> deleteUser (
            @Parameter(description = "ID of the user to delete", required = true)
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(usersService.deleteUser(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
