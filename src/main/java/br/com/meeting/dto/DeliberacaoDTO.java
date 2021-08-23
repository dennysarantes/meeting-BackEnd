package br.com.meeting.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import br.com.meeting.model.Acao;
import br.com.meeting.model.Deliberacao;
import br.com.meeting.model.Item;
import br.com.meeting.model.Usuario;

public class DeliberacaoDTO {

	private Long id;
	private String descricao;
	private Date dataLimite;
	private Date dataRegistro;
	private String status;
	private List<AcaoDTO> acoes;
 	
	private List<Long> responsaveis;
	
	private Long item;
	
	
	
	
	public List<AcaoDTO> getAcoes() {
		return acoes;
	}

	public void setAcoes(List<AcaoDTO> acoes) {
		this.acoes = acoes;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItem() {
		return item;
	}

	public void setItem(Long item) {
		this.item = item;
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Date dataLimite) {
			
		this.dataLimite = dataLimite;
	}

	public List<Long> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(List<Long> users) {
			
		this.responsaveis = users;
		
//		users.forEach(res -> {
//				this.responsaveis.add(res);
//				});
	
	}

	public DeliberacaoDTO toDTO(Deliberacao deliberacao) {
		
		this.dataLimite = deliberacao.getDataLimite();
		this.dataRegistro = deliberacao.getDataRegistro();
		this.descricao = deliberacao.getDescricao();
		this.id = deliberacao.getId();
		this.item = deliberacao.getItem().getId();
		this.status = deliberacao.getStatus();		
		
		this.acoes = new ArrayList<AcaoDTO>();
		
		deliberacao.getAcoes().forEach(acao ->{
			this.acoes.add(new AcaoDTO().toDTO(acao));
		});
		
		
		this.responsaveis = new ArrayList<Long>();
		
		deliberacao.getResponsaveis().forEach(responsavel ->{
			this.responsaveis.add(new UsuarioDTO().toDTOApensaId(responsavel));
		});
		
		this.acoes = new ArrayList<AcaoDTO>();
		deliberacao.getAcoes().forEach(acao ->{
			this.acoes.add(new AcaoDTO().toDTO(acao));
		});
		
		return this;
	}

	public Deliberacao toDeliberacao(Deliberacao deliberacao, List<Usuario> responsaveis, Item item) {
		
		deliberacao.setDataLimite(this.dataLimite);
		deliberacao.setDescricao(this.descricao);
		deliberacao.setItem(item);
		deliberacao.setResponsaveis(responsaveis);
		deliberacao.setStatus(this.status);
		
		return deliberacao;
	} 
	
	

}
