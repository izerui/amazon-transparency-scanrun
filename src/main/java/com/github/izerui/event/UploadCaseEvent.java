package com.github.izerui.event;

import com.github.izerui.entity.ScanBatch;
import org.springframework.context.ApplicationEvent;

public class UploadCaseEvent extends ApplicationEvent {

    private String cookie;
    private String batchId;

    public UploadCaseEvent(Object source, String cookie, String batchId) {
        super(source);
        this.cookie = cookie;
        this.batchId = batchId;
    }

    public String getBatchId() {
        return batchId;
    }

    public String getCookie() {
        return cookie;
    }
}
