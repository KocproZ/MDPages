package ovh.kocproz.markpages.data.model;

import ovh.kocproz.markpages.Visibility;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Hubertus
 * Created 18.11.2017
 */
@Entity
@Table(name = "files")
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 128)
    private String name;

    @NotNull
    @Column(nullable = false, unique = true, length = 8)
    private String code;

    @NotNull
    @ManyToOne
    private UserModel creator;

    @NotNull
    @Column(name = "data", columnDefinition = "MEDIUMBLOB")
    private byte[] data;

    @NotNull
    @Column(name = "lastEdited", columnDefinition = "DATETIME", nullable = false)
    private Date lastEdited;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", length = 16, nullable = false)
    private Visibility visibility;

    private String mimeType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
