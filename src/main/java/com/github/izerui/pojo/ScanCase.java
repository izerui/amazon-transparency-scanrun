/**
 * Copyright 2018 bejson.com
 */
package com.github.izerui.pojo;

import javax.persistence.*;

/**
 * 包装
 */
@Entity
@Table(
        name = "scan_case",
        indexes = {
                @Index(name = "scanTime", columnList = "scanTime"),
                @Index(name = "batchId", columnList = "batchId"),
                @Index(name = "status", columnList = "status"),
                @Index(name = "itemId", columnList = "itemId"),
        }
)
public class ScanCase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 64)
    private String batchId; //扫描批次id
    @Column(length = 64)
    private String itemId;//条码内容
    private Long scanTime;//扫描时间
    @Column(length = 12)
    private String status;//同步状态 unDone Done


    //包装下的数量
    private long count;

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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}