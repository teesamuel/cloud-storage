package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(id="note-title")
    private WebElement titleField;

    @FindBy(id="note-description")
    private WebElement descriptionField;

    @FindBy(id="noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id ="nav-notes-btn")
    private WebElement addNewNote;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(css="#credential-url")
    private WebElement credentialUrlField;

    @FindBy(css="#credential-username")
    private WebElement credentialUsernameField;

    @FindBy(css="#credential-password")
    private WebElement credentialPasswordField;

    @FindBy(css="#credentialSubmit")
    private WebElement credentialSubmitButton;

    @FindBy(css="#logout-button")
    private WebElement logoutButton;

    @FindBy(id="upload-success-msg")
    private WebElement successMessage;

    @FindBy(id="upload-failed-msg")
    private WebElement failedMessage;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void displayNewNoteForm(){
        this.addNewNote.click();
    }
    public void openNoteTab(){
        this.noteTab.click();
    }
    public void logout() {
        this.logoutButton.click();
    }

    public void submitNote() { this.noteSubmitButton.click();}

    public void createNote(String title, String description ){
        this.titleField.sendKeys(title);
        this.descriptionField.sendKeys(description);
        this.noteSubmitButton.click();
    }

    public String getSuccessMessage(){
        return this.successMessage.getText();
    }


}
