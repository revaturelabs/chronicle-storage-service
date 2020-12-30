package com.revature.chronicle.daos;

import com.revature.chronicle.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Data access object interface for tag data
 */
public interface TagRepo extends JpaRepository<Tag, Integer> {
}
