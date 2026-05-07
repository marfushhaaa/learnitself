package ch.oberemok.marharyta.learnitself.user;

import ch.oberemok.marharyta.learnitself.base.MessageResponse;
import ch.oberemok.marharyta.learnitself.dataaccess.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getUsers() {
        return repository.findByOrderByNameAsc();
    }

    public User getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, User.class));
    }

    public User registerUser(User user) {
        return repository.save(user);
    }

    public User updateUser(User user, Long id) {
        return repository.findById(id)
                .map(userOrig -> {
                    userOrig.setFirst_name(user.getFirst_name());
                    userOrig.setLast_name(user.getLast_name());
                    userOrig.setUsername(user.getUsername());
                    userOrig.setEmail(user.getEmail());
                    userOrig.setPassword(user.getPassword());
                    return repository.save(userOrig);
                })
                .orElseGet(() -> repository.save(user));
    }

    public MessageResponse deleteUser(Long id) {
        repository.deleteById(id);
        return new MessageResponse("User " + id + " deleted");
    }
}
