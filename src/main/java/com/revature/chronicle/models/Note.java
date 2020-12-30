package com.revature.chronicle.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * A note uploaded by a user. Can be any file type
 */
@Entity
@Table(name="note")
@Data
public class Note {
    @Id
    @Column(name="note_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteID;

    @Column(name="url")
    private String url;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id", columnDefinition = "INT")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "note_tag",
            joinColumns = @JoinColumn(name = "note_id", referencedColumnName = "note_id", columnDefinition = "INT"),
            inverseJoinColumns = @JoinColumn(name = "tag_id",referencedColumnName = "tag_id", columnDefinition = "INT"))
    private Set<Tag> noteTags;
}
