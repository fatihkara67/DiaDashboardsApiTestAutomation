package com.sema.utilities;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Requests {

    public static JSONObject sendWidget11Request() throws IOException {
        // İnsecure client'ı oluştur ve AYNI client üstüne timeoutları uygula (ssl ayarları korunur)
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A520%7D&force=true";

        String jsonBody = """
        {"datasource":{"id":363,"type":"table"},"force":true,"queries":[{"filters":[],"extras":{"having":"","where":"(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\nLIKE \\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll( {% if url_param('BM') %} '{{ url_param('BM') }}' {% else %} 'Marmara' {% endif %}, 'ı', 'i'), 'İ', 'I')))[1])"},"applied_time_extras":{},"columns":[{"columnType":"BASE_AXIS","sqlExpression":"PMonth","label":"PMonth","expressionType":"SQL"},"FYear"],"metrics":[{"aggregate":"SUM","column":{"column_name":"Total_Sales","type":"Nullable(Float64)"},"expressionType":"SIMPLE","label":"SUM(Total_Sales)"}],"orderby":[[{"aggregate":"SUM","column":{"column_name":"Total_Sales"},"expressionType":"SIMPLE","label":"SUM(Total_Sales)"},false]],"annotation_layers":[],"row_limit":10000,"series_columns":["FYear"],"series_limit":0,"order_desc":true,"url_params":{"dashboard_page_id":"1J1r34hZDTtlUqPdX77xx","slice_id":"520"},"post_processing":[{"operation":"pivot","options":{"index":["PMonth"],"columns":["FYear"],"aggregates":{"SUM(Total_Sales)":{"operator":"mean"}},"drop_missing_columns":false}},{"operation":"rename","options":{"columns":{"SUM(Total_Sales)":null},"level":0,"inplace":true}},{"operation":"flatten"}]}],"form_data":{"datasource":"363__table","viz_type":"echarts_timeseries_bar","slice_id":520,"url_params":{"dashboard_page_id":"1J1r34hZDTtlUqPdX77xx","slice_id":"520"},"x_axis":"PMonth","x_axis_sort_asc":true,"metrics":[{"aggregate":"SUM","column":{"column_name":"Total_Sales"},"expressionType":"SIMPLE","label":"SUM(Total_Sales)"}],"groupby":["FYear"],"order_desc":true,"row_limit":10000,"show_empty_columns":true,"comparison_type":"values","annotation_layers":[],"orientation":"vertical","x_axis_title":"Aylar","y_axis_title":"Litre","x_axis_time_format":"smart_date","y_axis_format":"SMART_NUMBER","logAxis":false,"rich_tooltip":true,"extra_form_data":{},"force":true,"result_format":"json","result_type":"full"},"result_format":"json","result_type":"full"}
        """;

        RequestBody body =
                RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Connection", "keep-alive")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=P4Dat7A69PU&dashboard_page_id=1J1r34hZDTtlUqPdX77xx&slice_id=520")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36")
                .addHeader("Cookie", cookie)
                .build();

        int maxRetries = 3;
        IOException lastException = null;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseStr = response.body().string();
                    return new JSONObject(responseStr);
                } else {
                    System.err.println("Request failed (status=" + response.code() + "), attempt " + attempt);
                }
            } catch (IOException e) {
                lastException = e;
                System.err.println("Attempt " + attempt + " failed: " + e.getMessage());
            }
        }

        throw new IOException("Failed to get response after " + maxRetries + " attempts", lastException);
    }

    public static JSONObject sendWidget12Request() throws IOException {
        String cookie = ConfigurationReader.getProperty("cookie");

        // SSL doğrulamasını kapatan client'ı baz al, timeout'ları ekle
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        // URL (slice_id=531)
        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A531%7D&force=true";

        // Body (curl'deki --data-raw birebir)
        String jsonBody = """
            {"datasource":{"id":330,"type":"table"},"force":true,"queries":[{"filters":[],"extras":{"having":"","where":"(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (toDate(CONCAT(FISCALYEAR,'-',FISCALMONTH,'-1')) >= '2024-11-01') AND (ROTA NOT ILIKE '%LZM%')"},"applied_time_extras":{},"columns":[{"columnType":"BASE_AXIS","datasourceWarning":false,"expressionType":"SQL","label":"FISCALMONTH","sqlExpression":"toDate(CONCAT(FISCALYEAR,'-',FISCALMONTH,'-1'))"}],"metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_gf934wxhf6i_dgxgeysbz2p","sqlExpression":"SUM(TotalTarget)"}],"annotation_layers":[],"row_limit":100,"series_columns":[],"series_limit":0,"url_params":{"dashboard_page_id":"1J1r34hZDTtlUqPdX77xx","slice_id":"531"},"custom_params":{},"custom_form_data":{},"time_offsets":[],"post_processing":[{"operation":"pivot","options":{"index":["FISCALMONTH"],"columns":[],"aggregates":{"İç Hedef":{"operator":"mean"}},"drop_missing_columns":true}},{"operation":"flatten"}],"orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_gf934wxhf6i_dgxgeysbz2p","sqlExpression":"SUM(TotalTarget)"},false]]},{"filters":[],"extras":{"having":"","where":"(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (toDate(CONCAT(FISCALYEAR,'-',FISCALMONTH,'-1')) >= '2024-11-01') AND (ROTA NOT ILIKE '%LZM%')"},"applied_time_extras":{},"columns":[{"columnType":"BASE_AXIS","datasourceWarning":false,"expressionType":"SQL","label":"FISCALMONTH","sqlExpression":"toDate(CONCAT(FISCALYEAR,'-',FISCALMONTH,'-1'))"}],"metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme","optionName":"metric_y0bto0urhvs_q4mcb4h5k","sqlExpression":"SUM(Total_Sales)"}],"annotation_layers":[],"row_limit":10000,"series_columns":[],"series_limit":0,"url_params":{"dashboard_page_id":"1J1r34hZDTtlUqPdX77xx","slice_id":"531"},"custom_params":{},"custom_form_data":{},"time_offsets":[],"post_processing":[{"operation":"pivot","options":{"index":["FISCALMONTH"],"columns":[],"aggregates":{"Gerçekleşme":{"operator":"mean"}},"drop_missing_columns":true}},{"operation":"flatten"}],"orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme","optionName":"metric_y0bto0urhvs_q4mcb4h5k","sqlExpression":"SUM(Total_Sales)"},false]]}],"form_data":{"datasource":"330__table","viz_type":"mixed_timeseries","slice_id":531,"url_params":{"dashboard_page_id":"1J1r34hZDTtlUqPdX77xx","slice_id":"531"},"x_axis":{"datasourceWarning":false,"expressionType":"SQL","label":"FISCALMONTH","sqlExpression":"toDate(CONCAT(FISCALYEAR,'-',FISCALMONTH,'-1'))"},"metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_gf934wxhf6i_dgxgeysbz2p","sqlExpression":"SUM(TotalTarget)"}],"groupby":[],"adhoc_filters":[{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_5xhp6l9pxzg_bcqhkgw55j5","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","subject":null},{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_qxatkma6p2_dz3jzprdyho","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"toDate(CONCAT(FISCALYEAR,'-',FISCALMONTH,'-1')) >= '2024-11-01'","subject":null},{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_ygihefr39ba_2pcearedtt5","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ROTA NOT ILIKE '%LZM%'","subject":null}],"order_desc":true,"row_limit":100,"truncate_metric":true,"comparison_type":"values","annotation_layers":[],"x_axis_title":"","x_axis_title_margin":15,"y_axis_title":"","y_axis_title_margin":50,"y_axis_title_position":"Left","color_scheme":"d3Category20","time_shift_color":true,"seriesType":"middle","show_value":true,"opacity":0.2,"markerEnabled":true,"markerSize":6,"seriesTypeB":"bar","show_valueB":false,"opacityB":0.2,"markerEnabledB":true,"markerSizeB":6,"show_legend":true,"legendType":"scroll","legendOrientation":"bottom","legendMargin":null,"x_axis_time_format":"%B","xAxisLabelRotation":45,"rich_tooltip":true,"showTooltipTotal":true,"tooltipTimeFormat":"smart_date","minorSplitLine":false,"truncateXAxis":true,"truncateYAxis":false,"y_axis_bounds":[null,null],"y_axis_format":"SMART_NUMBER","logAxis":false,"y_axis_bounds_secondary":[null,null],"y_axis_format_secondary":"SMART_NUMBER","extra_form_data":{},"force":true,"result_format":"json","result_type":"full"},"result_format":"json","result_type":"full"}
            """;

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Connection", "keep-alive")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=VefSP7GAnVI&dashboard_page_id=1J1r34hZDTtlUqPdX77xx&slice_id=531")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36")
                .addHeader("Cookie", cookie)
                // İstersen boş da olsa CSRF header'ını ekleyebilirsin:
                // .addHeader("X-CSRFToken", "")
                .build();

        int maxRetries = 3;
        int attempt = 0;
        IOException lastEx = null;

        while (attempt < maxRetries) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String resp = response.body().string();
                    return new JSONObject(resp);
                } else {
                    System.err.println("sendV12Request - HTTP " + response.code());
                }
            } catch (IOException e) {
                lastEx = e;
                System.err.println("sendV12Request attempt " + (attempt + 1) + " failed: " + e.getMessage());
            }
            attempt++;
            try { Thread.sleep(350L); } catch (InterruptedException ignored) {}
        }

        throw new IOException("sendV12Request failed after " + maxRetries + " attempts", lastEx);
    }

    static int timeoutValue = Integer.parseInt(ConfigurationReader.getProperty("timeout"));
    public static JSONObject sendWidget13Request(String region) throws IOException {
        // 1) Cookie'yi en başta al
        String cookie = ConfigurationReader.getProperty("cookie"); // örn: "session=...."

        // 2) PKIX hatasını bypass eden client + timeoutlar (lokal/test içindir)
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(timeoutValue, TimeUnit.SECONDS)
                .readTimeout(timeoutValue, TimeUnit.SECONDS)
                .writeTimeout(timeoutValue, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        // 3) URL
        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A602%7D";

        // 4) Body (curl'deki --data-raw birebir)
        String jsonBody = """
        {"datasource":{"id":330,"type":"table"},"force":false,"queries":[{"filters":[],"extras":{"having":"","where":"(ProductCat NOT IN ('Şarap')) AND (upperUTF8(replaceAll(replaceAll(SM, 'ı', 'i'), 'İ', 'I')) = upperUTF8(replaceAll(replaceAll({% if url_param('SM') %}\\r\\n\\t'{{ url_param('SM') }}'\\r\\n{% else %}\\r\\n  'Trakya'\\r\\n{% endif %}, 'ı', 'i'), 'İ', 'I'))) AND (ROTA NOT ILIKE '%LZM%') AND (FISCALYEAR >= toYear(today())\\r\\n) AND (FISCALMONTH >= toMonth(today())\\r\\n)"},"applied_time_extras":{},"columns":[{"expressionType":"SQL","label":"URUN_KALITE_SEGMENT_ACIKLAMA","sqlExpression":"CASE WHEN ProductQuality IN ('Super Premium','Premium Plus','Ultra Premium','Premium') THEN 'Prem' ELSE 'Diğer' END"}],"metrics":[{"aggregate":"SUM","column":{"advanced_data_type":null,"changed_on":"2025-07-24T13:27:21.640818","column_name":"Total_Sales","created_on":"2025-07-24T13:27:21.640816","description":null,"expression":null,"extra":"{\\"warning_markdown\\":null}","filterable":true,"groupby":true,"id":7207,"is_active":true,"is_dttm":false,"python_date_format":null,"type":"Nullable(Float64)","type_generic":null,"uuid":"edbce4ca-b8ae-44a5-bc78-96f8c0b942ce","verbose_name":null},"datasourceWarning":false,"expressionType":"SIMPLE","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_dwg0hvl6qcp_t94ouzcs39f","sqlExpression":null}],"annotation_layers":[],"row_limit":100,"series_limit":0,"order_desc":true,"url_params":{"form_data_key":"YB15TPOTvKM","slice_id":"602"},"custom_params":{},"custom_form_data":{}}],"form_data":{"datasource":"330__table","viz_type":"pie","slice_id":602,"url_params":{"form_data_key":"YB15TPOTvKM","slice_id":"602"},"groupby":[{"expressionType":"SQL","label":"URUN_KALITE_SEGMENT_ACIKLAMA","sqlExpression":"CASE WHEN ProductQuality IN ('Super Premium','Premium Plus','Ultra Premium','Premium') THEN 'Prem' ELSE 'Diğer' END"}],"metric":{"aggregate":"SUM","column":{"advanced_data_type":null,"changed_on":"2025-07-24T13:27:21.640818","column_name":"Total_Sales","created_on":"2025-07-24T13:27:21.640816","description":null,"expression":null,"extra":"{\\"warning_markdown\\":null}","filterable":true,"groupby":true,"id":7207,"is_active":true,"is_dttm":false,"python_date_format":null,"type":"Nullable(Float64)","type_generic":null,"uuid":"edbce4ca-b8ae-44a5-bc78-96f8c0b942ce","verbose_name":null},"datasourceWarning":false,"expressionType":"SIMPLE","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_dwg0hvl6qcp_t94ouzcs39f","sqlExpression":null},"adhoc_filters":[{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_ikvph3ptweb_w3ihyq3cb","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ProductCat NOT IN ('Şarap')","subject":null},{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_rmt32gd8a_cimhuosw4d","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"upperUTF8(replaceAll(replaceAll(SM, 'ı', 'i'), 'İ', 'I')) = upperUTF8(replaceAll(replaceAll({% if url_param('SM') %}\\r\\n\\t'{{ url_param('SM') }}'\\r\\n{% else %}\\r\\n  'Trakya'\\r\\n{% endif %}, 'ı', 'i'), 'İ', 'I'))","subject":null},{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_1hvghqz4gc6_fqt0np049ma","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ROTA NOT ILIKE '%LZM%'","subject":null},{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_fm6ccuozj7_34dfvkripbi","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"FISCALYEAR >= toYear(today())\\r\\n","subject":null},{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_dgdrgra5hk_y08znxbpvq","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"FISCALMONTH >= toMonth(today())\\r\\n","subject":null}],"row_limit":100,"sort_by_metric":false,"color_scheme":"d3Category20","show_labels_threshold":5,"show_legend":true,"legendType":"scroll","legendOrientation":"bottom","legendMargin":null,"label_type":"percent","number_format":"SMART_NUMBER","date_format":"smart_date","show_labels":true,"labels_outside":true,"label_line":true,"outerRadius":60,"donut":true,"innerRadius":35,"extra_form_data":{},"force":false,"result_format":"json","result_type":"full"},"result_format":"json","result_type":"full"}
        """;

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7")
                .addHeader("Connection", "keep-alive")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=YB15TPOTvKM&slice_id=602")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36")
                .addHeader("X-CSRFToken", "") // cURL'de 'X-CSRFToken;' boş geldiğinden boş gönderiyoruz
                .addHeader("Cookie", cookie)   // -b ile gönderdiğin session cookie burada
                .build();

        // 5) Retry mekanizması
        int maxRetries = 3;
        IOException lastEx = null;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return new JSONObject(response.body().string());
                } else {
                    System.err.println("sendWidget13Request - HTTP " + response.code() + " (attempt " + attempt + ")");
                }
            } catch (IOException e) {
                lastEx = e;
                System.err.println("sendWidget13Request attempt " + attempt + " failed: " + e.getMessage());
            }

            try { Thread.sleep(300L); } catch (InterruptedException ignored) {}
        }

        throw new IOException("sendWidget13Request failed after " + maxRetries + " attempts", lastEx);
    }

    public static JSONObject sendWidget14Request() throws IOException {
        // Cookie'yi en başta al
        String cookie = ConfigurationReader.getProperty("cookie"); // örn: "session=...."

        // PKIX/SSL hatasını bypass eden client + timeoutlar (lokal/test içindir)
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(timeoutValue, TimeUnit.SECONDS)
                .readTimeout(timeoutValue, TimeUnit.SECONDS)
                .writeTimeout(timeoutValue, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        // URL (slice_id=532)
        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A532%7D";

        // Body (curl'deki --data-raw birebir)
        String jsonBody = """
        {"datasource":{"id":330,"type":"table"},"force":false,"queries":[{"filters":[],"extras":{"having":"","where":"(ProductCat NOT IN ('Şarap')) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (ROTA NOT ILIKE '%LZM%') AND (FISCALYEAR >= toYear(today())\\r\\n) AND (FISCALMONTH >= toMonth(today())\\r\\n)"},"applied_time_extras":{},"columns":[{"datasourceWarning":false,"expressionType":"SQL","label":"Satış Müdürlüğü","sqlExpression":"SM"}],"metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Prem. Satış %","optionName":"metric_u2lrr82plsg_yy5eko57hqm","sqlExpression":"(\\r\\n      sum(\\r\\n        IF(\\r\\n          ProductQuality IN (\\r\\n            'Super Premium',\\r\\n            'Premium Plus',\\r\\n            'Ultra Premium',\\r\\n            'Premium'\\r\\n          ),\\r\\n          Total_Sales,0\\r\\n        )\\r\\n      ) * 1.0\\r\\n    )\\r\\n    /\\r\\n    sum(Total_Sales\\r\\n        )"}],"orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Prem. Satış %","optionName":"metric_u2lrr82plsg_yy5eko57hqm","sqlExpression":"(\\r\\n      sum(\\r\\n        IF(\\r\\n          ProductQuality IN (\\r\\n            'Super Premium',\\r\\n            'Premium Plus',\\r\\n            'Ultra Premium',\\r\\n            'Premium'\\r\\n          ),\\r\\n          Total_Sales,0\\r\\n        )\\r\\n      ) * 1.0\\r\\n    )\\r\\n    /\\r\\n    sum(Total_Sales\\r\\n        )"},false]],"annotation_layers":[],"row_limit":100,"series_limit":0,"order_desc":true,"url_params":{"dashboard_page_id":"1J1r34hZDTtlUqPdX77xx","form_data_key":"M4owMfjPEF4","slice_id":"532"},"custom_params":{},"custom_form_data":{},"post_processing":[],"time_offsets":[]}],"form_data":{"datasource":"330__table","viz_type":"table","slice_id":532,"url_params":{"dashboard_page_id":"1J1r34hZDTtlUqPdX77xx","form_data_key":"M4owMfjPEF4","slice_id":"532"},"query_mode":"aggregate","groupby":[{"datasourceWarning":false,"expressionType":"SQL","label":"Satış Müdürlüğü","sqlExpression":"SM"}],"temporal_columns_lookup":{},"metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Prem. Satış %","optionName":"metric_u2lrr82plsg_yy5eko57hqm","sqlExpression":"(\\r\\n      sum(\\r\\n        IF(\\r\\n          ProductQuality IN (\\r\\n            'Super Premium',\\r\\n            'Premium Plus',\\r\\n            'Ultra Premium',\\r\\n            'Premium'\\r\\n          ),\\r\\n          Total_Sales,0\\r\\n        )\\r\\n      ) * 1.0\\r\\n    )\\r\\n    /\\r\\n    sum(Total_Sales\\r\\n        )"}],"all_columns":[],"percent_metrics":[],"adhoc_filters":[{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_ikvph3ptweb_w3ihyq3cb","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ProductCat NOT IN ('Şarap')","subject":null},{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_rmt32gd8a_cimhuosw4d","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","subject":null},{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_02b3841wyq1l_1get8gnl115","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ROTA NOT ILIKE '%LZM%'","subject":null},{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_nmhz4uq39nc_dhni2y2cyqs","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"FISCALYEAR >= toYear(today())\\r\\n","subject":null},{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_yqw59zn1wt_xk8rku3fxxm","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"FISCALMONTH >= toMonth(today())\\r\\n","subject":null}],"order_by_cols":[],"row_limit":100,"table_timestamp_format":"smart_date","allow_render_html":true,"column_config":{"Prem. Satış %":{"d3NumberFormat":".2%","d3SmallNumberFormat":".2%"}},"show_cell_bars":false,"color_pn":true,"comparison_color_scheme":"Green","conditional_formatting":[],"comparison_type":"values","extra_form_data":{},"force":false,"result_format":"json","result_type":"full"},"result_format":"json","result_type":"full"}
        """;

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7")
                .addHeader("Connection", "keep-alive")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=M4owMfjPEF4&dashboard_page_id=1J1r34hZDTtlUqPdX77xx&slice_id=532")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36")
                .addHeader("X-CSRFToken", "") // cURL'de 'X-CSRFToken;' boş geldiğinden boş gönderiyoruz
                .addHeader("Cookie", cookie)   // -b ile gönderdiğin session cookie burada
                .build();

        // Retry mekanizması
        int maxRetries = 3;
        IOException lastEx = null;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return new JSONObject(response.body().string());
                } else {
                    System.err.println("sendWidget14Request - HTTP " + response.code() + " (attempt " + attempt + ")");
                }
            } catch (IOException e) {
                lastEx = e;
                System.err.println("sendWidget14Request attempt " + attempt + " failed: " + e.getMessage());
            }
            try { Thread.sleep(300L); } catch (InterruptedException ignored) {}
        }

        throw new IOException("sendWidget14Request failed after " + maxRetries + " attempts", lastEx);
    }

    public static JSONObject sendWidget15Request() throws IOException {
        String cookie = ConfigurationReader.getProperty("cookie");

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(timeoutValue, TimeUnit.SECONDS)
                .readTimeout(timeoutValue, TimeUnit.SECONDS)
                .writeTimeout(timeoutValue, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A522%7D";

        // DİKKAT: where/sqlExpression içindeki satır sonları \\r\\n veya \\n olarak KAÇIŞLI yazıldı.
        String jsonBody = """
        {
          "datasource": { "id": 330, "type": "table" },
          "force": false,
          "queries": [
            {
              "filters": [],
              "extras": {
                "having": "",
                "where": "(makeDate(FISCALYEAR,FISCALMONTH,1) >= \\r\\n    if(toMonth(now()) >= 7,\\r\\n       addMonths(date_trunc('year', now()), 6),\\r\\n       addMonths(date_trunc('year', now()), -6))\\r\\n) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1])"
              },
              "applied_time_extras": {},
              "columns": [
                { "columnType": "BASE_AXIS", "datasourceWarning": false, "expressionType": "SQL", "label": "Ürün Kategori", "sqlExpression": "'Ürün Kategori'" },
                "ProductCat"
              ],
              "metrics": [
                {
                  "aggregate": null,
                  "column": null,
                  "datasourceWarning": false,
                  "expressionType": "SQL",
                  "hasCustomLabel": true,
                  "label": "Satış (L)",
                  "optionName": "metric_ectan2zdvy8_i6hm2sfdptq",
                  "sqlExpression": "SUM(Total_Sales)"
                }
              ],
              "orderby": [
                [
                  {
                    "aggregate": null,
                    "column": null,
                    "datasourceWarning": false,
                    "expressionType": "SQL",
                    "hasCustomLabel": false,
                    "label": "SUM(Total_Sales)",
                    "optionName": "metric_en6j3hwfkxk_g9klffngsog",
                    "sqlExpression": "SUM(Total_Sales)"
                  },
                  false
                ]
              ],
              "annotation_layers": [],
              "row_limit": 10000,
              "series_columns": ["ProductCat"],
              "series_limit": 0,
              "series_limit_metric": {
                "aggregate": null,
                "column": null,
                "datasourceWarning": false,
                "expressionType": "SQL",
                "hasCustomLabel": false,
                "label": "SUM(Total_Sales)",
                "optionName": "metric_en6j3hwfkxk_g9klffngsog",
                "sqlExpression": "SUM(Total_Sales)"
              },
              "order_desc": true,
              "url_params": { "slice_id": "522" },
              "custom_params": {},
              "custom_form_data": {},
              "time_offsets": [],
              "post_processing": [
                {
                  "operation": "pivot",
                  "options": {
                    "index": ["Ürün Kategori"],
                    "columns": ["ProductCat"],
                    "aggregates": {
                      "Satış (L)": { "operator": "mean" }
                    },
                    "drop_missing_columns": false
                  }
                },
                {
                  "operation": "rename",
                  "options": {
                    "columns": { "Satış (L)": null },
                    "level": 0,
                    "inplace": true
                  }
                },
                { "operation": "flatten" }
              ]
            }
          ],
          "form_data": {
            "datasource": "330__table",
            "viz_type": "echarts_timeseries_bar",
            "slice_id": 522,
            "url_params": { "slice_id": "522" },
            "x_axis": {
              "datasourceWarning": false,
              "expressionType": "SQL",
              "label": "Ürün Kategori",
              "sqlExpression": "'Ürün Kategori'"
            },
            "x_axis_sort_asc": true,
            "x_axis_sort_series": "name",
            "x_axis_sort_series_ascending": true,
            "metrics": [
              {
                "aggregate": null,
                "column": null,
                "datasourceWarning": false,
                "expressionType": "SQL",
                "hasCustomLabel": true,
                "label": "Satış (L)",
                "optionName": "metric_ectan2zdvy8_i6hm2sfdptq",
                "sqlExpression": "SUM(Total_Sales)"
              }
            ],
            "groupby": ["ProductCat"],
            "adhoc_filters": [
              {
                "clause": "WHERE",
                "comparator": null,
                "datasourceWarning": false,
                "expressionType": "SQL",
                "filterOptionName": "filter_ixgownj8ei_hecuhtjs62",
                "isExtra": false,
                "isNew": false,
                "operator": "TEMPORAL_RANGE",
                "sqlExpression": "makeDate(FISCALYEAR,FISCALMONTH,1) >= \\r\\n    if(toMonth(now()) >= 7,\\r\\n       addMonths(date_trunc('year', now()), 6),\\r\\n       addMonths(date_trunc('year', now()), -6))\\r\\n",
                "subject": "TRHISLEMTARIHI"
              },
              {
                "clause": "WHERE",
                "comparator": null,
                "datasourceWarning": false,
                "expressionType": "SQL",
                "filterOptionName": "filter_f4bm6lm48t9_x4e3xz5c7zg",
                "isExtra": false,
                "isNew": false,
                "operator": null,
                "sqlExpression": "splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]",
                "subject": null
              }
            ],
            "timeseries_limit_metric": {
              "aggregate": null,
              "column": null,
              "datasourceWarning": false,
              "expressionType": "SQL",
              "hasCustomLabel": false,
              "label": "SUM(Total_Sales)",
              "optionName": "metric_en6j3hwfkxk_g9klffngsog",
              "sqlExpression": "SUM(Total_Sales)"
            },
            "order_desc": true,
            "row_limit": 10000,
            "truncate_metric": true,
            "show_empty_columns": true,
            "comparison_type": "values",
            "annotation_layers": [],
            "forecastPeriods": 10,
            "forecastInterval": 0.8,
            "orientation": "vertical",
            "x_axis_title_margin": 15,
            "y_axis_title": "",
            "y_axis_title_margin": "45",
            "y_axis_title_position": "Left",
            "sort_series_type": "sum",
            "color_scheme": "bnbColors",
            "time_shift_color": true,
            "show_value": true,
            "only_total": true,
            "show_legend": true,
            "legendType": "scroll",
            "legendOrientation": "bottom",
            "legendMargin": 40,
            "x_axis_time_format": "smart_date",
            "xAxisLabelRotation": 0,
            "y_axis_format": "SMART_NUMBER",
            "logAxis": false,
            "y_axis_bounds": [null, null],
            "truncateXAxis": true,
            "rich_tooltip": true,
            "showTooltipTotal": true,
            "tooltipSortByMetric": false,
            "tooltipTimeFormat": "%d/%m/%Y",
            "extra_form_data": {},
            "force": false,
            "result_format": "json",
            "result_type": "full"
          },
          "result_format": "json",
          "result_type": "full"
        }
        """;

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Connection", "keep-alive")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=Vr4C6hVEdI4&slice_id=522")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36")
                // cURL'de header ismi gerçekten 'X-CSRFToken;' (noktalı virgüllü). Aynısını gönderelim:
                .addHeader("X-CSRFToken;", "")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Google Chrome\";v=\"140\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("Cookie", cookie)
                .build();

        int maxRetries = 3;
        IOException lastEx = null;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try (Response response = client.newCall(request).execute()) {
                String respBody = (response.body() != null) ? response.body().string() : null;
                if (response.isSuccessful() && respBody != null) {
                    return new JSONObject(respBody);
                } else {
                    System.err.println("sendWidget15Request - HTTP " + response.code() + " (attempt " + attempt + ")");
                    if (respBody != null) System.err.println("Response body: " + respBody);
                }
            } catch (IOException e) {
                lastEx = e;
                System.err.println("sendWidget15Request attempt " + attempt + " failed: " + e.getMessage());
            }
            try { Thread.sleep(300L); } catch (InterruptedException ignored) {}
        }

        throw new IOException("sendWidget15Request failed after " + maxRetries + " attempts", lastEx);
    }


    public static JSONObject sendWidget16Request() throws IOException {
        // Cookie'yi dışarıdan al (örn: "session=....")
        String cookie = ConfigurationReader.getProperty("cookie");

        // SSL doğrulamasını bypass eden client (lokal/test için) + timeoutlar
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(timeoutValue, TimeUnit.SECONDS)
                .readTimeout(timeoutValue, TimeUnit.SECONDS)
                .writeTimeout(timeoutValue, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        // URL (slice_id=523)
        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A523%7D";

        // Body (cURL'deki --data-raw birebir, \r\n kaçışlı)
        String jsonBody = """
        {"datasource":{"id":330,"type":"table"},"force":false,"queries":[{"filters":[],"extras":{"having":"","where":"(makeDate(FISCALYEAR,FISCALMONTH,1) >= \\r\\n    if(toMonth(now()) >= 7,\\r\\n       addMonths(date_trunc('year', now()), 6),\\r\\n       addMonths(date_trunc('year', now()), -6))\\r\\n) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1])"},"applied_time_extras":{},"columns":[{"expressionType":"SQL","label":"Kalite Segment","sqlExpression":"ProductQuality"}],"metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_rufg1yjrf89_xlaru84993l","sqlExpression":"SUM(Total_Sales)"}],"orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_rufg1yjrf89_xlaru84993l","sqlExpression":"SUM(Total_Sales)"},false]],"annotation_layers":[],"row_limit":100,"series_limit":0,"order_desc":true,"url_params":{"slice_id":"523"},"custom_params":{},"custom_form_data":{}}],"form_data":{"datasource":"330__table","viz_type":"pie","slice_id":523,"url_params":{"slice_id":"523"},"groupby":[{"expressionType":"SQL","label":"Kalite Segment","sqlExpression":"ProductQuality"}],"metric":{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_rufg1yjrf89_xlaru84993l","sqlExpression":"SUM(Total_Sales)"},"adhoc_filters":[{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_mjbqmduoopq_lnjm9tpfin","isExtra":false,"isNew":false,"operator":"TEMPORAL_RANGE","sqlExpression":"makeDate(FISCALYEAR,FISCALMONTH,1) >= \\r\\n    if(toMonth(now()) >= 7,\\r\\n       addMonths(date_trunc('year', now()), 6),\\r\\n       addMonths(date_trunc('year', now()), -6))\\r\\n","subject":"TRHISLEMTARIHI"},{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_fy3zmmp6mm6_n8n5guvcuxq","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","subject":null}],"row_limit":100,"sort_by_metric":true,"color_scheme":"supersetColors","show_labels_threshold":0.1,"show_legend":false,"legendType":"scroll","legendOrientation":"top","legendMargin":null,"label_type":"key_percent","number_format":"SMART_NUMBER","date_format":"smart_date","show_labels":true,"labels_outside":true,"label_line":true,"show_total":false,"outerRadius":60,"donut":true,"innerRadius":35,"extra_form_data":{},"force":false,"result_format":"json","result_type":"full"},"result_format":"json","result_type":"full"}
        """;

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Connection", "keep-alive")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=Xp02fQeA4Dc&slice_id=523")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "same-origin")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36")
                // cURL'de header adı 'X-CSRFToken;' (sonunda ';') — aynısını gönderiyoruz:
                .addHeader("X-CSRFToken;", "")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Google Chrome\";v=\"140\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("Cookie", cookie)
                .build();

        int maxRetries = 3;
        IOException lastEx = null;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try (Response response = client.newCall(request).execute()) {
                String respBody = response.body() != null ? response.body().string() : null;
                if (response.isSuccessful() && respBody != null) {
                    return new JSONObject(respBody);
                } else {
                    System.err.println("sendWidget16Request - HTTP " + response.code() + " (attempt " + attempt + ")");
                    if (respBody != null) System.err.println("Response body: " + respBody);
                }
            } catch (IOException e) {
                lastEx = e;
                System.err.println("sendWidget16Request attempt " + attempt + " failed: " + e.getMessage());
            }
            try { Thread.sleep(300L); } catch (InterruptedException ignored) {}
        }

        throw new IOException("sendWidget16Request failed after 3 attempts", lastEx);
    }


    // W17 (slice_id=530) – Marmara, cari ay/yıl için SM bazında İç Hedef & Gerçekleşme
    public static JSONObject sendWidget17Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A530%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .readTimeout(7, TimeUnit.SECONDS)
                .writeTimeout(7, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource":{"id":330,"type":"table"},
      "force":false,
      "queries":[
        {
          "filters":[],
          "extras":{
            "time_grain_sqla":"P1M",
            "having":"",
            "where":"(FISCALMONTH >= toMonth(today()) AND FISCALYEAR >= toYear(today())) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (ROTA NOT ILIKE '%LZM%')"
          },
          "applied_time_extras":{},
          "columns":[{"timeGrain":"P1M","columnType":"BASE_AXIS","datasourceWarning":false,"expressionType":"SQL","label":"SM","sqlExpression":"replaceAll(replaceAll(replaceAll(upperUTF8(SM), 'İ', 'I'),'Ç','C'),'Ğ','G')"}],
          "metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_jr2dj3aj59g_deudfhooy6","sqlExpression":"SUM(TotalTarget)"}],
          "annotation_layers":[],
          "row_limit":10000,
          "series_columns":[],
          "series_limit":0,
          "url_params":{"dashboard_page_id":"1J1r34hZDTtlUqPdX77xx","form_data_key":"VljXT89x8HY","slice_id":"530"},
          "custom_params":{},
          "custom_form_data":{},
          "time_offsets":[],
          "post_processing":[
            {"operation":"pivot","options":{"index":["SM"],"columns":[],"aggregates":{"İç Hedef":{"operator":"mean"}},"drop_missing_columns":true}},
            {"operation":"flatten"}
          ],
          "orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_jr2dj3aj59g_deudfhooy6","sqlExpression":"SUM(TotalTarget)"},false]]
        },
        {
          "filters":[],
          "extras":{
            "time_grain_sqla":"P1M",
            "having":"",
            "where":"(FISCALMONTH >= toMonth(today()) AND FISCALYEAR >= toYear(today())) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (ROTA NOT ILIKE '%LZM%')"
          },
          "applied_time_extras":{},
          "columns":[{"timeGrain":"P1M","columnType":"BASE_AXIS","datasourceWarning":false,"expressionType":"SQL","label":"SM","sqlExpression":"replaceAll(replaceAll(replaceAll(upperUTF8(SM), 'İ', 'I'),'Ç','C'),'Ğ','G')"}],
          "metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme","optionName":"metric_r6mwgbwrz3_bgajh3vv9mc","sqlExpression":"SUM(Total_Sales)"}],
          "annotation_layers":[],
          "row_limit":10000,
          "series_columns":[],
          "series_limit":0,
          "url_params":{"dashboard_page_id":"1J1r34hZDTtlUqPdX77xx","form_data_key":"VljXT89x8HY","slice_id":"530"},
          "custom_params":{},
          "custom_form_data":{},
          "time_offsets":[],
          "post_processing":[
            {"operation":"pivot","options":{"index":["SM"],"columns":[],"aggregates":{"Gerçekleşme":{"operator":"mean"}},"drop_missing_columns":true}},
            {"operation":"flatten"}
          ],
          "orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme","optionName":"metric_r6mwgbwrz3_bgajh3vv9mc","sqlExpression":"SUM(Total_Sales)"},false]]
        }
      ],
      "form_data":{
        "datasource":"330__table",
        "viz_type":"mixed_timeseries",
        "slice_id":530,
        "url_params":{"dashboard_page_id":"1J1r34hZDTtlUqPdX77xx","form_data_key":"VljXT89x8HY","slice_id":"530"},
        "x_axis":{"datasourceWarning":false,"expressionType":"SQL","label":"SM","sqlExpression":"replaceAll(replaceAll(replaceAll(upperUTF8(SM), 'İ', 'I'),'Ç','C'),'Ğ','G')"},
        "time_grain_sqla":"P1M",
        "metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_jr2dj3aj59g_deudfhooy6","sqlExpression":"SUM(TotalTarget)"}],
        "groupby":[],
        "adhoc_filters":[
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_gvzbk3gunkd_49y2aly44y7","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"FISCALMONTH >= toMonth(today()) AND FISCALYEAR >= toYear(today())","subject":null},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_jqtpyexvqm_s329xnq60va","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","subject":null},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_q44mz3gtgh_g2ki2msjef9","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ROTA NOT ILIKE '%LZM%'","subject":null}
        ],
        "order_desc":true,
        "row_limit":10000,
        "truncate_metric":true,
        "comparison_type":"values",
        "annotation_layers":[],
        "x_axis_title_margin":15,
        "y_axis_title":"",
        "y_axis_title_margin":"45",
        "y_axis_title_position":"Left",
        "color_scheme":"d3Category20",
        "time_shift_color":true,
        "seriesType":"middle",
        "area":false,
        "show_value":true,
        "opacity":0.2,
        "markerSize":6,
        "seriesTypeB":"bar",
        "areaB":false,
        "opacityB":0.2,
        "markerSizeB":6,
        "show_legend":false,
        "legendType":"scroll",
        "legendOrientation":"top",
        "legendMargin":null,
        "x_axis_time_format":"smart_date",
        "xAxisLabelRotation":45,
        "rich_tooltip":true,
        "showTooltipTotal":true,
        "tooltipTimeFormat":"%d/%m/%Y",
        "truncateXAxis":true,
        "y_axis_bounds":[null,null],
        "y_axis_format":"SMART_NUMBER",
        "y_axis_bounds_secondary":[null,null],
        "y_axis_format_secondary":"SMART_NUMBER",
        "extra_form_data":{},
        "force":false,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=VljXT89x8HY&dashboard_page_id=1J1r34hZDTtlUqPdX77xx&slice_id=530")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static JSONObject sendWidget18Request() throws IOException {
        // 1) Cookie: config'ten al
        String cookie = ConfigurationReader.getProperty("cookie");
        if (cookie == null || cookie.isEmpty()) {
            throw new IOException("Cookie boş: ConfigurationReader.getProperty(\"cookie\") bir değer döndürmedi.");
        }

        // 2) Insecure SSL + makul timeoutlar
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(timeoutValue, TimeUnit.SECONDS)
                .readTimeout(timeoutValue, TimeUnit.SECONDS)
                .writeTimeout(timeoutValue, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        // 3) URL
        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A458%7D";

        // 4) Body (cURL ile birebir)
        String jsonBody = """
        {"datasource":{"id":330,"type":"table"},"force":false,"queries":[{"filters":[],"extras":{"having":"","where":"(makeDate(FISCALYEAR,FISCALMONTH,1) >= DATE_TRUNC('month', NOW())\\r\\n) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1])"},"applied_time_extras":{},"columns":[{"expressionType":"SQL","label":"Ürün Kategori","sqlExpression":"ProductCat"}],"metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_pg02q4oc7zm_e8ka6ligme","sqlExpression":"SUM(Total_Sales)"}],"orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_pg02q4oc7zm_e8ka6ligme","sqlExpression":"SUM(Total_Sales)"},false]],"annotation_layers":[],"row_limit":100,"series_limit":0,"order_desc":true,"url_params":{"dashboard_page_id":"xqNMRzE0JaD1AHyRal6CQ","form_data_key":"Ziid9jU0fd8","save_action":"overwrite","slice_id":"458"},"custom_params":{},"custom_form_data":{}}],"form_data":{"datasource":"330__table","viz_type":"pie","slice_id":458,"url_params":{"dashboard_page_id":"xqNMRzE0JaD1AHyRal6CQ","form_data_key":"Ziid9jU0fd8","save_action":"overwrite","slice_id":"458"},"groupby":[{"expressionType":"SQL","label":"Ürün Kategori","sqlExpression":"ProductCat"}],"metric":{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_pg02q4oc7zm_e8ka6ligme","sqlExpression":"SUM(Total_Sales)"},"adhoc_filters":[{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_mjbqmduoopq_lnjm9tpfin","isExtra":false,"isNew":false,"operator":"TEMPORAL_RANGE","sqlExpression":"makeDate(FISCALYEAR,FISCALMONTH,1) >= DATE_TRUNC('month', NOW())\\r\\n","subject":"TRHISLEMTARIHI"},{"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_637qipoavcs_dq4wk78wvv5","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","subject":null}],"row_limit":100,"sort_by_metric":true,"color_scheme":"supersetColors","show_labels_threshold":0.1,"show_legend":false,"legendType":"scroll","legendOrientation":"top","legendMargin":null,"label_type":"key_percent","number_format":"SMART_NUMBER","date_format":"smart_date","show_labels":true,"labels_outside":true,"label_line":true,"show_total":false,"outerRadius":70,"donut":true,"innerRadius":39,"extra_form_data":{},"force":false,"result_format":"json","result_type":"full"},"result_format":"json","result_type":"full"}
        """;

        // 5) Request
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Connection", "keep-alive")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=Ziid9jU0fd8&dashboard_page_id=xqNMRzE0JaD1AHyRal6CQ&slice_id=458&save_action=overwrite")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36")
                // cURL'de olduğu gibi; anahtar adı 'X-CSRFToken;' (sonunda ; var)
                .addHeader("X-CSRFToken;", "")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Google Chrome\";v=\"140\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("Cookie", cookie)
                .build();

        // 6) Retry + hata gövdesi log
        int maxRetries = 3;
        IOException lastEx = null;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try (Response response = client.newCall(request).execute()) {
                String resp = response.body() != null ? response.body().string() : null;
                if (response.isSuccessful() && resp != null) {
                    return new JSONObject(resp);
                } else {
                    System.err.println("sendWidget18Request - HTTP " + response.code() + " (attempt " + attempt + ")");
                    if (resp != null) System.err.println("Response body: " + resp);
                }
            } catch (IOException e) {
                lastEx = e;
                System.err.println("sendWidget18Request attempt " + attempt + " failed: " + e.getMessage());
            }
            try { Thread.sleep(350L); } catch (InterruptedException ignored) {}
        }

        throw new IOException("sendWidget18Request failed after " + maxRetries + " attempts", lastEx);
    }


    // W19 (slice_id=459) – Cari ay (ay başından itibaren), Marmara filtresi, kalite segmente göre Satış (L) pasta
    public static JSONObject sendWidget19Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A459%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(timeoutValue, TimeUnit.SECONDS)
                .readTimeout(timeoutValue, TimeUnit.SECONDS)
                .writeTimeout(timeoutValue, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource":{"id":330,"type":"table"},
      "force":false,
      "queries":[
        {
          "filters":[],
          "extras":{
            "having":"",
            "where":"(makeDate(FISCALYEAR,FISCALMONTH,1) >= DATE_TRUNC('month', NOW())\\r\\n) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (ROTA NOT ILIKE '%LZM%')"
          },
          "applied_time_extras":{},
          "columns":[{"expressionType":"SQL","label":"Kalite Segment","sqlExpression":"ProductQuality"}],
          "metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_hbjwn2tnukl_uwr8j00154n","sqlExpression":"SUM(Total_Sales)"}],
          "orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_hbjwn2tnukl_uwr8j00154n","sqlExpression":"SUM(Total_Sales)"},false]],
          "annotation_layers":[],
          "row_limit":100,
          "series_limit":0,
          "order_desc":true,
          "url_params":{"dashboard_page_id":"xqNMRzE0JaD1AHyRal6CQ","form_data_key":"mMeptOYOT7A","slice_id":"459"},
          "custom_params":{},
          "custom_form_data":{}
        }
      ],
      "form_data":{
        "datasource":"330__table",
        "viz_type":"pie",
        "slice_id":459,
        "url_params":{"dashboard_page_id":"xqNMRzE0JaD1AHyRal6CQ","form_data_key":"mMeptOYOT7A","slice_id":"459"},
        "groupby":[{"expressionType":"SQL","label":"Kalite Segment","sqlExpression":"ProductQuality"}],
        "metric":{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_hbjwn2tnukl_uwr8j00154n","sqlExpression":"SUM(Total_Sales)"},
        "adhoc_filters":[
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_mjbqmduoopq_lnjm9tpfin","isExtra":false,"isNew":false,"operator":"TEMPORAL_RANGE","sqlExpression":"makeDate(FISCALYEAR,FISCALMONTH,1) >= DATE_TRUNC('month', NOW())\\r\\n","subject":"TRHISLEMTARIHI"},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_2bl015r3yj9_noejtydk5d","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","subject":null},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_kwaspqx9k4_4s6tyl033ta","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ROTA NOT ILIKE '%LZM%'","subject":null}
        ],
        "row_limit":100,
        "sort_by_metric":true,
        "color_scheme":"supersetColors",
        "show_labels_threshold":0.1,
        "show_legend":false,
        "legendType":"scroll",
        "legendOrientation":"top",
        "label_type":"key_percent",
        "number_format":"SMART_NUMBER",
        "date_format":"smart_date",
        "show_labels":true,
        "labels_outside":true,
        "label_line":true,
        "show_total":false,
        "outerRadius":70,
        "donut":true,
        "innerRadius":39,
        "extra_form_data":{},
        "force":false,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=mMeptOYOT7A&dashboard_page_id=xqNMRzE0JaD1AHyRal6CQ&slice_id=459")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget20Request() throws IOException {
        String cookie = ConfigurationReader.getProperty("cookie");

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A460%7D&force=true";

        String jsonBody = """
    {
      "datasource": { "id": 257, "type": "table" },
      "force": true,
      "queries": [
        {
          "filters": [
            { "col": "TRHISLEMTARIHI", "op": "TEMPORAL_RANGE", "val": "No filter" },
            { "col": "Urun_Kategori", "op": "NOT IN", "val": [""] },
            { "col": "TIP", "op": "NOT IN", "val": ["4", "ZM"] }
          ],
          "extras": {
            "time_grain_sqla": "P1W",
            "having": "",
            "where": "(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1] LIKE splitByChar(' ', upperUTF8(replaceAll(replaceAll('Marmara', 'ı', 'i'), 'İ', 'I')))[1])"
          },
          "applied_time_extras": {},
          "columns": [
            {
              "timeGrain": "P1W",
              "columnType": "BASE_AXIS",
              "sqlExpression": "TRHISLEMTARIHI",
              "label": "TRHISLEMTARIHI",
              "expressionType": "SQL"
            },
            {
              "expressionType": "SQL",
              "label": "Ürün Kategori",
              "sqlExpression": "Urun_Kategori"
            }
          ],
          "metrics": [
            {
              "aggregate": "SUM",
              "column": {
                "column_name": "Total_Sales"
              },
              "expressionType": "SIMPLE",
              "hasCustomLabel": true,
              "label": "Toplam Satış"
            }
          ],
          "orderby": [
            [
              {
                "aggregate": "SUM",
                "column": { "column_name": "Total_Sales" },
                "expressionType": "SIMPLE",
                "hasCustomLabel": true,
                "label": "Toplam Satış"
              },
              false
            ]
          ],
          "annotation_layers": [],
          "row_limit": 50000,
          "series_columns": [
            {
              "expressionType": "SQL",
              "label": "Ürün Kategori",
              "sqlExpression": "Urun_Kategori"
            }
          ],
          "series_limit": 0,
          "url_params": {
            "dashboard_page_id": "xqNMRzE0JaD1AHyRal6CQ",
            "slice_id": "460"
          },
          "post_processing": [
            {
              "operation": "pivot",
              "options": {
                "index": ["TRHISLEMTARIHI"],
                "columns": ["Ürün Kategori"],
                "aggregates": {
                  "Toplam Satış": { "operator": "mean" }
                },
                "drop_missing_columns": false
              }
            },
            {
              "operation": "rename",
              "options": {
                "columns": { "Toplam Satış": null },
                "level": 0,
                "inplace": true
              }
            },
            { "operation": "flatten" }
          ]
        }
      ],
      "form_data": {
        "datasource": "257__table",
        "viz_type": "echarts_area",
        "slice_id": 460,
        "url_params": {
          "dashboard_page_id": "xqNMRzE0JaD1AHyRal6CQ",
          "slice_id": "460"
        },
        "x_axis": "TRHISLEMTARIHI",
        "time_grain_sqla": "P1W",
        "metrics": [
          {
            "aggregate": "SUM",
            "column": { "column_name": "Total_Sales" },
            "expressionType": "SIMPLE",
            "hasCustomLabel": true,
            "label": "Toplam Satış"
          }
        ],
        "groupby": [
          {
            "expressionType": "SQL",
            "label": "Ürün Kategori",
            "sqlExpression": "Urun_Kategori"
          }
        ],
        "adhoc_filters": [],
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=KNuFQeGNeKI&dashboard_page_id=xqNMRzE0JaD1AHyRal6CQ&slice_id=460")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return new JSONObject(response.body().string());
            } else {
                throw new IOException("Request failed: " + response.code());
            }
        }
    }

    public static double sumCurrentMonthCategories(JSONObject w20JsonBody) {
        // Zaman dilimi: gerekirse kendi TZ’nize uyarlayın (örn. "Europe/Istanbul")
        ZoneId zone = ZoneId.systemDefault();
        YearMonth currentYM = YearMonth.now(zone);

        JSONArray result = w20JsonBody.getJSONArray("result");
        JSONObject first = result.getJSONObject(0);

        // Kategorilerin isimleri colnames içinde: 0. eleman tarih, geri kalanı kategoriler
        JSONArray colnames = first.getJSONArray("colnames"); // ["TRHISLEMTARIHI","Cin","Distile A. İçki",...]
        int firstCategoryIdx = 1; // 0 = TRHISLEMTARIHI

        JSONArray data = first.getJSONArray("data");

        double total = 0.0;
        boolean printedMonth = false;

        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.getJSONObject(i);

            // epoch millis JSON'da double geliyor, long'a çeviriyoruz
            long epochMs = (long) row.getDouble("TRHISLEMTARIHI");
            YearMonth rowYM = YearMonth.from(Instant.ofEpochMilli(epochMs).atZone(zone));

            if (rowYM.equals(currentYM)) {
                if (!printedMonth) {
                    System.out.println("İçinde bulunulan ay (sayısal): " + currentYM.getMonthValue());
                    printedMonth = true;
                }

                // O ay içindeki satır: tüm kategori kolonlarını topla (null olanları atla)
                for (int c = firstCategoryIdx; c < colnames.length(); c++) {
                    String col = colnames.getString(c);
                    if (row.has(col) && !row.isNull(col)) {
                        total += row.getDouble(col);
                    }
                }
            }
        }

        return total;
    }


    public static JSONObject sendWidget21Request() throws IOException {
        String cookie = ConfigurationReader.getProperty("cookie");

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A461%7D&force=true";

        String jsonBody = """
    {
      "datasource": { "id": 257, "type": "table" },
      "force": true,
      "queries": [
        {
          "filters": [
            { "col": "TRHISLEMTARIHI", "op": "TEMPORAL_RANGE", "val": "No filter" },
            { "col": "Quality_Segment", "op": "NOT IN", "val": [""] },
            { "col": "TIP", "op": "NOT IN", "val": ["4","ZM"] }
          ],
          "extras": {
            "time_grain_sqla": "P1W",
            "having": "",
            "where": "(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1])"
          },
          "applied_time_extras": {},
          "columns": [
            {
              "timeGrain": "P1W",
              "columnType": "BASE_AXIS",
              "sqlExpression": "TRHISLEMTARIHI",
              "label": "TRHISLEMTARIHI",
              "expressionType": "SQL"
            },
            {
              "datasourceWarning": false,
              "expressionType": "SQL",
              "label": "Kalite Segment",
              "sqlExpression": "Quality_Segment"
            }
          ],
          "metrics": [
            {
              "aggregate": "SUM",
              "column": {
                "advanced_data_type": null,
                "changed_on": "2024-12-05T06:47:07.971649",
                "column_name": "Total_Sales",
                "created_on": "2024-12-05T06:47:01.671351",
                "description": null,
                "expression": "",
                "extra": null,
                "filterable": true,
                "groupby": true,
                "id": 4809,
                "is_active": true,
                "is_dttm": false,
                "python_date_format": null,
                "type": "Nullable(Decimal(38, 14))",
                "type_generic": 0,
                "uuid": "304bef9b-93a2-4640-89a4-c1104767fabe",
                "verbose_name": null
              },
              "datasourceWarning": false,
              "expressionType": "SIMPLE",
              "hasCustomLabel": true,
              "label": "Toplam Satış",
              "optionName": "metric_50b6zsc76my_rli69u1cs9",
              "sqlExpression": null
            }
          ],
          "orderby": [
            [
              {
                "aggregate": "SUM",
                "column": {
                  "advanced_data_type": null,
                  "changed_on": "2024-12-05T06:47:07.971649",
                  "column_name": "Total_Sales",
                  "created_on": "2024-12-05T06:47:01.671351",
                  "description": null,
                  "expression": "",
                  "extra": null,
                  "filterable": true,
                  "groupby": true,
                  "id": 4809,
                  "is_active": true,
                  "is_dttm": false,
                  "python_date_format": null,
                  "type": "Nullable(Decimal(38, 14))",
                  "type_generic": 0,
                  "uuid": "304bef9b-93a2-4640-89a4-c1104767fabe",
                  "verbose_name": null
                },
                "datasourceWarning": false,
                "expressionType": "SIMPLE",
                "hasCustomLabel": true,
                "label": "Toplam Satış",
                "optionName": "metric_50b6zsc76my_rli69u1cs9",
                "sqlExpression": null
              },
              false
            ]
          ],
          "annotation_layers": [],
          "row_limit": 50000,
          "series_columns": [
            {
              "datasourceWarning": false,
              "expressionType": "SQL",
              "label": "Kalite Segment",
              "sqlExpression": "Quality_Segment"
            }
          ],
          "series_limit": 0,
          "order_desc": true,
          "url_params": { "dashboard_page_id": "xqNMRzE0JaD1AHyRal6CQ", "slice_id": "461" },
          "custom_params": {},
          "custom_form_data": {},
          "time_offsets": [],
          "post_processing": [
            {
              "operation": "pivot",
              "options": {
                "index": ["TRHISLEMTARIHI"],
                "columns": ["Kalite Segment"],
                "aggregates": { "Toplam Satış": { "operator": "mean" } },
                "drop_missing_columns": false
              }
            },
            {
              "operation": "rename",
              "options": { "columns": { "Toplam Satış": null }, "level": 0, "inplace": true }
            },
            { "operation": "flatten" }
          ]
        }
      ],
      "form_data": {
        "datasource": "257__table",
        "viz_type": "echarts_area",
        "slice_id": 461,
        "url_params": { "dashboard_page_id": "xqNMRzE0JaD1AHyRal6CQ", "slice_id": "461" },
        "x_axis": "TRHISLEMTARIHI",
        "time_grain_sqla": "P1W",
        "x_axis_sort_asc": true,
        "x_axis_sort_series": "name",
        "x_axis_sort_series_ascending": true,
        "metrics": [
          {
            "aggregate": "SUM",
            "column": {
              "advanced_data_type": null,
              "changed_on": "2024-12-05T06:47:07.971649",
              "column_name": "Total_Sales",
              "created_on": "2024-12-05T06:47:01.671351",
              "description": null,
              "expression": "",
              "extra": null,
              "filterable": true,
              "groupby": true,
              "id": 4809,
              "is_active": true,
              "is_dttm": false,
              "python_date_format": null,
              "type": "Nullable(Decimal(38, 14))",
              "type_generic": 0,
              "uuid": "304bef9b-93a2-4640-89a4-c1104767fabe",
              "verbose_name": null
            },
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "hasCustomLabel": true,
            "label": "Toplam Satış",
            "optionName": "metric_50b6zsc76my_rli69u1cs9",
            "sqlExpression": null
          }
        ],
        "groupby": [
          {
            "datasourceWarning": false,
            "expressionType": "SQL",
            "label": "Kalite Segment",
            "sqlExpression": "Quality_Segment"
          }
        ],
        "adhoc_filters": [
          {
            "clause": "WHERE",
            "comparator": "No filter",
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "filterOptionName": "filter_mjbqmduoopq_lnjm9tpfin",
            "isExtra": false,
            "isNew": false,
            "operator": "TEMPORAL_RANGE",
            "sqlExpression": null,
            "subject": "TRHISLEMTARIHI"
          },
          {
            "clause": "WHERE",
            "comparator": [""],
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "filterOptionName": "filter_ssa1v0avsr_nr4vdz5s3no",
            "isExtra": false,
            "isNew": false,
            "operator": "NOT IN",
            "operatorId": "NOT_IN",
            "sqlExpression": null,
            "subject": "Quality_Segment"
          },
          {
            "clause": "WHERE",
            "comparator": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "filterOptionName": "filter_qk5kdo6sdls_8b92j0p11c",
            "isExtra": false,
            "isNew": false,
            "operator": null,
            "sqlExpression": "splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]",
            "subject": null
          },
          {
            "clause": "WHERE",
            "comparator": ["4","ZM"],
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "filterOptionName": "filter_bsjd0s1kd39_a925y58jhi",
            "isExtra": false,
            "isNew": false,
            "operator": "NOT IN",
            "operatorId": "NOT_IN",
            "sqlExpression": null,
            "subject": "TIP"
          }
        ],
        "order_desc": true,
        "row_limit": 50000,
        "truncate_metric": true,
        "show_empty_columns": true,
        "comparison_type": "values",
        "annotation_layers": [],
        "forecastPeriods": 10,
        "forecastInterval": 0.8,
        "x_axis_title_margin": 15,
        "y_axis_title_margin": 15,
        "y_axis_title_position": "Left",
        "sort_series_type": "sum",
        "color_scheme": "supersetColors",
        "time_shift_color": true,
        "seriesType": "smooth",
        "opacity": 0.2,
        "only_total": true,
        "markerEnabled": true,
        "markerSize": 6,
        "show_legend": true,
        "legendType": "scroll",
        "legendOrientation": "bottom",
        "x_axis_time_format": "smart_date",
        "rich_tooltip": true,
        "showTooltipTotal": true,
        "tooltipTimeFormat": "smart_date",
        "y_axis_format": "SMART_NUMBER",
        "logAxis": false,
        "truncateXAxis": true,
        "y_axis_bounds": [null, null],
        "extra_form_data": {},
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=Cy8JDk_FB9M&dashboard_page_id=xqNMRzE0JaD1AHyRal6CQ&slice_id=461")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .build();

        int maxRetries = 3;
        IOException lastEx = null;

        for (int i = 1; i <= maxRetries; i++) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return new JSONObject(response.body().string());
                } else {
                    System.err.println("sendWidget21Request - HTTP " + response.code() + " (attempt " + i + ")");
                }
            } catch (IOException e) {
                lastEx = e;
                System.err.println("sendWidget21Request attempt " + i + " failed: " + e.getMessage());
            }
            try { Thread.sleep(300L); } catch (InterruptedException ignored) {}
        }

        throw new IOException("sendWidget21Request failed after " + maxRetries + " attempts", lastEx);
    }

    public static JSONObject sendWidget23Request() throws IOException {
        String cookie = ConfigurationReader.getProperty("cookie");

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A462%7D&force=true";

        String jsonBody = """
    {
      "datasource": { "id": 330, "type": "table" },
      "force": true,
      "queries": [
        {
          "filters": [],
          "extras": {
            "time_grain_sqla": "P1Y",
            "having": "",
            "where": "(FISCALYEAR >= toYear(today())\\r\\n) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (FISCALMONTH >= toMonth(today())\\r\\n) AND (ROTA NOT ILIKE '%LZM%')"
          },
          "applied_time_extras": {},
          "columns": [
            {
              "timeGrain": "P1Y",
              "columnType": "BASE_AXIS",
              "expressionType": "SQL",
              "label": "Ürün Kategori",
              "sqlExpression": "ProductCat"
            }
          ],
          "metrics": [
            {
              "aggregate": null,
              "column": null,
              "datasourceWarning": false,
              "expressionType": "SQL",
              "hasCustomLabel": true,
              "label": "İç Hedef",
              "optionName": "metric_ectan2zdvy8_i6hm2sfdptq",
              "sqlExpression": "SUM(TotalTarget)"
            }
          ],
          "annotation_layers": [],
          "row_limit": 10000,
          "series_columns": [],
          "series_limit": 0,
          "url_params": { "dashboard_page_id": "xqNMRzE0JaD1AHyRal6CQ", "slice_id": "462" },
          "custom_params": {},
          "custom_form_data": {},
          "time_offsets": [],
          "post_processing": [
            {
              "operation": "pivot",
              "options": {
                "index": ["Ürün Kategori"],
                "columns": [],
                "aggregates": { "İç Hedef": { "operator": "mean" } },
                "drop_missing_columns": true
              }
            },
            { "operation": "flatten" }
          ],
          "orderby": [
            [
              {
                "aggregate": null,
                "column": null,
                "datasourceWarning": false,
                "expressionType": "SQL",
                "hasCustomLabel": false,
                "label": "SUM(TotalTarget)",
                "optionName": "metric_en6j3hwfkxk_g9klffngsog",
                "sqlExpression": "SUM(TotalTarget)"
              },
              false
            ]
          ]
        },
        {
          "filters": [],
          "extras": {
            "time_grain_sqla": "P1Y",
            "having": "",
            "where": "(FISCALMONTH >= toMonth(today())\\r\\n) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (FISCALYEAR >= toYear(today())\\r\\n)"
          },
          "applied_time_extras": {},
          "columns": [
            {
              "timeGrain": "P1Y",
              "columnType": "BASE_AXIS",
              "expressionType": "SQL",
              "label": "Ürün Kategori",
              "sqlExpression": "ProductCat"
            }
          ],
          "metrics": [
            {
              "aggregate": null,
              "column": null,
              "datasourceWarning": false,
              "expressionType": "SQL",
              "hasCustomLabel": true,
              "label": "Gerçekleşme",
              "optionName": "metric_t0wxkdobs49_jl9w2cs9x1m",
              "sqlExpression": "SUM(Total_Sales)"
            }
          ],
          "annotation_layers": [],
          "row_limit": 10000,
          "series_columns": [],
          "series_limit": 0,
          "url_params": { "dashboard_page_id": "xqNMRzE0JaD1AHyRal6CQ", "slice_id": "462" },
          "custom_params": {},
          "custom_form_data": {},
          "time_offsets": [],
          "post_processing": [
            {
              "operation": "pivot",
              "options": {
                "index": ["Ürün Kategori"],
                "columns": [],
                "aggregates": { "Gerçekleşme": { "operator": "mean" } },
                "drop_missing_columns": true
              }
            },
            { "operation": "flatten" }
          ],
          "orderby": [
            [
              {
                "aggregate": null,
                "column": null,
                "datasourceWarning": false,
                "expressionType": "SQL",
                "hasCustomLabel": false,
                "label": "SUM(TotalTarget)",
                "optionName": "metric_lwamyrhf6s_07vftfl2t8nn",
                "sqlExpression": "SUM(TotalTarget)"
              },
              false
            ]
          ]
        }
      ],
      "form_data": {
        "datasource": "330__table",
        "viz_type": "mixed_timeseries",
        "slice_id": 462,
        "url_params": { "dashboard_page_id": "xqNMRzE0JaD1AHyRal6CQ", "slice_id": "462" },
        "x_axis": { "expressionType": "SQL", "label": "Ürün Kategori", "sqlExpression": "ProductCat" },
        "time_grain_sqla": "P1Y",
        "metrics": [
          {
            "aggregate": null,
            "column": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "hasCustomLabel": true,
            "label": "İç Hedef",
            "optionName": "metric_ectan2zdvy8_i6hm2sfdptq",
            "sqlExpression": "SUM(TotalTarget)"
          }
        ],
        "groupby": [],
        "adhoc_filters": [
          {
            "clause": "WHERE",
            "comparator": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "filterOptionName": "filter_ixgownj8ei_hecuhtjs62",
            "isExtra": false,
            "isNew": false,
            "operator": "TEMPORAL_RANGE",
            "sqlExpression": "FISCALYEAR >= toYear(today())\\r\\n",
            "subject": "TRHISLEMTARIHI"
          },
          {
            "clause": "WHERE",
            "comparator": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "filterOptionName": "filter_pula2h0s689_r5ynyrrz2sh",
            "isExtra": false,
            "isNew": false,
            "operator": null,
            "sqlExpression": "splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]",
            "subject": null
          },
          {
            "clause": "WHERE",
            "comparator": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "filterOptionName": "filter_udbvd316weo_2trsqhanu31",
            "isExtra": false,
            "isNew": false,
            "operator": null,
            "sqlExpression": "FISCALMONTH >= toMonth(today())\\r\\n",
            "subject": null
          },
          {
            "clause": "WHERE",
            "comparator": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "filterOptionName": "filter_eutahf9khj_tdau2rsra2",
            "isExtra": false,
            "isNew": false,
            "operator": null,
            "sqlExpression": "ROTA NOT ILIKE '%LZM%'",
            "subject": null
          }
        ],
        "timeseries_limit_metric": {
          "aggregate": null,
          "column": null,
          "datasourceWarning": false,
          "expressionType": "SQL",
          "hasCustomLabel": false,
          "label": "SUM(TotalTarget)",
          "optionName": "metric_en6j3hwfkxk_g9klffngsog",
          "sqlExpression": "SUM(TotalTarget)"
        },
        "order_desc": true,
        "row_limit": 10000,
        "truncate_metric": true,
        "comparison_type": "values",
        "annotation_layers": [],
        "x_axis_title_margin": 15,
        "y_axis_title": "Litre",
        "y_axis_title_margin": "45",
        "y_axis_title_position": "Left",
        "color_scheme": "googleCategory10c",
        "time_shift_color": true,
        "seriesType": "middle",
        "area": false,
        "show_value": true,
        "opacity": 0.2,
        "markerEnabled": false,
        "markerSize": 6,
        "seriesTypeB": "bar",
        "areaB": false,
        "opacityB": 0.2,
        "markerSizeB": 6,
        "show_legend": true,
        "legendType": "scroll",
        "legendOrientation": "bottom",
        "x_axis_time_format": "smart_date",
        "xAxisLabelRotation": 45,
        "rich_tooltip": true,
        "showTooltipTotal": true,
        "tooltipSortByMetric": false,
        "tooltipTimeFormat": "%d/%m/%Y",
        "truncateXAxis": true,
        "y_axis_bounds": [null, null],
        "y_axis_format": "SMART_NUMBER",
        "logAxis": false,
        "y_axis_bounds_secondary": [null, null],
        "y_axis_format_secondary": "SMART_NUMBER",
        "logAxisSecondary": false,
        "extra_form_data": {},
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=eQStRb7wuOg&dashboard_page_id=xqNMRzE0JaD1AHyRal6CQ&slice_id=462")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .build();

        int maxRetries = 3;
        IOException lastEx = null;

        for (int i = 1; i <= maxRetries; i++) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return new JSONObject(response.body().string());
                } else {
                    System.err.println("sendWidget23Request - HTTP " + response.code() + " (attempt " + i + ")");
                }
            } catch (IOException e) {
                lastEx = e;
                System.err.println("sendWidget23Request attempt " + i + " failed: " + e.getMessage());
            }
            try { Thread.sleep(300L); } catch (InterruptedException ignored) {}
        }

        throw new IOException("sendWidget23Request failed after " + maxRetries + " attempts", lastEx);
    }



    public static JSONObject sendWidget24Request() throws IOException {
        String cookie = ConfigurationReader.getProperty("cookie");

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(timeoutValue, TimeUnit.SECONDS)
                .readTimeout(timeoutValue, TimeUnit.SECONDS)
                .writeTimeout(timeoutValue, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A463%7D&force=true";

        String jsonBody = """
    {
      "datasource": { "id": 330, "type": "table" },
      "force": true,
      "queries": [
        {
          "filters": [],
          "extras": {
            "having": "",
            "where": "(FISCALYEAR >= toYear(today())\\r\\n) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (FISCALMONTH >= toMonth(today())\\r\\n) AND (ROTA NOT ILIKE '%LZM%')"
          },
          "applied_time_extras": {},
          "columns": [
            {
              "columnType": "BASE_AXIS",
              "sqlExpression": "ProductQuality",
              "label": "ProductQuality",
              "expressionType": "SQL"
            }
          ],
          "metrics": [
            {
              "expressionType": "SQL",
              "label": "İç Hedef",
              "hasCustomLabel": true,
              "sqlExpression": "SUM(TotalTarget)"
            }
          ],
          "post_processing": [
            {
              "operation": "pivot",
              "options": {
                "index": ["ProductQuality"],
                "columns": [],
                "aggregates": { "İç Hedef": { "operator": "mean" } },
                "drop_missing_columns": true
              }
            },
            { "operation": "flatten" }
          ],
          "orderby": [
            [
              {
                "expressionType": "SQL",
                "label": "SUM(TotalTarget)",
                "sqlExpression": "SUM(TotalTarget)"
              },
              false
            ]
          ],
          "row_limit": 10000
        },
        {
          "filters": [],
          "extras": {
            "having": "",
            "where": "(FISCALMONTH >= toMonth(today())\\r\\n) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (FISCALYEAR >= toYear(today())\\r\\n) AND (ROTA NOT ILIKE '%LZM%')"
          },
          "applied_time_extras": {},
          "columns": [
            {
              "columnType": "BASE_AXIS",
              "sqlExpression": "ProductQuality",
              "label": "ProductQuality",
              "expressionType": "SQL"
            }
          ],
          "metrics": [
            {
              "expressionType": "SQL",
              "label": "Gerçekleşme",
              "hasCustomLabel": true,
              "sqlExpression": "SUM(Total_Sales)"
            }
          ],
          "post_processing": [
            {
              "operation": "pivot",
              "options": {
                "index": ["ProductQuality"],
                "columns": [],
                "aggregates": { "Gerçekleşme": { "operator": "mean" } },
                "drop_missing_columns": true
              }
            },
            { "operation": "flatten" }
          ],
          "orderby": [
            [
              {
                "expressionType": "SQL",
                "label": "SUM(TotalTarget)",
                "sqlExpression": "SUM(TotalTarget)"
              },
              false
            ]
          ],
          "row_limit": 10000
        }
      ],
      "form_data": {
        "datasource": "330__table",
        "viz_type": "mixed_timeseries",
        "slice_id": 463,
        "url_params": {
          "dashboard_page_id": "xqNMRzE0JaD1AHyRal6CQ",
          "slice_id": "463"
        },
        "x_axis": "ProductQuality",
        "metrics": [
          {
            "expressionType": "SQL",
            "label": "İç Hedef",
            "hasCustomLabel": true,
            "sqlExpression": "SUM(TotalTarget)"
          }
        ],
        "row_limit": 10000,
        "order_desc": true,
        "truncate_metric": true,
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=2f4W_FxUSkI&dashboard_page_id=xqNMRzE0JaD1AHyRal6CQ&slice_id=463")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .build();

        int maxRetries = 3;
        IOException lastEx = null;

        for (int i = 1; i <= maxRetries; i++) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return new JSONObject(response.body().string());
                } else {
                    System.err.println("sendWidget24Request - HTTP " + response.code() + " (attempt " + i + ")");
                }
            } catch (IOException e) {
                lastEx = e;
                System.err.println("sendWidget24Request attempt " + i + " failed: " + e.getMessage());
            }
            try { Thread.sleep(300L); } catch (InterruptedException ignored) {}
        }

        throw new IOException("sendWidget24Request failed after " + maxRetries + " attempts", lastEx);
    }

    public static JSONObject sendWidget25Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(timeoutValue, TimeUnit.SECONDS)
                .readTimeout(timeoutValue, TimeUnit.SECONDS)
                .writeTimeout(timeoutValue, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie"); // DIA cookie

        // DİKKAT: Burada kesinlikle String.format/.formatted kullanılmıyor.
        String bodyStr = """
{
  "datasource": {"id": 375, "type": "table"},
  "force": false,
  "queries": [
    {
      "filters": [],
      "extras": {
        "time_grain_sqla": "P1D",
        "having": "",
        "where": "(TRHISLEMTARIHI >= today() - 1) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\nLIKE \\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\n      'Marmara'\\n    , 'ı', 'i'), 'İ', 'I')))[1])"
      },
      "applied_time_extras": {},
      "columns": [
        {
          "timeGrain": "P1D",
          "columnType": "BASE_AXIS",
          "datasourceWarning": false,
          "expressionType": "SQL",
          "label": "Column",
          "sqlExpression": "TRHISLEMTARIHI"
        }
      ],
      "metrics": [
        {
          "aggregate": null,
          "column": null,
          "datasourceWarning": false,
          "expressionType": "SQL",
          "hasCustomLabel": true,
          "label": "İç Hedef",
          "optionName": "metric_sghnx7sduc8_lyz8geip0o",
          "sqlExpression": "CASE WHEN SUM(Distinct DailyTarget) < 0 THEN 0 ELSE SUM(Distinct DailyTarget) END"
        },
        {
          "aggregate": "SUM",
          "column": {
            "advanced_data_type": null,
            "changed_on": "2025-09-14T02:58:38.127382",
            "column_name": "OrdersNHotSales",
            "created_on": "2025-09-14T02:58:38.127380",
            "description": null,
            "expression": null,
            "extra": "{\\"warning_markdown\\":null}",
            "filterable": true,
            "groupby": true,
            "id": 8399,
            "is_active": true,
            "is_dttm": false,
            "python_date_format": null,
            "type": "Decimal(38, 14)",
            "type_generic": 0,
            "uuid": "29c377c6-6041-4080-9dc6-f631bbff26e5",
            "verbose_name": null
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Satış (L)",
          "optionName": "metric_16l610pstum_wchr40urtu",
          "sqlExpression": null
        }
      ],
      "orderby": [
        [
          {
            "aggregate": null,
            "column": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "hasCustomLabel": true,
            "label": "İç Hedef",
            "optionName": "metric_sghnx7sduc8_lyz8geip0o",
            "sqlExpression": "CASE WHEN SUM(Distinct DailyTarget) < 0 THEN 0 ELSE SUM(Distinct DailyTarget) END"
          },
          false
        ]
      ],
      "annotation_layers": [],
      "row_limit": 10000,
      "series_columns": [],
      "series_limit": 0,
      "order_desc": true,
      "url_params": {
        "dashboard_page_id": "kSc6pUD3su3tG4AiF9e_t",
        "slice_id": "162"
      },
      "custom_params": {},
      "custom_form_data": {},
      "time_offsets": [],
      "post_processing": [
        {
          "operation": "pivot",
          "options": {
            "index": ["Column"],
            "columns": [],
            "aggregates": {
              "İç Hedef": { "operator": "mean" },
              "Satış (L)": { "operator": "mean" }
            },
            "drop_missing_columns": false
          }
        },
        { "operation": "flatten" }
      ]
    }
  ],
  "form_data": {
    "datasource": "375__table",
    "viz_type": "echarts_timeseries_bar",
    "slice_id": 162,
    "x_axis": {
      "datasourceWarning": false,
      "expressionType": "SQL",
      "label": "Column",
      "sqlExpression": "TRHISLEMTARIHI"
    },
    "time_grain_sqla": "P1D",
    "x_axis_sort_series": "name",
    "x_axis_sort_series_ascending": true,
    "metrics": [
      {
        "aggregate": null,
        "column": null,
        "datasourceWarning": false,
        "expressionType": "SQL",
        "hasCustomLabel": true,
        "label": "İç Hedef",
        "optionName": "metric_sghnx7sduc8_lyz8geip0o",
        "sqlExpression": "CASE WHEN SUM(Distinct DailyTarget) < 0 THEN 0 ELSE SUM(Distinct DailyTarget) END"
      },
      {
        "aggregate": "SUM",
        "column": {
          "advanced_data_type": null,
          "changed_on": "2025-09-14T02:58:38.127382",
          "column_name": "OrdersNHotSales",
          "created_on": "2025-09-14T02:58:38.127380",
          "description": null,
          "expression": null,
          "extra": "{\\"warning_markdown\\":null}",
          "filterable": true,
          "groupby": true,
          "id": 8399,
          "is_active": true,
          "is_dttm": false,
          "python_date_format": null,
          "type": "Decimal(38, 14)",
          "type_generic": 0,
          "uuid": "29c377c6-6041-4080-9dc6-f631bbff26e5",
          "verbose_name": null
        },
        "datasourceWarning": false,
        "expressionType": "SIMPLE",
        "hasCustomLabel": true,
        "label": "Satış (L)",
        "optionName": "metric_16l610pstum_wchr40urtu",
        "sqlExpression": null
      }
    ],
    "groupby": [],
    "adhoc_filters": [
      {
        "expressionType": "SQL",
        "sqlExpression": "TRHISLEMTARIHI >= today() - 1",
        "clause": "WHERE",
        "subject": "TRHISLEMTARIHI",
        "operator": "TEMPORAL_RANGE",
        "operatorId": "TEMPORAL_RANGE",
        "comparator": null,
        "isExtra": false,
        "isNew": false,
        "datasourceWarning": false,
        "filterOptionName": "filter_4qlcx7z0p6r_g4ndh7e130t"
      },
      {
        "expressionType": "SQL",
        "sqlExpression": "splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\nLIKE \\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\n      'Marmara'\\n    , 'ı', 'i'), 'İ', 'I')))[1]",
        "clause": "WHERE",
        "subject": null,
        "operator": null,
        "comparator": null,
        "isExtra": false,
        "isNew": false,
        "datasourceWarning": false,
        "filterOptionName": "filter_fd9nx1igt07_35ubhujpugz"
      }
    ],
    "row_limit": 10000,
    "truncate_metric": true,
    "show_empty_columns": true,
    "comparison_type": "values",
    "annotation_layers": [],
    "forecastPeriods": 10,
    "forecastInterval": 0.8,
    "orientation": "vertical",
    "x_axis_title_margin": 15,
    "y_axis_title": "",
    "y_axis_title_margin": "45",
    "y_axis_title_position": "Left",
    "sort_series_type": "name",
    "sort_series_ascending": true,
    "color_scheme": "bnbColors",
    "time_shift_color": true,
    "show_value": true,
    "stack": null,
    "only_total": true,
    "show_legend": true,
    "legendType": "scroll",
    "legendOrientation": "bottom",
    "x_axis_time_format": "%d/%m/%Y",
    "xAxisLabelRotation": 0,
    "y_axis_format": "SMART_NUMBER",
    "truncateYAxis": false,
    "y_axis_bounds": [null, null],
    "truncateXAxis": true,
    "rich_tooltip": true,
    "showTooltipTotal": true,
    "tooltipTimeFormat": "%d/%m/%Y",
    "extra_form_data": {},
    "force": false,
    "result_format": "json",
    "result_type": "full"
  },
  "result_format": "json",
  "result_type": "full"
}
""";

        RequestBody body = RequestBody.create(bodyStr, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A162%7D")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return new JSONObject(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static JSONObject sendWidget42Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A380%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource":{"id":209,"type":"table"},
  "force":true,
  "queries":[
    {
      "filters":[{"col":"DISTRIBUTOR_KOD","op":"NOT IN","val":["EDIRNE MAHALO"]}],
      "extras":{"having":"","where":""},
      "applied_time_extras":{},
      "columns":[{"datasourceWarning":false,"expressionType":"SQL","label":"Marka","sqlExpression":"URUN_TIP_ACIKLAMA"}],
      "metrics":[
        {
          "aggregate":null,
          "column":null,
          "datasourceWarning":false,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Kalan Stok Gün",
          "optionName":"metric_t2nf5uauaar_heczve27fu",
          "sqlExpression":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n"
        }
      ],
      "orderby":[
        [
          {
            "aggregate":null,
            "column":null,
            "datasourceWarning":false,
            "expressionType":"SQL",
            "hasCustomLabel":false,
            "label":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n",
            "optionName":"metric_4n0u3ciixyr_0u06yz56st0g",
            "sqlExpression":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n"
          },
          true
        ]
      ],
      "annotation_layers":[],
      "row_limit":5,
      "series_limit":0,
      "series_limit_metric":{
        "aggregate":null,
        "column":null,
        "datasourceWarning":false,
        "expressionType":"SQL",
        "hasCustomLabel":false,
        "label":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n",
        "optionName":"metric_4n0u3ciixyr_0u06yz56st0g",
        "sqlExpression":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n"
      },
      "order_desc":false,
      "url_params":{"dashboard_page_id":"RQU5lFjUavfRCcebEFYwP","slice_id":"380"},
      "custom_params":{},
      "custom_form_data":{},
      "post_processing":[],
      "time_offsets":[]
    }
  ],
  "form_data":{
    "datasource":"209__table",
    "viz_type":"table",
    "slice_id":380,
    "url_params":{"dashboard_page_id":"RQU5lFjUavfRCcebEFYwP","slice_id":"380"},
    "query_mode":"aggregate",
    "groupby":[{"datasourceWarning":false,"expressionType":"SQL","label":"Marka","sqlExpression":"URUN_TIP_ACIKLAMA"}],
    "temporal_columns_lookup":{},
    "metrics":[
      {
        "aggregate":null,
        "column":null,
        "datasourceWarning":false,
        "expressionType":"SQL",
        "hasCustomLabel":true,
        "label":"Kalan Stok Gün",
        "optionName":"metric_t2nf5uauaar_heczve27fu",
        "sqlExpression":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n"
      }
    ],
    "all_columns":[],
    "percent_metrics":[],
    "adhoc_filters":[
      {
        "clause":"WHERE",
        "comparator":["EDIRNE MAHALO"],
        "datasourceWarning":false,
        "expressionType":"SIMPLE",
        "filterOptionName":"filter_bo2gs628ayv_rd9b9mn71w",
        "isExtra":false,
        "isNew":false,
        "operator":"NOT IN",
        "operatorId":"NOT_IN",
        "sqlExpression":null,
        "subject":"DISTRIBUTOR_KOD"
      }
    ],
    "timeseries_limit_metric":{
      "aggregate":null,
      "column":null,
      "datasourceWarning":false,
      "expressionType":"SQL",
      "hasCustomLabel":false,
      "label":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n",
      "optionName":"metric_4n0u3ciixyr_0u06yz56st0g",
      "sqlExpression":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n"
    },
    "order_by_cols":[],
    "order_desc":false,
    "row_limit":"5",
    "table_timestamp_format":"smart_date",
    "allow_render_html":true,
    "show_cell_bars":true,
    "color_pn":true,
    "comparison_color_scheme":"Green",
    "conditional_formatting":[],
    "comparison_type":"values",
    "extra_form_data":{},
    "force":true,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=bOwPByLDjbg&dashboard_page_id=RQU5lFjUavfRCcebEFYwP&slice_id=380")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static JSONObject sendWidget43AggreationS56Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A376%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        // Not: Bu sorguda AY/YIL yok. Eğer tablo destekliyorsa aşağıdaki OPSİYONEL bloğa bakın.
        String body = """
{
  "datasource":{"id":209,"type":"table"},
  "force":false,
  "queries":[
    {
      "filters":[
        {"col":"DISTRIBUTOR_KOD","op":"NOT IN","val":["EDIRNE MAHALO"]}
      ],
      "extras":{"time_grain_sqla":"P1D","having":"","where":""},
      "applied_time_extras":{},
      "columns":[
        {"expressionType":"SQL","label":"Ürün Kısa Ad","sqlExpression":"URUN_KISA_AD"}
      ],
      "metrics":[
        {
          "expressionType":"SQL",
          "sqlExpression":"    SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n    /\\r\\n    SUM(\\r\\n       CAST(\\r\\n         CASE\\r\\n           WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n               THEN 0\\r\\n               ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n         END\\r\\n         AS FLOAT\\r\\n       )\\r\\n    ) * 1.0 \\r\\n",
          "column":null,"aggregate":null,"datasourceWarning":false,
          "hasCustomLabel":true,"label":"Kalan Stok Gün",
          "optionName":"metric_9qo7z6huo08_4rdu8tfhocu"
        }
      ],
      "orderby":[
        [
          {
            "expressionType":"SQL",
            "sqlExpression":"    SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n    /\\r\\n    SUM(\\r\\n       CAST(\\r\\n         CASE\\r\\n           WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n               THEN 0\\r\\n               ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n         END\\r\\n         AS FLOAT\\r\\n       )\\r\\n    ) * 1.0 \\r\\n",
            "column":null,"aggregate":null,"datasourceWarning":false,
            "hasCustomLabel":true,"label":"Kalan Stok Gün",
            "optionName":"metric_9qo7z6huo08_4rdu8tfhocu"
          },
          false
        ]
      ],
      "annotation_layers":[],
      "row_limit":100000,
      "series_limit":0,
      "order_desc":true,
      "url_params":{"dashboard_page_id":"RQU5lFjUavfRCcebEFYwP","form_data_key":"g-kk0LNmc6U","slice_id":"376"},
      "custom_params":{},
      "custom_form_data":{},
      "post_processing":[],
      "time_offsets":[]
    }
  ],
  "form_data":{
    "datasource":"209__table",
    "viz_type":"table",
    "slice_id":376,
    "url_params":{"dashboard_page_id":"RQU5lFjUavfRCcebEFYwP","form_data_key":"g-kk0LNmc6U","slice_id":"376"},
    "query_mode":"aggregate",
    "groupby":[
      {"expressionType":"SQL","label":"Ürün Kısa Ad","sqlExpression":"URUN_KISA_AD"}
    ],
    "time_grain_sqla":"P1D",
    "temporal_columns_lookup":{},
    "metrics":[
      {
        "expressionType":"SQL",
        "sqlExpression":"    SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n    /\\r\\n    SUM(\\r\\n       CAST(\\r\\n         CASE\\r\\n           WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n               THEN 0\\r\\n               ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n         END\\r\\n         AS FLOAT\\r\\n       )\\r\\n    ) * 1.0 \\r\\n",
        "column":null,"aggregate":null,"datasourceWarning":false,
        "hasCustomLabel":true,"label":"Kalan Stok Gün",
        "optionName":"metric_9qo7z6huo08_4rdu8tfhocu"
      }
    ],
    "all_columns":[],
    "percent_metrics":[],
    "adhoc_filters":[
      {
        "clause":"WHERE","comparator":["EDIRNE MAHALO"],"datasourceWarning":false,
        "expressionType":"SIMPLE","filterOptionName":"filter_xxpk6dan4db_yrds5ko880b",
        "isExtra":false,"isNew":false,"operator":"NOT IN","operatorId":"NOT_IN",
        "sqlExpression":null,"subject":"DISTRIBUTOR_KOD"
      }
    ],
    "order_by_cols":[],
    "row_limit":"100000",
    "table_timestamp_format":"smart_date",
    "include_search":true,
    "allow_render_html":true,
    "column_config":{
      "Günlük Ortalama Litre Satış":{"d3NumberFormat":",.2f","d3SmallNumberFormat":",.2f"},
      "Günlük Ortalama Şişe Satış":{"d3NumberFormat":",.2f","d3SmallNumberFormat":",.2f"},
      "Kalan Stok Gün":{"d3NumberFormat":",.2f","d3SmallNumberFormat":",.2f"},
      "Litre Stok":{"d3NumberFormat":",.2f","d3SmallNumberFormat":",.2f"}
    },
    "show_cell_bars":false,
    "color_pn":true,
    "comparison_color_scheme":"Green",
    "conditional_formatting":[],
    "comparison_type":"values",
    "extra_form_data":{},
    "force":false,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=g-kk0LNmc6U&dashboard_page_id=RQU5lFjUavfRCcebEFYwP&slice_id=376")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget43AggreationS57Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A376%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource":{"id":209,"type":"table"},
  "force":false,
  "queries":[
    {
      "filters":[{"col":"DISTRIBUTOR_KOD","op":"NOT IN","val":["EDIRNE MAHALO"]}],
      "extras":{"time_grain_sqla":"P1D","having":"","where":""},
      "applied_time_extras":{},
      "columns":[{"label":"Marka","sqlExpression":"URUN_TIP_ACIKLAMA","expressionType":"SQL"}],
      "metrics":[
        {
          "expressionType":"SQL",
          "sqlExpression":"    SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n    /\\r\\n    SUM(\\r\\n       CAST(\\r\\n         CASE\\r\\n           WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n               THEN 0\\r\\n               ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n         END\\r\\n         AS FLOAT\\r\\n       )\\r\\n    ) * 1.0 \\r\\n",
          "column":null,
          "aggregate":null,
          "datasourceWarning":false,
          "hasCustomLabel":true,
          "label":"Kalan Stok Gün",
          "optionName":"metric_9qo7z6huo08_4rdu8tfhocu"
        }
      ],
      "orderby":[
        [
          {
            "expressionType":"SQL",
            "sqlExpression":"    SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n    /\\r\\n    SUM(\\r\\n       CAST(\\r\\n         CASE\\r\\n           WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n               THEN 0\\r\\n               ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n         END\\r\\n         AS FLOAT\\r\\n       )\\r\\n    ) * 1.0 \\r\\n",
            "column":null,
            "aggregate":null,
            "datasourceWarning":false,
            "hasCustomLabel":true,
            "label":"Kalan Stok Gün",
            "optionName":"metric_9qo7z6huo08_4rdu8tfhocu"
          },
          false
        ]
      ],
      "annotation_layers":[],
      "row_limit":100000,
      "series_limit":0,
      "order_desc":true,
      "url_params":{"dashboard_page_id":"RQU5lFjUavfRCcebEFYwP","form_data_key":"g-kk0LNmc6U","slice_id":"376"},
      "custom_params":{},
      "custom_form_data":{},
      "post_processing":[],
      "time_offsets":[]
    }
  ],
  "form_data":{
    "datasource":"209__table",
    "viz_type":"table",
    "slice_id":376,
    "url_params":{"dashboard_page_id":"RQU5lFjUavfRCcebEFYwP","form_data_key":"g-kk0LNmc6U","slice_id":"376"},
    "query_mode":"aggregate",
    "groupby":[{"label":"Marka","sqlExpression":"URUN_TIP_ACIKLAMA","expressionType":"SQL"}],
    "time_grain_sqla":"P1D",
    "temporal_columns_lookup":{},
    "metrics":[
      {
        "expressionType":"SQL",
        "sqlExpression":"    SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n    /\\r\\n    SUM(\\r\\n       CAST(\\r\\n         CASE\\r\\n           WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n               THEN 0\\r\\n               ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n         END\\r\\n         AS FLOAT\\r\\n       )\\r\\n    ) * 1.0 \\r\\n",
        "column":null,
        "aggregate":null,
        "datasourceWarning":false,
        "hasCustomLabel":true,
        "label":"Kalan Stok Gün",
        "optionName":"metric_9qo7z6huo08_4rdu8tfhocu"
      }
    ],
    "all_columns":[],
    "percent_metrics":[],
    "adhoc_filters":[
      {
        "clause":"WHERE",
        "comparator":["EDIRNE MAHALO"],
        "datasourceWarning":false,
        "expressionType":"SIMPLE",
        "filterOptionName":"filter_xxpk6dan4db_yrds5ko880b",
        "isExtra":false,
        "isNew":false,
        "operator":"NOT IN",
        "operatorId":"NOT_IN",
        "sqlExpression":null,
        "subject":"DISTRIBUTOR_KOD"
      }
    ],
    "order_by_cols":[],
    "row_limit":"100000",
    "table_timestamp_format":"smart_date",
    "include_search":true,
    "allow_render_html":true,
    "column_config":{
      "Günlük Ortalama Litre Satış":{"d3NumberFormat":",.2f","d3SmallNumberFormat":",.2f"},
      "Günlük Ortalama Şişe Satış":{"d3NumberFormat":",.2f","d3SmallNumberFormat":",.2f"},
      "Kalan Stok Gün":{"d3NumberFormat":",.2f","d3SmallNumberFormat":",.2f"}
    },
    "show_cell_bars":false,
    "color_pn":true,
    "comparison_color_scheme":"Green",
    "conditional_formatting":[],
    "comparison_type":"values",
    "extra_form_data":{},
    "force":false,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=g-kk0LNmc6U&dashboard_page_id=RQU5lFjUavfRCcebEFYwP&slice_id=376")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    public static JSONObject sendWidget47Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(timeoutValue, TimeUnit.SECONDS)
                .readTimeout(timeoutValue, TimeUnit.SECONDS)
                .writeTimeout(timeoutValue, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        // İçinde bulunduğumuz yıl/ay
        int currentYear  = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        // Yer tutucular: ${YEAR} ve ${MONTH}
        String template = """
{
  "datasource": { "id": 381, "type": "table" },
  "force": true,
  "queries": [
    {
      "filters": [
        { "col": "FISCALYEAR", "op": "IN", "val": [${YEAR}] },
        { "col": "FISCALMONTH", "op": "IN", "val": [${MONTH}] }
      ],
      "extras": {
        "having": "",
        "where": "(1=1) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1] LIKE splitByChar(' ', upperUTF8(replaceAll(replaceAll('Marmara', 'ı', 'i'), 'İ', 'I')))[1]) AND (ROTA NOT ILIKE '%LZM%')"
      },
      "columns": [],
      "metrics": [
        {
          "expressionType": "SQL",
          "label": "Güncel Günlük Satılması Gereken",
          "sqlExpression": "(SUM(month_target) - SUM(ytd_sales)) / MAX(bd_remaining_from_today_inclusive)",
          "hasCustomLabel": true
        }
      ]
    }
  ],
  "form_data": {
    "datasource": "381__table",
    "viz_type": "big_number_total",
    "slice_id": 1814,
    "metric": {
      "expressionType": "SQL",
      "label": "Güncel Günlük Satılması Gereken",
      "sqlExpression": "(SUM(month_target) - SUM(ytd_sales)) / MAX(bd_remaining_from_today_inclusive)",
      "hasCustomLabel": true
    }
  },
  "result_format": "json",
  "result_type": "full"
}
""";

        String bodyStr = template
                .replace("${YEAR}", String.valueOf(currentYear))
                .replace("${MONTH}", String.valueOf(currentMonth));

        RequestBody body = RequestBody.create(bodyStr, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1814%7D&force=true")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return new JSONObject(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget101Request() throws IOException {
        String cookie = ConfigurationReader.getProperty("cookie");

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A352%7D&force=true";

        // İstersen BM’yi buradan parametrize edebilirsin:
        // String bm = "Marmara";
        // final String whereBM = "(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı','i'), 'İ','I')))[1] " +
        //        "LIKE splitByChar(' ', upperUTF8(replaceAll(replaceAll('" + bm + "', 'ı','i'), 'İ','I')))[1])";

        final String whereBM =
                "(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı','i'), 'İ','I')))[1] " +
                        "LIKE splitByChar(' ', upperUTF8(replaceAll(replaceAll('Marmara', 'ı','i'), 'İ','I')))[1])";

        String jsonBody = """
{
  "datasource": { "id": 335, "type": "table" },
  "force": true,
  "queries": [
    {
      "filters": [],
      "extras": {
        "having": "",
        "where": \"%s\"
      },
      "applied_time_extras": {},
      "columns": ["SM"],
      "metrics": [
        { "aggregate": "SUM", "column": { "column_name": "Cin", "id": 6916, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Cin" },
        { "aggregate": "SUM", "column": { "column_name": "Distile A. İçki", "id": 6918, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Distile A. İçki" },
        { "aggregate": "SUM", "column": { "column_name": "İthal Cin", "id": 6919, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "İthal Cin" },
        { "aggregate": "SUM", "column": { "column_name": "İthal Likör", "id": 6920, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "İthal Likör" },
        { "aggregate": "SUM", "column": { "column_name": "İthal Votka", "id": 6921, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "İthal Votka" },
        { "aggregate": "SUM", "column": { "column_name": "Likör", "id": 6922, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Likör" },
        { "aggregate": "SUM", "column": { "column_name": "Rakı", "id": 6923, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Rakı" },
        { "aggregate": "SUM", "column": { "column_name": "Rom", "id": 6924, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Rom" },
        { "aggregate": "SUM", "column": { "column_name": "Şarap", "id": 6925, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Şarap" },
        { "aggregate": "SUM", "column": { "column_name": "Tekila", "id": 6926, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Tekila" },
        { "aggregate": "SUM", "column": { "column_name": "Viski", "id": 6927, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Viski" },
        { "aggregate": "SUM", "column": { "column_name": "Votka", "id": 6928, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Votka" },
        { "aggregate": "SUM", "column": { "column_name": "Diğer", "id": 6917, "type": "Decimal(38, 14)", "filterable": true, "groupby": true }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Diğer" }
      ],
      "orderby": [
        [
          { "aggregate": "SUM", "column": { "column_name": "Cin", "id": 6916, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Cin" },
          false
        ]
      ],
      "annotation_layers": [],
      "row_limit": 1000,
      "series_limit": 0,
      "order_desc": true,
      "url_params": { "dashboard_page_id": "hShkLIJ2wQpc2K4rOnTqK", "slice_id": "352" },
      "custom_params": {},
      "custom_form_data": {}
    }
  ],
  "form_data": {
    "datasource": "335__table",
    "viz_type": "pivot_table_v2",
    "slice_id": 352,
    "url_params": { "dashboard_page_id": "hShkLIJ2wQpc2K4rOnTqK", "slice_id": "352" },
    "groupbyColumns": [],
    "groupbyRows": ["SM"],
    "temporal_columns_lookup": {},
    "metrics": [
      { "aggregate": "SUM", "column": { "column_name": "Cin", "id": 6916, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Cin" },
      { "aggregate": "SUM", "column": { "column_name": "Distile A. İçki", "id": 6918, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Distile A. İçki" },
      { "aggregate": "SUM", "column": { "column_name": "İthal Cin", "id": 6919, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "İthal Cin" },
      { "aggregate": "SUM", "column": { "column_name": "İthal Likör", "id": 6920, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "İthal Likör" },
      { "aggregate": "SUM", "column": { "column_name": "İthal Votka", "id": 6921, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "İthal Votka" },
      { "aggregate": "SUM", "column": { "column_name": "Likör", "id": 6922, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Likör" },
      { "aggregate": "SUM", "column": { "column_name": "Rakı", "id": 6923, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Rakı" },
      { "aggregate": "SUM", "column": { "column_name": "Rom", "id": 6924, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Rom" },
      { "aggregate": "SUM", "column": { "column_name": "Şarap", "id": 6925, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Şarap" },
      { "aggregate": "SUM", "column": { "column_name": "Tekila", "id": 6926, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Tekila" },
      { "aggregate": "SUM", "column": { "column_name": "Viski", "id": 6927, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Viski" },
      { "aggregate": "SUM", "column": { "column_name": "Votka", "id": 6928, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Votka" },
      { "aggregate": "SUM", "column": { "column_name": "Diğer", "id": 6917, "type": "Decimal(38, 14)" }, "expressionType": "SIMPLE", "hasCustomLabel": true, "label": "Diğer" }
    ],
    "metricsLayout": "COLUMNS",
    "adhoc_filters": [
      { "clause": "WHERE", "expressionType": "SQL", "sqlExpression": \"%s\", "subject": null }
    ],
    "row_limit": 1000,
    "order_desc": true,
    "aggregateFunction": "Sum",
    "rowTotals": true,
    "colTotals": true,
    "valueFormat": "SMART_NUMBER",
    "date_format": "smart_date",
    "rowOrder": "key_a_to_z",
    "colOrder": "key_a_to_z",
    "conditional_formatting": [],
    "allow_render_html": true,
    "extra_form_data": {},
    "force": true,
    "result_format": "json",
    "result_type": "full"
  },
  "result_format": "json",
  "result_type": "full"
}
""".formatted(whereBM, whereBM);

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=3OV-pvn71Gk&dashboard_page_id=hShkLIJ2wQpc2K4rOnTqK&slice_id=352")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .build();

        int maxRetries = 3;
        IOException lastEx = null;

        for (int i = 1; i <= maxRetries; i++) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return new JSONObject(response.body().string());
                } else {
                    System.err.println("sendWidget101Request - HTTP " + response.code() + " (attempt " + i + ")");
                }
            } catch (IOException e) {
                lastEx = e;
                System.err.println("sendWidget101Request attempt " + i + " failed: " + e.getMessage());
            }
            try { Thread.sleep(300L); } catch (InterruptedException ignored) {}
        }

        throw new IOException("sendWidget101Request failed after " + maxRetries + " attempts", lastEx);
    }

    public static JSONObject sendWidget26Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String jsonBody = """
    {
      "datasource": {"id":259,"type":"table"},
      "force": true,
      "queries": [{
        "filters": [
          {"col":"dimension","op":"IN","val":["KalanRotaZiyaretSayisi","ZiyaretEdilenRotaSayisi"]},
          {"col":"Bolge","op":"IN","val":["Ege Bölge","Anadolu Bölge","Marmara Bölge","Ulusal On-Trade Kanal MD."]}
        ],
        "extras": {"having":"","where":""},
        "applied_time_extras": {},
        "columns": [{
          "expressionType":"SQL",
          "label":"dimension",
          "sqlExpression":"case when dimension = 'ZiyaretEdilenRotaSayisi' then 'Z. Başlanan Rota' WHEN dimension = 'KalanRotaZiyaretSayisi' then 'Kalan Rota' end"
        }],
        "metrics": [{
          "aggregate":"SUM",
          "column":{
            "advanced_data_type":null,"certification_details":null,"certified_by":null,
            "column_name":"value","description":null,"expression":null,"filterable":true,
            "groupby":true,"id":5767,"is_certified":false,"is_dttm":false,
            "python_date_format":null,"type":"Int64","type_generic":0,"verbose_name":null,"warning_markdown":null
          },
          "datasourceWarning":false,"expressionType":"SIMPLE","hasCustomLabel":true,
          "label":"Rota Sayısı","optionName":"metric_w9v1ougpyd_bds81f3i0xb","sqlExpression":null
        }],
        "orderby":[[{
          "aggregate":"SUM",
          "column":{
            "advanced_data_type":null,"certification_details":null,"certified_by":null,
            "column_name":"value","description":null,"expression":null,"filterable":true,
            "groupby":true,"id":5767,"is_certified":false,"is_dttm":false,
            "python_date_format":null,"type":"Int64","type_generic":0,"verbose_name":null,"warning_markdown":null
          },
          "datasourceWarning":false,"expressionType":"SIMPLE","hasCustomLabel":true,
          "label":"Rota Sayısı","optionName":"metric_w9v1ougpyd_bds81f3i0xb","sqlExpression":null
        },false]],
        "annotation_layers":[],
        "row_limit":100,
        "series_limit":0,
        "order_desc":true,
        "url_params":{"dashboard_page_id":"kSc6pUD3su3tG4AiF9e_t","slice_id":"159"},
        "custom_params":{},
        "custom_form_data":{}
      }],
      "form_data":{
        "datasource":"259__table",
        "viz_type":"pie",
        "slice_id":159,
        "url_params":{"dashboard_page_id":"kSc6pUD3su3tG4AiF9e_t","slice_id":"159"},
        "groupby":[{
          "expressionType":"SQL",
          "label":"dimension",
          "sqlExpression":"case when dimension = 'ZiyaretEdilenRotaSayisi' then 'Z. Başlanan Rota' WHEN dimension = 'KalanRotaZiyaretSayisi' then 'Kalan Rota' end"
        }],
        "metric":{
          "aggregate":"SUM",
          "column":{
            "advanced_data_type":null,"certification_details":null,"certified_by":null,
            "column_name":"value","description":null,"expression":null,"filterable":true,
            "groupby":true,"id":5767,"is_certified":false,"is_dttm":false,
            "python_date_format":null,"type":"Int64","type_generic":0,"verbose_name":null,"warning_markdown":null
          },
          "datasourceWarning":false,"expressionType":"SIMPLE","hasCustomLabel":true,
          "label":"Rota Sayısı","optionName":"metric_w9v1ougpyd_bds81f3i0xb","sqlExpression":null
        },
        "adhoc_filters":[
          {"clause":"WHERE","comparator":["KalanRotaZiyaretSayisi","ZiyaretEdilenRotaSayisi"],"datasourceWarning":false,"expressionType":"SIMPLE","filterOptionName":"filter_5z33obgwvxn_g6q4lirckzr","isExtra":false,"isNew":false,"operator":"IN","operatorId":"IN","sqlExpression":null,"subject":"dimension"},
          {"clause":"WHERE","comparator":["Ege Bölge","Anadolu Bölge","Marmara Bölge","Ulusal On-Trade Kanal MD."],"datasourceWarning":false,"expressionType":"SIMPLE","filterOptionName":"filter_syzc8hxnzjk_mbqmq8oo3u","isExtra":false,"isNew":false,"operator":"IN","operatorId":"IN","sqlExpression":null,"subject":"Bolge"}
        ],
        "row_limit":100,
        "sort_by_metric":true,
        "color_scheme":"bnbColors",
        "show_labels_threshold":0.1,
        "show_legend":true,
        "legendType":"scroll",
        "legendOrientation":"bottom",
        "legendMargin":null,
        "label_type":"percent",
        "number_format":"SMART_NUMBER",
        "date_format":"smart_date",
        "show_labels":true,
        "labels_outside":true,
        "label_line":true,
        "show_total":true,
        "outerRadius":70,
        "donut":true,
        "innerRadius":43,
        "extra_form_data":{},
        "force":true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A159%7D&force=true")
                .post(RequestBody.create(jsonBody, MediaType.parse("application/json")))
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=qRHholOkv94&dashboard_page_id=kSc6pUD3su3tG4AiF9e_t&slice_id=159")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String respBody = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                System.err.println("sendWidget26Request HTTP " + response.code() + " body: " + respBody);
                throw new IOException("Unexpected code " + response);
            }
            return new JSONObject(respBody);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static JSONObject sendWidget27Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String jsonBody = """
    {
      "datasource": {"id":259,"type":"table"},
      "force": true,
      "queries": [{
        "filters": [
          {"col":"Bolge","op":"IN","val":["Ulusal On-Trade Kanal MD.","Marmara Bölge","Anadolu Bölge","Ege Bölge"]},
          {"col":"dimension","op":"IN","val":["KalanRotaZiyaretSayisi","ZiyaretEdilenRotaSayisi"]}
        ],
        "extras":{"having":"","where":""},
        "applied_time_extras":{},
        "columns":[
          {"columnType":"BASE_AXIS","sqlExpression":"Bolge","label":"Bolge","expressionType":"SQL"},
          {
            "expressionType":"SQL",
            "label":"dimension",
            "sqlExpression":"case when dimension = 'ZiyaretEdilenRotaSayisi' then 'Ziyaret Başlanan Rota Sayısı' WHEN dimension = 'KalanRotaZiyaretSayisi' then 'Kalan Rota Sayısı' end"
          }
        ],
        "metrics":[{
          "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL",
          "hasCustomLabel":false,"label":"sum(if(value < 0, 0, value))",
          "optionName":"metric_htccv7ukmx6_sg7q2qj66u8",
          "sqlExpression":"sum(if(value < 0, 0, value))"
        }],
        "orderby":[[{
          "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL",
          "hasCustomLabel":false,"label":"sum(if(value < 0, 0, value))",
          "optionName":"metric_htccv7ukmx6_sg7q2qj66u8",
          "sqlExpression":"sum(if(value < 0, 0, value))"
        },false]],
        "annotation_layers":[],
        "row_limit":1000,
        "series_columns":[{
          "expressionType":"SQL",
          "label":"dimension",
          "sqlExpression":"case when dimension = 'ZiyaretEdilenRotaSayisi' then 'Ziyaret Başlanan Rota Sayısı' WHEN dimension = 'KalanRotaZiyaretSayisi' then 'Kalan Rota Sayısı' end"
        }],
        "series_limit":0,
        "order_desc":true,
        "url_params":{"dashboard_page_id":"kSc6pUD3su3tG4AiF9e_t","slice_id":"161"},
        "custom_params":{},
        "custom_form_data":{},
        "time_offsets":[],
        "post_processing":[
          {"operation":"pivot","options":{
            "index":["Bolge"],
            "columns":["dimension"],
            "aggregates":{"sum(if(value < 0, 0, value))":{"operator":"mean"}},
            "drop_missing_columns":false
          }},
          {"operation":"rename","options":{"columns":{"sum(if(value < 0, 0, value))":null},"level":0,"inplace":true}},
          {"operation":"flatten"}
        ]
      }],
      "form_data":{
        "datasource":"259__table","viz_type":"echarts_timeseries_bar","slice_id":161,
        "url_params":{"dashboard_page_id":"kSc6pUD3su3tG4AiF9e_t","slice_id":"161"},
        "x_axis":"Bolge",
        "x_axis_sort_series":"name","x_axis_sort_series_ascending":true,
        "metrics":[{
          "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL",
          "hasCustomLabel":false,"label":"sum(if(value < 0, 0, value))",
          "optionName":"metric_htccv7ukmx6_sg7q2qj66u8",
          "sqlExpression":"sum(if(value < 0, 0, value))"
        }],
        "groupby":[{
          "expressionType":"SQL",
          "label":"dimension",
          "sqlExpression":"case when dimension = 'ZiyaretEdilenRotaSayisi' then 'Ziyaret Başlanan Rota Sayısı' WHEN dimension = 'KalanRotaZiyaretSayisi' then 'Kalan Rota Sayısı' end"
        }],
        "adhoc_filters":[
          {"clause":"WHERE","comparator":["Ulusal On-Trade Kanal MD.","Marmara Bölge","Anadolu Bölge","Ege Bölge"],"datasourceWarning":false,"expressionType":"SIMPLE","filterOptionName":"filter_dthwyjo43e_aiilcdcn0x5","isExtra":false,"isNew":false,"operator":"IN","operatorId":"IN","sqlExpression":null,"subject":"Bolge"},
          {"clause":"WHERE","comparator":["KalanRotaZiyaretSayisi","ZiyaretEdilenRotaSayisi"],"datasourceWarning":false,"expressionType":"SIMPLE","filterOptionName":"filter_os9lnl9e3kp_bn3n1kwr8","isExtra":false,"isNew":false,"operator":"IN","operatorId":"IN","sqlExpression":null,"subject":"dimension"}
        ],
        "row_limit":1000,"truncate_metric":true,"show_empty_columns":true,
        "annotation_layers":[],
        "orientation":"vertical",
        "x_axis_title":"","x_axis_title_margin":15,
        "y_axis_title":"","y_axis_title_margin":15,"y_axis_title_position":"Left",
        "sort_series_type":"name","sort_series_ascending":false,
        "color_scheme":"bnbColors","time_shift_color":true,"show_value":false,
        "stack":"Stack","only_total":true,"percentage_threshold":0,
        "show_legend":true,"legendType":"plain","legendOrientation":"bottom","legendMargin":null,
        "x_axis_time_format":"smart_date","xAxisLabelRotation":45,
        "y_axis_format":"SMART_NUMBER","logAxis":false,"minorSplitLine":false,
        "truncateYAxis":false,"y_axis_bounds":[null,null],
        "truncateXAxis":true,"rich_tooltip":true,"showTooltipTotal":true,"tooltipTimeFormat":"smart_date",
        "extra_form_data":{},"force":true,"result_format":"json","result_type":"full"
      },
      "result_format":"json","result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A161%7D&force=true")
                .post(RequestBody.create(jsonBody, MediaType.parse("application/json")))
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=ScZfXpn6FfI&dashboard_page_id=kSc6pUD3su3tG4AiF9e_t&slice_id=161")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String respBody = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                System.err.println("sendWidget27Request HTTP " + response.code() + " body: " + respBody);
                throw new IOException("Unexpected code " + response);
            }
            return new JSONObject(respBody);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget28Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        // Postman cURL içeriğiyle aynı body
        String json = """
    {
      "datasource":{"id":302,"type":"table"},
      "force":true,
      "queries":[
        {
          "filters":[
            {"col":"Z_Status","op":"IN","val":["Ziyaret Edilen","Kalan Ziyaret"]},
            {"col":"Tarih","op":"TEMPORAL_RANGE","val":"No filter"}
          ],
          "extras":{"having":"","where":""},
          "applied_time_extras":{},
          "columns":["Z_Status"],
          "metrics":[
            {
              "aggregate":"SUM",
              "column":{
                "advanced_data_type":null,
                "certification_details":null,
                "certified_by":null,
                "column_name":"Z_Number",
                "description":null,
                "expression":null,
                "filterable":true,
                "groupby":true,
                "id":6755,
                "is_certified":false,
                "is_dttm":false,
                "python_date_format":null,
                "type":"Nullable(Int64)",
                "type_generic":0,
                "verbose_name":null,
                "warning_markdown":null
              },
              "datasourceWarning":false,
              "expressionType":"SIMPLE",
              "hasCustomLabel":true,
              "label":"Ziyaret Sayısı",
              "optionName":"metric_20iw66gpu8_5rzpuzhtwd4",
              "sqlExpression":null
            }
          ],
          "orderby":[[
            {
              "aggregate":"SUM",
              "column":{
                "advanced_data_type":null,
                "certification_details":null,
                "certified_by":null,
                "column_name":"Z_Number",
                "description":null,
                "expression":null,
                "filterable":true,
                "groupby":true,
                "id":6755,
                "is_certified":false,
                "is_dttm":false,
                "python_date_format":null,
                "type":"Nullable(Int64)",
                "type_generic":0,
                "verbose_name":null,
                "warning_markdown":null
              },
              "datasourceWarning":false,
              "expressionType":"SIMPLE",
              "hasCustomLabel":true,
              "label":"Ziyaret Sayısı",
              "optionName":"metric_20iw66gpu8_5rzpuzhtwd4",
              "sqlExpression":null
            },
            false
          ]],
          "annotation_layers":[],
          "row_limit":100,
          "series_limit":0,
          "order_desc":true,
          "url_params":{"dashboard_page_id":"kSc6pUD3su3tG4AiF9e_t","slice_id":"160"},
          "custom_params":{},
          "custom_form_data":{}
        }
      ],
      "form_data":{
        "datasource":"302__table",
        "viz_type":"pie",
        "slice_id":160,
        "url_params":{"dashboard_page_id":"kSc6pUD3su3tG4AiF9e_t","slice_id":"160"},
        "groupby":["Z_Status"],
        "metric":{
          "aggregate":"SUM",
          "column":{
            "advanced_data_type":null,
            "certification_details":null,
            "certified_by":null,
            "column_name":"Z_Number",
            "description":null,
            "expression":null,
            "filterable":true,
            "groupby":true,
            "id":6755,
            "is_certified":false,
            "is_dttm":false,
            "python_date_format":null,
            "type":"Nullable(Int64)",
            "type_generic":0,
            "verbose_name":null,
            "warning_markdown":null
          },
          "datasourceWarning":false,
          "expressionType":"SIMPLE",
          "hasCustomLabel":true,
          "label":"Ziyaret Sayısı",
          "optionName":"metric_20iw66gpu8_5rzpuzhtwd4",
          "sqlExpression":null
        },
        "adhoc_filters":[
          {
            "clause":"WHERE",
            "comparator":["Ziyaret Edilen","Kalan Ziyaret"],
            "datasourceWarning":false,
            "expressionType":"SIMPLE",
            "filterOptionName":"filter_uqn11vc9ia_i26y95pcd0p",
            "isExtra":false,
            "isNew":false,
            "operator":"IN",
            "operatorId":"IN",
            "sqlExpression":null,
            "subject":"Z_Status"
          },
          {
            "clause":"WHERE",
            "comparator":"No filter",
            "expressionType":"SIMPLE",
            "operator":"TEMPORAL_RANGE",
            "subject":"Tarih"
          }
        ],
        "row_limit":100,
        "sort_by_metric":true,
        "color_scheme":"bnbColors",
        "show_labels_threshold":0.5,
        "show_legend":true,
        "legendType":"scroll",
        "legendOrientation":"bottom",
        "legendMargin":null,
        "label_type":"percent",
        "number_format":",d",
        "date_format":"smart_date",
        "show_labels":true,
        "labels_outside":true,
        "label_line":true,
        "show_total":true,
        "outerRadius":60,
        "donut":true,
        "innerRadius":38,
        "extra_form_data":{},
        "force":true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A160%7D&force=true")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String respBody = response.body() != null ? response.body().string() : "";
                throw new IOException("sendWidget28Request failed: HTTP " + response.code() + " - " + respBody);
            }
            String resp = response.body() != null ? response.body().string() : "{}";
            return new JSONObject(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget29Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String json = """
    {
      "datasource": {"id":302,"type":"table"},
      "force": true,
      "queries": [{
        "filters": [
          {"col":"Z_Status","op":"IN","val":["Başarılı Ziyaret","Başarılı Olmayan Ziyaret"]},
          {"col":"Tarih","op":"TEMPORAL_RANGE","val":"No filter"}
        ],
        "extras": {"having":"","where":""},
        "applied_time_extras": {},
        "columns": [{
          "datasourceWarning": false,
          "expressionType": "SQL",
          "label": "Z_Status",
          "sqlExpression": "CASE WHEN Z_Status = 'Başarılı Olmayan Ziyaret' Then 'Başarısız' WHEN Z_Status= 'Başarılı Ziyaret' Then 'Başarılı'END"
        }],
        "metrics": [{
          "aggregate": "SUM",
          "column": {
            "advanced_data_type": null,
            "certification_details": null,
            "certified_by": null,
            "column_name": "Z_Number",
            "description": null,
            "expression": null,
            "filterable": true,
            "groupby": true,
            "id": 6755,
            "is_certified": false,
            "is_dttm": false,
            "python_date_format": null,
            "type": "Nullable(Int64)",
            "type_generic": 0,
            "verbose_name": null,
            "warning_markdown": null
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Ziyaret Sayısı",
          "optionName": "metric_391jewukb5s_9dmhfqpgw79",
          "sqlExpression": null
        }],
        "orderby": [[{
          "aggregate": "SUM",
          "column": {
            "advanced_data_type": null,
            "certification_details": null,
            "certified_by": null,
            "column_name": "Z_Number",
            "description": null,
            "expression": null,
            "filterable": true,
            "groupby": true,
            "id": 6755,
            "is_certified": false,
            "is_dttm": false,
            "python_date_format": null,
            "type": "Nullable(Int64)",
            "type_generic": 0,
            "verbose_name": null,
            "warning_markdown": null
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Ziyaret Sayısı",
          "optionName": "metric_391jewukb5s_9dmhfqpgw79",
          "sqlExpression": null
        }, false]],
        "annotation_layers": [],
        "row_limit": 100,
        "series_limit": 0,
        "order_desc": true,
        "url_params": {"dashboard_page_id":"kSc6pUD3su3tG4AiF9e_t","slice_id":"163"},
        "custom_params": {},
        "custom_form_data": {}
      }],
      "form_data": {
        "datasource":"302__table",
        "viz_type":"pie",
        "slice_id":163,
        "url_params":{"dashboard_page_id":"kSc6pUD3su3tG4AiF9e_t","slice_id":"163"},
        "groupby": [{
          "datasourceWarning": false,
          "expressionType": "SQL",
          "label": "Z_Status",
          "sqlExpression": "CASE WHEN Z_Status = 'Başarılı Olmayan Ziyaret' Then 'Başarısız' WHEN Z_Status= 'Başarılı Ziyaret' Then 'Başarılı'END"
        }],
        "metric": {
          "aggregate": "SUM",
          "column": {
            "advanced_data_type": null,
            "certification_details": null,
            "certified_by": null,
            "column_name": "Z_Number",
            "description": null,
            "expression": null,
            "filterable": true,
            "groupby": true,
            "id": 6755,
            "is_certified": false,
            "is_dttm": false,
            "python_date_format": null,
            "type": "Nullable(Int64)",
            "type_generic": 0,
            "verbose_name": null,
            "warning_markdown": null
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Ziyaret Sayısı",
          "optionName": "metric_391jewukb5s_9dmhfqpgw79",
          "sqlExpression": null
        },
        "adhoc_filters": [
          {
            "clause":"WHERE",
            "comparator":["Başarılı Ziyaret","Başarılı Olmayan Ziyaret"],
            "datasourceWarning":false,
            "expressionType":"SIMPLE",
            "filterOptionName":"filter_00wksw2qf5u5h_jvcpw4lwhab",
            "isExtra":false,
            "isNew":false,
            "operator":"IN",
            "operatorId":"IN",
            "sqlExpression":null,
            "subject":"Z_Status"
          },
          {
            "clause":"WHERE",
            "comparator":"No filter",
            "expressionType":"SIMPLE",
            "operator":"TEMPORAL_RANGE",
            "subject":"Tarih"
          }
        ],
        "row_limit":100,
        "sort_by_metric":true,
        "color_scheme":"bnbColors",
        "show_labels_threshold":5,
        "show_legend":true,
        "legendType":"scroll",
        "legendOrientation":"bottom",
        "legendMargin":null,
        "label_type":"percent",
        "number_format":"SMART_NUMBER",
        "date_format":"smart_date",
        "show_labels":true,
        "labels_outside":true,
        "label_line":true,
        "show_total":true,
        "outerRadius":60,
        "donut":true,
        "innerRadius":38,
        "extra_form_data":{},
        "force":true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A163%7D&force=true")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String respBody = response.body() != null ? response.body().string() : "";
                throw new IOException("sendWidget29Request failed: HTTP " + response.code() + " - " + respBody);
            }
            String resp = response.body() != null ? response.body().string() : "{}";
            return new JSONObject(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget30Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        // cURL’deki payload’ın sadeleştirilmiş ama eşdeğer JSON’ı
        String jsonBody = """
    {
      "datasource": {"id": 302, "type": "table"},
      "force": true,
      "queries": [{
        "filters": [
          {"col": "Z_Status", "op": "IN", "val": ["Yerinde Olmayan Ziyaret","Yerinde Ziyaret"]},
          {"col": "Tarih", "op": "TEMPORAL_RANGE", "val": "No filter"}
        ],
        "extras": {"having": "", "where": ""},
        "applied_time_extras": {},
        "columns": [{
          "datasourceWarning": false,
          "expressionType": "SQL",
          "label": "Z_Status",
          "sqlExpression": "CASE WHEN Z_Status = 'Yerinde Ziyaret' then 'Yerinde' WHEN Z_Status = 'Yerinde Olmayan Ziyaret' Then 'Yerinde Değil' END"
        }],
        "metrics": [{
          "aggregate": "SUM",
          "column": {
            "column_name": "Z_Number",
            "id": 6755,
            "filterable": true,
            "groupby": true,
            "type": "Nullable(Int64)",
            "type_generic": 0
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Ziyaret Sayısı",
          "optionName": "metric_abbrzlozpx_6ujgnl1j6rj",
          "sqlExpression": null
        }],
        "orderby": [[{
          "aggregate": "SUM",
          "column": {
            "column_name": "Z_Number",
            "id": 6755,
            "filterable": true,
            "groupby": true,
            "type": "Nullable(Int64)",
            "type_generic": 0
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Ziyaret Sayısı",
          "optionName": "metric_abbrzlozpx_6ujgnl1j6rj",
          "sqlExpression": null
        }, false]],
        "annotation_layers": [],
        "row_limit": 100,
        "series_limit": 0,
        "order_desc": true,
        "url_params": {"dashboard_page_id": "kSc6pUD3su3tG4AiF9e_t", "slice_id": "164"},
        "custom_params": {},
        "custom_form_data": {}
      }],
      "form_data": {
        "datasource": "302__table",
        "viz_type": "pie",
        "slice_id": 164,
        "url_params": {"dashboard_page_id": "kSc6pUD3su3tG4AiF9e_t", "slice_id": "164"},
        "groupby": [{
          "datasourceWarning": false,
          "expressionType": "SQL",
          "label": "Z_Status",
          "sqlExpression": "CASE WHEN Z_Status = 'Yerinde Ziyaret' then 'Yerinde' WHEN Z_Status = 'Yerinde Olmayan Ziyaret' Then 'Yerinde Değil' END"
        }],
        "metric": {
          "aggregate": "SUM",
          "column": {
            "column_name": "Z_Number",
            "id": 6755,
            "filterable": true,
            "groupby": true,
            "type": "Nullable(Int64)",
            "type_generic": 0
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Ziyaret Sayısı",
          "optionName": "metric_abbrzlozpx_6ujgnl1j6rj",
          "sqlExpression": null
        },
        "adhoc_filters": [
          {"clause":"WHERE","operator":"IN","operatorId":"IN","subject":"Z_Status","comparator":["Yerinde Olmayan Ziyaret","Yerinde Ziyaret"],"expressionType":"SIMPLE","isNew":false,"isExtra":false,"datasourceWarning":false,"filterOptionName":"filter_f2icu4o5ea_6rjov6xciz8"},
          {"clause":"WHERE","operator":"TEMPORAL_RANGE","subject":"Tarih","comparator":"No filter","expressionType":"SIMPLE"}
        ],
        "row_limit": 100,
        "sort_by_metric": true,
        "color_scheme": "bnbColors",
        "show_labels_threshold": 5,
        "show_legend": true,
        "legendType": "scroll",
        "legendOrientation": "bottom",
        "label_type": "percent",
        "number_format": "SMART_NUMBER",
        "date_format": "smart_date",
        "show_labels": true,
        "labels_outside": true,
        "label_line": true,
        "show_total": true,
        "outerRadius": 60,
        "donut": true,
        "innerRadius": 38,
        "extra_form_data": {},
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A164%7D&force=true")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.code() + " - " + (response.body() != null ? response.body().string() : ""));
            }
            String resp = response.body() != null ? response.body().string() : "{}";
            return new JSONObject(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget38Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String json = """
    {"datasource":{"id":301,"type":"table"},"force":true,
     "queries":[{"filters":[{"col":"DISTRIBUTOR_KOD","op":"NOT IN","val":["EDIRNE MAHALO"]}],
     "extras":{"having":"","where":""},"applied_time_extras":{},
     "columns":[],"metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,
     "expressionType":"SQL","hasCustomLabel":false,"label":"SUM(Stock_Liters)",
     "optionName":"metric_hw8i3qs6reo_tga48abpfe","sqlExpression":"SUM(Stock_Liters)"}],
     "annotation_layers":[],"series_limit":0,"order_desc":true,
     "url_params":{"dashboard_page_id":"RQU5lFjUavfRCcebEFYwP","slice_id":"378"},
     "custom_params":{},"custom_form_data":{}}],
     "form_data":{"datasource":"301__table","viz_type":"big_number_total","slice_id":378,
     "url_params":{"dashboard_page_id":"RQU5lFjUavfRCcebEFYwP","slice_id":"378"},
     "metric":{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL",
     "hasCustomLabel":false,"label":"SUM(Stock_Liters)","optionName":"metric_hw8i3qs6reo_tga48abpfe",
     "sqlExpression":"SUM(Stock_Liters)"},
     "adhoc_filters":[{"clause":"WHERE","comparator":["EDIRNE MAHALO"],"datasourceWarning":false,
     "expressionType":"SIMPLE","filterOptionName":"filter_4t2sgzens7n_8kynuyn3rl6","isExtra":false,
     "isNew":false,"operator":"NOT IN","operatorId":"NOT_IN","sqlExpression":null,
     "subject":"DISTRIBUTOR_KOD"}],
     "subheader":"Litre","header_font_size":0.4,"subheader_font_size":0.15,"y_axis_format":",.2f",
     "time_format":"smart_date","conditional_formatting":[]},
     "result_format":"json","result_type":"full"}
    """;

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A378%7D&force=true")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.code() + " - " + (response.body() != null ? response.body().string() : ""));
            }
            return new JSONObject(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget43Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String json = """
    {
      "datasource": { "id": 209, "type": "table" },
      "force": true,
      "queries": [
        {
          "filters": [
            { "col": "DISTRIBUTOR_KOD", "op": "NOT IN", "val": ["EDIRNE MAHALO"] }
          ],
          "extras": { "time_grain_sqla": "P1D", "having": "", "where": "" },
          "applied_time_extras": {},
          "columns": [
            { "expressionType": "SQL", "label": "Distribütör Kod", "sqlExpression": "DISTRIBUTOR_KOD" },
            { "expressionType": "SQL", "label": "Ürün Kod",        "sqlExpression": "URUN_KOD" },
            { "expressionType": "SQL", "label": "Ürün Kategori",   "sqlExpression": "URUN_KATEGORU_ACIKLAMA" },
            { "expressionType": "SQL", "label": "Ürün Tip",        "sqlExpression": "URUN_TIP_ACIKLAMA" },
            { "expressionType": "SQL", "label": "Ürün Kısa Ad",    "sqlExpression": "URUN_KISA_AD" },
            { "expressionType": "SQL", "label": "Şişe Stok",       "sqlExpression": "Stock" },
            { "expressionType": "SQL", "label": "Litre Stok",      "sqlExpression": "Stock_Liters" },
            { "expressionType": "SQL", "label": "Günlük Ortalama Şişe Satış",  "sqlExpression": "Est_Total_Sales" },
            { "expressionType": "SQL", "label": "Günlük Ortalama Litre Satış", "sqlExpression": "Est_Total_Sales_Liters" },
            { "expressionType": "SQL", "label": "Kalan Stok Gün",  "sqlExpression": "toFloat64(Stock_Liters)/toFloat64(Est_Total_Sales_Liters)*1.0" }
          ],
          "metrics": [],
          "orderby": [
            ["URUN_TIP_ACIKLAMA", true]
          ],
          "annotation_layers": [],
          "row_limit": 100000,
          "series_limit": 0,
          "order_desc": true,
          "url_params": { "dashboard_page_id": "RQU5lFjUavfRCcebEFYwP", "slice_id": "376" },
          "custom_params": {},
          "custom_form_data": {},
          "post_processing": [],
          "time_offsets": []
        }
      ],
      "form_data": {
        "datasource": "209__table",
        "viz_type": "table",
        "slice_id": 376,
        "url_params": { "dashboard_page_id": "RQU5lFjUavfRCcebEFYwP", "slice_id": "376" },
        "query_mode": "aggregate",
        "groupby": [
          { "expressionType": "SQL", "label": "Distribütör Kod", "sqlExpression": "DISTRIBUTOR_KOD" },
          { "expressionType": "SQL", "label": "Ürün Kod",        "sqlExpression": "URUN_KOD" },
          { "expressionType": "SQL", "label": "Ürün Kategori",   "sqlExpression": "URUN_KATEGORU_ACIKLAMA" },
          { "expressionType": "SQL", "label": "Ürün Tip",        "sqlExpression": "URUN_TIP_ACIKLAMA" },
          { "expressionType": "SQL", "label": "Ürün Kısa Ad",    "sqlExpression": "URUN_KISA_AD" },
          { "expressionType": "SQL", "label": "Şişe Stok",       "sqlExpression": "Stock" },
          { "expressionType": "SQL", "label": "Litre Stok",      "sqlExpression": "Stock_Liters" },
          { "expressionType": "SQL", "label": "Günlük Ortalama Şişe Satış",  "sqlExpression": "Est_Total_Sales" },
          { "expressionType": "SQL", "label": "Günlük Ortalama Litre Satış", "sqlExpression": "Est_Total_Sales_Liters" },
          { "expressionType": "SQL", "label": "Kalan Stok Gün",  "sqlExpression": "toFloat64(Stock_Liters)/toFloat64(Est_Total_Sales_Liters)*1.0" }
        ],
        "time_grain_sqla": "P1D",
        "temporal_columns_lookup": {},
        "all_columns": [],
        "percent_metrics": [],
        "adhoc_filters": [
          {
            "clause": "WHERE",
            "comparator": ["EDIRNE MAHALO"],
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "filterOptionName": "filter_xxpk6dan4db_yrds5ko880b",
            "isExtra": false,
            "isNew": false,
            "operator": "NOT IN",
            "operatorId": "NOT_IN",
            "sqlExpression": null,
            "subject": "DISTRIBUTOR_KOD"
          }
        ],
        "order_by_cols": [
          "[\\"URUN_TIP_ACIKLAMA\\", true]"
        ],
        "row_limit": "100000",
        "table_timestamp_format": "smart_date",
        "include_search": true,
        "allow_render_html": true,
        "column_config": {
          "Günlük Ortalama Litre Satış": { "d3NumberFormat": ",.2f", "d3SmallNumberFormat": ",.2f" },
          "Günlük Ortalama Şişe Satış":  { "d3NumberFormat": ",.2f", "d3SmallNumberFormat": ",.2f" },
          "Kalan Stok Gün":              { "d3NumberFormat": ",.2f", "d3SmallNumberFormat": ",.2f" },
          "Litre Stok":                  { "d3NumberFormat": ",.2f", "d3SmallNumberFormat": ",.2f" }
        },
        "show_cell_bars": false,
        "color_pn": true,
        "comparison_color_scheme": "Green",
        "conditional_formatting": [],
        "comparison_type": "values",
        "extra_form_data": {},
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A376%7D&force=true")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String err = response.body() != null ? response.body().string() : "";
                throw new IOException("Unexpected code " + response.code() + " - " + err);
            }
            return new JSONObject(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static JSONObject sendWidget94Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        RequestBody body = RequestBody.create("""
    {
      "datasource": {
        "id": 152,
        "type": "table"
      },
      "force": true,
      "queries": [
        {
          "filters": [
            {
              "col": "DISTRIBUTOR_KOD",
              "op": "NOT IN",
              "val": ["EDIRNE MAHALO"]
            }
          ],
          "extras": {
            "having": "",
            "where": ""
          },
          "applied_time_extras": {},
          "columns": ["Durum"],
          "metrics": [
            {
              "aggregate": "COUNT_DISTINCT",
              "column": {
                "advanced_data_type": null,
                "certification_details": null,
                "certified_by": null,
                "column_name": "DISTRIBUTOR_KOD",
                "description": null,
                "expression": null,
                "filterable": true,
                "groupby": true,
                "id": 3372,
                "is_certified": false,
                "is_dttm": false,
                "python_date_format": null,
                "type": "String",
                "type_generic": 1,
                "verbose_name": null,
                "warning_markdown": null
              },
              "datasourceWarning": false,
              "expressionType": "SIMPLE",
              "hasCustomLabel": true,
              "label": "Distribütör",
              "optionName": "metric_aaog31e67pv_0a3d141sdew",
              "sqlExpression": null
            }
          ],
          "annotation_layers": [],
          "row_limit": 5,
          "series_limit": 0,
          "order_desc": true,
          "url_params": {
            "dashboard_page_id": "S4uuQtRPAZQdnwSbZK06f",
            "slice_id": "535"
          },
          "custom_params": {},
          "custom_form_data": {}
        }
      ],
      "form_data": {
        "datasource": "152__table",
        "viz_type": "pie",
        "slice_id": 535,
        "url_params": {
          "dashboard_page_id": "S4uuQtRPAZQdnwSbZK06f",
          "slice_id": "535"
        },
        "groupby": ["Durum"],
        "metric": {
          "aggregate": "COUNT_DISTINCT",
          "column": {
            "advanced_data_type": null,
            "certification_details": null,
            "certified_by": null,
            "column_name": "DISTRIBUTOR_KOD",
            "description": null,
            "expression": null,
            "filterable": true,
            "groupby": true,
            "id": 3372,
            "is_certified": false,
            "is_dttm": false,
            "python_date_format": null,
            "type": "String",
            "type_generic": 1,
            "verbose_name": null,
            "warning_markdown": null
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Distribütör",
          "optionName": "metric_aaog31e67pv_0a3d141sdew",
          "sqlExpression": null
        },
        "adhoc_filters": [
          {
            "clause": "WHERE",
            "comparator": ["EDIRNE MAHALO"],
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "filterOptionName": "filter_ohf4v6ragy_thdr8gabjyl",
            "isExtra": false,
            "isNew": false,
            "operator": "NOT IN",
            "operatorId": "NOT_IN",
            "sqlExpression": null,
            "subject": "DISTRIBUTOR_KOD"
          }
        ],
        "row_limit": "5",
        "sort_by_metric": false,
        "color_scheme": "bnbColors",
        "show_labels_threshold": 1,
        "show_legend": false,
        "legendType": "scroll",
        "legendOrientation": "top",
        "legendMargin": null,
        "label_type": "key_value_percent",
        "number_format": "SMART_NUMBER",
        "date_format": "smart_date",
        "show_labels": true,
        "labels_outside": true,
        "label_line": true,
        "show_total": true,
        "outerRadius": 50,
        "donut": true,
        "innerRadius": 30,
        "extra_form_data": {},
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A535%7D&force=true")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            ResponseBody respBody = response.body();
            return new JSONObject(respBody != null ? respBody.string() : "{}");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget95Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        RequestBody body = RequestBody.create("""
    {
      "datasource": { "id": 152, "type": "table" },
      "force": true,
      "queries": [
        {
          "filters": [
            { "col": "DISTRIBUTOR_KOD", "op": "NOT IN", "val": ["EDIRNE MAHALO"] },
            { "col": "Durum", "op": "IN", "val": ["Yetersiz"] }
          ],
          "extras": { "having": "", "where": "" },
          "applied_time_extras": {},
          "columns": [
            { "expressionType": "SQL", "label": "Distribütör Kod", "sqlExpression": "DISTRIBUTOR_KOD" }
          ],
          "metrics": [],
          "orderby": [],
          "annotation_layers": [],
          "row_limit": 5000,
          "series_limit": 0,
          "order_desc": true,
          "url_params": { "dashboard_page_id": "S4uuQtRPAZQdnwSbZK06f", "slice_id": "539" },
          "custom_params": {},
          "custom_form_data": {},
          "post_processing": [],
          "time_offsets": []
        }
      ],
      "form_data": {
        "datasource": "152__table",
        "viz_type": "table",
        "slice_id": 539,
        "url_params": { "dashboard_page_id": "S4uuQtRPAZQdnwSbZK06f", "slice_id": "539" },
        "query_mode": "aggregate",
        "groupby": [
          { "expressionType": "SQL", "label": "Distribütör Kod", "sqlExpression": "DISTRIBUTOR_KOD" }
        ],
        "temporal_columns_lookup": {},
        "metrics": [],
        "all_columns": [],
        "percent_metrics": [],
        "adhoc_filters": [
          {
            "clause": "WHERE",
            "comparator": ["EDIRNE MAHALO"],
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "filterOptionName": "filter_ohf4v6ragy_thdr8gabjyl",
            "isExtra": false,
            "isNew": false,
            "operator": "NOT IN",
            "operatorId": "NOT_IN",
            "sqlExpression": null,
            "subject": "DISTRIBUTOR_KOD"
          },
          {
            "clause": "WHERE",
            "comparator": ["Yetersiz"],
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "filterOptionName": "filter_q12f6845o3p_3r5s60u5f5h",
            "isExtra": false,
            "isNew": false,
            "operator": "IN",
            "operatorId": "IN",
            "sqlExpression": null,
            "subject": "Durum"
          }
        ],
        "order_by_cols": [],
        "row_limit": 5000,
        "table_timestamp_format": "smart_date",
        "allow_render_html": true,
        "show_cell_bars": true,
        "color_pn": true,
        "comparison_color_scheme": "Green",
        "comparison_type": "values",
        "extra_form_data": {},
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A539%7D&force=true")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.code() + " - " + (response.body() != null ? response.body().string() : ""));
            }
            ResponseBody respBody = response.body();
            return new JSONObject(respBody != null ? respBody.string() : "{}");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget96Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        RequestBody body = RequestBody.create("""
    {
      "datasource": { "id": 246, "type": "table" },
      "force": true,
      "queries": [
        {
          "filters": [],
          "extras": { "having": "", "where": "" },
          "applied_time_extras": {},
          "columns": ["Durum"],
          "metrics": [
            {
              "aggregate": "COUNT_DISTINCT",
              "column": {
                "advanced_data_type": null,
                "certification_details": null,
                "certified_by": null,
                "column_name": "URUN_TIP_ACIKLAMA",
                "description": null,
                "expression": null,
                "filterable": true,
                "groupby": true,
                "id": 5593,
                "is_certified": false,
                "is_dttm": false,
                "python_date_format": null,
                "type": "Nullable(String)",
                "type_generic": 1,
                "verbose_name": null,
                "warning_markdown": null
              },
              "datasourceWarning": false,
              "expressionType": "SIMPLE",
              "hasCustomLabel": true,
              "label": "Marka",
              "optionName": "metric_bth2rq0h62e_eyyx9eijq57",
              "sqlExpression": null
            }
          ],
          "annotation_layers": [],
          "row_limit": 5,
          "series_limit": 0,
          "order_desc": true,
          "url_params": { "dashboard_page_id": "S4uuQtRPAZQdnwSbZK06f", "slice_id": "536" },
          "custom_params": {},
          "custom_form_data": {}
        }
      ],
      "form_data": {
        "datasource": "246__table",
        "viz_type": "pie",
        "slice_id": 536,
        "url_params": { "dashboard_page_id": "S4uuQtRPAZQdnwSbZK06f", "slice_id": "536" },
        "groupby": ["Durum"],
        "metric": {
          "aggregate": "COUNT_DISTINCT",
          "column": {
            "advanced_data_type": null,
            "certification_details": null,
            "certified_by": null,
            "column_name": "URUN_TIP_ACIKLAMA",
            "description": null,
            "expression": null,
            "filterable": true,
            "groupby": true,
            "id": 5593,
            "is_certified": false,
            "is_dttm": false,
            "python_date_format": null,
            "type": "Nullable(String)",
            "type_generic": 1,
            "verbose_name": null,
            "warning_markdown": null
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Marka",
          "optionName": "metric_bth2rq0h62e_eyyx9eijq57",
          "sqlExpression": null
        },
        "adhoc_filters": [],
        "row_limit": "5",
        "sort_by_metric": false,
        "color_scheme": "bnbColors",
        "show_labels_threshold": 0.1,
        "show_legend": false,
        "legendType": "scroll",
        "legendOrientation": "top",
        "legendMargin": null,
        "label_type": "key_value_percent",
        "number_format": "SMART_NUMBER",
        "date_format": "smart_date",
        "show_labels": true,
        "labels_outside": true,
        "label_line": true,
        "show_total": true,
        "outerRadius": 50,
        "donut": true,
        "innerRadius": 30,
        "extra_form_data": {},
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A536%7D&force=true")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                // aşağıdaki başlıklar Postman ile aynı davranışı yakalamaya yardımcı olur
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=rkTmIOsID3o&dashboard_page_id=S4uuQtRPAZQdnwSbZK06f&slice_id=536")
                .addHeader("User-Agent", "Mozilla/5.0")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.code() + " - " + response.message() + " - " + (response.body() != null ? response.body().string() : ""));
            }
            String resp = response.body() != null ? response.body().string() : "{}";
            return new JSONObject(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget97Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        RequestBody body = RequestBody.create("""
    {
      "datasource": { "id": 246, "type": "table" },
      "force": true,
      "queries": [
        {
          "filters": [
            { "col": "Durum", "op": "IN", "val": ["Yetersiz"] }
          ],
          "extras": { "having": "", "where": "" },
          "applied_time_extras": {},
          "columns": [
            { "datasourceWarning": false, "expressionType": "SQL", "label": "Marka", "sqlExpression": "URUN_TIP_ACIKLAMA" }
          ],
          "metrics": [],
          "orderby": [],
          "annotation_layers": [],
          "row_limit": 5,
          "series_limit": 0,
          "order_desc": true,
          "url_params": { "dashboard_page_id": "S4uuQtRPAZQdnwSbZK06f", "slice_id": "540" },
          "custom_params": {},
          "custom_form_data": {},
          "post_processing": [],
          "time_offsets": []
        }
      ],
      "form_data": {
        "datasource": "246__table",
        "viz_type": "table",
        "slice_id": 540,
        "url_params": { "dashboard_page_id": "S4uuQtRPAZQdnwSbZK06f", "slice_id": "540" },
        "query_mode": "aggregate",
        "groupby": [
          { "datasourceWarning": false, "expressionType": "SQL", "label": "Marka", "sqlExpression": "URUN_TIP_ACIKLAMA" }
        ],
        "temporal_columns_lookup": {},
        "all_columns": [],
        "percent_metrics": [],
        "adhoc_filters": [
          {
            "clause": "WHERE",
            "comparator": ["Yetersiz"],
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "filterOptionName": "filter_j8ieptify59_ncje3v41vmj",
            "isExtra": false,
            "isNew": false,
            "operator": "IN",
            "operatorId": "IN",
            "sqlExpression": null,
            "subject": "Durum"
          }
        ],
        "order_by_cols": [],
        "row_limit": "5",
        "table_timestamp_format": "smart_date",
        "allow_render_html": true,
        "show_cell_bars": true,
        "color_pn": true,
        "comparison_color_scheme": "Green",
        "comparison_type": "values",
        "extra_form_data": {},
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A540%7D&force=true")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=usSt2WvL64A&dashboard_page_id=S4uuQtRPAZQdnwSbZK06f&slice_id=540")
                .addHeader("User-Agent", "Mozilla/5.0")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String bodyStr = response.body() != null ? response.body().string() : "";
                throw new IOException("Unexpected code " + response.code() + " - " + response.message() + " - " + bodyStr);
            }
            String resp = response.body() != null ? response.body().string() : "{}";
            return new JSONObject(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget98Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A534%7D";

        MediaType JSON = MediaType.parse("application/json");

        String bodyStr = """
    {
      "datasource": { "id": 162, "type": "table" },
      "force": false,
      "queries": [{
        "filters": [],
        "extras": { "having": "", "where": "" },
        "applied_time_extras": {},
        "columns": [
          { "datasourceWarning": false, "expressionType": "SQL", "label": "Distribütör", "sqlExpression": "DISTRIBUTOR_KOD" },
          { "datasourceWarning": false, "expressionType": "SQL", "label": "Stock Out", "sqlExpression": "num_stockouts" }
        ],
        "orderby": [["num_stockouts", false]],
        "annotation_layers": [],
        "row_limit": 10,
        "series_limit": 0,
        "order_desc": true,
        "url_params": { "dashboard_page_id": "S4uuQtRPAZQdnwSbZK06f", "slice_id": "534" },
        "custom_params": {},
        "custom_form_data": {},
        "post_processing": [],
        "time_offsets": []
      }],
      "form_data": {
        "datasource": "162__table",
        "viz_type": "table",
        "slice_id": 534,
        "url_params": { "dashboard_page_id": "S4uuQtRPAZQdnwSbZK06f", "slice_id": "534" },
        "query_mode": "raw",
        "groupby": [],
        "temporal_columns_lookup": {},
        "all_columns": [
          { "datasourceWarning": false, "expressionType": "SQL", "label": "Distribütör", "sqlExpression": "DISTRIBUTOR_KOD" },
          { "datasourceWarning": false, "expressionType": "SQL", "label": "Stock Out", "sqlExpression": "num_stockouts" }
        ],
        "percent_metrics": [],
        "adhoc_filters": [],
        "order_by_cols": ["[\\"num_stockouts\\", false]"],
        "row_limit": 10,
        "table_timestamp_format": "smart_date",
        "allow_render_html": true,
        "show_cell_bars": false,
        "color_pn": true,
        "comparison_color_scheme": "Green",
        "conditional_formatting": [],
        "comparison_type": "values",
        "extra_form_data": {},
        "force": false,
        "result_format": "json",
        "result_type": "full",
        "include_time": false
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        RequestBody body = RequestBody.create(bodyStr, JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie) // session cookie burada
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String respBody = response.body() != null ? response.body().string() : "";
                throw new IOException("Unexpected code " + response.code() + " - " + respBody);
            }
            String resp = response.body().string();
            return new JSONObject(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget99Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A533%7D";

        MediaType JSON = MediaType.parse("application/json");
        String bodyStr = """
    {
      "datasource": { "id": 221, "type": "table" },
      "force": false,
      "queries": [
        {
          "filters": [],
          "extras": {
            "having": "",
            "where": ""
          },
          "applied_time_extras": {},
          "columns": [
            "URUN_TIP_ACIKLAMA",
            "num_distributors_with_so"
          ],
          "orderby": [
            ["num_distributors_with_so", false],
            ["URUN_TIP_ACIKLAMA", true]
          ],
          "annotation_layers": [],
          "row_limit": 10,
          "series_limit": 0,
          "order_desc": true,
          "url_params": {
            "dashboard_page_id": "S4uuQtRPAZQdnwSbZK06f",
            "slice_id": "533"
          },
          "custom_params": {},
          "custom_form_data": {},
          "post_processing": [],
          "time_offsets": []
        }
      ],
      "form_data": {
        "datasource": "221__table",
        "viz_type": "table",
        "slice_id": 533,
        "url_params": {
          "dashboard_page_id": "S4uuQtRPAZQdnwSbZK06f",
          "slice_id": "533"
        },
        "query_mode": "raw",
        "groupby": [],
        "temporal_columns_lookup": {},
        "all_columns": [
          "URUN_TIP_ACIKLAMA",
          "num_distributors_with_so"
        ],
        "percent_metrics": [],
        "adhoc_filters": [],
        "order_by_cols": [
          "[\\"num_distributors_with_so\\", false]",
          "[\\"URUN_TIP_ACIKLAMA\\", true]"
        ],
        "row_limit": 10,
        "table_timestamp_format": "smart_date",
        "include_search": false,
        "allow_rearrange_columns": false,
        "allow_render_html": true,
        "column_config": {
          "StockOut": { "horizontalAlign": "right" }
        },
        "show_cell_bars": false,
        "align_pn": false,
        "color_pn": false,
        "comparison_color_scheme": "Green",
        "conditional_formatting": [],
        "comparison_type": "values",
        "extra_form_data": {},
        "force": false,
        "result_format": "json",
        "result_type": "full",
        "include_time": false
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        RequestBody body = RequestBody.create(bodyStr, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String respBody = response.body() != null ? response.body().string() : "";
                throw new IOException("Unexpected code " + response.code() + " - " + respBody);
            }
            String resp = response.body() != null ? response.body().string() : "{}";
            return new JSONObject(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    public static JSONObject sendWidget31Request() {
        String cookie = ConfigurationReader.getProperty("cookie");
        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A165%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String json = """
    {
      "datasource": { "id": 324, "type": "table" },
      "force": false,
      "queries": [{
        "filters": [
          { "col": "ROTA", "op": "IN", "val": ["PRESELL ON KIZILAY BÖLGESI"] }
        ],
        "extras": {
          "having": "",
          "where": "(FISCALMONTH >= toMonth(today()) AND FISCALYEAR >= toYear(today())) AND (`ROTA` NOT ILIKE '%Telesell%'\\nAND `ROTA` NOT ILIKE '%Hospitality%' AND ROTA NOT ILIKE '%LZM%')"
        },
        "applied_time_extras": {},
        "columns": ["ROTA"],
        "metrics": [{
          "aggregate": null,
          "column": null,
          "datasourceWarning": false,
          "expressionType": "SQL",
          "hasCustomLabel": true,
          "label": "Oran %",
          "optionName": "metric_993uygn1uqo_ctf06qduzct",
          "sqlExpression": "SUM(Total_Sales)/SUM(TotalTarget)"
        }],
        "orderby": [[{
          "aggregate": null,
          "column": null,
          "datasourceWarning": false,
          "expressionType": "SQL",
          "hasCustomLabel": false,
          "label": "SUM(Total_Sales)/SUM(TotalTarget)",
          "optionName": "metric_yxkx9ru5wt_i19cupllyvg",
          "sqlExpression": "SUM(Total_Sales)/SUM(TotalTarget)"
        }, false]],
        "annotation_layers": [],
        "row_limit": 50000,
        "series_limit": 0,
        "series_limit_metric": {
          "aggregate": null,
          "column": null,
          "datasourceWarning": false,
          "expressionType": "SQL",
          "hasCustomLabel": false,
          "label": "SUM(Total_Sales)/SUM(TotalTarget)",
          "optionName": "metric_yxkx9ru5wt_i19cupllyvg",
          "sqlExpression": "SUM(Total_Sales)/SUM(TotalTarget)"
        },
        "order_desc": true,
        "url_params": {
          "dashboard_page_id": "kSc6pUD3su3tG4AiF9e_t",
          "form_data_key": "74Ez7VROrlk",
          "slice_id": "165"
        },
        "custom_params": {},
        "custom_form_data": {},
        "post_processing": [],
        "time_offsets": []
      }],
      "form_data": {
        "datasource": "324__table",
        "viz_type": "table",
        "slice_id": 165,
        "url_params": {
          "dashboard_page_id": "kSc6pUD3su3tG4AiF9e_t",
          "form_data_key": "74Ez7VROrlk",
          "slice_id": "165"
        },
        "query_mode": "aggregate",
        "groupby": ["ROTA"],
        "temporal_columns_lookup": {},
        "metrics": [{
          "aggregate": null,
          "column": null,
          "datasourceWarning": false,
          "expressionType": "SQL",
          "hasCustomLabel": true,
          "label": "Oran %",
          "optionName": "metric_993uygn1uqo_ctf06qduzct",
          "sqlExpression": "SUM(Total_Sales)/SUM(TotalTarget)"
        }],
        "all_columns": [],
        "percent_metrics": [],
        "adhoc_filters": [
          {
            "expressionType": "SQL",
            "sqlExpression": "FISCALMONTH >= toMonth(today()) AND FISCALYEAR >= toYear(today())",
            "clause": "WHERE", "subject": null, "operator": null, "comparator": null,
            "isExtra": false, "isNew": false, "datasourceWarning": false,
            "filterOptionName": "filter_u5uftfl8a5m_bw67w9zg3s"
          },
          {
            "expressionType": "SQL",
            "sqlExpression": "`ROTA` NOT ILIKE '%Telesell%'\\nAND `ROTA` NOT ILIKE '%Hospitality%' AND ROTA NOT ILIKE '%LZM%'",
            "clause": "WHERE", "subject": null, "operator": null, "comparator": null,
            "isExtra": false, "isNew": false, "datasourceWarning": false,
            "filterOptionName": "filter_b5bifiolntv_y5zy3ugrjar"
          },
          {
            "expressionType": "SIMPLE", "subject": "ROTA", "operator": "IN", "operatorId": "IN",
            "comparator": ["PRESELL ON KIZILAY BÖLGESI"], "clause": "WHERE",
            "sqlExpression": null, "isExtra": false, "isNew": false, "datasourceWarning": false,
            "filterOptionName": "filter_tin41hxliof_m7f0iyj3j6p"
          }
        ],
        "timeseries_limit_metric": {
          "aggregate": null, "column": null, "datasourceWarning": false,
          "expressionType": "SQL", "hasCustomLabel": false,
          "label": "SUM(Total_Sales)/SUM(TotalTarget)",
          "optionName": "metric_yxkx9ru5wt_i19cupllyvg",
          "sqlExpression": "SUM(Total_Sales)/SUM(TotalTarget)"
        },
        "order_by_cols": [],
        "order_desc": true,
        "row_limit": 50000,
        "server_page_length": 10,
        "table_timestamp_format": "smart_date",
        "allow_render_html": true,
        "column_config": { "Oran %": { "d3NumberFormat": ".2%", "d3SmallNumberFormat": ".2%" } },
        "show_cell_bars": false,
        "color_pn": true,
        "comparison_color_scheme": "Green",
        "conditional_formatting": [],
        "comparison_type": "values",
        "extra_form_data": {},
        "force": false,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                // oturum cookie’nizi buraya koyun:
                .addHeader("Cookie", cookie)
                .build();


        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) throw new IOException("HTTP " + resp.code());
            String body = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(body);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget12AggregationRequest() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A643%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        // cURL body – FISCALMONTH HER YERDE TIRNAKSIZ!
        String body = """
    {
      "datasource":{"id":358,"type":"table"},
      "force":false,
      "queries":[
        {
          "filters":[{"col":"ROTA","op":"IN","val":["PRESELL ON KIZILAY BÖLGESI"]}],
          "extras":{
            "having":"",
            "where":"(upperUTF8(replaceAll(replaceAll(MY, 'ı', 'i'), 'İ', 'I')) LIKE upperUTF8(replaceAll(replaceAll({% if url_param('MY') %}\\r\\n\\t'{{ url_param('MY') }}'\\r\\n{% else %}\\r\\n  MY\\r\\n{% endif %}, 'ı', 'i'), 'İ', 'I'))) AND (upperUTF8(replaceAll(replaceAll(ROTA, 'ı', 'i'), 'İ', 'I')) LIKE upperUTF8(replaceAll(replaceAll({% if url_param('ROUTE') %}\\r\\n\\t'{{ url_param('ROUTE') }}'\\r\\n{% else %}\\r\\n  ROTA\\r\\n{% endif %}, 'ı', 'i'), 'İ', 'I'))) AND (toDate(CONCAT(FISCALYEAR,'-',FISCALMONTH,'-1')) >= '2024-11-01') AND (ROTA NOT ILIKE '%LZM%')"
          },
          "applied_time_extras":{},
          "columns":[{"columnType":"BASE_AXIS","datasourceWarning":false,"expressionType":"SQL","label":"FISCALMONTH","sqlExpression":"toDate(CONCAT(FISCALYEAR,'-',FISCALMONTH,'-1'))"}],
          "metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_gf934wxhf6i_dgxgeysbz2p","sqlExpression":"SUM(TotalTarget)"}],
          "annotation_layers":[],
          "row_limit":100,
          "series_columns":[],
          "series_limit":0,
          "url_params":{"dashboard_page_id":"m4S0CI_9IdgWhsCtW_xAr","slice_id":"643"},
          "custom_params":{},
          "custom_form_data":{},
          "time_offsets":[],
          "post_processing":[
            {"operation":"pivot","options":{"index":["FISCALMONTH"],"columns":[],"aggregates":{"İç Hedef":{"operator":"mean"}},"drop_missing_columns":true}},
            {"operation":"flatten"}
          ],
          "orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_gf934wxhf6i_dgxgeysbz2p","sqlExpression":"SUM(TotalTarget)"},false]]
        },
        {
          "filters":[{"col":"ROTA","op":"IN","val":["PRESELL ON KIZILAY BÖLGESI"]}],
          "extras":{
            "having":"",
            "where":"(upperUTF8(replaceAll(replaceAll(MY, 'ı', 'i'), 'İ', 'I')) LIKE upperUTF8(replaceAll(replaceAll({% if url_param('MY') %}\\r\\n\\t'{{ url_param('MY') }}'\\r\\n{% else %}\\r\\n  MY\\r\\n{% endif %}, 'ı', 'i'), 'İ', 'I'))) AND (upperUTF8(replaceAll(replaceAll(ROTA, 'ı', 'i'), 'İ', 'I')) LIKE upperUTF8(replaceAll(replaceAll({% if url_param('ROUTE') %}\\r\\n\\t'{{ url_param('ROUTE') }}'\\r\\n{% else %}\\r\\n  ROTA\\r\\n{% endif %}, 'ı', 'i'), 'İ', 'I'))) AND (toDate(CONCAT(FISCALYEAR,'-',FISCALMONTH,'-1')) >= '2024-11-01') AND (ROTA NOT ILIKE '%LZM%')"
          },
          "applied_time_extras":{},
          "columns":[{"columnType":"BASE_AXIS","datasourceWarning":false,"expressionType":"SQL","label":"FISCALMONTH","sqlExpression":"toDate(CONCAT(FISCALYEAR,'-',FISCALMONTH,'-1'))"}],
          "metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme","optionName":"metric_y0bto0urhvs_q4mcb4h5k","sqlExpression":"SUM(Total_Sales)"}],
          "annotation_layers":[],
          "row_limit":10000,
          "series_columns":[],
          "series_limit":0,
          "url_params":{"dashboard_page_id":"m4S0CI_9IdgWhsCtW_xAr","slice_id":"643"},
          "custom_params":{},
          "custom_form_data":{},
          "time_offsets":[],
          "post_processing":[
            {"operation":"pivot","options":{"index":["FISCALMONTH"],"columns":[],"aggregates":{"Gerçekleşme":{"operator":"mean"}},"drop_missing_columns":true}},
            {"operation":"flatten"}
          ],
          "orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme","optionName":"metric_y0bto0urhvs_q4mcb4h5k","sqlExpression":"SUM(Total_Sales)"},false]]
        }
      ],
      "form_data":{
        "datasource":"358__table",
        "viz_type":"mixed_timeseries",
        "slice_id":643,
        "url_params":{"dashboard_page_id":"m4S0CI_9IdgWhsCtW_xAr","slice_id":"643"},
        "x_axis":{"datasourceWarning":false,"expressionType":"SQL","label":"FISCALMONTH","sqlExpression":"toDate(CONCAT(FISCALYEAR,'-',FISCALMONTH,'-1'))"},
        "metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_gf934wxhf6i_dgxgeysbz2p","sqlExpression":"SUM(TotalTarget)"}],
        "groupby":[],
        "adhoc_filters":[
          {"expressionType":"SQL","sqlExpression":"upperUTF8(replaceAll(replaceAll(MY, 'ı', 'i'), 'İ', 'I')) LIKE upperUTF8(replaceAll(replaceAll({% if url_param('MY') %}\\r\\n\\t'{{ url_param('MY') }}'\\r\\n{% else %}\\r\\n  MY\\r\\n{% endif %}, 'ı', 'i'), 'İ', 'I'))","clause":"WHERE","subject":null,"operator":null,"comparator":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_5xhp6l9pxzg_bcqhkgw55j5"},
          {"expressionType":"SQL","sqlExpression":"upperUTF8(replaceAll(replaceAll(ROTA, 'ı', 'i'), 'İ', 'I')) LIKE upperUTF8(replaceAll(replaceAll({% if url_param('ROUTE') %}\\r\\n\\t'{{ url_param('ROUTE') }}'\\r\\n{% else %}\\r\\n  ROTA\\r\\n{% endif %}, 'ı', 'i'), 'İ', 'I'))","clause":"WHERE","subject":null,"operator":null,"comparator":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_x4usy00xu49_zn79reoy5t"},
          {"expressionType":"SQL","sqlExpression":"toDate(CONCAT(FISCALYEAR,'-',FISCALMONTH,'-1')) >= '2024-11-01'","clause":"WHERE","subject":null,"operator":null,"comparator":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_ewtjht0hbpp_pr6qivzk6g"},
          {"expressionType":"SQL","sqlExpression":"ROTA NOT ILIKE '%LZM%'","clause":"WHERE","subject":null,"operator":null,"comparator":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_1ykz8ygnclm_6i2i8f1s8r"},
          {"expressionType":"SIMPLE","subject":"ROTA","operator":"IN","operatorId":"IN","comparator":["PRESELL ON KIZILAY BÖLGESI"],"clause":"WHERE","sqlExpression":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_rjy3xecn0r_t0hkpb5n64"}
        ],
        "order_desc":true,"row_limit":100,"truncate_metric":true,"comparison_type":"values",
        "annotation_layers":[],
        "x_axis_title":"","x_axis_title_margin":15,
        "y_axis_title":"","y_axis_title_margin":50,"y_axis_title_position":"Left",
        "color_scheme":"d3Category20","time_shift_color":true,
        "seriesType":"middle","show_value":true,"opacity":0.2,"markerEnabled":true,"markerSize":6,
        "seriesTypeB":"bar","show_valueB":false,"opacityB":0.2,"markerEnabledB":true,"markerSizeB":6,
        "show_legend":true,"legendType":"scroll","legendOrientation":"bottom",
        "x_axis_time_format":"%B","xAxisLabelRotation":45,"rich_tooltip":true,"showTooltipTotal":true,
        "tooltipTimeFormat":"smart_date","minorSplitLine":false,"truncateXAxis":true,"truncateYAxis":false,
        "y_axis_bounds":[null,null],"y_axis_format":"SMART_NUMBER",
        "logAxis":false,"y_axis_bounds_secondary":[null,null],"y_axis_format_secondary":"SMART_NUMBER",
        "extra_form_data":{},"force":false,"result_format":"json","result_type":"full"
      },
      "result_format":"json","result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=mqhGNHChnfI&dashboard_page_id=m4S0CI_9IdgWhsCtW_xAr&slice_id=643")
                .addHeader("User-Agent", "Mozilla/5.0")
                // cURL’de X-CSRFToken değersiz geliyor; aynısını yapıyoruz:
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static JSONObject sendWidget100AggregationRequest() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A353%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource":{"id":367,"type":"table"},
      "force":true,
      "queries":[
        {
          "filters":[],
          "extras":{
            "having":"",
            "where":"(TRHISLEMTARIHI = today()\\r\\n) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll({% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1])"
          },
          "applied_time_extras":{},
          "columns":[{"columnType":"BASE_AXIS","sqlExpression":"SM","label":"SM","expressionType":"SQL"}],
          "metrics":[
            {
              "aggregate":null,
              "column":null,
              "datasourceWarning":false,
              "expressionType":"SQL",
              "hasCustomLabel":true,
              "label":"İç Hedef",
              "optionName":"metric_ycupwirbsvn_1makve3y5zm",
              "sqlExpression":"CASE WHEN SUM(DailyTarget) < 0 THEN 0 ELSE SUM(DailyTarget) END"
            }
          ],
          "annotation_layers":[],
          "row_limit":10000,
          "series_columns":[],
          "series_limit":0,
          "url_params":{"dashboard_page_id":"gtPMUU2FhOWD087tm6s9o","slice_id":"353"},
          "custom_params":{},
          "custom_form_data":{},
          "time_offsets":[],
          "post_processing":[
            {"operation":"pivot","options":{"index":["SM"],"columns":[],"aggregates":{"İç Hedef":{"operator":"mean"}},"drop_missing_columns":true}},
            {"operation":"flatten"}
          ],
          "orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_ycupwirbsvn_1makve3y5zm","sqlExpression":"CASE WHEN SUM(DailyTarget) < 0 THEN 0 ELSE SUM(DailyTarget) END"},false]]
        },
        {
          "filters":[],
          "extras":{
            "having":"",
            "where":"(TRHISLEMTARIHI = today()\\r\\n) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll({% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1])"
          },
          "applied_time_extras":{},
          "columns":[{"columnType":"BASE_AXIS","sqlExpression":"SM","label":"SM","expressionType":"SQL"}],
          "metrics":[
            {
              "aggregate":null,
              "column":null,
              "datasourceWarning":false,
              "expressionType":"SQL",
              "hasCustomLabel":true,
              "label":"Satış (L)",
              "optionName":"metric_6fxllqqwlcq_ugz0ig4r9",
              "sqlExpression":"SUM(OrdersNHotSales)"
            }
          ],
          "annotation_layers":[],
          "row_limit":10000,
          "series_columns":[],
          "series_limit":0,
          "url_params":{"dashboard_page_id":"gtPMUU2FhOWD087tm6s9o","slice_id":"353"},
          "custom_params":{},
          "custom_form_data":{},
          "time_offsets":[],
          "post_processing":[
            {"operation":"pivot","options":{"index":["SM"],"columns":[],"aggregates":{"Satış (L)":{"operator":"mean"}},"drop_missing_columns":true}},
            {"operation":"flatten"}
          ],
          "orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_6fxllqqwlcq_ugz0ig4r9","sqlExpression":"SUM(OrdersNHotSales)"},false]]
        }
      ],
      "form_data":{
        "datasource":"367__table",
        "viz_type":"mixed_timeseries",
        "slice_id":353,
        "url_params":{"dashboard_page_id":"gtPMUU2FhOWD087tm6s9o","slice_id":"353"},
        "x_axis":"SM",
        "metrics":[
          {
            "aggregate":null,
            "column":null,
            "datasourceWarning":false,
            "expressionType":"SQL",
            "hasCustomLabel":true,
            "label":"İç Hedef",
            "optionName":"metric_ycupwirbsvn_1makve3y5zm",
            "sqlExpression":"CASE WHEN SUM(DailyTarget) < 0 THEN 0 ELSE SUM(DailyTarget) END"
          }
        ],
        "groupby":[],
        "adhoc_filters":[
          {"clause":"WHERE","expressionType":"SQL","sqlExpression":"TRHISLEMTARIHI = today()\\r\\n"},
          {"clause":"WHERE","expressionType":"SQL","sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll({% if url_param('BM') %}\\r\\n '{{ url_param('BM') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]"}
        ],
        "order_desc":true,
        "row_limit":10000,
        "truncate_metric":true,
        "comparison_type":"values",
        "annotation_layers":[],
        "x_axis_title_margin":15,
        "y_axis_title_margin":15,
        "y_axis_title_position":"Left",
        "color_scheme":"supersetColors",
        "time_shift_color":true,
        "seriesType":"bar",
        "show_value":true,
        "opacity":0.2,
        "markerSize":6,
        "seriesTypeB":"bar",
        "show_valueB":true,
        "opacityB":0.2,
        "markerSizeB":6,
        "show_legend":true,
        "legendType":"scroll",
        "legendOrientation":"top",
        "x_axis_time_format":"smart_date",
        "xAxisLabelRotation":90,
        "rich_tooltip":true,
        "showTooltipTotal":true,
        "tooltipTimeFormat":"smart_date",
        "truncateXAxis":true,
        "y_axis_bounds":[null,null],
        "y_axis_format":"SMART_NUMBER",
        "y_axis_bounds_secondary":[null,null],
        "y_axis_format_secondary":"SMART_NUMBER",
        "extra_form_data":{},
        "force":true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=h2GV-GlPSY8&dashboard_page_id=gtPMUU2FhOWD087tm6s9o&slice_id=353")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget25AggreationRequest() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A346%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource": {"id": 368, "type": "table"},
      "force": true,
      "queries": [
        {
          "filters": [],
          "extras": {
            "time_grain_sqla": "P1D",
            "having": "",
            "where": "(TRHISLEMTARIHI = today()) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1])"
          },
          "applied_time_extras": {},
          "columns": [
            {
              "timeGrain": "P1D",
              "columnType": "BASE_AXIS",
              "datasourceWarning": false,
              "expressionType": "SQL",
              "label": "Column",
              "sqlExpression": "TRHISLEMTARIHI"
            }
          ],
          "metrics": [
            {
              "aggregate": null,
              "column": null,
              "datasourceWarning": false,
              "expressionType": "SQL",
              "hasCustomLabel": true,
              "label": "İç Hedef",
              "optionName": "metric_sghnx7sduc8_lyz8geip0o",
              "sqlExpression": "CASE WHEN SUM(Distinct DailyTarget) < 0 THEN 0 ELSE SUM(Distinct DailyTarget) END"
            },
            {
              "aggregate": "SUM",
              "column": {
                "advanced_data_type": null,
                "certification_details": null,
                "certified_by": null,
                "column_name": "OrdersNHotSales",
                "description": null,
                "expression": null,
                "filterable": true,
                "groupby": true,
                "id": 6370,
                "is_certified": false,
                "is_dttm": false,
                "python_date_format": null,
                "type": "Nullable(Decimal(38, 14))",
                "type_generic": 0,
                "verbose_name": null,
                "warning_markdown": null
              },
              "datasourceWarning": false,
              "expressionType": "SIMPLE",
              "hasCustomLabel": true,
              "label": "Satış (L)",
              "optionName": "metric_8h4r3pkwafb_p4ml4u09rpi",
              "sqlExpression": null
            }
          ],
          "orderby": [
            [
              {
                "aggregate": null,
                "column": null,
                "datasourceWarning": false,
                "expressionType": "SQL",
                "hasCustomLabel": true,
                "label": "İç Hedef",
                "optionName": "metric_sghnx7sduc8_lyz8geip0o",
                "sqlExpression": "CASE WHEN SUM(Distinct DailyTarget) < 0 THEN 0 ELSE SUM(Distinct DailyTarget) END"
              },
              false
            ]
          ],
          "annotation_layers": [],
          "row_limit": 10000,
          "series_columns": [],
          "series_limit": 0,
          "order_desc": true,
          "url_params": {"dashboard_page_id": "vbi3uHRpl6RDtYlZ08lPP", "slice_id": "346"},
          "custom_params": {},
          "custom_form_data": {},
          "time_offsets": [],
          "post_processing": [
            {
              "operation": "pivot",
              "options": {
                "index": ["Column"],
                "columns": [],
                "aggregates": {
                  "İç Hedef": {"operator": "mean"},
                  "Satış (L)": {"operator": "mean"}
                },
                "drop_missing_columns": false
              }
            },
            {"operation": "flatten"}
          ]
        }
      ],
      "form_data": {
        "datasource": "368__table",
        "viz_type": "echarts_timeseries_bar",
        "slice_id": 346,
        "url_params": {"dashboard_page_id": "vbi3uHRpl6RDtYlZ08lPP", "slice_id": "346"},
        "x_axis": {
          "datasourceWarning": false,
          "expressionType": "SQL",
          "label": "Column",
          "sqlExpression": "TRHISLEMTARIHI"
        },
        "time_grain_sqla": "P1D",
        "x_axis_sort_asc": true,
        "x_axis_sort_series": "name",
        "x_axis_sort_series_ascending": true,
        "metrics": [
          {
            "aggregate": null,
            "column": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "hasCustomLabel": true,
            "label": "İç Hedef",
            "optionName": "metric_sghnx7sduc8_lyz8geip0o",
            "sqlExpression": "CASE WHEN SUM(Distinct DailyTarget) < 0 THEN 0 ELSE SUM(Distinct DailyTarget) END"
          },
          {
            "aggregate": "SUM",
            "column": {
              "advanced_data_type": null,
              "certification_details": null,
              "certified_by": null,
              "column_name": "OrdersNHotSales",
              "description": null,
              "expression": null,
              "filterable": true,
              "groupby": true,
              "id": 6370,
              "is_certified": false,
              "is_dttm": false,
              "python_date_format": null,
              "type": "Nullable(Decimal(38, 14))",
              "type_generic": 0,
              "verbose_name": null,
              "warning_markdown": null
            },
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "hasCustomLabel": true,
            "label": "Satış (L)",
            "optionName": "metric_8h4r3pkwafb_p4ml4u09rpi",
            "sqlExpression": null
          }
        ],
        "groupby": [],
        "adhoc_filters": [
          {
            "clause": "WHERE",
            "comparator": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "filterOptionName": "filter_4qlcx7z0p6r_g4ndh7e130t",
            "isExtra": false,
            "isNew": false,
            "operator": "TEMPORAL_RANGE",
            "operatorId": "TEMPORAL_RANGE",
            "sqlExpression": "TRHISLEMTARIHI = today()",
            "subject": "TRHISLEMTARIHI"
          },
          {
            "clause": "WHERE",
            "comparator": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "filterOptionName": "filter_900tax7vbbn_0ec9wuhaxphq",
            "isExtra": false,
            "isNew": false,
            "operator": null,
            "sqlExpression": "splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]",
            "subject": null
          }
        ],
        "order_desc": true,
        "row_limit": 10000,
        "truncate_metric": true,
        "show_empty_columns": true,
        "comparison_type": "values",
        "annotation_layers": [],
        "forecastPeriods": 10,
        "forecastInterval": 0.8,
        "orientation": "vertical",
        "x_axis_title_margin": 15,
        "y_axis_title": "",
        "y_axis_title_margin": "45",
        "y_axis_title_position": "Left",
        "sort_series_type": "name",
        "sort_series_ascending": true,
        "color_scheme": "bnbColors",
        "time_shift_color": true,
        "show_value": true,
        "stack": null,
        "only_total": true,
        "show_legend": true,
        "legendType": "scroll",
        "legendOrientation": "bottom",
        "x_axis_time_format": "%d/%m/%Y",
        "xAxisLabelRotation": 0,
        "y_axis_format": "SMART_NUMBER",
        "truncateYAxis": false,
        "y_axis_bounds": [null, null],
        "truncateXAxis": true,
        "rich_tooltip": true,
        "showTooltipTotal": true,
        "tooltipTimeFormat": "%d/%m/%Y",
        "extra_form_data": {},
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=KrLrInyunDg&dashboard_page_id=vbi3uHRpl6RDtYlZ08lPP&slice_id=346")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget51Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1835%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");
        // İçinde bulunduğumuz yıl/ay
        int currentYear  = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        String template = """
    {
      "datasource":{"id":330,"type":"table"},
      "force":false,
      "queries":[
        {
          "filters":[
            {"col":"ProductQuality","op":"IN","val":["Ultra Premium","Premium","Semi Premium"]},
            {"col":"FISCALYEAR","op":"IN","val":[${YEAR}]},
            {"col":"FISCALMONTH","op":"IN","val":[${MONTH}]}
          ],
          "extras":{
            "time_grain_sqla":"P1D",
            "having":"",
            "where":"(upperUTF8(replaceAll(replaceAll(SM, 'ı', 'i'), 'İ', 'I')) LIKE upperUTF8(replaceAll(replaceAll({% if url_param('SM') %}\\r\\n\\t'{{ url_param('SM') }}'\\r\\n{% else %}\\r\\n  'Trakya'\\r\\n{% endif %}, 'ı', 'i'), 'İ', 'I'))) AND (ROTA NOT ILIKE '%LZM%') AND (ProductCat NOT IN ('Şarap'))"
          },
          "applied_time_extras":{},
          "columns":[
            {
              "datasourceWarning":false,
              "expressionType":"SQL",
              "label":"Ürün Kategori",
              "sqlExpression":"CASE WHEN ProductCat = 'Rakı' THEN 'Premium Rakı'\\nWHEN ProductCat = 'Viski' THEN 'Premium Viski'\\nELSE 'Premium Diğer' END\\n"
            }
          ],
          "metrics":[
            {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_massxws5la_ljiii0bepu","sqlExpression":"SUM(TotalTarget)"},
            {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme","optionName":"metric_6g3co30fvdr_tijy1lnx78","sqlExpression":"SUM(Total_Sales)"},
            {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme %","optionName":"metric_6znao65vssp_yshas122kff","sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)*100.0"}
          ],
          "orderby":[[
            {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_massxws5la_ljiii0bepu","sqlExpression":"SUM(TotalTarget)"},
            false
          ]],
          "annotation_layers":[],
          "row_limit":50000,
          "series_limit":0,
          "order_desc":true,
          "url_params":{"dashboard_page_id":"LGkY0mAl2wdVDf57wBtIC","slice_id":"1835"},
          "custom_params":{},
          "custom_form_data":{}
        }
      ],
      "form_data":{
        "datasource":"330__table",
        "viz_type":"pivot_table_v2",
        "slice_id":1835,
        "url_params":{"dashboard_page_id":"LGkY0mAl2wdVDf57wBtIC","slice_id":"1835"},
        "groupbyColumns":[
          {
            "datasourceWarning":false,
            "expressionType":"SQL",
            "label":"Ürün Kategori",
            "sqlExpression":"CASE WHEN ProductCat = 'Rakı' THEN 'Premium Rakı'\\nWHEN ProductCat = 'Viski' THEN 'Premium Viski'\\nELSE 'Premium Diğer' END\\n"
          }
        ],
        "groupbyRows":[],
        "time_grain_sqla":"P1D",
        "temporal_columns_lookup":{},
        "metrics":[
          {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_massxws5la_ljiii0bepu","sqlExpression":"SUM(TotalTarget)"},
          {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme","optionName":"metric_6g3co30fvdr_tijy1lnx78","sqlExpression":"SUM(Total_Sales)"},
          {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme %","optionName":"metric_6znao65vssp_yshas122kff","sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)*100.0"}
        ],
        "metricsLayout":"COLUMNS",
        "adhoc_filters":[
          {"clause":"WHERE","comparator":["Ultra Premium","Premium","Semi Premium"],"datasourceWarning":false,"expressionType":"SIMPLE","filterOptionName":"filter_jvfi42wgjf_2zxzs69n769","isExtra":false,"isNew":false,"operator":"IN","operatorId":"IN","sqlExpression":null,"subject":"ProductQuality"},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_rdzr3eh44ni_6ygocok1l7","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"upperUTF8(replaceAll(replaceAll(SM, 'ı', 'i'), 'İ', 'I')) LIKE upperUTF8(replaceAll(replaceAll({% if url_param('SM') %}\\r\\n\\t'{{ url_param('SM') }}'\\r\\n{% else %}\\r\\n  'Trakya'\\r\\n{% endif %}, 'ı', 'i'), 'İ', 'I'))","subject":null},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_nrrjalzwplh_rmauyri5qz","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ROTA NOT ILIKE '%LZM%'","subject":null},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_qq730cb5usd_be75k716vs","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ProductCat NOT IN ('Şarap')","subject":null},
          {"expressionType":"SIMPLE","clause":"WHERE","operator":"IN","operatorId":"IN","subject":"FISCALYEAR","comparator":[2025],"isExtra":true,"filterOptionName":"filter_sf3x4ybl6d_m38a6k0jhns"},
          {"expressionType":"SIMPLE","clause":"WHERE","operator":"IN","operatorId":"IN","subject":"FISCALMONTH","comparator":[10],"isExtra":true,"filterOptionName":"filter_3sxiau87bd1_xjuyknbh7cs"}
        ],
        "row_limit":50000,
        "order_desc":true,
        "aggregateFunction":"Sum",
        "rowTotals":false,
        "rowSubTotals":true,
        "colTotals":true,
        "colSubTotals":true,
        "transposePivot":true,
        "valueFormat":"SMART_NUMBER",
        "date_format":"smart_date",
        "rowOrder":"key_a_to_z",
        "colOrder":"key_a_to_z",
        "conditional_formatting":[],
        "allow_render_html":true,
        "extra_form_data":{},
        "force":false,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        String body = template
                .replace("${YEAR}", String.valueOf(currentYear))
                .replace("${MONTH}", String.valueOf(currentMonth));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=BdHB4gq4Z_4&dashboard_page_id=LGkY0mAl2wdVDf57wBtIC&slice_id=1835")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget1Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A616%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource":{"id":306,"type":"table"},
      "force":false,
      "queries":[
        {
          "filters":[],
          "extras":{
            "having":"",
            "where":"(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM_Code, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('dashboard_filter_bm') %}\\r\\n '{{ url_param('dashboard_filter_bm') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1])"
          },
          "applied_time_extras":{},
          "columns":["BM_Name"],
          "metrics":["count"],
          "annotation_layers":[],
          "row_limit":10000,
          "series_limit":0,
          "order_desc":true,
          "url_params":{"slice_id":"616"},
          "custom_params":{},
          "custom_form_data":{}
        }
      ],
      "form_data":{
        "datasource":"306__table",
        "viz_type":"word_cloud",
        "slice_id":616,
        "url_params":{"slice_id":"616"},
        "series":"BM_Name",
        "metric":"count",
        "adhoc_filters":[
          {
            "clause":"WHERE",
            "comparator":null,
            "datasourceWarning":false,
            "expressionType":"SQL",
            "filterOptionName":"filter_8bsy7ln9scl_aqpm07085yu",
            "isExtra":false,
            "isNew":false,
            "operator":null,
            "sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM_Code, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('dashboard_filter_bm') %}\\r\\n '{{ url_param('dashboard_filter_bm') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]"
          }
        ],
        "row_limit":10000,
        "size_from":10,
        "size_to":70,
        "rotation":"flat",
        "color_scheme":"bnbColors",
        "extra_form_data":{},
        "force":false,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=KarEKeBeMnQ&slice_id=616")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget2Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A617%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource": {"id":306,"type":"table"},
      "force": false,
      "queries": [
        {
          "filters": [],
          "extras": {
            "time_grain_sqla": "P1D",
            "having": "",
            "where": "(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM_Code, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('dashboard_filter_bm') %}\\r\\n '{{ url_param('dashboard_filter_bm') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1])"
          },
          "applied_time_extras": {},
          "columns": [
            "BM",
            {
              "expressionType": "SQL",
              "label": "Email",
              "sqlExpression": "BM_Email"
            }
          ],
          "metrics": ["count"],
          "orderby": [["count", false]],
          "annotation_layers": [],
          "row_limit": 1000,
          "series_limit": 0,
          "order_desc": true,
          "url_params": {"slice_id":"617"},
          "custom_params": {},
          "custom_form_data": {}
        }
      ],
      "form_data": {
        "datasource": "306__table",
        "viz_type": "pivot_table_v2",
        "slice_id": 617,
        "url_params": {"slice_id":"617"},
        "groupbyColumns": [],
        "groupbyRows": [
          "BM",
          {
            "expressionType": "SQL",
            "label": "Email",
            "sqlExpression": "BM_Email"
          }
        ],
        "time_grain_sqla": "P1D",
        "temporal_columns_lookup": {},
        "metrics": ["count"],
        "metricsLayout": "COLUMNS",
        "adhoc_filters": [
          {
            "clause": "WHERE",
            "comparator": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "filterOptionName": "filter_8u5av5vmmd9_pp8d7e9zhkh",
            "isExtra": false,
            "isNew": false,
            "operator": null,
            "sqlExpression": "splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM_Code, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('dashboard_filter_bm') %}\\r\\n '{{ url_param('dashboard_filter_bm') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]"
          }
        ],
        "row_limit": 1000,
        "order_desc": true,
        "aggregateFunction": "Sum",
        "transposePivot": true,
        "valueFormat": "SMART_NUMBER",
        "date_format": "smart_date",
        "rowOrder": "key_a_to_z",
        "colOrder": "key_a_to_z",
        "allow_render_html": true,
        "extra_form_data": {},
        "force": false,
        "result_format": "json",
        "result_type": "results"
      },
      "result_format": "json",
      "result_type": "results"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=VAgtQ2N-Fds&slice_id=617")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget3Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A613%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource": {"id":235,"type":"table"},
      "force": true,
      "queries": [
        {
          "filters": [],
          "extras": {
            "having": "",
            "where": "(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('dashboard_filter_bm') %}\\r\\n '{{ url_param('dashboard_filter_bm') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1])"
          },
          "applied_time_extras": {},
          "columns": [
            {
              "datasourceWarning": false,
              "expressionType": "SQL",
              "label": "My column",
              "sqlExpression": "BM_LGD"
            }
          ],
          "metrics": [
            {
              "aggregate": "COUNT_DISTINCT",
              "column": {
                "advanced_data_type": null,
                "changed_on": "2025-04-23T15:36:55.513172",
                "column_name": "SM",
                "created_on": "2025-04-23T15:36:55.513169",
                "description": null,
                "expression": null,
                "extra": null,
                "filterable": true,
                "groupby": true,
                "id": 7071,
                "is_active": true,
                "is_dttm": false,
                "python_date_format": null,
                "type": "String",
                "type_generic": 1,
                "uuid": "b495ab96-2a2f-4555-a6df-f113fb6008cf",
                "verbose_name": null
              },
              "datasourceWarning": false,
              "expressionType": "SIMPLE",
              "hasCustomLabel": false,
              "label": "COUNT_DISTINCT(SM)",
              "optionName": "metric_5cpklbu3pya_7n7pnkm5cm7",
              "sqlExpression": null
            }
          ],
          "annotation_layers": [],
          "row_limit": 100,
          "series_limit": 0,
          "order_desc": true,
          "url_params": {"dashboard_page_id":"Yn9pIQiFlhfBwFAQkmiHs","slice_id":"613"},
          "custom_params": {},
          "custom_form_data": {}
        }
      ],
      "form_data": {
        "datasource": "235__table",
        "viz_type": "word_cloud",
        "slice_id": 613,
        "url_params": {"dashboard_page_id":"Yn9pIQiFlhfBwFAQkmiHs","slice_id":"613"},
        "series": {
          "datasourceWarning": false,
          "expressionType": "SQL",
          "label": "My column",
          "sqlExpression": "BM_LGD"
        },
        "metric": {
          "aggregate": "COUNT_DISTINCT",
          "column": {
            "advanced_data_type": null,
            "changed_on": "2025-04-23T15:36:55.513172",
            "column_name": "SM",
            "created_on": "2025-04-23T15:36:55.513169",
            "description": null,
            "expression": null,
            "extra": null,
            "filterable": true,
            "groupby": true,
            "id": 7071,
            "is_active": true,
            "is_dttm": false,
            "python_date_format": null,
            "type": "String",
            "type_generic": 1,
            "uuid": "b495ab96-2a2f-4555-a6df-f113fb6008cf",
            "verbose_name": null
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": false,
          "label": "COUNT_DISTINCT(SM)",
          "optionName": "metric_5cpklbu3pya_7n7pnkm5cm7",
          "sqlExpression": null
        },
        "adhoc_filters": [
          {
            "clause": "WHERE",
            "comparator": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "filterOptionName": "filter_pal1alm1wf9_ikr5hq2fus",
            "isExtra": false,
            "isNew": false,
            "operator": null,
            "sqlExpression": "splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('dashboard_filter_bm') %}\\r\\n '{{ url_param('dashboard_filter_bm') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]"
          }
        ],
        "row_limit": 100,
        "size_from": 10,
        "size_to": 70,
        "rotation": "flat",
        "color_scheme": "supersetColors",
        "extra_form_data": {},
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=HvmQzk1xiSw&dashboard_page_id=Yn9pIQiFlhfBwFAQkmiHs&slice_id=613")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget9Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A614%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource": {"id":289,"type":"table"},
  "force": false,
  "queries": [
    {
      "filters": [
        {"col":"sf.BYTDURUM","op":"IN","val":["0"]}
      ],
      "extras": {
        "time_grain_sqla":"P1D",
        "having":"",
        "where":"(TRHISLEMTARIHI >= today()-9) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(MUSTERI_BOLGE_1_ACIKLAMA, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('dashboard_filter_bm') %}\\r\\n      '{{ url_param('dashboard_filter_bm') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (MUSTERI_ROTA_ICI_KANAL_KOD NOT IN (4) OR MUSTERI_ROTA_ICI_KANAL_KOD IS NULL) AND (EK_ROUTE_NAME NOT ILIKE '%LZM%')"
      },
      "applied_time_extras": {},
      "columns": [
        {
          "timeGrain":"P1D",
          "columnType":"BASE_AXIS",
          "sqlExpression":"TRHISLEMTARIHI",
          "label":"TRHISLEMTARIHI",
          "expressionType":"SQL"
        }
      ],
      "metrics": [
        {
          "aggregate": null,
          "column": null,
          "datasourceWarning": false,
          "expressionType": "SQL",
          "hasCustomLabel": true,
          "label": "Satış (L)",
          "optionName": "metric_snm5jhpgv_ch0nh6zmlp",
          "sqlExpression": " SUM(\\r\\n            IF(\\r\\n                BYTTUR IN (0,3), \\r\\n                URUN_AMBALAJ_LITRE * INVOICE_QUANTITY, \\r\\n                IF(BYTTUR IN (2, 4), -1 * URUN_AMBALAJ_LITRE * INVOICE_QUANTITY, 0)\\r\\n            )\\r\\n        )"
        }
      ],
      "orderby": [
        [
          {
            "aggregate": null,
            "column": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "hasCustomLabel": true,
            "label": "Satış (L)",
            "optionName": "metric_snm5jhpgv_ch0nh6zmlp",
            "sqlExpression": " SUM(\\r\\n            IF(\\r\\n                BYTTUR IN (0,3), \\r\\n                URUN_AMBALAJ_LITRE * INVOICE_QUANTITY, \\r\\n                IF(BYTTUR IN (2, 4), -1 * URUN_AMBALAJ_LITRE * INVOICE_QUANTITY, 0)\\r\\n            )\\r\\n        )"
          },
          false
        ]
      ],
      "annotation_layers": [],
      "row_limit": 10000,
      "series_columns": [],
      "series_limit": 0,
      "order_desc": true,
      "url_params": {
        "dashboard_page_id":"Yn9pIQiFlhfBwFAQkmiHs",
        "form_data_key":"z7umYDQ6gOU",
        "slice_id":"614"
      },
      "custom_params": {},
      "custom_form_data": {},
      "time_offsets": [],
      "post_processing": [
        {
          "operation":"pivot",
          "options":{
            "index":["TRHISLEMTARIHI"],
            "columns":[],
            "aggregates":{"Satış (L)":{"operator":"mean"}},
            "drop_missing_columns":false
          }
        },
        {"operation":"flatten"}
      ]
    }
  ],
  "form_data": {
    "datasource":"289__table",
    "viz_type":"echarts_timeseries_bar",
    "slice_id":614,
    "url_params":{"dashboard_page_id":"Yn9pIQiFlhfBwFAQkmiHs","form_data_key":"z7umYDQ6gOU","slice_id":"614"},
    "x_axis":"TRHISLEMTARIHI",
    "time_grain_sqla":"P1D",
    "metrics":[
      {
        "aggregate":null,
        "column":null,
        "datasourceWarning":false,
        "expressionType":"SQL",
        "hasCustomLabel":true,
        "label":"Satış (L)",
        "optionName":"metric_snm5jhpgv_ch0nh6zmlp",
        "sqlExpression":" SUM(\\r\\n            IF(\\r\\n                BYTTUR IN (0,3), \\r\\n                URUN_AMBALAJ_LITRE * INVOICE_QUANTITY, \\r\\n                IF(BYTTUR IN (2, 4), -1 * URUN_AMBALAJ_LITRE * INVOICE_QUANTITY, 0)\\r\\n            )\\r\\n        )"
      }
    ],
    "groupby":[],
    "adhoc_filters":[
      {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_yd6d2mtg61_jmdpxyp4qvp","isExtra":false,"isNew":false,"operator":"TEMPORAL_RANGE","sqlExpression":"TRHISLEMTARIHI >= today()-9","subject":"TRHISLEMTARIHI"},
      {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_sazau3qly9_utbjjah4tz","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(MUSTERI_BOLGE_1_ACIKLAMA, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('dashboard_filter_bm') %}\\r\\n      '{{ url_param('dashboard_filter_bm') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","subject":null},
      {"clause":"WHERE","comparator":["0"],"datasourceWarning":false,"expressionType":"SIMPLE","filterOptionName":"filter_9b0qy9uz9vl_6tve85l5k4c","isExtra":false,"isNew":false,"operator":"IN","operatorId":"IN","sqlExpression":null,"subject":"sf.BYTDURUM"},
      {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_ba23yc8qae_tkwy7d7lwiq","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"MUSTERI_ROTA_ICI_KANAL_KOD NOT IN (4) OR MUSTERI_ROTA_ICI_KANAL_KOD IS NULL","subject":null},
      {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_3x1vp8ip6e7_f5pzsp2vgu","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"EK_ROUTE_NAME NOT ILIKE '%LZM%'","subject":null}
    ],
    "row_limit":10000,
    "truncate_metric":true,
    "show_empty_columns":true,
    "comparison_type":"values",
    "annotation_layers":[],
    "forecastPeriods":10,
    "forecastInterval":0.8,
    "orientation":"vertical",
    "x_axis_title_margin":15,
    "y_axis_title_margin":15,
    "y_axis_title_position":"Left",
    "sort_series_type":"sum",
    "color_scheme":"supersetColors",
    "time_shift_color":true,
    "show_value":true,
    "only_total":true,
    "show_legend":true,
    "legendType":"scroll",
    "legendOrientation":"bottom",
    "x_axis_time_format":"smart_date",
    "xAxisLabelRotation":0,
    "y_axis_format":"SMART_NUMBER",
    "y_axis_bounds":[null,null],
    "truncateXAxis":true,
    "rich_tooltip":true,
    "showTooltipTotal":true,
    "tooltipTimeFormat":"%d/%m/%Y",
    "extra_form_data":{},
    "force":false,
    "result_format":"json",
    "result_type":"results"
  },
  "result_format":"json",
  "result_type":"results"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=z7umYDQ6gOU&dashboard_page_id=Yn9pIQiFlhfBwFAQkmiHs&slice_id=614")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    public static JSONObject sendWidget4Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A615%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource":{"id":356,"type":"table"},
      "force":true,
      "queries":[
        {
          "filters":[],
          "extras":{
            "having":"",
            "where":"(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('dashboard_filter_bm') %}\\r\\n '{{ url_param('dashboard_filter_bm') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (FISCALMONTH >= toMonth(today())) AND (FISCALYEAR >= toYear(today()))"
          },
          "applied_time_extras":{},
          "columns":[],
          "metrics":[
            {
              "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL",
              "hasCustomLabel":true,"label":"Başarılı","optionName":"metric_g1fa91tszrj_m7q1zlnect8",
              "sqlExpression":"SUM(BASARILI_ZIYARET_SAYISI)/SUM(GERCEKLESEN_ZIYARET_SAYISI)*1.0"
            }
          ],
          "annotation_layers":[],
          "series_limit":0,
          "order_desc":true,
          "url_params":{"dashboard_page_id":"Yn9pIQiFlhfBwFAQkmiHs","slice_id":"615"},
          "custom_params":{},
          "custom_form_data":{}
        }
      ],
      "form_data":{
        "datasource":"356__table",
        "viz_type":"big_number_total",
        "slice_id":615,
        "url_params":{"dashboard_page_id":"Yn9pIQiFlhfBwFAQkmiHs","slice_id":"615"},
        "metric":{
          "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL",
          "hasCustomLabel":true,"label":"Başarılı","optionName":"metric_g1fa91tszrj_m7q1zlnect8",
          "sqlExpression":"SUM(BASARILI_ZIYARET_SAYISI)/SUM(GERCEKLESEN_ZIYARET_SAYISI)*1.0"
        },
        "adhoc_filters":[
          {
            "clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL",
            "filterOptionName":"filter_vhinj7ogak_jjz4zib5vxk","isExtra":false,"isNew":false,"operator":null,
            "sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('dashboard_filter_bm') %}\\r\\n '{{ url_param('dashboard_filter_bm') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]"
          },
          {
            "clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL",
            "filterOptionName":"filter_c3ij1ssgijb_te0qfivxw8","isExtra":false,"isNew":false,"operator":"TEMPORAL_RANGE",
            "sqlExpression":"FISCALMONTH >= toMonth(today())","subject":"TARIH"
          },
          {
            "clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL",
            "filterOptionName":"filter_s7pw297rsp_elrc9lql4p7","isExtra":false,"isNew":false,"operator":null,
            "sqlExpression":"FISCALYEAR >= toYear(today())"
          }
        ],
        "header_font_size":0.4,
        "subheader_font_size":0.15,
        "y_axis_format":".2%",
        "time_format":"smart_date",
        "conditional_formatting":[],
        "extra_form_data":{},
        "force":true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=Whl6bNGTPk0&dashboard_page_id=Yn9pIQiFlhfBwFAQkmiHs&slice_id=615")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget8Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A627%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource":{"id":356,"type":"table"},
      "force":true,
      "queries":[
        {
          "filters":[],
          "extras":{
            "time_grain_sqla":"P1M",
            "having":"",
            "where":"(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('dashboard_filter_bm') %}\\r\\n '{{ url_param('dashboard_filter_bm') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1])"
          },
          "applied_time_extras":{},
          "columns":[
            {"timeGrain":"P1M","columnType":"BASE_AXIS","datasourceWarning":false,"expressionType":"SQL","label":"My column","sqlExpression":"makeDate(FISCALYEAR,FISCALMONTH,1)"}
          ],
          "metrics":[
            {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Başarılı Ziyaret","optionName":"metric_wac12dl0y2_r7azfkxy0zk","sqlExpression":"SUM(BASARILI_ZIYARET_SAYISI)/SUM(GERCEKLESEN_ZIYARET_SAYISI)*1.0"}
          ],
          "annotation_layers":[],
          "series_limit":0,
          "order_desc":true,
          "url_params":{"dashboard_page_id":"Yn9pIQiFlhfBwFAQkmiHs","slice_id":"627"},
          "custom_params":{},
          "custom_form_data":{},
          "post_processing":[
            {"operation":"pivot","options":{"index":["My column"],"columns":[],"aggregates":{"Başarılı Ziyaret":{"operator":"mean"}},"drop_missing_columns":true}},
            {"operation":"flatten"}
          ]
        }
      ],
      "form_data":{
        "datasource":"356__table",
        "viz_type":"big_number",
        "slice_id":627,
        "url_params":{"dashboard_page_id":"Yn9pIQiFlhfBwFAQkmiHs","slice_id":"627"},
        "x_axis":{"datasourceWarning":false,"expressionType":"SQL","label":"My column","sqlExpression":"makeDate(FISCALYEAR,FISCALMONTH,1)"},
        "time_grain_sqla":"P1M",
        "metric":{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Başarılı Ziyaret","optionName":"metric_wac12dl0y2_r7azfkxy0zk","sqlExpression":"SUM(BASARILI_ZIYARET_SAYISI)/SUM(GERCEKLESEN_ZIYARET_SAYISI)*1.0"},
        "adhoc_filters":[
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_3yuyv7ymz32_21yuvxl26yq","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('dashboard_filter_bm') %}\\r\\n '{{ url_param('dashboard_filter_bm') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","subject":null}
        ],
        "compare_lag":1,
        "compare_suffix":"MoM",
        "show_trend_line":true,
        "start_y_axis_at_zero":true,
        "color_picker":{"a":1,"b":135,"g":122,"r":0},
        "header_font_size":0.4,
        "subheader_font_size":0.15,
        "y_axis_format":".2%",
        "time_format":"smart_date",
        "rolling_type":"None",
        "extra_form_data":{},
        "force":true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=yzTwQL4GE98&dashboard_page_id=Yn9pIQiFlhfBwFAQkmiHs&slice_id=627")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget10Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A621%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource": {"id":318,"type":"table"},
      "force": true,
      "queries": [
        {
          "filters": [],
          "extras": {
            "time_grain_sqla": "P1W",
            "having": "",
            "where": "(TRHISLEMTARIHI >= toStartOfWeek(today()) - INTERVAL 3 MONTHS) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('dashboard_filter_bm') %}\\r\\n '{{ url_param('dashboard_filter_bm') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (ProductQuality IN ('Super Premium','Premium Plus','Ultra Premium','Premium')) AND (ProductCat NOT IN ('Şarap')) AND (ROTA NOT ILIKE '%LZM%') AND (TIP NOT IN (4) OR TIP IS NULL)"
          },
          "applied_time_extras": {},
          "columns": [
            {"timeGrain":"P1W","columnType":"BASE_AXIS","sqlExpression":"TRHISLEMTARIHI","label":"TRHISLEMTARIHI","expressionType":"SQL"},
            {"expressionType":"SQL","label":"Kalite Segment","sqlExpression":"CASE WHEN ProductQuality IN ('Super Premium','Premium Plus','Ultra Premium','Premium') THEN 'Prem' ELSE 'Diğer' END"}
          ],
          "metrics": [
            {"aggregate":"SUM","column":{"advanced_data_type":null,"changed_on":"2024-12-05T06:47:07.971649","column_name":"Total_Sales","created_on":"2024-12-05T06:47:01.671351","description":null,"expression":"","extra":null,"filterable":true,"groupby":true,"id":4809,"is_active":true,"is_dttm":false,"python_date_format":null,"type":"Nullable(Decimal(38, 14))","type_generic":0,"uuid":"304bef9b-93a2-4640-89a4-c1104767fabe","verbose_name":null},"datasourceWarning":false,"expressionType":"SIMPLE","hasCustomLabel":true,"label":"Toplam Satış","optionName":"metric_50b6zsc76my_rli69u1cs9","sqlExpression":null}
          ],
          "orderby": [[{"aggregate":"SUM","column":{"advanced_data_type":null,"changed_on":"2024-12-05T06:47:07.971649","column_name":"Total_Sales","created_on":"2024-12-05T06:47:01.671351","description":null,"expression":"","extra":null,"filterable":true,"groupby":true,"id":4809,"is_active":true,"is_dttm":false,"python_date_format":null,"type":"Nullable(Decimal(38, 14))","type_generic":0,"uuid":"304bef9b-93a2-4640-89a4-c1104767fabe","verbose_name":null},"datasourceWarning":false,"expressionType":"SIMPLE","hasCustomLabel":true,"label":"Toplam Satış","optionName":"metric_50b6zsc76my_rli69u1cs9","sqlExpression":null},false]],
          "annotation_layers": [],
          "row_limit": 50000,
          "series_columns": [{"expressionType":"SQL","label":"Kalite Segment","sqlExpression":"CASE WHEN ProductQuality IN ('Super Premium','Premium Plus','Ultra Premium','Premium') THEN 'Prem' ELSE 'Diğer' END"}],
          "series_limit": 0,
          "order_desc": true,
          "url_params": {"dashboard_page_id":"Yn9pIQiFlhfBwFAQkmiHs","slice_id":"621"},
          "custom_params": {},
          "custom_form_data": {},
          "time_offsets": [],
          "post_processing": [
            {"operation":"pivot","options":{"index":["TRHISLEMTARIHI"],"columns":["Kalite Segment"],"aggregates":{"Toplam Satış":{"operator":"mean"}},"drop_missing_columns":false}},
            {"operation":"rename","options":{"columns":{"Toplam Satış":null},"level":0,"inplace":true}},
            {"operation":"flatten"}
          ]
        }
      ],
      "form_data": {
        "datasource":"318__table",
        "viz_type":"echarts_area",
        "slice_id":621,
        "url_params":{"dashboard_page_id":"Yn9pIQiFlhfBwFAQkmiHs","slice_id":"621"},
        "x_axis":"TRHISLEMTARIHI",
        "time_grain_sqla":"P1W",
        "metrics":[{"aggregate":"SUM","column":{"advanced_data_type":null,"changed_on":"2024-12-05T06:47:07.971649","column_name":"Total_Sales","created_on":"2024-12-05T06:47:01.671351","description":null,"expression":"","extra":null,"filterable":true,"groupby":true,"id":4809,"is_active":true,"is_dttm":false,"python_date_format":null,"type":"Nullable(Decimal(38, 14))","type_generic":0,"uuid":"304bef9b-93a2-4640-89a4-c1104767fabe","verbose_name":null},"datasourceWarning":false,"expressionType":"SIMPLE","hasCustomLabel":true,"label":"Toplam Satış","optionName":"metric_50b6zsc76my_rli69u1cs9","sqlExpression":null}],
        "groupby":[{"expressionType":"SQL","label":"Kalite Segment","sqlExpression":"CASE WHEN ProductQuality IN ('Super Premium','Premium Plus','Ultra Premium','Premium') THEN 'Prem' ELSE 'Diğer' END"}],
        "adhoc_filters":[
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_mjbqmduoopq_lnjm9tpfin","isExtra":false,"isNew":false,"operator":"TEMPORAL_RANGE","sqlExpression":"TRHISLEMTARIHI >= toStartOfWeek(today()) - INTERVAL 3 MONTHS","subject":"TRHISLEMTARIHI"},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_qk5kdo6sdls_8b92j0p11c","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n {% if url_param('dashboard_filter_bm') %}\\r\\n '{{ url_param('dashboard_filter_bm') }}'\\r\\n {% else %}\\r\\n 'Marmara'\\r\\n {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","subject":null},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_f4poqtvvvxv_wudassgbbrd","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ProductQuality IN ('Super Premium','Premium Plus','Ultra Premium','Premium')","subject":null},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_e92f5by3loa_5nae7pfm5l3","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ProductCat NOT IN ('Şarap')","subject":null},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_yci0670np0m_zijwblnooc9","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ROTA NOT ILIKE '%LZM%'","subject":null},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_tee48vuq9yj_z20quue53zs","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"TIP NOT IN (4) OR TIP IS NULL","subject":null}
        ],
        "row_limit":50000,
        "truncate_metric":true,
        "show_empty_columns":true,
        "comparison_type":"values",
        "annotation_layers":[],
        "forecastPeriods":10,
        "forecastInterval":0.8,
        "x_axis_title_margin":15,
        "y_axis_title_margin":15,
        "y_axis_title_position":"Left",
        "sort_series_type":"sum",
        "color_scheme":"supersetColors",
        "time_shift_color":true,
        "seriesType":"smooth",
        "opacity":0.2,
        "only_total":true,
        "markerEnabled":true,
        "markerSize":6,
        "show_legend":true,
        "legendType":"scroll",
        "legendOrientation":"bottom",
        "x_axis_time_format":"smart_date",
        "rich_tooltip":true,
        "showTooltipTotal":true,
        "tooltipTimeFormat":"smart_date",
        "y_axis_format":"SMART_NUMBER",
        "logAxis":false,
        "truncateXAxis":true,
        "y_axis_bounds":[null,null],
        "extra_form_data":{},
        "force":true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=i7TonBnsKNg&dashboard_page_id=Yn9pIQiFlhfBwFAQkmiHs&slice_id=621")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget3AggreationS10Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A331%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        // Dinamik yıl ve ay
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        String body = """
{
  "datasource":{"id":235,"type":"table"},
  "force":true,
  "queries":[
    {
      "filters":[],
      "extras":{"having":"","where":""},
      "applied_time_extras":{},
      "columns":[
        {
          "expressionType":"SQL",
          "label":"My column",
          "sqlExpression":"GMY_LGD"
        }
      ],
      "metrics":[
        {
          "aggregate":"COUNT_DISTINCT",
          "column":{
            "advanced_data_type":null,
            "changed_on":"2025-04-23T15:36:55.513172",
            "column_name":"SM",
            "created_on":"2025-04-23T15:36:55.513169",
            "description":null,
            "expression":null,
            "extra":null,
            "filterable":true,
            "groupby":true,
            "id":7071,
            "is_active":true,
            "is_dttm":false,
            "python_date_format":null,
            "type":"String",
            "type_generic":1,
            "uuid":"b495ab96-2a2f-4555-a6df-f113fb6008cf",
            "verbose_name":null
          },
          "datasourceWarning":false,
          "expressionType":"SIMPLE",
          "hasCustomLabel":false,
          "label":"COUNT_DISTINCT(SM)",
          "optionName":"metric_5cpklbu3pya_7n7pnkm5cm7",
          "sqlExpression":null
        }
      ],
      "annotation_layers":[],
      "row_limit":100,
      "series_limit":0,
      "order_desc":true,
      "url_params":{
        "dashboard_page_id":"l1TKaHEB81w9YjrOrNac3",
        "slice_id":"331"
      },
      "custom_params":{},
      "custom_form_data":{}
    }
  ],
  "form_data":{
    "datasource":"235__table",
    "viz_type":"word_cloud",
    "slice_id":331,
    "url_params":{
      "dashboard_page_id":"l1TKaHEB81w9YjrOrNac3",
      "slice_id":"331"
    },
    "series":{
      "expressionType":"SQL",
      "label":"My column",
      "sqlExpression":"GMY_LGD"
    },
    "metric":{
      "aggregate":"COUNT_DISTINCT",
      "column":{
        "advanced_data_type":null,
        "changed_on":"2025-04-23T15:36:55.513172",
        "column_name":"SM",
        "created_on":"2025-04-23T15:36:55.513169",
        "description":null,
        "expression":null,
        "extra":null,
        "filterable":true,
        "groupby":true,
        "id":7071,
        "is_active":true,
        "is_dttm":false,
        "python_date_format":null,
        "type":"String",
        "type_generic":1,
        "uuid":"b495ab96-2a2f-4555-a6df-f113fb6008cf",
        "verbose_name":null
      },
      "datasourceWarning":false,
      "expressionType":"SIMPLE",
      "hasCustomLabel":false,
      "label":"COUNT_DISTINCT(SM)",
      "optionName":"metric_5cpklbu3pya_7n7pnkm5cm7",
      "sqlExpression":null
    },
    "adhoc_filters":[
      
    ],
    "row_limit":100,
    "size_from":10,
    "size_to":70,
    "rotation":"flat",
    "color_scheme":"supersetColors",
    "extra_form_data":{},
    "force":true,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""".formatted(currentYear, currentMonth);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=oAnrGYjbPcw&dashboard_page_id=l1TKaHEB81w9YjrOrNac3&slice_id=331")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    // W22 (slice_id=1990) – Son 12 ay (ay başlangıçtan 11 ay geriye), BM=Marmara, LZM hariç, Rakı kategorisi, marka kırılımında Satış (L)
    public static JSONObject sendWidget22Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1990%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource":{"id":289,"type":"table"},
      "force":false,
      "queries":[
        {
          "filters":[
            {"col":"sf.BYTDURUM","op":"IN","val":[0]},
            {"col":"MUSTERI_KOD","op":"NOT IN","val":["Mey İçki"]}
          ],
          "extras":{
            "time_grain_sqla":"P1M",
            "having":"",
            "where":"(TRHISLEMTARIHI >= toStartOfMonth(today()) - INTERVAL 11 MONTHS) AND (`sf.LNGDISTKOD` NOT IN (1,2,384,999)) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(EK_BOLGEMUDURLUGU, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (EK_ROUTE_NAME NOT ILIKE '%LZM%') AND (URUN_KATEGORU_ACIKLAMA IN ('Rakı'))"
          },
          "applied_time_extras":{},
          "columns":[
            {"timeGrain":"P1M","columnType":"BASE_AXIS","sqlExpression":"TRHISLEMTARIHI","label":"TRHISLEMTARIHI","expressionType":"SQL"},
            {"expressionType":"SQL","label":"Marka","sqlExpression":"URUN_TIP_ACIKLAMA"}
          ],
          "metrics":[
            {
              "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
              "label":"Satış (L)","optionName":"metric_0zybd90b3hm_jhh4rzexh9e",
              "sqlExpression":"toFloat64(SUM(IF(BYTTUR IN (0,3),  URUN_AMBALAJ_LITRE * INVOICE_QUANTITY,\\r\\n                         IF(BYTTUR IN (2,4), -URUN_AMBALAJ_LITRE * INVOICE_QUANTITY, 0))))"
            }
          ],
          "orderby":[[
            {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
             "label":"Satış (L)","optionName":"metric_0zybd90b3hm_jhh4rzexh9e",
             "sqlExpression":"toFloat64(SUM(IF(BYTTUR IN (0,3),  URUN_AMBALAJ_LITRE * INVOICE_QUANTITY,\\r\\n                         IF(BYTTUR IN (2,4), -URUN_AMBALAJ_LITRE * INVOICE_QUANTITY, 0))))"
            },false
          ]],
          "annotation_layers":[],
          "row_limit":100000,
          "series_columns":[{"expressionType":"SQL","label":"Marka","sqlExpression":"URUN_TIP_ACIKLAMA"}],
          "series_limit":0,
          "order_desc":true,
          "url_params":{"dashboard_page_id":"0EcUSL_uCmuyNeM-zFeVO","form_data_key":"qatdhWS214c","save_action":"overwrite","slice_id":"1990"},
          "custom_params":{},
          "custom_form_data":{},
          "time_offsets":[],
          "post_processing":[
            {"operation":"pivot","options":{"index":["TRHISLEMTARIHI"],"columns":["Marka"],"aggregates":{"Satış (L)":{"operator":"mean"}},"drop_missing_columns":false}},
            {"operation":"rename","options":{"columns":{"Satış (L)":null},"level":0,"inplace":true}},
            {"operation":"flatten"}
          ]
        }
      ],
      "form_data":{
        "datasource":"289__table",
        "viz_type":"echarts_timeseries_smooth",
        "slice_id":1990,
        "url_params":{"dashboard_page_id":"0EcUSL_uCmuyNeM-zFeVO","form_data_key":"qatdhWS214c","save_action":"overwrite","slice_id":"1990"},
        "x_axis":"TRHISLEMTARIHI",
        "time_grain_sqla":"P1M",
        "x_axis_sort_asc":true,
        "x_axis_sort_series":"name",
        "x_axis_sort_series_ascending":true,
        "metrics":[
          {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_0zybd90b3hm_jhh4rzexh9e","sqlExpression":"toFloat64(SUM(IF(BYTTUR IN (0,3),  URUN_AMBALAJ_LITRE * INVOICE_QUANTITY,\\r\\n                         IF(BYTTUR IN (2,4), -URUN_AMBALAJ_LITRE * INVOICE_QUANTITY, 0))))"}
        ],
        "groupby":[{"expressionType":"SQL","label":"Marka","sqlExpression":"URUN_TIP_ACIKLAMA"}],
        "adhoc_filters":[
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_sioznfc8ip_eogd06wsk1g","isExtra":false,"isNew":false,"operator":"TEMPORAL_RANGE","sqlExpression":"TRHISLEMTARIHI >= toStartOfMonth(today()) - INTERVAL 11 MONTHS","subject":"TRHISLEMTARIHI"},
          {"clause":"WHERE","comparator":[0],"datasourceWarning":false,"expressionType":"SIMPLE","filterOptionName":"filter_zbeyltxbndf_w90xva1pn5","isExtra":false,"isNew":false,"operator":"IN","operatorId":"IN","sqlExpression":null,"subject":"sf.BYTDURUM"},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_55xw80ooinb_87649rafmra","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"`sf.LNGDISTKOD` NOT IN (1,2,384,999)","subject":null},
          {"clause":"WHERE","comparator":["Mey İçki"],"datasourceWarning":false,"expressionType":"SIMPLE","filterOptionName":"filter_jqzh58yin3m_gmqri43v5rk","isExtra":false,"isNew":false,"operator":"NOT IN","operatorId":"NOT_IN","sqlExpression":null,"subject":"MUSTERI_KOD"},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_rjwqksoykrg_3hkqctpuxvy","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(EK_BOLGEMUDURLUGU, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","subject":null},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_0cqj4zh42zz7_sgl3s0vh9","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"EK_ROUTE_NAME NOT ILIKE '%LZM%'","subject":null},
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_zeaowzejteh_xgi7fk8lnbb","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"URUN_KATEGORU_ACIKLAMA IN ('Rakı')","subject":null}
        ],
        "order_desc":true,
        "row_limit":"100000",
        "truncate_metric":true,
        "show_empty_columns":true,
        "comparison_type":"values",
        "annotation_layers":[],
        "forecastPeriods":10,
        "forecastInterval":0.8,
        "x_axis_title_margin":15,
        "y_axis_title_margin":30,
        "y_axis_title_position":"Left",
        "sort_series_type":"sum",
        "color_scheme":"supersetColors",
        "time_shift_color":true,
        "only_total":true,
        "markerEnabled":true,
        "markerSize":6,
        "show_legend":true,
        "legendType":"scroll",
        "legendOrientation":"left",
        "x_axis_time_format":"%B",
        "xAxisLabelRotation":45,
        "rich_tooltip":true,
        "showTooltipTotal":true,
        "tooltipTimeFormat":"smart_date",
        "y_axis_format":"SMART_NUMBER",
        "truncateXAxis":true,
        "y_axis_bounds":[null,null],
        "extra_form_data":{},
        "force":false,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=qatdhWS214c&dashboard_page_id=0EcUSL_uCmuyNeM-zFeVO&slice_id=1990&save_action=overwrite")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget15AggregationRequest() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A522%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
            {"datasource":{"id":330,"type":"table"},"force":false,"queries":[{"filters":[{"col":"BM","op":"IN","val":["Marmara Bölge"]}],"extras":{"having":"","where":"(makeDate(FISCALYEAR,FISCALMONTH,1) >= \\r\\n    toStartOfMonth(today())\\r\\n) AND (splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      ifNull(BM, '')\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (ROTA NOT ILIKE '%LZM%')"},"applied_time_extras":{},"columns":[{"columnType":"BASE_AXIS","datasourceWarning":false,"expressionType":"SQL","label":"Ürün Kategori","sqlExpression":"'Ürün Kategori'"},"ProductCat"],"metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_ectan2zdvy8_i6hm2sfdptq","sqlExpression":"SUM(Total_Sales)"}],"orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":false,"label":"SUM(Total_Sales)","optionName":"metric_en6j3hwfkxk_g9klffngsog","sqlExpression":"SUM(Total_Sales)"},false]],"annotation_layers":[],"row_limit":10000,"series_columns":["ProductCat"],"series_limit":0,"series_limit_metric":{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":false,"label":"SUM(Total_Sales)","optionName":"metric_en6j3hwfkxk_g9klffngsog","sqlExpression":"SUM(Total_Sales)"},"order_desc":true,"url_params":{"dashboard_page_id":"1J1r34hZDTtlUqPdX77xx","form_data_key":"XMGz08xYW1g","slice_id":"522"},"custom_params":{},"custom_form_data":{},"time_offsets":[],"post_processing":[{"operation":"pivot","options":{"index":["Ürün Kategori"],"columns":["ProductCat"],"aggregates":{"Satış (L)":{"operator":"mean"}},"drop_missing_columns":false}},{"operation":"rename","options":{"columns":{"Satış (L)":null},"level":0,"inplace":true}},{"operation":"flatten"}]}],"form_data":{"datasource":"330__table","viz_type":"echarts_timeseries_bar","slice_id":522,"url_params":{"dashboard_page_id":"1J1r34hZDTtlUqPdX77xx","form_data_key":"XMGz08xYW1g","slice_id":"522"},"x_axis":{"datasourceWarning":false,"expressionType":"SQL","label":"Ürün Kategori","sqlExpression":"'Ürün Kategori'"},"x_axis_sort_asc":true,"x_axis_sort_series":"name","x_axis_sort_series_ascending":true,"metrics":[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Satış (L)","optionName":"metric_ectan2zdvy8_i6hm2sfdptq","sqlExpression":"SUM(Total_Sales)"}],"groupby":["ProductCat"],"adhoc_filters":[{"expressionType":"SQL","sqlExpression":"makeDate(FISCALYEAR,FISCALMONTH,1) >= \\r\\n    toStartOfMonth(today())\\r\\n","clause":"WHERE","subject":"TRHISLEMTARIHI","operator":"TEMPORAL_RANGE","comparator":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_ixgownj8ei_hecuhtjs62"},{"expressionType":"SQL","sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      ifNull(BM, '')\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","clause":"WHERE","subject":null,"operator":null,"comparator":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_f4bm6lm48t9_x4e3xz5c7zg"},{"expressionType":"SQL","sqlExpression":"ROTA NOT ILIKE '%LZM%'","clause":"WHERE","subject":null,"operator":null,"comparator":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_mb6vdltun4g_ym4wxjhngc"},{"expressionType":"SIMPLE","subject":"BM","operator":"IN","operatorId":"IN","comparator":["Marmara Bölge"],"clause":"WHERE","sqlExpression":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_f8fmn7by3hw_2wzaf2p034u"}],"timeseries_limit_metric":{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":false,"label":"SUM(Total_Sales)","optionName":"metric_en6j3hwfkxk_g9klffngsog","sqlExpression":"SUM(Total_Sales)"},"order_desc":true,"row_limit":10000,"truncate_metric":true,"show_empty_columns":true,"comparison_type":"values","annotation_layers":[],"forecastPeriods":10,"forecastInterval":0.8,"orientation":"vertical","x_axis_title_margin":15,"y_axis_title":"","y_axis_title_margin":"45","y_axis_title_position":"Left","sort_series_type":"sum","color_scheme":"bnbColors","time_shift_color":true,"show_value":true,"only_total":true,"show_legend":true,"legendType":"scroll","legendOrientation":"bottom","legendMargin":40,"x_axis_time_format":"smart_date","xAxisLabelRotation":0,"y_axis_format":"SMART_NUMBER","logAxis":false,"y_axis_bounds":[null,null],"truncateXAxis":true,"rich_tooltip":true,"showTooltipTotal":true,"tooltipSortByMetric":false,"tooltipTimeFormat":"%d/%m/%Y","extra_form_data":{},"force":false,"result_format":"json","result_type":"full"},"result_format":"json","result_type":"full"}
            """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=XMGz08xYW1g&dashboard_page_id=1J1r34hZDTtlUqPdX77xx&slice_id=522")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                String err = resp.body() != null ? resp.body().string() : "";
                throw new IOException("Unexpected code " + resp.code() + " - " + err);
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget33Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A276%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(4, TimeUnit.SECONDS)
                .writeTimeout(4, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource":{"id":216,"type":"table"},
  "force":true,
  "queries":[
    {
      "filters":[{"col":"SM","op":"IS NOT NULL"}],
      "extras":{"having":"","where":"(toDate(TARIH) = today())"},
      "applied_time_extras":{},
      "columns":[
        {"columnType":"BASE_AXIS","expressionType":"SQL","label":"SM","sqlExpression":"replaceAll(replaceAll(replaceAll(upperUTF8(SM), 'İ', 'I'),'Ç','C'),'Ğ','G')"}
      ],
      "metrics":[
        {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
         "label":"Ziyaret İç Hedef","optionName":"metric_ab6mdk3a4ng_dgsu9m79ruf","sqlExpression":"SUM(PLANANAN_MUSTERI_SAYISI)"}
      ],
      "annotation_layers":[],
      "row_limit":1000,
      "series_columns":[],
      "series_limit":0,
      "url_params":{"dashboard_page_id":"5NCptbSXXst-z0R9ZBf4L","slice_id":"276"},
      "custom_params":{},
      "custom_form_data":{},
      "time_offsets":[],
      "post_processing":[
        {"operation":"pivot","options":{"index":["SM"],"columns":[],"aggregates":{"Ziyaret İç Hedef":{"operator":"mean"}},"drop_missing_columns":true}},
        {"operation":"flatten"}
      ],
      "orderby":[[
        {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
         "label":"Ziyaret İç Hedef","optionName":"metric_ab6mdk3a4ng_dgsu9m79ruf","sqlExpression":"SUM(PLANANAN_MUSTERI_SAYISI)"},
        false
      ]]
    },
    {
      "filters":[{"col":"SM","op":"IS NOT NULL"}],
      "extras":{"having":"","where":"(toDate(TARIH) = today())"},
      "applied_time_extras":{},
      "columns":[
        {"columnType":"BASE_AXIS","expressionType":"SQL","label":"SM","sqlExpression":"replaceAll(replaceAll(replaceAll(upperUTF8(SM), 'İ', 'I'),'Ç','C'),'Ğ','G')"}
      ],
      "metrics":[
        {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
         "label":"Gerçekleşen Ziyaret","optionName":"metric_onux0gmtb3_kc4x9jhsa5a","sqlExpression":"SUM(YAPILAN_ZIYARET_MUSTERI_SAYISI)"}
      ],
      "annotation_layers":[],
      "row_limit":10000,
      "series_columns":[],
      "series_limit":0,
      "url_params":{"dashboard_page_id":"5NCptbSXXst-z0R9ZBf4L","slice_id":"276"},
      "custom_params":{},
      "custom_form_data":{},
      "time_offsets":[],
      "post_processing":[
        {"operation":"pivot","options":{"index":["SM"],"columns":[],"aggregates":{"Gerçekleşen Ziyaret":{"operator":"mean"}},"drop_missing_columns":true}},
        {"operation":"flatten"}
      ],
      "orderby":[[
        {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
         "label":"Gerçekleşen Ziyaret","optionName":"metric_onux0gmtb3_kc4x9jhsa5a","sqlExpression":"SUM(YAPILAN_ZIYARET_MUSTERI_SAYISI)"},
        false
      ]]
    }
  ],
  "form_data":{
    "datasource":"216__table",
    "viz_type":"mixed_timeseries",
    "slice_id":276,
    "url_params":{"dashboard_page_id":"5NCptbSXXst-z0R9ZBf4L","slice_id":"276"},
    "x_axis":{"expressionType":"SQL","label":"SM","sqlExpression":"replaceAll(replaceAll(replaceAll(upperUTF8(SM), 'İ', 'I'),'Ç','C'),'Ğ','G')"},
    "metrics":[
      {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
       "label":"Ziyaret İç Hedef","optionName":"metric_ab6mdk3a4ng_dgsu9m79ruf","sqlExpression":"SUM(PLANANAN_MUSTERI_SAYISI)"}
    ],
    "groupby":[],
    "adhoc_filters":[
      {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_1y8xc57gmqh_6eik70zf0gw","isExtra":false,"isNew":false,"operator":"TEMPORAL_RANGE","sqlExpression":"toDate(TARIH) = today()","subject":"TARIH"},
      {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SIMPLE","filterOptionName":"filter_j4ffkiu9ndo_cj270cl7d6q","isExtra":false,"isNew":false,"operator":"IS NOT NULL","operatorId":"IS_NOT_NULL","sqlExpression":null,"subject":"SM"}
    ],
    "order_desc":true,
    "row_limit":1000,
    "truncate_metric":true,
    "comparison_type":"values",
    "annotation_layers":[],
    "x_axis_title_margin":30,
    "y_axis_title_margin":30,
    "y_axis_title_position":"Left",
    "color_scheme":"presetColors",
    "time_shift_color":true,
    "seriesType":"middle",
    "show_value":true,
    "opacity":0.6,
    "markerEnabled":true,
    "markerSize":6,
    "seriesTypeB":"bar",
    "stackB":false,
    "opacityB":0.2,
    "markerEnabledB":false,
    "markerSizeB":6,
    "show_legend":true,
    "legendType":"scroll",
    "legendOrientation":"bottom",
    "legendMargin":90,
    "x_axis_time_format":"smart_date",
    "xAxisLabelRotation":90,
    "rich_tooltip":true,
    "showTooltipTotal":true,
    "tooltipTimeFormat":"smart_date",
    "truncateXAxis":true,
    "y_axis_bounds":[null,null],
    "y_axis_format":"SMART_NUMBER",
    "y_axis_bounds_secondary":[null,null],
    "y_axis_format_secondary":"SMART_NUMBER",
    "extra_form_data":{},
    "force":true,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=Rf7yqr3imMQ&dashboard_page_id=5NCptbSXXst-z0R9ZBf4L&slice_id=276")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget34Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A270%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(4, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource":{"id":216,"type":"table"},
      "force":true,
      "queries":[
        {
          "filters":[{"col":"SM","op":"IS NOT NULL"}],
          "extras":{
            "time_grain_sqla":"P1D",
            "having":"",
            "where":"(TARIH >= DATE_TRUNC('Month', NOW()) AND TARIH <= today())"
          },
          "applied_time_extras":{},
          "columns":[
            {"timeGrain":"P1D","columnType":"BASE_AXIS","sqlExpression":"TARIH","label":"TARIH","expressionType":"SQL"}
          ],
          "metrics":[
            {
              "aggregate":"SUM",
              "column":{
                "advanced_data_type":null,"changed_on":"2025-07-02T09:16:59.390062","column_name":"PLANANAN_MUSTERI_SAYISI",
                "created_on":"2025-07-02T09:16:59.390057","description":null,"expression":null,"extra":"{\\"warning_markdown\\": null}",
                "filterable":true,"groupby":true,"id":4951,"is_active":true,"is_dttm":false,"python_date_format":null,"type":"UInt64",
                "type_generic":0,"uuid":"62e6cbda-e864-4efd-a503-d45c52cbd0ba","verbose_name":null
              },
              "datasourceWarning":false,"expressionType":"SIMPLE","hasCustomLabel":false,
              "label":"SUM(PLANANAN_MUSTERI_SAYISI)","optionName":"metric_fax5mlkhzrc_1igqvoqb2s","sqlExpression":null
            }
          ],
          "annotation_layers":[],
          "series_limit":0,
          "order_desc":true,
          "url_params":{"dashboard_page_id":"5NCptbSXXst-z0R9ZBf4L","slice_id":"270"},
          "custom_params":{},
          "custom_form_data":{},
          "post_processing":[
            {"operation":"pivot","options":{"index":["TARIH"],"columns":[],"aggregates":{"SUM(PLANANAN_MUSTERI_SAYISI)":{"operator":"mean"}},"drop_missing_columns":true}},
            {"operation":"flatten"}
          ]
        }
      ],
      "form_data":{
        "datasource":"216__table",
        "viz_type":"big_number",
        "slice_id":270,
        "url_params":{"dashboard_page_id":"5NCptbSXXst-z0R9ZBf4L","slice_id":"270"},
        "x_axis":"TARIH",
        "time_grain_sqla":"P1D",
        "metric":{
          "aggregate":"SUM",
          "column":{
            "advanced_data_type":null,"changed_on":"2025-07-02T09:16:59.390062","column_name":"PLANANAN_MUSTERI_SAYISI",
            "created_on":"2025-07-02T09:16:59.390057","description":null,"expression":null,"extra":"{\\"warning_markdown\\": null}",
            "filterable":true,"groupby":true,"id":4951,"is_active":true,"is_dttm":false,"python_date_format":null,"type":"UInt64",
            "type_generic":0,"uuid":"62e6cbda-e864-4efd-a503-d45c52cbd0ba","verbose_name":null
          },
          "datasourceWarning":false,"expressionType":"SIMPLE","hasCustomLabel":false,
          "label":"SUM(PLANANAN_MUSTERI_SAYISI)","optionName":"metric_fax5mlkhzrc_1igqvoqb2s","sqlExpression":null
        },
        "adhoc_filters":[
          {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_l6pj38q1gi_84i05zipt9g","isExtra":false,"isNew":false,"operator":"TEMPORAL_RANGE","sqlExpression":"TARIH >= DATE_TRUNC('Month', NOW()) AND TARIH <= today()","subject":"TARIH"},
          {"clause":"WHERE","datasourceWarning":false,"expressionType":"SIMPLE","filterOptionName":"filter_bily9e01tmk_ijghn0nsoch","isExtra":false,"isNew":false,"operator":"IS NOT NULL","operatorId":"IS_NOT_NULL","sqlExpression":null,"subject":"SM"}
        ],
        "compare_lag":1,
        "compare_suffix":"DoD",
        "show_timestamp":true,
        "show_trend_line":true,
        "start_y_axis_at_zero":true,
        "color_picker":{"a":1,"b":135,"g":122,"r":0},
        "header_font_size":0.4,
        "subheader_font_size":0.15,
        "y_axis_format":",d",
        "time_format":"%d/%m/%Y",
        "rolling_type":"None",
        "extra_form_data":{},
        "force":true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=xMZQf1nA2j0&dashboard_page_id=5NCptbSXXst-z0R9ZBf4L&slice_id=270")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget35Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A262%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource":{"id":279,"type":"table"},
  "force":true,
  "queries":[
    {
      "filters":[{"col":"TARIH","op":"TEMPORAL_RANGE","val":"No filter"}],
      "extras":{
        "time_grain_sqla":"P1D",
        "having":"",
        "where":"(TARIH >= DATE_TRUNC('Month', NOW()) AND TARIH <= today())"
      },
      "applied_time_extras":{},
      "columns":[
        {"timeGrain":"P1D","columnType":"BASE_AXIS","sqlExpression":"TARIH","label":"TARIH","expressionType":"SQL"}
      ],
      "metrics":[
        {
          "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
          "label":"Müşteri Sayısı","optionName":"metric_6ni0regqze2_4jwm5v17rwc",
          "sqlExpression":"SUM(YAPILAN_ZIYARET_MUSTERI_SAYISI)"
        }
      ],
      "annotation_layers":[],
      "series_limit":0,
      "order_desc":true,
      "url_params":{"dashboard_page_id":"5NCptbSXXst-z0R9ZBf4L","slice_id":"262"},
      "custom_params":{},
      "custom_form_data":{},
      "post_processing":[
        {"operation":"pivot","options":{"index":["TARIH"],"columns":[],"aggregates":{"Müşteri Sayısı":{"operator":"mean"}},"drop_missing_columns":true}},
        {"operation":"flatten"}
      ]
    }
  ],
  "form_data":{
    "datasource":"279__table",
    "viz_type":"big_number",
    "slice_id":262,
    "url_params":{"dashboard_page_id":"5NCptbSXXst-z0R9ZBf4L","slice_id":"262"},
    "x_axis":"TARIH",
    "time_grain_sqla":"P1D",
    "metric":{
      "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
      "label":"Müşteri Sayısı","optionName":"metric_6ni0regqze2_4jwm5v17rwc",
      "sqlExpression":"SUM(YAPILAN_ZIYARET_MUSTERI_SAYISI)"
    },
    "adhoc_filters":[
      {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_l6pj38q1gi_84i05zipt9g","isExtra":false,"isNew":false,"operator":"TEMPORAL_RANGE","sqlExpression":"TARIH >= DATE_TRUNC('Month', NOW()) AND TARIH <= today()","subject":"TARIH"},
      {"clause":"WHERE","comparator":"No filter","datasourceWarning":false,"expressionType":"SIMPLE","filterOptionName":"filter_3ml0h6f0vo9_a8c4j3aath","isExtra":false,"isNew":false,"operator":"TEMPORAL_RANGE","sqlExpression":null,"subject":"TARIH"}
    ],
    "compare_lag":1,
    "compare_suffix":"DoD",
    "show_timestamp":true,
    "show_trend_line":true,
    "start_y_axis_at_zero":true,
    "color_picker":{"a":1,"b":135,"g":122,"r":0},
    "header_font_size":0.4,
    "subheader_font_size":0.15,
    "y_axis_format":",d",
    "time_format":"%d/%m/%Y",
    "rolling_type":"None",
    "extra_form_data":{},
    "force":true,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=IvDn9q5w6z0&dashboard_page_id=5NCptbSXXst-z0R9ZBf4L&slice_id=262")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget49Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1823%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(4, TimeUnit.SECONDS)
                .readTimeout(4, TimeUnit.SECONDS)
                .writeTimeout(4, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        // İçinde bulunduğumuz yıl/ay
        int currentYear  = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        String template = """
{
  "datasource":{"id":372,"type":"table"},
  "force":true,
  "queries":[
    {
      "filters":[
        {"col":"FISCALYEAR","op":"IN","val":[${YEAR}]},
        {"col":"FISCALMONTH","op":"IN","val":[${MONTH}]}
      ],
      "extras":{
        "having":"",
        "where":"(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (ROTA NOT ILIKE '%LZM%')"
      },
      "applied_time_extras":{},
      "columns":[],
      "metrics":[
        {
          "aggregate":null,
          "column":null,
          "datasourceWarning":false,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Ort. Günlük Gerçekleşme",
          "optionName":"metric_lko7lwgwphj_o76jqjipan",
          "sqlExpression":"divide(\\r\\n    divide(\\r\\n        SUM(Total_Sales),\\r\\n        nullIf(SUM(TotalTarget), 0)\\r\\n    )\\r\\n    *\\r\\n    length(\\r\\n        arrayFilter(\\r\\n            x -> dayOfWeek(\\r\\n                     addDays(\\r\\n                         toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                         x\\r\\n                     )\\r\\n                 ) BETWEEN 1 AND 5,\\r\\n            range(\\r\\n                toUInt64(\\r\\n                    dateDiff(\\r\\n                        'day',\\r\\n                        toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                        addMonths(toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')), 1)\\r\\n                    )\\r\\n                )\\r\\n            )\\r\\n        )\\r\\n    ),\\r\\n    nullIf(\\r\\n        if(\\r\\n            addMonths(\\r\\n                toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                1\\r\\n            ) <= today(),\\r\\n            length(\\r\\n                arrayFilter(\\r\\n                    x -> dayOfWeek(\\r\\n                             addDays(\\r\\n                                 toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                                 x\\r\\n                             )\\r\\n                         ) BETWEEN 1 AND 5,\\r\\n                    range(\\r\\n                        toUInt64(\\r\\n                            dateDiff(\\r\\n                                'day',\\r\\n                                toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                                addMonths(toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')), 1)\\r\\n                            )\\r\\n                        )\\r\\n                    )\\r\\n                )\\r\\n            ),\\r\\n            length(\\r\\n                arrayFilter(\\r\\n                    x -> dayOfWeek(\\r\\n                             addDays(\\r\\n                                 toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                                 x\\r\\n                             )\\r\\n                         ) BETWEEN 1 AND 5,\\r\\n                    range(\\r\\n                        toUInt64(\\r\\n                            greatest(\\r\\n                                0,\\r\\n                                dateDiff(\\r\\n                                    'day',\\r\\n                                    toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                                    addDays(today(), 0)\\r\\n                                )\\r\\n                            )\\r\\n                        )\\r\\n                    )\\r\\n                )\\r\\n            )\\r\\n        ),\\r\\n        0\\r\\n    )\\r\\n)\\r\\n"
        }
      ],
      "annotation_layers":[],
      "series_limit":0,
      "order_desc":true,
      "url_params":{"dashboard_page_id":"x1QcZob6QxFeXRWPS8a49","form_data_key":"EXfMMvzZzGU","save_action":"overwrite","slice_id":"1823"},
      "custom_params":{},
      "custom_form_data":{}
    }
  ],
  "form_data":{
    "datasource":"372__table",
    "viz_type":"big_number_total",
    "slice_id":1823,
    "url_params":{"dashboard_page_id":"x1QcZob6QxFeXRWPS8a49","form_data_key":"EXfMMvzZzGU","save_action":"overwrite","slice_id":"1823"},
    "metric":{
      "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
      "label":"Ort. Günlük Gerçekleşme","optionName":"metric_lko7lwgwphj_o76jqjipan",
      "sqlExpression":"divide(\\r\\n    divide(\\r\\n        SUM(Total_Sales),\\r\\n        nullIf(SUM(TotalTarget), 0)\\r\\n    )\\r\\n    *\\r\\n    length(\\r\\n        arrayFilter(\\r\\n            x -> dayOfWeek(\\r\\n                     addDays(\\r\\n                         toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                         x\\r\\n                     )\\r\\n                 ) BETWEEN 1 AND 5,\\r\\n            range(\\r\\n                toUInt64(\\r\\n                    dateDiff(\\r\\n                        'day',\\r\\n                        toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                        addMonths(toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')), 1)\\r\\n                    )\\r\\n                )\\r\\n            )\\r\\n        )\\r\\n    ),\\r\\n    nullIf(\\r\\n        if(\\r\\n            addMonths(\\r\\n                toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                1\\r\\n            ) <= today(),\\r\\n            length(\\r\\n                arrayFilter(\\r\\n                    x -> dayOfWeek(\\r\\n                             addDays(\\r\\n                                 toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                                 x\\r\\n                             )\\r\\n                         ) BETWEEN 1 AND 5,\\r\\n                    range(\\r\\n                        toUInt64(\\r\\n                            dateDiff(\\r\\n                                'day',\\r\\n                                toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                                addMonths(toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')), 1)\\r\\n                            )\\r\\n                        )\\r\\n                    )\\r\\n                )\\r\\n            ),\\r\\n            length(\\r\\n                arrayFilter(\\r\\n                    x -> dayOfWeek(\\r\\n                             addDays(\\r\\n                                 toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                                 x\\r\\n                             )\\r\\n                         ) BETWEEN 1 AND 5,\\r\\n                    range(\\r\\n                        toUInt64(\\r\\n                            greatest(\\r\\n                                0,\\r\\n                                dateDiff(\\r\\n                                    'day',\\r\\n                                    toDate(concat(toString(toUInt32(any(FISCALYEAR))), '-', leftPad(toString(toUInt32(any(FISCALMONTH))), 2, '0'), '-01')),\\r\\n                                    addDays(today(), 0)\\r\\n                                )\\r\\n                            )\\r\\n                        )\\r\\n                    )\\r\\n                )\\r\\n            )\\r\\n        ),\\r\\n        0\\r\\n    )\\r\\n)"
    },
    "adhoc_filters":[
      {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_vo3qu7rmqoo_4xgkz73uqbd","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","subject":null},
      {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_0cgl2e6xragr_bsae4rotrtu","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ROTA NOT ILIKE '%LZM%'","subject":null},
      {"expressionType":"SIMPLE","clause":"WHERE","operator":"IN","operatorId":"IN","subject":"FISCALYEAR","comparator":[${YEAR}],"isExtra":true,"filterOptionName":"filter_85ng918c07q_ubddtddjlc"},
      {"expressionType":"SIMPLE","clause":"WHERE","operator":"IN","operatorId":"IN","subject":"FISCALMONTH","comparator":[${MONTH}],"isExtra":true,"filterOptionName":"filter_iz42v2faz6s_z4ajue2ymb"}
    ],
    "subheader":"Tahmini Kapanış %",
    "header_font_size":0.4,
    "subheader_font_size":0.15,
    "y_axis_format":".2%",
    "time_format":"smart_date",
    "conditional_formatting":[],
    "extra_form_data":{},
    "force":true,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        String body = template
                .replace("${YEAR}", String.valueOf(currentYear))
                .replace("${MONTH}", String.valueOf(currentMonth));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=EXfMMvzZzGU&dashboard_page_id=x1QcZob6QxFeXRWPS8a49&slice_id=1823&save_action=overwrite")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget44Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1822%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        // İçinde bulunduğumuz yıl/ay
        int currentYear  = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        String template = """
{
  "datasource":{"id":330,"type":"table"},
  "force":true,
  "queries":[
    {
      "filters":[
        {"col":"FISCALYEAR","op":"IN","val":[${YEAR}]},
        {"col":"FISCALMONTH","op":"IN","val":[${MONTH}]}
      ],
      "extras":{
        "having":"",
        "where":"(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (ROTA NOT ILIKE '%LZM%')"
      },
      "applied_time_extras":{},
      "columns":[],
      "metrics":[
        {
          "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
          "label":"İç Hedef","optionName":"metric_kx6tnysvcoq_22hv22puxqw","sqlExpression":"SUM(TotalTarget)"
        }
      ],
      "annotation_layers":[],
      "series_limit":0,
      "order_desc":true,
      "url_params":{"dashboard_page_id":"c9tZcOIS8TuouNnC1m4td","form_data_key":"8df9uuh1Ujs","slice_id":"1822"},
      "custom_params":{},
      "custom_form_data":{}
    }
  ],
  "form_data":{
    "datasource":"330__table",
    "viz_type":"big_number_total",
    "slice_id":1822,
    "url_params":{"dashboard_page_id":"c9tZcOIS8TuouNnC1m4td","form_data_key":"8df9uuh1Ujs","slice_id":"1822"},
    "metric":{
      "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
      "label":"İç Hedef","optionName":"metric_kx6tnysvcoq_22hv22puxqw","sqlExpression":"SUM(TotalTarget)"
    },
    "adhoc_filters":[
      {
        "clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_nd7s42ac6dm_w8u33x0qkgs","isExtra":false,"isNew":false,"operator":null,
        "sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]",
        "subject":null
      },
      {
        "clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_6xfr1cutlfa_yr8rx64wn87","isExtra":false,"isNew":false,"operator":null,
        "sqlExpression":"ROTA NOT ILIKE '%LZM%'", "subject":null
      },
      {"clause":"WHERE","comparator":[${YEAR}],"expressionType":"SIMPLE","filterOptionName":"filter_n15wuovdc7m_qz4t7puf2iq","isExtra":true,"operator":"IN","operatorId":"IN","subject":"FISCALYEAR"},
      {"clause":"WHERE","comparator":[${MONTH}],"expressionType":"SIMPLE","filterOptionName":"filter_slfwqdjira_kdkeur0zug","isExtra":true,"operator":"IN","operatorId":"IN","subject":"FISCALMONTH"}
    ],
    "subheader":"İç Hedef",
    "header_font_size":0.4,
    "subheader_font_size":0.15,
    "y_axis_format":"SMART_NUMBER",
    "time_format":"smart_date",
    "conditional_formatting":[],
    "extra_form_data":{},
    "force":true,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        String body = template
                .replace("${YEAR}", String.valueOf(currentYear))
                .replace("${MONTH}", String.valueOf(currentMonth));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=8df9uuh1Ujs&dashboard_page_id=c9tZcOIS8TuouNnC1m4td&slice_id=1822")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget87Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1959%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource":{"id":344,"type":"table"},
      "force":true,
      "queries":[
        {
          "filters":[],
          "extras":{"having":"","where":""},
          "applied_time_extras":{},
          "columns":[],
          "metrics":[
            {
              "aggregate":"SUM",
              "column":{
                "advanced_data_type":null,
                "changed_on":"2025-08-04T09:58:59.261495",
                "column_name":"Tutar",
                "created_on":"2025-08-04T09:58:59.261494",
                "description":null,
                "expression":null,
                "extra":null,
                "filterable":true,
                "groupby":true,
                "id":7400,
                "is_active":true,
                "is_dttm":false,
                "python_date_format":null,
                "type":"Decimal(38, 2)",
                "type_generic":0,
                "uuid":"8118fa35-fe65-4413-bdb7-fee783960461",
                "verbose_name":null
              },
              "datasourceWarning":false,
              "expressionType":"SIMPLE",
              "hasCustomLabel":true,
              "label":"Tutar",
              "optionName":"metric_r5fa7njezfn_2p7knd1q5cb",
              "sqlExpression":null
            }
          ],
          "annotation_layers":[],
          "series_limit":0,
          "order_desc":true,
          "url_params":{"dashboard_page_id":"GulxjbioGz-6_xA0Jptak","slice_id":"1959"},
          "custom_params":{},
          "custom_form_data":{}
        }
      ],
      "form_data":{
        "datasource":"344__table",
        "viz_type":"big_number_total",
        "slice_id":1959,
        "url_params":{"dashboard_page_id":"GulxjbioGz-6_xA0Jptak","slice_id":"1959"},
        "metric":{
          "aggregate":"SUM",
          "column":{
            "advanced_data_type":null,
            "changed_on":"2025-08-04T09:58:59.261495",
            "column_name":"Tutar",
            "created_on":"2025-08-04T09:58:59.261494",
            "description":null,
            "expression":null,
            "extra":null,
            "filterable":true,
            "groupby":true,
            "id":7400,
            "is_active":true,
            "is_dttm":false,
            "python_date_format":null,
            "type":"Decimal(38, 2)",
            "type_generic":0,
            "uuid":"8118fa35-fe65-4413-bdb7-fee783960461",
            "verbose_name":null
          },
          "datasourceWarning":false,
          "expressionType":"SIMPLE",
          "hasCustomLabel":true,
          "label":"Tutar",
          "optionName":"metric_r5fa7njezfn_2p7knd1q5cb",
          "sqlExpression":null
        },
        "adhoc_filters":[],
        "subheader":"Toplam Açık Bakiye",
        "header_font_size":0.4,
        "subheader_font_size":0.15,
        "y_axis_format":"SMART_NUMBER",
        "time_format":"smart_date",
        "extra_form_data":{},
        "force":true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=3II2Xn4gVY4&dashboard_page_id=GulxjbioGz-6_xA0Jptak&slice_id=1959")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget84Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1951%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource": {"id":347,"type":"table"},
      "force": true,
      "queries": [
        {
          "filters": [
            {"col":"VadeTarihi","op":"TEMPORAL_RANGE","val":"No filter"}
          ],
          "extras": {"time_grain_sqla":"P1D","having":"","where":""},
          "applied_time_extras": {},
          "columns": [
            {"datasourceWarning":false,"expressionType":"SQL","label":"Vade Tarihi","sqlExpression":"VadeTarihiKisa"},
            {"datasourceWarning":false,"expressionType":"SQL","label":"Kalan Gün","sqlExpression":"VadeKalanGun"},
            {"datasourceWarning":false,"expressionType":"SQL","label":"Durum","sqlExpression":"VadeDurumu"}
          ],
          "metrics": [
            {
              "aggregate":"SUM",
              "column":{"advanced_data_type":null,"column_name":"KalanTutar","description":null,"expression":null,"filterable":true,"groupby":true,"id":7419,"is_dttm":false,"python_date_format":null,"type":"Decimal(15, 2)","type_generic":0,"verbose_name":null},
              "datasourceWarning":false,
              "expressionType":"SIMPLE",
              "hasCustomLabel":true,
              "label":"Tutar",
              "optionName":"metric_y1mj68ttl4_1nde7zzxuc0j",
              "sqlExpression":null
            }
          ],
          "orderby": [
            [
              {
                "aggregate":"SUM",
                "column":{"advanced_data_type":null,"column_name":"VadeKalanGun","description":null,"expression":null,"filterable":true,"groupby":true,"id":7420,"is_dttm":false,"python_date_format":null,"type":"Nullable(Int64)","type_generic":0,"verbose_name":null},
                "datasourceWarning":false,
                "expressionType":"SIMPLE",
                "hasCustomLabel":false,
                "label":"SUM(VadeKalanGun)",
                "optionName":"metric_gwu8y9f6znu_v4wtjgyclho",
                "sqlExpression":null
              },
              false
            ]
          ],
          "annotation_layers": [],
          "row_limit": 1000,
          "series_limit": 0,
          "series_limit_metric": {
            "aggregate":"SUM",
            "column":{"advanced_data_type":null,"column_name":"VadeKalanGun","description":null,"expression":null,"filterable":true,"groupby":true,"id":7420,"is_dttm":false,"python_date_format":null,"type":"Nullable(Int64)","type_generic":0,"verbose_name":null},
            "datasourceWarning":false,
            "expressionType":"SIMPLE",
            "hasCustomLabel":false,
            "label":"SUM(VadeKalanGun)",
            "optionName":"metric_gwu8y9f6znu_v4wtjgyclho",
            "sqlExpression":null
          },
          "order_desc": true,
          "url_params": {"dashboard_page_id":"GulxjbioGz-6_xA0Jptak","slice_id":"1951"},
          "custom_params": {},
          "custom_form_data": {},
          "post_processing": [],
          "time_offsets": []
        }
      ],
      "form_data": {
        "datasource":"347__table",
        "viz_type":"table",
        "slice_id":1951,
        "url_params":{"dashboard_page_id":"GulxjbioGz-6_xA0Jptak","slice_id":"1951"},
        "query_mode":"aggregate",
        "groupby":[
          {"datasourceWarning":false,"expressionType":"SQL","label":"Vade Tarihi","sqlExpression":"VadeTarihiKisa"},
          {"datasourceWarning":false,"expressionType":"SQL","label":"Kalan Gün","sqlExpression":"VadeKalanGun"},
          {"datasourceWarning":false,"expressionType":"SQL","label":"Durum","sqlExpression":"VadeDurumu"}
        ],
        "time_grain_sqla":"P1D",
        "temporal_columns_lookup":{"VadeTarihi":true},
        "metrics":[
          {
            "aggregate":"SUM",
            "column":{"advanced_data_type":null,"column_name":"KalanTutar","description":null,"expression":null,"filterable":true,"groupby":true,"id":7419,"is_dttm":false,"python_date_format":null,"type":"Decimal(15, 2)","type_generic":0,"verbose_name":null},
            "datasourceWarning":false,
            "expressionType":"SIMPLE",
            "hasCustomLabel":true,
            "label":"Tutar",
            "optionName":"metric_y1mj68ttl4_1nde7zzxuc0j",
            "sqlExpression":null
          }
        ],
        "all_columns":["VadeTarihiKisa","VadeKalanGun","VadeDurumu"],
        "percent_metrics":[],
        "adhoc_filters":[{"clause":"WHERE","comparator":"No filter","expressionType":"SIMPLE","operator":"TEMPORAL_RANGE","subject":"VadeTarihi"}],
        "timeseries_limit_metric":{
          "aggregate":"SUM",
          "column":{"advanced_data_type":null,"column_name":"VadeKalanGun","description":null,"expression":null,"filterable":true,"groupby":true,"id":7420,"is_dttm":false,"python_date_format":null,"type":"Nullable(Int64)","type_generic":0,"verbose_name":null},
          "datasourceWarning":false,
          "expressionType":"SIMPLE",
          "hasCustomLabel":false,
          "label":"SUM(VadeKalanGun)",
          "optionName":"metric_gwu8y9f6znu_v4wtjgyclho",
          "sqlExpression":null
        },
        "order_by_cols":[],
        "order_desc":true,
        "row_limit":1000,
        "table_timestamp_format":"smart_date",
        "include_search":false,
        "allow_render_html":true,
        "show_cell_bars":true,
        "color_pn":true,
        "comparison_color_scheme":"Green",
        "conditional_formatting":[
          {"colorScheme":"#ACE1C4","column":"Kalan Gün","operator":"≥","targetValue":5},
          {"colorScheme":"#FDE380","column":"Kalan Gün","operator":"< x <","targetValueLeft":"0","targetValueRight":"5"},
          {"colorScheme":"#EFA1AA","column":"Kalan Gün","operator":"≤","targetValue":0}
        ],
        "comparison_type":"values",
        "extra_form_data":{},
        "force":true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=Y5tTlnNR0nE&dashboard_page_id=GulxjbioGz-6_xA0Jptak&slice_id=1951")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget91Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1956%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(4, TimeUnit.SECONDS)
                .readTimeout(4, TimeUnit.SECONDS)
                .writeTimeout(4, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource": {"id":345, "type":"table"},
      "force": true,
      "queries": [
        {
          "filters": [],
          "extras": {"having":"", "where":""},
          "applied_time_extras": {},
          "columns": [],
          "metrics": [
            {
              "aggregate":"SUM",
              "column":{
                "advanced_data_type": null,
                "certification_details": null,
                "certified_by": null,
                "column_name":"NormalVadeTutar",
                "description": null,
                "expression": null,
                "filterable": true,
                "groupby": true,
                "id": 7406,
                "is_certified": false,
                "is_dttm": false,
                "python_date_format": null,
                "type": "Decimal(38, 2)",
                "type_generic": 0,
                "verbose_name": null,
                "warning_markdown": null
              },
              "datasourceWarning": false,
              "expressionType": "SIMPLE",
              "hasCustomLabel": true,
              "label": "Normal Vadeli",
              "optionName": "metric_kvj0fi7bata_hemg8tda5zd",
              "sqlExpression": null
            }
          ],
          "annotation_layers": [],
          "series_limit": 0,
          "order_desc": true,
          "url_params": {"dashboard_page_id":"GulxjbioGz-6_xA0Jptak", "slice_id":"1956"},
          "custom_params": {},
          "custom_form_data": {}
        }
      ],
      "form_data": {
        "datasource":"345__table",
        "viz_type":"big_number_total",
        "slice_id":1956,
        "url_params":{"dashboard_page_id":"GulxjbioGz-6_xA0Jptak","slice_id":"1956"},
        "metric":{
          "aggregate":"SUM",
          "column":{
            "advanced_data_type": null,
            "certification_details": null,
            "certified_by": null,
            "column_name":"NormalVadeTutar",
            "description": null,
            "expression": null,
            "filterable": true,
            "groupby": true,
            "id": 7406,
            "is_certified": false,
            "is_dttm": false,
            "python_date_format": null,
            "type": "Decimal(38, 2)",
            "type_generic": 0,
            "verbose_name": null,
            "warning_markdown": null
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Normal Vadeli",
          "optionName": "metric_kvj0fi7bata_hemg8tda5zd",
          "sqlExpression": null
        },
        "adhoc_filters": [],
        "subheader":"Vadesi Gelmemiş",
        "header_font_size":0.4,
        "subheader_font_size":0.15,
        "y_axis_format":"SMART_NUMBER",
        "time_format":"smart_date",
        "conditional_formatting":[],
        "extra_form_data":{},
        "force": true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=TWVcKtZ979g&dashboard_page_id=GulxjbioGz-6_xA0Jptak&slice_id=1956")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Add this to your Requests class
    public static JSONObject sendWidget89Request() {
        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1952%7D&force=true";

        String jsonBody = "{\"datasource\":{\"id\":347,\"type\":\"table\"},\"force\":true,\"queries\":[{\"filters\":[{\"col\":\"VadeTarihi\",\"op\":\"TEMPORAL_RANGE\",\"val\":\"No filter\"}],\"extras\":{\"having\":\"\",\"where\":\"(VadeKalanGun > 0 AND VadeKalanGun <= 90)\"},\"applied_time_extras\":{},\"columns\":[],\"metrics\":[{\"aggregate\":\"SUM\",\"column\":{\"advanced_data_type\":null,\"certification_details\":null,\"certified_by\":null,\"column_name\":\"KalanTutar\",\"description\":null,\"expression\":null,\"filterable\":true,\"groupby\":true,\"id\":7419,\"is_certified\":false,\"is_dttm\":false,\"python_date_format\":null,\"type\":\"Decimal(15, 2)\",\"type_generic\":0,\"verbose_name\":null,\"warning_markdown\":null},\"datasourceWarning\":false,\"expressionType\":\"SIMPLE\",\"hasCustomLabel\":true,\"label\":\"Tutar\",\"optionName\":\"metric_y1mj68ttl4_1nde7zzxuc0j\",\"sqlExpression\":null}],\"annotation_layers\":[],\"series_limit\":0,\"order_desc\":true,\"url_params\":{\"dashboard_page_id\":\"GulxjbioGz-6_xA0Jptak\",\"slice_id\":\"1952\"},\"custom_params\":{},\"custom_form_data\":{}}],\"form_data\":{\"datasource\":\"347__table\",\"viz_type\":\"big_number_total\",\"slice_id\":1952,\"url_params\":{\"dashboard_page_id\":\"GulxjbioGz-6_xA0Jptak\",\"slice_id\":\"1952\"},\"metric\":{\"aggregate\":\"SUM\",\"column\":{\"advanced_data_type\":null,\"certification_details\":null,\"certified_by\":null,\"column_name\":\"KalanTutar\",\"description\":null,\"expression\":null,\"filterable\":true,\"groupby\":true,\"id\":7419,\"is_certified\":false,\"is_dttm\":false,\"python_date_format\":null,\"type\":\"Decimal(15, 2)\",\"type_generic\":0,\"verbose_name\":null,\"warning_markdown\":null},\"datasourceWarning\":false,\"expressionType\":\"SIMPLE\",\"hasCustomLabel\":true,\"label\":\"Tutar\",\"optionName\":\"metric_y1mj68ttl4_1nde7zzxuc0j\",\"sqlExpression\":null},\"adhoc_filters\":[{\"clause\":\"WHERE\",\"comparator\":\"No filter\",\"datasourceWarning\":false,\"expressionType\":\"SIMPLE\",\"filterOptionName\":\"filter_2srboofnpgw_ew5fi5c2kcr\",\"isExtra\":false,\"isNew\":false,\"operator\":\"TEMPORAL_RANGE\",\"sqlExpression\":null,\"subject\":\"VadeTarihi\"},{\"clause\":\"WHERE\",\"comparator\":null,\"datasourceWarning\":false,\"expressionType\":\"SQL\",\"filterOptionName\":\"filter_ewborm8um2k_zz8j1zeeox7\",\"isExtra\":false,\"isNew\":false,\"operator\":null,\"sqlExpression\":\"VadeKalanGun > 0 AND VadeKalanGun <= 90\",\"subject\":null}],\"subheader\":\"90 Gün İçinde Vadesi Gelecek\",\"header_font_size\":0.4,\"subheader_font_size\":0.15,\"y_axis_format\":\"SMART_NUMBER\",\"time_format\":\"smart_date\",\"conditional_formatting\":[],\"extra_form_data\":{},\"force\":true,\"result_format\":\"json\",\"result_type\":\"full\"},\"result_format\":\"json\",\"result_type\":\"full\"}";

        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json")
        );

        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=2o0_U-BQ2Go&dashboard_page_id=GulxjbioGz-6_xA0Jptak&slice_id=1952")
                .addHeader("User-Agent", "Mozilla/5.0");

        // Optional: inject Cookie / CSRF from env if needed
        String cookie = System.getenv("DIA_COOKIE");
        if (cookie != null && !cookie.isEmpty()) {
            reqBuilder.addHeader("Cookie", cookie);
        }
        String csrf = System.getenv("DIA_CSRF");
        if (csrf != null && !csrf.isEmpty()) {
            reqBuilder.addHeader("X-CSRFToken", csrf);
        }

        try (Response response = client.newCall(reqBuilder.build()).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("sendWidget89Request failed: HTTP " + response.code());
                return null;
            }
            String resp = response.body() != null ? response.body().string() : "";
            return new JSONObject(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget90Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1948%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource": {"id":353,"type":"table"},
      "force": true,
      "queries": [
        {
          "filters": [],
          "extras": {"having":"","where":""},
          "applied_time_extras": {},
          "columns": [],
          "metrics": [
            {
              "aggregate": null,
              "column": null,
              "datasourceWarning": false,
              "expressionType": "SQL",
              "hasCustomLabel": true,
              "label": "Vadesi Geçen",
              "optionName": "metric_4hjexpsijeu_42s2n6q609y",
              "sqlExpression": "SUM(VadesiGecenTutar)"
            }
          ],
          "annotation_layers": [],
          "series_limit": 0,
          "order_desc": true,
          "url_params": {"dashboard_page_id":"GulxjbioGz-6_xA0Jptak","slice_id":"1948"},
          "custom_params": {},
          "custom_form_data": {}
        }
      ],
      "form_data": {
        "datasource": "353__table",
        "viz_type": "big_number_total",
        "slice_id": 1948,
        "url_params": {"dashboard_page_id":"GulxjbioGz-6_xA0Jptak","slice_id":"1948"},
        "metric": {
          "aggregate": null,
          "column": null,
          "datasourceWarning": false,
          "expressionType": "SQL",
          "hasCustomLabel": true,
          "label": "Vadesi Geçen",
          "optionName": "metric_4hjexpsijeu_42s2n6q609y",
          "sqlExpression": "SUM(VadesiGecenTutar)"
        },
        "adhoc_filters": [],
        "subheader": "Vadesi Geçen",
        "header_font_size": 0.4,
        "subheader_font_size": 0.15,
        "y_axis_format": "SMART_NUMBER",
        "time_format": "smart_date",
        "conditional_formatting": [],
        "extra_form_data": {},
        "force": true,
        "result_format": "json",
        "result_type": "full"
      },
      "result_format": "json",
      "result_type": "full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=TZT22-RpEHw&dashboard_page_id=GulxjbioGz-6_xA0Jptak&slice_id=1948")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget55Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1752%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource": {"id":331,"type":"table"},
      "force": false,
      "queries": [
        {
          "filters": [
            {"col":"FM","op":"IN","val":["Yavuz Yeşil"]},
            {"col":"FM","op":"NOT IN","val":["ok"]},
            {"col":"SM","op":"NOT IN","val":["Kıbrıs"]},
            {"col":"FISCALYEAR","op":"IN","val":[2025]},
            {"col":"FISCALMONTH","op":"IN","val":[9]}
          ],
          "extras": {
            "time_grain_sqla": "P1D",
            "having": "",
            "where": "(ROTA NOT ILIKE '%LZM%' AND ROTA NOT ILIKE '%Büyük Nokta%' AND ROTA NOT ILIKE '%ok%')"
          },
          "applied_time_extras": {},
          "columns": [
            {"datasourceWarning": false, "expressionType":"SQL", "label":"FM", "sqlExpression":"FM"}
          ],
          "metrics": [
            {
              "aggregate": null,
              "column": null,
              "datasourceWarning": false,
              "expressionType": "SQL",
              "hasCustomLabel": true,
              "label": "Gerçekleşme %",
              "optionName": "metric_i4keqoezoi_9aeb2rb13uv",
              "sqlExpression": "sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
            }
          ],
          "orderby": [
            [
              {
                "aggregate": null,
                "column": null,
                "datasourceWarning": false,
                "expressionType": "SQL",
                "hasCustomLabel": true,
                "label": "Gerçekleşme %",
                "optionName": "metric_i4keqoezoi_9aeb2rb13uv",
                "sqlExpression": "sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
              },
              false
            ]
          ],
          "annotation_layers": [],
          "row_limit": 1000,
          "series_limit": 0,
          "order_desc": true,
          "url_params": {"dashboard_page_id":"Xiio5-y-oQ4KZF4iTs0EX","slice_id":"1752"},
          "custom_params": {},
          "custom_form_data": {},
          "post_processing": [],
          "time_offsets": []
        }
      ],
      "form_data": {
        "datasource":"331__table",
        "viz_type":"table",
        "slice_id":1752,
        "url_params":{"dashboard_page_id":"Xiio5-y-oQ4KZF4iTs0EX","slice_id":"1752"},
        "query_mode":"aggregate",
        "groupby":[{"datasourceWarning":false,"expressionType":"SQL","label":"FM","sqlExpression":"FM"}],
        "time_grain_sqla":"P1D",
        "temporal_columns_lookup":{},
        "metrics":[
          {
            "aggregate": null,
            "column": null,
            "datasourceWarning": false,
            "expressionType": "SQL",
            "hasCustomLabel": true,
            "label": "Gerçekleşme %",
            "optionName": "metric_i4keqoezoi_9aeb2rb13uv",
            "sqlExpression": "sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
          }
        ],
        "all_columns":[],
        "percent_metrics":[],
        "adhoc_filters":[
          {"expressionType":"SIMPLE","subject":"FM","operator":"IN","operatorId":"IN","comparator":["Yavuz Yeşil"],"clause":"WHERE","sqlExpression":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_hwds99d64d7_s2hi2tn0lsg"},
          {"expressionType":"SIMPLE","subject":"FM","operator":"NOT IN","operatorId":"NOT_IN","comparator":["ok"],"clause":"WHERE","sqlExpression":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_6i5r71q0m0q_ir9z5n2pf3"},
          {"expressionType":"SQL","sqlExpression":"ROTA NOT ILIKE '%LZM%' AND ROTA NOT ILIKE '%Büyük Nokta%' AND ROTA NOT ILIKE '%ok%'", "clause":"WHERE","subject":null,"operator":null,"comparator":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_ud8fdkffvm_cagn1pq0yrd"},
          {"expressionType":"SIMPLE","subject":"SM","operator":"NOT IN","operatorId":"NOT_IN","comparator":["Kıbrıs"],"clause":"WHERE","sqlExpression":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_hrb97nszlal_82jgbrj7oly"},
          {"expressionType":"SIMPLE","subject":"FISCALYEAR","operator":"IN","operatorId":"IN","comparator":[2025],"clause":"WHERE","sqlExpression":null,"isExtra":true,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_pvyxe3nz3y_4od8gqb03k"},
          {"expressionType":"SIMPLE","subject":"FISCALMONTH","operator":"IN","operatorId":"IN","comparator":[9],"clause":"WHERE","sqlExpression":null,"isExtra":true,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_zjiccqu3cvo_8x8rcoy5khm"}
        ],
        "order_by_cols":[],
        "row_limit":1000,
        "table_timestamp_format":"smart_date",
        "allow_render_html":true,
        "column_config":{"Gerçekleşme %":{"d3NumberFormat":".2%"}},
        "show_cell_bars":true,
        "color_pn":true,
        "comparison_color_scheme":"Green",
        "conditional_formatting":[],
        "comparison_type":"values",
        "extra_form_data":{},
        "force": false,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=f99fBBAS2MY&dashboard_page_id=Xiio5-y-oQ4KZF4iTs0EX&slice_id=1752")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget48AggreationS49Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1844%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource": {"id":331,"type":"table"},
      "force": true,
      "queries": [{
        "filters":[
          {"col":"FISCALYEAR","op":"IN","val":[2025]},
          {"col":"FISCALMONTH","op":"IN","val":[9]}
        ],
        "extras":{
          "having":"",
          "where":"(upperUTF8(replaceAll(replaceAll(FM, 'ı', 'i'), 'İ', 'I')) LIKE upperUTF8(replaceAll(replaceAll('Yavuz Yeşil', 'ı', 'i'), 'İ', 'I'))) AND (ROTA NOT ILIKE '%LZM%' AND ROTA NOT ILIKE '%Büyük Nokta%')"
        },
        "applied_time_extras":{},
        "columns":[],
        "metrics":[{
          "aggregate":null,
          "column":null,
          "datasourceWarning":true,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Gerçekleşme %",
          "optionName":"metric_zb0zhchi3ka_zdkjsc4njos",
          "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
        }],
        "annotation_layers":[],
        "series_limit":0,
        "order_desc":true,
        "url_params":{"dashboard_page_id":"WF8g3owQZuEKA7yV-Fekh","slice_id":"1844"},
        "custom_params":{},
        "custom_form_data":{}
      }],
      "form_data":{
        "datasource":"331__table",
        "viz_type":"big_number_total",
        "slice_id":1844,
        "url_params":{"dashboard_page_id":"WF8g3owQZuEKA7yV-Fekh","slice_id":"1844"},
        "metric":{
          "aggregate":null,
          "column":null,
          "datasourceWarning":true,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Gerçekleşme %",
          "optionName":"metric_zb0zhchi3ka_zdkjsc4njos",
          "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
        },
        "adhoc_filters":[
          {
            "clause":"WHERE",
            "datasourceWarning":true,
            "expressionType":"SQL",
            "filterOptionName":"filter_duyy03s6ggs_mylqnrd4o9",
            "isExtra":false,
            "isNew":false,
            "operator":null,
            "sqlExpression":"upperUTF8(replaceAll(replaceAll(FM, 'ı', 'i'), 'İ', 'I')) LIKE upperUTF8(replaceAll(replaceAll('Yavuz Yeşil', 'ı', 'i'), 'İ', 'I'))",
            "subject":null
          },
          {
            "clause":"WHERE",
            "datasourceWarning":false,
            "expressionType":"SQL",
            "filterOptionName":"filter_2t5wwzuo4k_do6wrxbi1qc",
            "isExtra":false,
            "isNew":false,
            "operator":null,
            "sqlExpression":"ROTA NOT ILIKE '%LZM%' AND ROTA NOT ILIKE '%Büyük Nokta%'",
            "subject":null
          },
          {"expressionType":"SIMPLE","clause":"WHERE","operator":"IN","operatorId":"IN","subject":"FISCALYEAR","comparator":[2025],"isExtra":true,"filterOptionName":"filter_8u1txz9q7q_5wbggefgbvy"},
          {"expressionType":"SIMPLE","clause":"WHERE","operator":"IN","operatorId":"IN","subject":"FISCALMONTH","comparator":[9],"isExtra":true,"filterOptionName":"filter_ml8vkemzr7_chi00hgez8h"}
        ],
        "subheader":"Gerçekleşme %",
        "header_font_size":0.4,
        "subheader_font_size":0.15,
        "y_axis_format":".2%",
        "time_format":"smart_date",
        "conditional_formatting":[],
        "extra_form_data":{},
        "force":true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=WSmKJ7kHkbM&dashboard_page_id=WF8g3owQZuEKA7yV-Fekh&slice_id=1844")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget56Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1753%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource":{"id":331,"type":"table"},
      "force": false,
      "queries": [{
        "filters": [
          {"col":"SM","op":"NOT IN","val":["Kıbrıs"]},
          {"col":"FISCALYEAR","op":"IN","val":[2025]},
          {"col":"FISCALMONTH","op":"IN","val":[9]},
          {"col":"ROTA","op":"IN","val":["PRESELL D. HAY-BAB ROTASI"]}
        ],
        "extras":{
          "having":"",
          "where":"(ROTA NOT IN ('ZM','Presell Alfa Lzm-1 Bölgesi','Presell Alfa Lzm-2 Bölgesi','Presell Alfa Lzm-4 Bölgesi','Presell Ankara Laçin Lzm Bölgesi','Presell Doruk Lzm Bölgesi','Presell Dünya Lzm Bölgesi','Presell Gram Lzm Bölgesi','Presell İzmir Gürpa LZM Bölgesi','Presell İzmir Pirim LZM Bölgesi','Presell Rit Lzm Bölgesi','Presell SGM Lzm Bölgesi Yeni','Presell Edremit Bayraktar LZM Bölgesi','Presell Asya Gürpa LZM Bölgesi','Presell Çanakkale Bayraktar LZM Bölgesi','Presell Gülener LZM Bölgesi','Presell Doğuş LZM Bölgesi','Presell Özyiğit LZM Bölgesi','Presell Asya Doğuş LZM Bölgesi','Presell İstanbul Pirim LZM Bölgesi','Presell Gürpa LZM Bölgesi')) AND (ROTA NOT ILIKE '%LZM%' AND ROTA NOT ILIKE '%Büyük Nokta%' AND ROTA NOT ILIKE '%ok%')"
        },
        "applied_time_extras":{},
        "columns":["ROTA"],
        "metrics":[{
          "aggregate":null,
          "column":null,
          "datasourceWarning":false,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Gerçekleşme %",
          "optionName":"metric_dvp0yxp1g7_qgt9jx77zq",
          "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
        }],
        "orderby":[[{
          "aggregate":null,"column":null,"datasourceWarning":false,
          "expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme %",
          "optionName":"metric_dvp0yxp1g7_qgt9jx77zq",
          "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
        }, false]],
        "annotation_layers":[],
        "row_limit":1000,
        "series_limit":0,
        "order_desc":true,
        "url_params":{"dashboard_page_id":"Xiio5-y-oQ4KZF4iTs0EX","slice_id":"1753"},
        "custom_params":{},
        "custom_form_data":{},
        "post_processing":[],
        "time_offsets":[]
      }],
      "form_data":{
        "datasource":"331__table",
        "viz_type":"table",
        "slice_id":1753,
        "url_params":{"dashboard_page_id":"Xiio5-y-oQ4KZF4iTs0EX","slice_id":"1753"},
        "query_mode":"aggregate",
        "groupby":["ROTA"],
        "metrics":[{
          "aggregate":null,"column":null,"datasourceWarning":false,
          "expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme %",
          "optionName":"metric_dvp0yxp1g7_qgt9jx77zq",
          "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
        }],
        "adhoc_filters":[
          {"expressionType":"SQL","sqlExpression":"ROTA NOT IN ('ZM','Presell Alfa Lzm-1 Bölgesi','Presell Alfa Lzm-2 Bölgesi','Presell Alfa Lzm-4 Bölgesi','Presell Ankara Laçin Lzm Bölgesi','Presell Doruk Lzm Bölgesi','Presell Dünya Lzm Bölgesi','Presell Gram Lzm Bölgesi','Presell İzmir Gürpa LZM Bölgesi','Presell İzmir Pirim LZM Bölgesi','Presell Rit Lzm Bölgesi','Presell SGM Lzm Bölgesi Yeni','Presell Edremit Bayraktar LZM Bölgesi','Presell Asya Gürpa LZM Bölgesi','Presell Çanakkale Bayraktar LZM Bölgesi','Presell Gülener LZM Bölgesi','Presell Doğuş LZM Bölgesi','Presell Özyiğit LZM Bölgesi','Presell Asya Doğuş LZM Bölgesi','Presell İstanbul Pirim LZM Bölgesi','Presell Gürpa LZM Bölgesi')","clause":"WHERE","subject":null,"operator":null,"comparator":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_0rapvb5u7vyl_j4uao54bnwn"},
          {"expressionType":"SQL","sqlExpression":"ROTA NOT ILIKE '%LZM%' AND ROTA NOT ILIKE '%Büyük Nokta%' AND ROTA NOT ILIKE '%ok%'","clause":"WHERE","subject":null,"operator":null,"comparator":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_1clzkloc29s_f1pl88fp95"},
          {"expressionType":"SIMPLE","subject":"SM","operator":"NOT IN","operatorId":"NOT_IN","comparator":["Kıbrıs"],"clause":"WHERE","sqlExpression":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_r25uk6syg19_duh6y2w0aqv"},
          {"expressionType":"SIMPLE","subject":"FISCALYEAR","operator":"IN","operatorId":"IN","comparator":[2025],"clause":"WHERE","sqlExpression":null,"isExtra":true,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_ip3o0li0tbk_hvxbq1gt2kn"},
          {"expressionType":"SIMPLE","subject":"FISCALMONTH","operator":"IN","operatorId":"IN","comparator":[9],"clause":"WHERE","sqlExpression":null,"isExtra":true,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_5wr5k41ufwm_ccr28xvncge"},
          {"expressionType":"SIMPLE","subject":"ROTA","operator":"IN","operatorId":"IN","comparator":["PRESELL D. HAY-BAB ROTASI"],"clause":"WHERE","sqlExpression":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_tarzzkmx5d_90qbdf7hce"}
        ],
        "table_timestamp_format":"smart_date",
        "allow_render_html":true,
        "column_config":{"Gerçekleşme %":{"d3NumberFormat":".2%"}},
        "show_cell_bars":true,
        "color_pn":true,
        "comparison_color_scheme":"Green",
        "comparison_type":"values",
        "extra_form_data":{},
        "force": false,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept","application/json")
                .addHeader("Content-Type","application/json")
                .addHeader("Accept-Language","en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin","https://dia-dashboard.efectura.com")
                .addHeader("Referer","https://dia-dashboard.efectura.com/explore/?form_data_key=zyK_-UMsB3c&dashboard_page_id=Xiio5-y-oQ4KZF4iTs0EX&slice_id=1753")
                .addHeader("User-Agent","Mozilla/5.0")
                .addHeader("X-CSRFToken","")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body()!=null ? resp.body().string() : ""));
            String respStr = resp.body()!=null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget54Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1751%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource":{"id":330,"type":"table"},
      "force": false,
      "queries":[
        {
          "filters":[
            {"col":"SM","op":"IN","val":["Trakya"]},
            {"col":"SM","op":"NOT IN","val":["Kıbrıs"]},
            {"col":"FISCALYEAR","op":"IN","val":[2025]},
            {"col":"FISCALMONTH","op":"IN","val":[9]}
          ],
          "extras":{"time_grain_sqla":"P1D","having":"","where":"(ROTA NOT ILIKE '%LZM%')"},
          "applied_time_extras":{},
          "columns":[{"datasourceWarning":false,"expressionType":"SQL","label":"SM","sqlExpression":"SM"}],
          "metrics":[
            {
              "aggregate":null,"column":null,"datasourceWarning":false,
              "expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme %",
              "optionName":"metric_rfhiqkqrg6r_jxn0f6umwz8",
              "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
            }
          ],
          "orderby":[[
            {
              "aggregate":null,"column":null,"datasourceWarning":false,
              "expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme %",
              "optionName":"metric_rfhiqkqrg6r_jxn0f6umwz8",
              "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
            }, false
          ]],
          "annotation_layers":[],
          "row_limit":1000,
          "series_limit":0,
          "order_desc":true,
          "url_params":{"dashboard_page_id":"Xiio5-y-oQ4KZF4iTs0EX","slice_id":"1751"},
          "custom_params":{},
          "custom_form_data":{},
          "post_processing":[],
          "time_offsets":[]
        }
      ],
      "form_data":{
        "datasource":"330__table",
        "viz_type":"table",
        "slice_id":1751,
        "url_params":{"dashboard_page_id":"Xiio5-y-oQ4KZF4iTs0EX","slice_id":"1751"},
        "query_mode":"aggregate",
        "groupby":[{"datasourceWarning":false,"expressionType":"SQL","label":"SM","sqlExpression":"SM"}],
        "time_grain_sqla":"P1D",
        "metrics":[
          {
            "aggregate":null,"column":null,"datasourceWarning":false,
            "expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme %",
            "optionName":"metric_rfhiqkqrg6r_jxn0f6umwz8",
            "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
          }
        ],
        "adhoc_filters":[
          {"expressionType":"SIMPLE","subject":"SM","operator":"IN","operatorId":"IN","comparator":["Trakya"],"clause":"WHERE","sqlExpression":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_l18j9ps7f1d_loc53ua31y"},
          {"expressionType":"SIMPLE","subject":"SM","operator":"NOT IN","operatorId":"NOT_IN","comparator":["Kıbrıs"],"clause":"WHERE","sqlExpression":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_uxnujx5ulik_v54ztk69sk"},
          {"expressionType":"SQL","sqlExpression":"ROTA NOT ILIKE '%LZM%'","clause":"WHERE","subject":null,"operator":null,"comparator":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_8xx6vshabiv_9tyg41mgica"},
          {"expressionType":"SIMPLE","subject":"FISCALYEAR","operator":"IN","operatorId":"IN","comparator":[2025],"clause":"WHERE","sqlExpression":null,"isExtra":true,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_75fk1m6nb7q_rhdiapxx6z"},
          {"expressionType":"SIMPLE","subject":"FISCALMONTH","operator":"IN","operatorId":"IN","comparator":[9],"clause":"WHERE","sqlExpression":null,"isExtra":true,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_3hikov6i1tg_rglh6kha5dn"}
        ],
        "order_by_cols":[],
        "order_desc":true,
        "server_page_length":10,
        "table_timestamp_format":"smart_date",
        "allow_render_html":true,
        "column_config":{"Gerçekleşme %":{"d3NumberFormat":".2%"}},
        "show_cell_bars":true,
        "color_pn":true,
        "comparison_color_scheme":"Green",
        "conditional_formatting":[],
        "comparison_type":"values",
        "extra_form_data":{},
        "force": false,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=HYLaEToqa8g&dashboard_page_id=Xiio5-y-oQ4KZF4iTs0EX&slice_id=1751")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget48AggreationS48Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1830%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
    {
      "datasource":{"id":330,"type":"table"},
      "force": false,
      "queries":[
        {
          "filters":[
            {"col":"FISCALYEAR","op":"IN","val":[2025]},
            {"col":"FISCALMONTH","op":"IN","val":[9]}
          ],
          "extras":{
            "having":"",
            "where":"(upperUTF8(replaceAll(replaceAll(SM, 'ı', 'i'), 'İ', 'I')) LIKE upperUTF8(replaceAll(replaceAll({% if url_param('SM') %}\\n\\t'{{ url_param('SM') }}'\\n{% else %}\\n  'Trakya'\\n{% endif %}, 'ı', 'i'), 'İ', 'I'))) AND (ROTA NOT ILIKE '%LZM%')"
          },
          "applied_time_extras":{},
          "columns":[],
          "metrics":[
            {
              "aggregate":null,
              "column":null,
              "datasourceWarning":false,
              "expressionType":"SQL",
              "hasCustomLabel":true,
              "label":"Gerçekleşme %",
              "optionName":"metric_zb0zhchi3ka_zdkjsc4njos",
              "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
            }
          ],
          "annotation_layers":[],
          "series_limit":0,
          "order_desc":true,
          "url_params":{"dashboard_page_id":"8eq4u6FIYgP6PZyg-xYFf","slice_id":"1830"},
          "custom_params":{},
          "custom_form_data":{}
        }
      ],
      "form_data":{
        "datasource":"330__table",
        "viz_type":"big_number_total",
        "slice_id":1830,
        "url_params":{"dashboard_page_id":"8eq4u6FIYgP6PZyg-xYFf","slice_id":"1830"},
        "metric":{
          "aggregate":null,
          "column":null,
          "datasourceWarning":false,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Gerçekleşme %",
          "optionName":"metric_zb0zhchi3ka_zdkjsc4njos",
          "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
        },
        "adhoc_filters":[
          {
            "clause":"WHERE",
            "comparator":null,
            "datasourceWarning":false,
            "expressionType":"SQL",
            "filterOptionName":"filter_aa3jorsprak_17b8ioyw878",
            "isExtra":false,
            "isNew":false,
            "operator":null,
            "sqlExpression":"upperUTF8(replaceAll(replaceAll(SM, 'ı', 'i'), 'İ', 'I')) LIKE upperUTF8(replaceAll(replaceAll({% if url_param('SM') %}\\n\\t'{{ url_param('SM') }}'\\n{% else %}\\n  'Trakya'\\n{% endif %}, 'ı', 'i'), 'İ', 'I'))",
            "subject":null
          },
          {
            "clause":"WHERE",
            "comparator":null,
            "datasourceWarning":false,
            "expressionType":"SQL",
            "filterOptionName":"filter_50csvahnpde_9fcdrn379gq",
            "isExtra":false,
            "isNew":false,
            "operator":null,
            "sqlExpression":"ROTA NOT ILIKE '%LZM%'",
            "subject":null
          },
          {"expressionType":"SIMPLE","clause":"WHERE","operator":"IN","operatorId":"IN","subject":"FISCALYEAR","comparator":[2025],"isExtra":true,"filterOptionName":"filter_v7cfl2eogkr_b87i460gia"},
          {"expressionType":"SIMPLE","clause":"WHERE","operator":"IN","operatorId":"IN","subject":"FISCALMONTH","comparator":[9],"isExtra":true,"filterOptionName":"filter_gi6yt57zztt_qae00eon0z"}
        ],
        "subheader":"Gerçekleşme %",
        "header_font_size":0.4,
        "subheader_font_size":0.15,
        "y_axis_format":".2%",
        "time_format":"smart_date",
        "conditional_formatting":[],
        "extra_form_data":{},
        "force": false,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=28tJXgN64WI&dashboard_page_id=8eq4u6FIYgP6PZyg-xYFf&slice_id=1830")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget53Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1750%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        // Dinamik yıl ve ay
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        // Template (YEAR/MONTH dinamik)
        String template = """
{
  "datasource":{"id":330,"type":"table"},
  "force": false,
  "queries":[
    {
      "filters":[
        {"col":"BM","op":"IN","val":["Marmara Bölge"]},
        {"col":"SM","op":"NOT IN","val":["Kıbrıs"]},
        {"col":"FISCALYEAR","op":"IN","val":[${YEAR}]},
        {"col":"FISCALMONTH","op":"IN","val":[${MONTH}]}
      ],
      "extras":{"having":"","where":"(ROTA NOT ILIKE '%LZM%')"},
      "applied_time_extras":{},
      "columns":["BM"],
      "metrics":[
        {
          "aggregate":null,
          "column":null,
          "datasourceWarning":false,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Gerçekleşme %",
          "optionName":"metric_z6k9pder6gd_xe3ph1kyopd",
          "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
        }
      ],
      "orderby":[[
        {
          "aggregate":null,
          "column":null,
          "datasourceWarning":false,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Gerçekleşme %",
          "optionName":"metric_z6k9pder6gd_xe3ph1kyopd",
          "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
        }, false
      ]],
      "annotation_layers":[],
      "row_limit":1000,
      "series_limit":0,
      "order_desc":true,
      "url_params":{"dashboard_page_id":"Xiio5-y-oQ4KZF4iTs0EX","slice_id":"1750"},
      "custom_params":{},
      "custom_form_data":{},
      "post_processing":[],
      "time_offsets":[]
    }
  ],
  "form_data":{
    "datasource":"330__table",
    "viz_type":"table",
    "slice_id":1750,
    "url_params":{"dashboard_page_id":"Xiio5-y-oQ4KZF4iTs0EX","slice_id":"1750"},
    "query_mode":"aggregate",
    "groupby":["BM"],
    "metrics":[
      {
        "aggregate":null,
        "column":null,
        "datasourceWarning":false,
        "expressionType":"SQL",
        "hasCustomLabel":true,
        "label":"Gerçekleşme %",
        "optionName":"metric_z6k9pder6gd_xe3ph1kyopd",
        "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
      }
    ],
    "adhoc_filters":[
      {"expressionType":"SIMPLE","subject":"BM","operator":"IN","operatorId":"IN","comparator":["Marmara Bölge"],"clause":"WHERE","sqlExpression":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_fpen5yml4wj_sy8hecj09ue"},
      {"expressionType":"SIMPLE","subject":"SM","operator":"NOT IN","operatorId":"NOT_IN","comparator":["Kıbrıs"],"clause":"WHERE","sqlExpression":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_79pgmzhjx38_1sff87o3gd6"},
      {"expressionType":"SQL","sqlExpression":"ROTA NOT ILIKE '%LZM%'", "clause":"WHERE","subject":null,"operator":null,"comparator":null,"isExtra":false,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_bo8mbesc5r_gq8a8rl811v"},
      {"expressionType":"SIMPLE","subject":"FISCALYEAR","operator":"IN","operatorId":"IN","comparator":[${YEAR}],"clause":"WHERE","sqlExpression":null,"isExtra":true,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_1j0wvmc7bvs_7k9ttcz12fp"},
      {"expressionType":"SIMPLE","subject":"FISCALMONTH","operator":"IN","operatorId":"IN","comparator":[${MONTH}],"clause":"WHERE","sqlExpression":null,"isExtra":true,"isNew":false,"datasourceWarning":false,"filterOptionName":"filter_gp3r4usejdg_pkiff3hzrn"}
    ],
    "column_config":{"Gerçekleşme %":{"d3NumberFormat":".2%"}},
    "order_desc":true,
    "row_limit":1000,
    "server_page_length":10,
    "allow_render_html":true,
    "show_cell_bars":true,
    "color_pn":true,
    "comparison_color_scheme":"Green",
    "conditional_formatting":[],
    "comparison_type":"values",
    "extra_form_data":{},
    "force": false,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        String body = template
                .replace("${YEAR}", String.valueOf(currentYear))
                .replace("${MONTH}", String.valueOf(currentMonth));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=cQ8CxGxFPUY&dashboard_page_id=Xiio5-y-oQ4KZF4iTs0EX&slice_id=1750")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget48Request(String bmValue) {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1816%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");
        if (bmValue == null || bmValue.isBlank()) bmValue = "Marmara";

        // 🔹 İçinde bulunduğumuz yıl/ay
        int currentYear  = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        // where ve BM filtresi (Jinja yok; sabit değer)
        String whereClause =
                "(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1] " +
                        "LIKE " +
                        "splitByChar(' ', upperUTF8(replaceAll(replaceAll('" + bmValue + "', 'ı', 'i'), 'İ', 'I')))[1]) " +
                        "AND (ROTA NOT ILIKE '%LZM%')";

        String bmSqlFilter =
                "splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1] " +
                        "LIKE " +
                        "splitByChar(' ', upperUTF8(replaceAll(replaceAll('" + bmValue + "', 'ı', 'i'), 'İ', 'I')))[1]";

        try {
            // metric
            JSONObject metric = new JSONObject()
                    .put("aggregate", JSONObject.NULL)
                    .put("column", JSONObject.NULL)
                    .put("datasourceWarning", false)
                    .put("expressionType", "SQL")
                    .put("hasCustomLabel", true)
                    .put("label", "Gerçekleşme %")
                    .put("optionName", "metric_zb0zhchi3ka_zdkjsc4njos")
                    .put("sqlExpression", "sum(Total_Sales) / nullIf(sum(TotalTarget), 0)");

            // queries[0]
            JSONObject query0 = new JSONObject()
                    .put("filters", new JSONArray()
                            .put(new JSONObject()
                                    .put("col", "FISCALYEAR")
                                    .put("op", "IN")
                                    .put("val", new JSONArray().put(currentYear)))    // ← dinamik yıl
                            .put(new JSONObject()
                                    .put("col", "FISCALMONTH")
                                    .put("op", "IN")
                                    .put("val", new JSONArray().put(currentMonth)))   // ← dinamik ay
                    )
                    .put("extras", new JSONObject()
                            .put("having", "")
                            .put("where", whereClause)
                    )
                    .put("applied_time_extras", new JSONObject())
                    .put("columns", new JSONArray())
                    .put("metrics", new JSONArray().put(metric))
                    .put("annotation_layers", new JSONArray())
                    .put("series_limit", 0)
                    .put("order_desc", true)
                    .put("url_params", new JSONObject()
                            .put("dashboard_page_id", "pHSLgQAVyKoqIcsgv5HUW")
                            .put("slice_id", "1816")
                    )
                    .put("custom_params", new JSONObject())
                    .put("custom_form_data", new JSONObject());

            // form_data.adhoc_filters (yıl/ay dinamik)
            JSONArray adhocFilters = new JSONArray()
                    .put(new JSONObject()
                            .put("clause", "WHERE")
                            .put("comparator", JSONObject.NULL)
                            .put("datasourceWarning", false)
                            .put("expressionType", "SQL")
                            .put("filterOptionName", "filter_bm_match")
                            .put("isExtra", false)
                            .put("isNew", false)
                            .put("operator", JSONObject.NULL)
                            .put("sqlExpression", bmSqlFilter)
                    )
                    .put(new JSONObject()
                            .put("clause", "WHERE")
                            .put("comparator", JSONObject.NULL)
                            .put("datasourceWarning", false)
                            .put("expressionType", "SQL")
                            .put("filterOptionName", "filter_no_lzm")
                            .put("isExtra", false)
                            .put("isNew", false)
                            .put("operator", JSONObject.NULL)
                            .put("sqlExpression", "ROTA NOT ILIKE '%LZM%'")
                    )
                    .put(new JSONObject()
                            .put("expressionType", "SIMPLE")
                            .put("clause", "WHERE")
                            .put("operator", "IN")
                            .put("operatorId", "IN")
                            .put("subject", "FISCALYEAR")
                            .put("comparator", new JSONArray().put(currentYear))       // ← dinamik yıl
                            .put("isExtra", true)
                            .put("filterOptionName", "filter_year")
                    )
                    .put(new JSONObject()
                            .put("expressionType", "SIMPLE")
                            .put("clause", "WHERE")
                            .put("operator", "IN")
                            .put("operatorId", "IN")
                            .put("subject", "FISCALMONTH")
                            .put("comparator", new JSONArray().put(currentMonth))      // ← dinamik ay
                            .put("isExtra", true)
                            .put("filterOptionName", "filter_month")
                    );

            JSONObject formData = new JSONObject()
                    .put("datasource", "330__table")
                    .put("viz_type", "big_number_total")
                    .put("slice_id", 1816)
                    .put("url_params", new JSONObject()
                            .put("dashboard_page_id", "pHSLgQAVyKoqIcsgv5HUW")
                            .put("slice_id", "1816"))
                    .put("metric", metric)
                    .put("adhoc_filters", adhocFilters)
                    .put("subheader", "Gerçekleşme %")
                    .put("header_font_size", 0.4)
                    .put("subheader_font_size", 0.15)
                    .put("y_axis_format", ".2%")
                    .put("time_format", "smart_date")
                    .put("conditional_formatting", new JSONArray())
                    .put("extra_form_data", new JSONObject())
                    .put("force", true)
                    .put("result_format", "json")
                    .put("result_type", "full");

            JSONObject payload = new JSONObject()
                    .put("datasource", new JSONObject().put("id", 330).put("type", "table"))
                    .put("force", true)
                    .put("queries", new JSONArray().put(query0))
                    .put("form_data", formData)
                    .put("result_format", "json")
                    .put("result_type", "full");

            RequestBody requestBody = RequestBody.create(
                    payload.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                    .addHeader("Origin", "https://dia-dashboard.efectura.com")
                    .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=ryIUc8cITPg&dashboard_page_id=pHSLgQAVyKoqIcsgv5HUW&slice_id=1816")
                    .addHeader("User-Agent", "Mozilla/5.0")
                    .addHeader("Cookie", cookie)
                    .post(requestBody)
                    .build();

            try (Response resp = client.newCall(request).execute()) {
                String respStr = resp.body() != null ? resp.body().string() : "";
                if (!resp.isSuccessful()) {
                    throw new IOException("Unexpected code " + resp.code() + " - " + respStr);
                }
                return new JSONObject(respStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget52Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1819%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        // İçinde bulunduğumuz yıl/ay
        int currentYear  = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        // Jinja şablonları 400’e sebep olabildiği için "Marmara"yı düz metin olarak gömdüm.
        String where =
                "(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1] " +
                        "LIKE splitByChar(' ', upperUTF8(replaceAll(replaceAll('Marmara', 'ı', 'i'), 'İ', 'I')))[1]) " +
                        "AND (ROTA NOT ILIKE '%LZM%')";

        // BODY şablonu: Yıl/Ay yer tutucuları
        String template = "{\n" +
                "  \"datasource\": {\"id\": 330, \"type\": \"table\"},\n" +
                "  \"force\": true,\n" +
                "  \"queries\": [{\n" +
                "    \"filters\": [\n" +
                "      {\"col\": \"FISCALYEAR\", \"op\": \"IN\", \"val\": [${YEAR}]},\n" +
                "      {\"col\": \"FISCALMONTH\", \"op\": \"IN\", \"val\": [${MONTH}]}\n" +
                "    ],\n" +
                "    \"extras\": {\"time_grain_sqla\": \"P1D\", \"having\": \"\", \"where\": \"" + where.replace("\"","\\\"") + "\"},\n" +
                "    \"applied_time_extras\": {},\n" +
                "    \"columns\": [\n" +
                "      {\"datasourceWarning\": true, \"expressionType\": \"SQL\", \"label\": \"Ürün Kategori\", \"sqlExpression\": \"ProductCat\"},\n" +
                "      \"Product\"\n" +
                "    ],\n" +
                "    \"metrics\": [\n" +
                "      {\"expressionType\": \"SQL\", \"hasCustomLabel\": true, \"label\": \"İç Hedef\", \"sqlExpression\": \"SUM(TotalTarget)\"},\n" +
                "      {\"expressionType\": \"SQL\", \"hasCustomLabel\": true, \"label\": \"Gerçekleşme\", \"sqlExpression\": \"SUM(Total_Sales)\"},\n" +
                "      {\"expressionType\": \"SQL\", \"hasCustomLabel\": true, \"label\": \"Gerçekleşme %\", \"sqlExpression\": \"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)*100.0\"}\n" +
                "    ],\n" +
                "    \"orderby\": [[{\"expressionType\": \"SQL\", \"label\": \"İç Hedef\", \"sqlExpression\": \"SUM(TotalTarget)\"}, false]],\n" +
                "    \"annotation_layers\": [],\n" +
                "    \"row_limit\": 50000,\n" +
                "    \"series_limit\": 0,\n" +
                "    \"order_desc\": true,\n" +
                "    \"url_params\": {\"dashboard_page_id\": \"pHSLgQAVyKoqIcsgv5HUW\", \"slice_id\": \"1819\"},\n" +
                "    \"custom_params\": {},\n" +
                "    \"custom_form_data\": {}\n" +
                "  }],\n" +
                "  \"form_data\": {\n" +
                "    \"datasource\": \"330__table\",\n" +
                "    \"viz_type\": \"pivot_table_v2\",\n" +
                "    \"slice_id\": 1819,\n" +
                "    \"url_params\": {\"dashboard_page_id\": \"pHSLgQAVyKoqIcsgv5HUW\", \"slice_id\": \"1819\"},\n" +
                "    \"groupbyColumns\": [\n" +
                "      {\"datasourceWarning\": true, \"expressionType\": \"SQL\", \"label\": \"Ürün Kategori\", \"sqlExpression\": \"ProductCat\"},\n" +
                "      \"Product\"\n" +
                "    ],\n" +
                "    \"groupbyRows\": [],\n" +
                "    \"time_grain_sqla\": \"P1D\",\n" +
                "    \"metrics\": [\n" +
                "      {\"expressionType\": \"SQL\", \"hasCustomLabel\": true, \"label\": \"İç Hedef\", \"sqlExpression\": \"SUM(TotalTarget)\"},\n" +
                "      {\"expressionType\": \"SQL\", \"hasCustomLabel\": true, \"label\": \"Gerçekleşme\", \"sqlExpression\": \"SUM(Total_Sales)\"},\n" +
                "      {\"expressionType\": \"SQL\", \"hasCustomLabel\": true, \"label\": \"Gerçekleşme %\", \"sqlExpression\": \"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)*100.0\"}\n" +
                "    ],\n" +
                "    \"metricsLayout\": \"COLUMNS\",\n" +
                "    \"adhoc_filters\": [\n" +
                "      {\"clause\": \"WHERE\", \"expressionType\": \"SQL\", \"sqlExpression\": \"" + where.replace("\"","\\\"") + "\"},\n" +
                "      {\"clause\": \"WHERE\", \"expressionType\": \"SQL\", \"sqlExpression\": \"ROTA NOT ILIKE '%LZM%'\"},\n" +
                "      {\"expressionType\": \"SIMPLE\", \"clause\": \"WHERE\", \"operator\": \"IN\", \"operatorId\": \"IN\", \"subject\": \"FISCALYEAR\", \"comparator\": [${YEAR}], \"isExtra\": true},\n" +
                "      {\"expressionType\": \"SIMPLE\", \"clause\": \"WHERE\", \"operator\": \"IN\", \"operatorId\": \"IN\", \"subject\": \"FISCALMONTH\", \"comparator\": [${MONTH}], \"isExtra\": true}\n" +
                "    ],\n" +
                "    \"row_limit\": 50000,\n" +
                "    \"order_desc\": true,\n" +
                "    \"aggregateFunction\": \"Sum\",\n" +
                "    \"rowTotals\": false,\n" +
                "    \"rowSubTotals\": true,\n" +
                "    \"colTotals\": true,\n" +
                "    \"colSubTotals\": true,\n" +
                "    \"transposePivot\": true,\n" +
                "    \"valueFormat\": \"SMART_NUMBER\",\n" +
                "    \"date_format\": \"smart_date\",\n" +
                "    \"rowOrder\": \"key_a_to_z\",\n" +
                "    \"colOrder\": \"key_a_to_z\",\n" +
                "    \"conditional_formatting\": [],\n" +
                "    \"allow_render_html\": true,\n" +
                "    \"extra_form_data\": {},\n" +
                "    \"force\": true,\n" +
                "    \"result_format\": \"json\",\n" +
                "    \"result_type\": \"full\"\n" +
                "  },\n" +
                "  \"result_format\": \"json\",\n" +
                "  \"result_type\": \"full\"\n" +
                "}";

        String body = template
                .replace("${YEAR}", String.valueOf(currentYear))
                .replace("${MONTH}", String.valueOf(currentMonth));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=5dTlQ0nDxTo&dashboard_page_id=pHSLgQAVyKoqIcsgv5HUW&slice_id=1819")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget45Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1820%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear  = LocalDate.now().getYear();

        // ŞABLON: Yıl/Ay için ${YEAR} ve ${MONTH} yer tutucuları kullandık
        String template = """
    {
      "datasource":{"id":330,"type":"table"},
      "force":true,
      "queries":[
        {
          "filters":[
            {"col":"FISCALYEAR","op":"IN","val":[${YEAR}]},
            {"col":"FISCALMONTH","op":"IN","val":[${MONTH}]}
          ],
          "extras":{
            "having":"",
            "where":"(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\nLIKE \\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\n    {% if url_param('BM') %}\\n      '{{ url_param('BM') }}'\\n    {% else %}\\n      'Marmara'\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (ROTA NOT ILIKE '%LZM%')"
          },
          "applied_time_extras":{},
          "columns":[],
          "metrics":[
            {
              "aggregate":"SUM",
              "column":{"column_name":"Total_Sales"},
              "expressionType":"SIMPLE",
              "label":"SUM(Total_Sales)"
            }
          ],
          "annotation_layers":[],
          "series_limit":0,
          "order_desc":true,
          "url_params":{"dashboard_page_id":"G3J7lAQ8fSBRoVCthsylN","slice_id":"1820"},
          "custom_params":{},
          "custom_form_data":{}
        }
      ],
      "form_data":{
        "datasource":"330__table",
        "viz_type":"big_number_total",
        "slice_id":1820,
        "url_params":{"dashboard_page_id":"G3J7lAQ8fSBRoVCthsylN","slice_id":"1820"},
        "metric":{
          "aggregate":"SUM",
          "column":{"column_name":"Total_Sales"},
          "expressionType":"SIMPLE",
          "label":"SUM(Total_Sales)"
        },
        "adhoc_filters":[
          {
            "clause":"WHERE",
            "expressionType":"SQL",
            "sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\nLIKE \\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\n    {% if url_param('BM') %}\\n      '{{ url_param('BM') }}'\\n    {% else %}\\n      'Marmara'\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]"
          },
          {
            "clause":"WHERE",
            "expressionType":"SQL",
            "sqlExpression":"ROTA NOT ILIKE '%LZM%'"
          },
          {"expressionType":"SIMPLE","clause":"WHERE","operator":"IN","subject":"FISCALYEAR","comparator":[${YEAR}]},
          {"expressionType":"SIMPLE","clause":"WHERE","operator":"IN","subject":"FISCALMONTH","comparator":[${MONTH}]}
        ],
        "subheader":"Gerçekleşme",
        "header_font_size":0.4,
        "subheader_font_size":0.15,
        "y_axis_format":"SMART_NUMBER",
        "time_format":"smart_date",
        "force":true,
        "result_format":"json",
        "result_type":"full"
      },
      "result_format":"json",
      "result_type":"full"
    }
    """;

        String body = template
                .replace("${YEAR}", String.valueOf(currentYear))
                .replace("${MONTH}", String.valueOf(currentMonth));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=qHm37zLgRXM&dashboard_page_id=G3J7lAQ8fSBRoVCthsylN&slice_id=1820")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }





    public static JSONObject sendWidget40Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A381%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource":{"id":209,"type":"table"},
  "force":true,
  "queries":[
    {
      "filters":[
        {"col":"DISTRIBUTOR_KOD","op":"NOT IN","val":["EDIRNE MAHALO"]}
      ],
      "extras":{"having":"","where":""},
      "applied_time_extras":{},
      "columns":[
        {"datasourceWarning":false,"expressionType":"SQL","label":"Ürün","sqlExpression":"URUN_KISA_AD"}
      ],
      "metrics":[
        {
          "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
          "label":"Kalan Stok Gün","optionName":"metric_t2nf5uauaar_heczve27fu",
          "sqlExpression":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n"
        }
      ],
      "orderby":[
        [
          {
            "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":false,
            "label":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n",
            "optionName":"metric_4lwx5jbmw3q_vo733pmf2lj",
            "sqlExpression":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n"
          },
          true
        ]
      ],
      "annotation_layers":[],
      "row_limit":5,
      "series_limit":0,
      "series_limit_metric":{
        "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":false,
        "label":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n",
        "optionName":"metric_4lwx5jbmw3q_vo733pmf2lj",
        "sqlExpression":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n"
      },
      "order_desc":false,
      "url_params":{"dashboard_page_id":"RQU5lFjUavfRCcebEFYwP","slice_id":"381"},
      "custom_params":{},
      "custom_form_data":{},
      "post_processing":[],
      "time_offsets":[]
    }
  ],
  "form_data":{
    "datasource":"209__table",
    "viz_type":"table",
    "slice_id":381,
    "url_params":{"dashboard_page_id":"RQU5lFjUavfRCcebEFYwP","slice_id":"381"},
    "query_mode":"aggregate",
    "groupby":[{"datasourceWarning":false,"expressionType":"SQL","label":"Ürün","sqlExpression":"URUN_KISA_AD"}],
    "temporal_columns_lookup":{},
    "metrics":[
      {
        "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,
        "label":"Kalan Stok Gün","optionName":"metric_t2nf5uauaar_heczve27fu",
        "sqlExpression":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n"
      }
    ],
    "all_columns":[],
    "percent_metrics":[],
    "adhoc_filters":[
      {
        "clause":"WHERE","comparator":["EDIRNE MAHALO"],"datasourceWarning":false,"expressionType":"SIMPLE",
        "filterOptionName":"filter_4cgeyudndwt_i44wzvtvdg","isExtra":false,"isNew":false,"operator":"NOT IN","operatorId":"NOT_IN",
        "sqlExpression":null,"subject":"DISTRIBUTOR_KOD"
      }
    ],
    "timeseries_limit_metric":{
      "aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":false,
      "label":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n",
      "optionName":"metric_4lwx5jbmw3q_vo733pmf2lj",
      "sqlExpression":" SUM(CAST(COALESCE(Stock_Liters, 0) AS FLOAT))\\r\\n /\\r\\n SUM(\\r\\n CAST(\\r\\n CASE\\r\\n WHEN COALESCE(Est_Total_Sales_Liters, 0) < 0\\r\\n THEN 0\\r\\n ELSE COALESCE(Est_Total_Sales_Liters, 0)\\r\\n END\\r\\n AS FLOAT\\r\\n )\\r\\n ) * 1.0 \\r\\n"
    },
    "order_by_cols":[],
    "order_desc":false,
    "row_limit":"5",
    "table_timestamp_format":"smart_date",
    "allow_render_html":true,
    "show_cell_bars":true,
    "color_pn":true,
    "comparison_color_scheme":"Green",
    "conditional_formatting":[],
    "comparison_type":"values",
    "extra_form_data":{},
    "force":true,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=Aa1QeuB45nY&dashboard_page_id=RQU5lFjUavfRCcebEFYwP&slice_id=381")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget50Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1824%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        // İçinde bulunduğumuz yıl/ay
        int currentYear  = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        // ${YEAR} ve ${MONTH} yer tutucularını kullanıyoruz (formatted() kullanmıyoruz!)
        String template = """
{
  "datasource":{"id":381,"type":"table"},
  "force":true,
  "queries":[
    {
      "filters":[
        {"col":"FISCALYEAR","op":"IN","val":[${YEAR}]},
        {"col":"FISCALMONTH","op":"IN","val":[${MONTH}]}
      ],
      "extras":{
        "time_grain_sqla":"P1D",
        "having":"",
        "where":"(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (ROTA NOT ILIKE '%LZM%')"
      },
      "applied_time_extras":{},
      "columns":[
        {"datasourceWarning":false,"expressionType":"SQL","label":"Ürün Kategori","sqlExpression":"ProductCat"}
      ],
      "metrics":[
        {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_massxws5la_ljiii0bepu","sqlExpression":"SUM(month_target)"},
        {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme","optionName":"metric_6g3co30fvdr_tijy1lnx78","sqlExpression":"SUM(Total_Sales)"},
        {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Günlük Ort. Gerç.","optionName":"metric_aqogyk8i8p_qrbeo7vunbs","sqlExpression":"SUM(ytd_sales)\\r\\n/ nullIf(\\r\\n    length(\\r\\n        arrayFilter(\\r\\n            x ->\\r\\n                dayOfWeek(\\r\\n                    addDays(\\r\\n                        toStartOfMonth(\\r\\n                            toDate(                     \\r\\n                                concat(\\r\\n                                    toString(toUInt32(any(FISCALYEAR))),\\r\\n                                    '-',\\r\\n                                    leftPad(\\r\\n                                        toString(toUInt32(any(FISCALMONTH))),\\r\\n                                        2,\\r\\n                                        '0'\\r\\n                                    ),\\r\\n                                    '-01'\\r\\n                                )\\r\\n                            )\\r\\n                        ),\\r\\n                        x\\r\\n                    )\\r\\n                ) BETWEEN 1 AND 5,                     \\r\\n            range(\\r\\n                toUInt64(\\r\\n                    dateDiff(\\r\\n                        'day',\\r\\n                        toStartOfMonth(\\r\\n                            toDate(\\r\\n                                concat(\\r\\n                                    toString(toUInt32(any(FISCALYEAR))),\\r\\n                                    '-',\\r\\n                                    leftPad(\\r\\n                                        toString(toUInt32(any(FISCALMONTH))),\\r\\n                                        2,\\r\\n                                        '0'\\r\\n                                    ),\\r\\n                                    '-01'\\r\\n                                )\\r\\n                            )\\r\\n                        ),\\r\\n                        least(\\r\\n                            today(),\\r\\n                            addMonths(\\r\\n                                toStartOfMonth(\\r\\n                                    toDate(\\r\\n                                        concat(\\r\\n                                            toString(toUInt32(any(FISCALYEAR))),\\r\\n                                            '-',\\r\\n                                            leftPad(\\r\\n                                                toString(toUInt32(any(FISCALMONTH))),\\r\\n                                                2,\\r\\n                                                '0'\\r\\n                                            ),\\r\\n                                            '-01'\\r\\n                                        )\\r\\n                                    )\\r\\n                                ),\\r\\n                                1\\r\\n                            )\\r\\n                        )\\r\\n                    )\\r\\n                )\\r\\n            )\\r\\n        )\\r\\n    ),\\r\\n    0                           \\r\\n)\\r\\n"},
        {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Günlük Hedef","optionName":"metric_cm904h3ovh_q9vxpav3boa","sqlExpression":"(SUM(month_target) - SUM(ytd_sales)) / MAX(bd_remaining_from_today_inclusive)"},
        {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme %","optionName":"metric_6znao65vssp_yshas122kff","sqlExpression":"(sum(ytd_sales)+sum(today_sales)) / nullIf(sum(month_target), 0)*100.0"}
      ],
      "orderby":[[{"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_massxws5la_ljiii0bepu","sqlExpression":"SUM(month_target)"},false]],
      "annotation_layers":[],
      "row_limit":50000,
      "series_limit":0,
      "order_desc":true,
      "url_params":{"dashboard_page_id":"8ul2z96El8lQAsQzkEbFM","form_data_key":"GlqUaLJJkoQ","save_action":"overwrite","slice_id":"1824"},
      "custom_params":{},
      "custom_form_data":{}
    }
  ],
  "form_data":{
    "datasource":"381__table",
    "viz_type":"pivot_table_v2",
    "slice_id":1824,
    "url_params":{"dashboard_page_id":"8ul2z96El8lQAsQzkEbFM","form_data_key":"GlqUaLJJkoQ","save_action":"overwrite","slice_id":"1824"},
    "groupbyColumns":[{"datasourceWarning":false,"expressionType":"SQL","label":"Ürün Kategori","sqlExpression":"ProductCat"}],
    "groupbyRows":[],
    "time_grain_sqla":"P1D",
    "temporal_columns_lookup":{},
    "metrics":[
      {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"İç Hedef","optionName":"metric_massxws5la_ljiii0bepu","sqlExpression":"SUM(month_target)"},
      {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme","optionName":"metric_6g3co30fvdr_tijy1lnx78","sqlExpression":"SUM(Total_Sales)"},
      {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Günlük Ort. Gerç.","optionName":"metric_aqogyk8i8p_qrbeo7vunbs","sqlExpression":"SUM(ytd_sales)\\r\\n/ nullIf(\\r\\n    length(\\r\\n        arrayFilter(\\r\\n            x ->\\r\\n                dayOfWeek(\\r\\n                    addDays(\\r\\n                        toStartOfMonth(\\r\\n                            toDate(                     \\r\\n                                concat(\\r\\n                                    toString(toUInt32(any(FISCALYEAR))),\\r\\n                                    '-',\\r\\n                                    leftPad(\\r\\n                                        toString(toUInt32(any(FISCALMONTH))),\\r\\n                                        2,\\r\\n                                        '0'\\r\\n                                    ),\\r\\n                                    '-01'\\r\\n                                )\\r\\n                            )\\r\\n                        ),\\r\\n                        x\\r\\n                    )\\r\\n                ) BETWEEN 1 AND 5,                     \\r\\n            range(\\r\\n                toUInt64(\\r\\n                    dateDiff(\\r\\n                        'day',\\r\\n                        toStartOfMonth(\\r\\n                            toDate(\\r\\n                                concat(\\r\\n                                    toString(toUInt32(any(FISCALYEAR))),\\r\\n                                    '-',\\r\\n                                    leftPad(\\r\\n                                        toString(toUInt32(any(FISCALMONTH))),\\r\\n                                        2,\\r\\n                                        '0'\\r\\n                                    ),\\r\\n                                    '-01'\\r\\n                                )\\r\\n                            )\\r\\n                        ),\\r\\n                        least(\\r\\n                            today(),\\r\\n                            addMonths(\\r\\n                                toStartOfMonth(\\r\\n                                    toDate(\\r\\n                                        concat(\\r\\n                                            toString(toUInt32(any(FISCALYEAR))),\\r\\n                                            '-',\\r\\n                                            leftPad(\\r\\n                                                toString(toUInt32(any(FISCALMONTH))),\\r\\n                                                2,\\r\\n                                                '0'\\r\\n                                            ),\\r\\n                                            '-01'\\r\\n                                        )\\r\\n                                    )\\r\\n                                ),\\r\\n                                1\\r\\n                            )\\r\\n                        )\\r\\n                    )\\r\\n                )\\r\\n            )\\r\\n        )\\r\\n    ),\\r\\n    0                           \\r\\n)\\r\\n"},
      {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Günlük Hedef","optionName":"metric_cm904h3ovh_q9vxpav3boa","sqlExpression":"(SUM(month_target) - SUM(ytd_sales)) / MAX(bd_remaining_from_today_inclusive)"},
      {"aggregate":null,"column":null,"datasourceWarning":false,"expressionType":"SQL","hasCustomLabel":true,"label":"Gerçekleşme %","optionName":"metric_6znao65vssp_yshas122kff","sqlExpression":"(sum(ytd_sales)+sum(today_sales)) / nullIf(sum(month_target), 0)*100.0"}
    ],
    "metricsLayout":"COLUMNS",
    "adhoc_filters":[
      {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_wwqt5i7ya5n_43is682hogw","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]","subject":null},
      {"clause":"WHERE","comparator":null,"datasourceWarning":false,"expressionType":"SQL","filterOptionName":"filter_sr6ocxmxu3k_u3j8p3gephi","isExtra":false,"isNew":false,"operator":null,"sqlExpression":"ROTA NOT ILIKE '%LZM%'","subject":null},
      {"clause":"WHERE","comparator":[${YEAR}],"expressionType":"SIMPLE","filterOptionName":"filter_xe9fjgoicur_ziglwxlb8wn","isExtra":true,"operator":"IN","operatorId":"IN","subject":"FISCALYEAR"},
      {"clause":"WHERE","comparator":[${MONTH}],"expressionType":"SIMPLE","filterOptionName":"filter_qbeck43gv4_a3x1ywbd5aq","isExtra":true,"operator":"IN","operatorId":"IN","subject":"FISCALMONTH"}
    ],
    "row_limit":50000,
    "order_desc":true,
    "aggregateFunction":"Sum",
    "rowTotals":false,
    "rowSubTotals":true,
    "colTotals":true,
    "colSubTotals":true,
    "transposePivot":true,
    "valueFormat":"SMART_NUMBER",
    "date_format":"smart_date",
    "rowOrder":"key_a_to_z",
    "colOrder":"key_a_to_z",
    "conditional_formatting":[],
    "allow_render_html":true,
    "extra_form_data":{},
    "force":true,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        String body = template
                .replace("${YEAR}", String.valueOf(currentYear))
                .replace("${MONTH}", String.valueOf(currentMonth));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=GlqUaLJJkoQ&dashboard_page_id=8ul2z96El8lQAsQzkEbFM&slice_id=1824&save_action=overwrite")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static JSONObject sendWidget46Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1817%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(4, TimeUnit.SECONDS)
                .readTimeout(4, TimeUnit.SECONDS)
                .writeTimeout(4, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        // İçinde bulunduğumuz yıl/ay
        int currentYear  = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        // ŞABLON: YEAR/MONTH yer tutucularını body içinde her geçtiği yerde kullan
        String template = """
{
  "datasource":{"id":372,"type":"table"},
  "force":true,
  "queries":[
    {
      "filters":[
        {"col":"FISCALYEAR","op":"IN","val":[${YEAR}]},
        {"col":"FISCALMONTH","op":"IN","val":[${MONTH}]}
      ],
      "extras":{
        "having":"",
        "where":"(splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]) AND (ROTA NOT ILIKE '%LZM%')"
      },
      "applied_time_extras":{},
      "columns":[],
      "metrics":[
        {
          "aggregate":null,
          "column":null,
          "datasourceWarning":false,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Ort. Günlük Gerçekleşme",
          "optionName":"metric_lko7lwgwphj_o76jqjipan",
          "sqlExpression":"SUM(Total_Sales)\\r\\n/ nullIf(\\r\\n    length(\\r\\n        arrayFilter(\\r\\n            x ->\\r\\n                dayOfWeek(\\r\\n                    addDays(\\r\\n                        toStartOfMonth(\\r\\n                            toDate(                     \\r\\n                                concat(\\r\\n                                    toString(toUInt32(any(FISCALYEAR))),\\r\\n                                    '-',\\r\\n                                    leftPad(\\r\\n                                        toString(toUInt32(any(FISCALMONTH))),\\r\\n                                        2,\\r\\n                                        '0'\\r\\n                                    ),\\r\\n                                    '-01'\\r\\n                                )\\r\\n                            )\\r\\n                        ),\\r\\n                        x\\r\\n                    )\\r\\n                ) BETWEEN 1 AND 5,                     \\r\\n            range(\\r\\n                toUInt64(\\r\\n                    dateDiff(\\r\\n                        'day',\\r\\n                        toStartOfMonth(\\r\\n                            toDate(\\r\\n                                concat(\\r\\n                                    toString(toUInt32(any(FISCALYEAR))),\\r\\n                                    '-',\\r\\n                                    leftPad(\\r\\n                                        toString(toUInt32(any(FISCALMONTH))),\\r\\n                                        2,\\r\\n                                        '0'\\r\\n                                    ),\\r\\n                                    '-01'\\r\\n                                )\\r\\n                            )\\r\\n                        ),\\r\\n                        least(\\r\\n                            today(),\\r\\n                            addMonths(\\r\\n                                toStartOfMonth(\\r\\n                                    toDate(\\r\\n                                        concat(\\r\\n                                            toString(toUInt32(any(FISCALYEAR))),\\r\\n                                            '-',\\r\\n                                            leftPad(\\r\\n                                                toString(toUInt32(any(FISCALMONTH))),\\r\\n                                                2,\\r\\n                                                '0'\\r\\n                                            ),\\r\\n                                            '-01'\\r\\n                                        )\\r\\n                                    )\\r\\n                                ),\\r\\n                                1\\r\\n                            )\\r\\n                        )\\r\\n                    )\\r\\n                )\\r\\n            )\\r\\n        )\\r\\n    ),\\r\\n    0                           \\r\\n)\\r\\n"
        }
      ],
      "annotation_layers":[],
      "series_limit":0,
      "order_desc":true,
      "url_params":{"dashboard_page_id":"pHSLgQAVyKoqIcsgv5HUW","slice_id":"1817"},
      "custom_params":{},
      "custom_form_data":{}
    }
  ],
  "form_data":{
    "datasource":"372__table",
    "viz_type":"big_number_total",
    "slice_id":1817,
    "url_params":{"dashboard_page_id":"pHSLgQAVyKoqIcsgv5HUW","slice_id":"1817"},
    "metric":{
      "aggregate":null,
      "column":null,
      "datasourceWarning":false,
      "expressionType":"SQL",
      "hasCustomLabel":true,
      "label":"Ort. Günlük Gerçekleşme",
      "optionName":"metric_lko7lwgwphj_o76jqjipan",
      "sqlExpression":"SUM(Total_Sales)\\r\\n/ nullIf(\\r\\n    length(\\r\\n        arrayFilter(\\r\\n            x ->\\r\\n                dayOfWeek(\\r\\n                    addDays(\\r\\n                        toStartOfMonth(\\r\\n                            toDate(                     \\r\\n                                concat(\\r\\n                                    toString(toUInt32(any(FISCALYEAR))),\\r\\n                                    '-',\\r\\n                                    leftPad(\\r\\n                                        toString(toUInt32(any(FISCALMONTH))),\\r\\n                                        2,\\r\\n                                        '0'\\r\\n                                    ),\\r\\n                                    '-01'\\r\\n                                )\\r\\n                            )\\r\\n                        ),\\r\\n                        x\\r\\n                    )\\r\\n                ) BETWEEN 1 AND 5,                     \\r\\n            range(\\r\\n                toUInt64(\\r\\n                    dateDiff(\\r\\n                        'day',\\r\\n                        toStartOfMonth(\\r\\n                            toDate(\\r\\n                                concat(\\r\\n                                    toString(toUInt32(any(FISCALYEAR))),\\r\\n                                    '-',\\r\\n                                    leftPad(\\r\\n                                        toString(toUInt32(any(FISCALMONTH))),\\r\\n                                        2,\\r\\n                                        '0'\\r\\n                                    ),\\r\\n                                    '-01'\\r\\n                                )\\r\\n                            )\\r\\n                        ),\\r\\n                        least(\\r\\n                            today(),\\r\\n                            addMonths(\\r\\n                                toStartOfMonth(\\r\\n                                    toDate(\\r\\n                                        concat(\\r\\n                                            toString(toUInt32(any(FISCALYEAR))),\\r\\n                                            '-',\\r\\n                                            leftPad(\\r\\n                                                toString(toUInt32(any(FISCALMONTH))),\\r\\n                                                2,\\r\\n                                                '0'\\r\\n                                            ),\\r\\n                                            '-01'\\r\\n                                        )\\r\\n                                    )\\r\\n                                ),\\r\\n                                1\\r\\n                            )\\r\\n                        )\\r\\n                    )\\r\\n                )\\r\\n            )\\r\\n        )\\r\\n    ),\\r\\n    0                           \\r\\n)\\r\\n"
    },
    "adhoc_filters":[
      {
        "clause":"WHERE",
        "comparator":null,
        "datasourceWarning":false,
        "expressionType":"SQL",
        "filterOptionName":"filter_ct8uxzqywqh_hgrac6lz4f",
        "isExtra":false,
        "isNew":false,
        "operator":null,
        "sqlExpression":"splitByChar(' ', upperUTF8(replaceAll(replaceAll(ifNull(BM, ''), 'ı', 'i'), 'İ', 'I')))[1]\\r\\nLIKE \\r\\nsplitByChar(' ', upperUTF8(replaceAll(replaceAll(\\r\\n    {% if url_param('BM') %}\\r\\n      '{{ url_param('BM') }}'\\r\\n    {% else %}\\r\\n      'Marmara'\\r\\n    {% endif %}, 'ı', 'i'), 'İ', 'I')))[1]"
      },
      {
        "clause":"WHERE",
        "comparator":null,
        "datasourceWarning":false,
        "expressionType":"SQL",
        "filterOptionName":"filter_4chxm8vm2ud_16zdyw9czvuj",
        "isExtra":false,
        "isNew":false,
        "operator":null,
        "sqlExpression":"ROTA NOT ILIKE '%LZM%'"
      },
      {"expressionType":"SIMPLE","clause":"WHERE","operator":"IN","operatorId":"IN","subject":"FISCALYEAR","comparator":[${YEAR}],"isExtra":true,"filterOptionName":"filter_971tj403659_rf5srdng4o"},
      {"expressionType":"SIMPLE","clause":"WHERE","operator":"IN","operatorId":"IN","subject":"FISCALMONTH","comparator":[${MONTH}],"isExtra":true,"filterOptionName":"filter_64dvbgz1qg_0yexo9xc1up"}
    ],
    "subheader":"Günlük Ort.  Gerçekleşme",
    "header_font_size":0.4,
    "subheader_font_size":0.15,
    "y_axis_format":"SMART_NUMBER",
    "time_format":"smart_date",
    "extra_form_data":{},
    "force":true,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        String body = template
                .replace("${YEAR}", String.valueOf(currentYear))
                .replace("${MONTH}", String.valueOf(currentMonth));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=8KxX6Si7QwE&dashboard_page_id=pHSLgQAVyKoqIcsgv5HUW&slice_id=1817")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget48AggreationS50Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1844%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource":{"id":331,"type":"table"},
  "force":false,
  "queries":[
    {
      "filters":[
        {"col":"FISCALYEAR","op":"IN","val":[2025]},
        {"col":"FISCALMONTH","op":"IN","val":[9]},
        {"col":"ROTA","op":"IN","val":["PRESELL D. HAY-BAB ROTASI"]}
      ],
      "extras":{
        "having":"",
        "where":"(ROTA NOT ILIKE '%LZM%' AND ROTA NOT ILIKE '%Büyük Nokta%')"
      },
      "applied_time_extras":{},
      "columns":[],
      "metrics":[
        {
          "aggregate":null,
          "column":null,
          "datasourceWarning":true,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Gerçekleşme %",
          "optionName":"metric_zb0zhchi3ka_zdkjsc4njos",
          "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
        }
      ],
      "annotation_layers":[],
      "series_limit":0,
      "order_desc":true,
      "url_params":{"dashboard_page_id":"WF8g3owQZuEKA7yV-Fekh","slice_id":"1844"},
      "custom_params":{},
      "custom_form_data":{}
    }
  ],
  "form_data":{
    "datasource":"331__table",
    "viz_type":"big_number_total",
    "slice_id":1844,
    "url_params":{"dashboard_page_id":"WF8g3owQZuEKA7yV-Fekh","slice_id":"1844"},
    "metric":{
      "aggregate":null,
      "column":null,
      "datasourceWarning":true,
      "expressionType":"SQL",
      "hasCustomLabel":true,
      "label":"Gerçekleşme %",
      "optionName":"metric_zb0zhchi3ka_zdkjsc4njos",
      "sqlExpression":"sum(Total_Sales) / nullIf(sum(TotalTarget), 0)"
    },
    "adhoc_filters":[
      {
        "expressionType":"SQL",
        "sqlExpression":"ROTA NOT ILIKE '%LZM%' AND ROTA NOT ILIKE '%Büyük Nokta%'",
        "clause":"WHERE",
        "subject":null,
        "operator":null,
        "comparator":null,
        "isExtra":false,
        "isNew":false,
        "datasourceWarning":false,
        "filterOptionName":"filter_2t5wwzuo4k_do6wrxbi1qc"
      },
      {
        "expressionType":"SIMPLE",
        "subject":"FISCALYEAR",
        "operator":"IN",
        "operatorId":"IN",
        "comparator":[2025],
        "clause":"WHERE",
        "sqlExpression":null,
        "isExtra":true,
        "isNew":false,
        "datasourceWarning":false,
        "filterOptionName":"filter_8u1txz9q7q_5wbggefgbvy"
      },
      {
        "expressionType":"SIMPLE",
        "subject":"FISCALMONTH",
        "operator":"IN",
        "operatorId":"IN",
        "comparator":[9],
        "clause":"WHERE",
        "sqlExpression":null,
        "isExtra":true,
        "isNew":false,
        "datasourceWarning":false,
        "filterOptionName":"filter_ml8vkemzr7_chi00hgez8h"
      },
      {
        "expressionType":"SIMPLE",
        "subject":"ROTA",
        "operator":"IN",
        "operatorId":"IN",
        "comparator":["PRESELL D. HAY-BAB ROTASI"],
        "clause":"WHERE",
        "sqlExpression":null,
        "isExtra":false,
        "isNew":false,
        "datasourceWarning":false,
        "filterOptionName":"filter_ajomv1cu18h_tcaj06sps0b"
      }
    ],
    "subheader":"Gerçekleşme %",
    "header_font_size":0.4,
    "subheader_font_size":0.15,
    "y_axis_format":".2%",
    "time_format":"smart_date",
    "conditional_formatting":[],
    "extra_form_data":{},
    "force":false,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=WSmKJ7kHkbM&dashboard_page_id=WF8g3owQZuEKA7yV-Fekh&slice_id=1844")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget36Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A271%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource":{"id":285,"type":"table"},
  "force":true,
  "queries":[
    {
      "filters":[
        {"col":"TARIH","op":"TEMPORAL_RANGE","val":"No filter"}
      ],
      "extras":{
        "time_grain_sqla":"P1D",
        "having":"",
        "where":"(TARIH >= DATE_TRUNC('Month', NOW()) AND TARIH <= today())"
      },
      "applied_time_extras":{},
      "columns":[
        {
          "timeGrain":"P1D",
          "columnType":"BASE_AXIS",
          "sqlExpression":"TARIH",
          "label":"TARIH",
          "expressionType":"SQL"
        }
      ],
      "metrics":[
        {
          "aggregate":"SUM",
          "column":{
            "advanced_data_type":null,
            "certification_details":null,
            "certified_by":null,
            "column_name":"BASARILI_MUSTERI_SAYISI",
            "description":null,
            "expression":null,
            "filterable":true,
            "groupby":true,
            "id":6427,
            "is_certified":false,
            "is_dttm":false,
            "python_date_format":null,
            "type":"UInt64",
            "type_generic":0,
            "verbose_name":null,
            "warning_markdown":null
          },
          "datasourceWarning":false,
          "expressionType":"SIMPLE",
          "hasCustomLabel":true,
          "label":"Müşteri Sayısı",
          "optionName":"metric_6trs7pqmoa4_qhujh1pq79",
          "sqlExpression":null
        }
      ],
      "annotation_layers":[],
      "series_limit":0,
      "order_desc":true,
      "url_params":{"dashboard_page_id":"5NCptbSXXst-z0R9ZBf4L","slice_id":"271"},
      "custom_params":{},
      "custom_form_data":{},
      "post_processing":[
        {
          "operation":"pivot",
          "options":{
            "index":["TARIH"],
            "columns":[],
            "aggregates":{"Müşteri Sayısı":{"operator":"mean"}},
            "drop_missing_columns":true
          }
        },
        {"operation":"flatten"}
      ]
    }
  ],
  "form_data":{
    "datasource":"285__table",
    "viz_type":"big_number",
    "slice_id":271,
    "url_params":{"dashboard_page_id":"5NCptbSXXst-z0R9ZBf4L","slice_id":"271"},
    "x_axis":"TARIH",
    "time_grain_sqla":"P1D",
    "metric":{
      "aggregate":"SUM",
      "column":{
        "advanced_data_type":null,
        "certification_details":null,
        "certified_by":null,
        "column_name":"BASARILI_MUSTERI_SAYISI",
        "description":null,
        "expression":null,
        "filterable":true,
        "groupby":true,
        "id":6427,
        "is_certified":false,
        "is_dttm":false,
        "python_date_format":null,
        "type":"UInt64",
        "type_generic":0,
        "verbose_name":null,
        "warning_markdown":null
      },
      "datasourceWarning":false,
      "expressionType":"SIMPLE",
      "hasCustomLabel":true,
      "label":"Müşteri Sayısı",
      "optionName":"metric_6trs7pqmoa4_qhujh1pq79",
      "sqlExpression":null
    },
    "adhoc_filters":[
      {
        "clause":"WHERE",
        "comparator":null,
        "datasourceWarning":false,
        "expressionType":"SQL",
        "filterOptionName":"filter_l6pj38q1gi_84i05zipt9g",
        "isExtra":false,
        "isNew":false,
        "operator":"TEMPORAL_RANGE",
        "sqlExpression":"TARIH >= DATE_TRUNC('Month', NOW()) AND TARIH <= today()",
        "subject":"TARIH"
      },
      {
        "clause":"WHERE",
        "comparator":"No filter",
        "datasourceWarning":false,
        "expressionType":"SIMPLE",
        "filterOptionName":"filter_w8ek5nfksmf_27af1wj5qym",
        "isExtra":false,
        "isNew":false,
        "operator":"TEMPORAL_RANGE",
        "sqlExpression":null,
        "subject":"TARIH"
      }
    ],
    "compare_lag":1,
    "compare_suffix":"DoD",
    "show_timestamp":true,
    "show_trend_line":true,
    "start_y_axis_at_zero":true,
    "color_picker":{"a":1,"b":135,"g":122,"r":0},
    "header_font_size":0.4,
    "subheader_font_size":0.15,
    "y_axis_format":",d",
    "time_format":"%d/%m/%Y",
    "rolling_type":"None",
    "extra_form_data":{},
    "force":true,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=XwQfk01CUPQ&dashboard_page_id=5NCptbSXXst-z0R9ZBf4L&slice_id=271")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget37Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A273%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource":{"id":216,"type":"table"},
  "force":true,
  "queries":[
    {
      "filters":[{"col":"SM","op":"IS NOT NULL"}],
      "extras":{"having":"","where":"(TARIH = today())"},
      "applied_time_extras":{},
      "columns":["ROUTE_NAME","FM"],
      "metrics":[
        {
          "aggregate":null,
          "column":null,
          "datasourceWarning":false,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Ziyaret İç Hedef",
          "optionName":"metric_ab6mdk3a4ng_dgsu9m79ruf",
          "sqlExpression":"SUM(PLANANAN_MUSTERI_SAYISI)"
        },
        {
          "aggregate":null,
          "column":null,
          "datasourceWarning":false,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Gerçekleşen Ziyaret",
          "optionName":"metric_8xew93462zr_buovqxd5f5m",
          "sqlExpression":"SUM(YAPILAN_ZIYARET_MUSTERI_SAYISI)"
        },
        {
          "aggregate":null,
          "column":null,
          "datasourceWarning":false,
          "expressionType":"SQL",
          "hasCustomLabel":true,
          "label":"Başarılı Ziyaret",
          "optionName":"metric_plr6q85g48q_9sjxo33ugn",
          "sqlExpression":"SUM(BASARILI_MUSTERI_SAYISI)/SUM(YAPILAN_ZIYARET_MUSTERI_SAYISI)*1.0"
        }
      ],
      "annotation_layers":[],
      "row_limit":50000,
      "series_limit":0,
      "order_desc":true,
      "url_params":{"dashboard_page_id":"5NCptbSXXst-z0R9ZBf4L","slice_id":"273"},
      "custom_params":{},
      "custom_form_data":{}
    }
  ],
  "form_data":{
    "datasource":"216__table",
    "viz_type":"bubble_v2",
    "slice_id":273,
    "url_params":{"dashboard_page_id":"5NCptbSXXst-z0R9ZBf4L","slice_id":"273"},
    "series":"FM",
    "entity":"ROUTE_NAME",
    "x":{
      "aggregate":null,
      "column":null,
      "datasourceWarning":false,
      "expressionType":"SQL",
      "hasCustomLabel":true,
      "label":"Ziyaret İç Hedef",
      "optionName":"metric_ab6mdk3a4ng_dgsu9m79ruf",
      "sqlExpression":"SUM(PLANANAN_MUSTERI_SAYISI)"
    },
    "y":{
      "aggregate":null,
      "column":null,
      "datasourceWarning":false,
      "expressionType":"SQL",
      "hasCustomLabel":true,
      "label":"Gerçekleşen Ziyaret",
      "optionName":"metric_8xew93462zr_buovqxd5f5m",
      "sqlExpression":"SUM(YAPILAN_ZIYARET_MUSTERI_SAYISI)"
    },
    "adhoc_filters":[
      {
        "clause":"WHERE",
        "comparator":null,
        "datasourceWarning":false,
        "expressionType":"SQL",
        "filterOptionName":"filter_1y8xc57gmqh_6eik70zf0gw",
        "isExtra":false,
        "isNew":false,
        "operator":"TEMPORAL_RANGE",
        "sqlExpression":"TARIH = today()",
        "subject":"TARIH"
      },
      {
        "clause":"WHERE",
        "comparator":null,
        "datasourceWarning":false,
        "expressionType":"SIMPLE",
        "filterOptionName":"filter_j4ffkiu9ndo_cj270cl7d6q",
        "isExtra":false,
        "isNew":false,
        "operator":"IS NOT NULL",
        "operatorId":"IS_NOT_NULL",
        "sqlExpression":null,
        "subject":"SM"
      }
    ],
    "size":{
      "aggregate":null,
      "column":null,
      "datasourceWarning":false,
      "expressionType":"SQL",
      "hasCustomLabel":true,
      "label":"Başarılı Ziyaret",
      "optionName":"metric_plr6q85g48q_9sjxo33ugn",
      "sqlExpression":"SUM(BASARILI_MUSTERI_SAYISI)/SUM(YAPILAN_ZIYARET_MUSTERI_SAYISI)*1.0"
    },
    "row_limit":50000,
    "color_scheme":"presetColors",
    "show_legend":true,
    "legendType":"scroll",
    "legendOrientation":"left",
    "legendMargin":150,
    "max_bubble_size":"20",
    "tooltipSizeFormat":".2%",
    "opacity":0.6,
    "x_axis_label":"Ziyaret İç Hedef",
    "x_axis_title_margin":30,
    "xAxisFormat":"SMART_NUMBER",
    "logXAxis":false,
    "y_axis_label":"Ziyaret Gerçekleşme",
    "y_axis_title_margin":30,
    "y_axis_format":"SMART_NUMBER",
    "logYAxis":false,
    "truncateXAxis":true,
    "y_axis_bounds":[null,null],
    "extra_form_data":{},
    "force":true,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=Tau-zQ6IExU&dashboard_page_id=5NCptbSXXst-z0R9ZBf4L&slice_id=273")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget29AggreationS25Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A785%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource": {"id": 216, "type": "table"},
  "force": true,
  "queries": [
    {
      "filters": [],
      "extras": {
        "having": "",
        "where": "( TARIH >= DATE_TRUNC('day', NOW()))"
      },
      "applied_time_extras": {},
      "columns": [],
      "metrics": [
        {
          "aggregate": null,
          "column": null,
          "datasourceWarning": false,
          "expressionType": "SQL",
          "hasCustomLabel": false,
          "label": "SUM(BASARILI_MUSTERI_SAYISI)*1.0/SUM(YAPILAN_ZIYARET_MUSTERI_SAYISI)",
          "optionName": "metric_jv4qytjnxvc_zatvndn061s",
          "sqlExpression": "SUM(BASARILI_MUSTERI_SAYISI)*1.0/SUM(YAPILAN_ZIYARET_MUSTERI_SAYISI)"
        }
      ],
      "annotation_layers": [],
      "series_limit": 0,
      "order_desc": true,
      "url_params": {
        "dashboard_page_id": "_RvsnBtgUopUggm5vkV7P",
        "slice_id": "785"
      },
      "custom_params": {},
      "custom_form_data": {}
    }
  ],
  "form_data": {
    "datasource": "216__table",
    "viz_type": "big_number_total",
    "slice_id": 785,
    "url_params": {
      "dashboard_page_id": "_RvsnBtgUopUggm5vkV7P",
      "slice_id": "785"
    },
    "metric": {
      "aggregate": null,
      "column": null,
      "datasourceWarning": false,
      "expressionType": "SQL",
      "hasCustomLabel": false,
      "label": "SUM(BASARILI_MUSTERI_SAYISI)*1.0/SUM(YAPILAN_ZIYARET_MUSTERI_SAYISI)",
      "optionName": "metric_jv4qytjnxvc_zatvndn061s",
      "sqlExpression": "SUM(BASARILI_MUSTERI_SAYISI)*1.0/SUM(YAPILAN_ZIYARET_MUSTERI_SAYISI)"
    },
    "adhoc_filters": [
      {
        "clause": "WHERE",
        "comparator": null,
        "datasourceWarning": false,
        "expressionType": "SQL",
        "filterOptionName": "filter_f2icu4o5ea_6rjov6xciz8",
        "isExtra": false,
        "isNew": false,
        "operator": "TEMPORAL_RANGE",
        "sqlExpression": " TARIH >= DATE_TRUNC('day', NOW())",
        "subject": "TARIH"
      }
    ],
    "header_font_size": 0.4,
    "subheader_font_size": 0.15,
    "y_axis_format": ".2%",
    "time_format": "smart_date",
    "conditional_formatting": [],
    "extra_form_data": {},
    "force": true,
    "result_format": "json",
    "result_type": "full"
  },
  "result_format": "json",
  "result_type": "full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=7HjmXcJrSLM&dashboard_page_id=_RvsnBtgUopUggm5vkV7P&slice_id=785")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget29AggreationS53Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A163%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource":{"id":302,"type":"table"},
  "force":true,
  "queries":[
    {
      "filters":[
        {"col":"Z_Status","op":"IN","val":["Başarılı Ziyaret","Başarılı Olmayan Ziyaret"]},
        {"col":"Tarih","op":"TEMPORAL_RANGE","val":"No filter"}
      ],
      "extras":{"having":"","where":""},
      "applied_time_extras":{},
      "columns":[
        {
          "datasourceWarning":false,
          "expressionType":"SQL",
          "label":"Z_Status",
          "sqlExpression":"CASE WHEN Z_Status = 'Başarılı Olmayan Ziyaret' Then 'Başarısız' WHEN Z_Status= 'Başarılı Ziyaret' Then 'Başarılı'END"
        }
      ],
      "metrics":[
        {
          "aggregate":"SUM",
          "column":{
            "advanced_data_type":null,
            "certification_details":null,
            "certified_by":null,
            "column_name":"Z_Number",
            "description":null,
            "expression":null,
            "filterable":true,
            "groupby":true,
            "id":6755,
            "is_certified":false,
            "is_dttm":false,
            "python_date_format":null,
            "type":"Nullable(Int64)",
            "type_generic":0,
            "verbose_name":null,
            "warning_markdown":null
          },
          "datasourceWarning":false,
          "expressionType":"SIMPLE",
          "hasCustomLabel":true,
          "label":"Ziyaret Sayısı",
          "optionName":"metric_391jewukb5s_9dmhfqpgw79",
          "sqlExpression":null
        }
      ],
      "orderby":[
        [
          {
            "aggregate":"SUM",
            "column":{
              "advanced_data_type":null,
              "certification_details":null,
              "certified_by":null,
              "column_name":"Z_Number",
              "description":null,
              "expression":null,
              "filterable":true,
              "groupby":true,
              "id":6755,
              "is_certified":false,
              "is_dttm":false,
              "python_date_format":null,
              "type":"Nullable(Int64)",
              "type_generic":0,
              "verbose_name":null,
              "warning_markdown":null
            },
            "datasourceWarning":false,
            "expressionType":"SIMPLE",
            "hasCustomLabel":true,
            "label":"Ziyaret Sayısı",
            "optionName":"metric_391jewukb5s_9dmhfqpgw79",
            "sqlExpression":null
          },
          false
        ]
      ],
      "annotation_layers":[],
      "row_limit":100,
      "series_limit":0,
      "order_desc":true,
      "url_params":{"dashboard_page_id":"3nyhN1NSOHcjPWUhEYLpm","slice_id":"163"},
      "custom_params":{},
      "custom_form_data":{}
    }
  ],
  "form_data":{
    "datasource":"302__table",
    "viz_type":"pie",
    "slice_id":163,
    "url_params":{"dashboard_page_id":"3nyhN1NSOHcjPWUhEYLpm","slice_id":"163"},
    "groupby":[
      {
        "datasourceWarning":false,
        "expressionType":"SQL",
        "label":"Z_Status",
        "sqlExpression":"CASE WHEN Z_Status = 'Başarılı Olmayan Ziyaret' Then 'Başarısız' WHEN Z_Status= 'Başarılı Ziyaret' Then 'Başarılı'END"
      }
    ],
    "metric":{
      "aggregate":"SUM",
      "column":{
        "advanced_data_type":null,
        "certification_details":null,
        "certified_by":null,
        "column_name":"Z_Number",
        "description":null,
        "expression":null,
        "filterable":true,
        "groupby":true,
        "id":6755,
        "is_certified":false,
        "is_dttm":false,
        "python_date_format":null,
        "type":"Nullable(Int64)",
        "type_generic":0,
        "verbose_name":null,
        "warning_markdown":null
      },
      "datasourceWarning":false,
      "expressionType":"SIMPLE",
      "hasCustomLabel":true,
      "label":"Ziyaret Sayısı",
      "optionName":"metric_391jewukb5s_9dmhfqpgw79",
      "sqlExpression":null
    },
    "adhoc_filters":[
      {
        "clause":"WHERE",
        "comparator":["Başarılı Ziyaret","Başarılı Olmayan Ziyaret"],
        "datasourceWarning":false,
        "expressionType":"SIMPLE",
        "filterOptionName":"filter_00wksw2qf5u5h_jvcpw4lwhab",
        "isExtra":false,
        "isNew":false,
        "operator":"IN",
        "operatorId":"IN",
        "sqlExpression":null,
        "subject":"Z_Status"
      },
      {
        "clause":"WHERE",
        "comparator":"No filter",
        "expressionType":"SIMPLE",
        "operator":"TEMPORAL_RANGE",
        "subject":"Tarih"
      }
    ],
    "row_limit":100,
    "sort_by_metric":true,
    "color_scheme":"bnbColors",
    "show_labels_threshold":5,
    "show_legend":true,
    "legendType":"scroll",
    "legendOrientation":"bottom",
    "legendMargin":null,
    "label_type":"percent",
    "number_format":"SMART_NUMBER",
    "date_format":"smart_date",
    "show_labels":true,
    "labels_outside":true,
    "label_line":true,
    "show_total":true,
    "outerRadius":60,
    "donut":true,
    "innerRadius":38,
    "extra_form_data":{},
    "force":true,
    "result_format":"json",
    "result_type":"full"
  },
  "result_format":"json",
  "result_type":"full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=rCCT-e87sJ4&dashboard_page_id=3nyhN1NSOHcjPWUhEYLpm&slice_id=163")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful())
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget83Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1949%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
            {
              "datasource": {
                "id": 346,
                "type": "table"
              },
              "force": false,
              "queries": [
                {
                  "filters": [],
                  "extras": {
                    "time_grain_sqla": "P1M",
                    "having": "",
                    "where": "(PayDate >= toStartOfMonth(today()) - INTERVAL 6 MONTHS AND PayDate < toStartOfMonth(today()) + INTERVAL 1 MONTHS)"
                  },
                  "applied_time_extras": {},
                  "columns": [
                    {
                      "timeGrain": "P1M",
                      "columnType": "BASE_AXIS",
                      "datasourceWarning": false,
                      "expressionType": "SQL",
                      "label": "PayDate",
                      "sqlExpression": "COALESCE(PayDate, toDate('1970-01-01'))"
                    }
                  ],
                  "metrics": [
                    {
                      "aggregate": "SUM",
                      "column": {
                        "advanced_data_type": null,
                        "certification_details": null,
                        "certified_by": null,
                        "column_name": "AylikOdemeTutari",
                        "description": null,
                        "expression": null,
                        "filterable": true,
                        "groupby": true,
                        "id": 7412,
                        "is_certified": false,
                        "is_dttm": false,
                        "python_date_format": null,
                        "type": "Decimal(38, 2)",
                        "type_generic": 0,
                        "verbose_name": null,
                        "warning_markdown": null
                      },
                      "datasourceWarning": false,
                      "expressionType": "SIMPLE",
                      "hasCustomLabel": true,
                      "label": "Ödeme Tutarı",
                      "optionName": "metric_r3y07js11j_2quwmjz2mwu",
                      "sqlExpression": null
                    }
                  ],
                  "annotation_layers": [],
                  "row_limit": 1000,
                  "series_columns": [],
                  "series_limit": 0,
                  "url_params": {
                    "form_data_key": "L55qFe4rBvc",
                    "slice_id": "1949"
                  },
                  "custom_params": {},
                  "custom_form_data": {},
                  "time_offsets": [],
                  "post_processing": [
                    {
                      "operation": "pivot",
                      "options": {
                        "index": [
                          "PayDate"
                        ],
                        "columns": [],
                        "aggregates": {
                          "Ödeme Tutarı": {
                            "operator": "mean"
                          }
                        },
                        "drop_missing_columns": true
                      }
                    },
                    {
                      "operation": "flatten"
                    }
                  ],
                  "orderby": [
                    [
                      {
                        "aggregate": "SUM",
                        "column": {
                          "advanced_data_type": null,
                          "certification_details": null,
                          "certified_by": null,
                          "column_name": "AylikOdemeTutari",
                          "description": null,
                          "expression": null,
                          "filterable": true,
                          "groupby": true,
                          "id": 7412,
                          "is_certified": false,
                          "is_dttm": false,
                          "python_date_format": null,
                          "type": "Decimal(38, 2)",
                          "type_generic": 0,
                          "verbose_name": null,
                          "warning_markdown": null
                        },
                        "datasourceWarning": false,
                        "expressionType": "SIMPLE",
                        "hasCustomLabel": true,
                        "label": "Ödeme Tutarı",
                        "optionName": "metric_r3y07js11j_2quwmjz2mwu",
                        "sqlExpression": null
                      },
                      false
                    ]
                  ]
                },
                {
                  "filters": [],
                  "extras": {
                    "time_grain_sqla": "P1M",
                    "having": "",
                    "where": "(PayDate >= toStartOfMonth(today()) - INTERVAL 6 MONTHS AND PayDate < toStartOfMonth(today()) + INTERVAL 1 MONTHS)"
                  },
                  "applied_time_extras": {},
                  "columns": [
                    {
                      "timeGrain": "P1M",
                      "columnType": "BASE_AXIS",
                      "datasourceWarning": false,
                      "expressionType": "SQL",
                      "label": "PayDate",
                      "sqlExpression": "COALESCE(PayDate, toDate('1970-01-01'))"
                    }
                  ],
                  "metrics": [
                    {
                      "aggregate": "AVG",
                      "column": {
                        "advanced_data_type": null,
                        "certification_details": null,
                        "certified_by": null,
                        "column_name": "OrtalamaGecikmeGunu",
                        "description": null,
                        "expression": null,
                        "filterable": true,
                        "groupby": true,
                        "id": 7414,
                        "is_certified": false,
                        "is_dttm": false,
                        "python_date_format": null,
                        "type": "Nullable(Float64)",
                        "type_generic": null,
                        "verbose_name": null,
                        "warning_markdown": null
                      },
                      "datasourceWarning": false,
                      "expressionType": "SIMPLE",
                      "hasCustomLabel": true,
                      "label": "Gecikme Gün",
                      "optionName": "metric_4mnitv8bl9_nva9y1anho",
                      "sqlExpression": null
                    }
                  ],
                  "annotation_layers": [],
                  "row_limit": 10000,
                  "series_columns": [],
                  "series_limit": 0,
                  "url_params": {
                    "form_data_key": "L55qFe4rBvc",
                    "slice_id": "1949"
                  },
                  "custom_params": {},
                  "custom_form_data": {},
                  "time_offsets": [],
                  "post_processing": [
                    {
                      "operation": "pivot",
                      "options": {
                        "index": [
                          "PayDate"
                        ],
                        "columns": [],
                        "aggregates": {
                          "Gecikme Gün": {
                            "operator": "mean"
                          }
                        },
                        "drop_missing_columns": true
                      }
                    },
                    {
                      "operation": "flatten"
                    }
                  ],
                  "orderby": [
                    [
                      {
                        "aggregate": "AVG",
                        "column": {
                          "advanced_data_type": null,
                          "certification_details": null,
                          "certified_by": null,
                          "column_name": "OrtalamaGecikmeGunu",
                          "description": null,
                          "expression": null,
                          "filterable": true,
                          "groupby": true,
                          "id": 7414,
                          "is_certified": false,
                          "is_dttm": false,
                          "python_date_format": null,
                          "type": "Nullable(Float64)",
                          "type_generic": null,
                          "verbose_name": null,
                          "warning_markdown": null
                        },
                        "datasourceWarning": false,
                        "expressionType": "SIMPLE",
                        "hasCustomLabel": true,
                        "label": "Gecikme Gün",
                        "optionName": "metric_4mnitv8bl9_nva9y1anho",
                        "sqlExpression": null
                      },
                      false
                    ]
                  ]
                }
              ],
              "form_data": {
                "datasource": "346__table",
                "viz_type": "mixed_timeseries",
                "slice_id": 1949,
                "url_params": {
                  "form_data_key": "L55qFe4rBvc",
                  "slice_id": "1949"
                },
                "x_axis": {
                  "datasourceWarning": false,
                  "expressionType": "SQL",
                  "label": "PayDate",
                  "sqlExpression": "COALESCE(PayDate, toDate('1970-01-01'))"
                },
                "time_grain_sqla": "P1M",
                "metrics": [
                  {
                    "aggregate": "SUM",
                    "column": {
                      "advanced_data_type": null,
                      "certification_details": null,
                      "certified_by": null,
                      "column_name": "AylikOdemeTutari",
                      "description": null,
                      "expression": null,
                      "filterable": true,
                      "groupby": true,
                      "id": 7412,
                      "is_certified": false,
                      "is_dttm": false,
                      "python_date_format": null,
                      "type": "Decimal(38, 2)",
                      "type_generic": 0,
                      "verbose_name": null,
                      "warning_markdown": null
                    },
                    "datasourceWarning": false,
                    "expressionType": "SIMPLE",
                    "hasCustomLabel": true,
                    "label": "Ödeme Tutarı",
                    "optionName": "metric_r3y07js11j_2quwmjz2mwu",
                    "sqlExpression": null
                  }
                ],
                "groupby": [],
                "adhoc_filters": [
                  {
                    "clause": "WHERE",
                    "comparator": null,
                    "datasourceWarning": false,
                    "expressionType": "SQL",
                    "filterOptionName": "filter_4kdxz17w2ml_v73ireqxyk",
                    "isExtra": false,
                    "isNew": false,
                    "operator": "TEMPORAL_RANGE",
                    "sqlExpression": "PayDate >= toStartOfMonth(today()) - INTERVAL 6 MONTHS AND PayDate < toStartOfMonth(today()) + INTERVAL 1 MONTHS",
                    "subject": "PayDate"
                  }
                ],
                "order_desc": true,
                "row_limit": 1000,
                "truncate_metric": true,
                "comparison_type": "values",
                "annotation_layers": [],
                "x_axis_title_margin": 15,
                "y_axis_title": "Ödeme Tutarı",
                "y_axis_title_margin": "40",
                "y_axis_title_position": "Left",
                "color_scheme": "supersetColors",
                "time_shift_color": true,
                "seriesType": "bar",
                "stack": false,
                "area": false,
                "show_value": true,
                "opacity": 0.2,
                "markerEnabled": true,
                "markerSize": 6,
                "yAxisIndex": 0,
                "seriesTypeB": "middle",
                "show_valueB": true,
                "opacityB": 0.2,
                "markerEnabledB": true,
                "markerSizeB": 10,
                "yAxisIndexB": 1,
                "minorTicks": false,
                "show_legend": true,
                "legendType": "scroll",
                "legendOrientation": "bottom",
                "x_axis_time_format": "smart_date",
                "xAxisLabelRotation": 45,
                "rich_tooltip": true,
                "showTooltipTotal": true,
                "tooltipTimeFormat": "smart_date",
                "truncateXAxis": true,
                "truncateYAxis": true,
                "y_axis_bounds": [
                  1,
                  null
                ],
                "y_axis_format": "SMART_NUMBER",
                "logAxis": true,
                "y_axis_bounds_secondary": [
                  1,
                  null
                ],
                "y_axis_format_secondary": "SMART_NUMBER",
                "currency_format_secondary": {},
                "yAxisTitleSecondary": "Gecikme Gün",
                "logAxisSecondary": false,
                "extra_form_data": {},
                "force": false,
                "result_format": "json",
                "result_type": "full"
              },
              "result_format": "json",
              "result_type": "full"
            }
            """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=L55qFe4rBvc&slice_id=1949")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                String err = resp.body() != null ? resp.body().string() : "";
                throw new IOException("Unexpected code " + resp.code() + " - " + err);
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    public static JSONObject sendWidget92AggreationS66Request() throws IOException {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1957%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie"); // config'den al

        String body = """
        {
          "datasource": {"id": 346, "type": "table"},
          "force": false,
          "queries": [
            {
              "filters": [
                {"col": "PayDate", "op": "TEMPORAL_RANGE", "val": "No filter"}
              ],
              "extras": {
                "having": "",
                "where": "(PayDate >= toStartOfMonth(today()) AND PayDate < toStartOfMonth(today()) + INTERVAL 1 MONTHS)"
              },
              "applied_time_extras": {},
              "columns": [],
              "metrics": [
                {
                  "aggregate": "AVG",
                  "column": {
                    "advanced_data_type": null,
                    "certification_details": null,
                    "certified_by": null,
                    "column_name": "OrtalamaGecikmeGunu",
                    "description": null,
                    "expression": null,
                    "filterable": true,
                    "groupby": true,
                    "id": 7414,
                    "is_certified": false,
                    "is_dttm": false,
                    "python_date_format": null,
                    "type": "Nullable(Float64)",
                    "type_generic": null,
                    "verbose_name": null,
                    "warning_markdown": null
                  },
                  "datasourceWarning": false,
                  "expressionType": "SIMPLE",
                  "hasCustomLabel": true,
                  "label": "Ort. Gecikme Gün",
                  "optionName": "metric_ogdokbt8jzd_fdie6e916up",
                  "sqlExpression": null
                }
              ],
              "annotation_layers": [],
              "series_limit": 0,
              "order_desc": true,
              "url_params": {
                "dashboard_page_id": "GulxjbioGz-6_xA0Jptak",
                "form_data_key": "xg2opFTudg4",
                "save_action": "overwrite",
                "slice_id": "1957"
              },
              "custom_params": {},
              "custom_form_data": {}
            }
          ],
          "form_data": {
            "datasource": "346__table",
            "viz_type": "big_number_total",
            "slice_id": 1957,
            "url_params": {
              "dashboard_page_id": "GulxjbioGz-6_xA0Jptak",
              "form_data_key": "xg2opFTudg4",
              "save_action": "overwrite",
              "slice_id": "1957"
            },
            "metric": {
              "aggregate": "AVG",
              "column": {
                "advanced_data_type": null,
                "certification_details": null,
                "certified_by": null,
                "column_name": "OrtalamaGecikmeGunu",
                "description": null,
                "expression": null,
                "filterable": true,
                "groupby": true,
                "id": 7414,
                "is_certified": false,
                "is_dttm": false,
                "python_date_format": null,
                "type": "Nullable(Float64)",
                "type_generic": null,
                "verbose_name": null,
                "warning_markdown": null
              },
              "datasourceWarning": false,
              "expressionType": "SIMPLE",
              "hasCustomLabel": true,
              "label": "Ort. Gecikme Gün",
              "optionName": "metric_ogdokbt8jzd_fdie6e916up",
              "sqlExpression": null
            },
            "adhoc_filters": [
              {
                "expressionType": "SQL",
                "sqlExpression": "PayDate >= toStartOfMonth(today()) AND PayDate < toStartOfMonth(today()) + INTERVAL 1 MONTHS",
                "clause": "WHERE",
                "subject": null,
                "operator": null,
                "comparator": null,
                "isExtra": false,
                "isNew": false,
                "datasourceWarning": false,
                "filterOptionName": "filter_p3c7tjwlrg_degth8mpmxa"
              },
              {
                "expressionType": "SIMPLE",
                "subject": "PayDate",
                "operator": "TEMPORAL_RANGE",
                "comparator": "No filter",
                "clause": "WHERE",
                "sqlExpression": null,
                "isExtra": false,
                "isNew": false,
                "datasourceWarning": false,
                "filterOptionName": "filter_k4dcbph7nu_7itggvr7dri"
              }
            ],
            "subheader": "Ort. Gecikme Gün",
            "header_font_size": 0.4,
            "subheader_font_size": 0.15,
            "y_axis_format": "SMART_NUMBER",
            "time_format": "smart_date",
            "conditional_formatting": [],
            "extra_form_data": {},
            "force": false,
            "result_format": "json",
            "result_type": "full"
          },
          "result_format": "json",
          "result_type": "full"
        }
        """;

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected HTTP " + response.code() + " - " + response.message());
            }
            String respBody = response.body() != null ? response.body().string() : "{}";
            return new JSONObject(respBody);
        }
    }



    public static JSONObject sendWidget93Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1958%7D";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
            {
              "datasource": {
                "id": 346,
                "type": "table"
              },
              "force": false,
              "queries": [
                {
                  "filters": [
                    {
                      "col": "PayDate",
                      "op": "TEMPORAL_RANGE",
                      "val": "No filter"
                    }
                  ],
                  "extras": {
                    "having": "",
                    "where": "(PayDate >= toStartOfMonth(today()) - INTERVAL 6 MONTHS AND PayDate < toStartOfMonth(today()) + INTERVAL 1 MONTHS)"
                  },
                  "applied_time_extras": {},
                  "columns": [],
                  "metrics": [
                    {
                      "aggregate": null,
                      "column": null,
                      "datasourceWarning": false,
                      "expressionType": "SQL",
                      "hasCustomLabel": true,
                      "label": "6 Aylık Ödeme Tutarı",
                      "optionName": "metric_c123aomdklf_1qzkwhbmfkq",
                      "sqlExpression": "SUM(AylikOdemeTutari)"
                    }
                  ],
                  "annotation_layers": [],
                  "series_limit": 0,
                  "order_desc": true,
                  "url_params": {
                    "dashboard_page_id": "GulxjbioGz-6_xA0Jptak",
                    "form_data_key": "Gs2uJC95KIY",
                    "slice_id": "1958"
                  },
                  "custom_params": {},
                  "custom_form_data": {}
                }
              ],
              "form_data": {
                "datasource": "346__table",
                "viz_type": "big_number_total",
                "slice_id": 1958,
                "url_params": {
                  "dashboard_page_id": "GulxjbioGz-6_xA0Jptak",
                  "form_data_key": "Gs2uJC95KIY",
                  "slice_id": "1958"
                },
                "metric": {
                  "aggregate": null,
                  "column": null,
                  "datasourceWarning": false,
                  "expressionType": "SQL",
                  "hasCustomLabel": true,
                  "label": "6 Aylık Ödeme Tutarı",
                  "optionName": "metric_c123aomdklf_1qzkwhbmfkq",
                  "sqlExpression": "SUM(AylikOdemeTutari)"
                },
                "adhoc_filters": [
                  {
                    "expressionType": "SQL",
                    "sqlExpression": "PayDate >= toStartOfMonth(today()) - INTERVAL 6 MONTHS AND PayDate < toStartOfMonth(today()) + INTERVAL 1 MONTHS",
                    "clause": "WHERE",
                    "subject": null,
                    "operator": null,
                    "comparator": null,
                    "isExtra": false,
                    "isNew": false,
                    "datasourceWarning": false,
                    "filterOptionName": "filter_p3c7tjwlrg_degth8mpmxa"
                  },
                  {
                    "expressionType": "SIMPLE",
                    "subject": "PayDate",
                    "operator": "TEMPORAL_RANGE",
                    "comparator": "No filter",
                    "clause": "WHERE",
                    "sqlExpression": null,
                    "isExtra": false,
                    "isNew": false,
                    "datasourceWarning": false,
                    "filterOptionName": "filter_krtcqzti8z_wopittlwmyj"
                  }
                ],
                "subheader": "6 Aylık Ödeme Tutarı",
                "header_font_size": 0.4,
                "subheader_font_size": 0.15,
                "y_axis_format": "SMART_NUMBER",
                "time_format": "smart_date",
                "conditional_formatting": [],
                "extra_form_data": {},
                "force": false,
                "result_format": "json",
                "result_type": "full"
              },
              "result_format": "json",
              "result_type": "full"
            }
            """;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=Gs2uJC95KIY&dashboard_page_id=GulxjbioGz-6_xA0Jptak&slice_id=1958")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                String err = resp.body() != null ? resp.body().string() : "";
                throw new IOException("Unexpected code " + resp.code() + " - " + err);
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    // --- WIDGET 88: Request (slice_id=1954, Big Number) ---
    public static JSONObject sendWidget88Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1954%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body =
                "{\"datasource\":{\"id\":346,\"type\":\"table\"},\"force\":true," +
                        "\"queries\":[{\"filters\":[{\"col\":\"PayDate\",\"op\":\"TEMPORAL_RANGE\",\"val\":\"No filter\"}]," +
                        "\"extras\":{\"time_grain_sqla\":\"P1M\",\"having\":\"\",\"where\":\"(PayDate >= toStartOfMonth(today()) - INTERVAL 6 MONTHS)\"}," +
                        "\"applied_time_extras\":{},\"columns\":[{\"timeGrain\":\"P1M\",\"columnType\":\"BASE_AXIS\",\"sqlExpression\":\"PayDate\",\"label\":\"PayDate\",\"expressionType\":\"SQL\"}]," +
                        "\"metrics\":[{\"aggregate\":null,\"column\":null,\"datasourceWarning\":false,\"expressionType\":\"SQL\",\"hasCustomLabel\":true,\"label\":\"Zamanında Ödeme %\",\"optionName\":\"metric_hv28lsyn4a_os39ow4yiz\",\"sqlExpression\":\"AVG(ZamanindaOdemeOrani)\"}]," +
                        "\"annotation_layers\":[],\"series_limit\":0,\"order_desc\":true," +
                        "\"url_params\":{\"dashboard_page_id\":\"GulxjbioGz-6_xA0Jptak\",\"slice_id\":\"1954\"}," +
                        "\"custom_params\":{},\"custom_form_data\":{}," +
                        "\"post_processing\":[{\"operation\":\"pivot\",\"options\":{\"index\":[\"PayDate\"],\"columns\":[],\"aggregates\":{\"Zamanında Ödeme %\":{\"operator\":\"mean\"}},\"drop_missing_columns\":true}},{\"operation\":\"flatten\"}]}]," +
                        "\"form_data\":{\"datasource\":\"346__table\",\"viz_type\":\"big_number\",\"slice_id\":1954," +
                        "\"url_params\":{\"dashboard_page_id\":\"GulxjbioGz-6_xA0Jptak\",\"slice_id\":\"1954\"}," +
                        "\"x_axis\":\"PayDate\",\"time_grain_sqla\":\"P1M\"," +
                        "\"metric\":{\"aggregate\":null,\"column\":null,\"datasourceWarning\":false,\"expressionType\":\"SQL\",\"hasCustomLabel\":true,\"label\":\"Zamanında Ödeme %\",\"optionName\":\"metric_hv28lsyn4a_os39ow4yiz\",\"sqlExpression\":\"AVG(ZamanindaOdemeOrani)\"}," +
                        "\"adhoc_filters\":[{\"clause\":\"WHERE\",\"comparator\":null,\"datasourceWarning\":false,\"expressionType\":\"SQL\",\"filterOptionName\":\"filter_rzkkc8691c8_aytv7lqaw3\",\"isExtra\":false,\"isNew\":false,\"operator\":null,\"sqlExpression\":\"PayDate >= toStartOfMonth(today()) - INTERVAL 6 MONTHS\",\"subject\":null},{\"clause\":\"WHERE\",\"comparator\":\"No filter\",\"datasourceWarning\":false,\"expressionType\":\"SIMPLE\",\"filterOptionName\":\"filter_i8wigpoe8n_4fcaweracu9\",\"isExtra\":false,\"isNew\":false,\"operator\":\"TEMPORAL_RANGE\",\"sqlExpression\":null,\"subject\":\"PayDate\"}]," +
                        "\"compare_lag\":1,\"compare_suffix\":\"Zamanında Ödeme % MoM\",\"show_timestamp\":true,\"show_trend_line\":true," +
                        "\"start_y_axis_at_zero\":true,\"header_font_size\":0.4,\"subheader_font_size\":0.15," +
                        "\"y_axis_format\":\"SMART_NUMBER\",\"time_format\":\"smart_date\",\"rolling_type\":\"None\"," +
                        "\"extra_form_data\":{},\"force\":true,\"result_format\":\"json\",\"result_type\":\"full\"}," +
                        "\"result_format\":\"json\",\"result_type\":\"full\"}";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=x9rg4nFktwE&dashboard_page_id=GulxjbioGz-6_xA0Jptak&slice_id=1954")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                String err = resp.body() != null ? resp.body().string() : "";
                throw new IOException("Unexpected code " + resp.code() + " - " + err);
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget63Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1179%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource": { "id": 64, "type": "table" },
  "force": true,
  "queries": [
    {
      "filters": [
        { "col": "TRHISLEMTARIHI", "op": "TEMPORAL_RANGE", "val": "No filter" },
        { "col": "BIRSISEKARLILIK", "op": "IS NOT NULL" }
      ],
      "extras": {
        "time_grain_sqla": "P1M",
        "having": "",
        "where": "(TXTTEKILKOD = {% if url_param('lngmusterikod') %}\\r\\n '{{ url_param('lngmusterikod') }}'\\r\\n{% else %}\\r\\n '1336024'\\r\\n{% endif %}\\r\\n) AND (TRHISLEMTARIHI >= toStartOfMonth(now() - INTERVAL 12 - 1 MONTH))"
      },
      "applied_time_extras": {},
      "columns": [
        {
          "timeGrain": "P1M",
          "columnType": "BASE_AXIS",
          "sqlExpression": "TRHISLEMTARIHI",
          "label": "TRHISLEMTARIHI",
          "expressionType": "SQL"
        },
        "URUN_KALITE_SEGMENT_ACIKLAMA"
      ],
      "metrics": [
        {
          "aggregate": "SUM",
          "column": {
            "advanced_data_type": null,
            "certification_details": null,
            "certified_by": null,
            "column_name": "TOTALKARLILIK",
            "description": null,
            "expression": null,
            "filterable": true,
            "groupby": true,
            "id": 1951,
            "is_certified": false,
            "is_dttm": false,
            "python_date_format": null,
            "type": "NULLABLE(DECIMAL(38, 6))",
            "type_generic": 0,
            "verbose_name": null,
            "warning_markdown": null
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Toplam Kâr",
          "optionName": "metric_65iydsuvegm_3acupoyy58x",
          "sqlExpression": null
        }
      ],
      "orderby": [
        [
          {
            "aggregate": "SUM",
            "column": {
              "advanced_data_type": null,
              "certification_details": null,
              "certified_by": null,
              "column_name": "TOTALKARLILIK",
              "description": null,
              "expression": null,
              "filterable": true,
              "groupby": true,
              "id": 1951,
              "is_certified": false,
              "is_dttm": false,
              "python_date_format": null,
              "type": "NULLABLE(DECIMAL(38, 6))",
              "type_generic": 0,
              "verbose_name": null,
              "warning_markdown": null
            },
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "hasCustomLabel": true,
            "label": "Toplam Kâr",
            "optionName": "metric_65iydsuvegm_3acupoyy58x",
            "sqlExpression": null
          },
          false
        ]
      ],
      "annotation_layers": [],
      "row_limit": 10000,
      "series_columns": ["URUN_KALITE_SEGMENT_ACIKLAMA"],
      "series_limit": 0,
      "order_desc": true,
      "url_params": { "dashboard_page_id": "ygFygJ7jQ1vsYlADifTIU", "slice_id": "1179" },
      "custom_params": {},
      "custom_form_data": {},
      "time_offsets": [],
      "post_processing": [
        {
          "operation": "pivot",
          "options": {
            "index": ["TRHISLEMTARIHI"],
            "columns": ["URUN_KALITE_SEGMENT_ACIKLAMA"],
            "aggregates": { "Toplam Kâr": { "operator": "mean" } },
            "drop_missing_columns": false
          }
        },
        { "operation": "rename", "options": { "columns": { "Toplam Kâr": null }, "level": 0, "inplace": true } },
        { "operation": "flatten" }
      ]
    }
  ],
  "form_data": {
    "datasource": "64__table",
    "viz_type": "echarts_timeseries_smooth",
    "slice_id": 1179,
    "url_params": { "dashboard_page_id": "ygFygJ7jQ1vsYlADifTIU", "slice_id": "1179" },
    "x_axis": "TRHISLEMTARIHI",
    "time_grain_sqla": "P1M",
    "metrics": [
      {
        "aggregate": "SUM",
        "column": {
          "advanced_data_type": null,
          "certification_details": null,
          "certified_by": null,
          "column_name": "TOTALKARLILIK",
          "description": null,
          "expression": null,
          "filterable": true,
          "groupby": true,
          "id": 1951,
          "is_certified": false,
          "is_dttm": false,
          "python_date_format": null,
          "type": "NULLABLE(DECIMAL(38, 6))",
          "type_generic": 0,
          "verbose_name": null,
          "warning_markdown": null
        },
        "datasourceWarning": false,
        "expressionType": "SIMPLE",
        "hasCustomLabel": true,
        "label": "Toplam Kâr",
        "optionName": "metric_65iydsuvegm_3acupoyy58x",
        "sqlExpression": null
      }
    ],
    "groupby": ["URUN_KALITE_SEGMENT_ACIKLAMA"],
    "adhoc_filters": [
      {
        "clause": "WHERE",
        "comparator": "No filter",
        "datasourceWarning": false,
        "expressionType": "SIMPLE",
        "filterOptionName": "filter_mck7x1z7c_hv76n2mf9zp",
        "isExtra": false,
        "isNew": false,
        "operator": "TEMPORAL_RANGE",
        "sqlExpression": null,
        "subject": "TRHISLEMTARIHI"
      },
      {
        "clause": "WHERE",
        "comparator": null,
        "datasourceWarning": false,
        "expressionType": "SIMPLE",
        "filterOptionName": "filter_b0l70becyle_5unzy6ubddr",
        "isExtra": false,
        "isNew": false,
        "operator": "IS NOT NULL",
        "operatorId": "IS_NOT_NULL",
        "sqlExpression": null,
        "subject": "BIRSISEKARLILIK"
      },
      {
        "clause": "WHERE",
        "comparator": null,
        "datasourceWarning": false,
        "expressionType": "SQL",
        "filterOptionName": "filter_l1t2g3xt01_npmt5yyuwq",
        "isExtra": false,
        "isNew": false,
        "operator": null,
        "sqlExpression": "TXTTEKILKOD = {% if url_param('lngmusterikod') %}\\r\\n '{{ url_param('lngmusterikod') }}'\\r\\n{% else %}\\r\\n '1336024'\\r\\n{% endif %}\\r\\n",
        "subject": null
      },
      {
        "clause": "WHERE",
        "comparator": null,
        "datasourceWarning": false,
        "expressionType": "SQL",
        "filterOptionName": "filter_54gg60yx9m_xnxl4nevose",
        "isExtra": false,
        "isNew": false,
        "operator": null,
        "sqlExpression": "TRHISLEMTARIHI >= toStartOfMonth(now() - INTERVAL 12 - 1 MONTH)",
        "subject": null
      }
    ],
    "row_limit": 10000,
    "truncate_metric": true,
    "show_empty_columns": true,
    "comparison_type": "values",
    "annotation_layers": [],
    "forecastPeriods": 10,
    "forecastInterval": 0.8,
    "x_axis_title_margin": 15,
    "y_axis_title": "Kâr (₺)",
    "y_axis_title_margin": 50,
    "y_axis_title_position": "Left",
    "sort_series_type": "sum",
    "color_scheme": "supersetColors",
    "time_shift_color": true,
    "only_total": true,
    "markerEnabled": true,
    "markerSize": 6,
    "minorTicks": true,
    "show_legend": true,
    "legendType": "plain",
    "legendOrientation": "left",
    "legendMargin": 75,
    "x_axis_time_format": "%d/%m/%Y",
    "xAxisLabelRotation": 45,
    "rich_tooltip": true,
    "showTooltipTotal": true,
    "tooltipTimeFormat": "%d/%m/%Y",
    "y_axis_format": "SMART_NUMBER",
    "logAxis": false,
    "truncateXAxis": true,
    "y_axis_bounds": [null, null],
    "extra_form_data": {},
    "force": true,
    "result_format": "json",
    "result_type": "full"
  },
  "result_format": "json",
  "result_type": "full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=fR_rNZnw35w&dashboard_page_id=ygFygJ7jQ1vsYlADifTIU&slice_id=1179")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget64Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1184%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource": { "id": 64, "type": "table" },
  "force": true,
  "queries": [
    {
      "filters": [
        { "col": "TRHISLEMTARIHI", "op": "TEMPORAL_RANGE", "val": "No filter" },
        { "col": "BIRSISEKARLILIK", "op": "IS NOT NULL" }
      ],
      "extras": {
        "time_grain_sqla": "P1M",
        "having": "",
        "where": "(TXTTEKILKOD = {% if url_param('lngmusterikod') %}\\r\\n '{{ url_param('lngmusterikod') }}'\\r\\n{% else %}\\r\\n '1336024'\\r\\n{% endif %}\\r\\n) AND (TRHISLEMTARIHI >= toStartOfMonth(now() - INTERVAL 12 - 1 MONTH))"
      },
      "applied_time_extras": {},
      "columns": [
        {
          "timeGrain": "P1M",
          "columnType": "BASE_AXIS",
          "sqlExpression": "TRHISLEMTARIHI",
          "label": "TRHISLEMTARIHI",
          "expressionType": "SQL"
        },
        "URUN_KATEGORU_ACIKLAMA"
      ],
      "metrics": [
        {
          "aggregate": "SUM",
          "column": {
            "advanced_data_type": null,
            "certification_details": null,
            "certified_by": null,
            "column_name": "TOTALKARLILIK",
            "description": null,
            "expression": null,
            "filterable": true,
            "groupby": true,
            "id": 1951,
            "is_certified": false,
            "is_dttm": false,
            "python_date_format": null,
            "type": "NULLABLE(DECIMAL(38, 6))",
            "type_generic": 0,
            "verbose_name": null,
            "warning_markdown": null
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Toplam Kâr",
          "optionName": "metric_65iydsuvegm_3acupoyy58x",
          "sqlExpression": null
        }
      ],
      "orderby": [
        [
          {
            "aggregate": "SUM",
            "column": {
              "advanced_data_type": null,
              "certification_details": null,
              "certified_by": null,
              "column_name": "TOTALKARLILIK",
              "description": null,
              "expression": null,
              "filterable": true,
              "groupby": true,
              "id": 1951,
              "is_certified": false,
              "is_dttm": false,
              "python_date_format": null,
              "type": "NULLABLE(DECIMAL(38, 6))",
              "type_generic": 0,
              "verbose_name": null,
              "warning_markdown": null
            },
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "hasCustomLabel": true,
            "label": "Toplam Kâr",
            "optionName": "metric_65iydsuvegm_3acupoyy58x",
            "sqlExpression": null
          },
          false
        ]
      ],
      "annotation_layers": [],
      "row_limit": 10000,
      "series_columns": ["URUN_KATEGORU_ACIKLAMA"],
      "series_limit": 0,
      "order_desc": true,
      "url_params": { "dashboard_page_id": "ygFygJ7jQ1vsYlADifTIU", "slice_id": "1184" },
      "custom_params": {},
      "custom_form_data": {},
      "time_offsets": [],
      "post_processing": [
        {
          "operation": "pivot",
          "options": {
            "index": ["TRHISLEMTARIHI"],
            "columns": ["URUN_KATEGORU_ACIKLAMA"],
            "aggregates": { "Toplam Kâr": { "operator": "mean" } },
            "drop_missing_columns": false
          }
        },
        { "operation": "rename", "options": { "columns": { "Toplam Kâr": null }, "level": 0, "inplace": true } },
        { "operation": "flatten" }
      ]
    }
  ],
  "form_data": {
    "datasource": "64__table",
    "viz_type": "echarts_timeseries_smooth",
    "slice_id": 1184,
    "url_params": { "dashboard_page_id": "ygFygJ7jQ1vsYlADifTIU", "slice_id": "1184" },
    "x_axis": "TRHISLEMTARIHI",
    "time_grain_sqla": "P1M",
    "metrics": [
      {
        "aggregate": "SUM",
        "column": {
          "advanced_data_type": null,
          "certification_details": null,
          "certified_by": null,
          "column_name": "TOTALKARLILIK",
          "description": null,
          "expression": null,
          "filterable": true,
          "groupby": true,
          "id": 1951,
          "is_certified": false,
          "is_dttm": false,
          "python_date_format": null,
          "type": "NULLABLE(DECIMAL(38, 6))",
          "type_generic": 0,
          "verbose_name": null,
          "warning_markdown": null
        },
        "datasourceWarning": false,
        "expressionType": "SIMPLE",
        "hasCustomLabel": true,
        "label": "Toplam Kâr",
        "optionName": "metric_65iydsuvegm_3acupoyy58x",
        "sqlExpression": null
      }
    ],
    "groupby": ["URUN_KATEGORU_ACIKLAMA"],
    "adhoc_filters": [
      {
        "clause": "WHERE",
        "comparator": "No filter",
        "datasourceWarning": false,
        "expressionType": "SIMPLE",
        "filterOptionName": "filter_mck7x1z7c_hv76n2mf9zp",
        "isExtra": false,
        "isNew": false,
        "operator": "TEMPORAL_RANGE",
        "sqlExpression": null,
        "subject": "TRHISLEMTARIHI"
      },
      {
        "clause": "WHERE",
        "comparator": null,
        "datasourceWarning": false,
        "expressionType": "SIMPLE",
        "filterOptionName": "filter_b0l70becyle_5unzy6ubddr",
        "isExtra": false,
        "isNew": false,
        "operator": "IS NOT NULL",
        "operatorId": "IS_NOT_NULL",
        "sqlExpression": null,
        "subject": "BIRSISEKARLILIK"
      },
      {
        "clause": "WHERE",
        "comparator": null,
        "datasourceWarning": false,
        "expressionType": "SQL",
        "filterOptionName": "filter_7tzhap79yk_y4mb41f63ak",
        "isExtra": false,
        "isNew": false,
        "operator": null,
        "sqlExpression": "TXTTEKILKOD = {% if url_param('lngmusterikod') %}\\r\\n '{{ url_param('lngmusterikod') }}'\\r\\n{% else %}\\r\\n '1336024'\\r\\n{% endif %}\\r\\n",
        "subject": null
      },
      {
        "clause": "WHERE",
        "comparator": null,
        "datasourceWarning": false,
        "expressionType": "SQL",
        "filterOptionName": "filter_3n65mvrlx2r_jf8cwmfhxe9",
        "isExtra": false,
        "isNew": false,
        "operator": null,
        "sqlExpression": "TRHISLEMTARIHI >= toStartOfMonth(now() - INTERVAL 12 - 1 MONTH)",
        "subject": null
      }
    ],
    "row_limit": 10000,
    "truncate_metric": true,
    "show_empty_columns": true,
    "comparison_type": "values",
    "annotation_layers": [],
    "forecastPeriods": 10,
    "forecastInterval": 0.8,
    "x_axis_title_margin": 15,
    "y_axis_title": "Kâr (₺)",
    "y_axis_title_margin": 50,
    "y_axis_title_position": "Left",
    "sort_series_type": "sum",
    "color_scheme": "supersetColors",
    "time_shift_color": true,
    "only_total": true,
    "markerEnabled": true,
    "markerSize": 6,
    "minorTicks": true,
    "show_legend": true,
    "legendType": "scroll",
    "legendOrientation": "bottom",
    "x_axis_time_format": "%d/%m/%Y",
    "xAxisLabelRotation": 45,
    "rich_tooltip": true,
    "showTooltipTotal": true,
    "tooltipTimeFormat": "%d/%m/%Y",
    "y_axis_format": "SMART_NUMBER",
    "logAxis": false,
    "truncateXAxis": true,
    "y_axis_bounds": [null, null],
    "extra_form_data": {},
    "force": true,
    "result_format": "json",
    "result_type": "full"
  },
  "result_format": "json",
  "result_type": "full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=2rd5qis5IK4&dashboard_page_id=ygFygJ7jQ1vsYlADifTIU&slice_id=1184")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sendWidget65Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1174%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource": { "id": 64, "type": "table" },
  "force": true,
  "queries": [
    {
      "filters": [
        { "col": "MUSTERI_LISANS_TARIH", "op": "TEMPORAL_RANGE", "val": "No filter" }
      ],
      "extras": {
        "time_grain_sqla": "P1M",
        "having": "",
        "where": "(TXTTEKILKOD = {% if url_param('lngmusterikod') %}\\r\\n '{{ url_param('lngmusterikod') }}'\\r\\n{% else %}\\r\\n '1336024'\\r\\n{% endif %}\\r\\n) AND (TRHISLEMTARIHI >= toStartOfMonth(now() - INTERVAL 12 - 1 MONTH))"
      },
      "applied_time_extras": {},
      "columns": [
        { "expressionType": "SQL", "label": "Fatura Tarihi", "sqlExpression": "formatDateTime(toDateTime(TRHISLEMTARIHI), '%Y/%m')\\r\\n" },
        { "datasourceWarning": false, "expressionType": "SQL", "label": "Ürün Kategorisi", "sqlExpression": "URUN_KATEGORU_ACIKLAMA" },
        { "datasourceWarning": false, "expressionType": "SQL", "label": "Kalite Segmenti", "sqlExpression": "URUN_KALITE_SEGMENT_ACIKLAMA" }
      ],
      "metrics": [
        {
          "aggregate": "SUM",
          "column": {
            "advanced_data_type": null,
            "certification_details": null,
            "certified_by": null,
            "column_name": "TOTALKARLILIK",
            "description": null,
            "expression": null,
            "filterable": true,
            "groupby": true,
            "id": 1951,
            "is_certified": false,
            "is_dttm": false,
            "python_date_format": null,
            "type": "NULLABLE(DECIMAL(38, 6))",
            "type_generic": 0,
            "verbose_name": null,
            "warning_markdown": null
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Toplam Kâr (₺)",
          "optionName": "metric_u2kufgot67_xlc9s0qfib9",
          "sqlExpression": null
        }
      ],
      "orderby": [
        [
          {
            "aggregate": "SUM",
            "column": {
              "advanced_data_type": null,
              "certification_details": null,
              "certified_by": null,
              "column_name": "TOTALKARLILIK",
              "description": null,
              "expression": null,
              "filterable": true,
              "groupby": true,
              "id": 1951,
              "is_certified": false,
              "is_dttm": false,
              "python_date_format": null,
              "type": "NULLABLE(DECIMAL(38, 6))",
              "type_generic": 0,
              "verbose_name": null,
              "warning_markdown": null
            },
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "hasCustomLabel": true,
            "label": "Toplam Kâr (₺)",
            "optionName": "metric_u2kufgot67_xlc9s0qfib9",
            "sqlExpression": null
          },
          false
        ]
      ],
      "annotation_layers": [],
      "row_limit": 10000,
      "series_limit": 0,
      "order_desc": true,
      "url_params": { "dashboard_page_id": "ygFygJ7jQ1vsYlADifTIU", "slice_id": "1174" },
      "custom_params": {},
      "custom_form_data": {}
    }
  ],
  "form_data": {
    "datasource": "64__table",
    "viz_type": "pivot_table_v2",
    "slice_id": 1174,
    "url_params": { "dashboard_page_id": "ygFygJ7jQ1vsYlADifTIU", "slice_id": "1174" },
    "groupbyColumns": [
      { "expressionType": "SQL", "label": "Fatura Tarihi", "sqlExpression": "formatDateTime(toDateTime(TRHISLEMTARIHI), '%Y/%m')\\r\\n" }
    ],
    "groupbyRows": [
      { "datasourceWarning": false, "expressionType": "SQL", "label": "Ürün Kategorisi", "sqlExpression": "URUN_KATEGORU_ACIKLAMA" },
      { "datasourceWarning": false, "expressionType": "SQL", "label": "Kalite Segmenti", "sqlExpression": "URUN_KALITE_SEGMENT_ACIKLAMA" }
    ],
    "time_grain_sqla": "P1M",
    "temporal_columns_lookup": {
      "TRHBASLANGICTARIH": true,
      "TRHVADETARIHI": true,
      "MUSTERI_LISANS_TARIH": true,
      "TRHILKISLEMTARIHI": true,
      "TRHSONISLEMTARIHI": true,
      "CREATEDAT": true,
      "UPDATEDAT": true,
      "TRHISLEMTARIHI": true
    },
    "metrics": [
      {
        "aggregate": "SUM",
        "column": {
          "advanced_data_type": null,
          "certification_details": null,
          "certified_by": null,
          "column_name": "TOTALKARLILIK",
          "description": null,
          "expression": null,
          "filterable": true,
          "groupby": true,
          "id": 1951,
          "is_certified": false,
          "is_dttm": false,
          "python_date_format": null,
          "type": "NULLABLE(DECIMAL(38, 6))",
          "type_generic": 0,
          "verbose_name": null,
          "warning_markdown": null
        },
        "datasourceWarning": false,
        "expressionType": "SIMPLE",
        "hasCustomLabel": true,
        "label": "Toplam Kâr (₺)",
        "optionName": "metric_u2kufgot67_xlc9s0qfib9",
        "sqlExpression": null
      }
    ],
    "metricsLayout": "COLUMNS",
    "adhoc_filters": [
      {
        "clause": "WHERE",
        "comparator": "No filter",
        "datasourceWarning": false,
        "expressionType": "SIMPLE",
        "filterOptionName": "filter_3ing857qpm5_2odlx3bxi62",
        "isExtra": false,
        "isNew": false,
        "operator": "TEMPORAL_RANGE",
        "sqlExpression": null,
        "subject": "MUSTERI_LISANS_TARIH"
      },
      {
        "clause": "WHERE",
        "comparator": null,
        "datasourceWarning": false,
        "expressionType": "SQL",
        "filterOptionName": "filter_93e07f6c3g_gixlcwvuha6",
        "isExtra": false,
        "isNew": false,
        "operator": null,
        "sqlExpression": "TXTTEKILKOD = {% if url_param('lngmusterikod') %}\\r\\n '{{ url_param('lngmusterikod') }}'\\r\\n{% else %}\\r\\n '1336024'\\r\\n{% endif %}\\r\\n",
        "subject": null
      },
      {
        "clause": "WHERE",
        "comparator": null,
        "datasourceWarning": false,
        "expressionType": "SQL",
        "filterOptionName": "filter_l6x8c6gmyo_tq7v77ets7l",
        "isExtra": false,
        "isNew": false,
        "operator": null,
        "sqlExpression": "TRHISLEMTARIHI >= toStartOfMonth(now() - INTERVAL 12 - 1 MONTH)",
        "subject": null
      }
    ],
    "row_limit": 10000,
    "order_desc": true,
    "aggregateFunction": "Sum",
    "rowTotals": true,
    "rowSubTotals": true,
    "colTotals": true,
    "colSubTotals": false,
    "valueFormat": ",.2f",
    "date_format": "smart_date",
    "rowOrder": "key_a_to_z",
    "colOrder": "key_a_to_z",
    "rowSubtotalPosition": true,
    "colSubtotalPosition": false,
    "conditional_formatting": [],
    "allow_render_html": true,
    "extra_form_data": {},
    "force": true,
    "result_format": "json",
    "result_type": "full"
  },
  "result_format": "json",
  "result_type": "full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=Ccv98v8D4pM&dashboard_page_id=ygFygJ7jQ1vsYlADifTIU&slice_id=1174")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget82Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1947%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource": { "id": 344, "type": "table" },
  "force": true,
  "queries": [
    {
      "filters": [],
      "extras": { "having": "", "where": "" },
      "applied_time_extras": {},
      "columns": [
        {
          "columnType": "BASE_AXIS",
          "datasourceWarning": false,
          "expressionType": "SQL",
          "label": "Yaş Grubu",
          "sqlExpression": "YasGrubu"
        }
      ],
      "metrics": [
        {
          "aggregate": "SUM",
          "column": {
            "advanced_data_type": null,
            "changed_on": "2025-08-04T09:58:59.261495",
            "column_name": "Tutar",
            "created_on": "2025-08-04T09:58:59.261494",
            "description": null,
            "expression": null,
            "extra": null,
            "filterable": true,
            "groupby": true,
            "id": 7400,
            "is_active": true,
            "is_dttm": false,
            "python_date_format": null,
            "type": "Decimal(38, 2)",
            "type_generic": 0,
            "uuid": "8118fa35-fe65-4413-bdb7-fee783960461",
            "verbose_name": null
          },
          "datasourceWarning": false,
          "expressionType": "SIMPLE",
          "hasCustomLabel": true,
          "label": "Tutar",
          "optionName": "metric_r5fa7njezfn_2p7knd1q5cb",
          "sqlExpression": null
        }
      ],
      "orderby": [
        [
          {
            "aggregate": "SUM",
            "column": {
              "advanced_data_type": null,
              "changed_on": "2025-08-04T09:58:59.261495",
              "column_name": "Tutar",
              "created_on": "2025-08-04T09:58:59.261494",
              "description": null,
              "expression": null,
              "extra": null,
              "filterable": true,
              "groupby": true,
              "id": 7400,
              "is_active": true,
              "is_dttm": false,
              "python_date_format": null,
              "type": "Decimal(38, 2)",
              "type_generic": 0,
              "uuid": "8118fa35-fe65-4413-bdb7-fee783960461",
              "verbose_name": null
            },
            "datasourceWarning": false,
            "expressionType": "SIMPLE",
            "hasCustomLabel": true,
            "label": "Tutar",
            "optionName": "metric_r5fa7njezfn_2p7knd1q5cb",
            "sqlExpression": null
          },
          false
        ]
      ],
      "annotation_layers": [],
      "row_limit": 100,
      "series_columns": [],
      "series_limit": 0,
      "order_desc": true,
      "url_params": { "dashboard_page_id": "GulxjbioGz-6_xA0Jptak", "slice_id": "1947" },
      "custom_params": {},
      "custom_form_data": {},
      "time_offsets": [],
      "post_processing": [
        {
          "operation": "pivot",
          "options": {
            "index": ["Yaş Grubu"],
            "columns": [],
            "aggregates": { "Tutar": { "operator": "mean" } },
            "drop_missing_columns": false
          }
        },
        { "operation": "flatten" }
      ]
    }
  ],
  "form_data": {
    "datasource": "344__table",
    "viz_type": "echarts_timeseries_bar",
    "slice_id": 1947,
    "url_params": { "dashboard_page_id": "GulxjbioGz-6_xA0Jptak", "slice_id": "1947" },
    "x_axis": {
      "datasourceWarning": false,
      "expressionType": "SQL",
      "label": "Yaş Grubu",
      "sqlExpression": "YasGrubu"
    },
    "x_axis_sort_asc": true,
    "x_axis_sort_series": "name",
    "x_axis_sort_series_ascending": true,
    "metrics": [
      {
        "aggregate": "SUM",
        "column": {
          "advanced_data_type": null,
          "changed_on": "2025-08-04T09:58:59.261495",
          "column_name": "Tutar",
          "created_on": "2025-08-04T09:58:59.261494",
          "description": null,
          "expression": null,
          "extra": null,
          "filterable": true,
          "groupby": true,
          "id": 7400,
          "is_active": true,
          "is_dttm": false,
          "python_date_format": null,
          "type": "Decimal(38, 2)",
          "type_generic": 0,
          "uuid": "8118fa35-fe65-4413-bdb7-fee783960461",
          "verbose_name": null
        },
        "datasourceWarning": false,
        "expressionType": "SIMPLE",
        "hasCustomLabel": true,
        "label": "Tutar",
        "optionName": "metric_r5fa7njezfn_2p7knd1q5cb",
        "sqlExpression": null
      }
    ],
    "groupby": [],
    "adhoc_filters": [],
    "order_desc": true,
    "row_limit": 100,
    "truncate_metric": true,
    "show_empty_columns": true,
    "comparison_type": "values",
    "annotation_layers": [],
    "forecastPeriods": 10,
    "forecastInterval": 0.8,
    "orientation": "vertical",
    "x_axis_title_margin": 15,
    "y_axis_title_margin": 30,
    "y_axis_title_position": "Left",
    "sort_series_type": "sum",
    "color_scheme": "supersetColors",
    "time_shift_color": true,
    "only_total": true,
    "show_legend": true,
    "legendType": "scroll",
    "legendOrientation": "bottom",
    "x_axis_time_format": "smart_date",
    "xAxisLabelRotation": 45,
    "y_axis_format": "SMART_NUMBER",
    "y_axis_bounds": [null, null],
    "truncateXAxis": true,
    "rich_tooltip": true,
    "showTooltipTotal": true,
    "tooltipTimeFormat": "smart_date",
    "extra_form_data": {},
    "force": true,
    "result_format": "json",
    "result_type": "full"
  },
  "result_format": "json",
  "result_type": "full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=Wui3e9NZEbY&dashboard_page_id=GulxjbioGz-6_xA0Jptak&slice_id=1947")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject sendWidget85Request() {
        final String url = "https://dia-dashboard.efectura.com/api/v1/chart/data?form_data=%7B%22slice_id%22%3A1953%7D&force=true";

        OkHttpClient client = InsecureHttp.newClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        String cookie = ConfigurationReader.getProperty("cookie");

        String body = """
{
  "datasource": { "id": 347, "type": "table" },
  "force": true,
  "queries": [
    {
      "filters": [
        { "col": "VadeTarihi", "op": "TEMPORAL_RANGE", "val": "No filter" }
      ],
      "extras": { "having": "", "where": "" },
      "applied_time_extras": {},
      "columns": [],
      "metrics": [
        {
          "aggregate": null,
          "column": null,
          "datasourceWarning": false,
          "expressionType": "SQL",
          "hasCustomLabel": true,
          "label": "Tutar",
          "optionName": "metric_y1mj68ttl4_1nde7zzxuc0j",
          "sqlExpression": "SUM(KalanTutar)"
        }
      ],
      "annotation_layers": [],
      "series_limit": 0,
      "order_desc": true,
      "url_params": { "dashboard_page_id": "GulxjbioGz-6_xA0Jptak", "slice_id": "1953" },
      "custom_params": {},
      "custom_form_data": {}
    }
  ],
  "form_data": {
    "datasource": "347__table",
    "viz_type": "big_number_total",
    "slice_id": 1953,
    "url_params": { "dashboard_page_id": "GulxjbioGz-6_xA0Jptak", "slice_id": "1953" },
    "metric": {
      "aggregate": null,
      "column": null,
      "datasourceWarning": false,
      "expressionType": "SQL",
      "hasCustomLabel": true,
      "label": "Tutar",
      "optionName": "metric_y1mj68ttl4_1nde7zzxuc0j",
      "sqlExpression": "SUM(KalanTutar)"
    },
    "adhoc_filters": [
      {
        "clause": "WHERE",
        "comparator": "No filter",
        "datasourceWarning": false,
        "expressionType": "SIMPLE",
        "filterOptionName": "filter_2srboofnpgw_ew5fi5c2kcr",
        "isExtra": false,
        "isNew": false,
        "operator": "TEMPORAL_RANGE",
        "sqlExpression": null,
        "subject": "VadeTarihi"
      }
    ],
    "subheader": "Toplam Borç",
    "header_font_size": 0.4,
    "subheader_font_size": 0.15,
    "y_axis_format": "SMART_NUMBER",
    "time_format": "smart_date",
    "conditional_formatting": [],
    "extra_form_data": {},
    "force": true,
    "result_format": "json",
    "result_type": "full"
  },
  "result_format": "json",
  "result_type": "full"
}
""";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", "en-US,en;q=0.9,tr;q=0.8")
                .addHeader("Origin", "https://dia-dashboard.efectura.com")
                .addHeader("Referer", "https://dia-dashboard.efectura.com/explore/?form_data_key=kwqv9EQ7hkA&dashboard_page_id=GulxjbioGz-6_xA0Jptak&slice_id=1953")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("X-CSRFToken", "")
                .addHeader("Cookie", cookie)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        try (Response resp = client.newCall(request).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("Unexpected code " + resp.code() + " - " +
                        (resp.body() != null ? resp.body().string() : ""));
            }
            String respStr = resp.body() != null ? resp.body().string() : "{}";
            return new JSONObject(respStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }









}
