package ie.soft8020.librarymanagement.service;

import ie.soft8020.librarymanagement.domain.*;
import ie.soft8020.librarymanagement.repository.IMemberRepository;
import ie.soft8020.librarymanagement.util.DateUtilility;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MemberServiceImplTest {
	private IMemberService memberService;
	private IMemberRepository repoMock;
	private Date adultDateOfBirth = DateUtilility.parseStringToDate("1998-03-01");
	private Date childDateOfBirth = DateUtilility.parseStringToDate("2010-07-11");
	private Book bookOne;
	private Book bookTwo;

	@Before
	public void setUp() {
		repoMock = mock(IMemberRepository.class);

		Member memberOne = MemberFactory.createMember("Mary Peterson", adultDateOfBirth);
		memberOne.setAddress("345 Rockestown Cork");
		memberOne.setLoanLimit(4);
		memberOne.setLoanLength(7);
		memberOne.setFinesOutstanding(7.5);

		System.out.println(memberOne.getFinesOutstanding());

		Member memberTwo = MemberFactory.createMember("Kate Upton", childDateOfBirth);
		memberTwo.setBooks(createBookListForTest());

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
		assertThat(memberService.get(2).getBooks(), equalTo(Arrays.asList(bookOne, bookTwo)));
	}

	@Test
	public void testFindAll() {
		assertThat(memberService.findAll(), hasSize(2));
	}

	private List<Book> createBookListForTest() {
		bookOne = new Book();
		bookOne.setTitle("Imperium");
		bookTwo = new Book();
		bookTwo.setTitle("Dictator");
		List<Book> books = new ArrayList<>();
		books.add(bookOne);
		books.add(bookTwo);
		return books;
	}
}
