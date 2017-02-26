package jsug.flaky.events;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import jsug.flaky.events.entity.Attendance;
import jsug.flaky.events.entity.Attendee;
import jsug.flaky.events.entity.Event;
import jsug.flaky.events.entity.Session;
import jsug.flaky.events.entity.Speaker;
import jsug.flaky.events.repository.AttendanceRepository;
import jsug.flaky.events.repository.AttendeeRepository;
import jsug.flaky.events.repository.EventRepository;
import jsug.flaky.events.repository.SessionRepository;
import jsug.flaky.events.repository.SpeakerRepository;

public class DummyDataCreator {
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	SessionRepository sessionRepository;

	@Autowired
	SpeakerRepository speakerRepository;
	
	@Autowired
	AttendeeRepository attendeeRepository;
		
	@Autowired
	AttendanceRepository attendanceRepository;
	
	
	public List<Event> createdEvents; 

	public void createDummyData() {
		createdEvents = createEvent(20);
	}
	
	
	List<Event> createEvent(int size) {
		List<Event> result = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Event e = new Event();
			e.eventName = "イベント" + i;
			e.eventPlace = "六本木" + i;
			e.startDate = LocalDateTime.of(2017, 1, i + 1, 0, 0);
			e.endDate = LocalDateTime.of(2017, 1, i + 1, 0, 0);
			e.lotteryDate = LocalDate.now();
			e.attendeeMaxSize = 100;
			eventRepository.save(e);

			createSession(e, 2);

			createAttendee(e, 20);
			
			result.add(e);
		}
		return result;
	}

	void createAttendee(Event e, int size) {
		for (int i=0; i<size; i++) {
			Attendee attendee = new Attendee();
			attendee.comment = "よろしくお願いします"+i;
			attendee.memberId = UUID.fromString(String.format("07bc1726-cdae-4cbf-bcbb-908023619%03d", i));
			attendee.event = e;
			attendeeRepository.save(attendee);
			
			if (i%2 == 0) {
				Attendance attendance = new Attendance();
				attendance.attendee = attendee;
				attendanceRepository.save(attendance);
				attendee.attendance = attendance;
			}
			
		}
		
	}

	void createSession(Event event, int size) {
		for (int i = 0; i < size; i++) {
			Session session = new Session();
			session.title = "タイトル" + i;
			session.summary = "概要" + i;
			session.seatSize = 50;
			session.event = event;
			sessionRepository.save(session);
			
			createSpeker(session, 1);
		}

	}

	void createSpeker(Session session, int size) {
		for (int i=0; i<size; i++) {
			Speaker speaker = new Speaker();
			speaker.session = session;
			speaker.briefHistory = "略歴"+i;
			speakerRepository.save(speaker);
		}
		
		
	}

}
