package com.sema.pages;

import com.sema.utilities.BrowserUtils;
import com.sema.utilities.ConfigurationReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ItemOverviewPage extends BasePage {

    @FindBy(xpath = "//div[contains(@class,'accordion-container')]")
    private WebElement accordionContainer;

    @FindBy(xpath = "//div[contains(@class,'category-accordion')]")
    private WebElement overviewSideAccordion;

    public void goToItemOverviewPage(String item) {
        driver.get(ConfigurationReader.getProperty("itemLinkWithoutItemName") + item);
    }

    public void closeSideAccordionInOverview() {
        boolean isSidebarOpen = BrowserUtils.isElementDisplayed(accordionContainer);
        if (isSidebarOpen) {
            overviewSideAccordion.click();
        }
    }

}
