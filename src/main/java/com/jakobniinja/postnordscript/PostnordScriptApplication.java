package com.jakobniinja.postnordscript;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@SpringBootApplication
public class PostnordScriptApplication {
    public static void main(String[] args) throws InterruptedException, IOException {

//        <------- Static values --------->

        CharSequence postkod = new StringBuffer("12245");
        final String seleniumUrl = "https://www.postnord.se/vara-verktyg/sok-utdelningsdag";

//        <------- Config --------->

        System.setProperty("webdriver.chrome.driver", "/home/niinja/selenium-drivers/chromedriver");

        ChromeOptions handlingSSL = new ChromeOptions();
        handlingSSL.setAcceptInsecureCerts(true);
        WebDriver driver = new ChromeDriver(handlingSSL);



//        System.setProperty("webdriver.chrome.silentOutput", "true");
//        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
//        java.util.logging.Logger.getLogger("org.apache.catalina.core").setLevel(Level.OFF);

//        <------- Config --------->

//        < -------     let the browser load the webelements  ------->

        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(seleniumUrl);
        System.out.println("title is :" + driver.getTitle());


        driver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler']")).click();
        driver.findElement(By.xpath("//*[@id=\"block-DeliveryDaySearchBlockProxy-41857\"]/div/pn-zipcode-search/pn-search-field/div/input")).sendKeys(postkod);
        driver.findElement(By.xpath("//*[@id=\"block-DeliveryDaySearchBlockProxy-41857\"]/div/pn-zipcode-search/pn-search-field/div/input")).sendKeys(Keys.ENTER);

        System.out.println("postkod is: " + postkod);


//        <------- Snapshot --------->

        Date currentDate = new Date();
        String newDate = currentDate.toString().replace(" ", "-").replace(":", "-");

        System.out.println("date is: " + newDate);


        Thread.sleep(600);
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//snapshot/" + newDate + ".png"));


//        <------- Snapshot --------->

        Thread.sleep(600);
        driver.quit();
    }
}


