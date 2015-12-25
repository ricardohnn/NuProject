package com.rdzero.nuproject.beans;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NuSummaryObjBean {

    @SerializedName("due_date")
    private Date dueDate;
    @SerializedName("close_date")
    private Date closeDate;
    @SerializedName("past_balance")
    private Long pastBalance;
    @SerializedName("total_balance")
    private Long totalBalance;
    @SerializedName("interest")
    private Long interest;
    @SerializedName("total_cumulative")
    private Long totalCumulative;
    @SerializedName("paid")
    private Long paid;
    @SerializedName("minimum_payment")
    private Long minimumPayment;
    @SerializedName("open_date")
    private Date openDate;

    public NuSummaryObjBean(Date dueDate, Date closeDate, Long pastBalance, Long totalBalance, Long interest, Long totalCumulative, Long paid, Long minimumPayment, Date openDate) {
        this.dueDate = dueDate;
        this.closeDate = closeDate;
        this.pastBalance = pastBalance;
        this.totalBalance = totalBalance;
        this.interest = interest;
        this.totalCumulative = totalCumulative;
        this.paid = paid;
        this.minimumPayment = minimumPayment;
        this.openDate = openDate;
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
