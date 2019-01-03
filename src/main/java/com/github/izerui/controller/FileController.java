package com.github.izerui.controller;

import com.github.izerui.entity.ScanBatch;
import com.github.izerui.entity.ScanItem;
import com.github.izerui.service.HistoryService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.*;

@RestController
public class FileController {


    @Autowired
    private HistoryService historyService;


    @GetMapping("/export/{batchId}")
    public ResponseEntity<byte[]> getCookie(@PathVariable("batchId") String batchId) throws IOException {

        ScanBatch batch = historyService.findBatch(batchId);
//        List<ScanCase> cases = historyService.findCases(batchId);
        List<ScanItem> items = historyService.findItems(batchId);

        Map<String, List<ScanItem>> collect = items.stream().collect(Collectors.groupingBy(o -> o.getCaseItemId()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        out.write("ParentID,ChildID,UPC,ASIN,ManufacturerLot,MadeDate,ManufacturerName,ManufacturerLocationCountry,ManufacturerLocationState,ManufacturerLocationCity,ManufacturerReference");
        out.newLine();

        collect.forEach((s, scanItems) -> {
            try {
                StringBuilder sb;
                String scanDate = "";
                for (ScanItem it : scanItems) {
                    scanDate = new DateTime(it.getScanTime()).toString("yyyy/MM/dd");
                    sb = new StringBuilder("");
                    sb.append(trimToEmpty(it.getCaseItemId()));
                    sb.append(",");
                    sb.append(trimToEmpty(it.getItemId()));
                    sb.append(",");
                    sb.append(trimToEmpty(batch.getUpc()));
                    sb.append(",");
                    sb.append(trimToEmpty(batch.getAsin()));
                    sb.append(",");
                    sb.append(trimToEmpty(batch.getManufacturerLot()));
                    sb.append(",");
                    sb.append(trimToEmpty(scanDate));
                    sb.append(",");
                    sb.append("ZAGG");
                    sb.append(",");
                    sb.append("China");
                    sb.append(",");
                    sb.append("Guangdong");
                    sb.append(",");
                    sb.append("ShenZhen");
                    sb.append(",");
                    sb.append(trimToEmpty(batch.getManufacturerReference()));
                    out.write(sb.toString());
                    out.newLine();

                }
                sb = new StringBuilder("");
                sb.append("");
                sb.append(",");
                sb.append(trimToEmpty(s));
                sb.append(",");
                sb.append(trimToEmpty(batch.getUpc()));
                sb.append(",");
                sb.append(trimToEmpty(batch.getAsin()));
                sb.append(",");
                sb.append(trimToEmpty(batch.getManufacturerLot()));
                sb.append(",");
                sb.append(trimToEmpty(scanDate));
                sb.append(",");
                sb.append("ZAGG");
                sb.append(",");
                sb.append("China");
                sb.append(",");
                sb.append("Guangdong");
                sb.append(",");
                sb.append("ShenZhen");
                sb.append(",");
                sb.append(trimToEmpty(batch.getManufacturerReference()));
                out.write(sb.toString());
                out.newLine();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        out.flush();
        out.close();


        byte[] bytes = outputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "ScanRunItems_"+batch.getManufacturerLot()+"_"+batch.getAsin()+"_"+batch.getCaseStringId()+"_"+batch.getUnitStringId()+"_"+DateTime.now().toString("yyyyMMdd") + ".csv");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(bytes.length);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }
}
