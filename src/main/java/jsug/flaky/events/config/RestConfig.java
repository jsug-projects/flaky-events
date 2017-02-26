package jsug.flaky.events.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import jsug.flaky.events.entity.Attendee;
import jsug.flaky.events.entity.Event;

@Configuration
public class RestConfig extends RepositoryRestConfigurerAdapter {
     @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config) {
    	 config.exposeIdsFor(Event.class, Attendee.class);
    }
}
