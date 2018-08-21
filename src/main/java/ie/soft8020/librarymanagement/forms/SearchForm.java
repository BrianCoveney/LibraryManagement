package ie.soft8020.librarymanagement.forms;

import javax.validation.constraints.Size;

public class SearchForm {

    @Size(min = 2, max = 30, message = "title must be in the range 2 - 30")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
