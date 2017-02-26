package jsug.flaky.events.repository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import jsug.flaky.events.entity.Event;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventRepositoryTest {

	@Autowired
	TestEntityManager em;

	@Autowired
	EventRepository target;

	@Before
	public void setup() {
		LocalDateTime date = LocalDateTime.of(2017,1,1,10,10);
		for (int i=0; i<20; i++) {
			Event event = new Event();
			event.startDate = date;
			em.persist(event);
			
			date = date.plusDays(1);
		}
	}
	
	
	@Test
	public void testFindByStartDateBetween() throws Exception {
		Page<Event> result = target.findByStartDateBetween(LocalDateTime.of(2017,1,1,10,10), LocalDateTime.of(2017,1,15,10,10), new PageRequest(1, 10));
		
		assertThat(result.getContent().size(), is(5));
		assertThat(result.getSize(), is(10));
		
	}

}
