package com.sema.utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class databaseMethods {


    public static Map<String, Integer> getUrunVeStockOutSayilariW43() {
        final String query = "WITH filtered AS (\n" +
                "  SELECT\n" +
                "    DISTRIBUTOR_KOD,\n" +
                "    URUN_TIP_ACIKLAMA,\n" +
                "    toFloat64(ifNull(Stock_Liters, 0))                  AS stock_l,\n" +
                "    greatest(0., toFloat64(ifNull(Est_Total_Sales_Liters, 0))) AS est_l\n" +
                "  FROM my_database.EstSalesAndCurrStocks\n" +
                "  WHERE DISTRIBUTOR_KOD IN (\n" +
                "    'ASYA KAYIKCI','ADIYAMAN DATA','AGRI TANRIVERDI','ANKARA GRAM','ANKARA LACIN',\n" +
                "    'ANTALYA ANDA','ANTALYA ANKA','ANTALYA INCI','ARTVIN KESKIN','ASYA DOGUS',\n" +
                "    'AYDIN PIRIM','BAYIR ACARLAR','BODRUM PIRIM','BURSA RIT','CAGAN',\n" +
                "    'CANAKKALE BAYRAKTAR','DENIZLI BIZIMYAKI','DIYARBAKIR HNR','DUNYA ZOGULDAK',\n" +
                "    'ELAZIG POLAT','GAZIANTEP EKER','ISPARTA CANTAYLAR','ISTANBUL DOGUS','ISTANBUL GURPA',\n" +
                "    'ISTANBUL KAYIKCI','ISTANBUL OZYIGIT','ISTANBUL PIRIM','IZMIR GURPA','IZMIR PIRIM',\n" +
                "    'KARAMAN ALFA','KASTAMONU LACIN','KAYSERI 4GEN','KIRSEHIR YUKSELLER','KOCAELI GULENER',\n" +
                "    'MALATYA OZSAH','MANISA CANTAY','MANISA CANTAY 2','MARDIN SECEM','MERSIN ALFA',\n" +
                "    'MERSIN SGM','MUGLA ACARLAR','NEVSEHIR ONUR','ORDU GURESCIOGLU','OSMANIYE MARSAS',\n" +
                "    'SAKARYA KOSEOGLU','SAMSUN TANRIVERDI','SIVAS SES','TRAKYA ERZA','URFA UCKOK',\n" +
                "    'USAK BIZIMYAKI','YALOVA KUZEYNAM','CORUM TANRIVERDI'\n" +
                "  )\n" +
                "),\n" +
                "cat AS (\n" +
                "  SELECT\n" +
                "    DISTRIBUTOR_KOD,\n" +
                "    URUN_TIP_ACIKLAMA,\n" +
                "    sum(stock_l) AS stock_l_sum,\n" +
                "    sum(est_l)   AS est_l_sum\n" +
                "  FROM filtered\n" +
                "  GROUP BY DISTRIBUTOR_KOD, URUN_TIP_ACIKLAMA\n" +
                ")\n" +
                "SELECT\n" +
                "  URUN_TIP_ACIKLAMA,\n" +
                "  countDistinctIf(DISTRIBUTOR_KOD, stock_l_sum / nullIf(est_l_sum, 0) < 10)\n" +
                "    AS num_distributors_with_so\n" +
                "FROM cat\n" +
                "GROUP BY URUN_TIP_ACIKLAMA\n" +
                "ORDER BY num_distributors_with_so DESC, URUN_TIP_ACIKLAMA desc\n" +
                "LIMIT 10;";

        Map<String, Integer> urunVeStockOutSayilariW43 = new HashMap<>();

        try (Connection conn = DatabaseManager.getConnection(
                DbConfigs.DIA_CLICKHOUSE, DbConfigs.DIA_CLICKHOUSE_USERNAME, DbConfigs.DIA_CLICKHOUSE_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Kolon adlarıyla güvenli okuma
                final String urun = rs.getString("URUN_TIP_ACIKLAMA");
                final int value = rs.getInt("num_distributors_with_so");

//                System.out.println(urun + ": " + value);

                urunVeStockOutSayilariW43.put(urun,value);
            }

            // İsteğe bağlı: log
            System.out.println("W43 ürün bazlı stock-out haritası: " + urunVeStockOutSayilariW43);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return urunVeStockOutSayilariW43;
    }

    public static int getStockOutSumW43() {
        final String query = " SELECT\n" +
                " `Distribütör`,\n" +
                "  count() AS c\n" +
                "FROM\n" +
                "(\n" +
                "  SELECT\n" +
                "    DISTRIBUTOR_KOD        AS `Distribütör`,\n" +
                "    URUN_KATEGORU_ACIKLAMA AS `Ürün Kategori`,\n" +
                "    sum(toFloat64(ifNull(Stock_Liters, 0)))                          AS `stok_sum`,\n" +
                "    sum(greatest(0., toFloat64(ifNull(Est_Total_Sales_Liters, 0))))  AS `est_sum`\n" +
                "  FROM my_database.EstSalesAndCurrStocks\n" +
                "  WHERE DISTRIBUTOR_KOD IN ('ASYA KAYIKCI','ADIYAMAN DATA','AGRI TANRIVERDI','ANKARA GRAM','ANKARA LACIN',\n" +
                "    'ANTALYA ANDA','ANTALYA ANKA','ANTALYA INCI','ARTVIN KESKIN','ASYA DOGUS',\n" +
                "    'AYDIN PIRIM','BAYIR ACARLAR','BODRUM PIRIM','BURSA RIT','CAGAN',\n" +
                "    'CANAKKALE BAYRAKTAR','DENIZLI BIZIMYAKI','DIYARBAKIR HNR','DUNYA ZOGULDAK',\n" +
                "    'ELAZIG POLAT','GAZIANTEP EKER','ISPARTA CANTAYLAR','ISTANBUL DOGUS','ISTANBUL GURPA',\n" +
                "    'ISTANBUL KAYIKCI','ISTANBUL OZYIGIT','ISTANBUL PIRIM','IZMIR GURPA','IZMIR PIRIM',\n" +
                "    'KARAMAN ALFA','KASTAMONU LACIN','KAYSERI 4GEN','KIRSEHIR YUKSELLER','KOCAELI GULENER',\n" +
                "    'MALATYA OZSAH','MANISA CANTAY','MANISA CANTAY 2','MARDIN SECEM','MERSIN ALFA',\n" +
                "    'MERSIN SGM','MUGLA ACARLAR','NEVSEHIR ONUR','ORDU GURESCIOGLU','OSMANIYE MARSAS',\n" +
                "    'SAKARYA KOSEOGLU','SAMSUN TANRIVERDI','SIVAS SES','TRAKYA ERZA','URFA UCKOK',\n" +
                "    'USAK BIZIMYAKI','YALOVA KUZEYNAM','CORUM TANRIVERDI')\n" +
                "    AND DISTRIBUTOR_KOD NOT IN ('EDIRNE MAHALO')\n" +
                "  GROUP BY `Distribütör`, `Ürün Kategori`\n" +
                ")\n" +
                "WHERE `stok_sum` / nullIf(`est_sum`, 0) < 10\n" +
                "GROUP BY `Distribütör`\n" +
                "ORDER BY c desc LIMIT 10;";

        int stockOutCount = 0;

        try (Connection conn = DatabaseManager.getConnection(
                DbConfigs.DIA_CLICKHOUSE, DbConfigs.DIA_CLICKHOUSE_USERNAME, DbConfigs.DIA_CLICKHOUSE_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Kolon adlarıyla güvenli okuma
                stockOutCount += rs.getInt("c");
            }

            // İsteğe bağlı: log
            System.out.println("W43 stockOutCount: " + stockOutCount);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stockOutCount;

    }

    public static int getPlannedRoutesScenario15() {
        final String query = "\n" +
                "SELECT\n" +
                "\tCOUNT(DISTINCT pvv.ROTA_KODU) AS HedefRotaSayisi\n" +
                "FROM\n" +
                "\tmy_database.planned_visits pvv\n" +
                "JOIN (\n" +
                "\tSELECT\n" +
                "\t\tDISTINCT SON_ROUTE_KODU,\n" +
                "\t\tbolge1.AttributeOptionLabel AS Bolge\n" +
                "\tFROM\n" +
                "\t\tmy_database.staging_account_dummy sad\n" +
                "\tLEFT JOIN my_database.AttributeOptions bolge1 ON\n" +
                "\t\tbolge1.AttributeCode = '1_28'\n" +
                "\t\tAND sad.`1_28` = bolge1.AttributeOptionCode\n" +
                "\tWHERE\n" +
                "\t\tisNotNull(sad.SON_ROUTE_KODU)\n" +
                "\t\tAND BYTDURUM = 0\n" +
                "\t\tAND sad.SON_ROUTE_ADI NOT LIKE '%LZM%'\n" +
                "\t\tAND sad.SON_ROUTE_ADI NOT LIKE '%Lzm%' ) AS bolge_join ON\n" +
                "\tbolge_join.SON_ROUTE_KODU = pvv.ROTA_KODU\n" +
                "LEFT JOIN my_database.MEY_MD_ROUTE_CRM mmrc ON\n" +
                "\tmmrc.CODE = pvv.ROTA_KODU\n" +
                "WHERE\n" +
                "\tPLANLANAN_ZIYARET_TARIHI = today()\n" +
                "\tAND mmrc.DESCRIPTION_1 NOT ILIKE '%Telesell%'\n" +
                "\tAND mmrc.DESCRIPTION_1 NOT ILIKE '%LZM%'\n" +
                "\tAND mmrc.DESCRIPTION_1 NOT ILIKE '%Reserve%'\n" +
                "\tAND mmrc.DESCRIPTION_1 NOT ILIKE '%soguk%'\n" +
                "\tAND mmrc.LNGDISTKOD NOT IN ('1', '2', '999', '384')\n" +
                "\tAND PLANLANAN_ZIYARET_TARIHI = today()";

        int plannedRoutesCount = 0;

        try (Connection conn = DatabaseManager.getConnection(
                DbConfigs.DIA_CLICKHOUSE, DbConfigs.DIA_CLICKHOUSE_USERNAME, DbConfigs.DIA_CLICKHOUSE_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Kolon adlarıyla güvenli okuma
                plannedRoutesCount = rs.getInt("HedefRotaSayisi");
            }

            // İsteğe bağlı: log
            System.out.println("PlannedRoutesScenario15: " + plannedRoutesCount);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plannedRoutesCount;
    }

    public static String getBmNameS26() {
        String query = "select DISTINCT\n" +
                "        BM_Name\n" +
                "from\n" +
                "        my_database.HierarchyInfo hi\n" +
                "WHERE\n" +
                "        ((splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM_Code, ''), 'ı', 'i'), 'İ', 'I')))[1] LIKE splitByChar(' ', upperUTF8(replaceAll(replaceAll('Marmara', 'ı', 'i'), 'İ', 'I')))[1]))";

        String bmName = null;

        try (Connection conn = DatabaseManager.getConnection(
                DbConfigs.DIA_CLICKHOUSE, DbConfigs.DIA_CLICKHOUSE_USERNAME, DbConfigs.DIA_CLICKHOUSE_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Kolon adlarıyla güvenli okuma
                bmName = rs.getString("BM_Name");
            }

            // İsteğe bağlı: log
            System.out.println("bmNameS26: " + bmName);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bmName;

    }

    public static String getBmEmailS27() {

        String query = "select DISTINCT\n" +
                "        BM_Email, BM\n" +
                "from\n" +
                "        my_database.HierarchyInfo hi\n" +
                "WHERE\n" +
                "        ((splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM_Code, ''), 'ı', 'i'), 'İ', 'I')))[1] LIKE splitByChar(' ', upperUTF8(replaceAll(replaceAll('Marmara', 'ı', 'i'), 'İ', 'I')))[1]))";

        String bmEmail = null;

        try (Connection conn = DatabaseManager.getConnection(
                DbConfigs.DIA_CLICKHOUSE, DbConfigs.DIA_CLICKHOUSE_USERNAME, DbConfigs.DIA_CLICKHOUSE_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Kolon adlarıyla güvenli okuma
                bmEmail = rs.getString("BM_Email");
            }

            // İsteğe bağlı: log
            System.out.println("bmEmailS27: " + bmEmail);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bmEmail;

    }

    public static String getAyTrS28() {
        String query = "WITH base AS (\n" +
                "    SELECT\n" +
                "        toDate(CONCAT(t.FISCALYEAR,'-',t.FISCALMONTH,'-1')) AS AY,\n" +
                "        avg(TotalTarget) AS TotalTarget,     -- rota/ürün kırılımında hedef\n" +
                "        sum(Total_Sales) AS Total_Sales      -- rota/ürün kırılımında gerçekleşme\n" +
                "    FROM my_database.IcHedefSatisVeriSuperset_v4 t\n" +
                "    WHERE\n" +
                "        (t.TIP NOT IN ('4') OR t.TIP IS NULL)\n" +
                "        AND t.ROTA NOT IN ('ZM')\n" +
                "        AND t.ROTA NOT ILIKE '%LZM%'\n" +
                "        AND splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(t.BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\n" +
                "            = splitByChar(' ', upperUTF8(replaceAll(replaceAll('Marmara', 'ı','i'),'İ','I')))[1]\n" +
                "        AND toDate(CONCAT(t.FISCALYEAR,'-',t.FISCALMONTH,'-1')) >= toDate('2024-11-01')\n" +
                "    GROUP BY\n" +
                "        t.FISCALYEAR, t.FISCALMONTH, t.ProductQuality, t.ProductCat, t.Product,\n" +
                "        t.BM, t.SM, t.FM, t.ROUTE_CODE, t.ROTA\n" +
                "),\n" +
                "ay_ozet AS (\n" +
                "    SELECT\n" +
                "        AY,\n" +
                "        sum(TotalTarget) AS Ic_Hedef,\n" +
                "        sum(Total_Sales) AS Gerceklesme\n" +
                "    FROM base\n" +
                "    GROUP BY AY\n" +
                ")\n" +
                "SELECT\n" +
                "        arrayElement(['Ocak','Şubat','Mart','Nisan','Mayıs','Haziran','Temmuz','Ağustos','Eylül','Ekim','Kasım','Aralık'], toMonth(AY)) AS Ay_TR\n" +
                "FROM ay_ozet\n" +
                "WHERE Gerceklesme > Ic_Hedef\n" +
                "ORDER BY AY DESC\n" +
                "LIMIT 1;";

        String ayTrS28 = null;

        try (Connection conn = DatabaseManager.getConnection(
                DbConfigs.DIA_CLICKHOUSE, DbConfigs.DIA_CLICKHOUSE_USERNAME, DbConfigs.DIA_CLICKHOUSE_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Kolon adlarıyla güvenli okuma
                ayTrS28 = rs.getString("Ay_TR");
            }

            // İsteğe bağlı: log
            System.out.println("-----\nayTrS28: " + ayTrS28 + "\n-----");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ayTrS28;

    }

    public static double getTotalSales30() {
        String query = "select\n" +
                "toFloat64(SUM(IF(sf.BYTTUR IN (0,3),  sf.URUN_AMBALAJ_LITRE * sf.INVOICE_QUANTITY,\n" +
                "                         IF(sf.BYTTUR IN (2,4), -sf.URUN_AMBALAJ_LITRE * sf.INVOICE_QUANTITY, 0)))) AS Total_Sales\n" +
                "    FROM my_database.staging_fatura_v2 sf\n" +
                "    WHERE sf.BYTDURUM = 0\n" +
                "      AND sf.LNGDISTKOD NOT IN (1,2,384,999)\n" +
                "      AND sf.MUSTERI_KOD != 'Mey İçki'\n" +
                "      AND TRHISLEMTARIHI >= today()\n" +
                "      AND EK_BOLGEMUDURLUGU = 'Marmara Bölge'";

        double totalSales = 0;

        try (Connection conn = DatabaseManager.getConnection(
                DbConfigs.DIA_CLICKHOUSE, DbConfigs.DIA_CLICKHOUSE_USERNAME, DbConfigs.DIA_CLICKHOUSE_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Kolon adlarıyla güvenli okuma
                totalSales = rs.getDouble("Total_Sales");
            }

            // İsteğe bağlı: log
            System.out.println("-----\ntotalSalesS30: " + totalSales + "\n-----");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalSales;


    }

    public static double getTotalSalesS31() {
        String query = "select\n" +
                "toFloat64(SUM(IF(sf.BYTTUR IN (0,3),  sf.URUN_AMBALAJ_LITRE * sf.INVOICE_QUANTITY,\n" +
                "                         IF(sf.BYTTUR IN (2,4), -sf.URUN_AMBALAJ_LITRE * sf.INVOICE_QUANTITY, 0)))) AS Total_Sales\n" +
                "    FROM my_database.staging_fatura_v2 sf\n" +
                "    WHERE sf.BYTDURUM = 0\n" +
                "      AND sf.LNGDISTKOD NOT IN (1,2,384,999)\n" +
                "      AND sf.MUSTERI_KOD != 'Mey İçki'\n" +
                "      AND TRHISLEMTARIHI >= toMonday(today())\n" +
                "      AND EK_BOLGEMUDURLUGU = 'Marmara Bölge'\n" +
                "      AND SP_URUN_KALITE_SEGMENT IN ('Super Premium','Premium Plus','Ultra Premium','Premium')\n" +
                "      AND SP_URUN_KATEGORI_ACIKLAMA NOT IN ('Şarap')\n" +
                "      AND EK_ROUTE_NAME NOT ILIKE '%LZM%'";

        double totalSales = 0;

        try (Connection conn = DatabaseManager.getConnection(
                DbConfigs.DIA_CLICKHOUSE, DbConfigs.DIA_CLICKHOUSE_USERNAME, DbConfigs.DIA_CLICKHOUSE_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Kolon adlarıyla güvenli okuma
                totalSales = rs.getDouble("Total_Sales");
            }

            // İsteğe bağlı: log
            System.out.println("-----\ntotalSalesS31: " + totalSales + "\n-----");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalSales;
    }

    public static double getProjectedSalesS39() {
        String query = "select\n" +
                "toFloat64(SUM(IF(sf.BYTTUR IN (0,3),  sf.URUN_AMBALAJ_LITRE * sf.INVOICE_QUANTITY,\n" +
                "                         IF(sf.BYTTUR IN (2,4), -sf.URUN_AMBALAJ_LITRE * sf.INVOICE_QUANTITY, 0))))/length(\n" +
                "  arrayFilter(\n" +
                "    x -> toDayOfWeek(addDays(toStartOfMonth(today()), x)) BETWEEN 1 AND 5,\n" +
                "    range(\n" +
                "      toUInt64(\n" +
                "        dateDiff(\n" +
                "          'day',\n" +
                "          toStartOfMonth(today()),\n" +
                "          least(today(), addMonths(toStartOfMonth(today()), 1))\n" +
                "        )\n" +
                "      )\n" +
                "    )\n" +
                "  )\n" +
                ")*length(\n" +
                "  arrayFilter(\n" +
                "    x -> toDayOfWeek(addDays(toStartOfMonth(today()), x)) BETWEEN 1 AND 5,\n" +
                "    range(\n" +
                "      toUInt64(\n" +
                "        dateDiff(\n" +
                "          'day',\n" +
                "          toStartOfMonth(today()),\n" +
                "          addMonths(toStartOfMonth(today()), 1)\n" +
                "        )\n" +
                "      )\n" +
                "    )\n" +
                "  )\n" +
                ")\n" +
                " AS Projected_Sales\n" +
                "    FROM my_database.staging_fatura_v2 sf\n" +
                "    WHERE sf.BYTDURUM = 0\n" +
                "      AND sf.LNGDISTKOD NOT IN (1,2,384,999)\n" +
                "      AND sf.MUSTERI_KOD != 'Mey İçki'\n" +
                "      AND TRHISLEMTARIHI >= toStartOfMonth(today())\n" +
                "      AND TRHISLEMTARIHI < today()\n" +
                "      AND EK_BOLGEMUDURLUGU = 'Marmara Bölge'\n" +
                "      AND EK_ROUTE_NAME NOT ILIKE '%LZM%'";

        double projectedSalesS39 = 0;

        try (Connection conn = DatabaseManager.getConnection(
                DbConfigs.DIA_CLICKHOUSE, DbConfigs.DIA_CLICKHOUSE_USERNAME, DbConfigs.DIA_CLICKHOUSE_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Kolon adlarıyla güvenli okuma
                projectedSalesS39 = rs.getDouble("Projected_Sales");
            }

            // İsteğe bağlı: log
            System.out.println("-----\ntotalSalesS39: " + projectedSalesS39 + "\n-----");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projectedSalesS39;
    }
}
