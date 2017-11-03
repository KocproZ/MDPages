package ovh.kocproz.markpages.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.kocproz.markpages.data.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel getByUsername(String username);
}
