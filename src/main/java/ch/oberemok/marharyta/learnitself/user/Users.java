package ch.oberemok.marharyta.learnitself.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Automatically generated id")
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    @Size(min = 3, max = 20, message = "Username must contain 3-20 characters")
    @NotEmpty(message = "Username cannot be empty")
    @NotNull
    private String username;

    @Column(nullable = false, length = 25)
    @Size(min = 2, max = 25, message = "First name must contain 2-25 characters")
    @NotEmpty(message = "First name cannot be empty")
    @NotNull
    private String first_name;

    @Column(length = 25)
    @Size(min = 2, max = 25, message = "Last name must contain 2-25 characters")
    private String last_name;

    @Column(nullable = false, length = 50)
    @Size(max = 50, message = "Email cannot be longer than 50 characters")
    @NotEmpty(message = "Email cannot be Empty")
    @NotNull
    @Email
    private String email;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 30)
    @Size(min = 8, max = 30, message = "Password must contain 8-30 characters")
    @NotEmpty
    @NotNull
    private String password;

    public Users() {

    }
}
