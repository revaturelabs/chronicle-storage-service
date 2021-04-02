package com.revature.chronicle.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ticket")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
	
	@Id
	@Column(name = "ticket_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ticketID;

	@Column(name = "issuer_id", nullable = false)
	private String issuerID;

	@Column(name = "editor_id", nullable = true)
	private String editorID;

	@Column(name = "topic", nullable = false)
	private String topic;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "start_time", nullable = false)
	private String startTime;

	@Column(name = "end_time", nullable = false)
	private String endTime;

	@Column(name = "zoom_link", nullable = true)
	private String zoomLink;

	@Column(name = "zoom_passcode", nullable = true)
	private String zoomPasscode;

	@Column(name = "clip_id", nullable = true)
	private int clipID;

	@Column(name = "clip_url", nullable = true)
	private String clipUrl;

	@Column(name = "ticket_status", nullable = false)
	private String ticketStatus;

	@Column(name = "identifier", nullable = false)
	private String identifier;

	@Column(name="reject_comment", nullable = true)
	private String rejectcomment;

	@Column(name="date_issued", nullable = false)
	private Date dateIssued;
	
	@Column(name="date_accepted", nullable = true)
	private Date dateAccepted;

}

