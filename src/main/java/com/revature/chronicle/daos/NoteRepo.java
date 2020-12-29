package com.revature.chronicle.daos;

import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for note data
 */
@Repository
public interface NoteRepo extends JpaRepository<Note, Integer> {
//    // Needs to be implemented, does not work
//    @Query(value = "SELECT * FROM note n INNER JOIN note_tag nt INNER JOIN tag t " +
//            "WHERE note.note_id = note_tag.note_id AND note_tag.tag_id = tag.tag_id " +
//            "AND tag.name = :tag_name AND tag.value = :tag_value")
//    List<Note> findByTag(@Param("tag_name") String tagName, @Param("tag_value") String tagValue);

    // JPA Functions:
    //      Optional<Note> findById(Integer id);       -->       Note getNote(int id);
    //      List<Note> findAll();                      -->       List<Note> getNotes();
    //      void save();                               -->       boolean addNote(Note note), boolean updateNote(Note note);
    //      void delete();                             -->       boolean deleteNote(Note note);
}
