package ch.oberemok.marharyta.learnitself.kategorie;

import ch.oberemok.marharyta.learnitself.base.MessageResponse;
import ch.oberemok.marharyta.learnitself.dataaccess.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategorieService {
    private final KategorieRepository repository;

    public KategorieService(KategorieRepository repository) {
        this.repository = repository;
    }

    public List<Kategorie> getKategorien() {
        return repository.findByOrderByNameAsc();
    }

    public Kategorie getKategorie(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Kategorie.class));
    }

    public Kategorie insertKategorie(Kategorie kategorie) {
        return repository.save(kategorie);
    }

    public Kategorie updateKategorie(Kategorie kategorie, Long id) {
        return repository.findById(id)
                .map(vehicleOrig -> {
                    vehicleOrig.setName(kategorie.getName());
                    return repository.save(vehicleOrig);
                })
                .orElseGet(() -> repository.save(kategorie));
    }

    public MessageResponse deleteKategorie(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Kategorie " + id + " deleted");
    }
}
