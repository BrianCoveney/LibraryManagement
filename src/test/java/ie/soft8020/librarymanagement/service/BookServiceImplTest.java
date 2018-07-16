package ie.soft8020.librarymanagement.service;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.repository.IBookRepository;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BookServiceImplTest {

	private IBookService bookService;
	private IBookRepository repoMock;

	private Date sqlDate = new Date(Calendar.getInstance().getTime().getTime());
	private String title1 = "My Car";
	private String title2 = "My Funny Book";
	private String isbn = "123, 456";
	private String author1 = "Henry Ford";
	private String author2 = "David Jones";
	private String publisher = "Motors Inc";
	private String edition = "First edition";

	@Before
	public void setUp() throws Exception {
		repoMock = mock(IBookRepository.class);

		Book bookOne = new Book();
		bookOne.setTitle(title1);
		bookOne.setIsbn(isbn);
		bookOne.setAuthor(author1);
		bookOne.setPublisher(publisher);
		bookOne.setEdition(edition);
		bookOne.setYearOfPublication(sqlDate);

		Book bookTwo = new Book();
		bookTwo.setTitle(title2);
		bookTwo.setAuthor(author2);

		List<Book> books = new ArrayList<>();
		books.add(bookOne);
		books.add(bookTwo);

		when(repoMock.getById(1)).thenReturn(bookOne);
		when(repoMock.getById(2)).thenReturn(bookTwo);

		when(repoMock.getByAuthor(author1)).thenReturn(bookOne);
		when(repoMock.getByAuthor(author2)).thenReturn(bookTwo);

		when(repoMock.getByTitle(title1)).thenReturn(bookOne);
		when(repoMock.getByTitle(title2)).thenReturn(bookTwo);

		when(repoMock.findAll()).thenReturn(books);

		bookService = new BookServiceImpl(repoMock);
	}

	@Test
	public void testGet() {
		assertThat(bookService.getById(1).getTitle(), equalTo(title1));
		assertThat(bookService.getById(1).getIsbn(), equalTo(isbn));
		assertThat(bookService.getById(1).getAuthor(), equalTo(author1));
		assertThat(bookService.getById(1).getPublisher(), equalTo(publisher));
		assertThat(bookService.getById(1).getEdition(), equalTo(edition));
		assertThat(bookService.getById(1).getYearOfPublication(), equalTo(sqlDate));

		assertThat(bookService.getById(2).getTitle(), equalTo(title2));
	}

	@Test
	public void testFindAll() {
		assertThat(bookService.findAll(), hasSize(2));
	}

	@Test
	public void testGetByAuthor() {
		assertThat(bookService.getByAuthor(author1).getAuthor(), equalTo(author1));
	}

	@Test
	public void testGetByTitle() {
		assertThat(bookService.getByTitle(title2).getTitle(), equalTo(title2));
	}

}





