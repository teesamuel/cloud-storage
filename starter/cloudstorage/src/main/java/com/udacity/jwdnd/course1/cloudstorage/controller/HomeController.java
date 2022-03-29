package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private FileService fileService;
    private CredentialService credentialService;
    private NoteService noteService;
    private UserService userService;
    private EncryptionService encryptionService;




    public HomeController( CredentialService credentialService, NoteService noteService,UserService userService,FileService  fileService,EncryptionService encryptionService) {
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.userService = userService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String Index(Model model,Principal principal){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!userService.isUsernameAvailable(auth.getPrincipal().toString())) {
            User user = userService.getUser(principal.getName());
            if(!fileService.isUserFileAvailable(user.getUserId())){
                List<Files> filesList = fileService.getFileByUser(user.getUserId());
                if (filesList != null) {
                    model.addAttribute("userFiles", filesList);
                }
            }

            if(!noteService.isUserNoteAvailable(user.getUserId())){
                List<Note> noteList = noteService.getNoteByUser(user.getUserId());
                if (noteList != null) {
                    model.addAttribute("userNotes", noteList);
                }
            }

            if(!credentialService.isUserCredentialsAvailable(user.getUserId())){
                List<Credential> credentialList = credentialService.getCredentialByUser(user.getUserId());
//                for (Credential credential : credentialList) {
//                    credential.setPassword(encryptionService.decryptValue(credential.getPassword(),credential.getKey()));
//                }
                if (credentialList != null) {
                    model.addAttribute("userCredentials", credentialList);
                }
            }
        }

        return "home";
    }
}
