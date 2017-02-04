package jsug.flaky.events.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import jsug.flaky.events.entity.Event;

public interface EventRepository extends PagingAndSortingRepository<Event, UUID>{

	@RestResource(path = "startDateBetween", rel = "startDateBetween")
	List<Event> findByStartDateBetween(
			@DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) @Param("fromDate") LocalDateTime startDate, 
			@DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) @Param("toDate") LocalDateTime endDate,
			Pageable pageable);

	
	
	
	
}
