package com.sema.pages.Analysis.RFM;

import com.sema.pages.BasePage;
import com.sema.utilities.BrowserUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RFM extends BasePage {
    @FindBy(xpath = "//a[normalize-space()='RFM']")
    private WebElement rfm;
    @FindBy(xpath = "//a[@class='css-1r97axf'][contains(text(),'RFM Partition')]")
    private WebElement rfmVerifies;
    @FindBy(xpath = "//a[@id='_posm-tab']")
    private WebElement POSM;
    @FindBy(xpath = "//div[@class=\"block-items-info\"]")
    private WebElement verifiesPOSM;

    public void onTheRFMPage() {
        BrowserUtils.wait(2);
        rfm.click();
    }

    public void rfmVerifies() {
        driver.switchTo().frame(0);
        BrowserUtils.waitForVisibility(rfmVerifies, 50);
        Assert.assertTrue(rfmVerifies.isDisplayed());
        driver.switchTo().defaultContent();

    }

    public void onThePOSMPage() {
        BrowserUtils.wait(5);
        POSM.click();
    }

    public void posmVerifies() {
        driver.switchTo().frame(0);
        BrowserUtils.waitForVisibility(verifiesPOSM, 50);
        Assert.assertTrue(verifiesPOSM.isDisplayed());
        driver.switchTo().defaultContent();

    }
}
