package br.com.meeting;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import br.com.meeting.security.SessionListener;

@WebListener
@SpringBootApplication
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MeetingApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication sa = new SpringApplication(MeetingApplication.class);
		sa.run(args);
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.addListener(new SessionListener());
		super.onStartup(servletContext);
	}
	
	@Bean
	public ServletListenerRegistrationBean<SessionListener> sessionListener(){
		ServletListenerRegistrationBean<SessionListener> listenerSession = 
				new ServletListenerRegistrationBean<>();
		
		listenerSession.setListener(new SessionListener());
		
		return listenerSession;
	}
}
