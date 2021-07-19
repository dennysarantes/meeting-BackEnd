package br.com.meeting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.meeting.dto.ReuniaoDTO;
import br.com.meeting.model.Reuniao;

@Repository
public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {

	@Query("select r from Reuniao r join r.participantes rp where rp.id = :pId")
	List<Reuniao> findAllByUserID(Long pId);

}
