package jsug.flaky.events.restdoc.gettingstarted;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import jsug.flaky.events.DummyDataCreator;
import jsug.flaky.events.entity.Event;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("development")
public class GettingStartedDocumentation {
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
	
	String docId = "/getting-started/{methodName}/{step}/";

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation).uris().withPort(8083))
				.alwaysDo(document(docId,
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.build();
		dummyDataCreator.createDummyData();
		eventFixture = dummyDataCreator.createdEvents.get(0);
	}
	
	@Test
	public void index() throws Exception {
		this.mockMvc.perform(get("/").accept(MediaTypes.HAL_JSON))
		.andExpect(status().isOk());
	}
	
	

}
