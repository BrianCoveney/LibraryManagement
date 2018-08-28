package ie.soft8020.librarymanagement.forms;

import ie.soft8020.librarymanagement.domain.BookList;

import javax.validation.constraints.NotBlank;

public class SearchAuthorForm {

    @NotBlank(message = "please the author of a book")
    private String author;

    private String title;

    private BookList books;

    public BookList getBooks() {
        return books;
    }

    public void setBooks(BookList books) {
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
}
