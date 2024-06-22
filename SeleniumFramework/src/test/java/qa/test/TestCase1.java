package qa.test;

import org.testng.annotations.Test;

import base.BaseTest;
import pageEvents.HomePageEvents;
import pageEvents.LoginPageEvents;
import utils.ElementFetch;

public class TestCase1 extends BaseTest{

	ElementFetch ele=new ElementFetch();
	HomePageEvents homepage=new HomePageEvents();
	LoginPageEvents loginPage=new LoginPageEvents();
	@Test
	public void sampleMethodForEnterCreditials()
	{
		homepage.signInButton();
		loginPage.verifyIfLoginPageIsLoaded();
		loginPage.enterCreditials();
	}
}
