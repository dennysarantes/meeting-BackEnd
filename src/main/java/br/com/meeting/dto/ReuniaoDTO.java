package br.com.meeting.dto;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.meeting.model.Item;
import br.com.meeting.model.Reuniao;
import br.com.meeting.model.StatusReuniao;
import br.com.meeting.model.Usuario;


public class ReuniaoDTO {

	
	private Long id;
	private String titulo;
	private LocalDate dataCadastro;
	private LocalDate dataAgendamento;
	private Time horarioAgendamento; //Time.valueOf("18:45:20");
	private Time duracao;
	private String local;
	
	@Enumerated(EnumType.STRING)
	private StatusReuniao statusReuniao;
	
	
	private List<Long> participantes;
	
	private List<Long> itens;
	
	public Long getId() {
		return id;
	}
	
	public String getLocal() {
		return local;
	}

	public List<Long> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Long> participantes) {
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

	public String getDataCadastro() {
		return dataCadastro.toString();
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getDataAgendamento() {
		return dataAgendamento.toString();
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

	public List<Long> getItens() {
		return itens;
	}

	public void setItens(List<Long> itens) {
		this.itens = itens;
	}

	@Override
	public String toString() {
		return "Reuniao [id=" + id + ", titulo=" + titulo + ", dataCadastro=" + dataCadastro + ", dataAgendamento="
				+ dataAgendamento + ", horarioAgendamento=" + horarioAgendamento + ", duracao=" + duracao
				+ ", statusReuniao=" + statusReuniao + ", itens=" + itens + "]";
	}

	public ReuniaoDTO toDTO(Reuniao reuniao) {
		
		this.id = reuniao.getId();
		this.dataAgendamento = reuniao.getDataAgendamento();
		this.dataCadastro = reuniao.getDataCadastro();
		this.duracao = reuniao.getDuracao();
		this.horarioAgendamento = reuniao.getHorarioAgendamento();
		this.local = reuniao.getLocal();
		this.statusReuniao = reuniao.getStatusReuniao();
		this.titulo = reuniao.getTitulo();
		
		this.participantes = new ArrayList<Long>();
		
		reuniao.getParticipantes().forEach(participante -> {
			System.out.println("nome participante: " + participante.getNome());			
			this.participantes.add(new UsuarioDTO().toDTOApensaId(participante));
		});
			
		
		this.itens = new ArrayList<Long>();
		
		reuniao.getItens().forEach(item -> {
			this.itens.add(new ItemDTO().toDTO(item).getId());
		});

		return this;
	}

	public Reuniao toReuniao(Reuniao reuniao, List<Usuario> participantes, List<Item> itens) {
		
		reuniao.setDataAgendamento(this.dataAgendamento);
		reuniao.setDataCadastro(this.dataCadastro);
		reuniao.setDuracao(this.duracao);
		reuniao.setHorarioAgendamento(this.horarioAgendamento);
		reuniao.setItens(itens);
		reuniao.setLocal(this.local);
		reuniao.setParticipantes(participantes);
		reuniao.setStatusReuniao(this.statusReuniao);
		reuniao.setTitulo(this.titulo);
				
		return reuniao;
	}
	
	
	
}
