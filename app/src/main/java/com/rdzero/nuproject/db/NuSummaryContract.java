package com.rdzero.nuproject.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(database = NuProjDatabase.class)
public class NuSummaryContract extends BaseModel{

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    @ForeignKey(saveForeignKeyModel = false)
    NuBillContract nuBillContract;

    @Column
    private Date dueDate;
    @Column
    private Date closeDate;
    @Column
    private Long pastBalance;
    @Column
    private Long totalBalance;
    @Column
    private Long interest;
    @Column
    private Long totalCumulative;
    @Column
    private Long paid;
    @Column
    private Long minimumPayment;
    @Column
    private Date openDate;

    public NuSummaryContract() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NuBillContract getNuBillContract() {
        return nuBillContract;
    }

    public void setNuBillContract(NuBillContract nuBillContract) {
        this.nuBillContract = nuBillContract;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Long getPastBalance() {
        return pastBalance;
    }

    public void setPastBalance(Long pastBalance) {
        this.pastBalance = pastBalance;
    }

    public Long getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Long totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Long getInterest() {
        return interest;
    }

    public void setInterest(Long interest) {
        this.interest = interest;
    }

    public Long getTotalCumulative() {
        return totalCumulative;
    }

    public void setTotalCumulative(Long totalCumulative) {
        this.totalCumulative = totalCumulative;
    }

    public Long getPaid() {
        return paid;
    }

    public void setPaid(Long paid) {
        this.paid = paid;
    }

    public Long getMinimumPayment() {
        return minimumPayment;
    }

    public void setMinimumPayment(Long minimumPayment) {
        this.minimumPayment = minimumPayment;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }
}
