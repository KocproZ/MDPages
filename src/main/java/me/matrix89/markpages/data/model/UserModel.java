package me.matrix89.markpages.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "User")
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username", unique = true, length = 32, updatable = false)
    private String username;

    @NotNull
    @Column(name = "password", columnDefinition = "TEXT", length = 160)
    private String password;  //TODO page model exposes user password

    @NotNull
    @Column(name = "role", length = 16)
    private String role;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean canEdit(PageModel page) {
        if (page == null)
            return false;
        if (getRole().equals("ROLE_ADMIN"))
            return true;
//        return page.getMaintainer().equals(this);
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserModel))
            return false;
        return this.getId() == ((UserModel) obj).getId();
    }
}
