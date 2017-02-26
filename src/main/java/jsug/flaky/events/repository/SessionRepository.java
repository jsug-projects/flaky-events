package jsug.flaky.events.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import jsug.flaky.events.entity.Event;
import jsug.flaky.events.entity.Session;

public interface SessionRepository extends PagingAndSortingRepository<Session, UUID>{

	List<Session> findByEventId(@Param("eventId") UUID eventId);

	
	@RestResource(exported = false)
	Session save(Session attendee);
	@RestResource(exported = false)
	void delete(Session attendee);

}
