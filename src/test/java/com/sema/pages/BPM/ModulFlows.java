package com.sema.pages.BPM;

import com.sema.pages.BasePage;
import com.sema.utilities.BrowserUtils;
import com.sema.utilities.ConfigurationReader;
import com.sema.utilities.Database;
import com.sema.utilities.Driver;
import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

@Getter
public class ModulFlows extends BasePage {

    TaskList taskList = new TaskList();

    @FindBy(xpath = "//input[@id='formNumber2']")
    private WebElement formNumberInput;

    @FindBy(xpath = "//input[@id='noktaTrackKodu2']")
    private WebElement musteriNoInputBox;

    //    ----------------------Module Budget---------------------------------
    @FindBy(xpath = "//input[@id='formNumber']")
    private WebElement modulBudgetFormNumberInput;

    @FindBy(xpath = "//input[@id='butceDestegi']")
    private WebElement budgetSupportInputBox;

    @FindBy(xpath = "//input[@id='calismaTarihi']")
    private WebElement moduleBudgetDate;

    @FindBy(xpath = "(//span[@class='flatpickr-day today'])[1]")
    private WebElement moduleBudgetTodayDate;

    @FindBy(xpath = "//input[@id='noktaTrackKodu']")
    private WebElement modulBudgetMusteriNoInput;

    @FindBy(xpath = "//button[@id='searchButton']")
    private WebElement modulBudgetGetirBtn;

    @FindBy(xpath = "//input[@id='docDate']")
    private WebElement modulBudgetDocDateInput;

    @FindBy(xpath = "//select[@id='docType']")
    private WebElement modulBudgetDocTypeSelect;

    @FindBy(xpath = "(//span[contains(@class,'flatpickr-day today')])[2]")
    private WebElement modulBudgetDocTodayDate;

    @FindBy(xpath = "//input[@id='fileInput']")
    private WebElement modulBudgetFileInput;

    @FindBy(xpath = "(//button[contains(text(),'Onaya Gönder')])[1]")
    private WebElement modulBudgetFlowSubmitButton;

    @FindBy(xpath = "(//input[contains(@id,'madde2')])[1]")
    private WebElement moduleBudgetStartFormFirstCheckbox;

    @FindBy(xpath = "(//input[contains(@id,'madde5')])[1]")
    private WebElement moduleBudgetStartFormFourthCheckbox;

    @FindBy(xpath = "(//input[contains(@id,'madde1')])[1]")
    private WebElement moduleBudgetStartFormFifthCheckbox;

    @FindBy(xpath = "//input[@id='ciroOrani']")
    private WebElement moduleBudgetDateCiroOraniInput;

    @FindBy(xpath = "//input[@id='musteriAdi']")
    private WebElement customerNameInput;

    @FindBy(xpath = "//input[@id='bankaAdi']")
    private WebElement bankNameInput;

    @FindBy(xpath = "//input[@id='bankaSube']")
    private WebElement bankSubeInput;

    @FindBy(xpath = "//input[@id='ibanNo']")
    private WebElement ibanNoInput;

    @FindBy(xpath = "//input[@id='invoiceAmount']")
    private WebElement invoiceAmount;

    @FindBy(xpath = "//input[@id='invoiceNo']")
    private WebElement invoiceNo;

    @FindBy(xpath = "//td[contains(text(),'Mey Faturası')]")
    private WebElement meyInvoiceUploadInfo;

    @FindBy(xpath = "(//span[contains(@class,'flatpickr-day today')])[1]")
    private WebElement moduleBudgetInvoiceTodayDate;

    @FindBy(xpath = "//div[contains(@class, 'notyf__wrapper')]/div[2]")
    private WebElement moduleBudgetInvoiceInfoMessage;



//    ----------------------------------------------------

    @FindBy(xpath = "//button[@id='searchButton2']")
    private WebElement getirBtn;

    @FindBy(xpath = "//span[@id='select2-vendorList-container']")
    private WebElement vendorSelect;

    @FindBy(xpath = "//input[@type='search']")
    private WebElement vendorInput;

    @FindBy(xpath = "//li[contains(.,'Efectura Vendor')]")
    private WebElement efecturaVendorOption;

