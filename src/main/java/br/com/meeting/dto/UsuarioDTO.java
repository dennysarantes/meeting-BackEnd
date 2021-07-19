package br.com.meeting.dto;

import java.io.Serializable;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.meeting.model.Usuario;

public class UsuarioDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String username;
	private String nome;
	private String email;
	private String senha;
	private String telefone;
	private String localTrabalho;
	
	
	
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getLocalTrabalho() {
		return localTrabalho;
	}
	public void setLocalTrabalho(String localTrabalho) {
		this.localTrabalho = localTrabalho;
	}
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UsuarioDTO toDTOCompleto(Usuario participante) {
			
			this.id = participante.getId();
			this.username = participante.getUsername();
			this.email = participante.getEmail();
			this.localTrabalho = participante.getLocalTrabalho();
			this.nome = participante.getNome();
			this.telefone = participante.getTelefone();
	
		return this;
	}
	public Long toDTOApensaId(Usuario participante) {
		
		return participante.getId();
	}
	
	public Usuario toUsuario(UsuarioDTO usuarioDTO) {
		
		Usuario usuario = new Usuario();
		
		usuario.setNome(usuarioDTO.nome);
		usuario.setEmail(usuarioDTO.email);
		usuario.setUsername(usuarioDTO.username);
		usuario.setLocalTrabalho(usuarioDTO.localTrabalho);
		usuario.setTelefone(usuarioDTO.telefone);
		usuario.setPassword(new BCryptPasswordEncoder().encode(this.senha));
		
		return usuario;
	}
	
}
