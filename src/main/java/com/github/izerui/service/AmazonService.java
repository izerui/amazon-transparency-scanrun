package com.github.izerui.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.izerui.pojo.ScanItemRequest;
import com.github.izerui.pojo.ScanResult;
import com.github.izerui.support.JMap;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RemotingDestination
public class AmazonService {

    private MediaType JSON_TYPE = MediaType.parse("application/json;charset=utf-8");

    @Autowired
    OkHttpClient okHttpClient;
    @Autowired
    ObjectMapper objectMapper;


    public String validateCookie(String cookie,String url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url,String.class);
//        Request request = new Request.Builder()
//                .url(url)
//                .header("cookie", cookie)
//                .header("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
//                .get().build();
//        Response response = okHttpClient.newCall(request).execute();
//        String body = response.body().string();
//        return body;
    }


    public Map<String, Object> getRunInfo(String cookie) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.amazon.com/authenticity/scanrun/v2?")
                .header("cookie", cookie)
                .header("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .get().build();
        Response response = okHttpClient.newCall(request).execute();
        String body = response.body().string();

        Document document = Jsoup.parse(body);


        Map<String, Object> info = new HashMap<>();


        Elements elements = document.getElementsByAttribute("data-ng-init");
        elements.forEach(element -> {
            String dataInit = element.attr("data-ng-init");
            String[] split = dataInit.split(";");
            for (String s : split) {
                String[] strings = s.split("=");
                if (strings[0].contains("scanRun.")) {
                    String key = strings[0].replace("scanRun.", "").trim();
                    String value = strings[1].replaceAll("'", "").trim();
                    info.put(key, value);
                }
            }
        });

        if (info.isEmpty()) {
            throw new RuntimeException("请先选择一个货柜,然后复制COOKIE重试!");
        }


        return info;
    }


    /**
     * 扫描提交
     *
     * @param cookie
     * @param scanItemList 扫描的多个对象
     * @return
     * @throws IOException
     */
    public ScanResult scanItems(String cookie, List<ScanItemRequest> scanItemList) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.amazon.com/authenticity/scanitems")
                .header("cookie", cookie)
                .header("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
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

    /**
     * 取消运行
     *
     * @param cookie
     * @param runId
     * @throws IOException
     */
    public void cancelScanRun(String cookie, String runId) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.amazon.com/authenticity/cancelscanrun")
                .header("cookie", cookie)
                .header("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
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

    public void completeScan(String cookie, String runId) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.amazon.com/authenticity/completescanrun")
                .header("cookie", cookie)
                .header("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
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
