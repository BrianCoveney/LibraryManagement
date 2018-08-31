package ie.soft8020.librarymanagement.repository;

import ie.soft8020.librarymanagement.domain.Book;

import java.util.List;

public interface IBookRepository {
	Book getById(int id);
	void save(Book book);
	void remove(Book book);
	List<Book> findAll();
    Book getByTitle(String title);
    Book getByAuthor(String author);
	List<Book> getBooksByAuthor(String author);
	List<Book> findBooksLoanedByMembers();
    List<Book> searchForBooksOnLoan(String author, String title);
    List<Book> searchBooks_NotOnLoan(String title, String author);
}
