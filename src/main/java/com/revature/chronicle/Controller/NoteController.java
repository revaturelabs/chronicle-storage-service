package com.revature.chronicle.Controller;

import com.revature.chronicle.daos.NoteRepo;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.services.NoteService;
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

@RestController
@RequestMapping(path = "/notes")
public class NoteController {

    private NoteService noteService;
    private NoteRepo noteRepo;

    @Autowired
    public NoteController (NoteService ns, NoteRepo nr) {
        this.noteService = ns;
        this.noteRepo = nr;
    }

    @GetMapping(path = "tags/{noteTags}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Note>> getNotesByTag(@PathVariable(name="noteTags") String crudeTags){
        System.out.println(crudeTags);
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
}
