package com.revature.chronicle.services;

import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.security.FirebaseInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service to handle business logic surrounding data access layer for tags
 */
@Service
public class TagService {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseInitializer.class);
    @Autowired
    private TagRepo tagRepo;

    public TagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    public List<Tag> findAll() {
        try {
            return tagRepo.findAll();
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
            return new ArrayList<Tag>();
        }

    }

    public Optional<Tag> findById(int id) {
        try{
            return tagRepo.findById(id);
        }
        catch (Exception e){
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    public boolean save(Tag tag) {
        try {
            tagRepo.save(tag);
            return true;
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }

    public boolean deleteByID(int tagID) {
        try {
            tagRepo.deleteById(tagID);
            return true;
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }
}
