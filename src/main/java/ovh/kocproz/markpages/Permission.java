package ovh.kocproz.markpages;

/**
 * @author KocproZ
 * Created 11/19/17
 */
public enum Permission {
    CREATE("mdpages-create"),    //creating pages
    UPLOAD("mdpages-upload"),    //uploading files
    MODERATE("mdpages-moderate"),  //editing everything
    REGISTER("mdpages-register"),  //registering users
    ADMIN("mdpages-admin");      //changing page settings

    private String role;

    Permission(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
