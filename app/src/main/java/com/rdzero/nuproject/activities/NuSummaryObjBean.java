package com.rdzero.nuproject.activities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NuSummaryObjBean {

    @SerializedName("due_date")
    private Date due_date;
    @SerializedName("close_date")
    private Date close_date;
    @SerializedName("past_balance")
    private Long past_balance;
    @SerializedName("total_balance")
    private Long total_balance;
    @SerializedName("interest")
    private Long interest;
    @SerializedName("total_cumulative")
    private Long total_cumulative;
    @SerializedName("paid")
    private Long paid;
    @SerializedName("minimum_payment")
    private Long minimum_payment;
    @SerializedName("open_date")
    private Date open_date;

    public NuSummaryObjBean(Date due_date, Date close_date, Long past_balance, Long total_balance, Long interest, Long total_cumulative, Long paid, Long minimum_payment, Date open_date) {
        this.due_date = due_date;
        this.close_date = close_date;
        this.past_balance = past_balance;
        this.total_balance = total_balance;
        this.interest = interest;
        this.total_cumulative = total_cumulative;
        this.paid = paid;
        this.minimum_payment = minimum_payment;
        this.open_date = open_date;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Date getClose_date() {
        return close_date;
    }

    public void setClose_date(Date close_date) {
        this.close_date = close_date;
    }

    public Long getPast_balance() {
        return past_balance;
    }

    public void setPast_balance(Long past_balance) {
        this.past_balance = past_balance;
    }

    public Long getTotal_balance() {
        return total_balance;
    }
    public void setTotal_balance(Long total_balance) {
        this.total_balance = total_balance;
    }

    public Long getInterest() {
        return interest;
    }

    public void setInterest(Long interest) {
        this.interest = interest;
    }

    public Long getTotal_cumulative() {
        return total_cumulative;
    }

    public void setTotal_cumulative(Long total_cumulative) {
        this.total_cumulative = total_cumulative;
    }

    public Long getPaid() {
        return paid;
    }

    public void setPaid(Long paid) {
        this.paid = paid;
    }

    public Long getMinimum_payment() {
        return minimum_payment;
    }

    public void setMinimum_payment(Long minimum_payment) {
        this.minimum_payment = minimum_payment;
    }

    public Date getOpen_date() {
        return open_date;
    }

    public void setOpen_date(Date open_date) {
        this.open_date = open_date;
    }

}
