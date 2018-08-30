package ie.soft8020.librarymanagement.repository;

import ie.soft8020.librarymanagement.domain.Book;

import java.util.List;

public interface IBookRepository {
	Book getById(int id);
	void save(Book book);
	void remove(Book book);
	List<Book> findAll();

    List<Book> getByAuthorOrTitle(String author, String title);

    Book getByTitle(String title);
    Book getByAuthor(String author);
	List<Book> getBooksByAuthor(String author);
	List<Book> findBooksLoanedByMembers();

    List<Book> findBooksLoanedWithSearch(String author, String title);
}
