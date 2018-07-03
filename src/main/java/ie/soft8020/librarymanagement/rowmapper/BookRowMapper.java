package ie.soft8020.librarymanagement.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ie.soft8020.librarymanagement.domain.Book;

public class BookRowMapper implements RowMapper<Book> {
	
	@Override
	public Book mapRow(ResultSet rs, int arg1) throws SQLException {

		Book book = new Book();
		
		book.setBookID(rs.getInt("book_id"));
		book.setTitle(rs.getString("title"));
		book.setIsbn(rs.getString("isbn"));
		book.setAuthor(rs.getString("author"));
		book.setPublisher(rs.getString("publisher"));
		book.setEdition(rs.getString("edition"));
		book.setYearOfPublication(rs.getDate("year_of_publication"));
		
		return book;
	}

}
