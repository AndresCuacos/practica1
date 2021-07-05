package es.urjc.code.daw.library;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.concurrent.TimeUnit;

import org.codehaus.groovy.transform.ThreadInterruptibleASTTransformation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestSelenium {

    @LocalServerPort
    int port;

    private WebDriver driver;

	@BeforeAll
	public static void setupClass() {
		WebDriverManager.chromedriver().version("91").setup();
	}

	@BeforeEach
	public void setupTest() {
        this.driver = new ChromeDriver();
	}

	@AfterEach
	public void teardown() {
		if (this.driver != null) {
            this.driver.quit();
		}
    }
    
	 @Test
		void bookAddedAndCanBeProved() throws Exception {

	        //given
	        this.driver.get("http://localhost:"+this.port+"/");

	        //when
	        String title = "Libro1";
	        String description = "Primer libro";
	        String title2 = "v2";
	        driver.findElement(By.xpath("//*[text()='New book']")).click();
	        driver.findElement(By.name("title")).sendKeys(title);
	        driver.findElement(By.name("description")).sendKeys(description );
	        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	        driver.findElement(By.id("Save")).click();
	        driver.findElement(By.xpath("//*[text()='Edit']")).click();
	        driver.findElement(By.name("title")).sendKeys(title2);
	        driver.findElement(By.id("Save")).click();
	        driver.findElement(By.xpath("//*[text()='All books']")).click();
	        //driver.findElement(By.xpath("/*[text()='All books']")).click();
	        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	        
	        //then 
	        assertNotNull(driver.findElement(By.linkText(title+title2)));
	    }
}