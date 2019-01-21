package com.github.izerui.service;

import com.github.izerui.dao.ScanBatchDao;
import com.github.izerui.dao.ScanCaseDao;
import com.github.izerui.dao.ScanItemDao;
import com.github.izerui.entity.ScanBatch;
import com.github.izerui.entity.ScanCase;
import com.github.izerui.entity.ScanItem;
import com.github.izerui.event.UploadCaseEvent;
import com.github.izerui.jpa.impl.Conditions;
import com.github.izerui.pojo.ScanItemRequest;
import com.github.izerui.pojo.ScanItemResult;
import com.github.izerui.pojo.ScanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;

@RemotingDestination
@Service
@Transactional
public class DataService {

    private Logger logger = LoggerFactory.getLogger(DataService.class);

    @Autowired
    private AmazonService amazonService;
    @Autowired
    private ScanBatchDao scanBatchDao;
    @Autowired
    private ScanCaseDao scanCaseDao;
    @Autowired
    private ScanItemDao scanItemDao;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ApplicationContext applicationContext;


    /**
     * 根据运行信息找到批次，没有则创建
     *
     * @param cookie
     * @return
     */
    public ScanBatch createBatch(String cookie) throws IOException {

        Map<String, Object> runInfo = amazonService.getRunInfo(cookie);

        String manufacturerLot = (String) runInfo.get("manufacturerLot");
        String parentUpc = (String) runInfo.get("parentUpc");
        String productTitle = (String) runInfo.get("productTitle");
        String asin = (String) runInfo.get("asin");
        String manufacturerReference = (String) runInfo.get("manufacturerReference");
        String runId = (String) runInfo.get("runId");
        String unitsPerCase = (String) runInfo.get("unitsPerCase");
        String expectedCaseCount = (String) runInfo.get("expectedCaseCount");
        String expectedCount = (String) runInfo.get("expectedCount");
        String unitLabelRegExPattern = (String) runInfo.get("unitLabelRegExPattern");
        String caseLabelRegExPattern = (String) runInfo.get("caseLabelRegExPattern");
        String tempCaseToken = (String) runInfo.get("tempCaseToken");
        String vendorCode = (String) runInfo.get("vendorCode");

        Conditions conditions = Conditions
                .where("manufacturerLot").is(manufacturerLot)
                .and("parentUpc").is(parentUpc)
                .and("productTitle").is(productTitle)
                .and("manufacturerReference").is(manufacturerReference);
        ScanBatch batch = scanBatchDao.findOne(conditions);
        if (batch == null) {
            batch = new ScanBatch();
            batch.setProductTitle(productTitle);
            batch.setManufacturerLot(manufacturerLot);
            batch.setParentUpc(parentUpc);
            batch.setAsin(asin);
            batch.setManufacturerReference(manufacturerReference);
            batch.setBatchId(UUID.randomUUID().toString());
        }
        batch.setVendorCode(vendorCode);
        batch.setRunId(runId);
        batch.setUnitsPerCase(Integer.valueOf(unitsPerCase));
        batch.setExpectedCaseCount(Integer.valueOf(expectedCaseCount));
        batch.setExpectedCount(Integer.valueOf(expectedCount));
        batch.setUnitLabelRegExPattern(unitLabelRegExPattern);
        batch.setCaseLabelRegExPattern(caseLabelRegExPattern);
        batch.setTempCaseToken(tempCaseToken);
        scanBatchDao.save(batch);

        //更新批次相关的详细信息
        historyService.updateScanBatches(cookie);

        return batch;
    }


