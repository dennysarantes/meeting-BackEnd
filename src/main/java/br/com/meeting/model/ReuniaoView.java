package br.com.meeting.model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import br.com.meeting.dto.ReuniaoDTO;

@Entity
@Immutable
@Table(name = "proximas_reunioes_sem_horario")
public class ReuniaoView  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name = "id")
	private Long id;
	@Column(name = "titulo")
	private String titulo;
	@Column(name = "data_cadastro")
	private LocalDate dataCadastro;
	@Column(name = "data_agendamento")
	private LocalDate dataAgendamento;
	@Column(name = "horario_agendamento")
	private Time horarioAgendamento; //Time.valueOf("18:45:20");
	@Column(name = "duracao")
	private Time duracao;
	@Column(name = "local")
	private String local;
	
	@Column(name = "status_reuniao")
	private StatusReuniao statusReuniao;
	
	@Column(name = "participantes_id")
	private Integer participantes;
	
	@Column(name = "reuniao_id")
	private Integer reuniao;
	
	public ReuniaoView() {}
	




	
	
	public Long getId() {
		return id;
	}







	public void setId(Long id) {
		this.id = id;
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







	public String getLocal() {
		return local;
	}







	public void setLocal(String local) {
		this.local = local;
	}







	public StatusReuniao getStatusReuniao() {
		return statusReuniao;
	}







	public void setStatusReuniao(StatusReuniao statusReuniao) {
		this.statusReuniao = statusReuniao;
	}







	public Integer getParticipantes() {
		return participantes;
	}







	public void setParticipantes(Integer participantes) {
		this.participantes = participantes;
	}







	public Integer getReuniao() {
		return reuniao;
	}







	public void setReuniao(Integer reuniao) {
		this.reuniao = reuniao;
	}







	public ReuniaoView(ReuniaoDTO reuniaoDTO, List<Usuario> participantes, List<Item> itens) {
		
		this.dataAgendamento = LocalDate.parse(reuniaoDTO.getDataAgendamento());
		this.dataCadastro = LocalDate.parse(reuniaoDTO.getDataCadastro());
		this.duracao = reuniaoDTO.getDuracao();
		this.horarioAgendamento = reuniaoDTO.getHorarioAgendamento();
		//this.itens = itens;
		this.local = reuniaoDTO.getLocal();
		//this.participantes = participantes;
		this.statusReuniao = reuniaoDTO.getStatusReuniao();
		this.titulo = reuniaoDTO.getTitulo();		
	}
	
	
}
