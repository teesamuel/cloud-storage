package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

@Controller
//@RequestMapping("/upload")
public class FileController {
    private FileService fileService;
    private  UserService userService;
    private  Files files;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();


    @PostMapping("/upload")
    public String store(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttrs, Principal principal) {
        String error = null;
        User user = this.userService.getUser(principal.getName());
        if (!this.fileService.isFileByUserAndFileNameAvailabe(user.getUserId(),file.getOriginalFilename())) {
            error = "File with name "+file.getOriginalFilename()+" already exists.";
        }
        if (error == null) {

            try {
                files = new Files(null,file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), file.getBytes(), user.getUserId());

            } catch (IOException e) {

                e.printStackTrace();
            }

            int rowsAdded = this.fileService.createFile(files);
            if (rowsAdded < 0) {
                error = "There was an error creating file upload. Please try again.";
            }
        }
        if (error == null) {
            redirectAttrs.addFlashAttribute("success", file.getOriginalFilename() +" was successfully uploaded");
        } else {
            redirectAttrs.addFlashAttribute("error", error);
       }
       return  "redirect:/home";
    }

    @GetMapping("/files/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id) {
        Files files = this.fileService.getFile(Integer.parseInt(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + files.getFileName() + "\"")
                .body(files.getFiledata());
    }

    @GetMapping("/files/delete/{id}")
    public String deleteFile(@PathVariable String id, RedirectAttributes redirectAttrs ) {
        String error = null;
        Files files = this.fileService.getFile(Integer.parseInt(id));
        int rowsAdded  = this.fileService.deleteRecordById(Integer.parseInt(id));
        if (rowsAdded < 0) {
            error = "There was an error in deleting the file at the moment. Please try again.";
        }
        if (error == null) {
            redirectAttrs.addFlashAttribute("success", files.getFileName()+"  was successfully deleted ");
        } else {
            redirectAttrs.addFlashAttribute("error", error);
        }
        return "redirect:/home#nav-files";
    }

}
