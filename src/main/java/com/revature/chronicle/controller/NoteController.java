package com.revature.chronicle.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.chronicle.daos.NoteRepo;
import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.services.NoteService;

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

    /**
     * returns a list of <code>Note</code> objects in the response body, determined by the tags specified in the URI
     * path in the form 'TagID:TagKey:TagValue', separating multiple tags by the '+' character. The handler method is
     * mapped to the path '/notes/tags/{noteTags}' and produces media of type application-json. The method formats the
     * passed path variables into <code>Tag</code> objects and passes this formatted list into the <code>NoteService</code>
     * <code>findAllNotesByTags</code> method. The returned list of <code>Note</code> objects is returned int the
     * response body with an HTTP status code of 200.
     *
     * @param crudeTags URI path variable in the form 'TagID:TagKey:TagValue'
     * @return list of <code>Note</code> objects
     */
    // Can convert the path variable formatting clause into a service method which can be called in both controllers to reduce clutter
    @GetMapping(path = "tags/{noteTags}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Note>> getNotesByTag(HttpServletRequest request, @PathVariable(name="noteTags") String crudeTags){
        logger.info("Received request for notes with tags: " + crudeTags);
        String[] arrTags = crudeTags.split("\\+");
        List<Tag> targetTags = new ArrayList<>();
        for (String tag: arrTags) {
            Tag tempTag = new Tag();
            String[] tagComponents = tag.split(":");
            tempTag.setTagID(Integer.parseInt(tagComponents[0]));
            tempTag.setType(tagComponents[1]);
            tempTag.setValue(tagComponents[2]);
            targetTags.add(tempTag);
        }
        User user = (User) request.getAttribute("user");
        logger.info("Retrieving target notes...");
        List <Note> targetNotes = noteService.findAllNotesByTags(targetTags, user);
        return new ResponseEntity<>(targetNotes, HttpStatus.OK);
    }

    /**
     * returns a list of all <code>Note</code> objects in the database in the response body. The handler method is
     * mapped to the URI '/notes/all/' and produces media type of application-json. The handler retrieves the list through
     * the <code>NoteService</code> <code>findAll</code> method.
     * @return list of all <code>Note</code> objects
     */
    @GetMapping(path = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Note>> getAllNotes(HttpServletRequest request) {
        logger.info("Retrieving all notes...");
        User user = (User) request.getAttribute("user");
        List<Note> targetNotes = noteService.findAll(user);
        return new ResponseEntity<>(targetNotes, HttpStatus.OK);
    }

    /**
     * returns a list of all <code>Tag</code> objects in the database linked to a <code>Note</code> in the response
     * body. The handler method is mapped to the URI '/notes/available-tags/' and produces media type of application-json.
     * The handler retrieves the list through the <code>TagRepo</code> <code>findByNameIn</code> method. The tag keys are
     * determined by a list tagNames which cn be updated based on what keys exist in the database.
     * @return list of all <code>Note</code> objects
     */
    @GetMapping(path = "available-tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tag>> getAllNoteTags(HttpServletRequest request) {
        List<String> tagNames = new ArrayList<>();
        tagNames.add("Topic");
        tagNames.add("Batch");
        logger.info("Retrieving all note tags with keys: " + tagNames +" ...");
        List<Tag> availableTags = tagRepo.findByTypeIn(tagNames);
        logger.info("Tags retrieved: " + availableTags);
        return new ResponseEntity<>(availableTags, HttpStatus.OK);
    }

    /**
     * returns a <code>Note</code> object in the response body, determined by the specified noteID in the URI path.
     * The handler method is mapped to the URI 'notes/id/{noteId}' and returns media type of application-json. The
     * target video is retrieved via the <code>NoteService</code> <code>findById</code> method, passing in the URI
     * path variable.
     * @param id target <code>Note</code>'s ID
     * @return target <code>Note</code> object
     */
    @GetMapping(path = "id/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Note getNoteById(HttpServletRequest request, @PathVariable(name="noteId") int id) {
        logger.info("Retrieving target note with ID: " + id + " ...");
        Note targetNote = noteService.findById(id);
        return targetNote;
    }
    
    @PutMapping(path = "whitelist/{noteId}")
    public ResponseEntity<Void> updateWhitelist(HttpServletRequest request, @PathVariable(name="noteId") int noteId, @RequestBody List<User> users){
    	Note currentNote = this.noteService.findById(noteId);
    	currentNote.setWhitelist(users);
    	User user = (User) request.getAttribute("user");
    	this.noteService.update(currentNote, user);
    	return null;
    }
}
