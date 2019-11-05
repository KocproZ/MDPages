package ovh.kocproz.mdpages.page.dto.request;

public class PaginatedListRequestDTO {
    private int page;

    public PaginatedListRequestDTO() {
    }

    public PaginatedListRequestDTO(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
