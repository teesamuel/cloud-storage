package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialPage {

    @FindBy(css="#credential-url")
    private WebElement credentialUrlField;

    @FindBy(css="#credential-username")
    private WebElement credentialUsernameField;

    @FindBy(css="#credential-password")
    private WebElement credentialPasswordField;

    @FindBy(css="#credentialSubmit")
    private WebElement credentialSubmitButton;

    public CredentialPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void createCredential(String url, String username, String password ){
        this.credentialUrlField.sendKeys(url);
        this.credentialUsernameField.sendKeys(username);
        this.credentialPasswordField.sendKeys(password);
        this.credentialSubmitButton.click();
    }

}
