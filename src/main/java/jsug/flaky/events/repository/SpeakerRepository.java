package jsug.flaky.events.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import jsug.flaky.events.entity.Speaker;

public interface SpeakerRepository extends PagingAndSortingRepository<Speaker, UUID>{

	
}
