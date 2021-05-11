package org.acme.services;

import io.quarkus.panache.common.Sort;
import org.acme.models.User;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class UserService {
    public List<User> all() {
        return User
                .listAll(Sort.by("name"));
    }

    @Transactional
    public void save(User user) {
        user.persist();
    }

    public User getById(Long id) throws NotFoundException {
        return (User) User.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
    }
}
