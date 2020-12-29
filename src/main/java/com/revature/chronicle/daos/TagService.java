package com.revature.chronicle.daos;

import com.revature.chronicle.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    TagRepo tagRepo;

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
