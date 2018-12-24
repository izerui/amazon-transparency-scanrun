/**
 * Copyright 2018 bejson.com
 */
package com.github.izerui.pojo;

import java.util.List;

/**
 * Auto-generated: 2018-12-21 19:21:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ScanResult {

    private int errorCount;
    private List<ScanItemResult> scanItemResultList;

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setScanItemResultList(List<ScanItemResult> scanItemResultList) {
        this.scanItemResultList = scanItemResultList;
    }

    public List<ScanItemResult> getScanItemResultList() {
        return scanItemResultList;
    }

}