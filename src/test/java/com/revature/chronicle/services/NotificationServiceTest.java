package com.revature.chronicle.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.chronicle.daos.NotificationRepo;
import com.revature.chronicle.models.Notification;
import com.revature.chronicle.models.Ticket;

@SpringBootTest
class NotificationServiceTest {
	
	@Mock
	private NotificationRepo notificationRepo;
	
	@InjectMocks
	private NotificationService notificationService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetNotified() {
		
		Ticket ticket =  new Ticket(1,"1","1","core java", "a","time","endTime","link1","pass1",10,"url1","status1","Id1","comments", new Date(20200101), new Date(20200104));
		Notification notification = new Notification(1, "1", "1", ticket, new Date(03302021), "First ticket");
		List<Notification> notificationList = new ArrayList<>();
		notificationList.add(notification);
		
		when(notificationRepo.findByReceiverId("1")).thenReturn(notificationList);
		
		List<Notification> result = notificationService.getNotified("1");
		
		assertEquals(notificationList,result);
	}

	@Test
	void testCreateNotification() {
		
		Ticket ticket =  new Ticket(1,"1","1","core java", "a","time","endTime","link1","pass1",10,"url1","status1","Id1","comments", new Date(20200101), new Date(20200104));
		Notification notification = new Notification(1, "me", "you", ticket, new Date(03302021), "First ticket");

		when(notificationRepo.save(notification)).thenReturn(notification);
		
		Notification result = notificationService.createNotification(notification);
		
		assertEquals(notification,result);
	}

}
