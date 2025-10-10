package com.sema.utilities;

import com.sema.pages.Analysis.Cocpit.CocpitGeneralDashboard;
import com.sema.pages.Analysis.RFM.RFM;
import com.sema.pages.Analysis.SalesDashboards;
import com.sema.pages.BPM.ModulFlows;
import com.sema.pages.BPM.Panel;
import com.sema.pages.BPM.TaskList;
import com.sema.pages.*;






public class Pages {

    private HomePage homePage;
    private LoginPage loginPage;
    private CocpitGeneralDashboard cocpitGeneralDashboard;
    private RFM rfm;
    private SalesDashboards salesDashboards;
    private ModulFlows modulFlows;
    private Panel panel;
    private GeneralPage generalPage;
    private ItemOverviewPage itemOverviewPage;
    private EditItemPage editItemPage;
    private TaskList taskList;



    public Pages() {
            this.homePage =new HomePage();
            this.loginPage =new LoginPage();
            this.cocpitGeneralDashboard = new CocpitGeneralDashboard();
            this.rfm= new RFM();
            this.salesDashboards = new SalesDashboards();
            this.modulFlows = new ModulFlows();
            this.panel = new Panel();
            this.generalPage = new GeneralPage();
            this.itemOverviewPage = new ItemOverviewPage();
            this.editItemPage = new EditItemPage();
            this.taskList = new TaskList();


    }
    // Getter metotları
    public HomePage homePage() {
        return homePage;
    }
    public LoginPage loginPage(){return loginPage;}
    public CocpitGeneralDashboard cocpit() {return cocpitGeneralDashboard;}
    public RFM rfm() {return rfm;}
    public SalesDashboards salesDashboards() {return  salesDashboards;}
    public ModulFlows modulFlows() {return modulFlows;}
    public Panel panel() {return panel;}
    public GeneralPage generalPage() {return generalPage;}
    public ItemOverviewPage itemOverviewPage() {return itemOverviewPage;}
    public EditItemPage editItemPage() {return editItemPage;}
    public TaskList taskList() {return taskList;}

}



