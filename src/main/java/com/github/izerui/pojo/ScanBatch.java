package com.github.izerui.pojo;

import javax.persistence.*;

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
    private String unitLabelRegExPattern; // 单位正则
    private String caseLabelRegExPattern; // 包装正则


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
}
