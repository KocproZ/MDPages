package me.matrix89.markpages.data.model;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getLast_user() {
        return last_user;
    }

    public void setLast_user(Timestamp last_user) {
        this.last_user = last_user;
    }

}
