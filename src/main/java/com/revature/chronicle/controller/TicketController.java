package com.revature.chronicle.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.chronicle.models.Notification;
import com.revature.chronicle.models.Ticket;
import com.revature.chronicle.models.User;
import com.revature.chronicle.services.NotificationService;
import com.revature.chronicle.services.TicketService;

@RestController(value="ticketController")
@RequestMapping(path="/ticket")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private NotificationService notificationService;
	
	//this endpoint returns all notifications for the trainer
	@GetMapping(path="notifications")
	public ResponseEntity <List<Ticket>> getNotifications(HttpServletRequest req){
		 User user =  (User) req.getAttribute("user");
		 List<Ticket> tickets = ticketService.ticketsByEditor(user);
		 return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

	
	//this endpoint returns a list of submitted tickets
	@GetMapping(path="submitted")
	public ResponseEntity <List<Ticket>> findAllSubmittedTickets(){
		List<Ticket> tickets =  this.ticketService.findAll();
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}
	
	//this endpoint returns a list of all pending tickets
	@GetMapping(path="pending")
	public ResponseEntity <List<Ticket>> findAllPending(){
		 List<Ticket> tickets = ticketService.ticketsByStatus("PENDING");
		 return new ResponseEntity<>(tickets, HttpStatus.OK);
	}
	
	//this endpoint returns a list of all under review tickets
	@GetMapping(path="underreview")
	public ResponseEntity <List<Ticket>> findAllUderReview(){
		
			 List<Ticket> tickets = ticketService.ticketsByStatus("UNDER_REVIEW");
			 return new ResponseEntity<>(tickets, HttpStatus.OK);
		}
			
	
	//this endpoint returns a list of all under review tickets
		@GetMapping(path="ticketsforeditor")
		public ResponseEntity <List<Ticket>> findAllTicketsByEditor(HttpServletRequest req){
			
				 User user =  (User) req.getAttribute("user");
				 List<Ticket> tickets = ticketService.ticketsByEditor(user);
				 return new ResponseEntity<>(tickets, HttpStatus.OK);
			}
	
	
	
	
	//this endpoint saves array of tickets
		@PostMapping(path="saveall", consumes = MediaType.APPLICATION_JSON_VALUE)
		public boolean saveAll(@RequestBody List<Ticket> tickets) {
			Date dateIssued =new Date(System.currentTimeMillis());
			System.out.println("save all method");
			for(Ticket t: tickets) {
				t.setDateIssued(dateIssued);
			}
			return this.ticketService.saveAll(tickets);
		}
	
		
		
		
	//this endpoint updates a ticket by the editor
		@PostMapping(path="update", consumes = MediaType.APPLICATION_JSON_VALUE)
		public boolean update(@RequestBody Ticket ticket, HttpServletRequest req) {
			
			// Grab the current User id (editor)
			User user = (User) req.getAttribute("user");
			
			// Create a new Notification object
			Notification notification = new Notification();
			// Set the sender id to the current user id (editor id)
			notification.setSenderId(user.getUid());
			// Set the reciever id to the trainer's id (issuer id in the ticket object)
			notification.setReceiverId(ticket.getIssuerID());
			
			// Set the Date object to the Senddate field in the notification table
			notification.setSenddate(new Date(System.currentTimeMillis()));
			
			// Setting the note field for the notification object.
			notification.setNote(ticket.getTicketStatus());
			
			
			String status = ticket.getTicketStatus();
			
			switch(status) {
			//editor initiatates this endpoint
			case "ACKNOWLEDGED": 
				// Set editor id to the current user id
				ticket.setEditorID(user.getUid());
				//get current time for date accepted
				ticket.setDateAccepted(new Date(System.currentTimeMillis()));
				//make a message for the trainer
				notification.setNote("Ticket number "+ ticket.getTicketID()+ " has been acknowledged");
				break;
			//editor initiatates this endpoint	
			case "IN_PROGRESS":
				notification.setNote("Ticket number "+ ticket.getTicketID()+ " is in progress");
				break;
			case "UNDER_REVIEW":
				notification.setNote("Ticket number "+ ticket.getTicketID()+ " is available for you review");
				break;
			default: System.out.println("status is invalid");
			}
			
			this.notificationService.createNotification(notification);
			return this.ticketService.update(ticket);
		}
		
		@PostMapping(path="reject")
		public boolean reject(@RequestBody Ticket ticket) {
			Notification notification = new Notification();
			notification.setNote("Ticket number "+ ticket.getTicketID()+ " has been sent back for review.");
			notification.setReceiverId(ticket.getEditorID());
			notification.setSenderId(ticket.getIssuerID());
			notification.setSenddate(new Date(System.currentTimeMillis()));
			notificationService.createNotification(notification);
			return this.ticketService.update(ticket);
		}
		
		//trainer accepted the clip and closes the ticket
		@PostMapping(path="accept")
		public boolean accept(@RequestBody Ticket ticket) {
			Notification notification = new Notification();
			notification.setNote("Ticket number "+ ticket.getTicketID()+ " has been accepted");
			notification.setReceiverId(ticket.getEditorID());
			notification.setSenderId(ticket.getIssuerID());
			notification.setSenddate(new Date(System.currentTimeMillis()));
			notificationService.createNotification(notification);
			return this.ticketService.update(ticket);
		}
		
		//trainer deactivates the ticket
		@PostMapping(path="deactivate")
		public boolean deactivate(@RequestBody Ticket ticket) {
			Notification notification = new Notification();
			notification.setNote("Ticket number "+ ticket.getTicketID()+ " has been deactivated");
			notification.setReceiverId(ticket.getEditorID());
			notification.setSenderId(ticket.getIssuerID());
			notification.setSenddate(new Date(System.currentTimeMillis()));
			notificationService.createNotification(notification);
			return this.ticketService.update(ticket);
		}

}
