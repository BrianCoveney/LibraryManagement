package ie.soft8020.librarymanagement.repository;

import java.util.List;

import ie.soft8020.librarymanagement.domain.Member;

public interface IMemberRepository {
	Member getById(int id);
	void save(Member member);
	void remove(Member member);
	List<Member> findAll();
}
