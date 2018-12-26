package com.github.izerui.service;

import com.github.izerui.dao.ScanBatchDao;
import com.github.izerui.dao.ScanCaseDao;
import com.github.izerui.dao.ScanItemDao;
import com.github.izerui.entity.ScanBatch;
import com.github.izerui.entity.ScanCase;
import com.github.izerui.entity.ScanItem;
import com.github.izerui.jpa.impl.Conditions;
import com.github.izerui.pojo.ScanItemRequest;
import com.github.izerui.pojo.ScanItemResult;
import com.github.izerui.pojo.ScanResult;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AmazonService amazonService;
    @Autowired
    private ScanBatchDao scanBatchDao;
    @Autowired
    private ScanCaseDao scanCaseDao;
    @Autowired
    private ScanItemDao scanItemDao;


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
        batch.setRunId(runId);
        batch.setUnitsPerCase(Integer.valueOf(unitsPerCase));
        batch.setExpectedCaseCount(Integer.valueOf(expectedCaseCount));
        batch.setExpectedCount(Integer.valueOf(expectedCount));
        batch.setUnitLabelRegExPattern(unitLabelRegExPattern);
        batch.setCaseLabelRegExPattern(caseLabelRegExPattern);
        batch.setTempCaseToken(tempCaseToken);
        scanBatchDao.save(batch);
        return batch;
    }


    /**
     * 扫描包装
     */
    public void saveCase(String batchId, String itemId) {
        Conditions conditions = Conditions
                .where("batchId").is(batchId)
                .and("itemId").is(itemId);
        Assert.state(scanCaseDao.count(conditions) == 0, "包装 " + itemId + " 已扫描过");
        //TODO 包装 itemId 已经在同批次货品中使用过
        ScanCase one = new ScanCase();
        one.setBatchId(batchId);
        one.setItemId(itemId);
        one.setCount(0);
        one.setScanTime(System.currentTimeMillis());
        scanCaseDao.save(one);
    }

    /**
     * 扫描单位
     */
    public void saveItem(String batchId, String caseItemId, String itemId) {
        Conditions conditions = Conditions
                .where("batchId").is(batchId)
                .and("itemId").is(itemId);
        Assert.state(scanItemDao.count(conditions) == 0, "货品 " + itemId + " 已扫描过");
        //TODO 货品 itemId 已经在同批次包装中使用过
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

    public void deleteCase(String batchId, String itemId) {
        Conditions conditions = Conditions
                .where("batchId").is(batchId)
                .and("itemId").is(itemId);
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


    public void submitScan(String cookie, String batchId) throws IOException {
        List<ScanItemRequest> requestList = null;
        ScanBatch batch = scanBatchDao.findOne(Conditions.where("batchId").is(batchId));
        List<ScanCase> caseList = this.findCaseList(batchId, false);
        if(caseList == null || caseList.isEmpty()){
            throw new RuntimeException("没有可提交的数据");
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

            List<ScanItem> scanItems = this.findItemList(batchId, scanCase.getItemId(), false);
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
        amazonService.completeScan(cookie,batch.getRunId());
    }

}
