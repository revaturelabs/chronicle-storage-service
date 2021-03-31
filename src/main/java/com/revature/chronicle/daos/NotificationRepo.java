package com.revature.chronicle.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.chronicle.models.Notification;
import com.revature.chronicle.models.Ticket;


@Repository(value="notificationRepository")
public interface NotificationRepo extends JpaRepository<Notification, Integer> {

		Notification findByTicketid(Ticket ticketid);

}
