package com.revature.chronicle.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * A video uploaded by a user. Will be streamed and viewable in Chronicle.
 */
@Entity
@Table(name = "video")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    @Column(name = "video_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int videoID;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    @CreationTimestamp
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", columnDefinition = "INT")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "video_tag",
            joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "video_id", columnDefinition = "INT"),
            inverseJoinColumns = @JoinColumn(name = "tag_id",referencedColumnName = "tag_id", columnDefinition = "INT"))
    private Set<Tag> videoTags;
}
