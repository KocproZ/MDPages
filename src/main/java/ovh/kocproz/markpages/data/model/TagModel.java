package ovh.kocproz.markpages.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Tag")
@Table(name = "tags")
public class TagModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", unique = true, length = 32, nullable = false, updatable = false)
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<PageModel> pages = new HashSet<>();

    public Set<PageModel> getPages() {
        return pages;
    }

    public void setPages(Set<PageModel> pages) {
        this.pages = pages;
    }

    public void addPage(PageModel page) {
        pages.add(page);
    }

    public void addPages(List<PageModel> pages) {
        this.pages.addAll(pages);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
