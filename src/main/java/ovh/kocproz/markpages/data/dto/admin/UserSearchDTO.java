package ovh.kocproz.markpages.data.dto.admin;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Hubertus
 * Created 30.01.2018
 */
public class UserSearchDTO {
    @Min(1)
    private int page;
    @NotNull
    private String search;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
