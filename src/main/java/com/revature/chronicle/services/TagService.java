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
        try {
            return tagRepo.findAll();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<Tag>;
        }

    }

    public Optional<Tag> findById(int tagID) {
        try{
            return tagRepo.findById(id);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public boolean save(Tag tag) {
        try {
            tagRepo.save(tag);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteByID(int tagID) {
        try {
            tagRepo.deleteById(tagID);
            return true;
        }
        catch {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
