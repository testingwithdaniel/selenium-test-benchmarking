package boilerplateTests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.common.SeleniumUtils;
import utils.common.WebpageElements;

import java.util.List;

public class stormy_herokuApp {
    public WebDriver driver ;

    public stormy_herokuApp(WebDriver driver) {
        this.driver=driver;
    }

    public static WebpageElements sendKeysBox= new WebpageElements("SendKeys-Test-Element","//*[@id='q']");
    public static WebpageElements submitButton=new WebpageElements("Submit-Button","//input[@type='submit']");
    public static WebpageElements getElements= new WebpageElements("FindElements-Test-Element","//div[@id='navcontainer']/ul/li");
    public static WebpageElements FormName= new WebpageElements("Form-textBox-Name","//input[@id='testname']");
    public static WebpageElements FormEmail = new WebpageElements("Form-Email-Name","//input[@id='testemail']");
    public static WebpageElements FormCheckBox = new WebpageElements("Form-CheckBox","//input[@id='testcheckbox']");
    public static WebpageElements FormDropDown = new WebpageElements("Form-DropDown","//select[@id='testcolor']");

    public void SendKeysToTextBox(String textForSendKeys){
        SeleniumUtils seleniumUtils = new SeleniumUtils(driver);
        seleniumUtils.waitForElement(sendKeysBox);
        seleniumUtils.sendKeys(sendKeysBox,textForSendKeys);
    }

    public void submitButtonClick(){
        SeleniumUtils seleniumUtils = new SeleniumUtils(driver);
        seleniumUtils.waitForElement(submitButton);
        seleniumUtils.clickElement(submitButton);
    }

    public List<WebElement> getMultipleElements(){
        SeleniumUtils seleniumUtils = new SeleniumUtils(driver);
        seleniumUtils.waitForElement(getElements);
        List<WebElement> elements=seleniumUtils.findMultipleElements(getElements);
        return elements;
    }

    public void sendKeystoFormName(String textForSendKeys){
        SeleniumUtils seleniumUtils = new SeleniumUtils(driver);
        seleniumUtils.waitForElement(FormName);
        seleniumUtils.sendKeys(FormName,textForSendKeys);
    }

    public void sendKeystoFormEmail(String textForSendKeys){
        SeleniumUtils seleniumUtils = new SeleniumUtils(driver);
        seleniumUtils.waitForElement(FormEmail);
        seleniumUtils.sendKeys(FormEmail,textForSendKeys);
    }

    public void clickFormCheckBox(){
        SeleniumUtils seleniumUtils = new SeleniumUtils(driver);
        seleniumUtils.waitForElement(FormCheckBox);
        seleniumUtils.clickElement(FormCheckBox);
    }

    public void selectDropDown(String value){
        SeleniumUtils seleniumUtils = new SeleniumUtils(driver);
        seleniumUtils.waitForElement(FormDropDown);
        seleniumUtils.selectDropDownByValue(FormDropDown,value);
    }

}
