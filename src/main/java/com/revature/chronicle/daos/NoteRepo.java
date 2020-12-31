package com.revature.chronicle.daos;

import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.Video;
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
    /**
     * Finds notes with an offset and limit used for paging
     * @param offset where to begin
     * @param limit how many records should be returned
     * @return the notes
     */
    @Query(value = "select * from note n order by n.date asc offset ?1 fetch next ?2 rows only",nativeQuery = true)
    List<Note> findNotesWithOffsetAndLimit(int offset, int limit);
}
