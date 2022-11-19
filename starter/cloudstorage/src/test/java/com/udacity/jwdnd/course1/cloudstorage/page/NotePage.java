package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {
    @FindBy(id="note-title")
    private WebElement titleField;
    @FindBy(id="note-description")
    private WebElement descriptionField;
    @FindBy(id="saveNoteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id ="nav-notes-btn")
    private WebElement addNewNote;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(css="#logout-button")
    private WebElement logoutButton;

    @FindBy(css="#upload-success-msg")
    private WebElement successMessage;

    @FindBy(id="upload-failed-msg")
    private WebElement failedMessage;


    public NotePage(WebDriver webDriver) {
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


    public void createNote(String title, String description,WebDriver driver ){
        this.titleField.sendKeys(title);
        this.descriptionField.sendKeys(description);
      new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(this.noteSubmitButton)).click();
    }

    public String getSuccessMessage(){
        return this.successMessage.getText();
    }

}
