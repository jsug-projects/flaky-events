package jsug.flaky.events.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Event {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "binary(16)")	
	public UUID id;
	
	public String eventName;

	@Column(columnDefinition="TIMESTAMP")
	public LocalDateTime startDate;	
	@Column(columnDefinition="TIMESTAMP")
	public LocalDateTime endDate;
	public String eventPlace;
	@Column(columnDefinition="DATE")
	public LocalDate lotteryDate;
	
	
	
}