    @FindBy(xpath = "(//input[contains(@id,'madde2')])[2]")
    private WebElement startFormFirstCheckbox;

    @FindBy(xpath = "(//input[contains(@id,'madde5')])[2]")
    private WebElement startFormFourthCheckbox;

    @FindBy(xpath = "(//input[contains(@id,'madde1')])[2]")
    private WebElement startFormFifthCheckbox;

    @FindBy(xpath = "//input[@id='ciroOrani2']")
    private WebElement ciroOraniInput;

    @FindBy(xpath = "//select[@id='docType2']")
    private WebElement docTypeSelect;

    @FindBy(xpath = "//input[@id='docDate2']")
    private WebElement docDateInput;

    @FindBy(xpath = "//input[@id='fileInput2']")
    private WebElement fileInput;

    @FindBy(xpath = "//input[@id='butceDestegi']")
    private WebElement initialBudgetInput;

    @FindBy(xpath = "//select[@id='docType']")
    private WebElement vendorDocTypeSelect;

    @FindBy(xpath = "//input[@id='docDate']")
    private WebElement vendorDocDateInput;

    @FindBy(xpath = "//input[@id='fileInput']")
    private WebElement vendorFileInput;

    @FindBy(xpath = "//span[contains(@class,'flatpickr-day today')]")
    private WebElement vendorTodayDate;

    @FindBy(xpath = "(//span[@class='flatpickr-day today'])[3]")
    private WebElement todayDate;

    @FindBy(xpath = "//ul[@id='select2-docType2-results']/li/span")
    private List<WebElement> docOptions;

    @FindBy(xpath = "(//button[contains(text(),'Onaya Gönder')])[2]")
    private WebElement flowSubmitButton;

    @FindBy(xpath = "/html/body/div[8]/div/div[1]/div[2]")
    private WebElement startFormInfoMessage;

    @FindBy(xpath = "/html/body/div[7]/div/div[1]/div[2]")
    private WebElement flowInfoMessage;

    @FindBy(xpath = "//button[@id='onayla']")
    private WebElement formSubmitButton;

    @FindBy(xpath = "//button[@id='reddet']")
    private WebElement formRejectButton;

    @FindBy(xpath = "//button[@id='revize-et']")
    private WebElement formReviseButton;

    @FindBy(xpath = "//button[@id='confirmBtn']")
    private WebElement confirmButton;

    @FindBy(xpath = "//h4[contains(text(),'Nokta Bilgileri')]")
    private WebElement trackInfoHeader;

    @FindBy(xpath = "//td[contains(text(),'Tütün Alkol Denetim Belgesi')]")
    private WebElement tadbUploadInfo;

    @FindBy(xpath = "//td[contains(text(),'Tasarım Dosyası')]")
    private WebElement tasarimDosyaUploadInfo;

    @FindBy(xpath = "//button[contains(text(),'İlerlet')]")
    private WebElement ilerletButton;

    @FindBy(xpath = "//input[@id='invoiceAmount']")
    private WebElement invoiceAmountInput;

    @FindBy(xpath = "//input[@id='invoiceNo']")
    private WebElement invoiceNoInput;

    @FindBy(xpath = "//td[contains(text(),'Montaj Görseli')]")
    private WebElement montajImageUploadInfo;

    @FindBy(xpath = "//td[contains(text(),'Tedarikçi Faturası')]")
    private WebElement vendorInvoiceUploadInfo;

    @FindBy(xpath = "//button[contains(text(),'Onaya Gönder')]")
    private WebElement onayaGonderButton;

    @FindBy(xpath = "//input[@id='isDistrubutor']")
    private WebElement distributorCheckbox;


