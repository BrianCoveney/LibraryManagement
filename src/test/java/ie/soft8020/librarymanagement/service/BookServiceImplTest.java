package ie.soft8020.librarymanagement.service;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.repository.IBookRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BookServiceImplTest {

	private IBookService bookService;
	private IBookRepository repoMock;

	private String title1 = "My Car";
	private String title2 = "My Funny Book";
	private String author1 = "Henry Ford";
	private String author2 = "David Jones";
	private List<Book> books;


	@Before
	public void setUp() {
		repoMock = mock(IBookRepository.class);

        Book bookOne = createValidBookObject(title1, author1);
        Book bookTwo = createValidBookObject(title2, author2);

		books = new ArrayList<>();
		books.add(bookOne);
		books.add(bookTwo);

		when(repoMock.getById(1)).thenReturn(bookOne);
		when(repoMock.getById(2)).thenReturn(bookTwo);

		when(repoMock.getByAuthor(author1)).thenReturn(bookOne);
		when(repoMock.getByAuthor(author2)).thenReturn(bookTwo);

		when(repoMock.getByTitle(title1)).thenReturn(bookOne);
		when(repoMock.getByTitle(title2)).thenReturn(bookTwo);

		when(repoMock.findAll()).thenReturn(books);

		when(repoMock.getBooksByAuthor(author1)).thenReturn(books);

		bookService = new BookServiceImpl(repoMock);
	}

	@Test
	public void testGet() {
		assertThat(bookService.getById(1).getTitle(), equalTo(title1));
		assertThat(bookService.getById(1).getAuthor(), equalTo(author1));

		assertThat(bookService.getById(2).getTitle(), equalTo(title2));
        assertThat(bookService.getById(2).getAuthor(), equalTo(author2));
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

	@Test
	public void testBooksGetByAuthor() {
		assertThat(bookService.getBooksByAuthor(author1), hasSize(books.size()));
	}

	private Book createValidBookObject(String title, String author) {
		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		return book;
	}

}





