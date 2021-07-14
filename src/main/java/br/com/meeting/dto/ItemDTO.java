package br.com.meeting.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.meeting.model.Item;
import br.com.meeting.model.StatusItem;
import br.com.meeting.model.Usuario;

public class ItemDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String titulo;
	private LocalDate dataCadastro;
	private String descricao;
	
	private Long responsavelCadastro;
	
	private Long responsavel;
	
	@Enumerated(EnumType.STRING)
	private StatusItem status;
	
	private List<Long> deliberacoes;
	
	private List<Long> acoes;
	
	
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

	public String getDataCadastro() {
		return dataCadastro.toString();
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getResponsavelCadastro() {
		return responsavelCadastro;
	}

	public void setResponsavelCadastro(Long responsavelCadastro) {
		this.responsavelCadastro = responsavelCadastro;
	}

	public Long getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Long responsavel) {
		this.responsavel = responsavel;
	}

	public StatusItem getStatus() {
		return status;
	}

	public void setStatus(StatusItem status) {
		this.status = status;
	}

	public List<Long> getDeliberacoes() {
		return deliberacoes;
	}

	public void setDeliberacoes(List<Long> deliberacoes) {
		this.deliberacoes = deliberacoes;
	}

	public List<Long> getAcoes() {
		return acoes;
	}

	public void setAcoes(List<Long> acoes) {
		this.acoes = acoes;
	}

	public ItemDTO toDTO(Item item) {
		
		this.dataCadastro = item.getDataCadastro();
		this.descricao = item.getDescricao();
		this.id = item.getId();
		this.responsavel =  new UsuarioDTO().toDTOApensaId(item.getResponsavel());
		this.responsavelCadastro = new UsuarioDTO().toDTOApensaId(item.getResponsavelCadastro());
		this.status = item.getStatus();
		this.titulo = item.getTitulo();
		
		this.acoes = new ArrayList<Long>();
		
		item.getAcoes().forEach(acao -> {
			this.acoes.add(new AcaoDTO().toDTO(acao).getId());
		});
		
		this.deliberacoes = new ArrayList<Long>();		
		
		item.getDeliberacoes().forEach(deliberacao ->{
			this.deliberacoes.add(new DeliberacaoDTO().toDTO(deliberacao).getId());
		});
		
		return this;
	}
	
	public Item toItem (Item item, Usuario responsavelDTO, Usuario responsavelCadastrado) {
		
		item.setDataCadastro(this.dataCadastro);
		item.setDescricao(this.descricao);
		item.setResponsavel(responsavelDTO);
		item.setResponsavelCadastro(responsavelCadastrado);
		item.setStatus(this.status);
		item.setTitulo(this.titulo);
		
		return item;
	}
	
}
