package com.revature.chronicle.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.google.api.services.storage.model.Notification;
@Repository(value="notificationRepository")
public interface NotificationRepo extends JpaRepository<Notification, Integer> {

		Notification findByTicketid(int ticketid);

}
