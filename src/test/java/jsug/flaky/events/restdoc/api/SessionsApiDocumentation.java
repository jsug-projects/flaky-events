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
public class SessionsApiDocumentation {
	
	@Rule
	public JUnitRestDocumentation restDocumentation =
			new JUnitRestDocumentation("target/generated-snippets");
		
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	DummyDataCreator dummyDataCreator;
	
	
	Event eventFixture;
	
	String docId = "/resources/sessions/{methodName}/";	
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation).uris().withPort(8083))
				.build();
		
		dummyDataCreator.createDummyData();
		eventFixture = dummyDataCreator.createdEvents.get(0);
	}
	
	
	@Test
	public void retrieveByEvent() throws Exception {
		
		this.mockMvc.perform(
				get("/sessions/search/findByEventId")
				.param("eventId", eventFixture.id.toString())
				.accept(MediaTypes.HAL_JSON)
		) 
		.andExpect(
				status().isOk()
		) 
		.andDo(
			document(docId,
				links(halLinks(),
					linkWithRel("self").description("this <<resources-sessions-retrieveByEvent, Retrieve sessions>>")
				),
				requestParameters(
					parameterWithName("eventId").description("event id associated with sessions")
				),
				responseFields(
					fieldWithPath("_embedded.sessions").description("list of sessions"),
					fieldWithPath("_links").description("<<resources-sessions-retrieveByEvent-links,Links>>")
				)
			)
		); 	
	}
	

	
}
