/**
 * Copyright 2018 bejson.com
 */
package com.github.izerui.pojo;

/**
 * Auto-generated: 2018-12-21 19:21:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ScanItemResult {

    private String requestStatus;
    private String scanItemId;
    private String failureReason;

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setScanItemId(String scanItemId) {
        this.scanItemId = scanItemId;
    }

    public String getScanItemId() {
        return scanItemId;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getFailureReason() {
        return failureReason;
    }

}