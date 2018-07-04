package ie.soft8020.librarymanagement.service;

import java.util.List;

import ie.soft8020.librarymanagement.domain.Member;

public interface IMemberService {
	void save(Member member);
	Member get(int id);
	void remove(Member member);
	List<Member> findAll();	
}
