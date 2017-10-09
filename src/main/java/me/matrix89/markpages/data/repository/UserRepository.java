package me.matrix89.markpages.data.repository;

import me.matrix89.markpages.data.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Long> {
    UserModel getByUsername(String username);
}
