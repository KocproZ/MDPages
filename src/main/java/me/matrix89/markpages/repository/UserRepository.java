package me.matrix89.markpages.repository;

import me.matrix89.markpages.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Integer> {
    UserModel getByUsername(String username);
}
