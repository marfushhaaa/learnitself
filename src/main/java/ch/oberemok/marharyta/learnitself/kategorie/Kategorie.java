package ch.oberemok.marharyta.learnitself.kategorie;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Kategorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // wie Auto-Increment
    private long id;

    @Column(nullable = false, length = 40)
    @Size(max = 40)
    private String name;

    public Kategorie() {

    }

    public Kategorie(String name) {
        this.name = name;
    }
}
