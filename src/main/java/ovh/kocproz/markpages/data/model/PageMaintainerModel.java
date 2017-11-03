package ovh.kocproz.markpages.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Hubertus
 * Created 24.10.2017
 */
@Entity
@Table(name = "pages_maintainers")
public class PageMaintainerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserModel user;

    @ManyToOne
    private PageModel page;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public PageMaintainerModel(UserModel user, PageModel page, Role role) {
        this.user = user;
        this.page = page;
        this.role = role;
    }

    public PageMaintainerModel() {
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

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        OWNER,
        MAINTAINER
    }
}
