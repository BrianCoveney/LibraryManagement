package ie.soft8020.librarymanagement.repository;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.domain.MemberFactory;
import ie.soft8020.librarymanagement.rowmapper.MemberRowMapper;
import ie.soft8020.librarymanagement.util.DateUtilility;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MemberRepositoryImpl implements IMemberRepository {

	private JdbcTemplate jdbcTemplate;
	private String sql;

	@Autowired
	public MemberRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Member getById(int id) {
		sql = "SELECT * FROM members WHERE member_id = ?";
		Member member = jdbcTemplate.queryForObject(sql, new Object[] { id },
				new MemberRowMapper());
		return member;
	}

	@Override
	public void save(Member member) {
		if (member.getMemberID() != 0) {
			update(member);
		} else {
			add(member);
		}
	}

	@Override
	public void remove(Member member) {
		sql = "DELETE FROM members WHERE member_id = ?";
		jdbcTemplate.update(sql, new Object[] { member.getMemberID() } );
	}

	@Override
	public List<Member> findAll() {
		sql = "SELECT * from members";
		List<Member> members = jdbcTemplate.query(sql, new MemberRowMapper());
		return members;
	}

	@Override
	public Member getMemberWithBooks(int id) {
		sql = "SELECT m.member_id, m.name, m.address, m.date_of_birth, m.fines_outstanding, " +
				"b.book_id, b.title, b.isbn, b.author, b.publisher, b.edition, b.year_of_publication " +
				"FROM members m, books b, loan l " +
				"WHERE b.book_id = l.book_id AND m.member_id = l.member_id " +
				"AND m.member_id = ?";

		Member member = jdbcTemplate.query(sql, new Object[] { id },
				new ResultSetExtractor<Member>() {

					@Override
					public Member extractData(ResultSet rs) throws SQLException, DataAccessException {
						Member member = null;
						List<Book> books = new ArrayList<>();
						Date date = DateUtilility.parseStringToDate("1998-03-01");

						while (rs.next()) {
							if (member == null) {
								member = MemberFactory.createMember("name", date);
								member.setMemberID(rs.getInt("member_id"));
								member.setName(rs.getString("name"));
								member.setAddress(rs.getString("address"));
								member.setDateOfBirth(rs.getDate("date_of_birth"));
								member.setFinesOutstanding(rs.getDouble("fines_outstanding"));
							}
							Book book = new Book();
							book.setBookID(rs.getInt("book_id"));
							book.setTitle(rs.getString("title"));
							book.setIsbn(rs.getString("isbn"));
							book.setAuthor(rs.getString("author"));
							book.setPublisher(rs.getString("publisher"));
							book.setEdition(rs.getString("edition"));
							book.setYearOfPublication(rs.getDate("year_of_publication"));
							books.add(book);
						}
						member.setBooks(books);
						return member;
					}
				});
		return member;
	}

	private void update(Member member) {
		sql = "UPDATE members SET name=?, address=?, date_of_birth=?, loan_limit=?, loan_length=?, fines_outstanding=?"
				+ " WHERE member_id = ?";
		jdbcTemplate.update(sql,
				new Object[] { member.getName(), member.getAddress(), member.getDateOfBirth(), member.getLoanLimit(),
						member.getLoanLength(), member.getFinesOutstanding(),
						member.getMemberID()});
	}

	private void add(Member member) {
		sql = "INSERT INTO members (name, address, date_of_birth, loan_limit, loan_length, fines_outstanding)"
				+ " VALUES(?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql,
				new Object[] {  member.getName(), member.getAddress(),  member.getDateOfBirth(), member.getLoanLimit(),
						member.getLoanLength(), member.getFinesOutstanding()});
	}

}
