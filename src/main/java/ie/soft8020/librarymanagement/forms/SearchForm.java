package ie.soft8020.librarymanagement.forms;

import ie.soft8020.librarymanagement.domain.Book;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.util.List;

public class SearchForm {

    @Pattern(regexp = "^[a-zA-Z ]*$", message = "author's name must be alphabetical")
    @Length(min = 0, max = 40, message = "author's name must between 3 and 40 characters")
    private String author;

    @Pattern(regexp = "^[a-zA-Z ]*$", message = "title must be alphabetical")
    @Length(min = 0, max = 40, message = "title must between 3 and 40 characters")
    private String title;

    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
}
