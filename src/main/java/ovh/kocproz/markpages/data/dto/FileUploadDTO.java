package ovh.kocproz.markpages.data.dto;

import org.springframework.web.multipart.MultipartFile;
import ovh.kocproz.markpages.Visibility;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FileUploadDTO {

    @NotNull
    @Size(min = 3, max = 128)
    private String name;
    private String code;
    @NotNull
    private MultipartFile file;
    private boolean fileProvided;
    @NotNull
    private Visibility visibility;

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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setFileProvided(boolean fileProvided) {
        this.fileProvided = fileProvided;
    }

    public boolean getFileProvided() {
        return fileProvided;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @AssertFalse(message = "File must be provided")
    public boolean isFileProvided() {
        return file != null && file.isEmpty();
    }

    @Override
    public String toString() {
        return "FileUploadDTO{name=" + this.name + ", visibility=" + this.visibility + "}";
    }
}
