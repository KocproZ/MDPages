package me.matrix89.markpages.data.dto;

import java.util.List;

public class SearchDTO {
    private List<String> tags;
    private String name;

    public SearchDTO() {
    }

    public SearchDTO(List<String> tags, String nameQuery) {
        this.tags = tags;
        this.name = nameQuery;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
