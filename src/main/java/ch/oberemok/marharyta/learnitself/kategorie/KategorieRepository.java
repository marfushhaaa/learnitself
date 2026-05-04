package ch.oberemok.marharyta.learnitself.kategorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KategorieRepository extends JpaRepository<Kategorie, Long> {
    List<Kategorie> findByOrderByNameAsc();
}
