package com.rdzero.nuproject.activities;

import com.google.gson.annotations.SerializedName;

public class NuLinksObjBean {

    @SerializedName("self")
    private NuHrefObjBean self;

    @SerializedName("boleto_email")
    private NuHrefObjBean boleto_email;

    @SerializedName("barcode")
    private NuHrefObjBean barcode;

    public NuLinksObjBean(NuHrefObjBean self, NuHrefObjBean boleto_email, NuHrefObjBean barcode) {
        this.self = self;
        this.boleto_email = boleto_email;
        this.barcode = barcode;
    }

    public NuHrefObjBean getSelf() {
        return self;
    }

    public void setSelf(NuHrefObjBean self) {
        this.self = self;
    }

    public NuHrefObjBean getBoleto_email() {
        return boleto_email;
    }

    public void setBoleto_email(NuHrefObjBean boleto_email) {
        this.boleto_email = boleto_email;
    }

    public NuHrefObjBean getBarcode() {
        return barcode;
    }

    public void setBarcode(NuHrefObjBean barcode) {
        this.barcode = barcode;
    }

}