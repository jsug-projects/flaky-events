package jsug.flaky.events;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = { FlakyEventsApplication.class, Jsr310JpaConverters.class })
public class FlakyEventsApplication {

	public static void main(String[] args) {
		ApplicationContext ctx  = SpringApplication.run(FlakyEventsApplication.class, args);
		ctx.getBean(DummyDataCreator.class).createDummyData();
	}
	

//	@Bean
//	public CommandLineRunner  commandLineRunner() {
//		return new CommandLineRunner(){
//			@Override
//			public void run(String... args) throws Exception {
//				dummyDataCreator().createDummyData();
//			}
//		};
//	}
	
	
//	@Bean
//	@Profile("!cloud")
//	RequestDumperFilter dumperFilter() {
//		return new RequestDumperFilter();
//	}
	
//	@Bean
//	@Profile("development")
//	UrlRewriteFilter urlRewriteFilter() {
//		return new UrlRewriteFilter();
//	}
	
	static class UrlRewriteFilter implements Filter {
		@Override
		public void init(FilterConfig filterConfig) throws ServletException {}
		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			HttpServletRequest req = (HttpServletRequest)request;
			String path = req.getRequestURI().substring(req.getContextPath().length());
			if (path != null && path.startsWith("/api")) {
				req.getRequestDispatcher(path.substring("/api".length())).forward(request, response);
			} else {
				chain.doFilter(request, response);
			}
		}
		@Override
		public void destroy() {}		
	}

	

	@Bean 
	public DataSource dataSource() {
      BasicDataSource ds = new BasicDataSource();
      ds.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
      ds.setUrl("jdbc:log4jdbc:h2:mem:testdb;MODE=MYSQL");
      ds.setUsername("sa");
      ds.setPassword("");        
      return ds;
	}
	
//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource ds = new BasicDataSource();
//        ds.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
//        ds.setUrl("jdbc:log4jdbc:mysql://localhost:3306/flaky_events?useUnicode=true&characterEncoding=utf8");
//        ds.setUsername("flaky_events");
//        ds.setPassword("flaky_events");        
//        return ds;
//    }
	
	@Bean
	public DummyDataCreator dummyDataCreator() {
		return new DummyDataCreator();
	}
}



