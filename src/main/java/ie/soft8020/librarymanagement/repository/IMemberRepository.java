package ie.soft8020.librarymanagement.repository;

import java.util.List;

import ie.soft8020.librarymanagement.domain.Member;

public interface IMemberRepository {
	
	public Member get(int id);
	public void save(Member member);
	public void remove(Member member);
	public List<Member> findAll();

}
