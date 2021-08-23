package br.com.meeting.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.meeting.model.Deliberacao;
import br.com.meeting.model.Usuario;

@Repository
public interface DeliberacaoRepository extends JpaRepository<Deliberacao, Long> {

	String PENDENTE = "PENDENTE";
	
//	@Query("select d "
//			+ "from Deliberacao d "
//			+ "join d.responsaveis dr "
//			+ "where dr.id = :pIdUsuario "
//			+ "and (d.dataLimite < :pHoje OR d.dataLimite BETWEEN > pHoje and :pDepoisAmanha"
//			+ "and d.item.status IN ('PENDENTE' , 'PENDENTE_COM_ATRASO', 'PENDENTE_EM_DIA')")
//	List<Deliberacao> findAllByResponsaveisOld(Long pIdUsuario, LocalDateTime pHoje, LocalDateTime pDepoisAmanha);

	@Query("select count(d) "
			+ "from Deliberacao d "
			+ "join d.responsaveis dr "
			+ "where dr.id = :pId "
			+ "and (d.dataLimite <= :pHoje "
			+ "or d.status = :pStatus) "
			+ "and d.status <> 'CANCELADO'")
	Integer findQtdByStatus(String pStatus, Long pId, Date pHoje);

	
	@Query("select d "
			+ "from Deliberacao d "
			+ "join d.responsaveis dr "
			+ "where dr.id = :pIdUsuario "
			+ "and (d.dataLimite <= :pHoje "
			+ "or d.status = :pStatus) "
			+ "and d.status <> 'CANCELADO' "
			+ "order by d.dataLimite asc")
	List<Deliberacao> findAtrasadasByResponsaveisCustom(Long pIdUsuario, Date pHoje, String pStatus);
	
	@Query("select d "
			+ "from Deliberacao d "
			+ "join d.responsaveis dr "
			+ "left join d.acoes da "
			+ "where dr.id = :pIdUsuario "
			+ "and d.status = :pStatus "
			+ "and d.status <> 'CANCELADO' "
			+ "order by d.dataLimite asc")
	List<Deliberacao> findPendentesByResponsavel(Long pIdUsuario, String pStatus);

	@Query("select d "
			+ "from Deliberacao d "
			+ "join d.responsaveis dr "
			+ "join d.acoes da "
			+ "where dr.id = :pIdUsuario "
			+ "and d.status = :pStatus "
			+ "and d.status <> 'CANCELADO' "
			+ "order by d.dataLimite asc")
	List<Deliberacao> findConcluidasByResponsavel(Long pIdUsuario, String pStatus);
	
	
//	@Query("select d "
//			+ "from Deliberacao d "
//			+ "join d.responsaveis dr "
//			+ "where dr.id = :pIdUsuario "
//			+ "and (d.status LIKE CONCAT(:pStatus, '%') "
//			+ "or d.status = 'ATRASADO') "
//			+ "and d.dataLimite < :pDepoisAmanha "
//			+ "order by d.dataLimite asc")
//	List<Deliberacao> findAtrasadasByResponsaveisCustom(Long pIdUsuario, Date pDepoisAmanha, String pStatus);

}
