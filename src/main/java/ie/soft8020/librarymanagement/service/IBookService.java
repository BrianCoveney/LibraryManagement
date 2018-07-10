package ie.soft8020.librarymanagement.service;

import java.util.List;

import ie.soft8020.librarymanagement.domain.Book;

public interface IBookService {
	void save(Book book);
	Book getById(int id);
	void remove(Book book);
	List<Book> findAll();
	Book getByTitle(String title);
	Book getByAuthor(String author);
}
