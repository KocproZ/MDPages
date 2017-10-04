package me.matrix89.markpages.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pages")
public class PageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "name", length = 128, unique = true, nullable = false)
    private String name;

    @NotNull
    @Column(name = "content", columnDefinition = "LONGBLOB")
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", length = 16, nullable = false)
    private Visibility visibility;

    @NotNull
    @Column(name = "created", columnDefinition = "DATETIME", nullable = false)
    private Date created;

    @NotNull
    @Column(name = "lastEdited", columnDefinition = "DATETIME", nullable = false)
    private Date lastEdited;

    @ManyToOne
    private UserModel maintainer;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "tags_pages", joinColumns = @JoinColumn(name = "page_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<TagModel> tags = new HashSet<>();

    @Transient
    final static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public Set<TagModel> getTags() {
        return tags;
    }

    public void setTags(Set<TagModel> tags) {
        this.tags = tags;
    }

    public void addTag(TagModel tag) {
        tags.add(tag);
    }

    public void addTags(List<TagModel> tags) {
        this.tags.addAll(tags);
    }

    public Date getCreationDate() {
        return created;
    }

    public String getFormattedCreationDate() {
        return dateFormatter.format(getCreationDate());
    }

    public String getFormattedLastEditedDate() {
        return dateFormatter.format(getLastEdited());
    }

    public void setCreationDate(Date created) {
        this.created = created;
    }

    public UserModel getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(UserModel maintainer) {
        this.maintainer = maintainer;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("PageModel{Name: %s}", getName());
    }

    public enum Visibility {
        PUBLIC,
        AUTHORIZED,
        HIDDEN

    }
}

