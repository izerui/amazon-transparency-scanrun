package com.github.izerui.event;


import com.github.izerui.entity.ScanBatch;
import com.github.izerui.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@Transactional
public class UploadCaseEventListener implements ApplicationListener<UploadCaseEvent> {

    @Autowired
    DataService dataService;

    @Async
    @Override
    public void onApplicationEvent(UploadCaseEvent uploadCaseEvent) {
        try {
            System.out.println("异步执行");
            ScanBatch batch = dataService.getBatch(uploadCaseEvent.getBatchId());
            if (batch == null) {
                return;
            }
            dataService.uploadCase(uploadCaseEvent.getCookie(), batch);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
