/**
 * Copyright 2018 bejson.com
 */
package com.github.izerui.pojo;

import java.util.List;

/**
 * Auto-generated: 2018-12-21 17:45:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Product {

    private String upc;
    private String asin;
    private String title;
    private int revision;
    private List<ProductContainerConfigs> productContainerConfigs;
    private Attributes attributes;
    private boolean weblabEnabled;

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getUpc() {
        return upc;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getAsin() {
        return asin;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public int getRevision() {
        return revision;
    }

    public void setProductContainerConfigs(List<ProductContainerConfigs> productContainerConfigs) {
        this.productContainerConfigs = productContainerConfigs;
    }

    public List<ProductContainerConfigs> getProductContainerConfigs() {
        return productContainerConfigs;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setWeblabEnabled(boolean weblabEnabled) {
        this.weblabEnabled = weblabEnabled;
    }

    public boolean getWeblabEnabled() {
        return weblabEnabled;
    }

}