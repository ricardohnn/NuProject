package com.rdzero.nuproject.beans;

import com.google.gson.annotations.SerializedName;

public class NuLinksObjBean {

    @SerializedName("self")
    private NuHrefObjBean self;

    @SerializedName("boleto_email")
    private NuHrefObjBean boletoEmail;

    @SerializedName("barcode")
    private NuHrefObjBean barcode;

    public NuLinksObjBean(NuHrefObjBean self, NuHrefObjBean boletoEmail, NuHrefObjBean barcode) {
        this.self = self;
        this.boletoEmail = boletoEmail;
        this.barcode = barcode;
    }

    public NuHrefObjBean getSelf() {
        return self;
    }

    public void setSelf(NuHrefObjBean self) {
        this.self = self;
    }

    public NuHrefObjBean getBoletoEmail() {
        return boletoEmail;
    }

    public void setBoletoEmail(NuHrefObjBean boletoEmail) {
        this.boletoEmail = boletoEmail;
    }

    public NuHrefObjBean getBarcode() {
        return barcode;
    }

    public void setBarcode(NuHrefObjBean barcode) {
        this.barcode = barcode;
    }

}