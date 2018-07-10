package ie.soft8020.librarymanagement.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ie.soft8020.librarymanagement.domain.Adult;
import ie.soft8020.librarymanagement.domain.Child;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.domain.MemberFactory;
import ie.soft8020.librarymanagement.repository.IMemberRepository;
import ie.soft8020.librarymanagement.util.DateUtil;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


public class MemberServiceImplTest {
	private IMemberService memberService;
	private IMemberRepository repoMock;
	private Date adultDateOfBirth = DateUtil.parseStringToDate("1998-03-01");
	private Date childDateOfBirth = DateUtil.parseStringToDate("2010-07-11");
	
	@Before
	public void setUp() {
		repoMock = mock(IMemberRepository.class);
		
		Member memberOne = MemberFactory.createMember("Mary Peterson", adultDateOfBirth);
		memberOne.setAddress("345 Rockestown Cork");
		memberOne.setLoanLimit(4);
		memberOne.setLoanLength(7);
		memberOne.setFinesOutstanding(7.5);
		
		Member memberTwo = MemberFactory.createMember("Kate Upton", childDateOfBirth);
		
		List<Member> members = new ArrayList<>();
		members.add(memberOne);
		members.add(memberTwo);
		
		when(repoMock.getById(1)).thenReturn(memberOne);
		when(repoMock.getById(2)).thenReturn(memberTwo);
		
		when(repoMock.findAll()).thenReturn(members);
		
		memberService = new MemberServiceImpl(repoMock);
	}
	
	@Test
	public void testGet() {
		assertThat(memberService.get(1), instanceOf(Adult.class));
		assertThat(memberService.get(1).getName(), equalTo("Mary Peterson"));
		assertThat(memberService.get(1).getAddress(), equalTo("345 Rockestown Cork"));
		assertThat(memberService.get(1).getDateOfBirth(), equalTo(adultDateOfBirth));
		assertThat(memberService.get(1).getLoanLimit(), equalTo(4));
		assertThat(memberService.get(1).getLoanLength(), equalTo(7));
		assertThat(memberService.get(1).getFinesOutstanding(), equalTo(7.5));
		
		assertThat(memberService.get(2), instanceOf(Child.class));
		assertThat(memberService.get(2).getName(), equalTo("Kate Upton"));
		assertThat(memberService.get(2).getDateOfBirth(), equalTo(childDateOfBirth));
	}
	
	@Test
	public void testFindAll() {
		assertThat(memberService.findAll(), hasSize(2));
	}

}
