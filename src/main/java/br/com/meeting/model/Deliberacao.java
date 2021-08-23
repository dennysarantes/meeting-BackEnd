package br.com.meeting.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.meeting.dto.DeliberacaoDTO;

@Entity
public class Deliberacao {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private Date dataRegistro;
	private Date dataLimite;
	private String status;
	
	@ManyToMany
	private List<Usuario> responsaveis;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "deliberacao")
	private List<Acao> acoes;
		
	@ManyToOne
	@JsonIgnore
	private Item item;

	public Deliberacao() {}
	
	public Deliberacao(
			DeliberacaoDTO deliberacaoDTO,
			List<Usuario> responsaveis,
			Item item) {
		
		this.descricao = deliberacaoDTO.getDescricao();
		this.dataLimite = deliberacaoDTO.getDataLimite();
		this.item = item;
		this.responsaveis = responsaveis;
	}
	
	
	
	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public List<Acao> getAcoes() {
		return acoes;
	}

	public void setAcoes(List<Acao> acoes) {
		this.acoes = acoes;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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

	public List<Usuario> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(List<Usuario> responsaveis) {
		this.responsaveis = responsaveis;
	}

	

}
