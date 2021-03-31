package com.revature.chronicle.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.chronicle.daos.TicketRepo;
import com.revature.chronicle.models.Ticket;

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
	}


	@Test
	public void testFindAll() {
		
		Ticket ticket1 =  new Ticket(1,"1","1","core java", "a","time","endTime","link1","pass1",10,"url1","status1","Id1","comments");
		Ticket ticket2 =  new Ticket(2,"10","10","core java", "a","time","endTime","link1","pass1",10,"url1","status1","Id1","comments");
		Ticket ticket3 =  new Ticket(3,"100","100","core java", "a","time","endTime","link1","pass1",10,"url1","status1","Id1","comments");
		List<Ticket> myList= new ArrayList<>();
		myList.add(ticket1);
		myList.add(ticket2);
		myList.add(ticket3);
		
		when(ticketRepo.findAll()).thenReturn(myList);
		
		List<Ticket> result = ticketService.findAll();
		
		assertEquals(myList,result);
	}


	@Test
	public void testSave() {
		Ticket ticket1 =  new Ticket(1,"1","1","core java", "a","time","endTime","link1","pass1",10,"url1","status1","Id1","comments");
				
		when(ticketRepo.save(ticket1)).thenReturn(ticket1);
		
		Ticket result = ticketService.save(ticket1);
		
		assertEquals(ticket1, result);
	}

	@Test
	public void testUpdate() {
		Ticket ticket1 =  new Ticket(1,"1","1","core java", "a","time","endTime","link1","pass1",10,"url1","status1","Id1","comments");
		
		when(ticketRepo.findByTicketID(1)).thenReturn(ticket1);
		when(ticketRepo.save(ticket1)).thenReturn(ticket1);
		
		boolean result = ticketService.update(ticket1);
		
		assertEquals(true, result);
	}

}