    /**
     * 扫描包装
     */
    public void saveCase(String cookie, String batchId, String itemId) {
        logger.info("本次扫描 批次: {} 包装: {}", batchId, itemId);
        Conditions conditions = Conditions
                .where("batchId").is(batchId)
                .and("itemId").is(itemId);
        Assert.state(scanCaseDao.count(conditions) == 0, "包装 " + itemId + " 已扫描过");
        Assert.state(scanItemDao.count(conditions) == 0, itemId + " 已经在同批次货品中使用过");
        ScanCase one = new ScanCase();
        one.setBatchId(batchId);
        one.setItemId(itemId);
        one.setCount(0);
        one.setScanTime(System.currentTimeMillis());
        scanCaseDao.save(one);
//        applicationContext.publishEvent(new UploadCaseEvent(this, cookie, batchId));
    }

    /**
     * 扫描单位
     */
    public void saveItem(String cookie, String batchId, String caseItemId, String itemId) {
        logger.info("本次扫描 批次: {} 单位: {}", batchId, itemId);
        Conditions conditions = Conditions
                .where("batchId").is(batchId)
                .and("itemId").is(itemId);
        Assert.state(scanItemDao.count(conditions) == 0, "货品 " + itemId + " 已扫描过");
        Assert.state(scanCaseDao.count(conditions) == 0, itemId + " 已经在同批次包装中使用过");
        ScanCase caseOne = scanCaseDao.findOne(Conditions.where("batchId").is(batchId).and("itemId").is(caseItemId));
        Assert.notNull(caseOne, "未找到包装");
        caseOne.setScanTime(System.currentTimeMillis());
        caseOne.setCount(caseOne.getCount() + 1);
        scanCaseDao.save(caseOne);

        ScanItem one = new ScanItem();
        one.setBatchId(batchId);
        one.setCaseItemId(caseItemId);
        one.setItemId(itemId);
        one.setScanTime(System.currentTimeMillis());
        scanItemDao.save(one);
    }

    /**
     * 查询包装列表
     *
     * @param batchId
     * @return
     */
    public List<ScanCase> findCaseList(String batchId, boolean submited) {
        Conditions conditions = Conditions
                .where("batchId").is(batchId)
                .and("submited").is(submited);
        List<ScanCase> caseList = scanCaseDao.findAll(conditions, Sort.by(Sort.Direction.DESC, "scanTime"));
        return caseList;
    }


    /**
     * 查询单位列表
     *
     * @param batchId
     * @return
     */
    public List<ScanItem> findItemList(String batchId, String caseItemId, boolean submited) {
        Conditions conditions = Conditions
                .where("batchId").is(batchId)
                .and("caseItemId").is(caseItemId)
                .and("submited").is(submited);
        List<ScanItem> itemList = scanItemDao.findAll(conditions, Sort.by(Sort.Direction.DESC, "scanTime"));
        return itemList;
    }

    /**
     * 上传包装盒到亚马逊
     *
     * @param cookie
     * @param batch
     */
    public void uploadCase(String cookie, ScanBatch batch) throws IOException {
        List<ScanItemRequest> requestList = null;
        List<ScanCase> caseList = this.findCaseList(batch.getBatchId(), false);
        if (caseList == null || caseList.isEmpty()) {
            return;
        }
        for (ScanCase scanCase : caseList) {
            ScanItemRequest caseRequest = new ScanItemRequest();
            caseRequest.setItemId(scanCase.getItemId());
            caseRequest.setCaseItemId("");
            caseRequest.setParent(true);
            caseRequest.setRunId(batch.getRunId());
            caseRequest.setActive(true);
            caseRequest.setTempCaseToken("empty");

            requestList = new ArrayList<>();
            requestList.add(caseRequest);

            List<ScanItem> scanItems = this.findItemList(batch.getBatchId(), scanCase.getItemId(), false);
            for (ScanItem scanItem : scanItems) {
                ScanItemRequest itemRequest = new ScanItemRequest();
                itemRequest.setItemId(scanItem.getItemId());
                itemRequest.setCaseItemId(scanCase.getItemId());
                itemRequest.setParent(false);
                itemRequest.setRunId(batch.getRunId());
                itemRequest.setActive(true);
                itemRequest.setTempCaseToken(batch.getTempCaseToken());
                requestList.add(itemRequest);
            }
            ScanResult scanResult = amazonService.scanItems(cookie, requestList);
            for (ScanItemResult itemResult : scanResult.getScanItemResultList()) {
                if (itemResult.getScanItemId().equals(scanCase.getItemId())) {
                    // 更新包装的同步状态
                    scanCase.setSubmited(true);
                    scanCase.setRequestStatus(itemResult.getRequestStatus());
                    scanCase.setFailureReason(itemResult.getFailureReason());
                    scanCase.setSubmitTime(new Date());
                    scanCaseDao.save(scanCase);
                }
                for (ScanItem scanItem : scanItems) {
                    if (itemResult.getScanItemId().equals(scanItem.getItemId())) {
                        // 更新包装内货品的同步状态
                        scanItem.setSubmited(true);
                        scanItem.setSubmitTime(new Date());
                        scanItem.setRequestStatus(itemResult.getRequestStatus());
                        scanItem.setFailureReason(itemResult.getFailureReason());
                        scanItemDao.save(scanItem);
                    }
                }

            }
        }
    }


