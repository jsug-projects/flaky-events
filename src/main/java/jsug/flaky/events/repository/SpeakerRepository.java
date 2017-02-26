package jsug.flaky.events.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import jsug.flaky.events.entity.Speaker;

public interface SpeakerRepository extends PagingAndSortingRepository<Speaker, UUID>{

	@RestResource(exported = false)
	Speaker save(Speaker attendee);
	@RestResource(exported = false)
	void delete(Speaker attendee);
}
