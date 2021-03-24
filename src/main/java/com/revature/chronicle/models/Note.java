package com.revature.chronicle.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A note uploaded by a user. Can be any file type
 */
@Entity
@Table(name="note")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note extends Media{
    @Id
    @Column(name="note_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="url", nullable = false)
    private String url;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date", nullable = false)
    @CreationTimestamp
    private Date date;

    @Column(name = "user_id", nullable = false)
    private String user;
    
    @Column(name = "display_name", nullable = false)
    private String displayName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "note_tag",
            joinColumns = @JoinColumn(name = "note_id", referencedColumnName = "note_id", columnDefinition = "INT"),
            inverseJoinColumns = @JoinColumn(name = "tag_id",referencedColumnName = "tag_id", columnDefinition = "INT"))
    private List<Tag> tags;
    
    @Column
    @ElementCollection(targetClass = String.class)
    private List<String> whitelist;
    
    @Column(name = "private", nullable = false)
    private boolean isPrivate;
    

    public Note(String description, Date date, String user, String displayName, List<Tag> tags, boolean isPrivate) {
    	super();
        this.description = description;
        this.date = date;
        this.user = user;
        this.displayName = displayName;
        this.whitelist = new ArrayList<>();
        this.isPrivate = isPrivate;
    }
    
    public Note(String description, Date date, String user, String displayName, List<Tag> tags, boolean isPrivate, List<String> users) {
    	super();
        this.description = description;
        this.date = date;
        this.user = user;
        this.displayName = displayName;
        this.whitelist = users;
        this.isPrivate = isPrivate;
    }

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUrl(String url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDate(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUser(String user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Tag> getTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTags(List<Tag> tags) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getWhitelist() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWhitelist(List<String> users) {
		// TODO Auto-generated method stub
		
	}
    
}
