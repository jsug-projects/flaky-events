package jsug.flaky.events.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import jsug.flaky.events.entity.Attendance;
import jsug.flaky.events.entity.Attendee;
import jsug.flaky.events.entity.Event;


@RunWith(SpringRunner.class)
@DataJpaTest
public class AttendeeRepositoryTest {

	@Autowired
	TestEntityManager em;

	@Autowired
	AttendeeRepository target;
	
	
	@Before
	public void setup() {
		eventFixture1 = new Event();
		em.persist(eventFixture1);
		eventFixture2 = new Event();
		em.persist(eventFixture2);
		
		memberIdFixture1 = UUID.fromString("18c2aaf0-a3b3-46e4-a0b9-e4dc237c8888");
		
	}

	
	UUID memberIdFixture1;
	//UUID memberIdFixture2;
	Event eventFixture1;
	Event eventFixture2;
	
	void createAttendee(int size, Event event) {
		for (int i=0; i<size; i++) {
			Attendee attendee = new Attendee();
			attendee.memberId = UUID.fromString(String.format("18c2aaf0-a3b3-46e4-a0b9-e4dc237c8%03d", i));
			attendee.comment = String.format("comment%03d", i);
			attendee.event = event;
			em.persist(attendee);			
		}		
	}
	
	void createAttendeeHalfFlaky(UUID memberId, int size) {
		LocalDateTime date = LocalDateTime.of(2017,1,1,10,10);
		for (int i=0; i<size; i++) {
			Event event = new Event();
			event.endDate = date;
			em.persist(event);
			Attendee attendee = new Attendee();
			attendee.memberId = memberId;
			attendee.event = event;
			em.persist(attendee);
			if (i%2==0) {
				Attendance attendance = new Attendance();
				attendance.attendee = attendee;
				em.persist(attendance);
			}
			date = date.plusDays(1);
		}
	}
	
	
	@Test
	public void testCountByEventId() throws Exception {
		createAttendee(10, eventFixture1);
		createAttendee(5, eventFixture2);
		long result = target.countByEventId(eventFixture1.id);
		assertThat(result, is(10L));
		
	}

	@Test
	public void testCountFlakyOccurrenceByMemberId() throws Exception {
		createAttendeeHalfFlaky(memberIdFixture1, 10);
		long result = target.countFlakyOccurrenceByMemberId(memberIdFixture1, LocalDateTime.of(2017,1,1,10,10), LocalDateTime.of(2017,1,5,10,10));
		assertThat(result, is(2L));
	}

	@Test
	public void testFindByEventIdAndMemberId() throws Exception {
		Attendee attendee = new Attendee();
		attendee.memberId = memberIdFixture1;
		attendee.event = eventFixture1;
		attendee.comment = "comment001";
		em.persist(attendee);

		Attendee result = target.findByEventIdAndMemberId(eventFixture1.id, memberIdFixture1);
		assertThat(result.comment, is("comment001"));
	}

}
