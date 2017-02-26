package jsug.flaky.events.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import jsug.flaky.events.entity.Attendee;
import jsug.flaky.events.repository.AttendeeRepository;

@Component
@RepositoryEventHandler(Attendee.class)
public class AttendeeEventHandler {
	
	@Autowired
	AttendeeRepository attendeeRepository;
	
	@HandleBeforeCreate
	public void handleBeforeCreate(Attendee input) {

		if (input.event == null) {
			throw new IllegalArgumentException("event must not be null.");
		}
		
		Attendee attendee = attendeeRepository.findByEventIdAndMemberId(input.event.id, input.memberId);
		if (attendee != null) {
			throw new DuplicateKeyException("attendee exists eventId="+input.event.id+" memberId="+input.memberId);
		}
		
		
	}
	

}
