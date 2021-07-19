package br.com.meeting.security;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.meeting.repository.UsuarioRepository;
import br.com.meeting.security.token.AutenticacaoTokenFilter;
import br.com.meeting.security.token.TokenService;


@EnableWebMvc
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	AutenticacaoService autenticacaoService;
	
	// O Bean é usado em interfaces que não podem ser autoinjetadas
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	// Configuração de autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		auth.userDetailsService(autenticacaoService).passwordEncoder(encoder);
		
//		auth.jdbcAuthentication()
//		.dataSource(dataSource)
//		.passwordEncoder(encoder);
//		//.withUser(user);
	
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
			http
//				.cors(corsConfigurationSource())
//				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/autenticacao").permitAll()
				.antMatchers(HttpMethod.POST, "/api/usuario").permitAll()
				.antMatchers(HttpMethod.POST, "/**").authenticated()
				.antMatchers(HttpMethod.GET, "/**").authenticated()
				.anyRequest().authenticated()
				// Aqui é a configuração de autenticação stateless, usando a biblioteca JJWT
				.and().cors()
				.and().csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(new AutenticacaoTokenFilter(tokenService, usuarioRepository),
						UsernamePasswordAuthenticationFilter.class);
		
//				.httpBasic()
//				.and()
//				.csrf().disable()
//				.logout(logout -> {
//					logout.clearAuthentication(true)
//					.invalidateHttpSession(true)
//					.deleteCookies("JSESSIONID")
//					.logoutSuccessUrl("/login");
//					});
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		
////		UserDetails user = User.builder()
////				.username("fernanda.arantes")
////				.password(encoder.encode("12345"))
////				.roles("ADM")
////				.build();
//		
//		auth.jdbcAuthentication()
//		.dataSource(dataSource)
//		.passwordEncoder(encoder);
//		//.withUser(user);
//	
//	}

//	@Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://locahost:4200"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
	
	
	public static void main(String[] args) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senha = encoder.encode("12345");
		System.out.println(senha);
		
	}
	
}

 

