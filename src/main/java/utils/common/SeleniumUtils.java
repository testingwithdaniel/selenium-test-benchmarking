package utils.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SeleniumUtils {
    public WebDriver driver;

    public SeleniumUtils(WebDriver driver) {
        this.driver=driver;
    }

    public void waitForElement(WebpageElements elementDetails){
        try{
            WebDriverWait wait = new WebDriverWait(driver,45);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementDetails.getElementIdentifier())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementDetails.getElementIdentifier())));
            WebElement element=driver.findElement(By.xpath(elementDetails.getElementIdentifier()));
            wait.until(ExpectedConditions.visibilityOf((element)));
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(elementDetails.getElementName()+" not found in the page.");
        }
    }

    public void waitForInvisibleElement(WebpageElements elementDetails){
        try{
            WebDriverWait wait = new WebDriverWait(driver,30);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementDetails.getElementIdentifier())));
        }
        catch(Exception e){
            throw new RuntimeException(elementDetails.getElementName()+" not found in the page.");
        }
    }

    public WebElement findElementToClick(WebpageElements elementDetails){
        WebElement element;
        try{
			/* Time out in seconds */
            WebDriverWait wait = new WebDriverWait(driver,45);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementDetails.getElementIdentifier())));
            element=driver.findElement(By.xpath(elementDetails.getElementIdentifier()));
            wait.until(ExpectedConditions.visibilityOf((element)));
            element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementDetails.getElementIdentifier())));
        }
        catch(Exception e){
            throw new RuntimeException(elementDetails.getElementName()+" not found in the page.");
        }
        return element;
    }

    public void waitForElementToBeClickable(WebpageElements elementDetails){
        WebElement element;
        try{
            WebDriverWait wait = new WebDriverWait(driver,45);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementDetails.getElementIdentifier())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementDetails.getElementIdentifier())));
            element=driver.findElement(By.xpath(elementDetails.getElementIdentifier()));
            wait.until(ExpectedConditions.visibilityOf((element)));
            element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementDetails.getElementIdentifier())));
        }
        catch(Exception e){
            throw new RuntimeException(elementDetails.getElementName()+" not found in the page.");
        }
    }

    public boolean isVisible(WebpageElements elementDetails){
		/*
		 * This function is to check the visibility of the element.
		 */
        boolean flagValue=false;
        try{
			/* Time out in seconds */
            WebDriverWait wait = new WebDriverWait(driver,5);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementDetails.getElementIdentifier())));
            WebElement element=driver.findElement(By.xpath(elementDetails.getElementIdentifier()));
            wait.until(ExpectedConditions.visibilityOf((element)));
            flagValue=true;
        }
        catch(Exception e){
            flagValue=false;
            //throw new RuntimeException(elementDetails.getElementName()+" not visible in the page.");
        }
        return flagValue;
    }

    public WebElement findElement(WebpageElements elementDetails){
		/*
		 * This function is the add on for selenium findElement method.
		 */
        WebElement element=null;
        try{
            element=driver.findElement(By.xpath(elementDetails.getElementIdentifier()));
        }
        catch(Exception e){
            throw new RuntimeException(elementDetails.getElementName()+" not found in the page.");
        }
        return element;
    }

    public void sendKeys(WebpageElements elementDetails,String keysToSend){
		/*
		 * This function is the add on for selenium sendKeys method.
		 */
        WebElement element=null;
        waitForElement(elementDetails);
        try{
            element=driver.findElement(By.xpath(elementDetails.getElementIdentifier()));
            element.clear();
            element.sendKeys(keysToSend);
        }
        catch(Exception e){
            throw new RuntimeException(elementDetails.getElementName()+" not found in the page.");
        }
    }

    public List<WebElement> findMultipleElements(WebpageElements elementDetails){
        /*
		 * This function is the add on for selenium findElements method.
		 */
        List<WebElement> element=null;
        try{
            element=driver.findElements(By.xpath(elementDetails.getElementIdentifier()));
        }
        catch(Exception e){
            throw new RuntimeException(elementDetails.getElementName()+" not found in the page.");
        }
        return element;
    }

    public void clickElement(WebpageElements elementDetails){
        /*
		 * This function is the add on for selenium click method.
		 */
        WebElement element=null;
        waitForElement(elementDetails);
        try{
            driver.findElement(By.xpath(elementDetails.getElementIdentifier())).click();
        }
        catch(Exception e){
            throw new RuntimeException(elementDetails.getElementName()+" not found in the page.");
        }
    }

    public void selectDropDownByValue(WebpageElements elementDetails,String value){
        /*
		 * This function is the add on for selenium click method.
		 */
        WebElement element=null;
        waitForElement(elementDetails);
        try{
            Select dropDown=new Select(driver.findElement(By.xpath(elementDetails.getElementIdentifier())));
            dropDown.selectByValue(value);
        }
        catch(Exception e){
            throw new RuntimeException("Error while working with Dropdown: "+elementDetails.getElementName());
        }
    }
}
