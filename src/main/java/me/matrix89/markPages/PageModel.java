package me.matrix89.markPages;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @Column(name = "content", columnDefinition = "TEXT") //TODO ask marcik
    private String content;

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
