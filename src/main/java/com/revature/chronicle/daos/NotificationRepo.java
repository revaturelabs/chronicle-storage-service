package com.revature.chronicle.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.chronicle.models.Notification;


@Repository(value="notificationRepository")
public interface NotificationRepo extends JpaRepository<Notification, Integer> {

	// Find a notification by it's ticket
//	@Query(value = "SELECT n FROM Notification n WHERE n.ticket.ticketId = :ticketId")
//	Notification findByTicket(Ticket ticket);
	
	@Query(value = "SELECT n FROM Notification n WHERE n.ticket.ticketID = :ticketId")
	Notification findByTicket(@Param("ticketId") int ticketId);
	
	// Create a new Notification
	Notification save(Notification notification);

}
