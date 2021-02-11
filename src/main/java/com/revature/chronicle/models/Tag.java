package com.revature.chronicle.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;

/**
 * A model representing a tag on a video or note
 */
@Entity
@Table(name="tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @Column(name="tag_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int tagID;

    @Column(name="type")
    private String type;

    @Column(name = "value")
    private String value;
    
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Video> vids;
    
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Note> notes;

    public Tag(String type, String value, List<Video> vids) {
        this.type = type;
        this.value = value;
//        this.vids = vids;
    }
    
}
