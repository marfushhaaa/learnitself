package ch.oberemok.marharyta.learnitself.category;

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
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("api/categories/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Category> one(@PathVariable Long id) {
        Category result = categoryService.getCategory(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/categories")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Category>> all() {
        List<Category> result = categoryService.getCategories();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("api/categories")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Category> newCategory(@Valid @RequestBody Category category) {
        Category newCategory = categoryService.insertCategory(category);
        return new ResponseEntity<>(newCategory, HttpStatus.OK);
    }


    @DeleteMapping("api/categories/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.deleteCategory(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
