package br.com.meeting.model;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import br.com.meeting.dto.ReuniaoDTO;

@Entity
public class Reuniao {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private LocalDate dataCadastro;
	private LocalDate dataAgendamento;
	private Time horarioAgendamento; //Time.valueOf("18:45:20");
	private Time duracao;
	private String local;
	
	@Enumerated(EnumType.STRING)
	private StatusReuniao statusReuniao;
	
	@ManyToMany
	private List<Usuario> participantes;
	
	@ManyToMany
	private List<Item> itens;
	
	public Reuniao() {}
	

	public Long getId() {
		return id;
	}
	
	public String getLocal() {
		return local;
	}



	public List<Usuario> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Usuario> participantes) {
		this.participantes = participantes;
	}

	public void setLocal(String local) {
		this.local = local;
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

	public LocalDate getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(LocalDate dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public Time getHorarioAgendamento() {
		return horarioAgendamento;
	}

	public void setHorarioAgendamento(Time horarioAgendamento) {
		this.horarioAgendamento = horarioAgendamento;
	}

	public Time getDuracao() {
		return duracao;
	}

	public void setDuracao(Time duracao) {
		this.duracao = duracao;
	}

	public StatusReuniao getStatusReuniao() {
		return statusReuniao;
	}

	public void setStatusReuniao(StatusReuniao statusReuniao) {
		this.statusReuniao = statusReuniao;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	@Override
	public String toString() {
		return "Reuniao [id=" + id + ", titulo=" + titulo + ", dataCadastro=" + dataCadastro + ", dataAgendamento="
				+ dataAgendamento + ", horarioAgendamento=" + horarioAgendamento + ", duracao=" + duracao
				+ ", statusReuniao=" + statusReuniao + ", itens=" + itens + "]";
	}
	
	
	public Reuniao(ReuniaoDTO reuniaoDTO, List<Usuario> participantes, List<Item> itens) {
		
		this.dataAgendamento = LocalDate.parse(reuniaoDTO.getDataAgendamento());
		this.dataCadastro = LocalDate.parse(reuniaoDTO.getDataCadastro());
		this.duracao = reuniaoDTO.getDuracao();
		this.horarioAgendamento = reuniaoDTO.getHorarioAgendamento();
		this.itens = itens;
		this.local = reuniaoDTO.getLocal();
		this.participantes = participantes;
		this.statusReuniao = reuniaoDTO.getStatusReuniao();
		this.titulo = reuniaoDTO.getTitulo();		
	}
	
	
}
