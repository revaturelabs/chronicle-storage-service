package com.revature.chronicle.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ticket_notification")
@Data
@NoArgsConstructor
public class TicketNotification {
    @Id
    @Column(name="notificationid")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int notificationId;
        
    @Column(name="senderid")
    private String senderId;
    
    @Column(name="receiverid")
    private String receiverId;
	
    @ManyToOne
    @JoinColumn(name = "ticketid", referencedColumnName = "ticketid")
    private Ticket ticket;
 
    @Column(name="send_time")
    private Date send_date;

    @Column(name="note")
    private String note;

	public TicketNotification(String senderId, String receiverId, Ticket ticket, Date send_date, String note) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.ticket = ticket;
		this.send_date = send_date;
		this.note = note;
	}
    
	
}
