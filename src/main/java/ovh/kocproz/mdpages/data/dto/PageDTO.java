package ovh.kocproz.mdpages.data.dto;

import java.util.List;

/**
 * @author Hubertus
 * Created 19.11.2017
 */
public class PageDTO {
    private String name;
    private List<String> tags;
    private String stringId;

    public PageDTO(String name, List<String> tags, String stringId) {
        this.name = name;
        this.tags = tags;
        this.stringId = stringId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }
}
