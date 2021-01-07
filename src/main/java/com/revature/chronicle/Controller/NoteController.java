package com.revature.chronicle.Controller;

import com.revature.chronicle.daos.NoteRepo;
import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.Video;
import com.revature.chronicle.services.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/notes")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    private final NoteService noteService;
    private final NoteRepo noteRepo;
    private final TagRepo tagRepo;

    @Autowired
    public NoteController (NoteService ns, NoteRepo nr, TagRepo tr) {
        this.noteService = ns;
        this.noteRepo = nr;
        this.tagRepo = tr;
    }

    @GetMapping(path = "tags/{noteTags}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Note>> getNotesByTag(@PathVariable(name="noteTags") String crudeTags){
        logger.info(crudeTags);
        String[] arrTags = crudeTags.split("\\+");
        List<Tag> targetTags = new ArrayList<>();
        for (String tag: arrTags) {
            Tag tempTag = new Tag();
            String[] tagComponents = tag.split(":");
            tempTag.setName(tagComponents[0]);
            tempTag.setValue(tagComponents[1]);
            targetTags.add(tempTag);
        }
        List <Note> targetNotes = noteService.findAllNotesByTags(targetTags);
        return new ResponseEntity<>(targetNotes, HttpStatus.OK);
    }

    @GetMapping(path = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> targetNotes = noteService.findAll();
        return new ResponseEntity<>(targetNotes, HttpStatus.OK);
    }

    @GetMapping(path = "available-tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tag>> getAllNoteTags() {
        List<String> tagNames = new ArrayList<>();
        tagNames.add("Technology");
        tagNames.add("Batch");
        List<Tag> availableTags = tagRepo.findByNameIn(tagNames);
        return new ResponseEntity<>(availableTags, HttpStatus.OK);
    }

    @GetMapping(path = "id/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Note> getNoteById(@PathVariable(name="noteId") int id) {
        Optional<Note> targetNote = noteService.findById(id);
        return targetNote.map(note -> new ResponseEntity<>(note, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
