package ie.soft8020.librarymanagement.forms;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SearchForm {

    @NotBlank(message = "please enter title of book")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "title must be alphabetical")
    @Length(min = 3, max = 40, message = "title must between 3 and 40 characters")
    private String title;

    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
