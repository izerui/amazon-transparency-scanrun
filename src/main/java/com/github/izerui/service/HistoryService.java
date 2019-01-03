package com.github.izerui.service;

import com.github.izerui.dao.ScanBatchDao;
import com.github.izerui.dao.ScanCaseDao;
import com.github.izerui.dao.ScanItemDao;
import com.github.izerui.entity.ScanBatch;
import com.github.izerui.entity.ScanCase;
import com.github.izerui.entity.ScanItem;
import com.github.izerui.jpa.impl.Conditions;
import com.github.izerui.pojo.Product;
import com.github.izerui.pojo.ProductContainerConfigs;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RemotingDestination
public class HistoryService {

    @Autowired
    private ScanBatchDao scanBatchDao;
    @Autowired
    private ScanCaseDao scanCaseDao;
    @Autowired
    private ScanItemDao scanItemDao;
    @Autowired
    private AmazonService amazonService;


    public List<ScanBatch> findBatches() {
        List<ScanBatch> all = scanBatchDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<Map> caseMaps = scanCaseDao.groupAll(Lists.newArrayList("batchId", "min(scanTime) as begin", "max(scanTime) as end", "count(0) as count"), Lists.newArrayList("batchId"), Map.class);
        List<Map> itemMaps = scanItemDao.groupAll(Lists.newArrayList("batchId", "count(0) as count"), Lists.newArrayList("batchId"), Map.class);
        for (ScanBatch scanBatch : all) {
            for (Map caseMap : caseMaps) {
                if (caseMap.get("batchId") != null && caseMap.get("batchId").equals(scanBatch.getBatchId())) {
                    scanBatch.setBeginTime(new DateTime(caseMap.get("begin")).toDate());
                    scanBatch.setEndTime(new DateTime(caseMap.get("end")).toDate());
                    scanBatch.setCaseCount((Long) caseMap.get("count"));
                }
            }
            for (Map itemMap : itemMaps) {
                if (itemMap.get("batchId") != null && itemMap.get("batchId").equals(scanBatch.getBatchId())) {
                    scanBatch.setUnitCount((Long) itemMap.get("count"));
                }
            }
        }
        return all;
    }

    public ScanBatch findBatch(String batchId) {
        return scanBatchDao.findOne(Conditions.where("batchId").is(batchId));
    }

    public List<ScanCase> findCases(String batchId) {
        return scanCaseDao.findAll(Conditions.where("batchId").is(batchId));
    }

    public List<ScanItem> findItems(String batchId) {
        return scanItemDao.findAll(Conditions.where("batchId").is(batchId));
    }

    @Async
    public void updateScanBatches(String cookie) throws IOException {
        List<ScanBatch> batches = scanBatchDao.findAll(Conditions.where("upc").isNull());
        for (ScanBatch batch : batches) {
            Product product = amazonService.getProductlist(cookie, batch.getParentUpc());
            batch.setUpc(product.getUpc());

            for (ProductContainerConfigs config : product.getProductContainerConfigs()) {
                if (StringUtils.equals(batch.getParentUpc(), config.getParentGtin())
                        && StringUtils.equals(batch.getCaseLabelRegExPattern(), config.getCaseLabelRegExPattern())
                        && StringUtils.equals(batch.getUnitLabelRegExPattern(), config.getUnitLabelRegExPattern())
                        && batch.getUnitsPerCase().equals(config.getUnitsPerCase())) {

                    batch.setGtin(config.getGtin());
                    batch.setUnitStringId(config.getUnitStringId());
                    batch.setCaseStringId(config.getCaseStringId());
                    batch.setInternalId(config.getInternalId());
                }
                scanBatchDao.save(batch);
            }
        }

    }
}
