package com.revature.chronicle.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.chronicle.models.Notification;


@Repository(value="notificationRepository")
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
	
	List<Notification> findByReceiverId(String receiverId);
	
	// Create a new Notification
	Notification save(Notification notification);

}