    String formNumber = "";
    public void fillModuleFlowForm(String lastCustomerCode) {
        BrowserUtils.adjustScreenSize(80,Driver.getDriver());
        formNumber = BrowserUtils.getValueInInputBox(formNumberInput);
        System.out.println("Form Number: " + formNumber);
        musteriNoInputBox.sendKeys(lastCustomerCode);
        getirBtn.click();
        startFormFirstCheckbox.click();
        startFormFourthCheckbox.click();
        ciroOraniInput.sendKeys("30");
        startFormFifthCheckbox.click();
        BrowserUtils.wait(3);
        BrowserUtils.waitForVisibility(trackInfoHeader,20);
        vendorSelect.click();
        vendorInput.sendKeys("Efectura Vendor");
        efecturaVendorOption.click();
        BrowserUtils.scrollToElement(Driver.getDriver(), docDateInput);

        List<String> docTypes = Arrays.asList("İşletme Modül Talep Formu","Rekabet Bildirim Formu","Tütün Alkol Denetim Belgesi");

        for (int i = 0; i < 3; i++) {
            BrowserUtils.selectDropdownOptionByVisibleText(docTypeSelect,docTypes.get(i));
            docDateInput.click();
            todayDate.click();
            fileInput.sendKeys("C:\\Users\\fkara\\Desktop\\workspace\\DIAGEO-PROD\\src\\test\\java\\com\\sema\\pages\\BPM\\ModulFlows.java");
            BrowserUtils.wait(2);

            WebElement file = driver.findElement(By.xpath("//td[contains(text(),'" + docTypes.get(i) +"')]"));
            BrowserUtils.waitForVisibility(file,60);

        }
        BrowserUtils.waitForVisibility(tadbUploadInfo,60);
        BrowserUtils.scrollToElement(Driver.getDriver(), flowSubmitButton);
        flowSubmitButton.click();
        BrowserUtils.waitForVisibility(startFormInfoMessage,60);
        Assert.assertEquals("Başarılı", startFormInfoMessage.getText());
    }

