package ch.oberemok.marharyta.learnitself.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Automatically generated id")
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    @Size(max = 20)
    private String username;

    @Column(length = 25)
    @Size(max = 25)
    private String last_name;

    @Column(nullable = false, length = 25)
    @Size(max = 25)
    private String first_name;

    @Column(nullable = false, length = 50)
    @Size(max = 50)
    private String email;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 30)
    @Size(max = 30)
    private String password;

    public User() {

    }
}
