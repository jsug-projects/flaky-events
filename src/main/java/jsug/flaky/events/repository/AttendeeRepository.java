package jsug.flaky.events.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import jsug.flaky.events.entity.Attendee;

public interface AttendeeRepository extends PagingAndSortingRepository<Attendee, UUID>{

	@RestResource(path = "countByEventId", rel = "countByEventId")
	Long countByEventId(@Param("eventId") UUID eventId);

	
	
}
