package test;

import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.time.Duration;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencsv.CSVReader;

public class SeleniumTest {

    private WebDriver driver;
    private String mainWindow;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Antonio\\Desktop\\JDK JAVA\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testDropdown() {
        driver.get("https://the-internet.herokuapp.com/dropdown");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdownElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("dropdown"))
        );

        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText("Option 2");

        List<WebElement> options = dropdown.getOptions();
        for (WebElement option : options) {
            System.out.println(option.getText());
        }

        assertTrue(options.size() > 1); // Simple verificación de que hay varias opciones
    }

    @Test
    public void testLoginDesdeCSV() throws Exception {
        mainWindow = driver.getWindowHandle();

        // Leer CSV
        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\Antonio\\eclipse-workspace\\HERMI\\src\\main\\java\\test\\data.csv"))) {
            List<String[]> allData = reader.readAll();

            for (String[] row : allData) {
                String user = row[0];
                String pass = row[1];

                // Nueva pestaña para login
                driver.switchTo().newWindow(WindowType.TAB);
                driver.get("https://practicetestautomation.com/practice-test-login/");
                Thread.sleep(2000);

                // Login con datos del CSV
                LoginPage login = new LoginPage(driver);
                login.enterUsername(user);
                login.enterPassword(pass);
                login.clickLogin();

                Thread.sleep(2000);

                try {
                    WebElement h1 = driver.findElement(By.tagName("h1"));
                    String mensaje = h1.getText();

                    boolean loginOk = mensaje.contains("Logged In Successfully");
                    System.out.println(loginOk ? "✅ Login exitoso con: " + user : "❌ Login falló con: " + user);
               
                } catch (Exception e) {
                    System.out.println("❌ No se encontró el mensaje. Login falló con: " + user);
                  
                }

                driver.close(); // Cerrar pestaña
                driver.switchTo().window(mainWindow); // Volver a principal
            }
        }
    }
}
      
   
    
    
