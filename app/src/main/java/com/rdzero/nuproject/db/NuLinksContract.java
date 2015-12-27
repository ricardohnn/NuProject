package com.rdzero.nuproject.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = NuProjDatabase.class)
public class NuLinksContract extends BaseModel{

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    @ForeignKey(saveForeignKeyModel = false)
    NuBillContract nuBillContract;

    @Column
    private String self;
    @Column
    private String boletoEmail;
    @Column
    private String barcode;

    public NuLinksContract() {
    }

    public NuBillContract getNuBillContract() {
        return nuBillContract;
    }

    public NuLinksContract(String self, String boletoEmail, String barcode) {
        this.self = self;
        this.boletoEmail = boletoEmail;
        this.barcode = barcode;
    }

    public NuLinksContract(NuBillContract nuBillContract, String self, String boletoEmail, String barcode) {
        this.nuBillContract = nuBillContract;
        this.self = self;
        this.boletoEmail = boletoEmail;
        this.barcode = barcode;
    }

    public void setNuBillContract(NuBillContract nuBillContract) {
        this.nuBillContract = nuBillContract;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getBoletoEmail() {
        return boletoEmail;
    }

    public void setBoletoEmail(String boletoEmail) {
        this.boletoEmail = boletoEmail;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}