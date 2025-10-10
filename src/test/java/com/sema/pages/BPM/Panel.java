package com.sema.pages.BPM;

import com.sema.pages.BasePage;
import com.sema.utilities.BrowserUtils;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class Panel extends BasePage {

    @FindBy(xpath = "//tr/td[3]")
    private List<WebElement> formNames;

    @FindBy(xpath = "//td/a")
    private List<WebElement> flowStartButtons;

    @FindBy(xpath = "//select[@id='formType']")
    private WebElement formTypeSelect;

    @FindBy(xpath = "//button[contains(@class, 'edit-dashboard-bpm') and span[contains(text(), 'Devam Etmekte')]]")
    private List<WebElement> ongoingNavigateButton;

    @FindBy(xpath = "//div[contains(@class,'table-action')]/button[contains(@class, 't-preview')][1]")
    private List<WebElement> processListFlowStatusButtons;

    @FindBy(xpath = "//div[contains(@class,'table-action')]/button[contains(@class, 't-preview')][2]")
    private List<WebElement> processListFlowDetailButtons;

    @FindBy(xpath = "//h4[contains(text(),'Akış Detayları')]")
    private WebElement processListDetailModalHeader;

    @FindBy(xpath = "//table[@id='process-detail']")
    private WebElement processDetailTable;


    public void goInFlow(String formName) {
        BrowserUtils.wait(1);
        for (int i = 0; i < formNames.size(); i++) {
            System.out.println("Form: " + formNames.get(i).getText());
            if (formNames.get(i).getText().equalsIgnoreCase(formName)) {
                flowStartButtons.get(i).click();
            }
        }
        BrowserUtils.waitForVisibility(formTypeSelect,5);
    }

}
