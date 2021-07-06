package br.com.meeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meeting.dto.ReuniaoDTO;
import br.com.meeting.model.Reuniao;

@Repository
public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {

}
