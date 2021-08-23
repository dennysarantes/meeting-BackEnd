package br.com.meeting.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.meeting.dto.ConfirmacaoDTO;

@Entity
public class Confirmacao {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private StatusConfirmacao statusConfirmacao;
	
	private LocalDateTime dataAlteracao;
	
	@ManyToOne
	@JsonIgnore
	private Usuario participante;
	
	@ManyToOne
	@JsonIgnore
	private Reuniao reuniao;

	
	public Confirmacao() {}
	
	public Confirmacao(ConfirmacaoDTO confirmacaoDTO, Usuario usuario, Reuniao reuniao) {
		
		this.id = confirmacaoDTO.getId();		
		this.dataAlteracao = LocalDateTime.now();
		this.participante = usuario;
		this.reuniao = reuniao;
		
		if (confirmacaoDTO.getStatusConfirmacao().equalsIgnoreCase("CONFIRMADO")) {
			this.statusConfirmacao = StatusConfirmacao.DUVIDA;
		}else {
			this.statusConfirmacao = StatusConfirmacao.CONFIRMADO;
		}

		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public StatusConfirmacao getStatusConfirmacao() {
		return statusConfirmacao;
	}


	public void setStatusConfirmacao(StatusConfirmacao statusConfirmacao) {
		this.statusConfirmacao = statusConfirmacao;
	}


	public LocalDateTime getDataAlteracao() {
		return dataAlteracao;
	}


	public void setDataAlteracao(LocalDateTime dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}


	public Usuario getParticipante() {
		return participante;
	}


	public void setParticipante(Usuario participante) {
		this.participante = participante;
	}


	public Reuniao getReuniao() {
		return reuniao;
	}


	public void setReuniao(Reuniao reuniao) {
		this.reuniao = reuniao;
	}
	
	
	
	
	}
