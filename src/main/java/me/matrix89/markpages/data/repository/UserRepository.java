package me.matrix89.markpages.data.repository;

import me.matrix89.markpages.data.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel getByUsername(String username);
}
