package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private String baseURL;

	private String username = "testusename";
	private String password = "abadpassword";



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

}
