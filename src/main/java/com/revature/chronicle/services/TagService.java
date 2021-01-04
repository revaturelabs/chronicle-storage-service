package com.revature.chronicle.services;

import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service to handle business logic surrounding data access layer for tags
 */
@Service
public class TagService {
    @Autowired
    private TagRepo tagRepo;

    public TagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    public List<Tag> findAll() {
        return tagRepo.findAll();
    }

    public Optional<Tag> findById(int tagID) {
        return tagRepo.findById(tagID);
    }

    public boolean save(Tag tag) {
        tagRepo.save(tag);
        return true;
    }

    public boolean deleteByID(int tagID) {
        tagRepo.deleteById(tagID);
        return true;
    }
}
