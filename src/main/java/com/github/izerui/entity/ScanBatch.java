package com.github.izerui.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 批次
 */
@Entity
@Table(
        name = "scan_batch",
        indexes = {
                @Index(name = "productTitle", columnList = "productTitle"),
                @Index(name = "manufacturerLot", columnList = "manufacturerLot"),
                @Index(name = "parentUpc", columnList = "parentUpc"),
        })
public class ScanBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 128)
    private String productTitle;//商品
    @Column(length = 64)
    private String manufacturerLot;//批次编号
    @Column(length = 64)
    private String parentUpc; //包装箱 UPC/GTIN
    @Column(length = 64)
    private String batchId;
    @Column(length = 64)
    private String asin;//ASIN
    @Column(length = 128)
    private String manufacturerReference; //备注
    @Column(length = 64)
    private String runId;
    private Integer unitsPerCase; //一个包装内单位数量
    private Integer expectedCaseCount;//预期包装数量
    private Integer expectedCount;//预期单位数量
    @Column(length = 128)
    private String unitLabelRegExPattern; // 单位正则
    @Column(length = 128)
    private String caseLabelRegExPattern; // 包装正则
    @Column(length = 128)
    private String tempCaseToken;
    @Column(length = 128)
    private String vendorCode;

    @Transient
    private Date beginTime;
    @Transient
    private Date endTime;

    @Column(length = 128)
    private String upc;

    @Column(length = 128)
    private String unitStringId;

    @Column(length = 128)
    private String caseStringId;

    @Column(length = 128)
    private String internalId;

    @Column(length = 128)
    private String gtin;

    @Transient
    private Long caseCount;
    @Transient
    private Long unitCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getManufacturerLot() {
        return manufacturerLot;
    }

    public void setManufacturerLot(String manufacturerLot) {
        this.manufacturerLot = manufacturerLot;
    }

    public String getParentUpc() {
        return parentUpc;
    }

    public void setParentUpc(String parentUpc) {
        this.parentUpc = parentUpc;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getManufacturerReference() {
        return manufacturerReference;
    }

    public void setManufacturerReference(String manufacturerReference) {
        this.manufacturerReference = manufacturerReference;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public Integer getUnitsPerCase() {
        return unitsPerCase;
    }

    public void setUnitsPerCase(Integer unitsPerCase) {
        this.unitsPerCase = unitsPerCase;
    }

    public Integer getExpectedCaseCount() {
        return expectedCaseCount;
    }

    public void setExpectedCaseCount(Integer expectedCaseCount) {
        this.expectedCaseCount = expectedCaseCount;
    }

    public Integer getExpectedCount() {
        return expectedCount;
    }

    public void setExpectedCount(Integer expectedCount) {
        this.expectedCount = expectedCount;
    }

    public String getUnitLabelRegExPattern() {
        return unitLabelRegExPattern;
    }

    public void setUnitLabelRegExPattern(String unitLabelRegExPattern) {
        this.unitLabelRegExPattern = unitLabelRegExPattern;
    }

    public String getCaseLabelRegExPattern() {
        return caseLabelRegExPattern;
    }

    public void setCaseLabelRegExPattern(String caseLabelRegExPattern) {
        this.caseLabelRegExPattern = caseLabelRegExPattern;
    }

    public String getTempCaseToken() {
        return tempCaseToken;
    }

    public void setTempCaseToken(String tempCaseToken) {
        this.tempCaseToken = tempCaseToken;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(Long caseCount) {
        this.caseCount = caseCount;
    }

    public Long getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Long unitCount) {
        this.unitCount = unitCount;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getUnitStringId() {
        return unitStringId;
    }

    public void setUnitStringId(String unitStringId) {
        this.unitStringId = unitStringId;
    }

    public String getCaseStringId() {
        return caseStringId;
    }

    public void setCaseStringId(String caseStringId) {
        this.caseStringId = caseStringId;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }
}
