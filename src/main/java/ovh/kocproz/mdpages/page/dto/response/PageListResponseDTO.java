package ovh.kocproz.mdpages.page.dto.response;

import java.util.List;

public class PageListResponseDTO {
    private List<PageInfoResponseDTO> pages;
    private int pageNumber;

    public PageListResponseDTO(List<PageInfoResponseDTO> pages, int pageNumber) {
        this.pages = pages;
        this.pageNumber = pageNumber;
    }

    public List<PageInfoResponseDTO> getPages() {
        return pages;
    }

    public void setPages(List<PageInfoResponseDTO> pages) {
        this.pages = pages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
