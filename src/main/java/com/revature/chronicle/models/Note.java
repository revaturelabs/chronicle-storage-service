package com.revature.chronicle.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "note_tag",
            joinColumns = @JoinColumn(name = "note_id", referencedColumnName = "note_id", columnDefinition = "INT"),
            inverseJoinColumns = @JoinColumn(name = "tag_id",referencedColumnName = "tag_id", columnDefinition = "INT"))
    private List<Tag> tags;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "note_whitelist",
    			joinColumns = @JoinColumn(name = "note_id", referencedColumnName = "note_id", columnDefinition = "INT"),
    			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    private List<User> whitelist;

    public Note(String description, Date date, String user, List<Tag> tags) {
    	super();
        this.description = description;
        this.date = date;
        this.user = user;
    }
}
