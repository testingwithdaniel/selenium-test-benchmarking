package boilerplateTests.Tests;

import boilerplateTests.Tests.browserstack.BrowserStackTestNGTest;
import boilerplateTests.pages.stormy_herokuApp;
import org.testng.annotations.Test;

public class testCase extends BrowserStackTestNGTest {

    @Test
    public void test() throws Exception{
        stormy_herokuApp samplePage = new stormy_herokuApp(driver);
        samplePage.SendKeysToTextBox("testing");
        samplePage.submitButtonClick();
        System.out.println(driver.getTitle());
        samplePage.getMultipleElements();
        samplePage.sendKeystoFormName("Sample Text");
        samplePage.sendKeystoFormEmail("abc@test.com");
        samplePage.clickFormCheckBox();
        samplePage.selectDropDown("yellow");
    }
}
