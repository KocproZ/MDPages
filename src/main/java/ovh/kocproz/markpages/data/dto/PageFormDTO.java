package ovh.kocproz.markpages.data.dto;

import ovh.kocproz.markpages.Visibility;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class PageFormDTO {

    @NotNull
    @Size(min = 3, max = 128)
    private String title;
    @NotNull
    private String code;
    private boolean isCodeValid;
    @NotNull
    private String content;
    @NotNull
    private Visibility visibility;
    private List<String> tags;
    private List<String> users;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public void setCodeValid(boolean codeValid) {
        isCodeValid = codeValid;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @AssertTrue(message = "Code must be exactly 8 characters")
    public boolean isCodeValid() {
        if (code.length() == 8 || code.isEmpty())
            return true;
        return false;
    }

    @AssertTrue(message = "Invalid visibility value")
    public boolean isVisibilityValid() {
        return visibility == Visibility.PUBLIC || visibility == Visibility.AUTHORIZED;
    }

    @Override
    public String toString() {
        return String.format("PageFormDTO{title=%s, code=%s}", title, code);
    }
}
