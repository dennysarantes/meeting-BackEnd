package br.com.meeting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.meeting.model.Confirmacao;
import br.com.meeting.model.Usuario;

@Repository
public interface ConfirmacaoRepository extends JpaRepository<Confirmacao, Long> {

	
	@Query("select distinct c "
			+ "from Confirmacao c "
			+ "where "
			+ "c.participante.id = :pIdUsuario "
			+ "and c.reuniao.id IN :pListaIdsReunioes ")
	List<Confirmacao> findAllByReuniaoIdAndUsuarioIDX(List<Long> pListaIdsReunioes, Long pIdUsuario);

	@Query("select distinct c "
			+ "from Confirmacao c "
			+ "where "
			+ "c.participante.id = :pIdUsuario ")
			//+ "and c.reuniao IN :pListaIdsReunioes ")
	List<Confirmacao> findAllByReuniaoIdAndUsuarioID(Long pIdUsuario);
	
}
