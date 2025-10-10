package com.sema.stepDefs;

import com.sema.utilities.BrowserUtils;
import com.sema.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ItemOverviewStepDefs extends BaseStep {

    @And("The user verify Reset button func for {string} text filter")
    public void theUserVerifyResetButtonFuncForTextFilter(String columnName) {
        String locate = "//thead/tr[1]/th[normalize-space()='" + columnName +
                "']/following::tr[1]/th[position()=count(//thead/tr[1]/th[normalize-space()='" + columnName +
                "']/preceding-sibling::th)+1]//input";
        WebElement textFilterInputBox = Driver.getDriver().findElement(By.xpath(locate));
        String actualValue = BrowserUtils.getValueInInputBox(textFilterInputBox);
        Assert.assertEquals("", actualValue);
    }

    @When("The user clicks {string} pagination button")
    public void theUserClicksLastPageTablePaginationButton(String btnName) {
        pages.generalPage().clickPaginationButton(btnName);
    }

    @And("The user verifies {string} button is {string}")
    public void theUserVerifiesButtonIs(String btnName, String btnStatus) {
        pages.generalPage().verifyButtonStatus(btnName,btnStatus);
    }

}
