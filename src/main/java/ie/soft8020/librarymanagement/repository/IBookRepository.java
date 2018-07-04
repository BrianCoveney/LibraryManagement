package ie.soft8020.librarymanagement.repository;

import java.util.List;

import ie.soft8020.librarymanagement.domain.Book;

public interface IBookRepository {
	public Book get(int id);
	public void save(Book book);
	public void remove(Book book);
	public List<Book> findAll();
}
