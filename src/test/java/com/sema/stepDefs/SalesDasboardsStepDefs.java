package com.sema.stepDefs;

import com.sema.utilities.BrowserUtils;
import com.sema.utilities.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

public class SalesDasboardsStepDefs extends BaseStep {

    @When("The user impersonate region manager")
    public void theUserImpersonateRegionManager() {
        Driver.getDriver().get("https://diageo.efectura.com/UserManage/Edit/c6a48794-519b-41e8-bf2d-52f89f463d4a");
        BrowserUtils.waitForVisibility(pages.salesDashboards().impersonateHoverBtn, 30);
        BrowserUtils.hoverOver(pages.salesDashboards().impersonateHoverBtn);
        pages.salesDashboards().impersonateButton.click();
    }

    @When("The user go to Sales Dashboard")
    public void theUserGoToSalesDashboard() {
        Driver.getDriver().get("https://diageo.efectura.com/Enrich/SalesDashboards");
        BrowserUtils.wait(1);
    }

    @Then("The user verify name and mail {string}")
    public void theUserVerifyNameAndMail(String mail) {
        BrowserUtils.waitForVisibility(pages.salesDashboards().iframe,30);
        Driver.getDriver().switchTo().frame(pages.salesDashboards().getIframe("Ekip"));
        Driver.getDriver().switchTo().frame(0);
        BrowserUtils.waitForVisibility(pages.salesDashboards().emailInDashboard,30);
        System.out.println("Dashboard'taki Email: " + pages.salesDashboards().emailInDashboard.getText());
        Assert.assertEquals("Email eşleşmedi",mail, pages.salesDashboards().emailInDashboard.getText());

        Driver.getDriver().switchTo().parentFrame(); // içten dışa
        Driver.getDriver().switchTo().defaultContent();

    }

    @When("The user impersonate sales manager")
    public void theUserImpersonateSalesManager() {
        Driver.getDriver().get("https://diageo.efectura.com/UserManage/Edit/8fe3c8af-0b5a-4991-bfdf-09f08538fa27");
        BrowserUtils.waitForVisibility(pages.salesDashboards().impersonateHoverBtn, 30);
        BrowserUtils.hoverOver(pages.salesDashboards().impersonateHoverBtn);
        pages.salesDashboards().impersonateButton.click();
    }

    @When("The user impersonate field manager")
    public void theUserImpersonateFieldManager() {
        Driver.getDriver().get("https://diageo.efectura.com/UserManage/Edit/24782192-cf73-4279-ac79-eb93ca0da087");
        BrowserUtils.waitForVisibility(pages.salesDashboards().impersonateHoverBtn, 30);
        BrowserUtils.hoverOver(pages.salesDashboards().impersonateHoverBtn);
        pages.salesDashboards().impersonateButton.click();
    }

    @Then("The user verify dashboard data {string}")
    public void theUserVerifyDashboardData(String tabName) {
//        BrowserUtils.wait(2);
        pages.salesDashboards().salesDashboardTabs.forEach(el -> System.out.println(el.getText()));
        pages.salesDashboards().salesDashboardTabs.stream().
                filter(el -> el.getText().equals(tabName)).findFirst().ifPresent(WebElement::click);


        SoftAssert softAssert = new SoftAssert();
//        BrowserUtils.waitForVisibility(pages.salesDashboards().iframe,30);
        Driver.getDriver().switchTo().frame(pages.salesDashboards().getIframe(tabName));
        Driver.getDriver().switchTo().frame(0);

        softAssert.assertFalse(BrowserUtils.isElementDisplayed(pages.salesDashboards().noData),"No Data Hatası");
        softAssert.assertFalse(BrowserUtils.isElementDisplayed(pages.salesDashboards().noDataAfterFilterOrNull),"noDataAfterFilterOrNull Hatası");
        softAssert.assertFalse(BrowserUtils.isElementDisplayed(pages.salesDashboards().noResultForQuery),"noResultForQuery Hatası");
        Driver.getDriver().switchTo().parentFrame(); // içten dışa
        Driver.getDriver().switchTo().defaultContent();
        softAssert.assertAll();
    }

    @When("The user use filters region")
    public void theUserUseFiltersRegion() {
        BrowserUtils.selectDropdownOptionByIndex(pages.salesDashboards().smFilterSelect,1);
        BrowserUtils.selectDropdownOptionByIndex(pages.salesDashboards().fmFilterSelect,1);
        BrowserUtils.selectDropdownOptionByIndex(pages.salesDashboards().routeFilterSelect,1);
    }

    @When("The user click find button")
    public void theUserClickFindButton() {
        Assert.assertTrue(BrowserUtils.isButtonActive(pages.salesDashboards().findButton));
        pages.salesDashboards().findButton.click();
        BrowserUtils.wait(2);
    }

    @When("The user use filters sales")
    public void theUserUseFiltersSales() {
        BrowserUtils.selectDropdownOptionByIndex(pages.salesDashboards().fmFilterSelect,1);
        BrowserUtils.selectDropdownOptionByIndex(pages.salesDashboards().routeFilterSelect,1);
    }

    @When("The user use filters field")
    public void theUserUseFiltersField() {
        BrowserUtils.selectDropdownOptionByIndex(pages.salesDashboards().routeFilterSelect,1);
    }
}
