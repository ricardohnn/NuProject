package com.rdzero.nuproject.activities;

import com.google.gson.annotations.SerializedName;

public class NuHrefObjBean {

    @SerializedName("href")
    private String href;

    public NuHrefObjBean(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}