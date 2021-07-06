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

import br.com.meeting.model.Deliberacao;
import br.com.meeting.model.Item;
import br.com.meeting.model.Usuario;

public class DeliberacaoDTO {

	private Long id;
	private String descricao;
	private Date dataLimite;
	
	private List<Long> responsaveis;
	
	private Long item;
	


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
		this.descricao = deliberacao.getDescricao();
		this.id = deliberacao.getId();
		this.item = deliberacao.getItem().getId();
		
		
		this.responsaveis = new ArrayList<Long>();
		
		deliberacao.getResponsaveis().forEach(responsavel ->{
			this.responsaveis.add(new UsuarioDTO().toDTOApensaId(responsavel));
		});
		
		
		
		return this;
	}

	public Deliberacao toDeliberacao(Deliberacao deliberacao, List<Usuario> responsaveis, Item item) {
		
		deliberacao.setDataLimite(this.dataLimite);
		deliberacao.setDescricao(this.descricao);
		deliberacao.setItem(item);
		deliberacao.setResponsaveis(responsaveis);
		
		return deliberacao;
	} 
	
	

}
