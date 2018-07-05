package ie.soft8020.librarymanagement.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.rowmapper.BookRowMapper;

@Repository
public class BookRepositoryImpl implements IBookRepository {
	
	private JdbcTemplate jdbcTemplate;
	private String sql;
	
	public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Book get(int id) {
		sql = "SELECT * FROM books WHERE book_id = ?";
		Book book = jdbcTemplate.queryForObject(sql, new Object[] { id },
				new BookRowMapper());
		return book;
	}

	@Override
	public void save(Book book) {
		if (book.getBookID() != 0) {
			update(book);
		} else {
			add(book);
		}
	}

	@Override
	public void remove(Book book) {
		sql = "DELETE FROM books WHERE book_id = ?";
		jdbcTemplate.update(sql, new Object[] { book.getBookID() } );
	}

	@Override
	public List<Book> findAll() {
		sql = "SELECT * from books";
		List<Book> books = jdbcTemplate.query(sql, new BookRowMapper());
		return books;
	}

	private void update(Book book) {
		sql = "UPDATE books SET title=?, isbn=?, author=?, publisher=?, edition=?, year_of_publication=?"
				+ " WHERE book_id = ?";
		jdbcTemplate.update(sql, new Object[] { book.getTitle(), book.getIsbn(), book.getAuthor(), book.getPublisher(),
				book.getEdition(), book.getYearOfPublication(),
				book.getBookID()});
	}
	
	private void add(Book book) {
		sql = "INSERT into books (title, isbn, author, publisher, edition, year_of_publication)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] {book.getTitle(), book.getIsbn(), book.getAuthor(), book.getPublisher(),
				book.getEdition(), book.getYearOfPublication()});
	}
	
}
 