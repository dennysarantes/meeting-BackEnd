package br.com.meeting.repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.meeting.model.Reuniao;

@Repository
public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {

	@Query("select r from Reuniao r join r.participantes rp where rp.id = :pId")
	List<Reuniao> findAllByUserID(Long pId);

	@Query("select r "
			+ "from Reuniao r "
			+ "join r.participantes rp "
			+ "where rp.id = :pId "
			+ "and "
			+ "r.dataAgendamento >= :pHoje "
			+ "and r.horarioAgendamento > :pHorario "
			+ "order by r.dataAgendamento asc")
	List<Reuniao> findAllByUserIDMaiorQueHoje(Long pId, LocalDate pHoje, Time pHorario);
	
	
	


	@Query("select count(r) "
			+ "from Reuniao r "
			+ "join r.participantes rp "
			+ "where rp.id = :pId "
			+ "and "
			+ "r.statusReuniao = 'AGENDADA' "
			+ "and "
			+ "r.dataAgendamento >= :pHoje")
	Integer findQtdByUser(Long pId, LocalDate pHoje);
	
	
	@Query("select distinct r "
			+ "from Reuniao r "
			+ "join r.participantes rp "
			+ "where rp.id <> :pId "
			+ "and "
			+ "r.dataAgendamento >= :pHoje "
			+ "and r.id not in :pReunioesID "
			+ "order by r.dataAgendamento asc")
	List<Reuniao> findAllProximasByNotUserID(Long pId, LocalDate pHoje, List<Long> pReunioesID);

	
	@Query("select r "
			+ "from Reuniao r "
			+ "join r.participantes rp "
			+ "where rp.id = :pId "
			+ "and "
			+ "r.dataAgendamento <= :pHoje"
			+ " order by r.dataAgendamento asc")
	List<Reuniao> findAllByUserIDMenorQueHoje(Long pId, LocalDate pHoje);

	@Query("select r "
			+ "from Reuniao r "
			+ "join r.participantes rp "
			+ "where rp.id = :pId "
			+ "and "
			+ "r.dataAgendamento >= :pHoje "
			+ "and r.horarioAgendamento > :pHorario "
			+ "order by r.dataAgendamento asc")
	List<Reuniao> findIdsReunioesByUserMaiorQueHoje(Long pId, LocalDate pHoje, Time pHorario);
}
