package com.sema.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "rerun:target/rerun.txt"
        },
        tags = "@widget",
        features = "src/test/java/com/sema/features",
        glue = "com/sema/stepDefs",
        dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @AfterSuite(alwaysRun = true)
    public void teardown() {
        File reportOutputDirectory = new File("target/cucumber-reports");
        generateReport(reportOutputDirectory.getAbsolutePath());
    }

    private void generateReport(String cucumberOutputPath) {
        Collection<File> jsonFiles =
                FileUtils.listFiles(new File(cucumberOutputPath), new String[]{"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));

        Configuration config = new Configuration(new File("target"), "Semaaa");
        new ReportBuilder(jsonPaths, config).generateReports();
    }
}
