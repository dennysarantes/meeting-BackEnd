package br.com.meeting.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.meeting.model.Confirmacao;
import br.com.meeting.model.Reuniao;
import br.com.meeting.model.StatusConfirmacao;
import br.com.meeting.model.Usuario;

public class ConfirmacaoDTO {

	private Long id;
	private String statusConfirmacao;
	//private LocalDateTime dataAlteracao;
	private Long participante;
	//private Reuniao reuniao;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatusConfirmacao() {
		return statusConfirmacao;
	}
	public void setStatusConfirmacao(String statusConfirmacao) {
		this.statusConfirmacao = statusConfirmacao;
	}
	public Long getParticipantes() {
		return participante;
	}
	public void setParticipantes(Long participante) {
		this.participante = participante;
	}
	
	public ConfirmacaoDTO confirmacaoToDTO(Confirmacao confirmacao) {
		
		this.id = confirmacao.getId();
		this.statusConfirmacao =  (confirmacao.getStatusConfirmacao().name());
		this.participante = confirmacao.getParticipante().getId();
		
		return this;
	}
	
}

