package ovh.kocproz.markpages.data.model;

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

    @OneToMany(mappedBy = "id")
    private UserModel user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false, updatable = false)
    private Permission permission;

    public enum Permission {
        CREATE,    //creating pages
        UPLOAD,    //uploading files
        MODERATE,  //editing everything
        REGISTER,  //registering users
        ADMIN      //changing page settings
    }

}
