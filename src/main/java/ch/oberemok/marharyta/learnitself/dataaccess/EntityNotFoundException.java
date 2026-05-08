package ch.oberemok.marharyta.learnitself.dataaccess;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Long id, Class c) {
        super("Could not find " + c.getSimpleName() + " " + id);
    }
}