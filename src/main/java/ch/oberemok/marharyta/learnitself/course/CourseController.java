package ch.oberemok.marharyta.learnitself.course;

import ch.oberemok.marharyta.learnitself.base.MessageResponse;
import ch.oberemok.marharyta.learnitself.category.Category;
import ch.oberemok.marharyta.learnitself.category.CategoryRepository;
import ch.oberemok.marharyta.learnitself.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class CourseController {
    private final CourseService courseService;
    private final CategoryRepository categoryRepository;

    public CourseController(CourseService courseService, CategoryRepository categoryRepository) {
        this.courseService = courseService;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("api/courses")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        Course newCourse = courseService.createCourse(course);
        return new ResponseEntity<>(newCourse,HttpStatus.CREATED);
    }

    @GetMapping("api/courses/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Course> getCourse(@PathVariable @NonNull Long id) {
        Course course = courseService.getCourse(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
    @GetMapping("api/courses")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Course>> all() {
        List<Course> course = courseService.getCourses();
        return new ResponseEntity<>(course, HttpStatus.OK);
    }


    @PutMapping("api/courses/{id}")
    @RolesAllowed(Roles.Read)
    @PreAuthorize("#course.ersteller.id == authentication.principal.id") // nur Ersteller
    public ResponseEntity<Course> updateCourse(
            @Valid @RequestBody Course course,
            @PathVariable @NonNull Long id) {
        Course updatedCourse = courseService.updateCourse(course, id);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }


    @DeleteMapping("api/courses/{id}")
    @RolesAllowed(Roles.Read)
    @PreAuthorize("hasRole('ADMIN')") // nur Admin darf löschen
    public ResponseEntity<MessageResponse> deleteCourse(@PathVariable @NonNull Long id) {
        try {
            return ResponseEntity.ok(courseService.deleteCourse(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }


    /**
     * In plans
     */
    // POST Kurs beitreten → braucht EnrollmentService
    @PostMapping("api/courses/{id}/enroll")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<MessageResponse> enrollCourse(@PathVariable @NonNull Long id) {
        // TODO: enrollmentService.enroll(id, authentication)
        return ResponseEntity.ok(new MessageResponse("Enrolled in course " + id));
    }

    // GET Fortschritt → braucht FortschrittService
    @GetMapping("api/courses/{id}/progress")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Double> getCourseProgress(@PathVariable @NonNull Long id) {
        // TODO: fortschrittService.getProgress(id, authentication)
        return ResponseEntity.ok(0.0);
    }
}
