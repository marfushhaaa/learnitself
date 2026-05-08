package ch.oberemok.marharyta.learnitself;

import ch.oberemok.marharyta.learnitself.category.Category;
import ch.oberemok.marharyta.learnitself.category.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class DBTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void insertVehicle() {
        Category objCatMusic = this.categoryRepository.save(new Category("Music"));
        Assertions.assertNotNull(objCatMusic.getId());
        Category objCatMath = this.categoryRepository.save(new Category("Mathematics"));
        Assertions.assertNotNull(objCatMath.getId());
    }
}
