package ie.soft8020.librarymanagement.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ie.soft8020.librarymanagement.LibraryManagementApplication;
import ie.soft8020.librarymanagement.domain.Member;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LibraryManagementApplication.class, webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class IMemberRepositoryTest {

	@Autowired
	IMemberRepository repo;
	
	@Test
	public void testGet() {
		Member member = repo.get(1);
		assertThat(member.getName(), is("Brian Bloggs"));
	}
	
	@Test
	public void testFindAll() {
		List<Member> members = repo.findAll();
		assertThat(members, hasSize(5));
	}
	
	@Test
	public void testRemove() {
		Member member = repo.get(1);
		repo.remove(member);
		List<Member> members = repo.findAll();
		assertThat(members, hasSize(4));
	}
	
}
