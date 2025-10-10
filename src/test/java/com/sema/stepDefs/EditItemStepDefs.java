package com.sema.stepDefs;

import com.sema.utilities.BrowserUtils;
import com.sema.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class EditItemStepDefs extends BaseStep {

    @And("The user select item at order {int} in association tab")
    public void theUserSelectItemAtOrderInAssociationTab(int assocCheckboxOrder) {
        pages.editItemPage().selectItemAtOrderInAssociationTab(assocCheckboxOrder);
    }

    @And("The user clicks save button in edit item")
    public void theUserClicksSaveButtonInEditItem() {
//        pages.editItemPage().getAccordionButton().click();
        BrowserUtils.waitForVisibility(pages.editItemPage().getSaveButton(),10);
        pages.editItemPage().getSaveButton().click();
    }

    @And("The user clicks save button in edit item save modal")
    public void theUserClicksSaveButtonInEditItemSaveModal() {
        pages.editItemPage().getSaveBtnInEditItemSaveModal().click();
    }

    @Then("The user verifies info {string} appears")
    public void theUserVerifiesInfoAppears(String expectedMessage) {
        BrowserUtils.wait(1);
        BrowserUtils.waitForVisibility(pages.generalPage().getInfoMessage(),20);
        Assert.assertEquals(expectedMessage, pages.generalPage().getInfoMessage().getText());
        BrowserUtils.wait(1);
    }

    @And("the user unassociate the item")
    public void theUserUnassociateTheItem() {
        pages.editItemPage().unassociate(pages);
    }

    @When("The user click import redirect button")
    public void theUserClickImportRedirectButton() {
        pages.editItemPage().getImportRedirectButton().click();
        BrowserUtils.wait(1);
        BrowserUtils.switchToTabByTitleAndCloseOld("DIA: İçe aktarım");
        BrowserUtils.wait(1);
        System.out.println("Title: " + Driver.getDriver().getTitle());

    }

    @Then("The user verify import page is open")
    public void theUserVerifyImportPageIsOpen() {
        Assert.assertEquals("Import Sayfası açılmadı",
                "https://diageo.efectura.com/Import", Driver.getDriver().getCurrentUrl());
    }
}
