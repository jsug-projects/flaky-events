package jsug.flaky.events.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jsug.flaky.events.entity.Attendee;

public interface AttendeeRepository extends PagingAndSortingRepository<Attendee, UUID>{

	@RestResource(exported = false)
	void delete(Attendee attendee);
	
	
	
	Long countByEventId(@Param("eventId") UUID eventId);


	@Query("select count(a) from Attendee a "
			+ "left join a.attendance "
			+ "where a.memberId=:memberId and a.attendance.id is null and "
			+ "a.event.endDate between :fromDate and :toDate")
	Long countFlakyOccurrenceByMemberId(@Param("memberId") UUID memberId, 
			@DateTimeFormat(iso=ISO.DATE_TIME) @Param("fromDate") LocalDateTime fromDate, 
			@DateTimeFormat(iso=ISO.DATE_TIME) @Param("toDate") LocalDateTime toDate
			);
	
	//use for duplicate check
	Attendee findByEventIdAndMemberId(UUID eventId, UUID memnerId);
	
	
	
}
