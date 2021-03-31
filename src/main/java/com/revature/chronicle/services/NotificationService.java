package com.revature.chronicle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.chronicle.daos.NotificationRepo;
import com.revature.chronicle.models.Notification;

@Service(value = "notificationService")
public class NotificationService {

	@Autowired
	private NotificationRepo notificationRepository;
	
	// Get a notification
	public Notification getNotified(int ticketID) {
		return this.notificationRepository.findByTicket(ticketID);
	}
	
	// Create a new Notification
	public Notification createNotification(Notification notification) {
		return this.notificationRepository.save(notification);
	}
	
}
