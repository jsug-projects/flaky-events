package jsug.flaky.events;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jsug.flaky.events.entity.Attendee;
import jsug.flaky.events.entity.Event;
import jsug.flaky.events.entity.Session;
import jsug.flaky.events.entity.Speaker;
import jsug.flaky.events.repository.AttendeeRepository;
import jsug.flaky.events.repository.EventRepository;
import jsug.flaky.events.repository.SessionRepository;
import jsug.flaky.events.repository.SpeakerRepository;

@SpringBootApplication
@EntityScan(basePackageClasses = { FlakyEventsApplication.class, Jsr310JpaConverters.class })
public class FlakyEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlakyEventsApplication.class, args);
	}

	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	SessionRepository sessionRepository;

	@Autowired
	SpeakerRepository speakerRepository;
	
	@Autowired
	AttendeeRepository attendeeRepository;
	
	@Bean
	public CommandLineRunner commandLineRunner() {

		return new CommandLineRunner() {
			@Override
			public void run(String... arg0) throws Exception {
				createEvent(eventRepository, 10);

			}

			private void createEvent(EventRepository repo, int size) {
				for (int i = 0; i < size; i++) {
					Event e = new Event();
					e.eventName = "イベント" + i;
					e.eventPlace = "六本木" + i;
					e.startDate = LocalDateTime.of(2017, 1, i + 1, 0, 0);
					e.endDate = LocalDateTime.of(2017, 1, i + 1, 0, 0);
					e.lotteryDate = LocalDate.now();
					repo.save(e);

					createSession(e, 2);

					createAttendee(e, 20);
					
				}
			}

			private void createAttendee(Event e, int size) {
				for (int i=0; i<size; i++) {
					Attendee attendee = new Attendee();
					attendee.comment = "よろしくお願いします"+i;
					attendee.event = e;
					attendeeRepository.save(attendee);
				}
				
			}

			private void createSession(Event event, int size) {
				for (int i = 0; i < size; i++) {
					Session session = new Session();
					session.title = "タイトル" + i;
					session.summary = "概要" + i;
					session.event = event;
					sessionRepository.save(session);
					
					createSpeker(session, 1);
				}

			}

			private void createSpeker(Session session, int size) {
				for (int i=0; i<size; i++) {
					Speaker speaker = new Speaker();
					speaker.session = session;
					speaker.briefHistory = "略歴"+i;
					speakerRepository.save(speaker);
				}
				
				
			}

		};
	}
}



