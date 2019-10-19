package ovh.kocproz.mdpages.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "persistent_logins")
public class PersistentLoginsModel {

    @NotNull
    @Column(name = "username", nullable = false, length = 32)
    private String username;

    @Id
    @NotNull
    @Column(name = "series", nullable = false, length = 64)
    private String series;

    @NotNull
    @Column(name = "token", nullable = false, length = 64)
    private String token;

    @NotNull
    @Column(name = "last_used", nullable = false)
    private Timestamp last_user;

}
