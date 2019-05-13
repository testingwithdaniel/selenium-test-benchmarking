package boilerplateTests.Tests.browserstack;

import java.io.FileReader;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.browserstack.local.Local;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import utils.mysql.WriteToDB;


public class BrowserStackTestNGTest {
    public WebDriver driver;
    private Local l;
    public Date startTime, endTime;
    public String sessionID,os,os_version,browser,browser_version;
    int Exception=0;

    @BeforeMethod(alwaysRun=true)
    @org.testng.annotations.Parameters(value={"config", "environment","base_url"})
    public void setUp(String config_file, String environment,String base_url) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/main/resources/conf/" + config_file));
        JSONObject envs = (JSONObject) config.get("environments");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            if(pair.getKey().toString().equalsIgnoreCase("os")){
                os=pair.getValue().toString();
            }
            if(pair.getKey().toString().equalsIgnoreCase("os_version")){
                os_version=pair.getValue().toString();
            }
            if(pair.getKey().toString().equalsIgnoreCase("browser")){
                browser=pair.getValue().toString();
            }
            if(pair.getKey().toString().equalsIgnoreCase("browser_version")){
                browser_version=pair.getValue().toString();
            }
        }

        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(capabilities.getCapability(pair.getKey().toString()) == null){
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if(username == null) {
            username = (String) config.get("user");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if(accessKey == null) {
            accessKey = (String) config.get("key");
        }

        if(capabilities.getCapability("browserstack.local") != null && capabilities.getCapability("browserstack.local") == "true"){
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", accessKey);
            l.start(options);
        }

        try {
            startTime=new Date(System.currentTimeMillis());
            driver = new RemoteWebDriver(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
            try{
                driver.manage().window().maximize();
            }catch (Exception e2){
                e2.printStackTrace();
            }
            sessionID=((RemoteWebDriver)driver).getSessionId().toString();
            driver.get(base_url);
        }catch (Exception e){
            //Adding internal retry incase of test failure
            try {
                Exception=1;
                startTime=new Date(System.currentTimeMillis());
                driver = new RemoteWebDriver(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
                try{
                    driver.manage().window().maximize();
                }catch (Exception e2){
                    e2.printStackTrace();
                }
                driver.get(base_url);
            }catch (Exception nestedException){
                nestedException.printStackTrace();
                Exception=2;
            }
        }
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() throws Exception {
        try{
            driver.quit();
            endTime=new Date(System.currentTimeMillis());
            WriteToDB writeDB= new WriteToDB();
            if(Exception==2){
                writeDB.addResultstoDB(sessionID, startTime, endTime, os, os_version, browser, browser_version, String.valueOf(Exception));
            }else {
                writeDB.addResultstoDB(sessionID, startTime, endTime, os, os_version, browser, browser_version, String.valueOf(Exception));
            }
            if(l != null){
                l.stop();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
