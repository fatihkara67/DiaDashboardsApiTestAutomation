package com.sema.pages;

import com.sema.utilities.BrowserUtils;
import com.sema.utilities.Driver;
import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.sema.utilities.BrowserUtils.isButtonActive;

@Getter
public class GeneralPage extends BasePage {

    @FindBy(xpath = "//button[contains(@id,'-reset-basic')]")
    private WebElement basicResetButton;

    @FindBy(xpath = "//a[contains(@class,'t-edit')]")
    private WebElement editBtnInTable;

    @FindBy(xpath = "//div[@class='notyf__message']")
    private WebElement infoMessage;

    @FindBy(xpath = "//iframe[@src='https://diageoms.efectura.com:9010/hangfire']")
    private WebElement jobsFrame;

    @FindBy(xpath = "//h1[text()='Overview']")
    private WebElement jobsHeader;

    @FindBy(xpath = "//iframe[contains(@src,'/Enrich/EmbedDashboard?dashboardName=report-analysis')]")
    private WebElement reportCardAnalysisMainFrame;

    @FindBy(xpath = "//iframe[contains(@src,'https://dia-dashboard.efectura.com/embedded/')]")
    private WebElement reportCardAnalysisSecondFrame;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div/div/div/div/div/div/div[1]/div/div/div[1]/div/div/div[2]/div/div[1]/div/div/div/div/div/div/div[1]/div/div[2]/div/div/div/div/div/div/div[2]/div[2]/table")
    private WebElement cardAnalysisTable;

    @FindBy(xpath = "//*[@id='impersonate']/span[2]")
    public WebElement impersonateHoverBtn;

    @FindBy(xpath = "//a[@id='impersonate-fletum']")
    public WebElement impersonateButton;



    public void useTextFilter(String value, String columnName) {
        // (//table[.//thead/tr[1]/th[normalize-space()='Fletum Kod']] )[1]//thead/tr[2]/th[position() = count((//table[.//thead/tr[1]/th[normalize-space()='Fletum Kod']])[1]//thead/tr[1]/th[normalize-space()='Fletum Kod']/preceding-sibling::th) + 1 ]//input
        String locate = "(//table[.//thead/tr[1]/th[normalize-space()='" + columnName + "']] )[1]//thead/tr[2]/th[position() = count((//table[.//thead/tr[1]/th[normalize-space()='" + columnName + "']])[1]//thead/tr[1]/th[normalize-space()='" + columnName + "']/preceding-sibling::th) + 1 ]//input";

        BrowserUtils.wait(5);
        WebElement filterInput = Driver.getDriver().findElement(By.xpath(locate));
        BrowserUtils.waitForVisibility(filterInput,40);
        filterInput.sendKeys(value + Keys.ENTER);

    }

    public void clickPaginationButton(String btnName) {
        BrowserUtils.wait(10);
        String locate = "//div[@class='pagination-container']//*[contains(translate(@id, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" +
                btnName + "')]";
        WebElement btn = driver.findElement(By.xpath(locate));
//        WebElement btn2 = driver.findElement(By.id(btnName));
//        WebElement button = driver.findElement
//                (By.xpath("//div[@class='pagination-container']//button[@id='" + btnName + "']"));
        btn.click();
    }

    public void verifyButtonStatus(String btnName, String btnStatus) {
        BrowserUtils.wait(4);
        String locate = "//div[@class='pagination-container']//*[contains(translate(@id, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" +
                btnName + "')]";
        System.out.println(locate);
        WebElement btn = driver.findElement(By.xpath(locate));
//        WebElement button = driver.findElement(By.id(btnName));
        if(btnStatus.equalsIgnoreCase("Active")) {
            Assert.assertTrue(isButtonActive(btn));
        } else if (btnStatus.equalsIgnoreCase("Passive")) {
            Assert.assertFalse(isButtonActive(btn));
        }
    }

    // you can use selectFilter as IsAssociated, Family, ItemStatuses in feature file
    public void selectOptionInSelectFilter(String selectOption, String selectFilter) {

        WebElement selectElement = driver.findElement(By.xpath("//select[contains(@id,'-" + selectFilter + "')]"));
        BrowserUtils.selectDropdownOptionByVisibleText(selectElement,selectOption);
        BrowserUtils.wait(3);
    }


}
