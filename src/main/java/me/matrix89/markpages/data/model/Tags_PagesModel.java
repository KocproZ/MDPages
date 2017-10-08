package me.matrix89.markpages.data.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Tags_Pages")
@Table(name = "tags_pages")
public class Tags_PagesModel implements Serializable {
    //Serializable because compiler kept screaming at me

    @Id
    @NotNull
    @Column(name = "page_id")
    private Integer page_id;

    @Id
    @NotNull
    @Column(name = "tag_id")
    private Integer tag_id;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tags_PagesModel))
            return false;
        return ((Tags_PagesModel) obj).tag_id == this.tag_id &&
                ((Tags_PagesModel) obj).page_id == this.page_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.page_id, this.tag_id);
    }
}
