package ie.soft8020.librarymanagement.domain;

import java.util.List;

public class BookList {
    private List<Book> books;

    public void addBook(Book book) {
        this.books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
