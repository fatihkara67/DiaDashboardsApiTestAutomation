package com.sema.stepDefs;

import com.sema.utilities.BrowserUtils;
import com.sema.utilities.ConfigurationReader;
import com.sema.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class ModuleFlowsStepDefs extends BaseStep {

    @Given("The user login with {string}")
    public void theUserLoginWith(String username) {
        BrowserUtils.wait(3);
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
        BrowserUtils.wait(3);
        BrowserUtils.waitForVisibility(pages.loginPage().getUsernameField(),60);
        pages.loginPage().loginWith(username);
    }

    @Given("The user go to {string} page")
    public void theUserGoToPage(String pageName) {
        BrowserUtils.wait(2);
        Driver.getDriver().get(ConfigurationReader.getProperty(pageName));
    }

    @Given("The user go in {string} flow")
    public void theUserGoInFlow(String formName) {
        pages.panel().goInFlow(formName);
    }

    @Given("The user select {string} as form type")
    public void theUserSelectAsFormType(String formType) {
        BrowserUtils.selectDropdownOptionByVisibleText(pages.panel().getFormTypeSelect(),formType);
        BrowserUtils.wait(7);
    }

    @Given("The user fill start form with musteri code {string}")
    public void theUserFillStartFormWithMusteriCode(String lastCustomerCode) {
        pages.modulFlows().fillModuleFlowForm(lastCustomerCode);
    }

    @Given("The user verify blocked budget with {string}")
    public void theUserVerifyBlockedBudgetWith(String budget) {
        boolean isBudgetOk = pages.modulFlows().verifyBlockedBudget(budget);
        Assert.assertTrue(isBudgetOk);
    }

    @Given("The user get blocked budget")
    public void theUserGetBlockedBudget() {
        pages.modulFlows().getBlockedBudget();
    }

    @Given("The user go in Task and submit")
    public void theUserGoInTaskAndSubmit() {
        pages.modulFlows().goInTaskAndSubmit();
    }

    @Given("The user fill vendor form with initial budget {string}")
    public void theUserFillVendorFormWithInitialBudget(String budget) {
        pages.modulFlows().fillVendorInitialForm(budget);
//        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
//        BrowserUtils.wait(19);
    }

    @Given("The user fill vendor invoice form with invoice {string}")
    public void theUserFillVendorInvoiceFormWithInvoice(String invoiceAmount) {
        pages.modulFlows().fillVendorInvoiceForm(invoiceAmount);
//        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
//        BrowserUtils.wait(19);
    }

    @Given("The user get actual budget")
    public void theUserGetActualBudget() {
        pages.modulFlows().getActualBudget();
    }

    @Given("The user verify actual budget with {string}")
    public void theUserVerifyActualBudgetWith(String expectedActualBudget) {
        boolean isBudgetOk = pages.modulFlows().verifyActualBudget(expectedActualBudget);
        Assert.assertTrue(isBudgetOk);
        BrowserUtils.wait(3);
    }

    @Given("The user go in Task {string}")
    public void theUserGoInTask(String tabTitleName) {
        pages.modulFlows().goInTask(tabTitleName);
    }

    @Given("The user submit the task")
    public void theUserSubmitTheTask() {
        BrowserUtils.wait(2);
        pages.modulFlows().submitTask();
//        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
//        BrowserUtils.wait(12);
    }

    @Given("The user change distributor help")
    public void theUserChangeDistributorHelp() {
        BrowserUtils.waitForVisibility(pages.modulFlows().getDistributorCheckbox(),30);
        BrowserUtils.wait(5);
        pages.modulFlows().getDistributorCheckbox().click();
//        BrowserUtils.wait(1);
        BrowserUtils.waitForVisibility(pages.modulFlows().getFlowInfoMessage(), 20);
    }


    @Given("The user reject the task")
    public void theUserRejectTheTask() {
        BrowserUtils.adjustScreenSize(75,Driver.getDriver());
        BrowserUtils.wait(2);
        pages.modulFlows().rejectTask();
    }

    @Given("The user revise the task")
    public void theUserReviseTheTask() {
        BrowserUtils.adjustScreenSize(75, Driver.getDriver());
        BrowserUtils.wait(2);
        pages.modulFlows().reviseTask();
    }

    @Given("The user fill start form with musteri code {string} and budget {string}")
    public void theUserFillStartFormWithMusteriCodeAndBudget(String lastCustomerCode, String budgetSupport) {
        pages.modulFlows().fillModuleBudgetForm(lastCustomerCode, budgetSupport);
    }


    @Given("The user fill invoice form with amount {string}")
    public void theUserFillInvoiceFormWithAmount(String invoiceAmount) {
        BrowserUtils.adjustScreenSize(75,Driver.getDriver());
        pages.modulFlows().fillInvoiceForm(invoiceAmount);
    }

    @Then("The user verify flow start page is open")
    public void theUserVerifyFlowStartPageIsOpen() {
        Assert.assertTrue(pages.panel().getFormTypeSelect().isDisplayed());
    }

    @When("The user go to task list")
    public void theUserGoToTaskList() {
        Driver.getDriver().get("https://diageo.efectura.com/Task/TaskList");
    }

    @When("The user enters {string} into search all")
    public void theUserEntersAhmetKayaIntoSearchAll(String filterInput) {
        BrowserUtils.wait(3);
        BrowserUtils.waitForVisibility(pages.taskList().getSearchAllFilterInput(),50);
        pages.taskList().getSearchAllFilterInput().sendKeys(filterInput);
        BrowserUtils.wait(5);
    }

    @When("The user click first row")
    public void theUserClickFirstRow() {
        pages.taskList().getFirstColumn().click();
        BrowserUtils.wait(1);
        BrowserUtils.switchToTabByTitleAndCloseOld("DIA: ConfirmationForm");
        BrowserUtils.wait(2);
    }

    @Then("The user verify form is open")
    public void theUserVerifyFormIsOpen() {
        Assert.assertEquals("Form Açılmadı", "DIA: ConfirmationForm",Driver.getDriver().getTitle());
    }
}
