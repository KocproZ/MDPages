package ovh.kocproz.mdpages.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ovh.kocproz.mdpages.data.model.UserModel;
import ovh.kocproz.mdpages.user.entity.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    public Optional<RefreshToken> findFirstByTokenAndUser(String token, UserModel user);
}
