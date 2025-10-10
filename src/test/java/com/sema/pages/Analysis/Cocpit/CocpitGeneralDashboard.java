package com.sema.pages.Analysis.Cocpit;

import com.sema.pages.BasePage;
import com.sema.utilities.BrowserUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class CocpitGeneralDashboard extends BasePage {
    @FindBy(xpath = "//a[@id='analysisTab']")
    private WebElement analysis;
    @FindBy(xpath = "//a[normalize-space()='Cockpit']")
    private WebElement cocpit;
    @FindBy(xpath = "//*[@id=\"TABS-e5Ruro0cjP-tab-TAB-d-E0Zc1cTH\"]")
    private WebElement overview;
    @FindBy(xpath = "//div[@class='dragdroppable-tab css-jik4gi']//span[contains(text(),'Network')]")
    private WebElement network;
    @FindBy(xpath = "//span[@class='editable-title']/a[contains(@class, 'css-1r97axf') and text()='Average Downlink Speed']\n")
    private WebElement networkIsDisplayed;
    @FindBy(xpath = "//div[@class='dragdroppable-tab css-jik4gi']//span[contains(text(),'Call Center')]")
    private WebElement callCenter;
    @FindBy(xpath = "//span[@class='editable-title']/a[text()='Hold Time']\n")
    private WebElement callCenterIsDisplayed;
    @FindBy(xpath = "//div[@class='dragdroppable-tab css-jik4gi']//span[contains(text(),'Marketing')]")
    private WebElement marketing;
    @FindBy(xpath = "//a[text()='Conversition Ratio, %']")
    private WebElement marketingIsDisplayed;
    @FindBy(xpath = "//div[@class='dragdroppable-tab css-jik4gi']//span[contains(text(),'Details')]")
    private WebElement details;
    @FindBy(xpath = "//a[text()='Conversition Ratio, %']")
    private WebElement detailsDisplayed;

    public void verifyAnalysisElement(int timeout) {
        BrowserUtils.waitForVisibility(analysis, timeout);
        Assert.assertTrue(analysis.isDisplayed());
        analysis.click();
    }

    public void onTheCocpitPage() {
        BrowserUtils.wait(2);
        cocpit.click();
    }
    public void overviewVerifies(){
        driver.switchTo().frame(0);
        BrowserUtils.waitForVisibility(overview,25);
        Assert.assertTrue(overview.isDisplayed());
        driver.switchTo().defaultContent();

    }
    public void clicksAndVerifiesNetwork(){
        driver.switchTo().frame(0);
        BrowserUtils.waitForVisibility(network,25);
        Assert.assertTrue(network.isDisplayed());
        network.click();
        networkIsDisplayed.isDisplayed();
        driver.switchTo().defaultContent();

    }
    public void clicksAndVerifiesCallCenter(){
        driver.switchTo().frame(0);
        BrowserUtils.waitForVisibility(callCenter,25);
        Assert.assertTrue(callCenter.isDisplayed());
        callCenter.click();
        callCenterIsDisplayed.isDisplayed();
        driver.switchTo().defaultContent();

    }
    public void clicksAndVerifiesMarketing(){
        driver.switchTo().frame(0);
        BrowserUtils.waitForVisibility(marketing,25);
        Assert.assertTrue(marketing.isDisplayed());
        marketing.click();

    }
    public void clicksAndVerifiesDetails(){
        driver.switchTo().frame(0);
        BrowserUtils.waitForVisibility(details,25);
        Assert.assertTrue(details.isDisplayed());
        details.click();

    }

}