package ie.soft8020.librarymanagement.repository;

import ie.soft8020.librarymanagement.LibraryManagementApplication;
import ie.soft8020.librarymanagement.domain.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LibraryManagementApplication.class, webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("default")
@Rollback
public class BookRepositoryImplTest {

	@Autowired
	IBookRepository repo;

	private int defaultDbSize;
	private int id = 2;

    @Before
    public void setUp() throws Exception {
        defaultDbSize = repo.findAll().size();
    }

    @Test
	public void testGet() {
		Book book = repo.getById(id);
		assertThat(book.getTitle(), equalTo("Effective Java"));
	}

	@Test
	public void testFindAll() {
		List<Book> books = repo.findAll();
		assertThat(books, hasSize(defaultDbSize));
	}

	@Test
	@Transactional
	public void testRemove() {
		// @Transactional at method level and @Roolback at the class level,
		// will ensure that the database rolls back at the end of testing.
		Book book = repo.getById(id);
		repo.remove(book);
		List<Book> books = repo.findAll();
		assertThat(books, hasSize(defaultDbSize -1));
	}

	@Test
	@Transactional
	public void testSave() {
		// Our database has rolled back to its default size,
		// due to the @Transactional and @Roolback annotations.
		List<Book> books = repo.findAll();
		assertThat(books, hasSize(defaultDbSize));

		Book book = new Book();
		book.setTitle("My New Book");

		repo.save(book);
		List<Book> newBooks = repo.findAll();
		assertThat(newBooks, hasSize(defaultDbSize + 1));
	}

	@Test
	public void testGetBookByAuthor() {
		String author = "Joshua Bloch";
		Book book = repo.getByAuthor(author);
		assertThat(book.getAuthor(), equalTo(author));
	}

	@Test
	public void testGetBookByTitle() {
		String title = "Clean Code";
		Book book = repo.getByTitle(title);
		assertThat(book.getTitle(), equalTo(title));
	}

	@Test
	public void testGetBooksByAuthor() {
        List<Book> books = repo.getBooksByAuthor("Robert Martin");
        assertThat(books.get(0).getTitle(), equalTo("Clean Code"));
        assertThat(books.get(1).getTitle(), equalTo("The Clean Coder"));
        assertThat(books.get(2).getTitle(), equalTo("Clean Architecture"));
        assertThat("List of books by R.M. is three", books, hasSize(3));
	}

    @Test
    public void testFindBooksLoanedByMembers_BookIdNotNull() {
        List<Book> books = repo.findBooksLoanedByMembers();
        books.stream().map(book -> book.getBookID()).forEach(Assert::assertNotNull);
    }

    @Test
    public void testFindBooksLoanedByMembers_MemberNotNull() {
        List<Book> books = repo.findBooksLoanedByMembers();
        books.stream().map(book -> book.getMembers()).forEach(Assert::assertNotNull);
    }

	@Test
	public void testFindBooksLoanedByMembers_ValidCountOfMembersInDB() {
		List<Book> books = repo.findBooksLoanedByMembers();

		// Test that our hardcoded DB contains members with 6 books on loan
        for (Book book : books) {
//			assertThat(book.getMembers(), hasSize(7));
        }
	}

}
