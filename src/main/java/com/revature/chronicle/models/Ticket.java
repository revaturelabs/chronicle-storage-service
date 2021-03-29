package com.revature.chronicle.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket")
@Data
@NoArgsConstructor
public class Ticket {
	@Id
	@Column(name = "ticketid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ticketID;
	@Column(name = "issuerid", nullable = false)
	private String issuerID;
	@Column(name = "editorid")
	private String editorID;
	@Column(name = "topic", nullable = false)
	private String topic;
	@Column(name = "description")
	private String description;
	@Column(name = "starttime", nullable = false)
	private String startTime;
	@Column(name = "endtime", nullable = false)
	private String endTime;
	@Column(name = "zoomlink")
	private String zoomLink;
	@Column(name = "zoompasscode")
	private String zoomPasscode;
	@Column(name = "clipid")
	private int clipID;
	@Column(name = "clipurl")
	private String clipUrl;
	@Column(name = "status", nullable = false)
	private String status;
	@Column(name = "identifier", nullable = false)
	private String identifier;
	
	public Ticket(String issuerID, String editorID, String topic, String description, String startTime, String endTime,
			String zoomLink, String zoomPasscode, int clipID, String clipUrl, String status, String identifier) {
		super();
		this.issuerID = issuerID;
		this.editorID = editorID;
		this.topic = topic;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.zoomLink = zoomLink;
		this.zoomPasscode = zoomPasscode;
		this.clipID = clipID;
		this.clipUrl = clipUrl;
		this.status = status;
		this.identifier = identifier;
	}

}
