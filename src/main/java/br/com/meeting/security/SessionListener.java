package br.com.meeting.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Bean;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent e) {
		System.out.println("Session criada...");
		e.getSession().setMaxInactiveInterval(1);
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("session destruída!");
	
	}
}


