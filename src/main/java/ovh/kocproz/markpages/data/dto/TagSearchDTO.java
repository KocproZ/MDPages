package ovh.kocproz.markpages.data.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Hubertus
 * Created 19.11.2017
 */
public class TagSearchDTO {
    @Min(1)
    private int p;
    @NotNull
    @Size(min = 3)
    private String tag;

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
