package jsug.flaky.events.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"event_id", "memberId"})
	})
public class Attendee {
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "binary(16)")	
	public UUID id;

	@ManyToOne
	@JoinColumn(name="event_id")
	@NotNull
	public Event event;
	
	
	@OneToOne(mappedBy="attendee")
	public Attendance attendance;
		
	public String comment;
	
	@Column(columnDefinition = "binary(16)")
	@NotNull
	public UUID memberId;
	
}
