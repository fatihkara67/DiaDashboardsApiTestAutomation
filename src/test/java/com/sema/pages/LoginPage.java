package com.sema.pages;


import com.sema.utilities.BrowserUtils;
import com.sema.utilities.ConfigurationReader;
import com.sema.utilities.Constants;
import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class LoginPage extends BasePage {
    @FindBy(xpath = "//iframe[@id='nr-ext-rsicon']")
    private WebElement iframe;
    @FindBy(id = "Username")
    private WebElement usernameField;
    @FindBy(xpath = "//input[@id='password-field']")
    private WebElement passwordField;
    @FindBy(xpath = "//input[@id='submit']")
    private WebElement loginButton;
    @FindBy(xpath = "//img[@id='navLogo']")
    private WebElement successfulLoginConfirmation;
    @FindBy(xpath = "//div[@class='validation-summary-errors text-danger']")
    private WebElement loginErrorText;
    @FindBy(xpath = "//label[@id='warning-text']")
    private WebElement emptyLoginErrorText;
    @FindBy(xpath = "//a[@class='colorWhite']")
    private WebElement forgotYourPasswordText;
    @FindBy(xpath = "//a[contains(text(),'← Giriş sayfasına geri dön')]")
    private WebElement returnToLoginPageText;
    @FindBy(xpath = "//input[@id='Email']")
    private WebElement emailBox;
    @FindBy(xpath = "//input[@id='submit']")
    private WebElement sendButton;
    @FindBy(xpath = "//li[contains(text(),'Hesap bulunamadı, lütfen sistem yöneticinize başvu')]")
    private WebElement forgotPasswordErrorText;
    @FindBy(xpath = "//img[@src='https://docs.microsoft.com/en-us/azure/active-directory/develop/media/howto-add-branding-in-azure-ad-apps/ms-symbollockup_signin_dark.png']")
    private WebElement signInWithMicrosoftButton;

    public LoginPage() {
    }

    public void theUserIsOnTheHomePage() {
        driver.get(ConfigurationReader.getProperty("url"));
    }

    public void setUsernameField(String username) {
        if (username.equalsIgnoreCase("validUsername")) {
            usernameField.sendKeys(ConfigurationReader.getProperty("validUsername"));
        } else if (username.equalsIgnoreCase("invalidUsername")) {
            usernameField.sendKeys(ConfigurationReader.getProperty("invalidUsername"));
        } else {
            usernameField.sendKeys(ConfigurationReader.getProperty(username));
        }
    }

    public void setPasswordField(String password) {
        // driver.switchTo().frame(iframe);
        if (password.equalsIgnoreCase("validPassword")) {
            passwordField.sendKeys(ConfigurationReader.getProperty("validPassword"));
        } else if (password.equalsIgnoreCase("invalidPassword")) {
            passwordField.sendKeys(ConfigurationReader.getProperty("invalidPassword"));
        } else {
            passwordField.sendKeys(ConfigurationReader.getProperty(password));
        }
        //  driver.switchTo().defaultContent();
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void loginVerify(String expectedResult) {
        if (expectedResult.equalsIgnoreCase("successful login")) {
            loginVerification();
        } else if (expectedResult.equalsIgnoreCase("session opening error empty")) {
            BrowserUtils.waitForVisibility(emptyLoginErrorText, 20);
            Assert.assertTrue(emptyLoginErrorText.isDisplayed());
        } else {
            BrowserUtils.waitForVisibility(loginErrorText, 20);
            Assert.assertTrue(loginErrorText.isDisplayed());
        }
    }
    private void loginVerification() {
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("https://diageo.efectura.com/"));
    }

    public void clickForgotYourPassword() {
        BrowserUtils.waitForVisibility(forgotYourPasswordText,20);
        forgotYourPasswordText.click();
    }
    public void clickReturnToLoginPageText() {
        BrowserUtils.waitForVisibility(returnToLoginPageText,20);
        returnToLoginPageText.click();
    }
    public void setEmailBox(String email){
        if (email.equalsIgnoreCase("valid email")) {
            emailBox.sendKeys(Constants.VALID_EMAIL);
        }
        else if (email.equalsIgnoreCase("invalid email")) {
            emailBox.sendKeys(Constants.INVALID_EMAIL);
        }
        else{
            emailBox.sendKeys(Constants.INVALID_FORMAT_EMAIL);
    }
    }
    public void clickSendButton(){
        BrowserUtils.waitForVisibility(sendButton,20);
        sendButton.click();
    }
    public void verifyForgotPasswordErrorText(String text){
        BrowserUtils.waitForVisibility(forgotPasswordErrorText,20);
        Assert.assertTrue(forgotPasswordErrorText.getText().equalsIgnoreCase(text));
    }
    public void clickSignInWithMicrosoftButton(){
        BrowserUtils.waitForVisibility(signInWithMicrosoftButton,20);
        signInWithMicrosoftButton.click();
    }

    public void loginWith(String username) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(ConfigurationReader.getProperty("flowsPassword"));
        loginButton.click();
    }

}