package ie.soft8020.librarymanagement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.rowmapper.BookRowMapper;
import ie.soft8020.librarymanagement.rowmapper.MemberRowMapper;

@SpringBootApplication
public class LibraryManagementApplication implements CommandLineRunner	 {

	@Autowired
	JdbcTemplate jdbcTemplate;
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
		
		book.setMambers(members);
		
		System.out.println(book.toString());
	}
}
	
