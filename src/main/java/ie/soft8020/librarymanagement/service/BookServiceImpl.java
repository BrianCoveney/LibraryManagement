package ie.soft8020.librarymanagement.service;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements IBookService {

	@Autowired
	IBookRepository iBookRepository;

	public BookServiceImpl(IBookRepository iBookRepository) {
		this.iBookRepository = iBookRepository;
	}

	@Override
	public void save(Book book) {
		iBookRepository.save(book);
	}

	@Override
	public void remove(Book book) { iBookRepository.remove(book); }

	@Override
	public List<Book> findAll() {
		return iBookRepository.findAll();
	}

	@Override
	public Book getById(int id) {
		return iBookRepository.getById(id);
	}

	@Override
	public Book getByTitle(String title) {
		return iBookRepository.getByTitle(title);
	}

	@Override
	public Book getByAuthor(String author) {
		return iBookRepository.getByAuthor(author);
	}

	@Override
	public List<Book> findAllBooksOverdue() {
		return iBookRepository.findBooksLoanedByMembers();
	}


}
