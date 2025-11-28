package com.sema.stepDefs;

import com.sema.utilities.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WidgetsStepDefs extends BaseStep {

    String currentMonth;
    String currentYear;
    double fValueW11;
    double totalF26W11;
    @Given("The user send widget11 request")
    public void theUserSendWidget11Request() throws IOException {
        JSONObject w11JsonBody = Requests.sendWidget11Request();

        System.out.println("currentMonth: " + currentMonth);
        System.out.println("currentYear: " + currentYear);

        System.out.println("W11 response: " + w11JsonBody);

        JSONArray dataArray = w11JsonBody
                .getJSONArray("result")
                .getJSONObject(0)
                .getJSONArray("data");

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject obj = dataArray.getJSONObject(i);
            if (currentMonth.equals(obj.getString("PMonth"))) {
                // F26 değerini al
                fValueW11 = obj.getDouble(currentYear);
                System.out.println(currentMonth + " - " + currentYear  + " değeri: " + fValueW11);
                break;
            }
        }

//        JSONArray dataArray = w11JsonBody.getJSONArray("result")
//                .getJSONObject(0)
//                .getJSONArray("data");

        totalF26W11 = 0.0;

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject row = dataArray.getJSONObject(i);
            if (row.has("F26") && !row.isNull("F26")) {
                totalF26W11 += row.getDouble("F26");
            }
        }

        System.out.println("F26 toplamı: " + totalF26W11);

    }


    double currentActualW12;
    double currentTargetW12;

    @Given("The user send widget12 request")
    public void theUserSendWidget12Request() throws IOException {
        JSONObject w12JsonBody = Requests.sendWidget12Request();
        System.out.println("w12JsonBody: " + w12JsonBody);

        currentActualW12 = 0.0;
        currentTargetW12 = 0.0;

        if (w12JsonBody == null) return;

        JSONArray results = w12JsonBody.optJSONArray("result");
        if (results == null || results.length() == 0) return;

        // === 1) En güncel "Gerçekleşme"yi bul ===
        String actualKey = "Gerçekleşme";
        Double latestActual = null;
        long latestActualMonth = Long.MIN_VALUE;

        for (int r = 0; r < results.length(); r++) {
            JSONObject block = results.optJSONObject(r);
            if (block == null) continue;

            // Bu blok "Gerçekleşme" kolonu içeriyor mu?
            JSONArray colnames = block.optJSONArray("colnames");
            if (colnames == null) continue;

            boolean hasActual = false;
            for (int c = 0; c < colnames.length(); c++) {
                if (actualKey.equals(colnames.optString(c))) {
                    hasActual = true;
                    break;
                }
            }
            if (!hasActual) continue;

            // Data'yı tara ve en büyük FISCALMONTH'u seç
            JSONArray data = block.optJSONArray("data");
            if (data == null) continue;

            for (int i = 0; i < data.length(); i++) {
                JSONObject row = data.optJSONObject(i);
                if (row == null) continue;

                if (!row.has("FISCALMONTH") || !row.has(actualKey) || row.isNull(actualKey)) continue;

                // Superset/ClickHouse bazen ms'i double döndürüyor → long'a çevir
                long fm = (long) row.optDouble("FISCALMONTH", -1);
                if (fm > latestActualMonth) {
                    latestActualMonth = fm;
                    latestActual = row.optDouble(actualKey, 0.0);
                }
            }
        }

        if (latestActual != null) {
            currentActualW12 = latestActual;
            System.out.println("En güncel Gerçekleşme ay (FISCALMONTH): " + latestActualMonth);
            System.out.println("currentActualW12: " + currentActualW12);
        } else {
            System.out.println("Uyarı: 'Gerçekleşme' kolonu bulunan blokta veri bulunamadı.");
        }

        // === 2) En güncel "İç Hedef"i bul ===
        String targetKey = "İç Hedef";
        Double latestTarget = null;
        long latestTargetMonth = Long.MIN_VALUE;

        for (int r = 0; r < results.length(); r++) {
            JSONObject block = results.optJSONObject(r);
            if (block == null) continue;

            JSONArray colnames = block.optJSONArray("colnames");
            if (colnames == null) continue;

            boolean hasTarget = false;
            for (int c = 0; c < colnames.length(); c++) {
                if (targetKey.equals(colnames.optString(c))) {
                    hasTarget = true;
                    break;
                }
            }
            if (!hasTarget) continue;

            JSONArray data = block.optJSONArray("data");
            if (data == null) continue;

            for (int i = 0; i < data.length(); i++) {
                JSONObject row = data.optJSONObject(i);
                if (row == null) continue;

                if (!row.has("FISCALMONTH") || !row.has(targetKey) || row.isNull(targetKey)) continue;

                long fm = (long) row.optDouble("FISCALMONTH", -1);
                if (fm > latestTargetMonth) {
                    latestTargetMonth = fm;
                    latestTarget = row.optDouble(targetKey, 0.0);
                }
            }
        }

        if (latestTarget != null) {
            currentTargetW12 = latestTarget;
            System.out.println("En güncel İç Hedef ay (FISCALMONTH): " + latestTargetMonth);
            System.out.println("currentTargetW12: " + currentTargetW12);
        } else {
            System.out.println("Uyarı: 'İç Hedef' kolonu bulunan blokta veri bulunamadı.");
        }
    }

    @Then("The user verify actuals")
    public void theUserVerifyActuals() {
        Assert.assertEquals(fValueW11, currentActualW12, 0.001);
    }

    @Given("The user get data")
    public void theUserGetData() {
        currentMonth = BrowserUtils.getCurrentFiscalP();
        System.out.println("currentMonth: " + currentMonth);
        currentYear = ConfigurationReader.getProperty("currentBudgetYear");
//        Driver.getDriver().manage().window().minimize();
    }

    double result1FromWidget13;
    double digerSatisW13;
    double premSatisW13;
    @Given("The user send widget13 request {string}")
    public void theUserSendWidget13Request(String region) throws IOException {
        JSONObject w13JsonBody = Requests.sendWidget13Request(region);

        System.out.println("w13JsonBody: " + w13JsonBody);

        JSONArray dataArray = w13JsonBody
                .getJSONArray("result")
                .getJSONObject(0)
                .getJSONArray("data");

        digerSatisW13 = 0;
        premSatisW13 = 0;

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject obj = dataArray.getJSONObject(i);
            String segment = obj.getString("URUN_KALITE_SEGMENT_ACIKLAMA");

            if ("Diğer".equals(segment)) {
                digerSatisW13 = obj.getDouble("Satış (L)");
            } else if ("Prem".equals(segment)) {
                premSatisW13 = obj.getDouble("Satış (L)");
            }
        }

        System.out.println("Diğer satış (L): " + digerSatisW13);
        System.out.println("Prem satış (L): " + premSatisW13);

        if (digerSatisW13 == 0 && premSatisW13 == 0) {
            result1FromWidget13 = 0;
            System.out.println("premSatis / (premSatis + digerSatis): " + result1FromWidget13);
            System.out.println("premSatis ve digerSatis ikisi de gelmedi");
            return;
        }

        result1FromWidget13 = premSatisW13 / (premSatisW13 + digerSatisW13);
        System.out.println("premSatis / (premSatis + digerSatis): " + result1FromWidget13);

    }

    double regionSalesRate;
    @Given("The user send widget14 request {string}")
    public void theUserSendWidget14Request(String region) throws IOException {
        JSONObject w14JsonBody = Requests.sendWidget14Request();

        System.out.println("w14JsonBody: " + w14JsonBody);

        JSONArray dataArray = w14JsonBody.getJSONArray("result")
                .getJSONObject(0)
                .getJSONArray("data");

        regionSalesRate = 0;

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject row = dataArray.getJSONObject(i);
            if (row.has("Satış Müdürlüğü") && row.getString("Satış Müdürlüğü").equalsIgnoreCase(region)) {
                regionSalesRate = row.getDouble("Prem. Satış %");
                break;
            }
        }

        System.out.println("Prem. Satış % for region: " + regionSalesRate);


    }

    @Then("The user verify scenario2")
    public void theUserVerifyScenario() {
        Assert.assertEquals(result1FromWidget13, regionSalesRate, 0.001);
    }

    double totalSales15;
    @Given("The user send widget15 request")
    public void theUserSendWidget15Request() throws IOException {
        JSONObject w15JsonBody = Requests.sendWidget15Request();

        System.out.println("w15JsonBody: " + w15JsonBody);

        totalSales15 = 0.0;

        JSONArray data = w15JsonBody.getJSONArray("result")
                .getJSONObject(0)
                .getJSONArray("data");

// Tek satırlık tablo bekleniyor; yine de güvenli döngü:
        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.getJSONObject(i);

            for (String key : row.keySet()) {
                // Etiket kolonunu atla
                if ("Ürün Kategori".equals(key)) continue;

                // null (JSONObject.NULL) veya sayı olmayanları atla
                Object val = row.get(key);
                if (val != JSONObject.NULL && val instanceof Number) {
                    totalSales15 += ((Number) val).doubleValue();
                }
            }
        }

        System.out.println("Toplam Satış (L): " + totalSales15);
    }

    @Then("The user verify scenario3")
    public void theUserVerifyScenario3() {
        Assert.assertEquals(totalSales15, totalF26W11, 0.001);
    }

    double toplamKaliteSegmentSatisLitre;
    @Given("The user send widget16 request")
    public void theUserSendWidget16Request() throws IOException {

        JSONObject w16JsonBody = Requests.sendWidget16Request();

        System.out.println("w16JsonBody: " + w16JsonBody);

        JSONArray dataArray = w16JsonBody.getJSONArray("result")
                .getJSONObject(0)
                .getJSONArray("data");

        toplamKaliteSegmentSatisLitre = 0.0;

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject item = dataArray.getJSONObject(i);
            if (!item.isNull("Satış (L)")) {
                toplamKaliteSegmentSatisLitre += item.getDouble("Satış (L)");
            }
        }

        System.out.println("Toplam Satış (L): " + toplamKaliteSegmentSatisLitre);


    }

    @Then("The user verify scenario4")
    public void theUserVerifyScenario4() {
        Assert.assertEquals(toplamKaliteSegmentSatisLitre, totalF26W11, 0.001);
    }

    double gerceklesmelerToplami;
    @Given("The user send widget17 request")
    public void theUserSendWidget17Request() throws IOException {
        JSONObject w17JsonBody = Requests.sendWidget17Request();

        System.out.println("w17JsonBody: " + w17JsonBody);

        gerceklesmelerToplami = 0.0;

// Dayanıklı: result içindeki her blokta "Gerçekleşme" kolonunu ara ve topla
        JSONArray results = w17JsonBody.getJSONArray("result");
        for (int r = 0; r < results.length(); r++) {
            JSONArray data = results.getJSONObject(r).getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject row = data.getJSONObject(i);
                if (row.has("Gerçekleşme") && !row.isNull("Gerçekleşme")) {
                    gerceklesmelerToplami += row.getDouble("Gerçekleşme");
                }
            }
        }

        System.out.println("Gerçekleşme toplamı: " + gerceklesmelerToplami);

    }

    @Then("The user verify scenario5")
    public void theUserVerifyScenario5() {
        Assert.assertEquals(gerceklesmelerToplami, currentActualW12, 0.001);
    }

    double totalSatis18;
    @Given("The user send widget18 request")
    public void theUserSendWidget18Request() throws IOException {
        JSONObject w18JsonBody = Requests.sendWidget18Request();

        System.out.println("w18JsonBody: " + w18JsonBody);


        totalSatis18 = 0.0;

        JSONArray dataArray = w18JsonBody
                .getJSONArray("result")
                .getJSONObject(0)
                .getJSONArray("data");

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject row = dataArray.getJSONObject(i);
            if (!row.isNull("Satış (L)")) {
                totalSatis18 += row.getDouble("Satış (L)");
            }
        }

        System.out.println("Toplam Satış (L) - 18: " + totalSatis18);



    }

    @Then("The user verify scenario6")
    public void theUserVerifyScenario6() {
        Assert.assertEquals(totalSatis18, gerceklesmelerToplami, 0.001);
    }


    double totalSatis19;
    @Given("The user send widget19 request")
    public void theUserSendWidget19Request() throws IOException {
        JSONObject w19JsonBody = Requests.sendWidget19Request();

        System.out.println("w19JsonBody: " + w19JsonBody);

        JSONArray results = w19JsonBody.getJSONArray("result");
        JSONObject first = results.getJSONObject(0);
        JSONArray data = first.getJSONArray("data");

        totalSatis19 = 0.0;
        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.getJSONObject(i);
            if (!row.isNull("Satış (L)")) {
                totalSatis19 += row.getDouble("Satış (L)");
            }
        }
        System.out.println("Toplam Satış (L): - 19: " + totalSatis19);

    }

    @Then("The user verify scenario7")
    public void theUserVerifyScenario7() {
        Assert.assertEquals(totalSatis19, gerceklesmelerToplami, 0.001);
    }

    double currentMonthCategoriesSum20;
    @Given("The user send widget20 request")
    public void theUserSendWidget20Request() throws IOException {
        JSONObject w20JsonBody = Requests.sendWidget20Request();

        System.out.println("w20JsonBody: " + w20JsonBody);

        currentMonthCategoriesSum20 = Requests.sumCurrentMonthCategories(w20JsonBody);

        System.out.println("currentMonthCategoriesSum20: " + currentMonthCategoriesSum20);

    }

    @Then("The user verify scenario8")
    public void theUserVerifyScenario8() {
        Assert.assertEquals(currentMonthCategoriesSum20, currentMonthCategoriesSumW21, 0.001);
    }

    double currentMonthCategoriesSumW21;
    @Given("The user send widget21 request")
    public void theUserSendWidgetRequest() throws IOException {
        JSONObject w21JsonBody = Requests.sendWidget21Request();

        System.out.println("w21JsonBody: " + w21JsonBody);

        currentMonthCategoriesSumW21 = Requests.sumCurrentMonthCategories(w21JsonBody);
        System.out.println("Bu ayın (widget21) toplamı: " + currentMonthCategoriesSumW21);

    }

    @Then("The user verify scenario9")
    public void theUserVerifyScenario9() {
        Assert.assertEquals(gerceklesmeFromW23, totalSatis19, 0.001);
    }

    double gerceklesmeFromW23;
    double icHedefToplamW23;
    @Given("The user send widget23 request")
    public void theUserSendWidget23Request() throws IOException {
        JSONObject w23JsonBody = Requests.sendWidget23Request();

        System.out.println("w23JsonBody: " + w23JsonBody);

        gerceklesmeFromW23 = w23JsonBody.getJSONArray("result")
                .getJSONObject(1)
                .getJSONArray("data")
                .toList()
                .stream()
                .mapToDouble(obj -> ((Map<?, ?>) obj).get("Gerçekleşme") instanceof Number ? ((Number) ((Map<?, ?>) obj).get("Gerçekleşme")).doubleValue() : 0.0)
                .sum();


        icHedefToplamW23 = w23JsonBody.getJSONArray("result")
                .getJSONObject(0) // 0. blok: İç Hedef
                .getJSONArray("data")
                .toList()
                .stream()
                .mapToDouble(obj -> ((Map<?, ?>) obj).get("İç Hedef") instanceof Number
                        ? ((Number) ((Map<?, ?>) obj).get("İç Hedef")).doubleValue()
                        : 0.0)
                .sum();

        System.out.println("gerceklesmeFromW23: " + gerceklesmeFromW23);
        System.out.println("içHedefToplamıFromW23: " + icHedefToplamW23);
    }

    @Then("The user verify scenario10")
    public void theUserVerifyScenario10() {
        Assert.assertEquals(myColumnW3AggreationS10, ayTrS10Query);
    }


    double toplamGerceklesmeW24;
    double toplamIcHedefW24;
    @Given("The user send widget24 request")
    public void theUserSendWidget24Request() throws IOException {
        JSONObject w24JsonBody = Requests.sendWidget24Request();
        System.out.println("w24JsonBody: " + w24JsonBody);

        toplamIcHedefW24       = sumMetricFromResults(w24JsonBody, "İç Hedef");
        toplamGerceklesmeW24   = sumMetricFromResults(w24JsonBody, "Gerçekleşme");

        System.out.println("İç Hedef ToplamıW24: " + toplamIcHedefW24);
        System.out.println("Gerçekleşme ToplamıW24: " + toplamGerceklesmeW24);


    }

    public static double sumMetricFromResults(JSONObject body, String metricName) {
        if (body == null) return 0.0;
        JSONArray results = body.optJSONArray("result");
        if (results == null) return 0.0;

        for (int r = 0; r < results.length(); r++) {
            JSONObject block = results.optJSONObject(r);
            if (block == null) continue;

            // 1) Bu blokta istenen metrik kolonu var mı? (colnames üzerinden kontrol)
            boolean hasMetric = false;
            JSONArray colnames = block.optJSONArray("colnames");
            if (colnames != null) {
                for (int i = 0; i < colnames.length(); i++) {
                    String col = colnames.optString(i, null);
                    if (metricName.equals(col)) {
                        hasMetric = true;
                        break;
                    }
                }
            }

            // 2) colnames yoksa ya da metrik görünmüyorsa, verinin içinde anahtar var mı diye bak (pivot/düşme durumları için)
            JSONArray data = block.optJSONArray("data");
            if (!hasMetric && data != null && data.length() > 0) {
                JSONObject firstRow = data.optJSONObject(0);
                if (firstRow != null && firstRow.has(metricName)) {
                    hasMetric = true;
                }
            }

            if (!hasMetric || data == null) continue;

            // 3) Varsa güvenli şekilde topla
            double total = 0.0;
            for (int i = 0; i < data.length(); i++) {
                JSONObject row = data.optJSONObject(i);
                if (row == null) continue;
                if (row.has(metricName) && !row.isNull(metricName)) {
                    total += row.optDouble(metricName, 0.0);
                }
            }

            // İstenen metrik bu blokta bulundu ve toplandı → dönebiliriz
            return total;
        }

        // Hiçbir blokta metrik bulunamadıysa 0 döner
        return 0.0;
    }

    @Then("The user verify scenario11")
    public void theUserVerifyScenario11() {
        Assert.assertEquals("gerceklesme farklı",toplamGerceklesmeW24, gerceklesmeFromW23, 0.001);
        Assert.assertEquals("ic hedef farklı",toplamIcHedefW24, icHedefToplamW23, 0.001);
    }

    @Then("The user verify scenario12")
    public void theUserVerifyScenario12() {
        Assert.assertEquals("gerceklesme farklı",toplamGerceklesmeW24, currentActualW12, 0.001);
        Assert.assertEquals("ic hedef farklı",toplamIcHedefW24, currentTargetW12, 0.001);
    }

    double icHedefW25;
    double satisLW25;

    @Given("The user send widget25 request")
    public void theUserSendWidget25Request() throws IOException {
        JSONObject w25JsonBody = Requests.sendWidget25Request();

        System.out.println("w25JsonBody: " + w25JsonBody);

        icHedefW25 = 0.0;
        satisLW25 = 0.0;

        JSONArray results = w25JsonBody.optJSONArray("result");
        if (results != null && results.length() > 0) {
            JSONObject block0 = results.optJSONObject(0);
            if (block0 != null) {
                JSONArray data = block0.optJSONArray("data");
                if (data != null && data.length() > 0) {
                    JSONObject row0 = data.optJSONObject(0);

                    // === İç Hedef Key'i bul ===
                    String icHedefKey = null;
                    JSONArray colnames = block0.optJSONArray("colnames");
                    if (colnames != null) {
                        for (int i = 0; i < colnames.length(); i++) {
                            String name = colnames.optString(i, "");
                            if ("İç Hedef".equals(name)) {
                                icHedefKey = name;
                                break;
                            }
                        }
                    }
                    if (icHedefKey == null && row0 != null) {
                        for (Iterator<String> it = row0.keys(); it.hasNext(); ) {
                            String k = it.next();
                            if (k.toLowerCase(Locale.ROOT).contains("hedef")) {
                                icHedefKey = k;
                                break;
                            }
                        }
                    }

                    // === Satış (L) Key'i bul ===
                    String satisLKey = null;
                    if (colnames != null) {
                        for (int i = 0; i < colnames.length(); i++) {
                            String name = colnames.optString(i, "");
                            if ("Satış (L)".equals(name)) {
                                satisLKey = name;
                                break;
                            }
                        }
                    }
                    if (satisLKey == null && row0 != null) {
                        for (Iterator<String> it = row0.keys(); it.hasNext(); ) {
                            String k = it.next();
                            if (k.toLowerCase(Locale.ROOT).contains("satış")) {
                                satisLKey = k;
                                break;
                            }
                        }
                    }

                    // === Değerleri ata ===
                    if (row0 != null) {
                        if (icHedefKey != null) {
                            icHedefW25 = row0.optDouble(icHedefKey, 0.0);
                        }
                        if (satisLKey != null) {
                            satisLW25 = row0.optDouble(satisLKey, 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("icHedefW25: " + icHedefW25);
        System.out.println("satisLW25: " + satisLW25);
    }


    double gunlukGerekliToplamW47;
    @Given("The user send widget47 request")
    public void theUserSendWidget47Request() {
        JSONObject w47JsonBody = Requests.sendWidget47Request();

        System.out.println("w47JsonBody: " + w47JsonBody);

        double gunlukGerekli = 0.0;

        assert w47JsonBody != null;
        JSONArray results = w47JsonBody.optJSONArray("result");

        gunlukGerekliToplamW47 = 0.0;
        if (results != null && results.length() > 0) {
            JSONObject block = results.optJSONObject(0);
            if (block != null) {
                String key = block.optJSONArray("colnames").optString(0, null);
                JSONArray data = block.optJSONArray("data");
                if (key != null && data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject row = data.optJSONObject(i);
                        if (row != null && row.has(key) && !row.isNull(key)) {
                            gunlukGerekliToplamW47 += row.optDouble(key, 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("gunlukGerekliToplamW47: " + gunlukGerekliToplamW47);


    }

    @Then("The user verify scenario13")
    public void theUserVerifyScenario13() {
        Assert.assertEquals("ic hedef farklı",icHedefW25, gunlukGerekliToplamW47, 0.001);
    }

    double grandTotalW101;
    @Given("The user send widget101 request")
    public void theUserSendWidget101Request() throws IOException {
        JSONObject w101JsonBody = Requests.sendWidget101Request();

        System.out.println("w101JsonBody: " + w101JsonBody);

        // w101JsonBody: JSONObject (Requests.sendWidget101Request() sonucu)
        JSONObject first = w101JsonBody.getJSONArray("result").getJSONObject(0);

// Değer kolonlarını çıkar (SM hariç tüm colnames)
        JSONArray colnames = first.getJSONArray("colnames");
        List<String> valueCols = new ArrayList<>();
        for (int i = 0; i < colnames.length(); i++) {
            String name = colnames.getString(i);
            if (!"SM".equals(name)) {
                valueCols.add(name);
            }
        }

// Tüm bölgelerdeki (satırlardaki) tüm değerleri topla
        JSONArray data = first.getJSONArray("data");
        grandTotalW101 = 0.0;

        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.getJSONObject(i);
            for (String col : valueCols) {
                if (row.has(col) && !row.isNull(col)) {
                    Object v = row.get(col);
                    if (v instanceof Number) {
                        grandTotalW101 += ((Number) v).doubleValue();
                    } else {
                        // nadiren string gelirse
                        try {
                            grandTotalW101 += Double.parseDouble(String.valueOf(v));
                        } catch (NumberFormatException ignore) { /* görmezden gel */ }
                    }
                }
            }
        }

        System.out.println("Tüm bölgelerdeki tüm değerlerin toplamı: " + grandTotalW101);




    }

    @Then("The user verify scenario14")
    public void theUserVerifyScenario14() {
        Assert.assertEquals("senaryo 14 değerler farklı",satisLW25, grandTotalW101, 0.001);
    }

    int zBaslananRotaSayisiW26;
    int kalanRotaSayisiW26;

    @Given("The user send widget26 request")
    public void theUserSendWidget26Request() {
        JSONObject w26JsonBody = Requests.sendWidget26Request();

        System.out.println("w26JsonBody: " + w26JsonBody);

        zBaslananRotaSayisiW26 = 0;
        kalanRotaSayisiW26 = 0;

        assert w26JsonBody != null;
        JSONArray results = w26JsonBody.optJSONArray("result");
        if (results != null && results.length() > 0) {
            JSONObject block0 = results.optJSONObject(0);
            if (block0 != null) {
                JSONArray data = block0.optJSONArray("data");
                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject row = data.optJSONObject(i);
                        if (row == null) continue;

                        String dimension = row.optString("dimension");
                        int rotaSayisi = row.optInt("Rota Sayısı", 0);

                        if ("Z. Başlanan Rota".equals(dimension)) {
                            zBaslananRotaSayisiW26 = rotaSayisi;
                        } else if ("Kalan Rota".equals(dimension)) {
                            kalanRotaSayisiW26 = rotaSayisi;
                        }
                    }
                }
            }
        }

        System.out.println("Z. Başlanan Rota Sayısı w26: " + zBaslananRotaSayisiW26);
        System.out.println("Kalan Rota Sayısı w26: " + kalanRotaSayisiW26);
        System.out.println("Toplam Rota w26: "  + (zBaslananRotaSayisiW26 + kalanRotaSayisiW26));
    }

    double toplamZiyaretBaslananRotaW27;
    @Given("The user send widget27 request")
    public void theUserSendWidget27Request() {
        JSONObject w27JsonBody = Requests.sendWidget27Request();

        System.out.println("w27JsonBody: " + w27JsonBody);

        toplamZiyaretBaslananRotaW27 = 0.0;

        JSONArray results = w27JsonBody.optJSONArray("result");
        if (results != null && results.length() > 0) {
            JSONObject block0 = results.optJSONObject(0);
            if (block0 != null) {
                JSONArray dataArray = block0.optJSONArray("data");
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject row = dataArray.optJSONObject(i);
                        if (row != null && row.has("Ziyaret Başlanan Rota Sayısı")) {
                            toplamZiyaretBaslananRotaW27 += row.optDouble("Ziyaret Başlanan Rota Sayısı", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Toplam Ziyaret Başlanan Rota SayısıW27: " + toplamZiyaretBaslananRotaW27);

    }

    @Then("The user verify scenario16")
    public void theUserVerifyScenario16() {
        Assert.assertEquals("senaryo 16 değerler farklı",zBaslananRotaSayisiW26, toplamZiyaretBaslananRotaW27, 0.001);
    }

    int ziyaretEdilenNoktaSayisiW28;
    @Given("The user send widget28 request")
    public void theUserSendWidget28Request() {
        ziyaretEdilenNoktaSayisiW28 = 0;

        JSONObject w28JsonBody = Requests.sendWidget28Request();
        System.out.println("w28JsonBody: " + w28JsonBody);
        JSONArray resultArray = w28JsonBody != null ? w28JsonBody.optJSONArray("result") : null;
        if (resultArray != null && resultArray.length() > 0) {
            JSONObject firstResult = resultArray.optJSONObject(0);
            if (firstResult != null) {
                // Kolon adını colnames'ten yakala (fallback ile)
                String countKey = null;
                JSONArray colnames = firstResult.optJSONArray("colnames");
                if (colnames != null) {
                    for (int i = 0; i < colnames.length(); i++) {
                        String c = colnames.optString(i, "");
                        if (c.equalsIgnoreCase("Nokta Sayısı")) { countKey = c; break; }
                    }
                }
                JSONArray dataArray = firstResult.optJSONArray("data");
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject row = dataArray.optJSONObject(i);
                        if (row == null) continue;

                        // fallback: anahtarları gez, "nokta" & "sayısı" içerene ata
                        if (countKey == null) {
                            for (Iterator<String> it = row.keys(); it.hasNext();) {
                                String k = it.next();
                                String lk = k.toLowerCase(Locale.ROOT);
                                if (lk.contains("ziyaret") && lk.contains("say")) { countKey = k; break; }
                            }
                        }

                        if ("Ziyaret Edilen".equals(row.optString("Z_Status"))) {
                            ziyaretEdilenNoktaSayisiW28 = safeToInt(row.opt(countKey));
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("Ziyaret Edilen Nokta Sayısı W28: " + ziyaretEdilenNoktaSayisiW28);

    }

    private static int safeToInt(Object val) {
        if (val == null) return 0;
        if (val instanceof Number) return ((Number) val).intValue();
        String s = String.valueOf(val).trim();
        // 0E-14 gibi exponent'li stringleri double'a çevirip yuvarlayalım
        try {
            if (s.matches("[-+]?\\d+(\\.\\d+)?([eE][-+]?\\d+)?")) {
                return (int) Math.round(Double.parseDouble(s));
            }
            return Integer.parseInt(s);
        } catch (Exception ignore) { return 0; }
    }

    int basariliZiyaretSayisiW29;
    int basarisizZiyaretSayisiW29;
    int toplamZiyaretSayisiW29;

    @Given("The user send widget29 request")
    public void theUserSendWidget29Request() {
        JSONObject w29JsonBody = Requests.sendWidget29Request();

        System.out.println("w29JsonBody: " + w29JsonBody);

        basariliZiyaretSayisiW29 = 0;
        basarisizZiyaretSayisiW29 = 0;
        toplamZiyaretSayisiW29 = 0;

        JSONArray results = w29JsonBody != null ? w29JsonBody.optJSONArray("result") : null;
        if (results != null && results.length() > 0) {
            JSONObject resultObj = results.optJSONObject(0);
            if (resultObj != null) {
                JSONArray data = resultObj.optJSONArray("data");
                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject row = data.optJSONObject(i);
                        if (row == null) continue;

                        String status = row.optString("Z_Status", "").toLowerCase(Locale.ROOT);
                        int ziyaretSayisi = safeToInt(row.opt("Ziyaret Sayısı"));

                        if (status.contains("başarılı") && !status.contains("olmayan")) {
                            basariliZiyaretSayisiW29 += ziyaretSayisi;
                        } else if (status.contains("başarısız")) {
                            basarisizZiyaretSayisiW29 += ziyaretSayisi;
                        }

                        toplamZiyaretSayisiW29 += ziyaretSayisi;
                    }
                }
            }
        }

        System.out.println("Başarılı Ziyaret Sayısı (W29): " + basariliZiyaretSayisiW29);
        System.out.println("Başarısız Ziyaret Sayısı (W29): " + basarisizZiyaretSayisiW29);
        System.out.println("Toplam Ziyaret Sayısı (W29): " + toplamZiyaretSayisiW29);
    }


    @Then("The user verify scenario17")
    public void theUserVerifyScenario17() {
        Assert.assertEquals("senaryo 17 değerler farklı",ziyaretEdilenNoktaSayisiW28, toplamZiyaretSayisiW29, 0.001);
    }

    int yerindeNoktaSayisiW30 = 0;
    int yerindeDegilNoktaSayisiW30 = 0;
    int toplamNoktaSayisiW30 = 0;
    @Given("The user send widget30 request")
    public void theUserSendWidget30Request() {
        JSONObject w30JsonBody = Requests.sendWidget30Request();

        System.out.println("w30JsonBody: " + w30JsonBody);

        assert w30JsonBody != null;
        JSONArray resultArray = w30JsonBody.optJSONArray("result");
        if (resultArray != null && resultArray.length() > 0) {
            JSONObject resultObj = resultArray.optJSONObject(0);
            if (resultObj != null) {
                JSONArray data = resultObj.optJSONArray("data");
                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject row = data.optJSONObject(i);
                        if (row != null) {
                            String status = row.optString("Z_Status");
                            int count = row.optInt("Ziyaret Sayısı", 0);

                            if ("Yerinde".equals(status)) {
                                yerindeNoktaSayisiW30 = count;
                            } else if ("Yerinde Değil".equals(status)) {
                                yerindeDegilNoktaSayisiW30 = count;
                            }
                        }
                    }
                }
            }
        }
        toplamNoktaSayisiW30 = yerindeDegilNoktaSayisiW30 + yerindeNoktaSayisiW30;
        System.out.println("Yerinde Nokta Sayısı (W30): " + yerindeNoktaSayisiW30);
        System.out.println("Yerinde Değil Nokta Sayısı (W30): " + yerindeDegilNoktaSayisiW30);
        System.out.println("toplam w30: " + toplamNoktaSayisiW30);
    }

    @Then("The user verify scenario18")
    public void theUserVerifyScenario18() {
        Assert.assertEquals("senaryo 18 değerler farklı",ziyaretEdilenNoktaSayisiW28, toplamNoktaSayisiW30, 0.001);
    }

    double totalStockLitersW38;
    @Given("The user send widget38 request")
    public void theUserSendWidget38Request() {
        JSONObject w38JsonBody = Requests.sendWidget38Request();

        System.out.println("w38JsonBody: " + w38JsonBody);

        totalStockLitersW38 = 0.0;

        if (w38JsonBody != null) {
            JSONArray resultArray = w38JsonBody.optJSONArray("result");
            if (resultArray != null && resultArray.length() > 0) {
                JSONObject firstResult = resultArray.optJSONObject(0);
                if (firstResult != null) {
                    JSONArray dataArray = firstResult.optJSONArray("data");
                    if (dataArray != null && dataArray.length() > 0) {
                        JSONObject row = dataArray.optJSONObject(0);
                        if (row != null) {
                            totalStockLitersW38 = row.optDouble("SUM(Stock_Liters)", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Toplam Litre Stok (W38): " + totalStockLitersW38);
    }

    double toplamLitreStokW43 = 0.0;
    double toplamGunlukOrtalamaLitreSatisW43 = 0.0;

    int stockOutCountW43;
    Map<String, Integer> urunVeStockOutSayilariW43 = new HashMap<>();

    @Given("The user send widget43 request")
    public void theUserSendWidget43Request() {
        JSONObject w43JsonBody = Requests.sendWidget43Request();

        System.out.println("w43JsonBody: " + w43JsonBody);

        if (w43JsonBody != null) {
            JSONArray resultArray = w43JsonBody.optJSONArray("result");
            if (resultArray != null && !resultArray.isEmpty()) {
                JSONObject resultObj = resultArray.optJSONObject(0);
                if (resultObj != null) {
                    JSONArray dataArray = resultObj.optJSONArray("data");
                    if (dataArray != null && !dataArray.isEmpty()) {
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject product = dataArray.getJSONObject(i);

                            // Litre Stok Toplamı
                            if (product.has("Litre Stok") && !product.isNull("Litre Stok")) {
                                toplamLitreStokW43 += product.optDouble("Litre Stok", 0.0);
                            }

                            // Günlük Ortalama Litre Satış Toplamı
                            if (product.has("Günlük Ortalama Litre Satış") && !product.isNull("Günlük Ortalama Litre Satış")) {
                                toplamGunlukOrtalamaLitreSatisW43 += product.optDouble("Günlük Ortalama Litre Satış", 0.0);
                            }
                        }
                    }
                }
            }
        }

        urunVeStockOutSayilariW43 = databaseMethods.getUrunVeStockOutSayilariW43();
        stockOutCountW43 = databaseMethods.getStockOutSumW43();

        System.out.println("Toplam Litre Stok W43: " + toplamLitreStokW43);
        System.out.println("Toplam Günlük Ortalama Litre Satış W43: " + toplamGunlukOrtalamaLitreSatisW43);

        for (Map.Entry<String, Integer> entry : urunVeStockOutSayilariW43.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

    }


    @Then("The user verify scenario19")
    public void theUserVerifyScenario19() {
        Assert.assertEquals("senaryo 19 değerler farklı",totalStockLitersW38, toplamLitreStokW43, 0.001);
    }

    int yetersizDistributorSayisiW94;
    int yeterliDistributorSayisiW94;
    @Given("The user send widget94 request")
    public void theUserSendWidget94Request() {
        JSONObject w94JsonBody = Requests.sendWidget94Request();

        System.out.println("w94JsonBody: " + w94JsonBody);

        yetersizDistributorSayisiW94 = 0;
        yeterliDistributorSayisiW94 = 0;

        assert w94JsonBody != null;
        JSONArray resultArray = w94JsonBody.optJSONArray("result");
        if (resultArray != null && resultArray.length() > 0) {
            JSONObject resultObj = resultArray.optJSONObject(0);
            if (resultObj != null) {
                JSONArray dataArray = resultObj.optJSONArray("data");
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject row = dataArray.optJSONObject(i);
                        if (row != null && "Yetersiz".equals(row.optString("Durum"))) {
                            yetersizDistributorSayisiW94 = row.optInt("Distribütör", 0);
                            break;
                        }
                    }
                }
            }
        }

        if (resultArray != null && resultArray.length() > 0) {
            JSONObject resultObj = resultArray.optJSONObject(0);
            if (resultObj != null) {
                JSONArray dataArray = resultObj.optJSONArray("data");
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject row = dataArray.optJSONObject(i);
                        if (row != null && "Yeterli".equals(row.optString("Durum"))) {
                            yeterliDistributorSayisiW94 = row.optInt("Distribütör", 0);
                            break;
                        }
                    }
                }
            }
        }

        System.out.println("Yetersiz Distribütör Sayısı W94: " + yetersizDistributorSayisiW94);
        System.out.println("Yeterli Distribütör Sayısı W94: " + yeterliDistributorSayisiW94);

    }

    int yetersizDistributorSayisiW95;
    @Given("The user send widget95 request")
    public void theUserSendWidget95Request() {
        yetersizDistributorSayisiW95 = 0;
        JSONObject w95JsonBody = Requests.sendWidget95Request();

        System.out.println("w95JsonBody: " + w95JsonBody);

        assert w95JsonBody != null;
        JSONArray resultArray = w95JsonBody.optJSONArray("result");
        if (resultArray != null && resultArray.length() > 0) {
            JSONObject resultObj = resultArray.optJSONObject(0);
            if (resultObj != null) {
                JSONArray dataArray = resultObj.optJSONArray("data");
                if (dataArray != null) {
                    yetersizDistributorSayisiW95 = dataArray.length();
                }
            }
        }

        System.out.println("Yetersiz Distribütör Sayısı W95: " + yetersizDistributorSayisiW95);



    }

    @Then("The user verify scenario20")
    public void theUserVerifyScenario20() {
        Assert.assertEquals("senaryo 20 değerler farklı",yetersizDistributorSayisiW94, yetersizDistributorSayisiW95, 0.001);
    }

    int yetersizMarkaSayisiW96;
    @Given("The user send widget96 request")
    public void theUserSendWidget96Request() {
        yetersizMarkaSayisiW96 = 0;

        JSONObject w96JsonBody = Requests.sendWidget96Request();
        System.out.println("w96JsonBody: " + w96JsonBody);

        if (w96JsonBody != null) {
            JSONArray resultArray = w96JsonBody.optJSONArray("result");
            if (resultArray != null && resultArray.length() > 0) {
                JSONObject firstResult = resultArray.optJSONObject(0);
                if (firstResult != null) {
                    JSONArray dataArray = firstResult.optJSONArray("data");
                    if (dataArray != null) {
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject row = dataArray.optJSONObject(i);
                            if (row != null && "Yetersiz".equals(row.optString("Durum"))) {
                                yetersizMarkaSayisiW96 = row.optInt("Marka", 0);
                                break;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Yetersiz Marka Sayısı W96: " + yetersizMarkaSayisiW96);

    }

    int yetersizMarkaSayisiW97;
    @Given("The user send widget97 request")
    public void theUserSendWidget97Request() {
        yetersizMarkaSayisiW97 = 0;

        JSONObject w97JsonBody = Requests.sendWidget97Request();
        System.out.println("w97JsonBody: " + w97JsonBody);

        if (w97JsonBody != null) {
            JSONArray resultArray = w97JsonBody.optJSONArray("result");
            if (resultArray != null && resultArray.length() > 0) {
                JSONObject firstResult = resultArray.optJSONObject(0);
                if (firstResult != null) {
                    JSONArray dataArray = firstResult.optJSONArray("data");
                    if (dataArray != null) {
                        yetersizMarkaSayisiW97 = dataArray.length();
                    }
                }
            }
        }

        System.out.println("Yetersiz Marka Sayısı W97: " + yetersizMarkaSayisiW97);
    }

    @Then("The user verify scenario22")
    public void theUserVerifyScenario22() {
        Assert.assertEquals("senaryo 22 değerler farklı",yetersizMarkaSayisiW96, yetersizMarkaSayisiW97, 0.001);
    }

    int toplamStockOutSayisiW98;
    @Given("The user send widget98 request")
    public void theUserSendWidget98Request() {
        JSONObject w98JsonBody = Requests.sendWidget98Request();
        System.out.println("w98JsonBody: " + w98JsonBody);

        toplamStockOutSayisiW98 = 0;

        if (w98JsonBody != null) {
            JSONArray results = w98JsonBody.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONArray dataArray = results.optJSONObject(0).optJSONArray("data");
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject row = dataArray.optJSONObject(i);
                        if (row != null) {
                            toplamStockOutSayisiW98 += row.optInt("Stock Out", 0);
                        }
                    }
                }
            }
        }

        System.out.println("Toplam Stock Out Sayısı w98: " + toplamStockOutSayisiW98);

    }

    @Then("The user verify scenario23")
    public void theUserVerifyScenario23() {
        Assert.assertEquals("senaryo 23 değerler farklı",toplamStockOutSayisiW98, stockOutCountW43, 0.001);
    }

    int toplamStockOutluMarkaSayisiW99;
    Map<String, Integer> urunVeStockOutSayilariW99 = new HashMap<>();
    @Given("The user send widget99 request")
    public void theUserSendWidget99Request() {

        JSONObject w99JsonBody = Requests.sendWidget99Request();
        System.out.println("w99JsonBody: " + w99JsonBody);

        urunVeStockOutSayilariW99.clear(); // önceden varsa temizle

        assert w99JsonBody != null;
        JSONArray resultArray = w99JsonBody.optJSONArray("result");
        if (resultArray != null && resultArray.length() > 0) {
            JSONObject firstResult = resultArray.optJSONObject(0);
            if (firstResult != null) {
                JSONArray dataArray = firstResult.optJSONArray("data");
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject row = dataArray.optJSONObject(i);
                        if (row != null) {
                            String urunAdi = row.optString("URUN_TIP_ACIKLAMA", "").trim();
                            int stockOutSayisi = row.optInt("num_distributors_with_so", 0);
                            urunVeStockOutSayilariW99.put(urunAdi, stockOutSayisi);
                        }
                    }
                }
            }
        }

        System.out.println("Ürünlere göre stok out sayıları:");
        for (Map.Entry<String, Integer> entry : urunVeStockOutSayilariW99.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

    }

    public static boolean compareStockOutMaps(Map<String, Integer> map1, Map<String, Integer> map2) {
        // Eğer boyutlar farklıysa zaten en az 1 fark var demektir
        if (map1.size() != map2.size()) {
            return false;
        }

        int diffCount = 0;

        for (Map.Entry<String, Integer> entry : map1.entrySet()) {
            String key = entry.getKey();
            Integer value1 = entry.getValue();
            Integer value2 = map2.get(key);

            // null kontrolü (sağlamlaştırma)
            if (value2 == null || !value1.equals(value2)) {
                diffCount++;
                System.out.println(value1  + " ve " + value2 + " farklı");
                // 2 fark olunca direkt false dönebilir
                if (diffCount >= 2) {
                    return false;
                }
            }
        }

        return true; // 0 veya 1 fark varsa
    }

    @Then("The user verify scenario24")
    public void theUserVerifyScenario24() {
        Assert.assertTrue("senaryo 24 değerler farklı",
                compareStockOutMaps(urunVeStockOutSayilariW43,urunVeStockOutSayilariW99));
    }

    double oranYuzdeW31;
    @Given("The user send widget31 request")
    public void theUserSendWidget31Request() {
        JSONObject w31JsonBody = Requests.sendWidget31Request();
        System.out.println("w31JsonBody: " + w31JsonBody);

        oranYuzdeW31 = 0.0;

        if (w31JsonBody != null) {
            JSONArray resultArr = w31JsonBody.optJSONArray("result");
            if (resultArr != null && resultArr.length() > 0) {
                JSONObject firstResult = resultArr.optJSONObject(0);
                if (firstResult != null) {
                    JSONArray data = firstResult.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        JSONObject row = data.optJSONObject(0);
                        if (row != null) {
                            oranYuzdeW31 = row.optDouble("Oran %", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Widget31 Oran %: " + oranYuzdeW31); // ör: 0.9145867999082702
    }

    double icHedefGuncelW12;
    double gerceklesmeGuncelW12;
    @Given("The user send widget12Aggreation request")
    public void theUserSendWidget12AggreationRequest() {

        JSONObject w12JsonBody = Requests.sendWidget12AggregationRequest();
        System.out.println("w12JsonBody: " + w12JsonBody);

        icHedefGuncelW12 = 0d;
        gerceklesmeGuncelW12 = 0d;

        if (w12JsonBody == null) return;

        // Güncel ayın (UTC) ilk günü 00:00 epoch-ms
        long currentMonthStartMs = java.time.LocalDate.now(java.time.ZoneOffset.UTC)
                .withDayOfMonth(1)
                .atStartOfDay(java.time.ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();

        JSONArray results = w12JsonBody.optJSONArray("result");
        if (results == null || results.length() < 2) return;

        // result[0] -> İç Hedef
        JSONObject icHedefObj = results.optJSONObject(0);
        if (icHedefObj != null) {
            JSONArray data = icHedefObj.optJSONArray("data");
            icHedefGuncelW12 = pickValueForMonth(data, "İç Hedef", currentMonthStartMs);
        }

        // result[1] -> Gerçekleşme
        JSONObject gercekObj = results.optJSONObject(1);
        if (gercekObj != null) {
            JSONArray data = gercekObj.optJSONArray("data");
            gerceklesmeGuncelW12 = pickValueForMonth(data, "Gerçekleşme", currentMonthStartMs);
        }

        System.out.println("Güncel Ay İç Hedef (W12): " + icHedefGuncelW12);
        System.out.println("Güncel Ay Gerçekleşme (W12): " + gerceklesmeGuncelW12);

    }

    private double pickValueForMonth(JSONArray data, String valueKey, long currentMonthStartMs) {
        if (data == null || data.length() == 0) return 0d;

        // 1) Tam eşleşme dene
        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.optJSONObject(i);
            if (row == null) continue;
            long fm = (long) Math.floor(row.optDouble("FISCALMONTH", -1));
            if (fm == currentMonthStartMs) {
                return row.optDouble(valueKey, 0d);
            }
        }

        // 2) Tam eşleşme yoksa en güncel (max FISCALMONTH) satırını al
        JSONObject latest = null;
        long maxFm = Long.MIN_VALUE;
        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.optJSONObject(i);
            if (row == null) continue;
            long fm = (long) Math.floor(row.optDouble("FISCALMONTH", -1));
            if (fm > maxFm) {
                maxFm = fm;
                latest = row;
            }
        }
        return latest != null ? latest.optDouble(valueKey, 0d) : 0d;
    }

    @Then("The user verify scenario33")
    public void theUserVerifyScenario33() {
        double w12AggrResult;
        if (gerceklesmeGuncelW12 == 0 || icHedefGuncelW12 == 0) {
            w12AggrResult = 0;
            Assert.assertEquals("senaryo 33 değerler farklı",oranYuzdeW31, w12AggrResult, 0.001);
            return;
        }

        w12AggrResult = gerceklesmeGuncelW12 / icHedefGuncelW12;

        Assert.assertEquals("senaryo 33 değerler farklı",oranYuzdeW31, w12AggrResult, 0.001);
    }

    double toplamSatisLW100;
    @Given("The user send widget100 request")
    public void theUserSendWidget100Request() {
        JSONObject w100JsonBody = Requests.sendWidget31Request();
        System.out.println("w100JsonBody: " + w100JsonBody);

// "Satış (L)" toplamını hesapla
        toplamSatisLW100 = 0.0;

        JSONArray results = w100JsonBody.optJSONArray("result");
        if (results != null) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject res = results.optJSONObject(i);
                if (res == null) continue;

                // İlgili metrik kolon adını bul (varsayılan: "Satış (L)")
                String metricKey = "Satış (L)";
                JSONArray colnames = res.optJSONArray("colnames");
                if (colnames != null) {
                    for (int c = 0; c < colnames.length(); c++) {
                        String cn = colnames.optString(c);
                        if ("Satış (L)".equals(cn) || cn.toLowerCase().contains("satış")) {
                            metricKey = cn;
                            break;
                        }
                    }
                }

                // Data satırlarından topla
                JSONArray data = res.optJSONArray("data");
                if (data == null) continue;
                for (int j = 0; j < data.length(); j++) {
                    JSONObject row = data.optJSONObject(j);
                    if (row == null) continue;
                    toplamSatisLW100 += row.optDouble(metricKey, 0.0);
                }
            }
        }

// İstersen yazdır
        System.out.println("Satış (L) toplamı: " + toplamSatisLW100);
    }

    double satisLW25Aggreation;
    @Given("The user send widget25Aggreation request")
    public void theUserSendWidget25AggreationRequest() {
        JSONObject widget25Aggreation = Requests.sendWidget12AggregationRequest();
        System.out.println("widget25Aggreation: " + widget25Aggreation);

        satisLW25Aggreation = 0.0;

        JSONArray results = widget25Aggreation.optJSONArray("result");
        if (results != null && results.length() > 0) {
            JSONObject firstRes = results.optJSONObject(0);

            // Kolon adını dinamik tespit et (genelde "Satış (L)")
            String metricKey = "Satış (L)";
            JSONArray colnames = firstRes.optJSONArray("colnames");
            if (colnames != null) {
                for (int i = 0; i < colnames.length(); i++) {
                    String cn = colnames.optString(i);
                    if ("Satış (L)".equals(cn) || cn.toLowerCase().contains("satış")) {
                        metricKey = cn;
                        break;
                    }
                }
            }

            JSONArray data = firstRes.optJSONArray("data");
            if (data != null && data.length() > 0) {
                JSONObject row0 = data.optJSONObject(0);
                if (row0 != null) {
                    satisLW25Aggreation = row0.optDouble(metricKey, 0.0);
                }
            }
        }

        System.out.println("Widget25Aggreation - Satış (L): " + satisLW25Aggreation);

    }

    @Then("The user verify scenario34")
    public void theUserVerifyScenario34() {
        Assert.assertEquals("senaryo 34 değerler farklı",toplamSatisLW100, satisLW25Aggreation, 0.001);
    }

    int plannedRoutesScenario15;
    @Given("The user get Scenario15 query")
    public void theUserGetScenarioQuery() {
        plannedRoutesScenario15 = databaseMethods.getPlannedRoutesScenario15();
    }

    @Then("The user verify scenario15")
    public void theUserVerifyScenario15() {
        Assert.assertEquals("senaryo 15 değerler farklı",
                kalanRotaSayisiW26 + zBaslananRotaSayisiW26, plannedRoutesScenario15, 10);
    }

    double toplamGerceklesmeW51;
    @Given("The user send widget51 request")
    public void theUserSendWidget51Request() {
        JSONObject w51Json = Requests.sendWidget51Request();
        System.out.println("w51Json: " + w51Json);

        toplamGerceklesmeW51 = 0.0;

        JSONArray results = w51Json.optJSONArray("result");
        if (results != null && results.length() > 0) {
            JSONObject firstResult = results.optJSONObject(0);
            if (firstResult != null) {
                JSONArray data = firstResult.optJSONArray("data");
                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject row = data.optJSONObject(i);
                        if (row != null) {
                            toplamGerceklesmeW51 += row.optDouble("Gerçekleşme", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("W51 toplam Gerçekleşme: " + toplamGerceklesmeW51);
    }

    @Then("The user verify scenario40")
    public void theUserVerifyScenario40() {
        Assert.assertEquals("senaryo 40 değerler farklı",
                toplamGerceklesmeW51, premSatisW13,0.01);
    }

    String bmNameW1 = "";
    @Given("The user send widget1 request")
    public void theUserSendWidget1Request() {
        JSONObject w1Json = Requests.sendWidget1Request();

        bmNameW1 = "";

        JSONArray results = w1Json.optJSONArray("result");
        if (results != null && results.length() > 0) {
            JSONObject firstResult = results.optJSONObject(0);
            if (firstResult != null) {
                JSONArray data = firstResult.optJSONArray("data");
                if (data != null && data.length() > 0) {
                    JSONObject firstRow = data.optJSONObject(0);
                    if (firstRow != null) {
                        bmNameW1 = firstRow.optString("BM_Name", "");
                    }
                }
            }
        }

        System.out.println("BM Name: " + bmNameW1);
    }

    String bM_nameS26;
    @Given("The user get S26 query")
    public void theUserGetS26Query() {
        bM_nameS26 = databaseMethods.getBmNameS26();
    }

    @Then("The user verify scenario26")
    public void theUserVerifyScenario26() {
        Assert.assertEquals("senaryo 26 değerler farklı",
                bmNameW1, bM_nameS26);
    }

    String bmEmailW2 = "";
    @Given("The user send widget2 request")
    public void theUserSendWidget2Request() {
        JSONObject w2Json = Requests.sendWidget2Request();

        bmEmailW2 = "";

        JSONArray results = w2Json.optJSONArray("result");
        if (results != null && results.length() > 0) {
            JSONObject firstResult = results.optJSONObject(0);
            if (firstResult != null) {
                JSONArray data = firstResult.optJSONArray("data");
                if (data != null && data.length() > 0) {
                    JSONObject firstRow = data.optJSONObject(0);
                    if (firstRow != null) {
                        bmEmailW2 = firstRow.optString("Email", "");
                    }
                }
            }
        }

        System.out.println("BM Email: " + bmEmailW2);

    }

    String bmEmailS27;
    @Given("The user get S27 query")
    public void theUserGetS27Query() {
        bmEmailS27 = databaseMethods.getBmEmailS27();
    }

    @Then("The user verify scenario27")
    public void theUserVerifyScenario27() {
        Assert.assertEquals("senaryo 27 değerler farklı",
                bmEmailW2, bmEmailS27);
    }

    String myColumnValueW3;
    @Given("The user send widget3 request")
    public void theUserSendWidget3Request() {
        JSONObject w3Json = Requests.sendWidget3Request();

        myColumnValueW3 = "";

        JSONArray results = w3Json.optJSONArray("result");
        if (results != null && results.length() > 0) {
            JSONObject firstResult = results.optJSONObject(0);
            if (firstResult != null) {
                JSONArray data = firstResult.optJSONArray("data");
                if (data != null && data.length() > 0) {
                    JSONObject firstRow = data.optJSONObject(0);
                    if (firstRow != null) {
                        myColumnValueW3 = firstRow.optString("My column", "");
                    }
                }
            }
        }

        System.out.println("My Column: " + myColumnValueW3);
    }

    String ayTrS28;
    @Given("The user get S28 query")
    public void theUserGetS28Query() {
        ayTrS28 = databaseMethods.getAyTrS28();
    }

    @Then("The user verify scenario28")
    public void theUserVerifyScenario28() {
        Assert.assertEquals("senaryo 28 değerler farklı",
                myColumnValueW3, ayTrS28);
    }

    // 1) Toplam "Satış (L)"
    double toplamSatisLW9 = 0.0;

    // 2) Bugün dahil ve sonrasındaki "Satış (L)" toplamı
    double sonGunSatisLW9 = 0.0;

    @Given("The user send widget9 request")
    public void theUserSendWidget9Request() {
        JSONObject w9Json = Requests.sendWidget9Request();
        System.out.println("w9Json: " + w9Json);

        toplamSatisLW9 = 0.0;
        sonGunSatisLW9 = 0.0;

        // Bugünün epoch milis başlangıcı (00:00)
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Istanbul"));
        long todayEpochMs = today.atStartOfDay(ZoneId.of("Europe/Istanbul")).toInstant().toEpochMilli();

        Map<String, Double> gunlukSatis = new LinkedHashMap<>();

        JSONArray results = w9Json.optJSONArray("result");
        if (results != null && results.length() > 0) {
            JSONObject block0 = results.optJSONObject(0);
            if (block0 != null) {
                JSONArray data = block0.optJSONArray("data");
                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject row = data.optJSONObject(i);
                        if (row == null) continue;

                        long ts = (long) row.optDouble("TRHISLEMTARIHI", 0.0); // epoch ms
                        double val = row.optDouble("Satış (L)", 0.0);

                        toplamSatisLW9 += val;

                        // Bugün veya sonrasındaki günlerin toplamını al
                        if (ts >= todayEpochMs) {
                            sonGunSatisLW9 += val;
                        }

                        // ISO tarih string (Europe/Istanbul)
                        if (ts > 0) {
                            Instant inst = Instant.ofEpochMilli(ts);
                            String isoDate = ZonedDateTime.ofInstant(inst, ZoneId.of("Europe/Istanbul"))
                                    .toLocalDate().toString();
                            gunlukSatis.put(isoDate, val);
                        }
                    }
                }
            }
        }

        System.out.println("W9 Toplam Satış (L): " + toplamSatisLW9);
        System.out.println("W9 Bugün + Sonrası Satış (L): " + sonGunSatisLW9);
        System.out.println("W9 Günlük Satış Haritası: " + gunlukSatis);
    }


    double totalSalesS30;
    @Given("The user get S30 query")
    public void theUserGetS30Query() {
        totalSalesS30 = databaseMethods.getTotalSales30();
    }

    @Then("The user verify scenario30")
    public void theUserVerifyScenario30() {
        Assert.assertEquals("senaryo 30 değerler farklı",
                sonGunSatisLW9, totalSalesS30,0.01);
    }

    double basariliDegeriW4;
    @Given("The user send widget4 request")
    public void theUserSendWidget4Request() {
        JSONObject widget4Json = Requests.sendWidget4Request();
        System.out.println("widget4Json: " + widget4Json);

        basariliDegeriW4 = 0.0;

        if (widget4Json != null) {
            JSONArray results = widget4Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject resultObj = results.optJSONObject(0);
                if (resultObj != null) {
                    JSONArray data = resultObj.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        JSONObject row = data.optJSONObject(0);
                        if (row != null) {
                            basariliDegeriW4 = row.optDouble("Başarılı", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Başarılı oranı: " + basariliDegeriW4);
    }

    double sonAyBasariliZiyaretW8;
    @Given("The user send widget8 request")
    public void theUserSendWidget8Request() {
        JSONObject w8Json = Requests.sendWidget8Request();

        sonAyBasariliZiyaretW8 = 0.0;
        long maxMonthTs = Long.MIN_VALUE;

        if (w8Json != null) {
            JSONArray results = w8Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject block0 = results.optJSONObject(0);
                if (block0 != null) {
                    JSONArray data = block0.optJSONArray("data");
                    if (data != null) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.optJSONObject(i);
                            if (row == null) continue;

                            long monthTs = (long) row.optDouble("My column", 0.0); // epoch ms (ay başı)
                            double val = row.optDouble("Başarılı Ziyaret", 0.0);

                            if (monthTs > maxMonthTs) {
                                maxMonthTs = monthTs;
                                sonAyBasariliZiyaretW8 = val;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Son Ay Başarılı Ziyaret (oran): " + sonAyBasariliZiyaretW8);
// Yüzde formatıyla görmek istersen:
//        System.out.printf("Son Ay Başarılı Ziyaret: %.2f%%%n", sonAyBasariliZiyaret * 100.0);
    }

    @Then("The user verify scenario29")
    public void theUserVerifyScenario29() {
        Assert.assertEquals(basariliDegeriW4, sonAyBasariliZiyaretW8, 0.001);
    }

    double latestPremW10;
    @Given("The user send widget10 request")
    public void theUserSendWidget10Request() {
        JSONObject w10JsonBody = Requests.sendWidget10Request();
        System.out.println("w10JsonBody: " + w10JsonBody);

        latestPremW10 = 0.0;
        double maxTs = -Double.MAX_VALUE;

        if (w10JsonBody != null) {
            JSONArray results = w10JsonBody.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject block0 = results.optJSONObject(0);
                if (block0 != null) {
                    JSONArray data = block0.optJSONArray("data");
                    if (data != null) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.optJSONObject(i);
                            if (row == null) continue;

                            // TRHISLEMTARIHI epoch ms olabilir (Number veya String gelebilir) -> double'a çevir
                            double ts;
                            Object tsObj = row.opt("TRHISLEMTARIHI");
                            if (tsObj instanceof Number) {
                                ts = ((Number) tsObj).doubleValue();
                            } else {
                                try {
                                    ts = Double.parseDouble(String.valueOf(tsObj));
                                } catch (Exception e) {
                                    continue; // pars edilemezse bu satırı atla
                                }
                            }

                            if (ts > maxTs) {
                                maxTs = ts;
                                latestPremW10 = row.optDouble("Prem", 0.0);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Widget10 en güncel Prem: " + latestPremW10);
    }

    double totalSalesS31;
    @Given("The user get S31 query")
    public void theUserGetS31Query() {
        totalSalesS31 = databaseMethods.getTotalSalesS31();
    }

    @Then("The user verify scenario31")
    public void theUserVerifyScenario31() {
        Assert.assertEquals("senaryo 31 değerler farklı",
                latestPremW10, totalSalesS31,0.01);
    }

    double guncelMarkaToplamiW22;

    @Given("The user send widget22 request")
    public void theUserSendWidget22Request() {
        JSONObject w22JsonBody = Requests.sendWidget22Request();
        System.out.println("w22JsonBody: " + w22JsonBody);

        guncelMarkaToplamiW22 = 0.0;

        if (w22JsonBody == null) {
            System.out.println("W22: JSON null döndü");
            return;
        }

        JSONArray results = w22JsonBody.optJSONArray("result");
        if (results == null || results.length() == 0) {
            System.out.println("W22: result boş");
            return;
        }

        JSONObject block0 = results.optJSONObject(0);
        if (block0 == null) {
            System.out.println("W22: result[0] yok");
            return;
        }

        JSONArray data = block0.optJSONArray("data");
        if (data == null || data.length() == 0) {
            System.out.println("W22: data boş");
            return;
        }

        // Europe/Istanbul’a göre içinde bulunduğumuz ayın başlangıcını ms (epoch) olarak al
        long startOfThisMonthMs = java.time.LocalDate.now(java.time.ZoneId.of("Europe/Istanbul"))
                .withDayOfMonth(1)
                .atStartOfDay(java.time.ZoneId.of("Europe/Istanbul"))
                .toInstant()
                .toEpochMilli();

        // Bu defa "en güncel satır" yerine, "bu ay ve sonrası" tüm satırları toplayacağız
        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.optJSONObject(i);
            if (row == null) continue;

            // TRHISLEMTARIHI ms cinsinden geliyor (örn: 1730419200000.0). Güvenli biçimde oku.
            double tsDouble = row.optDouble("TRHISLEMTARIHI", -1);
            long ts = (tsDouble >= 0) ? (long) tsDouble : -1L;
            if (ts < startOfThisMonthMs) {
                continue; // içinde bulunduğumuz aydan önceki satırlar elensin
            }

            // Bu satırda TRHISLEMTARIHI dışındaki tüm sayısal brand kolonlarını topla
            java.util.Iterator<String> it = row.keys();
            while (it.hasNext()) {
                String key = it.next();
                if ("TRHISLEMTARIHI".equals(key)) continue;

                // Sadece sayısal değerleri al (null/NaN -> 0 kabul)
                Object val = row.opt(key);
                if (val instanceof Number) {
                    double d = ((Number) val).doubleValue();
                    if (!Double.isNaN(d) && !Double.isInfinite(d)) {
                        guncelMarkaToplamiW22 += d;
                    }
                } else {
                    // Bazı durumlarda null/string gelebilir; yok say
                }
            }
        }

        System.out.println("Güncel (bu ay ve sonrası) marka toplamı (W22): " + guncelMarkaToplamiW22);
    }

    double rakiDegeriW15Aggregation;
    @Given("The user send widget15Aggregation request")
    public void theUserSendWidget15AggregationRequest() {
        // Widget15Aggregation sonucundan "Rakı" değerini alma
        JSONObject w15JsonBody = Requests.sendWidget15AggregationRequest();
        System.out.println("w15JsonBody: " + w15JsonBody);

        rakiDegeriW15Aggregation = 0.0;

        JSONArray resultArray = w15JsonBody.optJSONArray("result");
        if (resultArray != null && resultArray.length() > 0) {
            JSONObject resultObj = resultArray.optJSONObject(0);
            if (resultObj != null) {
                JSONArray dataArray = resultObj.optJSONArray("data");
                if (dataArray != null && dataArray.length() > 0) {
                    JSONObject firstRow = dataArray.optJSONObject(0);
                    if (firstRow != null) {
                        rakiDegeriW15Aggregation = firstRow.optDouble("Rakı", 0.0);
                    }
                }
            }
        }

        System.out.println("Widget15Aggregation → Rakı değeri: " + rakiDegeriW15Aggregation);

    }

    @Then("The user verify scenario32")
    public void theUserVerifyScenario32() {
        boolean scenario32 = Math.floor(guncelMarkaToplamiW22) >= Math.floor(rakiDegeriW15Aggregation);
        Assert.assertTrue("guncelMarkaToplamiW22, rakiDegeriW15Aggregation'dan daha küçük",
                scenario32);
    }

    double toplamZiyaretIcHedefW33;
    double gerceklesenToplamW33;
    @Given("The user send widget33 request")
    public void theUserSendWidget33Request() {
        JSONObject widget33Json = Requests.sendWidget33Request();
        System.out.println("widget33Json: " + widget33Json);

        toplamZiyaretIcHedefW33 = 0.0;

        if (widget33Json != null) {
            JSONArray results = widget33Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                // 0. sonuç: "Ziyaret İç Hedef" kırılımı
                JSONObject firstResult = results.optJSONObject(0);
                if (firstResult != null) {
                    JSONArray dataArr = firstResult.optJSONArray("data");
                    if (dataArr != null) {
                        for (int i = 0; i < dataArr.length(); i++) {
                            JSONObject row = dataArr.optJSONObject(i);
                            if (row != null) {
                                toplamZiyaretIcHedefW33 += row.optDouble("Ziyaret İç Hedef", 0.0);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Ziyaret İç Hedefler Toplamı W33: " + toplamZiyaretIcHedefW33);


        gerceklesenToplamW33 = 0.0;

        if (widget33Json != null) {
            JSONArray results = widget33Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                // Önce 2. result'tan (index 1) okumayı dene
                JSONObject second = results.length() > 1 ? results.optJSONObject(1) : null;
                JSONArray data = second != null ? second.optJSONArray("data") : null;

                // Eğer 2. result yoksa/boşsa, "Gerçekleşen Ziyaret" alanı olan result'ı bul
                if (data == null) {
                    for (int r = 0; r < results.length(); r++) {
                        JSONObject res = results.optJSONObject(r);
                        if (res == null) continue;
                        JSONArray d = res.optJSONArray("data");
                        if (d == null || d.length() == 0) continue;
                        JSONObject firstRow = d.optJSONObject(0);
                        if (firstRow != null && firstRow.has("Gerçekleşen Ziyaret")) {
                            data = d;
                            break;
                        }
                    }
                }

                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject row = data.optJSONObject(i);
                        if (row == null) continue;
                        gerceklesenToplamW33 += row.optDouble("Gerçekleşen Ziyaret", 0.0);
                    }
                }
            }
        }

        System.out.println("Gerçekleşen Ziyaret Toplamı (w33): " + gerceklesenToplamW33);

    }

    double guncelPlanlananMusteriSayisiW34;
    @Given("The user send widget34 request")
    public void theUserSendWidget34Request() {

        JSONObject widget34Json = Requests.sendWidget34Request();

        guncelPlanlananMusteriSayisiW34 = 0.0;
        long maxTarih = Long.MIN_VALUE;

        if (widget34Json != null) {
            JSONArray results = widget34Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject resultObj = results.optJSONObject(0);
                if (resultObj != null) {
                    JSONArray data = resultObj.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.optJSONObject(i);
                            if (row == null) continue;

                            // TARIH alanı double olarak gelebiliyor; epoch ms uzunluğu olarak al
                            long tarih = (long) Math.floor(row.optDouble("TARIH", Double.NaN));
                            if (tarih > maxTarih) {
                                maxTarih = tarih;
                                guncelPlanlananMusteriSayisiW34 = row.optDouble("SUM(PLANANAN_MUSTERI_SAYISI)", 0.0);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Güncel SUM(PLANANAN_MUSTERI_SAYISI): " + guncelPlanlananMusteriSayisiW34);
    }

    @Then("The user verify scenario51")
    public void theUserVerifyScenario51() {
        Assert.assertEquals("senaryo 51 değerler farklı",
                toplamZiyaretIcHedefW33, guncelPlanlananMusteriSayisiW34,0.01);
    }

    double guncelMusteriSayisiW35;
    @Given("The user send widget35 request")
    public void theUserSendWidget35Request() {
        JSONObject widget35Json = Requests.sendWidget35Request();
        System.out.println("widget35Json: " + widget35Json);

        guncelMusteriSayisiW35 = 0.0;
        long latestTsW35 = Long.MIN_VALUE;

        if (widget35Json != null) {
            JSONArray results = widget35Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject resultObj = results.optJSONObject(0);
                if (resultObj != null) {
                    JSONArray data = resultObj.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.optJSONObject(i);
                            if (row == null) continue;

                            // "TARIH" değerleri milisaniye epoch olarak double geliyor -> long'a çeviriyoruz
                            long ts = (long) row.optDouble("TARIH", -1);
                            if (ts > latestTsW35) {
                                latestTsW35 = ts;
                                guncelMusteriSayisiW35 = row.optDouble("Müşteri Sayısı", 0.0);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Güncel TARIH Müşteri Sayısı (w35): " + guncelMusteriSayisiW35);
    }

    @Then("The user verify scenario52")
    public void theUserVerifyScenario52() {
        Assert.assertEquals("senaryo 52 değerler farklı",
                gerceklesenToplamW33, guncelMusteriSayisiW35,0.01);
    }

    double ortGunlukGerceklesmeW49;
    @Given("The user send widget49 request")
    public void theUserSendWidget49Request() {
        JSONObject widget49Json = Requests.sendWidget49Request();
        System.out.println("widget49Json: " + widget49Json);
        ortGunlukGerceklesmeW49 = 0.0;

        if (widget49Json != null) {
            JSONArray results = widget49Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                for (int r = 0; r < results.length(); r++) {
                    JSONObject resultObj = results.optJSONObject(r);
                    if (resultObj == null) continue;

                    JSONArray data = resultObj.optJSONArray("data");
                    if (data == null || data.length() == 0) continue;

                    // Birden fazla satır gelirse en sondaki (genelde en güncel) değeri al
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject row = data.optJSONObject(i);
                        if (row == null) continue;
                        if (row.has("Ort. Günlük Gerçekleşme")) {
                            double val = row.optDouble("Ort. Günlük Gerçekleşme", Double.NaN);
                            if (!Double.isNaN(val)) {
                                ortGunlukGerceklesmeW49 = val;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Ort. Günlük Gerçekleşme: " + ortGunlukGerceklesmeW49);
    }

    double projectedSalesS39;
    @Given("The user get S39 query")
    public void theUserGetS39Query() {
        projectedSalesS39 = databaseMethods.getProjectedSalesS39();
    }

    @Then("The user verify scenario39")
    public void theUserVerifyScenario39() {
        System.out.println("projectedSalesS39 / icHedefAyW44: " + projectedSalesS39 / icHedefAyW44);
        Assert.assertEquals("senaryo 39 değerler farklı",
                ortGunlukGerceklesmeW49, projectedSalesS39 / icHedefAyW44,0.01);
    }

    double icHedefAyW44;
    double gerceklesmeAyW44;
    @Given("The user send widget44 request")
    public void theUserSendWidget44Request() {
        // widget44Json zaten alındı:
        JSONObject widget44Json = Requests.sendWidget44Request();
        System.out.println("widget44Json: " + widget44Json);

        icHedefAyW44 = 0.0;
        gerceklesmeAyW44 = 0.0;

// En güncel ayı yakalamak için epoch-ms (FISCALMONTH) tutacağız
        double latestFiscalMonth = -1;

        if (widget44Json != null) {
            JSONArray results = widget44Json.optJSONArray("result");
            if (results != null) {
                for (int i = 0; i < results.length(); i++) {
                    JSONObject res = results.optJSONObject(i);
                    if (res == null) continue;

                    // 1) Zaman serisi yapısı varsa (FISCALMONTH alanı olan satırlar)
                    JSONArray data = res.optJSONArray("data");
                    if (data != null && data.length() > 0) {

                        // Eğer tek satırlık toplam değer varsa (big_number_total), FISCALMONTH olmayabilir.
                        // Bu durumda doğrudan "İç Hedef" key'ini yakalayalım.
                        if (data.length() == 1 && data.optJSONObject(0) != null) {
                            JSONObject row = data.optJSONObject(0);
                            if (row.has("İç Hedef")) {
                                icHedefAyW44 = row.optDouble("İç Hedef", icHedefAyW44);
                            }
                            if (row.has("Projected_Sales")) { // bazı cevaplarda bu isimle gelebilir
                                gerceklesmeAyW44 = row.optDouble("Projected_Sales", gerceklesmeAyW44);
                            }
                        }

                        // Zaman serisi satırlarını tara (FISCALMONTH varsa en güncelini al)
                        for (int r = 0; r < data.length(); r++) {
                            JSONObject row = data.optJSONObject(r);
                            if (row == null) continue;

                            // FISCALMONTH epoch ms alanı var mı?
                            if (row.has("FISCALMONTH")) {
                                double fm = row.optDouble("FISCALMONTH", -1);
                                if (fm >= latestFiscalMonth) {
                                    latestFiscalMonth = fm;

                                    // Aynı satırda "Gerçekleşme" ve/veya "İç Hedef" olabilir:
                                    if (row.has("Gerçekleşme")) {
                                        gerceklesmeAyW44 = row.optDouble("Gerçekleşme", gerceklesmeAyW44);
                                    }
                                    if (row.has("İç Hedef")) {
                                        icHedefAyW44 = row.optDouble("İç Hedef", icHedefAyW44);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Güncel Ay Gerçekleşme W44: " + gerceklesmeAyW44);
        System.out.println("Güncel Ay İç Hedef W44: " + icHedefAyW44);

    }

    double toplamTutarW87;
    @Given("The user send widget87 request")
    public void theUserSendWidget87Request() {
        JSONObject widget87Json = Requests.sendWidget87Request();
        System.out.println("widget87Json: " + widget87Json);
        toplamTutarW87 = 0.0;

        if (widget87Json != null) {
            JSONArray results = widget87Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                for (int i = 0; i < results.length(); i++) {
                    JSONObject resultObj = results.optJSONObject(i);
                    if (resultObj == null) continue;

                    JSONArray data = resultObj.optJSONArray("data");
                    if (data == null || data.length() == 0) continue;

                    // İlk (veya tek) satırdaki "Tutar" değerini al
                    JSONObject row = data.optJSONObject(0);
                    if (row != null && row.has("Tutar")) {
                        toplamTutarW87 = row.optDouble("Tutar", 0.0);
                        break;
                    }
                }
            }
        }

        System.out.println("Toplam Açık Bakiye (Tutar) W87: " + toplamTutarW87);

    }

    double tutarlarToplamiW84 = 0.0;
    double tutarlarToplami_1_90GunW84 = 0.0;
    double tutarlarToplami_NegatifGunW84 = 0.0;          // Kalan Gün < 0
    double tutarlarToplami_PozitifVeSifirGunW84 = 0.0;   // Kalan Gün >= 0

    @Given("The user send widget84 request")
    public void theUserSendWidget84Request() {
        JSONObject widget84Json = Requests.sendWidget84Request();
        System.out.println("widget84Json: " + widget84Json);

        // reset
        tutarlarToplamiW84 = 0.0;
        tutarlarToplami_1_90GunW84 = 0.0;
        tutarlarToplami_NegatifGunW84 = 0.0;
        tutarlarToplami_PozitifVeSifirGunW84 = 0.0;

        if (widget84Json != null) {
            JSONArray results = widget84Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.optJSONObject(i);
                            if (row == null) continue;

                            double tutar = row.optDouble("Tutar", 0.0);
                            int kalanGun = row.optInt("Kalan Gün", 0);

                            // 1) Tüm tutarlar toplamı
                            tutarlarToplamiW84 += tutar;

                            // 2) Kalan Gün 1-90 (0 hariç)
                            if (kalanGun > 0 && kalanGun <= 90) {
                                tutarlarToplami_1_90GunW84 += tutar;
                            }

                            // 3) Kalan Gün < 0
                            if (kalanGun < 0) {
                                tutarlarToplami_NegatifGunW84 += tutar;
                            }

                            // 4) Kalan Gün >= 0 (0 ve pozitif tümü)
                            if (kalanGun >= 0) {
                                tutarlarToplami_PozitifVeSifirGunW84 += tutar;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Tutarlar Toplamı: " + tutarlarToplamiW84);
        System.out.println("Tutarlar Toplamı (Kalan Gün 1-90): " + tutarlarToplami_1_90GunW84);
        System.out.println("Tutarlar Toplamı (Kalan Gün < 0): " + tutarlarToplami_NegatifGunW84);
        System.out.println("Tutarlar Toplamı (Kalan Gün >= 0): " + tutarlarToplami_PozitifVeSifirGunW84);
    }

    @Then("The user verify scenario61")
    public void theUserVerifyScenario61() {
        Assert.assertEquals("senaryo 61 değerler farklı",
                toplamTutarW87, tutarlarToplamiW84,0.01);
    }

    double normalVadeliW91;
    @Given("The user send widget91 request")
    public void theUserSendWidget91Request() {
        JSONObject widget91Json = Requests.sendWidget91Request(); // API'den dönen tüm JSON
        System.out.println("widget91Json: " + widget91Json);
        normalVadeliW91 = widget91Json
                .getJSONArray("result")
                .getJSONObject(0)
                .getJSONArray("data")
                .getJSONObject(0)
                .getDouble("Normal Vadeli");

        System.out.println("Normal Vadeli W91: " + normalVadeliW91);
    }

    @Then("The user verify scenario64")
    public void theUserVerifyScenario64() {
        Assert.assertEquals("senaryo 64 değerler farklı",
                normalVadeliW91, tutarlarToplami_PozitifVeSifirGunW84,0.01);
    }

    double tutarW89;
    @Given("The user send widget89 request")
    public void theUserSendWidget89Request() {
        JSONObject widget89Json = Requests.sendWidget89Request();
        System.out.println("widget89Json: " + widget89Json);

        tutarW89 = 0.0;

        if (widget89Json != null) {
            JSONArray results = widget89Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        JSONObject row0 = data.optJSONObject(0);
                        if (row0 != null) {
                            tutarW89 = row0.optDouble("Tutar", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("W89 Tutar: " + tutarW89);
    }

    @Then("The user verify scenario62")
    public void theUserVerifyScenario62() {
        Assert.assertEquals("senaryo 64 değerler farklı",
                tutarW89, tutarlarToplami_1_90GunW84,0.01);
    }

    double vadesiGecenW90;
    @Given("The user send widget90 request")
    public void theUserSendWidget90Request() {
        JSONObject widget90Json = Requests.sendWidget90Request();
        System.out.println("widget90Json: " + widget90Json);

        vadesiGecenW90 = 0.0;

        if (widget90Json != null) {
            JSONArray results = widget90Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        JSONObject row0 = data.optJSONObject(0);
                        if (row0 != null) {
                            vadesiGecenW90 = row0.optDouble("Vadesi Geçen", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Vadesi Geçen (w90): " + vadesiGecenW90);

    }

    @Then("The user verify scenario63")
    public void theUserVerifyScenario63() {
        Assert.assertEquals("senaryo 64 değerler farklı",
                vadesiGecenW90, tutarlarToplami_NegatifGunW84,0.01);
    }

    double gerceklesmeW55;
    @Given("The user send widget55 request")
    public void theUserSendWidget55Request() {
        JSONObject widget55Json = Requests.sendWidget55Request();
        System.out.println("widget55Json: " + widget55Json);

        gerceklesmeW55 = 0.0;

        if (widget55Json != null) {
            JSONArray results = widget55Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        JSONObject row0 = data.optJSONObject(0);
                        if (row0 != null) {
                            gerceklesmeW55 = row0.optDouble("Gerçekleşme %", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Gerçekleşme % (w55): " + gerceklesmeW55);
        System.out.printf("Gerçekleşme %% (w55 - Yüzde Formatında): %.2f%%%n", gerceklesmeW55 * 100);
    }

    double gerceklesmeWidget48AggS49;
    @Given("The user send widget48AggreationS49 request")
    public void theUserSendWidget48AggreationS49Request() {
        JSONObject widget48AggS49Json = Requests.sendWidget48AggreationS49Request();
        System.out.println("widget48AggS49Json: " + widget48AggS49Json);

        gerceklesmeWidget48AggS49 = 0.0;

        if (widget48AggS49Json != null) {
            JSONArray results = widget48AggS49Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        JSONObject row0 = data.optJSONObject(0);
                        if (row0 != null) {
                            gerceklesmeWidget48AggS49 = row0.optDouble("Gerçekleşme %", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Gerçekleşme % (Widget48AggreationS49): " + gerceklesmeWidget48AggS49);
        System.out.printf("Gerçekleşme %% (yüzde): %.2f%%%n", gerceklesmeWidget48AggS49 * 100);
    }

    @Then("The user verify scenario49")
    public void theUserVerifyScenario49() {
        Assert.assertEquals("senaryo 49 değerler farklı",
                gerceklesmeW55, gerceklesmeWidget48AggS49,0.01);
    }

    double gerceklesmeW56;
    @Given("The user send widget56 request")
    public void theUserSendWidget56Request() {
        JSONObject w56Json = Requests.sendWidget56Request();
        System.out.println("widget56Json: " + w56Json);

        gerceklesmeW56 = 0.0;

        if (w56Json != null) {
            JSONArray results = w56Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        // İstersen ROTA adına göre bul:
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.optJSONObject(i);
                            if (row == null) continue;
                            String rota = row.optString("ROTA", "");
                            if ("PRESELL D. HAY-BAB ROTASI".equalsIgnoreCase(rota)) {
                                gerceklesmeW56 = row.optDouble("Gerçekleşme %", 0.0);
                                break;
                            }
                        }
                        // veya tek satır bekliyorsan ilk satır:
                        if (gerceklesmeW56 == 0.0 && data.length() > 0) {
                            gerceklesmeW56 = data.optJSONObject(0).optDouble("Gerçekleşme %", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Gerçekleşme % (w56): " + gerceklesmeW56);
        System.out.printf("Gerçekleşme %% (yüzde): %.2f%%%n", gerceklesmeW56 * 100);

    }

    double gerceklesmeWidget48AggS50;
    @Given("The user send widget48AggreationS50 request")
    public void theUserSendWidget48AggreationS50Request() {
        // Çağır, JSON'u al
        JSONObject widget48AggS50Json = Requests.sendWidget48AggreationS50Request();
        System.out.println("widget48AggS50Json: " + widget48AggS50Json);

        gerceklesmeWidget48AggS50 = 0.0;

        if (widget48AggS50Json != null) {
            JSONArray results = widget48AggS50Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        JSONObject row0 = data.optJSONObject(0);
                        if (row0 != null) {
                            // Alan adı aynen JSON'daki gibi: "Gerçekleşme %"
                            gerceklesmeWidget48AggS50 = row0.optDouble("Gerçekleşme %", 0.0);
                            // NaN güvenliği (bölü sıfır vs. durumlar için)
                            if (Double.isNaN(gerceklesmeWidget48AggS50) || Double.isInfinite(gerceklesmeWidget48AggS50)) {
                                gerceklesmeWidget48AggS50 = 0.0;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Gerçekleşme % (ham oran): " + gerceklesmeWidget48AggS50);
        System.out.printf("Gerçekleşme %% (yüzde): %.2f%%%n", gerceklesmeWidget48AggS50 * 100);

    }

    double gerceklesmeW54;
    @Given("The user send widget54 request")
    public void theUserSendWidget54Request() {
        JSONObject w54Json = Requests.sendWidget54Request();
        System.out.println("widget54Json: " + w54Json);

        gerceklesmeW54 = 0.0;

        if (w54Json != null) {
            JSONArray results = w54Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        // SM = "Trakya" satırını ara
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.optJSONObject(i);
                            if (row == null) continue;
                            String sm = row.optString("SM", "");
                            if ("Trakya".equalsIgnoreCase(sm)) {
                                gerceklesmeW54 = row.optDouble("Gerçekleşme %", 0.0);
                                break;
                            }
                        }
                        // Yedek: tek satır ise ilkini al
                        if (gerceklesmeW54 == 0.0) {
                            gerceklesmeW54 = data.optJSONObject(0).optDouble("Gerçekleşme %", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Gerçekleşme % (w54): " + gerceklesmeW54);
        System.out.printf("Gerçekleşme %% (yüzde): %.2f%%%n", gerceklesmeW54 * 100);

    }

    double gerceklesmeW48AggS48;
    @Given("The user send widget48AggreationS48 request")
    public void theUserSendWidget48AggreationS48Request() {
        JSONObject w48AggS48Json = Requests.sendWidget48AggreationS48Request();
        System.out.println("widget48AggreationS48Json: " + w48AggS48Json);

        gerceklesmeW48AggS48 = 0.0;

        if (w48AggS48Json != null) {
            JSONArray results = w48AggS48Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        // Big number total döndüğü için genelde tek satır
                        JSONObject row0 = data.optJSONObject(0);
                        if (row0 != null) {
                            gerceklesmeW48AggS48 = row0.optDouble("Gerçekleşme %", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Gerçekleşme % (w48AggreationS48): " + gerceklesmeW48AggS48);
        System.out.printf("Gerçekleşme %% (yüzde): %.2f%%%n", gerceklesmeW48AggS48 * 100);

    }

    @Then("The user verify scenario48")
    public void theUserVerifyScenario48() {
        Assert.assertEquals("senaryo 48 değerler farklı",
                gerceklesmeW54, gerceklesmeW48AggS48,0.01);
    }

    double gerceklesmeW53;
    @Given("The user send widget53 request")
    public void theUserSendWidget53Request() {
        JSONObject w53Json = Requests.sendWidget53Request();
        System.out.println("widget53Json: " + w53Json);

        gerceklesmeW53 = 0.0;

        if (w53Json != null) {
            JSONArray results = w53Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        // BM bazlı table; Marmara Bölge satırını al
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.optJSONObject(i);
                            if (row == null) continue;
                            String bm = row.optString("BM", "");
                            if ("Marmara Bölge".equalsIgnoreCase(bm)) {
                                gerceklesmeW53 = row.optDouble("Gerçekleşme %", 0.0);
                                break;
                            }
                        }
                        // tek satır gelirse ilkinden de al
                        if (gerceklesmeW53 == 0.0) {
                            gerceklesmeW53 = data.optJSONObject(0).optDouble("Gerçekleşme %", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Gerçekleşme % (W53): " + gerceklesmeW53);
        System.out.printf("Gerçekleşme %% (yüzde): %.2f%%%n", gerceklesmeW53 * 100);

    }

    double gerceklesmeW48;
    @Given("The user send widget48 request")
    public void theUserSendWidget48Request() {

        JSONObject w48Json = Requests.sendWidget48Request("Marmara");
        System.out.println("widget48Json: " + w48Json);

        gerceklesmeW48 = 0.0;

        if (w48Json != null) {
            JSONArray results = w48Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        // big_number_total tek satır döner
                        gerceklesmeW48 = data.optJSONObject(0).optDouble("Gerçekleşme %", 0.0);
                    }
                }
            }
        }

        System.out.println("Gerçekleşme % (W48): " + gerceklesmeW48);
        System.out.printf("Gerçekleşme %% (yüzde): %.2f%%%n", gerceklesmeW48 * 100);


    }

    @Then("The user verify scenario47")
    public void theUserVerifyScenario47() {
        Assert.assertEquals("senaryo 47 değerler farklı",
                gerceklesmeW53, gerceklesmeW48,0.01);
    }

    double icHedeflerToplamiW52;
    double gerceklesmelerToplamiW52;
    @Given("The user send widget52 request")
    public void theUserSendWidget52Request() {
        JSONObject w52 = Requests.sendWidget52Request();
        icHedeflerToplamiW52 = getIcHedeflerToplamiFromW52(w52);

        System.out.println("İç Hedefler Toplamı (W52): " + icHedeflerToplamiW52);

        // BigDecimal tercih edersen:
        // BigDecimal icHedefBD = getIcHedeflerToplamiDecimalFromW52(w52);
        // System.out.println("İç Hedefler Toplamı (W52) - BD: " + icHedefBD);

        gerceklesmelerToplamiW52 = getGerceklesmelerToplamiFromW52(w52);
        System.out.println("Gerçekleşmeler Toplamı (W52): " + gerceklesmelerToplamiW52);

        // BigDecimal istersen:
        // BigDecimal gerceklesmeBD = getGerceklesmelerToplamiDecimalFromW52(w52);
        // System.out.println("Gerçekleşmeler Toplamı (W52) - BD: " + gerceklesmeBD);


    }

    // double olarak Gerçekleşmeler toplamı
    public static double getGerceklesmelerToplamiFromW52(JSONObject w52Json) {
        if (w52Json == null) return 0.0;
        JSONArray results = w52Json.optJSONArray("result");
        if (results == null || results.length() == 0) return 0.0;

        JSONObject result0 = results.optJSONObject(0);
        if (result0 == null) return 0.0;

        JSONArray data = result0.optJSONArray("data");
        if (data == null) return 0.0;

        double sum = 0.0;
        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.optJSONObject(i);
            if (row == null) continue;
            // null olabilir; optDouble null’da 0.0 döner
            sum += row.optDouble("Gerçekleşme", 0.0);
        }
        return sum;
    }

    // BigDecimal versiyonu (daha güvenli)
    public static BigDecimal getGerceklesmelerToplamiDecimalFromW52(JSONObject w52Json) {
        if (w52Json == null) return BigDecimal.ZERO;
        JSONArray results = w52Json.optJSONArray("result");
        if (results == null || results.length() == 0) return BigDecimal.ZERO;

        JSONObject result0 = results.optJSONObject(0);
        if (result0 == null) return BigDecimal.ZERO;

        JSONArray data = result0.optJSONArray("data");
        if (data == null) return BigDecimal.ZERO;

        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.optJSONObject(i);
            if (row == null || row.isNull("Gerçekleşme")) continue;

            Object val = row.opt("Gerçekleşme");
            BigDecimal bd = (val instanceof Number)
                    ? new BigDecimal(((Number) val).toString())
                    : new BigDecimal(String.valueOf(val)); // sayı string geldiyse
            sum = sum.add(bd);
        }
        return sum;
    }

    // double olarak toplam
    public static double getIcHedeflerToplamiFromW52(JSONObject w52Json) {
        if (w52Json == null) return 0.0;
        JSONArray results = w52Json.optJSONArray("result");
        if (results == null || results.length() == 0) return 0.0;

        JSONObject result0 = results.optJSONObject(0);
        if (result0 == null) return 0.0;

        JSONArray data = result0.optJSONArray("data");
        if (data == null) return 0.0;

        double sum = 0.0;
        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.optJSONObject(i);
            if (row == null) continue;
            // "İç Hedef" null olabiliyor; optDouble null’da 0.0 döner
            sum += row.optDouble("İç Hedef", 0.0);
        }
        return sum;
    }

    // Finansal doğruluk için BigDecimal versiyonu (opsiyonel)
    public static BigDecimal getIcHedeflerToplamiDecimalFromW52(JSONObject w52Json) {
        if (w52Json == null) return BigDecimal.ZERO;
        JSONArray results = w52Json.optJSONArray("result");
        if (results == null || results.length() == 0) return BigDecimal.ZERO;

        JSONObject result0 = results.optJSONObject(0);
        if (result0 == null) return BigDecimal.ZERO;

        JSONArray data = result0.optJSONArray("data");
        if (data == null) return BigDecimal.ZERO;

        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.optJSONObject(i);
            if (row == null) continue;

            // null kontrolü
            if (!row.isNull("İç Hedef")) {
                // sayı double gelebilir; String'e çevirip BigDecimal oluşturmak daha güvenli
                Object val = row.opt("İç Hedef");
                BigDecimal bd = (val instanceof Number)
                        ? new BigDecimal(((Number) val).toString())
                        : new BigDecimal(String.valueOf(val));
                sum = sum.add(bd);
            }
        }
        return sum;
    }

    @Then("The user verify scenario41")
    public void theUserVerifyScenario41() {
        Assert.assertEquals("senaryo 41 değerler farklı",
                icHedeflerToplamiW52, icHedefAyW44,0.01);
    }

    double sumTotalSalesW45;
    @Given("The user send widget45 request")
    public void theUserSendWidget45Request() {
        JSONObject w45 = Requests.sendWidget45Request();
        sumTotalSalesW45 = getSumTotalSalesFromW45(w45);
        System.out.println("Widget45 SUM(Total_Sales): " + sumTotalSalesW45);
    }

    public static double getSumTotalSalesFromW45(JSONObject w45Json) {
        if (w45Json == null) return 0.0;
        JSONArray results = w45Json.optJSONArray("result");
        if (results == null || results.length() == 0) return 0.0;

        JSONObject result0 = results.optJSONObject(0);
        if (result0 == null) return 0.0;

        JSONArray data = result0.optJSONArray("data");
        if (data == null || data.length() == 0) return 0.0;

        JSONObject firstRow = data.optJSONObject(0);
        if (firstRow == null) return 0.0;

        return firstRow.optDouble("SUM(Total_Sales)", 0.0);
    }


    @Then("The user verify scenario42")
    public void theUserVerifyScenario42() {
        Assert.assertEquals("senaryo 42 değerler farklı",
                gerceklesmelerToplamiW52, sumTotalSalesW45,0.01);
    }

    public static double getGunlukOrtGercekToplamiFromW50(JSONObject w50Json) {
        if (w50Json == null) return 0.0;
        JSONArray results = w50Json.optJSONArray("result");
        if (results == null || results.length() == 0) return 0.0;
        JSONObject result0 = results.optJSONObject(0);
        if (result0 == null) return 0.0;

        JSONArray data = result0.optJSONArray("data");
        if (data == null) return 0.0;

        double sum = 0.0;
        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.optJSONObject(i);
            if (row == null) continue;
            // null olanları 0 kabul eder
            sum += row.optDouble("Günlük Ort. Gerç.", 0.0);
        }
        return sum;
    }

    public static double getTotalIcHedefW50(JSONObject jsonObject) {
        double totalIcHedef = 0.0;

        // "result" dizisini al
        JSONArray resultArray = jsonObject.getJSONArray("result");

        if (resultArray.length() > 0) {
            // İlk elemandaki "data" dizisini al
            JSONObject firstResult = resultArray.getJSONObject(0);
            JSONArray dataArray = firstResult.getJSONArray("data");

            // "data" içindeki her objenin "İç Hedef" alanını topla
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject item = dataArray.getJSONObject(i);
                if (item.has("İç Hedef") && !item.isNull("İç Hedef")) {
                    totalIcHedef += item.getDouble("İç Hedef");
                }
            }
        }

        return totalIcHedef;
    }

    public static double getTotalGerceklesme(JSONObject jsonObject) {
        double totalGerceklesme = 0.0;

        // "result" dizisini al
        JSONArray resultArray = jsonObject.getJSONArray("result");

        if (resultArray.length() > 0) {
            // İlk elemandaki "data" dizisini al
            JSONObject firstResult = resultArray.getJSONObject(0);
            JSONArray dataArray = firstResult.getJSONArray("data");

            // "data" içindeki her objenin "Gerçekleşme" alanını topla
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject item = dataArray.getJSONObject(i);
                if (item.has("Gerçekleşme") && !item.isNull("Gerçekleşme")) {
                    totalGerceklesme += item.getDouble("Gerçekleşme");
                }
            }
        }

        return totalGerceklesme;
    }

    public static void main(String[] args) {
        // Örnek kullanım
        String jsonStr = "{...}"; // Buraya senin JSON'unu string olarak koy
        JSONObject jsonObject = new JSONObject(jsonStr);

        double toplamGerceklesme = getTotalGerceklesme(jsonObject);
        System.out.println("Toplam Gerçekleşme: " + toplamGerceklesme);
    }

    public static double getTotalGunlukHedef(JSONObject jsonObject) {
        double totalGunlukHedef = 0.0;

        // "result" dizisini al
        JSONArray resultArray = jsonObject.getJSONArray("result");

        if (resultArray.length() > 0) {
            // İlk elemandaki "data" dizisini al
            JSONObject firstResult = resultArray.getJSONObject(0);
            JSONArray dataArray = firstResult.getJSONArray("data");

            // "data" içindeki her objenin "Günlük Hedef" alanını topla
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject item = dataArray.getJSONObject(i);
                if (item.has("Günlük Hedef") && !item.isNull("Günlük Hedef")) {
                    totalGunlukHedef += item.getDouble("Günlük Hedef");
                }
            }
        }

        return totalGunlukHedef;
    }




    double gunlukOrtGercekToplamW50;
    double icHedefToplamW50;
    double gerceklesmeToplamW50;
    double gunlukHedefToplamW50;
    @Given("The user send widget50 request")
    public void theUserSendWidget50Request() {
        JSONObject w50 = Requests.sendWidget50Request();
        System.out.println("w50 response: " + w50);
        gunlukOrtGercekToplamW50 = getGunlukOrtGercekToplamiFromW50(w50);
        icHedefToplamW50 = getTotalIcHedefW50(w50);
        gerceklesmeToplamW50 = getTotalGerceklesme(w50);
        gunlukHedefToplamW50 = getTotalGunlukHedef(w50);
        System.out.println("W50 Günlük Ort. Gerç. Toplamı: " + gunlukOrtGercekToplamW50);
        System.out.println("W50 IcHedef Toplamı: " + icHedefToplamW50);
        System.out.println("W50 Gerçekleşme Toplamı: " + gerceklesmeToplamW50);
        System.out.println("W50 Günlük Hedef Toplamı: " + gunlukHedefToplamW50);
    }

    public static double getOrtGunlukGerceklesmeFromW46(JSONObject resp) {
        if (resp == null) return 0.0;
        try {
            return resp.getJSONArray("result")
                    .getJSONObject(0)
                    .getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("Ort. Günlük Gerçekleşme");
        } catch (Exception e) {
            return 0.0;
        }
    }


    double ortGunlukW46;
    @Given("The user send widget46 request")
    public void theUserSendWidget46Request() {
        JSONObject w46 = Requests.sendWidget46Request();
        ortGunlukW46 = getOrtGunlukGerceklesmeFromW46(w46);
        System.out.println("W46 Ort. Günlük Gerçekleşme = " + ortGunlukW46);
    }

    @Then("The user verify scenario45")
    public void theUserVerifyScenario45() {
        Assert.assertEquals("senaryo 42 değerler farklı",
                gunlukOrtGercekToplamW50, ortGunlukW46,0.01);
    }

    @Then("The user verify scenario50")
    public void theUserVerifyScenario50() {
        Assert.assertEquals("senaryo 42 değerler farklı",
                gerceklesmeW56, gerceklesmeWidget48AggS50,0.01);
    }

    int musteriSayisiSonGunW36;
    @Given("The user send widget36 request")
    public void theUserSendWidget36Request() {
        JSONObject widget36Json = Requests.sendWidget36Request();

        musteriSayisiSonGunW36 = 0;
        long sonGunTs = Long.MIN_VALUE;

        if (widget36Json != null) {
            JSONArray results = widget36Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.optJSONObject(i);
                            if (row == null) continue;

                            // TARIH ms cinsinden double gelebiliyor → long’a güvenli çevir
                            long ts = (long) row.optDouble("TARIH", 0d);
                            if (ts > sonGunTs) {
                                sonGunTs = ts;
                                musteriSayisiSonGunW36 = row.optInt("Müşteri Sayısı", 0);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("En güncel tarih (ms): " + sonGunTs);
        System.out.println("Müşteri Sayısı (en güncel): " + musteriSayisiSonGunW36);

// İstersen tarih formatlı yazdır (İstanbul saat dilimi)
        if (sonGunTs > 0) {
            java.time.ZonedDateTime zdt = java.time.Instant.ofEpochMilli(sonGunTs)
                    .atZone(java.time.ZoneId.of("Europe/Istanbul"));
            System.out.println("En güncel tarih (TR): " + zdt.toLocalDate());
        }

    }


    int basariliZiyaretSayisiW29AggreationS53;
    @Given("The user send widget29AggreationS53 request")
    public void theUserSendWidget29AggreationS53Request() {
        JSONObject widget29AggS53Json = Requests.sendWidget29AggreationS53Request();
        System.out.println("widget29AggS53Json: " + widget29AggS53Json);

        basariliZiyaretSayisiW29AggreationS53 = 0;

        if (widget29AggS53Json != null) {
            JSONArray results = widget29AggS53Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject result0 = results.optJSONObject(0);
                if (result0 != null) {
                    JSONArray data = result0.optJSONArray("data");
                    if (data != null) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.optJSONObject(i);
                            if (row != null && "Başarılı".equalsIgnoreCase(row.optString("Z_Status"))) {
                                basariliZiyaretSayisiW29AggreationS53 = row.optInt("Ziyaret Sayısı", 0);
                                break;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Başarılı Ziyaret Sayısı: " + basariliZiyaretSayisiW29AggreationS53);

    }

    @Then("The user verify scenario53")
    public void theUserVerifyScenario53() {
        Assert.assertEquals("senaryo 42 değerler farklı",
                musteriSayisiSonGunW36, basariliZiyaretSayisiW29AggreationS53,0.01);
    }

    int countDistinctDistCodeS21;
    @Given("The user get S21 query")
    public void theUserGetS21Query() {
        countDistinctDistCodeS21 = databaseMethods.getCountDistinctDistCodeS21();
    }

    @Then("The user verify scenario21")
    public void theUserVerifyScenario21() {
        Assert.assertEquals("senaryo 21 değerler farklı",
                yetersizDistributorSayisiW94 + yeterliDistributorSayisiW94, countDistinctDistCodeS21,0.01);
    }

    @Then("The user verify scenario35")
    public void theUserVerifyScenario35() {
        Assert.assertEquals("senaryo 35 değerler farklı",
                icHedefAyW44, currentTargetW12,0.01);
    }

    double avg_Sales_Per_Business_Day;
    @Given("The user get S37 query")
    public void theUserGetS37Query() {
        avg_Sales_Per_Business_Day = databaseMethods.getAvgSalesPerBusinessDayS37();
    }

    @Then("The user verify scenario37")
    public void theUserVerifyScenario37() {
        Assert.assertEquals("senaryo 37 değerler farklı",
                ortGunlukW46, avg_Sales_Per_Business_Day,0.01);
    }

    @Then("The user verify scenario38")
    public void theUserVerifyScenario38() {
        Assert.assertEquals("senaryo 38 değerler farklı",
                gerceklesmeW48, sumTotalSalesW45 / icHedefAyW44,0.01);
    }

    @Then("The user verify scenario43")
    public void theUserVerifyScenario43() {
        Assert.assertEquals("senaryo 43 değerler farklı",
                icHedefToplamW50, icHedefAyW44,0.01);
    }

    @Then("The user verify scenario44")
    public void theUserVerifyScenario44() {
        Assert.assertEquals("senaryo 43 değerler farklı",
                gerceklesmeToplamW50, sumTotalSalesW45,0.01);
    }

    @Then("The user verify scenario46")
    public void theUserVerifyScenario46() {
        Assert.assertEquals("senaryo 43 değerler farklı",
                gunlukHedefToplamW50, gunlukGerekliToplamW47,0.01);
    }

    // Data bloklarını döner (result[0].data ve result[1].data)
    private static JSONArray getResultData(JSONObject resp, int blockIndex) {
        return resp.getJSONArray("result")
                .getJSONObject(blockIndex)
                .getJSONArray("data");
    }

    // Ör: "2025-06-01" -> 12345.67 gibi bir harita döndür (Ödeme Tutarı için)
    public static LinkedHashMap<String, Double> getOdemeTutarSeri(JSONObject resp) {
        LinkedHashMap<String, Double> map = new LinkedHashMap<>();
        if (resp == null) return map;
        try {
            JSONArray data = getResultData(resp, 0); // 1. query: Ödeme Tutarı
            for (int i = 0; i < data.length(); i++) {
                JSONObject row = data.getJSONObject(i);
                String date = row.optString("PayDate", null);
                double val = row.optDouble("Ödeme Tutarı", 0.0);
                if (date != null) map.put(date, val);
            }
        } catch (Exception ignored) {}
        return map;
    }

    // Gecikme Gün serisi (tarih -> değer)
    public static LinkedHashMap<String, Double> getGecikmeGunSeri(JSONObject resp) {
        LinkedHashMap<String, Double> map = new LinkedHashMap<>();
        if (resp == null) return map;
        try {
            JSONArray data = getResultData(resp, 1); // 2. query: Gecikme Gün
            for (int i = 0; i < data.length(); i++) {
                JSONObject row = data.getJSONObject(i);
                String date = row.optString("PayDate", null);
                double val = row.optDouble("Gecikme Gün", 0.0);
                if (date != null) map.put(date, val);
            }
        } catch (Exception ignored) {}
        return map;
    }

    // Son (en güncel) ayın Ödeme Tutarı
    public static double getLastOdemeTutari(JSONObject resp) {
        LinkedHashMap<String, Double> series = getOdemeTutarSeri(resp);
        double last = 0.0;
        for (Double v : series.values()) last = v; // insertion-order LinkedHashMap: son değer
        return last;
    }

    // Son (en güncel) ayın Gecikme Gün değeri
    public static double getLastGecikmeGun(JSONObject resp) {
        LinkedHashMap<String, Double> series = getGecikmeGunSeri(resp);
        double last = 0.0;
        for (Double v : series.values()) last = v;
        return last;
    }

    // 6 aylık toplam Ödeme Tutarı
    public static double getTotalOdemeTutari(JSONObject resp) {
        LinkedHashMap<String, Double> series = getOdemeTutarSeri(resp);
        double sum = 0.0;
        for (double v : series.values()) sum += v;
        return sum;
    }


    double odemeTutariToplamW83;
    double lastGecikmeGunW83;
    @Given("The user send widget83 request")
    public void theUserSendWidget83Request() throws Exception {
        JSONObject w83JsonObjectResponse = Requests.sendWidget83Request();
        System.out.println("w83JsonObjectResponse: " + w83JsonObjectResponse);

        odemeTutariToplamW83 = getTotalOdemeTutari(w83JsonObjectResponse);
        lastGecikmeGunW83 = getLastGecikmeGun(w83JsonObjectResponse);

        System.out.println("odemeTutariToplamW83: " + odemeTutariToplamW83);
        System.out.println("lastGecikmeGunW83: " + lastGecikmeGunW83);



    }

    double SixMonthOdemeTutariW93;
    @Given("The user send widget93 request")
    public void theUserSendWidget93Request() {
        JSONObject w93response = Requests.sendWidget93Request();
        System.out.println("w93response: " + w93response);

        if (w93response == null) SixMonthOdemeTutariW93 = 0.0;
        try {
            SixMonthOdemeTutariW93 = w93response.getJSONArray("result")
                    .getJSONObject(0)
                    .getJSONArray("data")
                    .getJSONObject(0)
                    .optDouble("6 Aylık Ödeme Tutarı", 0.0);
        } catch (Exception e) {
            SixMonthOdemeTutariW93 = 0.0;
        }

        System.out.println("SixMonthOdemeTutariW93: " + SixMonthOdemeTutariW93);

    }

    @Then("The user verify scenario65")
    public void theUserVerifyScenario65() {
        Assert.assertEquals("senaryo 65 değerler farklı",
                odemeTutariToplamW83, SixMonthOdemeTutariW93,0.02);
    }

    double ortGecikmeGunW92AggrS66;
    @Given("The user send widget92AggreationS66 request")
    public void theUserSendWidget92AggreationS66Request() throws IOException {
        JSONObject w92AggrS66Response = Requests.sendWidget92AggreationS66Request();
        System.out.println("w92AggrS66Response: " + w92AggrS66Response);

        if (w92AggrS66Response == null) ortGecikmeGunW92AggrS66 = 0.0;
        try {
            ortGecikmeGunW92AggrS66 = w92AggrS66Response.getJSONArray("result")
                    .getJSONObject(0)
                    .getJSONArray("data")
                    .getJSONObject(0)
                    .optDouble("Ort. Gecikme Gün", 0.0);
        } catch (Exception e) {
            ortGecikmeGunW92AggrS66 = 0.0;
        }

        System.out.println("ortGecikmeGunW92AggrS66: " + ortGecikmeGunW92AggrS66);

    }

    @Then("The user verify scenario66")
    public void theUserVerifyScenario66() {
        Assert.assertEquals("senaryo 66 değerler farklı",
                ortGecikmeGunW92AggrS66, lastGecikmeGunW83,0.01);
    }

    // En güncel "Zamanında Ödeme %" (yoksa 0.0)
    public static double getLatestOnTimePaymentPctFromW88(JSONObject root) {
        if (root == null) return 0.0;

        JSONArray data = extractDataArray(root);
        long maxTs = Long.MIN_VALUE;
        double latestVal = 0.0;

        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.getJSONObject(i);

            // PayDate ms/sn epoch, ISO string veya PayDate__timestamp olabilir
            Long ts = extractEpochMillis(row, "PayDate");
            if (ts == null) ts = extractEpochMillis(row, "PayDate__timestamp");
            if (ts == null) continue;

            double val = row.optDouble("Zamanında Ödeme %", Double.NaN);
            if (Double.isNaN(val)) continue;

            if (ts > maxTs) {
                maxTs = ts;
                latestVal = val;
            }
        }
        return (maxTs == Long.MIN_VALUE) ? 0.0 : latestVal;
    }

    // ---- helpers ----

    private static JSONArray extractDataArray(JSONObject root) {
        // Klasik Superset yapısı: result[0].data
        if (root.has("result")) {
            JSONArray result = root.getJSONArray("result");
            if (result.length() > 0 && result.getJSONObject(0).has("data")) {
                return result.getJSONObject(0).getJSONArray("data");
            }
        }
        // Üstte ekstra katman varsa (örn. "w88Response")
        for (String key : root.keySet()) {
            Object v = root.get(key);
            if (v instanceof JSONObject) {
                JSONObject o = (JSONObject) v;
                if (o.has("result")) {
                    JSONArray result = o.getJSONArray("result");
                    if (result.length() > 0 && result.getJSONObject(0).has("data")) {
                        return result.getJSONObject(0).getJSONArray("data");
                    }
                }
            }
        }
        return new JSONArray();
    }

    private static Long extractEpochMillis(JSONObject obj, String key) {
        if (!obj.has(key) || obj.isNull(key)) return null;
        Object v = obj.get(key);

        if (v instanceof Number) {
            long t = ((Number) v).longValue();
            // saniye epoch ise ms'e çevir
            if (t < 1_000_000_000_000L) t *= 1000L;
            return t;
        } else if (v instanceof String) {
            String s = ((String) v).trim();
            if (s.isEmpty()) return null;

            // yyyy-MM-dd
            try {
                java.time.LocalDate d = java.time.LocalDate.parse(s);
                return d.atStartOfDay(java.time.ZoneOffset.UTC).toInstant().toEpochMilli();
            } catch (Exception ignore) {}

            // ISO instant
            try {
                java.time.Instant inst = java.time.Instant.parse(s);
                return inst.toEpochMilli();
            } catch (Exception ignore) {}
        }
        return null;
    }

    double latestOnTimePaymentW88;
    @Given("The user send widget88 request")
    public void theUserSendWidget88Request() {
        JSONObject w88Response = Requests.sendWidget88Request();
        System.out.println("w88Response: " + w88Response);

        latestOnTimePaymentW88 = getLatestOnTimePaymentPctFromW88(w88Response);
        System.out.println("latestOnTimePaymentW88: " + latestOnTimePaymentW88);

    }

    double zamanindaOdemeOraniCheckS67;
    @Given("The user get S67 query")
    public void theUserGetS67Query() {
        zamanindaOdemeOraniCheckS67 = databaseMethods.getZamanindaOdemeOraniCheck();
        System.out.println("zamanindaOdemeOraniCheckS67: " + zamanindaOdemeOraniCheckS67);
    }

    @Then("The user verify scenario67")
    public void theUserVerifyScenario67() {
        Assert.assertEquals("senaryo 66 değerler farklı",
                latestOnTimePaymentW88, zamanindaOdemeOraniCheckS67,0.01);
    }

    double toplamZiyaretIcHedefW37;
    double toplamGerceklesenZiyaretW37;

    @Given("The user send widget37 request")
    public void theUserSendWidget37Request() {
        JSONObject widget37Json = Requests.sendWidget37Request();
        System.out.println("widget37Json: " + widget37Json);

        toplamZiyaretIcHedefW37 = 0.0;
        toplamGerceklesenZiyaretW37 = 0.0;

        if (widget37Json != null) {
            JSONArray results = widget37Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                for (int i = 0; i < results.length(); i++) {
                    JSONObject resultObj = results.optJSONObject(i);
                    if (resultObj == null) continue;

                    JSONArray data = resultObj.optJSONArray("data");
                    if (data == null || data.length() == 0) continue;

                    for (int j = 0; j < data.length(); j++) {
                        JSONObject row = data.optJSONObject(j);
                        if (row == null) continue;

                        // Ziyaret İç Hedef toplamı
                        if (row.has("Ziyaret İç Hedef")) {
                            toplamZiyaretIcHedefW37 += row.optDouble("Ziyaret İç Hedef", 0.0);
                        }

                        // Gerçekleşen Ziyaret toplamı
                        if (row.has("Gerçekleşen Ziyaret")) {
                            toplamGerceklesenZiyaretW37 += row.optDouble("Gerçekleşen Ziyaret", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Toplam Ziyaret İç Hedef (W37): " + toplamZiyaretIcHedefW37);
        System.out.println("Toplam Gerçekleşen Ziyaret (W37): " + toplamGerceklesenZiyaretW37);

        // Oran hesaplamak istersen:
        if (toplamZiyaretIcHedefW37 > 0) {
            double gerceklesmeOrani = (toplamGerceklesenZiyaretW37 / toplamZiyaretIcHedefW37) * 100.0;
            System.out.println("Gerçekleşme Oranı (W37): %" + String.format("%.2f", gerceklesmeOrani));
        }
    }


    @Then("The user verify scenario54")
    public void theUserVerifyScenario54() {
        Assert.assertEquals("senaryo 66 değerler farklı",
                toplamGerceklesenZiyaretW37, gerceklesenToplamW33,0.01);
    }

    @Then("The user verify scenario55")
    public void theUserVerifyScenario55() {
        Assert.assertEquals("senaryo 66 değerler farklı",
                toplamZiyaretIcHedefW37, toplamZiyaretIcHedefW33,0.01);
    }

    double basariOraniW29AggreationS25;

    @Given("The user send widget29AggreationS25 request")
    public void theUserSendWidget29AggreationS25Request() {
        JSONObject response = Requests.sendWidget29AggreationS25Request();
        System.out.println("widget29AggreationS25 response: " + response);

        basariOraniW29AggreationS25 = 0.0;

        if (response != null) {
            JSONArray results = response.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject resultObj = results.optJSONObject(0);
                if (resultObj != null) {
                    JSONArray dataArr = resultObj.optJSONArray("data");
                    if (dataArr != null && dataArr.length() > 0) {
                        JSONObject dataObj = dataArr.optJSONObject(0);
                        if (dataObj != null) {
                            // Dinamik olarak kolonu yakala
                            for (String key : dataObj.keySet()) {
                                if (key.contains("SUM(BASARILI_MUSTERI_SAYISI)")) {
                                    basariOraniW29AggreationS25 = dataObj.optDouble(key, 0.0);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Başarı Oranı (W29AggreationS25): %" + String.format("%.2f", basariOraniW29AggreationS25));
    }

    @Then("The user verify scenario25")
    public void theUserVerifyScenario25() {
        System.out.println("(double) basariliZiyaretSayisiW29 /(double) toplamZiyaretSayisiW29: " + (double) basariliZiyaretSayisiW29 /(double) toplamZiyaretSayisiW29);
        Assert.assertEquals("senaryo 25 değerler farklı",
                (double) basariliZiyaretSayisiW29 /(double) toplamZiyaretSayisiW29, basariOraniW29AggreationS25,0.01);
    }

    @Then("The user verify scenario36")
    public void theUserVerifyScenario36() {
        Assert.assertEquals("senaryo 36 değerler farklı",
                sumTotalSalesW45, currentActualW12,0.01);
    }

    // Fields (step class'ta)
    Map<String, Double> kalanStokGunByUrunW40 = new LinkedHashMap<>(); // sıralamayı korur (top N gibi)
    double minKalanStokGunW40 = Double.POSITIVE_INFINITY;
    double maxKalanStokGunW40 = Double.NEGATIVE_INFINITY;
    double toplamKalanStokGunW40 = 0.0;
    int   satirSayisiW40 = 0;

    @Given("The user send widget40 request")
    public void theUserSendWidget40Request() {
        JSONObject widget40Json = Requests.sendWidget40Request();
        System.out.println("widget40Json: " + widget40Json);

        kalanStokGunByUrunW40.clear();
        minKalanStokGunW40 = Double.POSITIVE_INFINITY;
        maxKalanStokGunW40 = Double.NEGATIVE_INFINITY;
        toplamKalanStokGunW40 = 0.0;
        satirSayisiW40 = 0;

        if (widget40Json != null) {
            JSONArray results = widget40Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                for (int i = 0; i < results.length(); i++) {
                    JSONObject resultObj = results.optJSONObject(i);
                    if (resultObj == null) continue;

                    JSONArray data = resultObj.optJSONArray("data");
                    if (data == null || data.length() == 0) continue;

                    for (int j = 0; j < data.length(); j++) {
                        JSONObject row = data.optJSONObject(j);
                        if (row == null) continue;

                        String urun = row.optString("Ürün", null);
                        if (urun == null || urun.isEmpty()) continue;

                        // "Kalan Stok Gün" sayısal değeri
                        double kalanGun = row.has("Kalan Stok Gün")
                                ? row.optDouble("Kalan Stok Gün", 0.0)
                                : 0.0;

                        kalanStokGunByUrunW40.put(urun, kalanGun);

                        // istatistikler
                        toplamKalanStokGunW40 += kalanGun;
                        satirSayisiW40++;
                        if (kalanGun < minKalanStokGunW40) minKalanStokGunW40 = kalanGun;
                        if (kalanGun > maxKalanStokGunW40) maxKalanStokGunW40 = kalanGun;
                    }
                }
            }
        }

        // Yazdırma
        System.out.println("W40 - Ürün bazında Kalan Stok Gün:");
        kalanStokGunByUrunW40.forEach((k, v) ->
                System.out.println(" - " + k + " => " + String.format("%.4f", v)));

        if (satirSayisiW40 > 0) {
            double ortalama = toplamKalanStokGunW40 / satirSayisiW40;
            System.out.println("Min  Kalan Stok Gün (W40): " + String.format("%.4f", minKalanStokGunW40));
            System.out.println("Maks Kalan Stok Gün (W40): " + String.format("%.4f", maxKalanStokGunW40));
            System.out.println("Ort. Kalan Stok Gün  (W40): " + String.format("%.4f", ortalama));
        }

    }

    // JSON:  "Ürün Kısa Ad"  (String)   →   "Kalan Stok Gün" (Double veya null)
    public static Map<String, Double> extractUrunKalanStokGunMap(JSONObject w43Resp) {
        Map<String, Double> map = new LinkedHashMap<>();
        if (w43Resp == null) return map;

        JSONArray results = w43Resp.optJSONArray("result");
        if (results == null) return map;

        for (int i = 0; i < results.length(); i++) {
            JSONObject res = results.optJSONObject(i);
            if (res == null) continue;

            JSONArray data = res.optJSONArray("data");
            if (data == null) continue;

            for (int j = 0; j < data.length(); j++) {
                JSONObject row = data.optJSONObject(j);
                if (row == null) continue;

                // Ürün adını al (öncelik "Ürün Kısa Ad"; fallback olarak "Ürün")
                String urun =
                        row.has("Ürün Kısa Ad") ? row.optString("Ürün Kısa Ad", null)
                                : row.optString("Ürün", null);
                if (urun == null || urun.isBlank()) continue;

                Double kalan = null;
                if (!row.isNull("Kalan Stok Gün")) {
                    // optDouble null'a 0 döndüğü için önce isNull kontrolü yaptık
                    kalan = row.optDouble("Kalan Stok Gün", Double.NaN);
                    if (Double.isNaN(kalan)) kalan = null;
                }

                // İstersen null değerleri atlamak için şu satırı aç:
                // if (kalan == null) continue;

                map.put(urun, kalan);
            }
        }
        return map;
    }



    Map<String, Double> kalanStokGunByUrunW43AggreationS56 = new LinkedHashMap<>();
    @Given("The user send widget43AggreationS56 request")
    public void theUserSendWidget43AggreationS56Request() {
        JSONObject w43AggreationS56Response = Requests.sendWidget43AggreationS56Request();
        System.out.println("W43AggreationS56Response: " + w43AggreationS56Response);
        kalanStokGunByUrunW43AggreationS56 = extractUrunKalanStokGunMap(w43AggreationS56Response);
    }

    public static boolean compareMaps(Map<String, Double> map1, Map<String, Double> map2) {
        double epsilon = 0.01; // tolerans değeri

        for (Map.Entry<String, Double> entry : map1.entrySet()) {
            String key = entry.getKey();
            Double value1 = entry.getValue();
            Double value2 = map2.get(key);

            // map2'de key yoksa veya değer null ise
            if (value2 == null) {
                return false;
            }

            // fark toleransın dışındaysa
            if (Math.abs(value1 - value2) > epsilon) {
                return false;
            }
        }

        return true;
    }


    public static boolean compareMapsStringString(Map<String, String> map1, Map<String, String> map2) {

        for (Map.Entry<String, String> entry : map1.entrySet()) {
            String key = entry.getKey();
            String value1 = entry.getValue();
            String value2 = map2.get(key);

            if (!value1.equals(value2)) {
                return false;
            }
        }

        return true;
    }

    @Then("The user verify scenario56")
    public void theUserVerifyScenario56() {
        Assert.assertTrue("senaryo 56 değerler farklı",
                compareMaps(kalanStokGunByUrunW40,kalanStokGunByUrunW43AggreationS56));
    }

    double toplamTutarW82;

    @Given("The user send widget82 request")
    public void theUserSendWidget82Request() {
        JSONObject widget82Json = Requests.sendWidget82Request();
        System.out.println("widget82Json: " + widget82Json);

        toplamTutarW82 = 0.0;

        if (widget82Json != null) {
            JSONArray results = widget82Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject resultObj = results.optJSONObject(0);
                if (resultObj != null) {
                    JSONArray data = resultObj.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.optJSONObject(i);
                            if (row == null) continue;

                            // Önce "Tutar" kolonunu direkt dene
                            Double val = readAsDouble(row, "Tutar");

                            // Bulunamazsa, anahtarları gezip "tutar" içereni yakala (fallback)
                            if (val == null) {
                                String fallbackKey = findKeyContaining(row, "tutar");
                                if (fallbackKey != null) {
                                    val = readAsDouble(row, fallbackKey);
                                }
                            }

                            if (val != null) {
                                toplamTutarW82 += val;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Widget82 — Tutar Toplamı: " + String.format("%,.2f", toplamTutarW82));
    }

    /** JSON değeri güvenli biçimde double okur (Number/String/null hepsini kaldırır). */
    private static Double readAsDouble(JSONObject obj, String key) {
        if (obj == null || key == null || !obj.has(key) || obj.isNull(key)) return null;
        Object v = obj.opt(key);
        if (v instanceof Number) return ((Number) v).doubleValue();
        if (v instanceof String) {
            try { return Double.parseDouble(((String) v).replace(",", "").trim()); }
            catch (Exception ignored) {}
        }
        return null;
    }

    /** Objede verilen ifadeyi (case-insensitive) içeren ilk anahtarı bulur. */
    private static String findKeyContaining(JSONObject obj, String needle) {
        if (obj == null || needle == null) return null;
        Iterator<String> it = obj.keys();
        String n = needle.toLowerCase(Locale.ROOT);
        while (it.hasNext()) {
            String k = it.next();
            if (k != null && k.toLowerCase(Locale.ROOT).contains(n)) return k;
        }
        return null;
    }

    double toplamTutarW85;
    @Given("The user send widget85 request")
    public void theUserSendWidget85Request() {
        JSONObject widget85Json = Requests.sendWidget85Request();
        System.out.println("widget85Json: " + widget85Json);

        toplamTutarW85 = 0.0;

        if (widget85Json != null) {
            JSONArray results = widget85Json.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject resultObj = results.optJSONObject(0);
                if (resultObj != null) {
                    JSONArray data = resultObj.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        JSONObject firstRow = data.optJSONObject(0);
                        if (firstRow != null && firstRow.has("Tutar")) {
                            toplamTutarW85 = firstRow.optDouble("Tutar", 0.0);
                        }
                    }
                }
            }
        }

        System.out.println("Widget85 - Toplam Tutar: " + String.format("%,.2f", toplamTutarW85));
    }



    @Then("The user verify scenario60")
    public void theUserVerifyScenario60() {
        Assert.assertEquals("senaryo 60 değerler farklı",
                toplamTutarW82, toplamTutarW85,0.01);
    }

    // Map: "01/MM/YYYY"  ->  Toplam Kâr (₺) (aynı aya ait satırlar toplanır)
    private static final Map<String, Double> w65TarihToplamKar = new LinkedHashMap<>();

    @Given("The user send widget65 request")
    public void theUserSendWidget65Request() {
        JSONObject resp = Requests.sendWidget65Request();
        System.out.println("widget65Json: " + resp);

        w65TarihToplamKar.clear();

        if (resp == null) return;

        JSONArray results = resp.optJSONArray("result");
        if (results == null || results.length() == 0) return;

        JSONObject result0 = results.optJSONObject(0);
        if (result0 == null) return;

        JSONArray data = result0.optJSONArray("data");
        if (data == null || data.length() == 0) return;

        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.optJSONObject(i);
            if (row == null) continue;

            // "Fatura Tarihi" -> "YYYY/MM"
            String ym = row.optString("Fatura Tarihi", "").trim();
            if (ym.isEmpty()) continue;

            // "Toplam Kâr (₺)" -> double
            if (row.isNull("Toplam Kâr (₺)")) continue;
            double kar = row.optDouble("Toplam Kâr (₺)", Double.NaN);
            if (Double.isNaN(kar)) continue;

            // YYYY/MM -> 01/MM/YYYY
            String firstDayKey = toFirstDayOfMonthKey(ym);

            // Aynı aya ait satırlar toplanır
            w65TarihToplamKar.merge(firstDayKey, kar, Double::sum);
        }

        // Debug çıktı
        System.out.println("Widget65 Tarih -> Toplam Kâr Map:");
        w65TarihToplamKar.forEach((tarih, toplamKar) ->
                System.out.println(tarih + " → " + String.format(Locale.US, "%.6f", toplamKar)));
    }

    /**
     * "YYYY/MM" formatındaki değeri "01/MM/YYYY" olarak döndürür.
     * Hatalı format gelirse olduğu gibi geri verir.
     */
    private static String toFirstDayOfMonthKey(String yearMonth) {
        try {
            // Beklenen format: YYYY/MM (örn: 2025/04)
            String[] parts = yearMonth.split("/");
            if (parts.length != 2) return yearMonth;

            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);

            java.time.LocalDate d = java.time.LocalDate.of(year, month, 1);
            return d.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            // Beklenmedik format/parse hatasında fallback
            return yearMonth;
        }
    }




    // Toplamları tutacağımız map
    Map<String, Double> w64TarihToplamDegerleri = new LinkedHashMap<>();

    @Given("The user send widget64 request")
    public void theUserSendWidget64Request() {
        JSONObject response = Requests.sendWidget64Request();
        System.out.println("widget64Json: " + response);

        w64TarihToplamDegerleri.clear();

        if (response != null) {
            JSONArray results = response.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject resultObj = results.optJSONObject(0);
                if (resultObj != null) {
                    JSONArray data = resultObj.optJSONArray("data");
                    if (data != null && data.length() > 0) {
                        // Tarih formatı (grafik form_data'daki "%d/%m/%Y" ile uyumlu)
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("tr", "TR"));

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.optJSONObject(i);
                            if (row == null) continue;

                            // Epoch millis -> dd/MM/yyyy
                            double epochMsDouble = row.optDouble("TRHISLEMTARIHI", Double.NaN);
                            if (Double.isNaN(epochMsDouble)) continue; // tarih yoksa geç
                            long epochMs = (long) epochMsDouble;
                            String tarihKey = sdf.format(new Date(epochMs));

                            // TRHISLEMTARIHI dışındaki tüm sayısal kolonları topla
                            double toplam = 0.0;
                            for (Iterator<String> it = row.keys(); it.hasNext(); ) {
                                String key = it.next();
                                if ("TRHISLEMTARIHI".equals(key)) continue;

                                Object val = row.opt(key);
                                if (val instanceof Number) {
                                    toplam += ((Number) val).doubleValue();
                                }
                                // null veya sayı olmayanlar otomatikman 0 kabul ediliyor
                            }

                            w64TarihToplamDegerleri.put(tarihKey, toplam);
                        }
                    }
                }
            }
        }

        // Debug: konsola yazdır
        System.out.println("Widget64 (Tarih → Toplam Değer) Map:");
        w64TarihToplamDegerleri.forEach((tarih, toplam) ->
                System.out.println(tarih + " → " + String.format(Locale.US, "%.2f", toplam)));
    }


    @Then("The user verify scenario59")
    public void theUserVerifyScenario59() {
        Assert.assertTrue("senaryo 59 değerler farklı",
                compareMaps(w65TarihToplamKar,w64TarihToplamDegerleri));
    }

    Map<String, Double> w63TarihVeSegmentToplamMap = new LinkedHashMap<>();

    @Given("The user send widget63 request")
    public void theUserSendWidget63Request() {
        JSONObject response = Requests.sendWidget63Request();
        System.out.println("widget63Json: " + response);

        w63TarihVeSegmentToplamMap.clear();

        if (response == null) return;

        JSONArray results = response.optJSONArray("result");
        if (results == null || results.length() == 0) return;

        JSONObject resultObj = results.optJSONObject(0);
        if (resultObj == null) return;

        // Kolon adlarını al: ilk kolon tarih, kalanları segment
        Set<String> segmentColumns = new LinkedHashSet<>();
        JSONArray colnames = resultObj.optJSONArray("colnames");
        if (colnames != null && colnames.length() > 1) {
            for (int i = 1; i < colnames.length(); i++) {
                segmentColumns.add(colnames.optString(i));
            }
        } else {
            // Fallback: bilinen segment isimleri
            segmentColumns.addAll(Arrays.asList("Mass","Premium","Primary","Semi Premium","Standard","Ultra Premium"));
        }

        JSONArray dataArr = resultObj.optJSONArray("data");
        if (dataArr == null) return;

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ZoneId zone = ZoneId.systemDefault(); // gerekirse ZoneId.of("Europe/Istanbul")

        for (int i = 0; i < dataArr.length(); i++) {
            JSONObject row = dataArr.optJSONObject(i);
            if (row == null) continue;

            // Tarihi çek (epoch millis double gelebiliyor)
            double epochMillisDbl = row.optDouble("TRHISLEMTARIHI", Double.NaN);
            if (Double.isNaN(epochMillisDbl)) continue;

            long epochMillis = (long) epochMillisDbl;
            String tarih = Instant.ofEpochMilli(epochMillis)
                    .atZone(zone)
                    .toLocalDate()
                    .format(fmt);

            // Segment toplamını hesapla (null/NaN değerleri atla)
            double sum = 0.0;
            for (String seg : segmentColumns) {
                if (!row.has(seg) || row.isNull(seg)) continue;
                Object v = row.opt(seg);
                if (v instanceof Number) {
                    double dv = ((Number) v).doubleValue();
                    if (!Double.isNaN(dv) && !Double.isInfinite(dv)) {
                        sum += dv;
                    }
                } else {
                    try {
                        double dv = Double.parseDouble(String.valueOf(v));
                        if (!Double.isNaN(dv) && !Double.isInfinite(dv)) sum += dv;
                    } catch (Exception ignore) {}
                }
            }

            w63TarihVeSegmentToplamMap.put(tarih, sum);
        }

        // Debug
        System.out.println("W63 Tarih → Segment Toplam Map:");
        w63TarihVeSegmentToplamMap.forEach((t, s) -> System.out.println(t + " → " + s));
    }


    @Then("The user verify scenario58")
    public void theUserVerifyScenario58() {
        Assert.assertTrue("senaryo 58 değerler farklı",
                compareMaps(w63TarihVeSegmentToplamMap,w64TarihToplamDegerleri));
    }

    Map<String, Double> markaVeKalanStokGunleriW42 = new LinkedHashMap<>();

    @Given("The user send widget42 request")
    public void theUserSendWidget42Request() {
        JSONObject response = Requests.sendWidget42Request();
        System.out.println("widget42Json: " + response);

        markaVeKalanStokGunleriW42.clear();

        if (response != null) {
            JSONArray results = response.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject resultObj = results.optJSONObject(0);
                if (resultObj != null) {
                    JSONArray dataArr = resultObj.optJSONArray("data");
                    if (dataArr != null && dataArr.length() > 0) {
                        for (int i = 0; i < dataArr.length(); i++) {
                            JSONObject row = dataArr.optJSONObject(i);
                            if (row == null) continue;

                            String marka = row.optString("Marka", "Unknown");
                            double kalanStokGun = row.optDouble("Kalan Stok Gün", 0.0);

                            // Null veya boş marka kontrolü
                            if (marka == null || marka.isEmpty()) continue;

                            // Map'e String-Double formatında ekle
                            markaVeKalanStokGunleriW42.put(marka, kalanStokGun);
                        }
                    }
                }
            }
        }

        // Debug amaçlı konsola bastır
        System.out.println("W42 Marka - Kalan Stok Gün Map (Double versiyon): ");
        for (Map.Entry<String, Double> entry : markaVeKalanStokGunleriW42.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }



    Map<String, Double> w43markaVeKalanStokGunleriS57 = new LinkedHashMap<>();

    @Given("The user send widget43AggreationS57 request")
    public void theUserSendWidget43AggreationS57Request() {
        JSONObject response = Requests.sendWidget43AggreationS57Request();
        System.out.println("widget43AggreationS57Json: " + response);

        w43markaVeKalanStokGunleriS57.clear();

        if (response != null) {
            JSONArray resultArray = response.optJSONArray("result");
            if (resultArray != null && resultArray.length() > 0) {
                JSONObject resultObj = resultArray.optJSONObject(0);
                if (resultObj != null) {
                    JSONArray dataArray = resultObj.optJSONArray("data");
                    if (dataArray != null && dataArray.length() > 0) {
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject row = dataArray.optJSONObject(i);
                            if (row == null) continue;

                            String marka = row.optString("Marka", "").trim();
                            if (marka.isEmpty() || row.isNull("Kalan Stok Gün")) continue;

                            double kalanStokGun = row.optDouble("Kalan Stok Gün", 0.0);
                            w43markaVeKalanStokGunleriS57.put(marka, kalanStokGun);
                        }
                    }
                }
            }
        }

        System.out.println("Widget43 Marka - Kalan Stok Gün Map (Double versiyon):");
        w43markaVeKalanStokGunleriS57.forEach((marka, gun) ->
                System.out.println(marka + " → " + gun));
    }


    @Then("The user verify scenario57")
    public void theUserVerifyScenario57() {
        Assert.assertTrue("senaryo 57 değerler farklı",
                compareMaps(markaVeKalanStokGunleriW42,w43markaVeKalanStokGunleriS57));
    }

    String myColumnW3AggreationS10;

    @Given("The user send widget3AggreationS10 request")
    public void theUserSendWidget3AggreationS10Request() {
        JSONObject response = Requests.sendWidget3AggreationS10Request();
        System.out.println("widget3AggreationS10 response: " + response);

        myColumnW3AggreationS10 = null;

        if (response != null) {
            JSONArray results = response.optJSONArray("result");
            if (results != null && results.length() > 0) {
                JSONObject resultObj = results.optJSONObject(0);
                if (resultObj != null) {
                    JSONArray dataArr = resultObj.optJSONArray("data");
                    if (dataArr != null && dataArr.length() > 0) {
                        JSONObject dataObj = dataArr.optJSONObject(0);
                        if (dataObj != null) {
                            // "My column" değerini al
                            myColumnW3AggreationS10 = dataObj.optString("My column", null);
                        }
                    }
                }
            }
        }

        if (myColumnW3AggreationS10 != null) {
            System.out.println("My Column (W3AggreationS10): " + myColumnW3AggreationS10);
        } else {
            System.out.println("My Column değeri bulunamadı (W3AggreationS10).");
        }
    }

    String ayTrS10Query;
    @Given("The user get S10 query")
    public void theUserGetS10Query() {
        ayTrS10Query = databaseMethods.getAyTrS10();
    }
}
