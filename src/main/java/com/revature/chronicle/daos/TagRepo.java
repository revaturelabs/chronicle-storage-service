package com.revature.chronicle.daos;

import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.Video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Data access object interface for tag data
 */
@Repository
public interface TagRepo extends JpaRepository<Tag, Integer> {

    List<Tag> findByTypeIn(Collection<String> tagNames);

    Tag findByValue(String value);

}
