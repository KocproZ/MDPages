package ovh.kocproz.mdpages.data.dto.admin;

import ovh.kocproz.mdpages.Permission;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Hubertus
 * Created 26.01.2018
 */
public class UserPermissionsDTO {
    @NotNull
    private String username;
    @NotNull
    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
