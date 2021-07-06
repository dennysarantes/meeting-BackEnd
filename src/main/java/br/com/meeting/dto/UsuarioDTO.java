package br.com.meeting.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.meeting.model.Usuario;

public class UsuarioDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String email;
	//private String senha;
	private Integer telefone;
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
//	public String getSenha() {
//		return senha;
//	}
//	public void setSenha(String senha) {
//		this.senha = senha;
//	}
	public Integer getTelefone() {
		return telefone;
	}
	public void setTelefone(Integer telefone) {
		this.telefone = telefone;
	}
	public String getLocalTrabalho() {
		return localTrabalho;
	}
	public void setLocalTrabalho(String localTrabalho) {
		this.localTrabalho = localTrabalho;
	}
	public UsuarioDTO toDTOCompleto(Usuario participante) {
			
			this.id = participante.getId();
			this.email = participante.getEmail();
			this.localTrabalho = participante.getLocalTrabalho();
			this.nome = participante.getNome();
			this.telefone = participante.getTelefone();
	
		return this;
	}
	public Long toDTOApensaId(Usuario participante) {
		
		return participante.getId();
	}
	
}
