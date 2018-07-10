package ie.soft8020.librarymanagement.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ie.soft8020.librarymanagement.LibraryManagementApplication;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.domain.MemberFactory;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LibraryManagementApplication.class, webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@Rollback
public class MemberRepositoryImplTest {

	@Autowired
	IMemberRepository repo;
	
	private Date dateOfBirth = new Date();
	private String name = "name";
	
	private static int DEFAULT_DB_SIZE = 7;
	private static int ID = 2;
	
	@Test
	public void testGet() {
		Member member = repo.getById(ID);
		assertThat(member.getName(), equalTo("John Smyth"));
	}
	
	@Test
	public void testFindAll() {
		List<Member> members = repo.findAll();
		assertThat(members, hasSize(DEFAULT_DB_SIZE));
	}
	
	@Test
	@Transactional
	public void testRemove() {
		Member member = repo.getById(ID);
		repo.remove(member);
		List<Member> members = repo.findAll();
		assertThat(members, hasSize(DEFAULT_DB_SIZE - 1));
	}
	
	@Test
	@Transactional
	public void testSave() {
		List<Member> members = repo.findAll();
		assertThat(members, hasSize(DEFAULT_DB_SIZE));
		
		Member member = MemberFactory.createMember(name, dateOfBirth);
		member.setName("My New Book");
		
		repo.save(member);
		List<Member> newMembers = repo.findAll();
		assertThat(newMembers, hasSize(DEFAULT_DB_SIZE + 1));
	}
}
