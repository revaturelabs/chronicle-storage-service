package com.revature.chronicle.daos;

import com.revature.chronicle.models.Tag;

import java.util.List;

/**
 * Data access object interface for tag data
 */
public interface TagDAO {
    public List<Tag> getTags();
    public Tag getTagByID(int tagID);

    public boolean addTag(Tag tag);
    public boolean updateTag(Tag tag);
    public boolean deleteTagByID(int tagID);
}
