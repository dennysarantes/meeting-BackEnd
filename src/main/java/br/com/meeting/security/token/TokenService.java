package br.com.meeting.security.token;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.meeting.model.Token;
import br.com.meeting.model.Usuario;
import br.com.meeting.service.GuardaTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	//Injeta o serviço que guarda o token recebido nas requisições
		@Autowired
		private GuardaTokenService guardaTokenService;
	
	//Injetando valores do arquivo application.properties
		@Value("${meeting.jwt.expiration}")
		private String expiration;
		
		@Value("${meeting.jwt.secret}")
		private String secret;
		
		public String geraToken(Authentication authentication) {
			
			Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
			Date dataExpiracao =  this.calculaDataExpiracao(expiration);
			
			return Jwts.builder()
					.setSubject(usuarioLogado.getId().toString())
					.claim("nomeCompleto", usuarioLogado.getNome())
					.claim("username", usuarioLogado.getUsername())
					.claim("email", usuarioLogado.getEmail())
					.claim("localTrabalho", usuarioLogado.getLocalTrabalho())
					.claim("telefone", usuarioLogado.getTelefone())
					.claim("perfil", usuarioLogado.getPerfis().get(0).getNome())
					.setIssuedAt(new Date())
					.setExpiration(dataExpiracao)
					.signWith(SignatureAlgorithm.HS256, secret)
					.compact();
		}
		
		public boolean isTokenValido(String token) {
			try {
				Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		public void getTokenDTO(String token) {
			
			Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
			
			Token tokenModel = new Token(
					body.getSubject(),
					(String) body.get("nomeCompleto"),
					(String) body.get("username"),
					(String) body.get("email"),
					(String) body.get("localTrabalho"),
					(String) body.get("telefone"),
					(String) body.get("perfil"),
					1,
					1
					);
			
				guardaToken(tokenModel);
		}

		private void guardaToken(Token tokenModel) {
			guardaTokenService.setToken(tokenModel);
		}
		
		public Long getIdUsuarioDeDentroDoToken(String token) {
			
			//Esse trecho de código extrai o id do usuário que está dentro do Token!
			Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
			return Long.parseLong(body.getSubject());
		}
		
		
		private Date calculaDataExpiracao(String expiration) {
			return new Date(new Date().getTime() + Long.parseLong(expiration));
		}
		

}
