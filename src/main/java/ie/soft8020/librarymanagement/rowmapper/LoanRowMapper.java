package ie.soft8020.librarymanagement.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ie.soft8020.librarymanagement.domain.Loan;

public class LoanRowMapper implements RowMapper<Loan> {

	@Override
	public Loan mapRow(ResultSet rs, int rowNum) throws SQLException {
		Loan loan = new Loan();
		loan.setBookId(rs.getInt("book_id"));
		loan.setMemberId(rs.getInt("member_id"));
		loan.setLoanDate(rs.getDate("loan_date"));
		loan.setReturnDate(rs.getDate("return_date"));
		
		return loan;
	}

}
