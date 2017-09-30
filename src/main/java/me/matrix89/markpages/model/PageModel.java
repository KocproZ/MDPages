package me.matrix89.markpages.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "pages")
public class PageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "name", length = 128, unique = true)
    private String name;

    @NotNull
    @Column(name = "content", columnDefinition = "LONGBLOB") //TODO ask marcik
    private String content;

    @NotNull
    @Column(name = "visibility", length = 16)
    private String visibility;

    @NotNull
    @Column(name = "lastEdited", columnDefinition = "DATETIME")
    private Date lastEdited;

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
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
}
