package jsug.flaky.events.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jsug.flaky.events.entity.Event;
import jsug.flaky.events.repository.EventRepository;

@RepositoryRestController
public class EventsContoller {

	@Autowired
	EventRepository eventRepository;
	
	
	@GetMapping("/events/nearest")
	public ResponseEntity<?> findNearestEvent(
			@DateTimeFormat(iso=ISO.DATE_TIME) @RequestParam LocalDateTime targetDate
			) {
		PageRequest pageable = new PageRequest(0, 1);
		
		Page<Event> page = eventRepository.findByStartDateBetween(targetDate, targetDate.plusMonths(6L), pageable);
		
		if (page.getContent().size() > 0) {
			Resource<Event> resource = new Resource<Event>(page.getContent().get(0));
			resource.add(linkTo(methodOn(EventsContoller.class).findNearestEvent(null)).withSelfRel().expand(targetDate));
			return 	ResponseEntity.ok(resource);
		}
		return ResponseEntity.ok(null);
	}
	
}
