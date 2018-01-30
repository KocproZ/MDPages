package ovh.kocproz.markpages.data.dto;

import java.util.List;

/**
 * @author Hubertus
 * Created 26.01.2018
 */
public class UserPermissionsDTO {
    private List<String> permissions;

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
