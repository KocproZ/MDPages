package me.matrix89.markpages.data.repository;

import me.matrix89.markpages.data.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Integer> {
    UserModel getByUsername(String username);
}
