package com.github.izerui.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.izerui.pojo.Product;
import com.github.izerui.pojo.ProductContainerConfigs;
import com.github.izerui.pojo.ScanItem;
import com.github.izerui.pojo.ScanResult;
import com.github.izerui.support.JMap;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RemotingDestination
public class AmazonService {

    private MediaType JSON_TYPE = MediaType.parse("application/json;charset=utf-8");

    @Autowired
    OkHttpClient okHttpClient;
    @Autowired
    ObjectMapper objectMapper;


    public boolean validateLoginStatus(String cookie) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.amazon.com/authenticity/scanrun/v2?")
                .header("cookie", cookie)
                .get().build();
        Response response = okHttpClient.newCall(request).execute();
        String string = response.body().string();
        return StringUtils.contains(string, "SourceMark");
    }

    public String getRunId(String cookie) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.amazon.com/authenticity/scanrun/v2?")
                .header("cookie", cookie)
                .get().build();
        Response response = okHttpClient.newCall(request).execute();
        String s = response.body().string();
        int i = s.indexOf("scanRun.runId=");
        String substring = s.substring(i + 14, s.length() - 1);
        substring = substring.substring(substring.indexOf("'") + 1, substring.length());
        substring = substring.substring(0, substring.indexOf("'"));
        return substring;
    }


    /**
     * 获取产品信息
     *
     * @param cookie
     * @param gtin
     * @return
     * @throws IOException
     */
    public Product getProductlist(String cookie, String gtin) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.amazon.com/authenticity/getproductlist")
                .header("cookie", cookie)
                .put(
                        RequestBody.create(JSON_TYPE, JMap.create("gtin", gtin).writeToBytes(objectMapper))
                ).build();
        Response response = okHttpClient.newCall(request).execute();
        JsonNode jsonNode = objectMapper.readValue(response.body().string(), JsonNode.class);
        boolean successful = jsonNode.path("successful").asBoolean();
        if (successful) {
            return objectMapper.readValue(jsonNode.path("data").toString(), Product.class);
        } else {
            throw new RuntimeException(jsonNode.path("errorCode").asText());
        }
    }

    /**
     * 开始扫描
     *
     * @param cookie
     * @param productContainerConfigs
     * @param manufacturerLot                   批次
     * @param expectedCount                     总单位数
     * @param selectedManufacturerLocationIndex 0
     * @param manufacturerReference             备注
     */
    public void scanRun(String cookie,
                        ProductContainerConfigs productContainerConfigs,
                        String manufacturerLot,
                        String expectedCount,
                        String selectedManufacturerLocationIndex,
                        String manufacturerReference) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.amazon.com/authenticity/scanrun")
                .header("cookie", cookie)
                .post(
                        new FormBody.Builder()
                                .add("applicationVersion", "v2")
                                .add("upc", productContainerConfigs.getGtin())
                                .add("configInternalId", productContainerConfigs.getInternalId())
                                .add("manufacturerLot", manufacturerLot)
                                .add("expectedCount", expectedCount)
                                .add("selectedManufacturerLocationIndex", selectedManufacturerLocationIndex)
                                .add("bestByDate", "")
                                .add("manufacturerReference", manufacturerReference)
                                .build()
                ).build();
        Response response = okHttpClient.newCall(request).execute();
        System.out.println(response);
    }

    /**
     * 扫描提交
     *
     * @param cookie
     * @param active       true：同步amazon  false：不同步
     * @param scanItemList 扫描的多个对象
     * @return
     * @throws IOException
     */
    public ScanResult scanItems(String cookie, boolean active, List<ScanItem> scanItemList) throws IOException {
        for (ScanItem scanItem : scanItemList) {
            scanItem.setActive(active);
        }
        Request request = new Request.Builder()
                .url("https://www.amazon.com/authenticity/scanitems")
                .header("cookie", cookie)
                .post(
                        RequestBody.create(JSON_TYPE, JMap.create("scanItemRequestList", scanItemList).writeToBytes(objectMapper))
                ).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            JsonNode jsonNode = objectMapper.readValue(response.body().string(), JsonNode.class);
            boolean successful = jsonNode.path("successful").asBoolean();
            if (successful) {
                return objectMapper.readValue(jsonNode.path("data").toString(), ScanResult.class);
            } else {
                throw new RuntimeException(jsonNode.path("errorCode").asText());
            }
        } else {
            throw new RuntimeException("请求失败");
        }

    }

    public void cancelScanRun(String cookie, String runId) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.amazon.com/authenticity/cancelscanrun")
                .header("cookie", cookie)
                .put(
                        RequestBody.create(JSON_TYPE, JMap.create("runId", runId).writeToBytes(objectMapper))
                ).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            JsonNode jsonNode = objectMapper.readValue(response.body().string(), JsonNode.class);
            boolean successful = jsonNode.path("successful").asBoolean();
            if (!successful) {
                throw new RuntimeException(jsonNode.path("errorCode").asText());
            }
        } else {
            throw new RuntimeException("请求失败");
        }
    }

}
