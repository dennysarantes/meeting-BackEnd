package br.com.meeting.controll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.meeting.dto.TokenDTO;
import br.com.meeting.model.LoginForm;
import br.com.meeting.security.token.TokenService;
import br.com.meeting.service.GuardaTokenService;

@CrossOrigin()
@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(@RequestBody LoginForm form){
		
		System.out.println("Tentando autenticar...");
		System.out.println("user: " + form.getUsername());
		System.out.println("senha: " + form.getPassword());
		
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		System.out.println("Dados de login recebidos: " + dadosLogin);
		
		try {
			Authentication authentication = authenticationManager.authenticate(dadosLogin);
			String token = tokenService.geraToken(authentication);
			
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
			
		} catch (AuthenticationException e) {
			System.out.println(e);
			return ResponseEntity.badRequest().build();
		}
		
		
	}
	
	
}
