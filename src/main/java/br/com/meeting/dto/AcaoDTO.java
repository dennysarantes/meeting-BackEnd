package br.com.meeting.dto;

import java.util.Date;
import java.util.List;

import br.com.meeting.model.Acao;
import br.com.meeting.model.Item;
import br.com.meeting.model.Usuario;

public class AcaoDTO {

	private Long id;
	private String descricao;
	private Date dataRegistro;
	private Date dataRealizada;
	
	private Long responsavel;
	
	private Long item;

	
	
	public Long getId() {
		return id;
	}
	
	

	public Long getItem() {
		return item;
	}



	public void setItem(Long itemId) {
		this.item = itemId;
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
		//this.responsavel = null;
		this.responsavel = acao.getResponsavel().getId();
		this.item = acao.getItem().getId();
		//this.responsavel = new UsuarioDTO().toDTO2(acao.getResponsavel().getId());
		//this.item = new ItemDTO().toDTO(acao.getItem());
		
		
		return this;
	}



	public Acao toAcao(Acao acao, Usuario responsavel, Item item) {
		
		acao.setDataRealizada(this.dataRealizada);
		acao.setDataRegistro(this.dataRegistro);
		acao.setDescricao(this.descricao);
		acao.setItem(item);
		acao.setResponsavel(responsavel);
		
		return acao;
	}
	
	
	
}
