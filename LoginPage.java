package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

class LoginPage {
    WebDriver driver;

    By username = By.id("username"); // ✔️ Correcto
    By password = By.id("password"); // ✔️ Correcto
    By loginButton = By.id("submit"); // ✔️ Correcto

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String user) {
        driver.findElement(username).sendKeys(user);
    }

    public void enterPassword(String pass) {
        driver.findElement(password).sendKeys(pass);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
        
        
    }
    
    
}

