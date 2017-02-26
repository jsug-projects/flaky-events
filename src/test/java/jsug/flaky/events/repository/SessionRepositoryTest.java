package jsug.flaky.events.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import jsug.flaky.events.entity.Event;
import jsug.flaky.events.entity.Session;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SessionRepositoryTest {

	
	@Autowired
	TestEntityManager em;

	@Autowired
	SessionRepository target;
	
	Event eventFixture;
	
	@Before
	public void setup() {
		eventFixture = new Event();
		em.persist(eventFixture);
		for (int i=0; i<5; i++) {
			Session session = new Session();
			session.event = eventFixture;
			em.persist(session);
		}
	}
	
	
	@Test
	public void testFindByEventId() throws Exception {
		List<Session> result = target.findByEventId(eventFixture.id);
		assertThat(result.size(), is(5));
	}

}
