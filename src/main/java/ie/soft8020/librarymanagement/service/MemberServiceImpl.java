package ie.soft8020.librarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.repository.IMemberRepository;

@Service
public class MemberServiceImpl implements IMemberService {
	
	@Autowired
	IMemberRepository iMemberRepository;

	
	public MemberServiceImpl(IMemberRepository iMemberServiceRepository) {
		this.iMemberRepository = iMemberServiceRepository;
	}
	
	@Override
	public void save(Member member) {
		iMemberRepository.save(member);
	}

	@Override
	public Member get(int id) {
		return iMemberRepository.getById(id);
	}

	@Override
	public void remove(Member member) {
		iMemberRepository.remove(member);
	}

	@Override
	public List<Member> findAll() {
		return iMemberRepository.findAll();
	}

}
