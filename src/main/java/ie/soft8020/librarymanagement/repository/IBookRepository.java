package ie.soft8020.librarymanagement.repository;

import java.util.List;

import ie.soft8020.librarymanagement.domain.Book;

public interface IBookRepository {
	Book getById(int id);
	void save(Book book);
	void remove(Book book);
	List<Book> findAll();
	Book getByTitle(String title);
	Book getByAuthor(String author);
}
