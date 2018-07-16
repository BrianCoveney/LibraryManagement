package ie.soft8020.librarymanagement.service;

import ie.soft8020.librarymanagement.domain.Member;

import java.util.List;

public interface IMemberService {
	void save(Member member);
	Member get(int id);
	Member getMemberWithBooks(int id);
	void remove(Member member);
	List<Member> findAll();
}
