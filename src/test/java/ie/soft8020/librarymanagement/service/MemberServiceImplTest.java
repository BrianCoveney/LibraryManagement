package ie.soft8020.librarymanagement.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.repository.IMemberRepository;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


public class MemberServiceImplTest {
	private IMemberService memberService;
	private IMemberRepository repoMock;
	private Date sqlDate = new Date(Calendar.getInstance().getTime().getTime());
	
	@Before
	public void setUp() {
		repoMock = mock(IMemberRepository.class);
		
		Member memberOne = new Member();
		memberOne.setName("Mary Peterson");
		memberOne.setAddress("345 Rockestown Cork");
		memberOne.setDateOfBirth(sqlDate);
		memberOne.setLoanLimit(4);
		memberOne.setLoanLength(7);
		memberOne.setFinesOutstanding(7.5);
		
		Member memberTwo = new Member();
		memberTwo.setName("Kate Upton");
		
		
		List<Member> members = new ArrayList<>();
		members.add(memberOne);
		members.add(memberTwo);
		
		when(repoMock.get(1)).thenReturn(memberOne);
		when(repoMock.get(2)).thenReturn(memberTwo);
		
		when(repoMock.findAll()).thenReturn(members);
		
		memberService = new MemberServiceImpl(repoMock);
	}
	
	@Test
	public void testGet() {
		assertThat(memberService.get(1).getName(), equalTo("Mary Peterson"));
		assertThat(memberService.get(1).getAddress(), equalTo("345 Rockestown Cork"));
		assertThat(memberService.get(1).getDateOfBirth(), equalTo(sqlDate));
		assertThat(memberService.get(1).getLoanLimit(), equalTo(4));
		assertThat(memberService.get(1).getLoanLength(), equalTo(7));
		assertThat(memberService.get(1).getFinesOutstanding(), equalTo(7.5));
		
		assertThat(memberService.get(2).getName(), equalTo("Kate Upton"));
	}
	
	@Test
	public void testFindAll() {
		assertThat(memberService.findAll(), hasSize(2));
	}

}
