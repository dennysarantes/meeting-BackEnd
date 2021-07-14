package br.com.meeting.service;

import org.springframework.stereotype.Service;

import br.com.meeting.model.Token;

@Service
public class GuardaTokenService {

	private Token token;
	
	public Token getToken() {
		return token;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}
		
}
