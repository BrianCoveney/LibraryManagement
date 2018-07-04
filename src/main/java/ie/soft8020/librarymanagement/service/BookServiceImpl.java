package ie.soft8020.librarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.repository.IBookRepository;

@Service
public class BookServiceImpl implements IBookService {
	
	@Autowired
	IBookRepository iBookRepository;

	@Override
	public void save(Book book) {
		iBookRepository.save(book);
	}

	@Override
	public Book get(int id) {
		return iBookRepository.get(id);
	}

	@Override
	public void remove(Book book) {
		iBookRepository.remove(book);
		
	}

	@Override
	public List<Book> findAll() {
		return iBookRepository.findAll();
	}
}
