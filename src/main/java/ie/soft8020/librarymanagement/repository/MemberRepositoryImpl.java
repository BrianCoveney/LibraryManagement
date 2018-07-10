package ie.soft8020.librarymanagement.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.rowmapper.MemberRowMapper;

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
