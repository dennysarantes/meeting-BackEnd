package br.com.meeting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.meeting.model.Deliberacao;
import br.com.meeting.model.Usuario;

@Repository
public interface DeliberacaoRepository extends JpaRepository<Deliberacao, Long> {

	@Query("select d "
			+ "from Deliberacao d "
			+ "join d.responsaveis dr "
			+ "where dr.id = :pId "
			+ "and d.item.status NOT IN ('CONCLUIDO' , 'CANCELADO')")
	List<Deliberacao> findAllByResponsaveis(Long pId);

	@Query("select count(d) "
			+ "from Deliberacao d "
			+ "join d.responsaveis dr "
			+ "where dr.id = :pId "
			+ "and d.status = :pStatus")
	Integer findQtdByStatus(String pStatus, Long pId);

}
