package com.revature.chronicle.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="notification")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
	
    @Id
    @Column(name="notification_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    
    
    @Column(name="sender_id")
    private String senderid;
    
    @Column(name="receiver_id")
    private String receiverid;
        
    @ManyToOne
    @JoinColumn(name="ticket_id")
    private Ticket tickeid;
    
    @Column(name="send_date")
    private Date senddate;
    
    @Column(name="note")
    private String note;


}
