package ovh.kocproz.markpages.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ovh.kocproz.markpages.data.model.UserModel;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel getByUsername(String username);

    List<UserModel> findAllByUsernameContaining(String username, Pageable pageable);
}
