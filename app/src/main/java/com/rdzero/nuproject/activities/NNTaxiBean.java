package com.rdzero.nuproject.activities;

import com.google.gson.annotations.SerializedName;

public class NNTaxiBean {

    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("driverId")
    private long driverId;
    @SerializedName("driverAvailable")
    private String driverAvailable;

    public NNTaxiBean(Double latitude, Double longitude, long driverId, String driverAvailable) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.driverId = driverId;
        this.driverAvailable = driverAvailable;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public String getDriverAvailable() {
        return driverAvailable;
    }

    public void setDriverAvailable(String driverAvailable) {
        this.driverAvailable = driverAvailable;
    }
}
