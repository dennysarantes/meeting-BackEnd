package br.com.meeting.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.meeting.model.Confirmacao;
import br.com.meeting.model.Item;
import br.com.meeting.model.Reuniao;
import br.com.meeting.model.Usuario;

@Repository
public interface ReuniaoRepository extends JpaRepository<Reuniao, Long>{

	@Query("select r from Reuniao r join r.participantes rp where rp.id = :pId")
	List<Reuniao> findAllByUserID(Long pId);

	@Query("select r "
			+ "from Reuniao r "
			+ "join r.participantes rp "
			+ "where rp.id = :pId "
			+ "and "
			+ "r.dataAgendamento >= :pHoje "
			+ "order by r.dataAgendamento asc")
	List<Reuniao> findAllByUserIDMaiorQueHoje(Long pId, LocalDateTime pHoje);
	
	
	@Query("select distinct r "
			+ "from Reuniao r "
			+ "join FETCH r.confirmacoes rc "
			+ "join r.participantes rp "
			+ "where (rc.participante.id = :pId "
			+ "and rp.id = :pId )"
			+ "and "
			//+ "rc.participante.id = :pId "
			//+ "and "
			+ "r.dataAgendamento >= :pHoje "
			+ "order by r.dataAgendamento asc")
	List<Reuniao> findAllByUserIDMaiorQueHojeTest(Long pId, LocalDateTime pHoje);
	
	
	
	@Query("Select r.itens "
			+ "from Reuniao r "
			+ "where r.id = :pId")
	List<Item> findItensByReuniaoId(Long pId);

	
	@Query("Select distinct r.participantes "
			+ "from Reuniao r "
			+ "join r.participantes rp "
			+ "where r.id = :pId")
	List<Usuario> findParticipantesByReuniaoId(Long pId);

	@Query("select count(r) "
			+ "from Reuniao r "
			+ "join r.participantes rp "
			+ "where rp.id = :pId "
			+ "and "
			+ "r.statusReuniao = 'AGENDADA' "
			+ "and "
			+ "r.dataAgendamento >= :pHoje")
	Integer findQtdByUser(Long pId, LocalDateTime pHoje);
	
	
//	@Query("select distinct r "
//			+ "from Reuniao r "
//			//+ "join r.confirmacoes rc "
//			+ "join r.participantes rp "
//			+ "where rp.id <> :pId "
//			+ "and "
//			//+ "rc.reuniao.id not in :pReunioesID "
//			//+ "and "
//			+ "r.dataAgendamento >= :pHoje "
//			+ "and r.id not in :pReunioesID "
//			+ "order by r.dataAgendamento asc")
//	List<Reuniao> findAllProximasByNotUserID(Long pId, LocalDateTime pHoje, List<Long> pReunioesID);
//	
	
	@Query("select distinct r "
			+ "from Reuniao r "
			+ "join r.participantes rp "
			+ "where rp.id <> :pId "
			+ "and "
			+ "r.dataAgendamento >= :pHoje "
			+ "order by r.dataAgendamento asc")
	List<Reuniao> findAllProximasByNotUserID(Long pId, LocalDateTime pHoje);
	
	

	
	@Query("select distinct r "
			+ "from Reuniao r "
			+ "join r.participantes rp "
			+ "where rp.id = :pId "
			+ "and "
			+ "r.dataAgendamento <= :pHoje "
			+ "order by r.dataAgendamento asc")
	List<Reuniao> findAllByUserIDMenorQueHoje(Long pId, LocalDateTime pHoje);

	@Query("select r "
			+ "from Reuniao r "
			+ "join r.participantes rp "
			+ "where rp.id = :pId "
			+ "and "
			+ "r.dataAgendamento >= :pHoje "
			+ "order by r.dataAgendamento asc")
	List<Reuniao> findIdsReunioesByUserMaiorQueHoje(Long pId, LocalDateTime pHoje);

//	@Modifying
//	@Query("delete from Reuniao r "
//			+ "join r.participantes rp "
//			+ "where r.id = :pIdReuniao "
//			+ "and rp.id = :pIdUsuario")
//	void retirarUsuario(Long pIdReuniao, Long pIdUsuario);
	
	@Query("select distinct r.participantes "
			+ "from Reuniao r "
			+ "join r.participantes rp "
			+ "where r = :pIdReuniao ")
	List<Usuario> getParticipantesById(Long pIdReuniao);


	List<Reuniao> findByDataAgendamentoAfterAndIdNotOrderByDataAgendamentoAsc( LocalDateTime localDateTime, Long id);
	
}
