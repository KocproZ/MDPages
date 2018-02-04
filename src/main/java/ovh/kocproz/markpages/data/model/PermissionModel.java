package ovh.kocproz.markpages.data.model;

import ovh.kocproz.markpages.Permission;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author KocproZ
 * Created 11/17/17
 */

@Entity(name = "Permission")
@Table(name = "permissions")
public class PermissionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserModel user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false, updatable = false)
    private Permission permission;

    public PermissionModel(UserModel user, Permission permission) {
        this.user = user;
        this.permission = permission;
    }

    public PermissionModel() {
    }

    public Long getId() {
        return id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Permission getPermission() {
        return permission;
    }

}
