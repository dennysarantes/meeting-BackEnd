package br.com.meeting.repository.specification;

import java.awt.print.Book;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import br.com.meeting.model.Reuniao;

public interface Specification<T> {

	Predicate toPredicate(Root<T> root, CriteriaQuery query, CriteriaBuilder cb);
	
	static Specification<Reuniao> confirmacaoDoParticipante(Long idParticipante) {
	    return (confirmacao, cq, cb) -> cb.equal(confirmacao.get("participante.id"), idParticipante);
	}

	
	
}
