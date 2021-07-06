package br.com.meeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meeting.model.Deliberacao;

@Repository
public interface DeliberacaoRepository extends JpaRepository<Deliberacao, Long> {

}
