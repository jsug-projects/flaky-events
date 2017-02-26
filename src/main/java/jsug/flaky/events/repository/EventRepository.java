package jsug.flaky.events.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jsug.flaky.events.entity.Event;

public interface EventRepository extends PagingAndSortingRepository<Event, UUID> {

	//@RestResource(path = "startDateBetween", rel = "startDateBetween")
	Page<Event> findByStartDateBetween(
			@DateTimeFormat(iso=ISO.DATE_TIME) @Param("fromDate") LocalDateTime fromDate, 
			@DateTimeFormat(iso=ISO.DATE_TIME) @Param("toDate") LocalDateTime toDate,
			Pageable pageable);


	@RestResource(exported = false)
	Event save(Event atteventendee);
	@RestResource(exported = false)
	void delete(Event event);
	
	
	
}
