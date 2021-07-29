package br.com.meeting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.meeting.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	//@Query("select i from Item i join i.participantes rp where rp.id = :pId")
	List<Item> findAllByResponsavelCadastro(Long pId);

}
