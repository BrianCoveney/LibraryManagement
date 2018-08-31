package ie.soft8020.librarymanagement.repository;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.Loan;
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
        return jdbcTemplate.queryForObject(sql, new Object[] { id }, new BookRowMapper());
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
        return jdbcTemplate.query(sql, new BookRowMapper());
	}

    @Override
    public Book getByTitle(String title) {
        sql = "SELECT * FROM books WHERE LOWER(title) LIKE ? LIMIT 1"; // We limit to 1 because queryForObject expects 1
        String str= "%" + title.toLowerCase().trim() + "%";
        return jdbcTemplate.queryForObject(sql, new Object[] { str }, new BookRowMapper());
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        sql = "SELECT * FROM books WHERE LOWER(author) LIKE ?";
        String str= "%" + author.toLowerCase().trim() + "%";
        return jdbcTemplate.query(sql, new Object[] { str }, new BookRowMapper());
    }

	@Override
	public Book getByAuthor(String author) {
        sql = "SELECT * FROM books WHERE author = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { author }, new BookRowMapper());
	}

	private void update(Book book) {
		sql = "UPDATE books SET title=?, isbn=?, author=?, publisher=?, edition=?, year_of_publication=?"
				+ " WHERE book_id = ?";

		jdbcTemplate.update(sql, book.getTitle(), book.getIsbn(), book.getAuthor(), book.getPublisher(),
                book.getEdition(), book.getYearOfPublication(),
                book.getBookID());
	}

	private void add(Book book) {
		sql = "INSERT into books (title, isbn, author, publisher, edition, year_of_publication)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql, book.getTitle(), book.getIsbn(), book.getAuthor(), book.getPublisher(),
                book.getEdition(), book.getYearOfPublication());
	}

	@Override
	public List<Book> findBooksLoanedByMembers() {
		sql = "SELECT b.book_id, b.title, m.member_id, m.name, m.fines_outstanding "
                + "from books b, members m, loan l WHERE b.book_id = l.book_id and m.member_id = l.member_id";

		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Book>>() {
			@Override
			public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Member> members = new ArrayList<>();
				List<Book> books = new ArrayList<>();
				while (rs.next()) {
                    Book book = new Book();
                    book.setBookID(rs.getInt("book_id"));
					book.setTitle(rs.getString("title"));
					books.add(book);

                    Member member = MemberFactory.createMember("name", new Date());
					member.setMemberID(rs.getInt("member_id"));
					member.setName(rs.getString("name"));
					members.add(member);

                    book.setMembers(members);
                }
				return books;
			}
		});
	}

    @Override
    public List<Book> searchForBooksOnLoan(String author, String title) {
        sql = "SELECT b.book_id, b.author, b.title, m.member_id, m.name, l.loan_date " +
                "FROM books b, members m, loan l " +
                "WHERE b.book_id = l.book_id " +
                "AND m.member_id = l.member_id " +
                "AND b.author = ? " +
                "UNION " +
                "SELECT b.book_id, b.author, b.title, m.member_id, m.name, l.loan_date " +
                "FROM books b, members m, loan l " +
                "WHERE b.book_id = l.book_id " +
                "AND m.member_id = l.member_id " +
                "AND b.title = ?";

        return jdbcTemplate.query(sql, new ResultSetExtractor<List<Book>>() {
            @Override
            public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Member> members = new ArrayList<>();
                List<Book> books = new ArrayList<>();
                List<Loan> loans = new ArrayList<>();

                Member member;
                Loan loan;

                while (rs.next()) {
                    Book book = new Book();
                    book.setBookID(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));

                    member = MemberFactory.createMember("name", new Date());
                    member.setMemberID(rs.getInt("member_id"));
                    member.setName(rs.getString("name"));
                    members.add(member);

                    loan = new Loan();
                    loan.setBookId(rs.getInt("book_id"));
                    loan.setMemberId(rs.getInt("member_id"));
                    loan.setLoanDate(rs.getDate("loan_date"));
                    loans.add(loan);
                    member.setLoans(loans);

                    book.setMembers(members);
                    books.add(book);
                }

                return books;
            }
        }, author, title);
    }

    @Override
    public List<Book> searchBooks_NotOnLoan(String title, String author) {
        sql = "SELECT b.book_id, b.author, b.title, m.member_id " +
                "FROM books b, members m " +
                "WHERE NOT EXISTS(Select * FROM loan l WHERE b.book_id = l.book_id) AND title=? " +
                "UNION " +
                "SELECT b.book_id, b.author, b.title, m.member_id " +
                "FROM books b, members m " +
                "WHERE NOT EXISTS(Select * FROM loan l WHERE b.book_id = l.book_id) AND author=?";

        return jdbcTemplate.query(sql, new ResultSetExtractor<List<Book>>() {
            @Override
            public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Book> books = new ArrayList<>();

                while (rs.next()) {
                    Book book = new Book();
                    book.setBookID(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    books.add(book);
                }
                return books;
            }
        }, title, author);
    }

    @Override
    public List<Book> searchBooksUnavailable(int memberID, int bookID) {
        sql = "SELECT b.book_id, b.author, b.title, m.member_id, m.name, m.fines_outstanding " +
                "FROM books b, members m, loan l " +
                "WHERE b.book_id = l.book_id " +
                "AND m.member_id = l.member_id " +
                "AND m.member_id  = ? " +
                "UNION " +
                "SELECT b.book_id, b.author, b.title, m.member_id, m.name, m.fines_outstanding " +
                "FROM books b, members m, loan l " +
                "WHERE b.book_id = l.book_id " +
                "AND m.member_id = l.member_id " +
                "AND b.book_id = ?";

        return jdbcTemplate.query(sql, new ResultSetExtractor<List<Book>>() {
            @Override
            public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Member> members = new ArrayList<>();
                List<Book> books = new ArrayList<>();
                List<Loan> loans = new ArrayList<>();

                Member member;
                Loan loan;

                while (rs.next()) {
                    Book book = new Book();
                    book.setBookID(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));

                    member = MemberFactory.createMember("name", new Date());
                    member.setMemberID(rs.getInt("member_id"));
                    member.setName(rs.getString("name"));
                    member.setFinesOutstanding(rs.getDouble("fines_outstanding"));
                    members.add(member);

                    loan = new Loan();
                    loan.setBookId(rs.getInt("book_id"));
                    loan.setMemberId(rs.getInt("member_id"));
                    loans.add(loan);
                    member.setLoans(loans);

                    book.setMembers(members);
                    books.add(book);
                }

                return books;
            }
        }, memberID, bookID);
    }

}
