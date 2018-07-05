package ie.soft8020.librarymanagement.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ie.soft8020.librarymanagement.LibraryManagementApplication;
import ie.soft8020.librarymanagement.domain.Book;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LibraryManagementApplication.class, webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class IBookRepositoryTest {
	
	@Autowired
	IBookRepository repo;

	@Test
	public void testGet() {
		Book book = repo.get(2);
		assertThat(book.getTitle(), is("Effective Java"));
	}

	@Test
	public void testFindAll() {
		List<Book> books = repo.findAll();
		assertThat(books, hasSize(10));
	}
	
	@Test
	public void testRemove() {
		Book book = repo.get(2);
		repo.remove(book);
		List<Book> books = repo.findAll();
		assertThat(books, hasSize(9));
	}

}
