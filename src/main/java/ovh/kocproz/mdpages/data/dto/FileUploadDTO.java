package ovh.kocproz.mdpages.data.dto;

import org.springframework.web.multipart.MultipartFile;
import ovh.kocproz.mdpages.Visibility;

import javax.validation.constraints.AssertTrue;
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
    private boolean visibilityValid;

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

    public void setVisibilityValid(boolean visibilityValid) {
        this.visibilityValid = visibilityValid;
    }

    public boolean getVisibilityValid() {
        return visibilityValid;
    }

    @AssertTrue(message = "File must be provided")
    public boolean isFileProvided() {
        return file != null && !file.isEmpty();
    }

    @AssertTrue(message = "Invalid visibility value")
    public boolean isVisibilityValid() {
        return visibility == Visibility.PUBLIC || visibility == Visibility.AUTHORIZED;
    }

    @Override
    public String toString() {
        return "FileUploadDTO{name=" + this.name + ", visibility=" + this.visibility + "}";
    }
}
