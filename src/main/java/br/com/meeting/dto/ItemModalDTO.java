package br.com.meeting.dto;

import java.io.Serializable;
import java.time.LocalDate;


public class ItemModalDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String titulo;
	private String descricao;
	private Long idReuniaoAtual;
	private Long idReuniaoNova;
	
		
	public Long getIdReuniaoNova() {
		return idReuniaoNova;
	}
	public void setIdReuniaoNova(Long idReuniaoNova) {
		this.idReuniaoNova = idReuniaoNova;
	}
	public Long getIdReuniaoAtual() {
		return idReuniaoAtual;
	}
	public void setIdReuniaoAtual(Long idReuniaoAtual) {
		this.idReuniaoAtual = idReuniaoAtual;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
