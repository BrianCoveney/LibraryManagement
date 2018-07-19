package ie.soft8020.librarymanagement.service;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.repository.IMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

	@Override
	public List<Member> findMembersWithFines() {

//        List<Member> members= iMemberRepository.findMembersWithFines();
//        for (Member member : members) {
//            double x = member.getTotalDaysOverLoanLimit();
//            member.setFinesOutstanding(x);
//        }


		return iMemberRepository.findMembersWithFines();
	}

	@Override
	public Member getMemberWithBooks(int id) {
		return iMemberRepository.getMemberWithBooks(id);
	}
}
