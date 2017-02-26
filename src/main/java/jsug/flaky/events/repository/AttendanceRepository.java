package jsug.flaky.events.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import jsug.flaky.events.entity.Attendance;
import jsug.flaky.events.entity.Attendee;

public interface AttendanceRepository extends CrudRepository<Attendance, UUID>{

	@RestResource(exported = false)
	Attendance save(Attendance attendee);
	@RestResource(exported = false)
	void delete(Attendance attendee);
	
	
}
