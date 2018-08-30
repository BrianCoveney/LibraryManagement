package ie.soft8020.librarymanagement.service;

import ie.soft8020.librarymanagement.domain.Book;

import java.util.List;

public interface IBookService {
	void save(Book book);
	Book getById(int id);
	void remove(Book book);
	List<Book> findAll();
	Book getByTitle(String title);
    Book getByAuthor(String author);
	List<Book> getBooksByAuthor(String author);
	List<Book> findBooksLoanedByMembers();
    List<Book> getBooksByAuthorOrTitle(String author, String title);
}
