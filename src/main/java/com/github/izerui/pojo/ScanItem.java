/**
 * Copyright 2018 bejson.com
 */
package com.github.izerui.pojo;

/**
 * Auto-generated: 2018-12-21 19:18:1
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ScanItem {

    private String itemId;
    private String caseItemId;
    private boolean parent;
    private String runId;
    private boolean active;
    private String tempCaseToken;

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setCaseItemId(String caseItemId) {
        this.caseItemId = caseItemId;
    }

    public String getCaseItemId() {
        return caseItemId;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public boolean getParent() {
        return parent;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getRunId() {
        return runId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    public void setTempCaseToken(String tempCaseToken) {
        this.tempCaseToken = tempCaseToken;
    }

    public String getTempCaseToken() {
        return tempCaseToken;
    }

}