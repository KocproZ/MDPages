package ovh.kocproz.mdpages.page.dto.response;

public class PaginationInfoResponseDTO {
    private int pages;
    private int elements;
    private int elementsOnPage;

    public PaginationInfoResponseDTO() {
    }

    public PaginationInfoResponseDTO(int pages, int elements, int elementsOnPage) {
        this.pages = pages;
        this.elements = elements;
        this.elementsOnPage = elementsOnPage;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getElements() {
        return elements;
    }

    public void setElements(int elements) {
        this.elements = elements;
    }

    public int getElementsOnPage() {
        return elementsOnPage;
    }

    public void setElementsOnPage(int elementsOnPage) {
        this.elementsOnPage = elementsOnPage;
    }
}
