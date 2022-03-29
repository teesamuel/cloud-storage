package com.udacity.jwdnd.course1.cloudstorage.controller;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;


@Controller
public class NoteController {
    private NoteService noteService;
    private UserService userService;
    private Note note;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note")
    public String store(Model model, @ModelAttribute Note formNote, Principal principal, RedirectAttributes redirectAttrs) {
        String error = null;
        User user = this.userService.getUser(principal.getName());
        if (error == null) {
            note = new Note(null,formNote.getNoteTitle(),formNote.getNoteDescription(), user.getUserId());
            int rowsAdded = this.noteService.createNote(note);
            if (rowsAdded < 0) {
                error = "There was an error creating your note. Please try again.";
            }
        }
        if (error == null) {
            redirectAttrs.addFlashAttribute("success", "Note was successfully created");
        } else {
            redirectAttrs.addFlashAttribute("error", error);
        }

        return "redirect:/home#nav-notes";
    }

    @GetMapping("/note/delete/{id}")
    public String deleteFile(@PathVariable String id, RedirectAttributes redirectAttrs ) {

        String error = null;
        int rowsAdded  = this.noteService.deleteRecordById(Integer.parseInt(id));
        if (rowsAdded < 0) {
            error = "There was an error in deleting your note at the moment. Please try again.";
        }
        if (error == null) {
            redirectAttrs.addFlashAttribute("success", "Note  was successfully deleted ");
        } else {
            redirectAttrs.addFlashAttribute("error", error);
        }

        return "redirect:/home";
    }
    @GetMapping("/note/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        model.addAttribute("noteEdit",  this.noteService.getNote(Integer.valueOf(id)));
        return "update/update-note";
    }

    @PostMapping("/note/update/{id}")
    public String update(@PathVariable("id") String id, @Valid Note note,
                             BindingResult result, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            note.setNoteId(Integer.valueOf(id));
            return "update/update-note";
        }
        System.out.println("Hererere");
        System.out.println(note.getNoteTitle());
        System.out.println(note.getNoteDescription());
        System.out.println(Integer.valueOf(id));
        System.out.println("Hererere HJJ");
        int updateRow = this.noteService.updateNote(note, Integer.valueOf(id));
        if (updateRow > 0 ) {
            redirectAttrs.addFlashAttribute("success", "Note was successfully updated");
        } else {
            redirectAttrs.addFlashAttribute("error", "We could not update your note at the moment please try again later");
        }
        return "redirect:/home";
    }


}
