package ch.oberemok.marharyta.learnitself.kategorie;

import ch.oberemok.marharyta.learnitself.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class KategorieController {
    private final KategorieService kategorieService;

    public KategorieController(KategorieService kategorieService) {
        this.kategorieService = kategorieService;
    }

    @GetMapping("api/kategorie")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Kategorie>> all() {
        List<Kategorie> result = kategorieService.getKategorien();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
