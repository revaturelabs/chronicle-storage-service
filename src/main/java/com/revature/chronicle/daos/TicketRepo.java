package com.revature.chronicle.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.chronicle.models.Ticket;

@Repository(value="ticketRepo")
public interface TicketRepo extends JpaRepository<Ticket, Integer>{
	
	List<Ticket> findAll();
	
	Ticket save(Ticket ticket);
	

}
