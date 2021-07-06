package br.com.meeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meeting.model.Acao;

@Repository
public interface AcaoRepository extends JpaRepository<Acao, Long> {

}
