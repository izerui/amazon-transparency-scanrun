package com.github.izerui.service;

import com.github.izerui.dao.ScanBatchDao;
import com.github.izerui.dao.ScanCaseDao;
import com.github.izerui.dao.ScanItemDao;
import com.github.izerui.entity.ScanBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    public List<ScanBatch> findBatches(){
        List<ScanBatch> all = scanBatchDao.findAll(Sort.by(Sort.Direction.DESC,"id"));
        return all;
    }

}
