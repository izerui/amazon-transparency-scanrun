package com.github.izerui.pojo;

import javax.persistence.*;

/**
 * 单位
 */
@Entity
@Table(
        name = "scan_item",
        indexes = {
                @Index(name = "scanTime", columnList = "scanTime"),
                @Index(name = "batchId", columnList = "batchId"),
                @Index(name = "caseItemId", columnList = "caseItemId"),
                @Index(name = "status", columnList = "status"),
                @Index(name = "itemId", columnList = "itemId"),
        }
)
public class ScanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 64)
    private String batchId; //扫描批次id
    @Column(length = 64)
    private String itemId;//条码内容
    @Column(length = 64)
    private String caseItemId; //包装id
    private Long scanTime;//扫描时间
    @Column(length = 12)
    private String status;//同步状态 unDone Done

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCaseItemId() {
        return caseItemId;
    }

    public void setCaseItemId(String caseItemId) {
        this.caseItemId = caseItemId;
    }

    public Long getScanTime() {
        return scanTime;
    }

    public void setScanTime(Long scanTime) {
        this.scanTime = scanTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
