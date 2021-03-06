package ovh.kocproz.markpages.data.model;


import ovh.kocproz.markpages.Visibility;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Page")
@Table(name = "pages", indexes = {@Index(name = "PAGES_INDEX", columnList = "id, code, visibility")})
public class PageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", length = 128, unique = true, nullable = false)
    private String title;

    @NotNull
    @Column(name = "code", length = 8, unique = true, nullable = false)
    private String code;

    @NotNull
    @Column(name = "content", columnDefinition = "MEDIUMTEXT")
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

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "tags_pages",
            joinColumns = @JoinColumn(name = "page_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<TagModel> tags = new HashSet<>();

    @Transient
    final static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public void clearTags() {
        tags.clear();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("PageModel{title: %s}", getTitle());
    }

}

