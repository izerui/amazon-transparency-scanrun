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
public class Attributes {

    private List<Locations> locations;
    private String manufacturerName;
    private boolean bestByDateRequired;

    public void setLocations(List<Locations> locations) {
        this.locations = locations;
    }

    public List<Locations> getLocations() {
        return locations;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setBestByDateRequired(boolean bestByDateRequired) {
        this.bestByDateRequired = bestByDateRequired;
    }

    public boolean getBestByDateRequired() {
        return bestByDateRequired;
    }

}