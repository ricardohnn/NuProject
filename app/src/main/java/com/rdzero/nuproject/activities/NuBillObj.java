package com.rdzero.nuproject.activities;

import com.google.gson.annotations.SerializedName;

public class NuBillObj {

    @SerializedName("bill")
    private NuBillObjBean bill;

    public NuBillObj(NuBillObjBean bill) {
        this.bill = bill;
    }

    public NuBillObjBean getBill() {
        return bill;
    }

    public void setBill(NuBillObjBean bill) {
        this.bill = bill;
    }

}