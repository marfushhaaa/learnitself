package ch.oberemok.marharyta.learnitself.category;

import ch.oberemok.marharyta.learnitself.base.MessageResponse;
import ch.oberemok.marharyta.learnitself.dataaccess.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getCategories() {
        return repository.findByOrderByNameAsc();
    }

    public Category getCategory(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Category.class));
    }

    public Category insertCategory(Category category) {
        return repository.save(category);
    }

    public MessageResponse deleteCategory(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Category " + id + " deleted");
    }
}
