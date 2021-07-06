package br.com.meeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meeting.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
