package ovh.kocproz.markpages.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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
    @JsonIgnore
    @Column(name = "password", columnDefinition = "TEXT", length = 160)
    private String password;

    @Column(name = "openid_subject", length = 255)
    private String openidSubject;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<PermissionModel> permissions = new HashSet<>();

    @OneToMany(mappedBy = "creator")
    private Set<FileModel> files = new HashSet<>();

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

    public Set<PermissionModel> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionModel> permissions) {
        this.permissions = permissions;
    }

    public void addPermission(PermissionModel permission) {
        permissions.add(permission);
    }

    public String getOpenidSubject() {
        return openidSubject;
    }

    public void setOpenidSubject(String openidSubject) {
        this.openidSubject = openidSubject;
    }

    @Override
    public String toString() {
        return String.format("UserModel{username: %s}", username);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UserModel && this.getId() == ((UserModel) obj).getId();
    }

    public Set<FileModel> getFiles() {
        return files;
    }

    public void setFiles(Set<FileModel> files) {
        this.files = files;
    }
}
