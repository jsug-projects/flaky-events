package jsug.flaky.events.handler;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.concurrent.SuccessCallback;

import jsug.flaky.events.entity.Attendee;
import jsug.flaky.events.entity.Event;
import jsug.flaky.events.repository.AttendeeRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


public class AttendeeEventHandlerTest {

	@Tested
	AttendeeEventHandler target;
	
	@Injectable 
	AttendeeRepository attendeeRepositoryMock;
	
	@Test
	public void testHandleBeforeSave() throws Exception {
		UUID a = UUID.fromString("07bc1726-cdae-4cbf-bcbb-908023619000");
		UUID b = UUID.fromString("07bc1726-cdae-4cbf-bcbb-908023619001");
		
		new Expectations() {{
			attendeeRepositoryMock.findByEventIdAndMemberId(a, b);
			result = null;
		}};
		
		Attendee attendee = new Attendee();
		attendee.event = new Event();
		attendee.event.id = a;
		attendee.memberId = b;
		target.handleBeforeCreate(attendee);
		
	}


	@Test
	public void testHandleBeforeSaveFail() throws Exception {
		UUID a = UUID.fromString("07bc1726-cdae-4cbf-bcbb-908023619000");
		UUID b = UUID.fromString("07bc1726-cdae-4cbf-bcbb-908023619001");
		
		new Expectations() {{
			attendeeRepositoryMock.findByEventIdAndMemberId(a, b);
			result = new Attendee();
		}};
		
		Attendee attendee = new Attendee();
		attendee.event = new Event();
		attendee.event.id = a;
		attendee.memberId = b;
		try {
			target.handleBeforeCreate(attendee);
			fail();
		} catch (DuplicateKeyException expedted) {}
		
		attendee = new Attendee();
		attendee.memberId = b;
		try {
			target.handleBeforeCreate(attendee);
			fail();
		} catch (IllegalArgumentException expedted) {}
		
		
	}

}
