package ie.soft8020.librarymanagement.repository;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.domain.MemberFactory;
import ie.soft8020.librarymanagement.rowmapper.BookRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class BookRepositoryImpl implements IBookRepository {

	private JdbcTemplate jdbcTemplate;
	private String sql;

	public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Book getById(int id) {
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

	@Override
	public Book getByTitle(String title) {
		sql = "SELECT * FROM books WHERE title = ?";
		Book book = jdbcTemplate.queryForObject(sql, new Object[] { title },
				new BookRowMapper());
		return book;
	}

	@Override
	public Book getByAuthor(String author) {
		sql = "SELECT * FROM books WHERE author = ?";
		Book book = jdbcTemplate.queryForObject(sql, new Object[] { author },
				new BookRowMapper());
		return book;
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

	@Override
	public List<Book> findBooksLoanedByMembers() {
		sql = "SELECT b.book_id, b.title, m.member_id, m.name from books b, members m, loan l " +
				"WHERE b.book_id = l.book_id and m.member_id = l.member_id";

		List<Book> books = jdbcTemplate.query(sql, new ResultSetExtractor<List<Book>>() {
			@Override
			public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Book book = new Book();
				List<Member> members = new ArrayList<>();
				List<Book> bookList = new ArrayList<>();
				while (rs.next()) {
					if (book == null) {
						book.setBookID(rs.getInt("book_id"));
						book.setTitle(rs.getString("title"));
					}
					Member member = MemberFactory.createMember("name", new Date());
					member.setMemberID(rs.getInt("member_id"));
					member.setName(rs.getString("name"));
					members.add(member);
				}
				book.setMembers(members);
				bookList.add(book);


				return bookList;
			}
		});
		return books;
	}


}
