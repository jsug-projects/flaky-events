package jsug.flaky.events.restdoc.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import jsug.flaky.events.DummyDataCreator;
import jsug.flaky.events.entity.Attendee;
import jsug.flaky.events.entity.Event;


@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("development")
public class AttendeeApiDocumentation {
	
	@Rule
	public JUnitRestDocumentation restDocumentation =
			new JUnitRestDocumentation("target/generated-snippets");

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	DummyDataCreator dummyDataCreator;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	Event eventFixture;
	
	String docId = "/resources/attendees/{methodName}/";
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation).uris().withPort(8083))
				.build();
		dummyDataCreator.createDummyData();
		eventFixture = dummyDataCreator.createdEvents.get(0);
	}
	
	
	
	@Test
	public void countFlaky() throws Exception {
		
		this.mockMvc.perform(
				get("/attendees/search/countFlakyOccurrenceByMemberId")
				.param("memberId", "07bc1726-cdae-4cbf-bcbb-908023619001")
				.param("fromDate", "2017-01-01T00:00")
				.param("toDate", "2017-02-01T00:00")
				.accept(MediaTypes.HAL_JSON)
		) 
		.andExpect(
				status().isOk()
		) 
		.andDo(
			document(docId,
				requestParameters(
					parameterWithName("memberId").description("member id for count"),
					parameterWithName("fromDate").description("target day greater than equal"),
					parameterWithName("toDate").description("target day less than equal")
				)
			)
		); 	
	}
	

	
	
	@Test
	public void countByEventId() throws Exception {

		this.mockMvc.perform(
				get("/attendees/search/countByEventId")
				.param("eventId", eventFixture.id.toString())
				.accept(MediaTypes.HAL_JSON)
		) 
		.andExpect(
				status().isOk()
		) 
		.andDo(
				document(docId,
				requestParameters(
					parameterWithName("eventId").description("event id for count")
				)
			)
		); 	

		
	}	

	@Test
	public void create() throws Exception {

		Map<String, String> attendee = new HashMap<String, String>();
		attendee.put("memberId", UUID.randomUUID().toString());
		attendee.put("event", "http://localhost:8083/events/"+eventFixture.id);
		attendee.put("comment", "宜しくお願いします！");
		
		ConstraintDescriptions fields = new ConstraintDescriptions(Attendee.class);
		List<String> foo = fields.descriptionsForProperty("memberId");
				
		this.mockMvc.perform(
				post("/attendees")
				.contentType(MediaTypes.HAL_JSON)
				.content(this.objectMapper.writeValueAsString(attendee))				
		) 
		.andExpect(
				status().isCreated()
		) 
		.andDo(
				document(docId,
					requestFields(
//							fieldWithPath("memberId").attributes(key("constraints").value(String.join(". ", fields.descriptionsForProperty("memberId"))))
//							.description("id of member"),
//							fieldWithPath("event").attributes(key("constraints").value(String.join(". ", fields.descriptionsForProperty("event"))))
//							.description("link of event"),
//							fieldWithPath("comment").attributes(key("constraints").value(String.join(". ", fields.descriptionsForProperty("comment"))))
//							.description("comment")
//							)
							fieldWithPath("memberId").description("id of member"),
							fieldWithPath("event").description("link of event"),
							fieldWithPath("comment").description("comment")
							)
					)
		); 	
	}
	
}
