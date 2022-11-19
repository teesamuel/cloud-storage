package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {

    @FindBy(id="credential-url")
    private WebElement credentialUrlField;

    @FindBy(id="credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id="credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id="credentialSubmitBtn")
    private WebElement credentialSubmitButton;

    @FindBy(css="#upload-success-msg")
    private WebElement successMessage;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id ="add-nav-btn")
    private WebElement addNewCredential;


    public CredentialPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void createCredential(String url, String username, String password , WebDriver driver ){
        this.credentialUrlField.sendKeys(url);
        this.credentialUsernameField.sendKeys(username);
        this.credentialPasswordField.sendKeys(password);
   //    this.credentialSubmitButton.click();
        new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(this.credentialSubmitButton)).click();
    }

    public void openCredentialTab(){
        this.credentialTab.click();
    }
    public String getSuccessMessage(){
        return this.successMessage.getText();
    }
    public void displayNewCredentialForm(){
        this.addNewCredential.click();
    }

}
