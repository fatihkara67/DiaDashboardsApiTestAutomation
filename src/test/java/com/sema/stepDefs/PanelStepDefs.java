package com.sema.stepDefs;

import com.sema.utilities.BrowserUtils;
import com.sema.utilities.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PanelStepDefs extends BaseStep {

    @When("The user go in {string} flow history")
    public void theUserGoInFlowHistory(String flowName) {
        WebElement flowRow = Driver.getDriver().findElement(By.xpath("//td[contains(text(),'" + flowName + "')]"));
        flowRow.click();
    }

    @Then("The  user verify history page is open")
    public void theUserVerifyHistoryPageIsOpen() {
//        Assert.assertEquals(
//                "https://dia-preprod-ui.efectura.com/Process/ProcessDefinitonDetail?id=SalesFlow_V3.1:1:7a0ec3d6-3321-11ef-8958-0242ac130003",
//                Driver.getDriver().getCurrentUrl());

        Assert.assertTrue("İşlem Listesi Tablosu bulunamadı",
                BrowserUtils.isElementDisplayed(pages.panel().getProcessDetailTable()));

    }

}
