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
	
//-----------------------------------------------------------------	
	
	//this method will return all tickets from the Ticket table
	public List<Ticket> findAll(){
		return this.ticketRepo.findAll();
	}
	
	public Ticket save(Ticket ticket) {
		return this.ticketRepo.save(ticket);
	}
	
	//this method will call the save method in the ticketRepository for each ticket in the list
	public boolean saveAll(List<Ticket> tickets) {
		for(Ticket t: tickets) {
		if(this.ticketRepo.save(t) == null) {
			return false;
		}
		}
		return true;
	}
	
	//this method will check if the ticket already exists in the Ticket table and update the ticket if there is a match
	public boolean update(Ticket ticket) {
		int x = ticket.getTicketID();
		if(ticketRepo.findByTicketID(x) != null) {
			this.ticketRepo.save(ticket);
			return true;
		}
		return false;	
	}
	
	public List<Ticket> ticketsByStatus(String status){
		List<Ticket> tickets = this.ticketRepo.findAll();
		tickets.removeIf(t -> !(t.getTicketStatus().equals(status)));
		return tickets;
	}
	
	//this method will create a new record in the notification table
	
	
}
