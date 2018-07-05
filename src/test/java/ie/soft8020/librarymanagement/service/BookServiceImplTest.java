package ie.soft8020.librarymanagement.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.repository.IBookRepository;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


public class BookServiceImplTest {
	private IBookService bookService;
	private IBookRepository repoMock;
	private Date sqlDate = new Date(Calendar.getInstance().getTime().getTime());
	
	@Before
	public void setUp() throws Exception {
		repoMock = mock(IBookRepository.class);
		
		Book bookOne = new Book();
		bookOne.setTitle("My Car");
		bookOne.setIsbn("123, 456");
		bookOne.setAuthor("Henry Ford");
		bookOne.setPublisher("Motors Inc");
		bookOne.setEdition("First edition");
		bookOne.setYearOfPublication(sqlDate);
		
		Book bookTwo = new Book();
		bookTwo.setTitle("My Funny Book");
		
		
		List<Book> books = new ArrayList<>();
		books.add(bookOne);
		books.add(bookTwo);
		
		when(repoMock.get(1)).thenReturn(bookOne);
		when(repoMock.get(2)).thenReturn(bookTwo);
		
		when(repoMock.findAll()).thenReturn(books);
		
		bookService = new BookServiceImpl(repoMock);		
	}

	@Test
	public void testGet() {
		assertThat(bookService.get(1).getTitle(), is("My Car"));
		assertThat(bookService.get(1).getIsbn(), is("123, 456"));
		assertThat(bookService.get(1).getAuthor(), is("Henry Ford"));
		assertThat(bookService.get(1).getPublisher(), is("Motors Inc"));
		assertThat(bookService.get(1).getEdition(), is("First edition"));
		assertThat(bookService.get(1).getYearOfPublication(), is(sqlDate));
		
		assertThat(bookService.get(2).getTitle(), is("My Funny Book"));
	}
	
	@Test
	public void testFindAll() {
		assertThat(bookService.findAll(), hasSize(2));
	}
}
