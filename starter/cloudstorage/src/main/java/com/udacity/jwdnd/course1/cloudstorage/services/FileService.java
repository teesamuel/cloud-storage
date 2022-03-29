package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public  int createFile(Files files){
        return fileMapper.insert(files);
    }

    public boolean isUserFileAvailable(Integer userId) {
        return fileMapper.getFileByUser(userId) == null;
    }
    public Files getAllFile(){
        return fileMapper.getAllFile();
    }

    public  Files getFile(Integer fileId){
        return  fileMapper.getFile(fileId);
    }

    public List<Files> getFileByUser(Integer userId){
        return fileMapper.getFileByUser(userId);
    }

    public boolean isFileByUserAndFileNameAvailabe(Integer userId,String fileName){
        return fileMapper.getFileByUserAndName(userId,fileName) == null ;
    }

    public  int deleteRecordById(Integer fileId){
        return  fileMapper.deleteFileById(fileId);
    }

}
