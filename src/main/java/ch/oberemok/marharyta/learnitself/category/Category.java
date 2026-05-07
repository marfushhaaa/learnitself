package ch.oberemok.marharyta.learnitself.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // wie Auto-Increment
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Auto-Increment")
    private Long id;

    @Column(nullable = false, length = 40)
    @Size(max = 40)
    private String name;

    public Category() {

    }

    public Category(String name) {
        this.name = name;
    }
}
