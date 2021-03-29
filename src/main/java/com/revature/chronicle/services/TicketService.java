package com.revature.chronicle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.chronicle.daos.TicketRepo;
import com.revature.chronicle.models.Ticket;

@Service(value="ticketService")
public class TicketService {
	@Autowired
	private TicketRepo ticketRepo;
	
	
	public List<Ticket> findAll(){
		return this.ticketRepo.findAll();
	}
	
	public Ticket save(Ticket ticket) {
		return this.ticketRepo.save(ticket);
	}
	
	public Ticket update(Ticket ticket) {
		String userId = ticket.getIssuerID();
		
		return this.ticketRepo.save(ticket);
		
	}
}
