package jsug.flaky.events.restdoc.api;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jsug.flaky.events.DummyDataCreator;
import jsug.flaky.events.entity.Event;


@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("development")
public class EventsApiDocumentation {
	
	@Rule
	public JUnitRestDocumentation restDocumentation =
			new JUnitRestDocumentation("target/generated-snippets");
		
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	DummyDataCreator dummyDataCreator;
	
	
	Event eventFixture;
	
	String docId = "/resources/events/{methodName}/";
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation).uris().withPort(8083))
				.build();
		
		dummyDataCreator.createDummyData();
		eventFixture = dummyDataCreator.createdEvents.get(0);
	}
	
	
	@Test
	public void retrieve() throws Exception {
		
		this.mockMvc.perform(
				get("/events/{eventId}", eventFixture.id)
				.accept(MediaTypes.HAL_JSON)
		) 
		.andExpect(
				status().isOk()
		) 
		.andDo(
			document(docId,
				links(halLinks(),
					linkWithRel("self").description("this <<resources-events-retrieve, Event>>"),
					linkWithRel("event").description("this <<resources-events-retrieve, Event>>")
				),
				pathParameters(
					parameterWithName("eventId").description("event id")
				),
				responseFields(
					fieldWithPath("id").description("id of event"),
					fieldWithPath("eventName").description("name of event"),
					fieldWithPath("attendeeMaxSize").description("max size of attendee"),
					fieldWithPath("startDate").description("start date and time"),
					fieldWithPath("endDate").description("end date and time"),
					fieldWithPath("eventPlace").description("place event holds"),					
					fieldWithPath("lotteryDate").description("event lottery date and time"),					
					fieldWithPath("_links").description("<<resources-events-retrieve-links,Links>>")
				)
			)
		); 	
		
	}
	
	@Test
	public void search() throws Exception {
		
		this.mockMvc.perform(
				get("/events/search/findByStartDateBetween")
				.param("fromDate", "2017-01-01T00:00")
				.param("toDate", "2017-02-01T00:00")
				.param("page", "1")
				.param("size", "5")
				.accept(MediaTypes.HAL_JSON)
		) 
		.andExpect(
				status().isOk()
		) 
		.andDo(
			document(docId,
				links(halLinks(),
					linkWithRel("self").description("this <<resources-events-search, Search events>>"),
					linkWithRel("next").description("next <<resources-events-search, Search events>>"),
					linkWithRel("last").description("last <<resources-events-search, Search events>>"),
					linkWithRel("prev").description("prev <<resources-events-search, Search events>>"),
					linkWithRel("first").description("first <<resources-events-search, Search events>>")
				),
				requestParameters(
					parameterWithName("fromDate").description("The day event starts greater than equal"),
					parameterWithName("toDate").description("The day event starts less than equal"),
					parameterWithName("page").description("page no of lists that starts from 0"),
					parameterWithName("size").description("lists size in one page")
				),
				responseFields(
					fieldWithPath("_embedded.events").description("list of events"),
					fieldWithPath("page.size").description("lists size in one page"),
					fieldWithPath("page.totalElements").description("total size of all lists"),
					fieldWithPath("page.totalPages").description("total page size of all Lists"),
					fieldWithPath("page.number").description("page no of this page"),					
					fieldWithPath("_links").description("<<resources-events-search-links,Links>>")
				)
			)
		); 	
	}
	

	
	
	@Test
	public void nearest() throws Exception {

		this.mockMvc.perform(
				get("/events/nearest")
				.param("targetDate", "2017-01-01T00:00")
				.accept(MediaTypes.HAL_JSON)
		) 
		.andExpect(
				status().isOk()
		) 
		.andDo(
			document(docId,
				links(halLinks(),
					linkWithRel("self").description("this <<resources-events-nearest, Nearest events>>")
				),
				requestParameters(
					parameterWithName("targetDate").description("The day search for nearest event")
				),
				responseFields(
						fieldWithPath("id").description("id of event"),
						fieldWithPath("eventName").description("name of event"),
						fieldWithPath("attendeeMaxSize").description("max size of attendee"),
						fieldWithPath("startDate").description("start date and time"),
						fieldWithPath("endDate").description("end date and time"),
						fieldWithPath("eventPlace").description("place event holds"),					
						fieldWithPath("lotteryDate").description("event lottery date and time"),					
						fieldWithPath("_links").description("<<resources-events-nearest-links,Links>>")
				)
			)
		); 	
		
		
	}	
	
}
