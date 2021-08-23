package br.com.meeting.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.meeting.dto.ItemDTO;

@Entity
public class Item {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private LocalDate dataCadastro;
	private String descricao;
	
	@ManyToOne
	private Usuario responsavelCadastro;
	
	@ManyToOne
	private Usuario responsavel;
	
	@Enumerated(EnumType.STRING)
	private StatusItem status;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
	private List<Deliberacao> deliberacoes;

	
	public Item() {}
	
	public Item(ItemDTO itemDTO,
			Usuario responsavel,
			Usuario responsavelCadastrado
			) {
		
		this.dataCadastro = LocalDate.parse(itemDTO.getDataCadastro());
		this.descricao = itemDTO.getDescricao();
		this.responsavel = responsavel;
		this.responsavelCadastro = responsavelCadastrado;
		this.status = itemDTO.getStatus();
		this.titulo = itemDTO.getTitulo();
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Usuario getResponsavelCadastro() {
		return responsavelCadastro;
	}

	public void setResponsavelCadastro(Usuario responsavelCadastro) {
		this.responsavelCadastro = responsavelCadastro;
	}

	public Usuario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}

	public StatusItem getStatus() {
		return status;
	}

	public void setStatus(StatusItem status) {
		this.status = status;
	}

	public List<Deliberacao> getDeliberacoes() {
		return deliberacoes;
	}

	public void setDeliberacoes(List<Deliberacao> deliberacoes) {
		this.deliberacoes = deliberacoes;
	}

	
	
	
	
}
