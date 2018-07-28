package ie.soft8020.librarymanagement.repository;

import ie.soft8020.librarymanagement.LibraryManagementApplication;
import ie.soft8020.librarymanagement.domain.*;
import ie.soft8020.librarymanagement.util.DateUtilility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LibraryManagementApplication.class, webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@Rollback
public class MemberRepositoryImplTest {

	@Autowired
	IMemberRepository repo;

	private Date dateOfBirth = new Date();
	private String name = "name";

	private int defaultDbSize;
	private static int MEMBER_ID = 2;


	@Before
	public void setUp() throws Exception {
		defaultDbSize = repo.findAll().size();
	}

	@Test
	public void testGet() {
		Member member = repo.getById(MEMBER_ID);
		assertThat(member.getName(), equalTo("John Smyth"));
	}

	@Test
	public void testFindAll() {
		List<Member> members = repo.findAll();
		assertThat(members, hasSize(defaultDbSize));
	}

	@Test
	@Transactional
	public void testRemove() {
		Member member = repo.getById(MEMBER_ID);
		repo.remove(member);
		List<Member> members = repo.findAll();
		assertThat(members, hasSize(defaultDbSize - 1));
	}

	@Test
	@Transactional
	public void testSave() {
		List<Member> members = repo.findAll();
		assertThat(members, hasSize(defaultDbSize));

		Member member = MemberFactory.createMember(name, dateOfBirth);
		member.setName("My New Book");

		repo.save(member);
		List<Member> newMembers = repo.findAll();
		assertThat(newMembers, hasSize(defaultDbSize + 1));
	}

	@Test
	public void testGetMemberByIdWithBooks_ForAdult() {
		Member member = repo.getMemberByIdWithBooks(4); // Member id equal to 4 is an adult in the hardcoded DB
		assertThat(member.getMemberID(), equalTo(4));
        assertThat(member.getClass(), equalTo(Adult.class));
        assertThat("Members current days over limit ", member.getDaysOverLimit(), equalTo(0));
        assertThat(member.getFinesOutstanding(), equalTo(0.0));

        assertThat("Validate member with an id of _4_ has 1 book" , member.getBooks(), hasSize(1));
        assertThat("Validate member with an id of _4_ has 1 loan" , member.getLoans(), hasSize(1));

        List<Loan> adultLoan = createListOfLoans("2018-06-01", "2018-06-16");
        member.setLoans(adultLoan);
//        member.updateFine(member);

        assertThat("Validate member with an id of _4_ has 1 book on loan",
                member.getBooks(), hasSize(1));

        assertThat(member.getDaysOnLoan(), equalTo(15));
        assertThat(member.getDaysOverLimit(), equalTo(1));
        assertThat(member.getFinesOutstanding(), equalTo(0.25));
	}


    @Test
    public void testGeMemberByIdWithBooks_ForChild() {
	    int memberId = 3;
        Member member = repo.getMemberByIdWithBooks(memberId);
        assertThat("Object is a Child ", member.getClass(), equalTo(Child.class));
        assertThat("Members current days over limit ", member.getDaysOverLimit(), equalTo(3));

        // Update members fine
        member.updateFine(member);
        assertThat(member.getFinesOutstanding(), equalTo(0.75)); // Current fine
        assertThat("Validate member with an id of _3_ has 1 book" , member.getBooks(), hasSize(1));

        // Create a new loan and update fine once more
        List<Loan> childLoan = createListOfLoans("2018-06-01", "2018-06-09");
        member.setLoans(childLoan);
        member.updateFine(member);

        assertThat("Validate member with an id of _1_ has 2 book on loan",
                member.getBooks(), hasSize(1));

        assertThat(member.getFinesOutstanding(), equalTo(1.0)); // Updated fine after new loan
    }


    private List<Loan> createListOfLoans(String dateOne, String dateTwo) {
        Date loanDate = DateUtilility.parseStringToDate(dateOne);
        Date returnDate = DateUtilility.parseStringToDate(dateTwo);

        // Create a loan objects with those dates
        Loan loanOne = new Loan();
        loanOne.setBookId(1);
        loanOne.setMemberId(1);
        loanOne.setLoanDate(loanDate);
        loanOne.setReturnDate(returnDate);
        Loan loanTwo = new Loan();
        loanTwo.setBookId(2);
        loanTwo.setMemberId(2);
        loanTwo.setLoanDate(loanDate);
        loanTwo.setReturnDate(returnDate);

        List<Loan> loans = new ArrayList<>();
        loans.add(loanOne);
        loans.add(loanTwo);
        return loans;
    }


}
