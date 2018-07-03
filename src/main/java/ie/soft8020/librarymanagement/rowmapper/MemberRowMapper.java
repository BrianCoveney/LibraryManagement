package ie.soft8020.librarymanagement.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ie.soft8020.librarymanagement.domain.Member;

public class MemberRowMapper implements RowMapper<Member> {

	@Override
	public Member mapRow(ResultSet rs, int arg1) throws SQLException {
		Member member = new Member();
		
		member.setMemberID(rs.getInt("member_id"));
		member.setName(rs.getString("name"));
		member.setAddress(rs.getString("address"));
		member.setDateOfBirth(rs.getDate("date_of_birth"));
		member.setLoanLimit(rs.getInt("loan_limit"));
		member.setLoanLength(rs.getInt("loan_length"));
		member.setFinesOutstanding(rs.getInt("fines_outstanding"));
		
		return member;
	}
}
