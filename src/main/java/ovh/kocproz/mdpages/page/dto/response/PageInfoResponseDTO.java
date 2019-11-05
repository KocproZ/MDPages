package ovh.kocproz.mdpages.page.dto.response;

import java.util.List;

public class PageInfoResponseDTO {
    private String code;
    private String title;
    private List<String> tags;

    public PageInfoResponseDTO(String code, String title, List<String> tags) {
        this.code = code;
        this.title = title;
        this.tags = tags;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
