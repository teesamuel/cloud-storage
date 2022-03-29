package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNote(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getNotebyUser(Integer userId);

    @Select("SELECT * FROM NOTES ")
    Note getAllNotes();

    @Insert("INSERT INTO NOTES ( noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId} ) ")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Delete("Delete FROM NOTES WHERE noteId = #{noteId}")
    int deleteNote(Integer noteId);

    @Update(" UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{id} ")
    int updateNote(String noteTitle,String noteDescription, Integer id);
}
