package ie.soft8020.librarymanagement.rowmapper;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.domain.MemberFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MemberRowMapper implements RowMapper<Member> {

	@Override
	public Member mapRow(ResultSet rs, int arg1) throws SQLException {

		Date dateOfBirth = rs.getDate("date_of_birth");
		String name = "name";

		Member member = MemberFactory.createMember(name, dateOfBirth);
		member.setMemberID(rs.getInt("member_id"));
		member.setName(rs.getString("name"));
		member.setAddress(rs.getString("address"));
		member.setDateOfBirth(rs.getDate("date_of_birth"));
		member.setLoanLimit(rs.getInt("loan_limit"));
		member.setLoanLength(rs.getInt("loan_length"));
		member.setFinesOutstanding(rs.getDouble("fines_outstanding"));

		return member;
	}
}
