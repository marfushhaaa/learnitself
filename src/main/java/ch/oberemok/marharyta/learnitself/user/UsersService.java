package ch.oberemok.marharyta.learnitself.user;

import ch.oberemok.marharyta.learnitself.base.MessageResponse;
import ch.oberemok.marharyta.learnitself.dataaccess.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private final UsersRepository repository;

    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }

    public List<Users> getUsers() {
        return repository.findByOrderByUsernameAsc();
    }

    public Users getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Users.class));
    }

    public Users registerUser(Users users) {
        return repository.save(users);
    }

    public Users updateUser(Users users, Long id) {
        return repository.findById(id)
                .map(userOrig -> {
                    userOrig.setFirst_name(users.getFirst_name());
                    userOrig.setLast_name(users.getLast_name());
                    userOrig.setUsername(users.getUsername());
                    userOrig.setEmail(users.getEmail());
                    userOrig.setPassword(users.getPassword());
                    return repository.save(userOrig);
                })
                .orElseGet(() -> repository.save(users));
    }

    public MessageResponse deleteUser(Long id) {
        repository.deleteById(id);
        return new MessageResponse("User " + id + " deleted");
    }
}
