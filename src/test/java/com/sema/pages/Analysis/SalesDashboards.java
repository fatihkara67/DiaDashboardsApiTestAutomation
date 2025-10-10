package com.sema.pages.Analysis;

import com.sema.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SalesDashboards extends BasePage {

    @FindBy(xpath = "//*[@id='impersonate']/span[2]")
    public WebElement impersonateHoverBtn;

    @FindBy(xpath = "//a[@id='impersonate-fletum']")
    public WebElement impersonateButton;

    
    //----------------------Iframes------------------------------------------
    @FindBy(xpath = "//iframe")
    public WebElement iframe;

    @FindBy(xpath = "//iframe[@id='dashboardSm-iframe']")
    public WebElement ekipIframe;

    @FindBy(xpath = "//iframe[@id='dashboardSalesSummary-iframe']")
    public WebElement summaryIframe;

    @FindBy(xpath = "//iframe[@id='dashboardLiveTrack-iframe']")
    public WebElement dailyLiveIframe;

    @FindBy(xpath = "//iframe[@id='dashboardSalesDetail-iframe']")
    public WebElement detailIframe;

    @FindBy(xpath = "//iframe[@id='dashboardVisit-iframe']")
    public WebElement visitIframe;
    
    @FindBy(xpath = "//iframe[@id='dashboardStock-iframe']")
    public WebElement stockIframe;

    @FindBy(xpath = "//th[contains(text(), '@diageo.com')]")
    public WebElement emailInDashboard;
//----------------------------------------------------------------------------------------    
    
//---------------------Error Elements-------------------------------------------------
    @FindBy(xpath = "//p[text()='No results were returned for this query']")
    public WebElement noResultForQuery;

    @FindBy(xpath = "//*[contains(text(), 'No data')]")
    public WebElement noData;

    @FindBy(xpath = "//*[contains(text(), 'No data after filtering or data is NULL')]")
    public WebElement noDataAfterFilterOrNull;
//--------------------------------------------------------------------------------------------

    @FindBy(xpath = "//ul[@class='sales-dashboards nav nav-tabs navigateTabs']/li/a")
    public List<WebElement> salesDashboardTabs;

    @FindBy(xpath = "//select[@id='dashboard_filter_sm']")
    public WebElement smFilterSelect;

    @FindBy(xpath = "//select[@id='dashboard_filter_fm']")
    public WebElement fmFilterSelect;

    @FindBy(xpath = "//select[@id='dashboard_filter_route']")
    public WebElement routeFilterSelect;

    @FindBy(xpath = "//span[@id='sm-dashboard-update']")
    public WebElement findButton;
    
    
    public WebElement getIframe(String tabName) {
        if (tabName.equalsIgnoreCase("Ekip")) 
            return ekipIframe;
        else if (tabName.equalsIgnoreCase("Özet"))
            return summaryIframe;
        else if (tabName.equalsIgnoreCase("Gün İçi"))
            return dailyLiveIframe;
        else if (tabName.equalsIgnoreCase("Detay"))
            return detailIframe;
        else if (tabName.equalsIgnoreCase("Ziyaret"))
            return visitIframe;
        else
            return stockIframe;
    }

}
