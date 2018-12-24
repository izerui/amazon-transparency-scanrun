/**
 * Copyright 2018 bejson.com
 */
package com.github.izerui.pojo;

/**
 * Auto-generated: 2018-12-21 17:45:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ProductContainerConfigs {

    private String internalId;
    private String gtin;
    private int unitsPerCase;
    private String unitLabelRegExPattern;
    private String caseLabelRegExPattern;
    private String parentGtin;
    private boolean childScanFirst;
    private String unitStringId;
    private String caseStringId;
    private boolean trackingCases;

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getGtin() {
        return gtin;
    }

    public void setUnitsPerCase(int unitsPerCase) {
        this.unitsPerCase = unitsPerCase;
    }

    public int getUnitsPerCase() {
        return unitsPerCase;
    }

    public void setUnitLabelRegExPattern(String unitLabelRegExPattern) {
        this.unitLabelRegExPattern = unitLabelRegExPattern;
    }

    public String getUnitLabelRegExPattern() {
        return unitLabelRegExPattern;
    }

    public void setCaseLabelRegExPattern(String caseLabelRegExPattern) {
        this.caseLabelRegExPattern = caseLabelRegExPattern;
    }

    public String getCaseLabelRegExPattern() {
        return caseLabelRegExPattern;
    }

    public void setParentGtin(String parentGtin) {
        this.parentGtin = parentGtin;
    }

    public String getParentGtin() {
        return parentGtin;
    }

    public void setChildScanFirst(boolean childScanFirst) {
        this.childScanFirst = childScanFirst;
    }

    public boolean getChildScanFirst() {
        return childScanFirst;
    }

    public void setUnitStringId(String unitStringId) {
        this.unitStringId = unitStringId;
    }

    public String getUnitStringId() {
        return unitStringId;
    }

    public void setCaseStringId(String caseStringId) {
        this.caseStringId = caseStringId;
    }

    public String getCaseStringId() {
        return caseStringId;
    }

    public void setTrackingCases(boolean trackingCases) {
        this.trackingCases = trackingCases;
    }

    public boolean getTrackingCases() {
        return trackingCases;
    }

}