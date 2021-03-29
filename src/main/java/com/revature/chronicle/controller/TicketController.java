package com.revature.chronicle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.chronicle.models.Ticket;
import com.revature.chronicle.services.TicketService;

@RestController
@RequestMapping(path="/ticket")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;

	
	//this endpoint returns a list of all tickets
	@GetMapping(path="all")
	List<Ticket> findAll(){
		return this.ticketService.findAll();
	}
	
	//this endpoint saves or updates a ticket
	@PostMapping(path="save", consumes = MediaType.APPLICATION_JSON_VALUE)
	Ticket save(@RequestBody Ticket ticket) {
		return this.ticketService.save(ticket);
	}
	
	//this endpoint saves or updates a ticket
		@PostMapping(path="update", consumes = MediaType.APPLICATION_JSON_VALUE)
		Ticket update(@RequestBody Ticket ticket) {
			return this.ticketService.update(ticket);
		}

}
