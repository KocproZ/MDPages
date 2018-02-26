package ovh.kocproz.markpages.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ovh.kocproz.markpages.data.model.UserModel;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel getByUsername(String username);

    UserModel getByOpenidSubject(String openidSubject);

    Page<UserModel> findAllByUsernameContaining(String username, Pageable pageable);

    List<UserModel> findAllByUsernameContaining(String username);

    Page<UserModel> findAll(Pageable pageable);

    Long countAllByUsernameContaining(String username);
}
