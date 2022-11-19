package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private String baseURL;

	private String username = "testusename";
	private String password = "abadpassword";

	private String url = "http://google.com";

	private String title = "This is a test Title";
	private String description = "This is a test description for test purpose";

	private final int delayPerioiod = 400;
	private final int shortDelayPerioiod = 4;



	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.baseURL  = "http://localhost:";
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get(this.baseURL + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage() {
		driver.get(this.baseURL + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}
	@Test
	public void testNoteDeleteAndVerify() throws InterruptedException {

		signUpUser();
		loginUser();
		noteCreate();
		noteExist(title,description);
		deleteNoteAndVerify();
		logOut();
	}
	@Test
	public void unauthenticatedUserRedirectToLoginPage() {
		driver.get(this.baseURL + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		Assertions.assertNotEquals("Home",  driver.getTitle());
	}

	@Test
	public void unAuthorisedUserCheck()
	{
		//have access to signup page,login page,can't access home
		getSignupPage();
		getLoginPage();
		unauthenticatedUserRedirectToLoginPage();
	}

	@Test
	void signUpUser() {
		driver.get(this.baseURL+ this.port  + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Samuel", "Testing", this.username, this.password);
	}

	void loginUser(){
		driver.get(baseURL+ this.port  + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertNotEquals("Login", driver.getTitle());
	}

	void logOut(){
		HomePage homePage = new HomePage(driver);
		homePage.logout();
		Assertions.assertEquals("Login", driver.getTitle());
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void testUserSignupLoginSubmitAndLogOutMessage() {
		signUpUser();
		loginUser();
		logOut();
		unauthenticatedUserRedirectToLoginPage();
	}

	@Test
	public void testCredentialsCreate() throws InterruptedException {
		signUpUser();
		loginUser();
		credentialCreate();
		deleteCredentialsAndVerify();
		logOut();
	}
	@Test
	public void testCredentialsCreateWithUpdate() throws InterruptedException {
		signUpUser();
		loginUser();
		credentialCreate();
		credentialsExistWithEncryptedPassword( url ,  username,  password);
		updateCredentials( url+"/update" , username+"update", password);
		credentialsExistWithEncryptedPassword( url+"/update" ,  username+"update",  password);
		deleteCredentialsAndVerify();
		logOut();
	}

	@Test
	public void testCredentialsDataExist() throws InterruptedException {
		signUpUser();
		loginUser();
		dataNotExist(url);
		dataNotExist(username);
		credentialCreate();
		dataExist(url);
		dataExist(username);
		credentialsExistWithEncryptedPassword( url ,  username,  password);
		deleteCredentialsAndVerify();
		logOut();
	}

	private  void dataExist(String Data){
		int exist = 0;
		if(driver.getPageSource().contains(url)){
			exist +=1;
		}
		Assertions.assertEquals(1,exist);
	}

	private  void dataNotExist(String Data){
		int exist = 0;
		if(driver.getPageSource().contains(url)){
			exist +=1;
		}
		Assertions.assertEquals(0,exist);
	}


	private void credentialCreate()  throws InterruptedException {
		driver.get(this.baseURL+ this.port  + "/home");
		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.openCredentialTab();
		WebDriverWait wait = new WebDriverWait(driver, 1000);
		delayOperation(wait,delayPerioiod);
		credentialPage.displayNewCredentialForm();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credentialSubmitBtn")));
		delayOperation(wait,delayPerioiod);
		credentialPage.createCredential(url, username, password , driver);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("upload-success-msg")));
		Assertions.assertEquals("Successful! Credential was successfully created", credentialPage.getSuccessMessage());
	}
	@Test
	public void testNoteCreate() throws InterruptedException {
		signUpUser();
		loginUser();
		noteCreate();
		deleteNoteAndVerify();
		logOut();
	}

	@Test
	public void testNotesDataExist() throws InterruptedException {
		signUpUser();
		loginUser();
		noteCreate();
		noteExist(title,description);
		deleteNoteAndVerify();
		logOut();
	}

	@Test
	public void testNoteUpdateAndVerify() throws InterruptedException {
		signUpUser();
		loginUser();
		noteCreate();
		String updatedDescription= description+"Update";
		noteExist(title,description);
		updateNote(title,updatedDescription);
		noteExist(title,updatedDescription);
		logOut();
	}

	private void deleteCredentialsAndVerify(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, shortDelayPerioiod);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-credentials-tab")));
		WebElement notesTab = driver.findElement(By.cssSelector("#nav-notes-tab"));
		driver.get(this.baseURL + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-credentials-tab")));
		notesTab = driver.findElement(By.cssSelector("#nav-credentials-tab"));
		notesTab.click();
		//delete first row
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#credentialTable > tbody > tr > td:nth-child(1) > a")));
		WebElement deleteButton = driver.findElement(By.cssSelector("#credentialTable > tbody > tr > td:nth-child(1) > a.btn.btn-danger"));
		deleteButton.click();
		//go back to page
		driver.get(this.baseURL+ this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-credentials-tab")));
		notesTab = driver.findElement(By.cssSelector("#nav-credentials-tab"));
		notesTab.click();
		//confirm delete
		assertThrows(TimeoutException.class, () -> {
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#credentialTable > tbody > tr")));
		});

	}

	private void deleteNoteAndVerify(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, shortDelayPerioiod);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-notes-tab")));
		WebElement notesTab = driver.findElement(By.cssSelector("#nav-notes-tab"));
		driver.get(this.baseURL + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-notes-tab")));
		notesTab = driver.findElement(By.cssSelector("#nav-notes-tab"));
		notesTab.click();
		//delete first row
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#userTable > tbody > tr > td:nth-child(1) > a")));
		WebElement deleteButton = driver.findElement(By.cssSelector("#userTable > tbody > tr > td:nth-child(1) > a"));
		deleteButton.click();

		//go back to page
		driver.get(this.baseURL+ this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-notes-tab")));
//		WebElement notesTab = driver.findElement(By.cssSelector("#nav-notes-tab"));
		notesTab = driver.findElement(By.cssSelector("#nav-notes-tab"));
		notesTab.click();
		//confirm delete
		assertThrows(TimeoutException.class, () -> {
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#userTable > tbody > tr")));
		});

	}
	private void updateNote(String newTitle, String newDescription){
		WebDriverWait webDriverWait = new WebDriverWait(driver, shortDelayPerioiod);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#userTable > tbody > tr > td:nth-child(1) > button > a" )));
		WebElement editButton = driver.findElement(By.cssSelector("#userTable > tbody > tr > td:nth-child(1) > button > a"));
		editButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitleInput = driver.findElement(By.cssSelector("#note-title"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescInput = driver.findElement(By.id("note-description"));

		noteDescInput = driver.findElement(By.cssSelector("#note-description"));
		noteDescInput.clear();
		noteDescInput.sendKeys(newDescription);

		noteTitleInput = driver.findElement(By.cssSelector("#note-title"));
		noteTitleInput.clear();
		noteTitleInput.sendKeys(newTitle);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#noteEdit > div > div > div.modal-footer > button")));
		WebElement saveButton = driver.findElement(By.cssSelector("#noteEdit > div > div > div.modal-footer > button"));

		saveButton.click();
	}

	private void updateCredentials(String url , String username, String password){

		WebDriverWait webDriverWait = new WebDriverWait(driver, shortDelayPerioiod);
//		#credentialTable > tbody > tr > td:nth-child(1) > a.btn.btn-success
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#credentialTable > tbody > tr > td:nth-child(1) >  a.btn.btn-success" )));
		WebElement editButton = driver.findElement(By.cssSelector("#credentialTable > tbody > tr > td:nth-child(1) > a.btn.btn-success"));
		editButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement credentialUrlInput = driver.findElement(By.cssSelector("#credential-url"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement credUsernameInput = driver.findElement(By.id("credential-username"));

		credUsernameInput = driver.findElement(By.cssSelector("#credential-username"));
		credUsernameInput.clear();
		credUsernameInput.sendKeys(username);

		credentialUrlInput = driver.findElement(By.cssSelector("#credential-url"));
		credentialUrlInput.clear();
		credentialUrlInput.sendKeys(url);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#credentialEdit > div > div > div.modal-footer > button")));
		WebElement saveButton = driver.findElement(By.cssSelector("#credentialEdit > div > div > div.modal-footer > button"));

		saveButton.click();
	}

	private void credentialsExistWithEncryptedPassword(String url , String username, String password){

		WebDriverWait webDriverWait = new WebDriverWait(driver, delayPerioiod);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-credentials-tab")));
		WebElement cresTab = driver.findElement(By.cssSelector("#nav-credentials-tab"));
		driver.get(this.baseURL + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-credentials-tab")));
		cresTab = driver.findElement(By.cssSelector("#nav-credentials-tab"));
		cresTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#credentialTable > tbody > tr > th")));
		WebElement address = driver.findElement(By.cssSelector("#credentialTable > tbody > tr > th"));
		Assertions.assertEquals(url, address.getText());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#credentialTable > tbody > tr:nth-child(1) > td:nth-child(3)")));
		WebElement userName = driver.findElement(By.cssSelector("#credentialTable > tbody > tr:nth-child(1) > td:nth-child(3)"));
		Assertions.assertEquals(username, userName.getText());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#credentialTable > tbody > tr:nth-child(1) > td:nth-child(4)")));
		WebElement passWord = driver.findElement(By.cssSelector("#credentialTable > tbody > tr:nth-child(1) > td:nth-child(4)"));
		Assertions.assertNotEquals(username, passWord.getText());

//		#credentialTable > tbody > tr > td:nth-child(4)

	}

	private void noteExist(String title , String description){

		WebDriverWait webDriverWait = new WebDriverWait(driver, delayPerioiod);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-notes-tab")));
		WebElement notesTab = driver.findElement(By.cssSelector("#nav-notes-tab"));
		driver.get(this.baseURL + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-notes-tab")));
		notesTab = driver.findElement(By.cssSelector("#nav-notes-tab"));
		notesTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#userTable > tbody > tr:nth-child(1) > th")));
		WebElement noteTitle = driver.findElement(By.cssSelector("#userTable > tbody > tr:nth-child(1) > th"));
		Assertions.assertEquals(title, noteTitle.getText());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#userTable > tbody > tr:nth-child(1) > td:nth-child(3)")));
		WebElement noteDesc = driver.findElement(By.cssSelector("#userTable > tbody > tr:nth-child(1) > td:nth-child(3)"));
		Assertions.assertEquals(description, noteDesc.getText());

	}
	private void noteCreate()  throws InterruptedException {
		driver.get(this.baseURL+ this.port  + "/home");
		NotePage notePage = new NotePage(driver);
		notePage.openNoteTab();
		WebDriverWait wait = new WebDriverWait(driver, 1000);
		delayOperation(wait,delayPerioiod);
		notePage.displayNewNoteForm();
		delayOperation(wait,delayPerioiod);
		notePage.createNote(title, description ,driver);
		delayOperation(wait,delayPerioiod);
		Assertions.assertEquals("Successful! Note was successfully created", notePage.getSuccessMessage());
	}

	private void delayOperation(WebDriverWait wait, int milliseconds){
		synchronized (wait) {
			try {
				wait.wait(delayPerioiod);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-button")));
		WebElement buttonSignUp = driver.findElement(By.id("submit-button"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-button")));
		WebElement loginButton = driver.findElement(By.id("submit-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}


}
