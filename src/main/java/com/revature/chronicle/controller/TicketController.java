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
import org.threeten.bp.LocalDateTime;

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

	
	//this endpoint returns a list of all tickets
	@GetMapping(path="all")
	public ResponseEntity <List<Ticket>> findAllSubmittedTickets(){
		List<Ticket> tickets =  this.ticketService.findAll();
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}
	
	//this endpoint returns a list of all pending tickets
	@GetMapping(path="pendingTickets")
	public ResponseEntity <List<Ticket>> findAllPending(){
		 List<Ticket> tickets = ticketService.ticketsByStatus("PENDING");
		 return new ResponseEntity<>(tickets, HttpStatus.OK);
	}
	
	//this endpoint returns a list of all under review tickets
	@GetMapping(path="underReviewTickets")
	public ResponseEntity <List<Ticket>> findAllUderReview(){
		
			 List<Ticket> tickets = ticketService.ticketsByStatus("UNDER_REVIEW");
			 return new ResponseEntity<>(tickets, HttpStatus.OK);
		}
			
	
	//this endpoint returns a list of all under review tickets
		@GetMapping(path="ticketsForEditor")
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
	
		
		
		
	//this endpoint saves or updates a ticket
		@PostMapping(path="update", consumes = MediaType.APPLICATION_JSON_VALUE)
		public boolean update(@RequestBody Ticket ticket, HttpServletRequest req) {
			
			// Grab the current User id (editor)
			User user = (User) req.getAttribute("user");
			
			// Create a new Notification object
			Notification notification = new Notification();
			// Set the sender id to the current user id (editor id)
			notification.setSenderid(user.getUid());
			// Set the reciever id to the trainer's id (issuer id in the ticket object)
			notification.setReceiverid(ticket.getIssuerID());

			// Get the current local date and time
			LocalDateTime date = LocalDateTime.now();
			
			// Get the integer values of the day, month, year, hour, minute, and second for the current date
			int dayOfYear = date.getDayOfYear();
			int monthOfYear = date.getMonthValue();
			int year = date.getYear();
			int hour = date.getHour();
			int minute = date.getMinute();
			int second = date.getSecond();
			
			// Convert the integer values of each aspect of the current date into string variables
			String day = String.valueOf(dayOfYear);
			String month = String.valueOf(monthOfYear);
			String year2 = String.valueOf(year);
			String hour2 = String.valueOf(hour);
			String minute2 = String.valueOf(minute);
			String second2 = String.valueOf(second);
			
			// Concatinate the string type versions of the numbers
			String stringDate = month + day + year2 + hour2 + minute2 + second2;
			
			// Convert the concatinated string into a long type
			long intDate = Long.parseLong(stringDate);
			
			// Create a new SQL Date object 
			Date today = new Date(intDate);
			
			// Set the Date object to the Senddate field in the notification table
			notification.setSenddate(today);
			
			// Setting the note field for the notification object.
			notification.setNote(ticket.getTicketStatus());
			
			
			String status = ticket.getTicketStatus();
			
			switch(status) {
			//trainer initiatates this endpoint
			case "PENDING": 
				notification.setNote("Ticket number "+ ticket.getTicketID()+ " is pending");
				break;
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
			case "closed":
				notification.setNote("Video associated with ticket number "+ ticket.getTicketID()+ " has been closed");
				break;
			case "DEACTIVATED":
				notification.setNote("Ticket number "+ ticket.getTicketID()+ " has been deactivated");
				break;
			default: System.out.println("status is invalid");
			}
			
			this.notificationService.createNotification(notification);
			return this.ticketService.update(ticket);
		}
		
		@PostMapping(path="reject")
		public boolean reject(@RequestBody Ticket ticket) {
			ticket.setTicketStatus("in progress");
			Notification notification = new Notification();
			notification.setNote("Ticket number "+ ticket.getTicketID()+ " has been rejected");
			notification.setReceiverid(ticket.getEditorID());
			notification.setSenderid(ticket.getIssuerID());
			notification.setSenddate(new Date(System.currentTimeMillis()));
			notificationService.createNotification(notification);
			return this.ticketService.update(ticket);
		}
		
		//trainer accepted the clip and closes the ticket
		@PostMapping(path="accept")
		public boolean accept(@RequestBody Ticket ticket) {
			ticket.setTicketStatus("closed");
			Notification notification = new Notification();
			notification.setNote("Ticket number "+ ticket.getTicketID()+ " has been accepted");
			notification.setReceiverid(ticket.getEditorID());
			notification.setSenderid(ticket.getIssuerID());
			notification.setSenddate(new Date(System.currentTimeMillis()));
			notificationService.createNotification(notification);
			return this.ticketService.update(ticket);
		}
		
		//trainer deactivates the ticket
		@PostMapping(path="deactivate")
		public boolean deactivate(@RequestBody Ticket ticket) {
			ticket.setTicketStatus("closed");
			Notification notification = new Notification();
			notification.setNote("Ticket number "+ ticket.getTicketID()+ " has been deactivated");
			notification.setReceiverid(ticket.getEditorID());
			notification.setSenderid(ticket.getIssuerID());
			notification.setSenddate(new Date(System.currentTimeMillis()));
			notificationService.createNotification(notification);
			return this.ticketService.update(ticket);
		}
		
		

}
