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
	ResponseEntity <List<Ticket>> findAll(){
		List<Ticket> ticket2 =  this.ticketService.findAll();
		return new ResponseEntity<>(ticket2, HttpStatus.OK);
	}
	
	
	//this endpoint saves array of tickets
		@PostMapping(path="saveall", consumes = MediaType.APPLICATION_JSON_VALUE)
		public boolean saveAll(@RequestBody List<Ticket> tickets) {
			return this.ticketService.saveAll(tickets);
		}
	
	//this endpoint saves or updates a ticket
		@PostMapping(path="update", consumes = MediaType.APPLICATION_JSON_VALUE)
		public boolean update(@RequestBody Ticket ticket, HttpServletRequest req) {
			// Grab the current User id (editor)
			User user = (User) req.getAttribute("user");
			// Set editor id to the current user id
			ticket.setEditorID(user.getUid());
			// Send the ticket through the update method in the ticket service layer
			//this.ticketService.update(ticket);
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
			
			// Send the newly created Notification to the service layer to create a new notification record
			this.notificationService.createNotification(notification);
			
			return this.ticketService.update(ticket);
		}

}