    /**
     * 上传剩下的并完成提交
     *
     * @param cookie
     * @param batchId
     * @throws IOException
     */
    public void submitScan(String cookie, String batchId) throws IOException {
        ScanBatch batch = scanBatchDao.findOne(Conditions.where("batchId").is(batchId));
        uploadCase(cookie, batch);
        amazonService.completeScan(cookie, batch.getRunId());
    }

    public void deleteCase(String cookie, String batchId, String itemId) throws IOException {
        ScanBatch batch = scanBatchDao.findOne(Conditions.where("batchId").is(batchId));
        ScanCase one = scanCaseDao.findOne(
                Conditions
                        .where("batchId").is(batchId)
                        .and("itemId").is(itemId)
        );
        Assert.notNull(one, "不存在的包装 " + itemId);

//        List<ScanItemRequest> requestList = new ArrayList<>();
//
//        ScanItemRequest caseRequest = new ScanItemRequest();
//        caseRequest.setItemId(one.getItemId());
//        caseRequest.setCaseItemId("");
//        caseRequest.setParent(true);
//        caseRequest.setRunId(batch.getRunId());
//        caseRequest.setActive(false);
//        caseRequest.setTempCaseToken("empty");
//        requestList.add(caseRequest);
//
//        Conditions conditions = Conditions
//                .where("batchId").is(batchId)
//                .and("caseItemId").is(itemId);
//        List<ScanItem> itemList = scanItemDao.findAll(conditions, Sort.by(Sort.Direction.DESC, "scanTime"));
//        for (ScanItem scanItem : itemList) {
//            ScanItemRequest itemRequest = new ScanItemRequest();
//            itemRequest.setItemId(scanItem.getItemId());
//            itemRequest.setCaseItemId(itemId);
//            itemRequest.setParent(false);
//            itemRequest.setRunId(batch.getRunId());
//            itemRequest.setActive(false);
//            itemRequest.setTempCaseToken(batch.getTempCaseToken());
//            requestList.add(itemRequest);
//        }
//        amazonService.scanItems(cookie, requestList);
        scanCaseDao.delete(one);
        scanItemDao.deleteAll(
                Conditions
                        .where("batchId").is(batchId)
                        .and("caseItemId").is(itemId)

        );
    }


    public void deleteItem(String batchId, String itemId) {
        ScanItem one = scanItemDao.findOne(
                Conditions
                        .where("batchId").is(batchId)
                        .and("itemId").is(itemId)
        );
        Assert.notNull(one, "不存在的货品 " + itemId);
        scanItemDao.delete(one);
    }

    public ScanBatch getBatch(String batchId) {
        return scanBatchDao.findOne(Conditions.where("batchId").is(batchId));
    }
}
