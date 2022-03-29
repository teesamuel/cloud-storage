package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotePage {
    @FindBy(css="#note-title")
    private WebElement titleField;

    @FindBy(css="#note-description")
    private WebElement descriptionField;

    @FindBy(css="#noteSubmit")
    private WebElement noteSubmitButton;


    @FindBy(css="#logout-button")
    private WebElement logoutButton;

    public NotePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void createNote(String title, String description ){
        this.titleField.sendKeys(title);
        this.descriptionField.sendKeys(description);
        this.noteSubmitButton.click();
    }

}
