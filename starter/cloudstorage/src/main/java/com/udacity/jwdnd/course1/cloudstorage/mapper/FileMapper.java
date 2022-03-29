package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    Files getFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userId = #{userId} and filename = #{filename} LIMIT 1 ")
    Files getFileByUserAndName(Integer userId,String filename);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<Files> getFileByUser(Integer userId);

    @Select("SELECT * FROM FILES")
    Files getAllFile();

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, filedata, userid) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{filedata}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(Files files);

    @Delete("Delete FROM FILES WHERE  fileId = #{fileId}")
    int deleteFileById(Integer fileId);

    @Update(" UPDATE FILES SET (filename, contenttype, filesize, filedata, userid) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId}) WHERE fileId = #{fileId} ")
    Files updateUser(Files file);
}
