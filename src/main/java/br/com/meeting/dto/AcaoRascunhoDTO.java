package br.com.meeting.dto;

import java.time.LocalDateTime;

import br.com.meeting.model.Acao;
import br.com.meeting.model.AcaoRascunho;

public class AcaoRascunhoDTO {

	private Long id;
	private String descricao;
	private LocalDateTime dataModificacao;
	private LocalDateTime dataRegistro;
	private LocalDateTime dataRealizada;
	
	private Long acaoDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(LocalDateTime dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public LocalDateTime getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDateTime dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public LocalDateTime getDataRealizada() {
		return dataRealizada;
	}

	public void setDataRealizada(LocalDateTime dataRealizada) {
		this.dataRealizada = dataRealizada;
	}

	public Long getAcaoDTO() {
		return acaoDTO;
	}

	public void setAcaoDTO(Long acaoDTO) {
		this.acaoDTO = acaoDTO;
	}
	
	public AcaoRascunhoDTO toDTO (AcaoRascunho acaoRascunho) {
		
		this.id = acaoRascunho.getId();
		this.acaoDTO = acaoRascunho.getAcao().getId();
		this.dataModificacao = acaoRascunho.getDataModificacao();
		this.dataRealizada = acaoRascunho.getDataRealizada();
		this.dataRegistro = acaoRascunho.getDataRegistro();
		this.descricao = acaoRascunho.getDescricao();
		
		return this;
	}	
}
