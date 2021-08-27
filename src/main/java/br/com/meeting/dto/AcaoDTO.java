package br.com.meeting.dto;

import java.util.Date;
import java.util.List;

import br.com.meeting.model.Acao;
import br.com.meeting.model.Deliberacao;
import br.com.meeting.model.Item;
import br.com.meeting.model.Usuario;

public class AcaoDTO {

	private Long id;
	private String descricao;
	private Date dataRegistro;
	private Date dataRealizada;
	
	private AcaoRascunhoDTO acaoRascunhoDTO; 
	
	private Long responsavel;
	
	private Long deliberacao;
	

	
	
	public Long getId() {
		return id;
	}
	
	

	public AcaoRascunhoDTO getAcaoRascunhoDTO() {
		return acaoRascunhoDTO;
	}



	public void setAcaoRascunhoDTO(AcaoRascunhoDTO acaoRascunhoDTO) {
		this.acaoRascunhoDTO = acaoRascunhoDTO;
	}



	public Long getDeliberacao() {
		return deliberacao;
	}



	public void setDeliberacao(Long deliberacaoId) {
		this.deliberacao = deliberacaoId;
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

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Date getDataRealizada() {
		return dataRealizada;
	}

	public void setDataRealizada(Date dataRealizada) {
		this.dataRealizada = dataRealizada;
	}

	public Long getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Long responsavel) {
		this.responsavel = responsavel;
	}
	
	public AcaoDTO toDTO (Acao acao){
		
		this.dataRealizada = acao.getDataRealizada();
		this.dataRegistro = acao.getDataRegistro();
		this.descricao = acao.getDescricao();
		this.id = acao.getId();
		this.responsavel = acao.getResponsavel().getId();
		this.deliberacao = acao.getDeliberacao().getId();
		
		try {
			this.acaoRascunhoDTO = new AcaoRascunhoDTO().toDTO(acao.getAcaoRascunho());
			return this;
		} catch (Exception e) {
			this.acaoRascunhoDTO = null;
			return this;
		}
		
		
		
	}



	public Acao toAcao(Acao acao, Usuario responsavel, Deliberacao deliberacao) {
		
		acao.setDataRealizada(this.dataRealizada);
		acao.setDataRegistro(this.dataRegistro);
		acao.setDescricao(this.descricao);
		acao.setDeliberacao(deliberacao);
		acao.setResponsavel(responsavel);
		
		return acao;
	}
	
	public Acao toAcaoNovo(Usuario responsavel, Deliberacao deliberacao) {
		Acao acao = new Acao();
		
		acao.setDataRealizada(this.dataRealizada);
		acao.setDataRegistro(this.dataRegistro);
		acao.setDescricao(this.descricao);
		acao.setDeliberacao(deliberacao);
		acao.setResponsavel(responsavel);
		
		return acao;
	}
	
	
	
}
