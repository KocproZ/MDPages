package ovh.kocproz.mdpages.data.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SearchDTO {
    @Min(1)
    private int p;
    @NotNull
    @Size(min = 3)
    private String query;

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
