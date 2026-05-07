package ch.oberemok.marharyta.learnitself.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Für die Kommunikation mit DB
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByOrderByNameAsc();
}
