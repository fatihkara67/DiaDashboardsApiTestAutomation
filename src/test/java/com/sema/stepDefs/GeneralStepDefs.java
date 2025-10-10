package com.sema.stepDefs;

import com.sema.pages.BasePage;
import com.sema.utilities.BrowserUtils;
import com.sema.utilities.ConfigurationReader;
import com.sema.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.sql.SQLException;
import java.util.List;

public class GeneralStepDefs extends BaseStep {

    @Then("The user verify {string} text filter with value {string} in {string}")
    public void theUserVerifyTextFilterWithValueIn(String columnName, String expectedValue, String table) {
        BrowserUtils.wait(2);
        WebElement tableElement = Driver.getDriver().findElement(By.id(ConfigurationReader.getProperty(table)));
        List<String> values =  BasePage.getColumnData(tableElement,columnName);

        System.out.println(values);
        BrowserUtils.wait(2);
        for (String actualValue : values) {
            Assert.assertTrue(actualValue.toLowerCase().contains(expectedValue.toLowerCase()));
//            Assert.assertEquals(expectedValue,actualValue);
        }
    }

    @And("The user enters {string} into {string} filter text input box")
    public void theUserEntersIntoFilterTextInputBox(String value, String columnName) {
        pages.generalPage().useTextFilter(value,columnName);
    }

    @And("The user reset the basic filters")
    public void theUserResetTheBasicFilters() {
        BrowserUtils.wait(10);
        BrowserUtils.waitForClickability(pages.generalPage().getBasicResetButton(),20);
        pages.generalPage().getBasicResetButton().click();
        BrowserUtils.wait(10);
    }

    @When("The user go to {string} overview page")
    public void theUserGoToAccountOverviewPage(String item) {
        pages.itemOverviewPage().goToItemOverviewPage(item);
    }

    @Then("The user verify {string} table is visible")
    public void theUserVerifyTableTableIsVisible(String tableName) {
        System.out.println("tableName " + tableName);
        Assert.assertTrue(tableName + "tablosu Bulunamadı",
                BrowserUtils.isElementDisplayed
                        (Driver.getDriver().findElement(By.id(ConfigurationReader.getProperty(tableName)))));
    }

    @And("The user clicks on edit button in table")
    public void theUserClicksOnEditButtonInTable() {

        pages.itemOverviewPage().closeSideAccordionInOverview();
        BrowserUtils.wait(3);
        BrowserUtils.adjustScreenSize(60,Driver.getDriver());
        BrowserUtils.hoverOver(pages.generalPage().getEditBtnInTable());
        BrowserUtils.wait(2);
        pages.generalPage().getEditBtnInTable().click();
    }

    @And("The user select {string} in {string} select filter")
    public void theUserSelectInSelectFilter(String selectOption, String selectFilter) {
        BrowserUtils.adjustScreenSize(70,Driver.getDriver());
        pages.generalPage().selectOptionInSelectFilter(selectOption,selectFilter);
        BrowserUtils.wait(5);
    }

    @Then("The user verify that first item with code {string} has association on {string}")
    public void theUserVerifyThatFirstItemWithCodeHasAssociationOn(String itemCode, String associationTypeCode) throws SQLException {
        BrowserUtils.wait(2);
        Assert.assertTrue(pages.editItemPage().getAssociations(itemCode,associationTypeCode));
    }

    @Given("The user go to Reports page")
    public void theUserGoToReportsPage() {
        Driver.getDriver().get("https://diageo.efectura.com/Reports");
    }

    @Given("The user go to Imports page")
    public void theUserGoToImportsPage() {
        Driver.getDriver().get("https://diageo.efectura.com/Import");
    }

    @Given("The user go to DB module page")
    public void theUserGoToDBModulePage() {
        Driver.getDriver().get("https://diageo.efectura.com/Settings/DBModuleMonitor");
    }

    @Given("The user go to Jobs page")
    public void theUserGoToJobsPage() {
        Driver.getDriver().get("https://diageo.efectura.com/Job/Jobs");
    }

    @Then("The user verify job page is open")
    public void theUserVerifyJobPageIsOpen() {
        Driver.getDriver().switchTo().frame(pages.generalPage().getJobsFrame());

        Assert.assertTrue("Jobs sayfası yüklenmedi",
                BrowserUtils.isElementDisplayed(pages.generalPage().getJobsHeader()));
    }

    @Given("the user go in the item {string}")
    public void theUserGoInTheItem(String itemId) {
        Driver.getDriver().get(ConfigurationReader.getProperty("editItemLinkWithoutId") + itemId);
    }

    @When("The user go to ReportCardAnalysis page")
    public void theUserGoToReportCardAnalysisPage() {
        Driver.getDriver().get("https://diageo.efectura.com/Enrich/ReportCardAnalysis");
    }

    @Then("The user verify table is displayed")
    public void theUserVerifyTableIsDisplayed() {
        Driver.getDriver().switchTo().frame(pages.generalPage().getReportCardAnalysisMainFrame());
        Driver.getDriver().switchTo().frame(pages.generalPage().getReportCardAnalysisSecondFrame());
        BrowserUtils.wait(2);
        Assert.assertTrue("ReportCardAnalysis table is not displayed",
                BrowserUtils.isElementDisplayed(pages.generalPage().getCardAnalysisTable()));
    }

    @When("The user go to reserve karne")
    public void theUserGoToReserveKarne() {
        Driver.getDriver().get("https://diageo.efectura.com:8112/KarnePilotReserve.html?token=pAGPE6qyyq10hb0XLUdPNJ70WSZjDRtOYnH42kH5z9q0nhSNA5xpU76AqyDk5lZIE%2fBDXVSMg50tLkJbxkqzCfB%2bE34nZM6lipTO%2bzxThXXh6VxUocLQgp5TDFQcuDHq3eBs4%2f6sxSrBHIrGoRn%2blm7eFYJSsR%2biXPY4fZr4UI8%3d&bolge=Reserve%20Ege");

    }

    @Then("The user verify reserve karne")
    public void theUserVerifyReserveKarne() {
        Assert.assertTrue("Reserve Karne İçeriği Bulunamadı",
                BrowserUtils.isElementDisplayed(Driver.getDriver().findElement(By.xpath("//div[@id='myTabContent']"))));
    }

    @When("The user impersonate Enis")
    public void theUserImpersonateEnis() {
        Driver.getDriver().get("https://diageo.efectura.com/UserManage/Edit/1812e8c5-a9f8-40c5-8f42-2aab632bf8d9");
        BrowserUtils.waitForVisibility(pages.generalPage().impersonateHoverBtn, 30);
        BrowserUtils.hoverOver(pages.generalPage().impersonateHoverBtn);
        pages.generalPage().impersonateButton.click();
        BrowserUtils.wait(3);
    }

    @Given("The user go to User Manage page")
    public void theUserGoToUserManagePage() {
        Driver.getDriver().get("https://diageo.efectura.com/UserManage");
    }
}