    public boolean verifyBlockedBudget(String budget) {
        String query = "SELECT * FROM DEV_MDM.dbo.vw_ModuleFlowTransactionTotals vmftt \n" +
                "WHERE MudurlukAdi = 'BolgeMuduru' AND ButceYili = 'F26'";

        int totalBlockedBudget = 0;
        try (Connection conn = Database.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                totalBlockedBudget = rs.getInt("ToplamBlokeButce");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Akış Sonrası Bloke Bütçe: " + totalBlockedBudget);
//        return totalBlockedBudget == firstBlockedBudget + Integer.parseInt(budget);
        Assert.assertEquals(totalBlockedBudget,firstBlockedBudget + Integer.parseInt(budget));
        return true;
    }

    int firstBlockedBudget;
    public void getBlockedBudget() {
        String query = "SELECT * FROM DEV_MDM.dbo.vw_ModuleFlowTransactionTotals vmftt \n" +
                "WHERE MudurlukAdi = 'BolgeMuduru' AND ButceYili = 'F26'";

        int totalBlockedBudget = 0;
        try (Connection conn = Database.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // İlk kolondaki tarih bilgisini al
                totalBlockedBudget = rs.getInt("ToplamBlokeButce");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        firstBlockedBudget = totalBlockedBudget;
        System.out.println("Akış Öncesi Bloke:  " + firstBlockedBudget);
    }

    public void goInTaskAndSubmit() {
        BrowserUtils.wait(1);
        Driver.getDriver().get(ConfigurationReader.getProperty("taskList"));
        BrowserUtils.wait(3);
        BrowserUtils.waitForVisibility(taskList.getSearchAllFilterInput(),50);
        taskList.getSearchAllFilterInput().sendKeys(formNumber);
        BrowserUtils.wait(5);
        taskList.getFirstColumn().click();
        BrowserUtils.wait(1);
//        BrowserUtils.switchToTab("DIA: ConfirmationForm");
        BrowserUtils.switchToTabByTitleAndCloseOld("DIA: ConfirmationForm");
        BrowserUtils.wait(3);
        System.out.println("Title: " + Driver.getDriver().getTitle());
        BrowserUtils.scrollToElement(Driver.getDriver(), formSubmitButton);
        BrowserUtils.wait(10);
        BrowserUtils.waitForClickability(formSubmitButton,35);
        BrowserUtils.adjustScreenSize(50,driver);
        formSubmitButton.click();
        BrowserUtils.waitForVisibility(flowInfoMessage,60);
        Assert.assertEquals("Başarılı", flowInfoMessage.getText());
    }

    String vendorInitialBudget = "";
    public void fillVendorInitialForm(String budget) {
        BrowserUtils.wait(2);
        Driver.getDriver().get(ConfigurationReader.getProperty("taskListVendor"));
        BrowserUtils.wait(3);
        BrowserUtils.waitForVisibility(taskList.getSearchAllFilterInput(),60);
        taskList.getSearchAllFilterInput().sendKeys(formNumber);
        BrowserUtils.wait(4);
        taskList.getFirstColumn().click();
        BrowserUtils.switchToTabByTitleAndCloseOld("DIA: SalesFlowForm");
        BrowserUtils.wait(3);
        System.out.println("Title: " + Driver.getDriver().getTitle());

        initialBudgetInput.sendKeys(budget);

        List<String> docTypes = Arrays.asList("Bütçe Detay Hesaplamaları","Tasarım Dosyası");

        for (String docType : docTypes) {
            BrowserUtils.selectDropdownOptionByVisibleText(vendorDocTypeSelect, docType);
            vendorDocDateInput.click();
            vendorTodayDate.click();
            vendorFileInput.sendKeys("C:\\Users\\fkara\\Desktop\\workspace\\DIAGEO-PROD\\src\\test\\java\\com\\sema\\pages\\BPM\\ModulFlows.java");
//            BrowserUtils.wait(2);

            WebElement file = driver.findElement(By.xpath("//td[contains(text(),'" + docType +"')]"));
            BrowserUtils.waitForVisibility(file,30);

        }

        BrowserUtils.waitForVisibility(tasarimDosyaUploadInfo,30);
        ilerletButton.click();
        BrowserUtils.waitForVisibility(flowInfoMessage,60);
        Assert.assertEquals("Başarılı", flowInfoMessage.getText());
        vendorInitialBudget = budget;
        System.out.println("Vendor Ininital Budget = " + vendorInitialBudget);
    }

    String invoiceAmountInForm;
    public void fillVendorInvoiceForm(String invoiceAmount) {
        BrowserUtils.wait(2);
        Driver.getDriver().get(ConfigurationReader.getProperty("taskListVendor"));
        BrowserUtils.wait(3);
        BrowserUtils.waitForVisibility(taskList.getSearchAllFilterInput(),60);
        taskList.getSearchAllFilterInput().sendKeys(formNumber);
        BrowserUtils.wait(7);
        taskList.getFirstColumn().click();
        BrowserUtils.switchToTabByTitleAndCloseOld("DIA: SalesFlowForm");
        BrowserUtils.wait(3);
        System.out.println("Title: " + Driver.getDriver().getTitle());

        List<String> docTypes = Arrays.asList("Tedarikçi Faturası","Montaj Görseli");

        BrowserUtils.selectDropdownOptionByVisibleText(vendorDocTypeSelect, "Tedarikçi Faturası");
        invoiceAmountInput.sendKeys(invoiceAmount);
        invoiceNoInput.sendKeys("555");
        vendorDocDateInput.click();
        vendorTodayDate.click();
        vendorFileInput.sendKeys("C:\\Users\\fkara\\Desktop\\workspace\\DIAGEO-PROD\\src\\test\\java\\com\\sema\\pages\\BPM\\ModulFlows.java");
        BrowserUtils.wait(2);
        BrowserUtils.waitForVisibility(vendorInvoiceUploadInfo,60);

        BrowserUtils.selectDropdownOptionByVisibleText(vendorDocTypeSelect, "Montaj Görseli");
        vendorDocDateInput.click();
        vendorTodayDate.click();
        vendorFileInput.sendKeys("C:\\Users\\fkara\\Desktop\\workspace\\DIAGEO-PROD\\src\\test\\java\\com\\sema\\pages\\BPM\\ModulFlows.java");
        BrowserUtils.wait(2);

        BrowserUtils.waitForVisibility(montajImageUploadInfo,60);
        onayaGonderButton.click();

        BrowserUtils.waitForVisibility(flowInfoMessage,60);
        Assert.assertEquals("Başarılı", flowInfoMessage.getText());
        invoiceAmountInForm = invoiceAmount;
        System.out.println("Vendor Invoice Amount = " + invoiceAmountInForm);
    }

    int actualBudgetBeforeFlow;
    public void getActualBudget() {
        String query = "SELECT * FROM DEV_MDM.dbo.vw_ModuleFlowTransactionTotals vmftt WHERE MudurlukAdi = 'BolgeMuduru' AND ButceYili = 'F26'";

        int totalActualBudget = 0;
        try (Connection conn = Database.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // İlk kolondaki tarih bilgisini al
                totalActualBudget = rs.getInt("ToplamGerceklesenButce");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        actualBudgetBeforeFlow = totalActualBudget;
        System.out.println("Akış Öncesi Gerçekleşen:  " + actualBudgetBeforeFlow);
    }

    public boolean verifyActualBudget(String expectedActualBudget) {
        String query = "SELECT * FROM DEV_MDM.dbo.vw_ModuleFlowTransactionTotals vmftt WHERE MudurlukAdi = 'BolgeMuduru' AND ButceYili = 'F26'";

        int totalActualBudget = 0;
        try (Connection conn = Database.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                totalActualBudget = rs.getInt("ToplamGerceklesenButce");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Akış Sonrası Gerçekleşen Bütçe: " + totalActualBudget);
        Assert.assertEquals(actualBudgetBeforeFlow + Integer.parseInt(expectedActualBudget),totalActualBudget);
//        return totalActualBudget == actualBudgetBeforeFlow + Integer.parseInt(expectedActualBudget);
        return true;
    }

    public void goInTask(String tabTitleName) {


        BrowserUtils.wait(1);
        Driver.getDriver().get(ConfigurationReader.getProperty("taskList"));
        BrowserUtils.wait(3);
        BrowserUtils.waitForVisibility(taskList.getSearchAllFilterInput(),50);
        taskList.getSearchAllFilterInput().sendKeys(formNumber);
        BrowserUtils.wait(5);
        taskList.getFirstColumn().click();

        BrowserUtils.switchToTabByTitleAndCloseOld(tabTitleName);
        BrowserUtils.wait(3);
        System.out.println("Title: " + Driver.getDriver().getTitle());



//        BrowserUtils.wait(1);
//        Driver.getDriver().get("https://dia-preprod-ui.efectura.com/Task/TaskList");
//        BrowserUtils.wait(3);
//        BrowserUtils.waitForVisibility(taskList.getSearchAllFilterInput(),60);
//        taskList.getSearchAllFilterInput().sendKeys(formNumber);
//        BrowserUtils.wait(5);
//        taskList.getFirstColumn().click();
//
//        BrowserUtils.switchToTabByTitleAndCloseOld(tabTitleName);
//        BrowserUtils.wait(3);
//        System.out.println("Title: " + Driver.getDriver().getTitle());
    }

    public void submitTask() {
        BrowserUtils.scrollToElement(Driver.getDriver(), formSubmitButton);
        BrowserUtils.wait(5);
        BrowserUtils.waitForClickability(formSubmitButton,30);
        formSubmitButton.click();
        BrowserUtils.waitForVisibility(flowInfoMessage,20);
        Assert.assertEquals("Başarılı", flowInfoMessage.getText());
    }

    public void rejectTask() {
        BrowserUtils.scrollToElement(Driver.getDriver(), formRejectButton);
        BrowserUtils.wait(2);
        formRejectButton.click();
        confirmButton.click();
        BrowserUtils.waitForVisibility(flowInfoMessage,60);
        Assert.assertEquals("Başarılı", flowInfoMessage.getText());
    }

    public void reviseTask() {
        BrowserUtils.scrollToElement(Driver.getDriver(), formReviseButton);
        BrowserUtils.wait(2);
        formReviseButton.click();
        confirmButton.click();
        BrowserUtils.waitForVisibility(flowInfoMessage,60);
        Assert.assertEquals("Başarılı", flowInfoMessage.getText());
    }

    public void fillModuleBudgetForm(String lastCustomerCode, String budgetSupport) {
//        BrowserUtils.adjustScreenSize(50,Driver.getDriver());
        formNumber = BrowserUtils.getValueInInputBox(modulBudgetFormNumberInput);
        System.out.println("Form Number: " + formNumber);
        modulBudgetMusteriNoInput.sendKeys(lastCustomerCode);
        modulBudgetGetirBtn.click();

        moduleBudgetDate.click();
        moduleBudgetTodayDate.click();
        budgetSupportInputBox.sendKeys(budgetSupport);

        moduleBudgetStartFormFirstCheckbox.click();
        moduleBudgetStartFormFourthCheckbox.click();
        moduleBudgetDateCiroOraniInput.sendKeys("30");
        moduleBudgetStartFormFifthCheckbox.click();
        BrowserUtils.wait(3);
        BrowserUtils.waitForVisibility(trackInfoHeader,20);
        BrowserUtils.scrollToElement(Driver.getDriver(), docDateInput);

        List<String> docTypes = Arrays.asList(
                "İşletme Modül Talep Formu","Rekabet Bildirim Formu","Bütçe Desteği Kanıt Dokümanı", "Tütün Alkol Denetim Belgesi"
        );

        for (int i = 0; i < 4; i++) {
            BrowserUtils.selectDropdownOptionByVisibleText(modulBudgetDocTypeSelect,docTypes.get(i));
            modulBudgetDocDateInput.click();
            BrowserUtils.wait(1);
            modulBudgetDocTodayDate.click();
            modulBudgetFileInput.sendKeys("C:\\Users\\fkara\\Desktop\\workspace\\DIAGEO-PROD\\src\\test\\java\\com\\sema\\pages\\BPM\\ModulFlows.java");
//            BrowserUtils.wait(1);
            WebElement file = driver.findElement(By.xpath("//td[contains(text(),'" + docTypes.get(i) +"')]"));
            BrowserUtils.waitForVisibility(file,30);
        }
        BrowserUtils.waitForVisibility(tadbUploadInfo,40);
        BrowserUtils.scrollToElement(Driver.getDriver(), modulBudgetFlowSubmitButton);
        modulBudgetFlowSubmitButton.click();
        BrowserUtils.waitForVisibility(startFormInfoMessage,60);
        Assert.assertEquals("Başarılı", startFormInfoMessage.getText());
    }

    public void fillInvoiceForm(String invoiceAmount) {
        customerNameInput.sendKeys("DamlaPlastic");
        bankNameInput.sendKeys("IsBank");
        bankSubeInput.sendKeys("67670");
        ibanNoInput.sendKeys("TR1213456789");

        List<String> docTypes = Arrays.asList(
                "Vergi Levhası","Ticaret Sicil Gazetesi","İmza sirküsü", "Tedarikçi Faturası","Mey Faturası"
        );

        for (String docType : docTypes) {
            BrowserUtils.selectDropdownOptionByVisibleText(modulBudgetDocTypeSelect, docType);
            modulBudgetDocDateInput.click();
            BrowserUtils.wait(1);
            moduleBudgetInvoiceTodayDate.click();

            if (docType.contains("Fatura")) {
                invoiceAmountInput.sendKeys(Keys.CONTROL + "a");
                invoiceAmountInput.sendKeys(invoiceAmount);
                invoiceNoInput.sendKeys(Keys.CONTROL + "a");
                invoiceNoInput.sendKeys("6767");
            }

            modulBudgetFileInput.sendKeys("C:\\Users\\fkara\\Desktop\\workspace\\DIAGEO-PROD\\src\\test\\java\\com\\sema\\pages\\BPM\\ModulFlows.java");
            BrowserUtils.wait(2);

        }

        BrowserUtils.waitForVisibility(meyInvoiceUploadInfo,50);
        BrowserUtils.scrollToElement(Driver.getDriver(), modulBudgetFlowSubmitButton);
        modulBudgetFlowSubmitButton.click();
        BrowserUtils.waitForVisibility(moduleBudgetInvoiceInfoMessage,60);
        Assert.assertEquals("Başarılı", moduleBudgetInvoiceInfoMessage.getText());

    }

}
