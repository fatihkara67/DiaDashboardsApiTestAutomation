package com.sema.pages.BPM;

import com.sema.pages.BasePage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class TaskList extends BasePage {

    @FindBy(id = "GeneralSearch")
    private WebElement searchAllFilterInput;

    @FindBy(xpath = "//tr/td[1]")
    private WebElement firstColumn;

}
