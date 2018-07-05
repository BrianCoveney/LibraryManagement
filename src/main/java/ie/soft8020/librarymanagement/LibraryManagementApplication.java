package ie.soft8020.librarymanagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.repository.IBookRepository;
import ie.soft8020.librarymanagement.repository.IMemberRepository;
import ie.soft8020.librarymanagement.rowmapper.BookRowMapper;
import ie.soft8020.librarymanagement.rowmapper.MemberRowMapper;
import ie.soft8020.librarymanagement.service.IBookService;
import ie.soft8020.librarymanagement.service.IMemberService;

@SpringBootApplication
public class LibraryManagementApplication implements CommandLineRunner	 {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	IMemberRepository iMemberRepository;
	
	@Autowired
	IBookRepository iBookRepository;
	
	@Autowired
	IMemberService iMemberService;
	
	@Autowired
	IBookService iBookService;
	
	private String sql;
	
	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		queryForObjects();
		queryForObject();
		queryForAggregate();
		queryForJoin1();
		queryForJoin2();
		queryResultSetExtractor();
		repositoryExample();
		serviceExample();
		repositoryBookExample();
		serviceBookExample();
	}
	
	public void query01() {
		System.out.println("\nQuery 1 \n----------");
		
		sql = "SELECT * FROM books";
		List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(sql);
		
		for (Map<String, Object> row : resultSet) {
			System.out.println("book_id: " + row.get("book_id"));
			System.out.println("title: " + row.get("title"));
			System.out.println("isbn: " + row.get("isbn"));
			System.out.println("author: " + row.get("author"));
			System.out.println("publisher: " + row.get("publisher"));
			System.out.println("edition: " + row.get("edition"));
			System.out.println("Year of publication: " + row.get("year_of_publication"));
		}
	}
	
	public void queryForObjects() {
		System.out.println("\nQuery 2 \n----------");
		
		sql = "SELECT * FROM books WHERE edition = ? AND author = ?";
		List<Book> books = jdbcTemplate.query(sql, new Object[] { "Third edition", "Joshua Bloch" }, 
				new BookRowMapper());
		
		for (Book book : books) {
			System.out.println(book.toString());
		}
	}
	
	public void queryForObject() {
		System.out.println("\nQuery 3 \n----------");
		
		sql = "SELECT * FROM books WHERE book_id = ?"; 
		Book book = jdbcTemplate.queryForObject(sql,  new Object[] { 1 }, new BookRowMapper());
		
		System.out.println(book.toString());
	}
	
	public void queryForAggregate() {
		System.out.println("\nQuery 4 \n----------");
		
		
		sql = "SELECT COUNT(*) FROM books";
		int bookCount = jdbcTemplate.queryForObject(sql, Integer.class);
		System.out.printf("Number of books: %d\n", bookCount);
		
		sql = "SELECT author, title FROM books WHERE book_id = 1";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		System.out.printf("Author: %s, Title: %s\n", map.get("author"),
				map.get("title"));
	}
	
	public void queryForJoin1() {
		System.out.println("\nQuery 5 \n----------");
		
		sql = "SELECT * FROM members WHERE member_id = ?";
		Member member = jdbcTemplate.queryForObject(sql, new Object[] { 1 },
				new MemberRowMapper());
		
		sql = "SELECT b.* FROM books b, loan l WHERE b.book_id = l.book_id AND l.member_id = ?";
		
		List<Book> books = jdbcTemplate.query(sql, new Object[] { 1 }, 
				new BookRowMapper());
		
		member.setBooks(books);
		
		System.out.println(member.toString());
	}
		
	public void queryForJoin2() {
		System.out.println("\nQuery 6 \n----------");
		
		sql = "SELECT * FROM books WHERE book_id = ?";
		Book book = jdbcTemplate.queryForObject(sql, new Object[] { 1 },
				new BookRowMapper());
		
		sql = "SELECT m.* FROM members m, loan l WHERE m.member_id = l.member_id AND l.book_id = ?";
		
		List<Member> members = jdbcTemplate.query(sql, new Object[] { 1 }, 
				new MemberRowMapper());
		
		book.setMembers(members);
		
		System.out.println(book.toString());
	}
	
	public void queryResultSetExtractor() {
		System.out.println("\nQuery 7 \n----------");
		
		sql = "SELECT m.member_id, m.name, m.address, m.date_of_birth, b.book_id, b.title "
				+ "FROM members m, books b, loan l "
				+ "WHERE b.book_id = l.book_id AND m.member_id = l.member_id "
				+ "AND l.member_id = ?";
		
		Member member = jdbcTemplate.query(sql, new Object[] { 1 },
				new ResultSetExtractor<Member>() {

					@Override
					public Member extractData(ResultSet rs) throws SQLException, DataAccessException { 
						Member member = null;
						List<Book> books = new ArrayList<>();
						
						while (rs.next()) {
							if(member == null) {
								member = new Member();
								member.setMemberID(rs.getInt("member_id"));
								member.setName(rs.getString("name"));
								member.setAddress(rs.getString("address"));
								member.setDateOfBirth(rs.getDate("date_of_birth"));
							}
							Book book = new Book();
							book.setBookID(rs.getInt("book_id"));
							book.setTitle(rs.getString("title"));
							
							books.add(book);
						}
						
						member.setBooks(books);
						return member;
					}
		});
					
		System.out.println(member.toString());
	}
	
	public void repositoryExample() {
		System.out.println("\nRepository Member Example \n----------");
		
		Member member = iMemberRepository.get(2);
		System.out.println(member.toString());
		
		member.setLoanLength(7);
		member.setFinesOutstanding(6.5);
		iMemberRepository.save(member);
		System.out.println(member.toString());
		
		System.out.println("All members:");
		List<Member> members = iMemberRepository.findAll();
		for (Member m : members) {
			System.out.println(m.toString());
		}
	}
	
	public void serviceExample() {
		System.out.println("\nService Member Example \n----------");
		
		Member member = iMemberService.get(2);
		member.setName("Jack Jones");
		iMemberService.save(member);
		System.out.println("Updated member via service:\n " + member.toString());
	}
	
	public void repositoryBookExample() {
		System.out.println("\nRepository Book Example \n----------");
		
		Book book = iBookRepository.get(2);
		System.out.println(book.toString());
		
		book.setTitle("Fective Java");
		iBookRepository.save(book);
		System.out.println(book.toString());
		
		System.out.println("All books: \n");
		List<Book> books = iBookRepository.findAll();
		for (Book b : books) {
			System.out.println(b.toString());
		}	
	}
	
	public void serviceBookExample() {
		System.out.println("\nService Book Example \n----------");
		
		Book book = iBookService.get(2);
		book.setTitle("Effective Java");
		iBookService.save(book);
		System.out.println("Updated book via service:\n " + book.toString());
	}
}

