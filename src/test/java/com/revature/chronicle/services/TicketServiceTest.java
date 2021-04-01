package com.revature.chronicle.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.chronicle.daos.TicketRepo;
import com.revature.chronicle.models.Ticket;
import com.revature.chronicle.models.User;


@SpringBootTest
public class TicketServiceTest {

	@Mock
	private TicketRepo ticketRepo;
	
	@InjectMocks
	private Ticket mockTicket;
	
	@InjectMocks
	private TicketService ticketService;
		
	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		
		Ticket ticket1 =  new Ticket(1,"1","1","core java", "a","time","endTime","link1","pass1",10,"url1","PENDING","Id1","comments", new Date(20200101), new Date(20200104));
		Ticket ticket2 =  new Ticket(2,"10","10","core java", "a","time","endTime","link1","pass1",10,"url1","UNDER_REVIEW","Id1","comments", new Date(20200101), new Date(20200104));
		Ticket ticket3 =  new Ticket(3,"100","100","core java", "a","time","endTime","link1","pass1",10,"url1","IN_PROGRESS","Id1","comments", new Date(20200101), new Date(20200104));
		List<Ticket> myList= new ArrayList<>();
		myList.add(ticket1);
		myList.add(ticket2);
		myList.add(ticket3);
		when(ticketRepo.findAll()).thenReturn(myList);
		when(ticketRepo.save(new Ticket())).thenReturn(ticket1);
		when(ticketRepo.findByTicketID(1)).thenReturn(ticket1);
	}


	@Test
	public void testFindAll() {
		
		List<Ticket> result = ticketService.findAll();
		
		int i = 1;
		
		//Check if get all correct tickets from our Dummy database
		for(Ticket t: result) {
			
			//Check returned ticket's id with the tickets in our database
			Assert.assertEquals(i++, t.getTicketID());
		}
	
	}

	@Test
	public void ticketsByStatus() {
		
		List<Ticket> result = ticketService.ticketsByStatus("PENDING");
		
		//Check if get all correct tickets from our Dummy database
		for(Ticket t: result) {
			
			//Check returned ticket's status with the tickets in our database
			Assert.assertEquals("PENDING", t.getTicketStatus());
		}
	
	}

	@Test
	public void ticketsByEditor() {
		
		User user = new User ("100","myRoll","myEmail","myDisplayedName");
		List<Ticket> result = ticketService.ticketsByEditor(user);
		
		//Check if get all correct tickets from our Dummy database
		for(Ticket t: result) {
			
			//Check returned ticket's editorId with the tickets in our database
			Assert.assertEquals("100", t.getEditorID());
		}
	
	}

	
	@Test
	public void testSave() {
		
		Ticket result = ticketService.save(new Ticket());
		assertEquals(1, result.getTicketID());
	}

	@Test
	public void testUpdate() {
		
		Ticket ticket = new Ticket();
		ticket.setTicketID(1);
		boolean result = ticketService.update(ticket);
		assertEquals(true, result);
		
	}
	
	@Test
	public void saveAll() {
		
		boolean result = ticketService.saveAll(new ArrayList());
		assertTrue(result);
		
	}

}
