package ovh.kocproz.mdpages.user.entity;

import ovh.kocproz.mdpages.data.model.UserModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = UserModel.class, fetch = FetchType.LAZY)
    private UserModel user;

    @Column(nullable = false, unique = true, updatable = false)
    private String token;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime expirationDate;


    public RefreshToken(int id, UserModel user, String token, LocalDateTime creationDate, LocalDateTime expirationDate) {
        this.id = id;
        this.user = user;
        this.token = token;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public RefreshToken() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
