package com.revature.chronicle.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.chronicle.models.Ticket;

@Repository(value="ticketRepo")
public interface TicketRepo extends JpaRepository<Ticket, Integer>{
	
	List<Ticket> findAll();
	
	Ticket save(Ticket ticket);
	
	Ticket findByTicketID(int TicketID);
	
	// Find by Editor id and ticket status
	@Query(value = "SELECT t FROM Ticket t WHERE t.editorID = :editorID AND t.ticketStatus = 'ACKNOWLEDGED' OR t.ticketStatus = 'IN_PROGRESS'")
	List<Ticket> findAllByEditorIDAndStatus(@Param("editorID") String editorID);
	
	}
