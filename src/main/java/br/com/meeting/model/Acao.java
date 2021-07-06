package br.com.meeting.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.meeting.dto.AcaoDTO;

@Entity
public class Acao {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private Date dataRegistro;
	private Date dataRealizada;
	
	@ManyToOne
	private Usuario responsavel;
	
	@ManyToOne
	@JsonIgnore
	private Item item;

	public Acao() {}
	
	/**
	 * @param descricao
	 * @param dataRegistro
	 * @param dataRealizada
	 * @param responsavel
	 * @param item
	 */
	public Acao(String descricao, Date dataRegistro, Date dataRealizada, Usuario responsavel, Item item) {
		this.descricao = descricao;
		this.dataRegistro = dataRegistro;
		this.dataRealizada = dataRealizada;
		this.responsavel = responsavel;
		this.item = item;
	}
	
	public Acao(AcaoDTO acaoDTO, Usuario responsavel, Item item) {
		this.descricao = acaoDTO.getDescricao();
		this.dataRegistro = acaoDTO.getDataRegistro();
		this.dataRealizada = acaoDTO.getDataRealizada();
		this.responsavel = responsavel;
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

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

	public Usuario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}
	
	public AcaoDTO toDTO (Acao acao) {
		return null;
		
	}
	
}
