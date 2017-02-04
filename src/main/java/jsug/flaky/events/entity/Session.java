package jsug.flaky.events.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Session {
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "binary(16)")	
	public UUID id;
	
	public Integer seatSize;
	
	public String title;
	
	public String summary;
	
	@ManyToOne
	@JoinColumn(name="event_id")	
	public Event event;

	@OneToMany(mappedBy="session")
	public List<Speaker> speakers;
	

}
