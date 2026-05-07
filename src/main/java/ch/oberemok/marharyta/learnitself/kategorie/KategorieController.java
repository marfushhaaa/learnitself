package ch.oberemok.marharyta.learnitself.kategorie;

import ch.oberemok.marharyta.learnitself.base.MessageResponse;
import ch.oberemok.marharyta.learnitself.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class KategorieController {
    private final KategorieService kategorieService;

    public KategorieController(KategorieService kategorieService) {
        this.kategorieService = kategorieService;
    }

    @GetMapping("api/kategorie/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Kategorie> one(@PathVariable Long id) {
        Kategorie result = kategorieService.getKategorie(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/kategorie")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Kategorie>> all() {
        List<Kategorie> result = kategorieService.getKategorien();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("api/kategorie")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Kategorie> newKategorie(@Valid @RequestBody Kategorie kategorie) {
        Kategorie newKategorie = kategorieService.insertKategorie(kategorie);
        return new ResponseEntity<>(newKategorie, HttpStatus.OK);
    }


    @DeleteMapping("api/kategorie/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteKategorie(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(kategorieService.deleteKategorie(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
