package br.com.meeting.security.token;

import java.sql.Time;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.meeting.model.Reuniao;
import br.com.meeting.model.Token;
import br.com.meeting.model.Usuario;
import br.com.meeting.repository.DeliberacaoRepository;
import br.com.meeting.repository.ItemRepository;
import br.com.meeting.repository.ReuniaoRepository;
import br.com.meeting.service.GuardaTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	//Injeta o serviço que guarda o token recebido nas requisições
		@Autowired
		private GuardaTokenService guardaTokenService;
		
		@Autowired
		private ReuniaoRepository reuniaoRepository;
		
		@Autowired
		private ItemRepository itemRepository;
		
		@Autowired
		private DeliberacaoRepository deliberacaoRepository;
	
	//Injetando valores do arquivo application.properties
		@Value("${meeting.jwt.expiration}")
		private String expiration;
		
		@Value("${meeting.jwt.secret}")
		private String secret;
		
		public String geraToken(Authentication authentication) {
			
			Integer qtdItensProximaReuniao = null;
			String dataProximaReuniao;
			
			//Pega o objeto Usuario que autenticou na API
			Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
			
			//Pega a lista de pŕoximas reuniões
			List<Reuniao> proxReunioesDoUsuario = 
									reuniaoRepository.findAllByUserIDMaiorQueHoje(
														usuarioLogado.getId(),
														LocalDate.now(),
														Time.valueOf(LocalTime.now()));
			
			//Pega a quantidade de deliberações que o usuário consta como responsável
						
			Integer qtdAcoesPendentes = deliberacaoRepository.findQtdByStatus("PENDENTE", usuarioLogado.getId());
			
			Integer qtdAcoesAtrasadas = deliberacaoRepository.findQtdByStatus("ATRASADO", usuarioLogado.getId());
			
			Integer qtdReunioesFuturas = reuniaoRepository.findQtdByUser(usuarioLogado.getId(), LocalDate.now());
			
			//Verifica se existe reuniões futuras e pega a quantidade de itens da próxima reunião. 
			try {
				if(proxReunioesDoUsuario.get(0).getItens().size() > 0) {
					qtdItensProximaReuniao =  proxReunioesDoUsuario.get(0).getItens().size();
				}else {
					qtdItensProximaReuniao = null;
				}
			} catch (Exception e) {
				qtdItensProximaReuniao = null;
			}
			
			
			//Verifica se existe reuniões futuras do usuário e pega a data da próxima.
			try {
				if (!proxReunioesDoUsuario.get(0).getDataAgendamento().toString().isEmpty()) {
					
					DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
									
					dataProximaReuniao = proxReunioesDoUsuario.get(0)
											.getDataAgendamento()
											.format(formato)
											.toString();
				}else {
					dataProximaReuniao = null;
				}
				
			} catch (Exception e) {
				dataProximaReuniao = null;
			}
			
			
			
			
			Date dataExpiracao =  this.calculaDataExpiracao(expiration);
			
			return Jwts.builder()
					.setSubject(usuarioLogado.getId().toString())
					.claim("nomeCompleto", usuarioLogado.getNome())
					.claim("username", usuarioLogado.getUsername())
					.claim("email", usuarioLogado.getEmail())
					.claim("localTrabalho", usuarioLogado.getLocalTrabalho())
					.claim("telefone", usuarioLogado.getTelefone())
					.claim("perfil", usuarioLogado.getPerfis().get(0).getNome())
					.claim("qtdReunioes", proxReunioesDoUsuario.size())
					.claim("dataProximaReuniao" , dataProximaReuniao)
					.claim("qtdItensProximaReuniao", String.valueOf(qtdItensProximaReuniao))
					.claim("qtdAcoesPendentes", String.valueOf(qtdAcoesPendentes))
					.claim("qtdAcoesAtrasadas", String.valueOf(qtdAcoesAtrasadas))
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
