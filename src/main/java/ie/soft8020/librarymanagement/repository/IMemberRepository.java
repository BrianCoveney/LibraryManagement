package ie.soft8020.librarymanagement.repository;

import ie.soft8020.librarymanagement.domain.Member;

import java.util.List;

public interface IMemberRepository {
	Member getById(int id);
	void save(Member member);
	void remove(Member member);
	List<Member> findAll();
	Member getMemberByIdWithBooks(int id);
	List<Member> findMembersWithFines();
}
