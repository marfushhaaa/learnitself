package ch.oberemok.marharyta.learnitself.category;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
@Tag(name = "Categories", description = "Category management operations")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("api/categories/{id}")
    @RolesAllowed(Roles.Read)
    @Operation(summary = "Get a category by ID", description = "Returns a single category by its ID")
    @ApiResponse(responseCode = "200", description = "Category successfully retrieved")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<Category> one(
            @Parameter(description = "ID of the category", required = true)
            @PathVariable Long id) {
        Category result = categoryService.getCategory(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/categories")
    @RolesAllowed(Roles.Read)
    @Operation(summary = "Get all categories", description = "Returns a list of all available categories")
    @ApiResponse(responseCode = "200", description = "Categories successfully retrieved")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<List<Category>> all() {
        List<Category> result = categoryService.getCategories();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("api/categories")
    @RolesAllowed(Roles.Admin)
    @Operation(summary = "Create a category", description = "Creates a new category – only admins can do this")
    @ApiResponse(responseCode = "201", description = "Category successfully created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<Category> newCategory(
            @Parameter(description = "Category object to be created", required = true)
            @Valid @RequestBody Category category) {
        Category newCategory = categoryService.insertCategory(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }


    @DeleteMapping("api/categories/{id}")
    @RolesAllowed(Roles.Admin)
    @Operation(summary = "Delete a category", description = "Deletes a category by its ID – only admins can do this")
    @ApiResponse(responseCode = "200", description = "Category successfully deleted")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<MessageResponse> deleteCategory(
            @Parameter(description = "ID of the category to delete", required = true)
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.deleteCategory(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
