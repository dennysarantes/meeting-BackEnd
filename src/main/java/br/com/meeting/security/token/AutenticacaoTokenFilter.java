package br.com.meeting.security.token;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.meeting.dto.TokenDTO;
import br.com.meeting.model.Usuario;
import br.com.meeting.repository.UsuarioRepository;


public class AutenticacaoTokenFilter extends OncePerRequestFilter{

	private UsuarioRepository usuarioRepository;
	private TokenService tokenService;
	private TokenDTO tokenDTO;
	
	
	public AutenticacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = this.recuperarToken(request);
		boolean tokenValido = tokenService.isTokenValido(token);

		if (tokenValido) {
			System.out.println("::::::TOKEN recebido no backend:::::::::");
			System.out.println(token);
			
			this.autenticarUsuario(token);
			try {
				tokenService.getTokenDTO(token); //Faz o decode do token e guarda para manipular os dados
				
			} catch (Exception e) {
				System.out.println("não gravou os dados...");
				System.out.println(e);
			}
			
		}else {
			System.out.println("::::::TOKEN não recebido no backend ou Token inválido:::::::::");
			System.out.println(token);
		}
		
		filterChain.doFilter(request, response);
		
	}


	private void autenticarUsuario(String token) {
		//Dentro do código criptografado do token existe o ID do usuário.
			Long idUsuario = tokenService.getIdUsuarioDeDentroDoToken(token);
			
			Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
			
			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(usuario.get(), null, usuario.get().getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
	}


	private String recuperarToken(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
