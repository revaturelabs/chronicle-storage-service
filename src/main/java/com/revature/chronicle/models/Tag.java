package com.revature.chronicle.models;


import lombok.Data;

import javax.persistence.*;

/**
 * A model representing a tag on a video or note
 */
@Entity
@Table(name="tag")
@Data
public class Tag {
    @Id
    @Column(name="tag_id", columnDefinition = "serial primary key")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int tagID;

    @Column(name="name")
    private String name;

    @Column(name = "value")
    private String value;
}
