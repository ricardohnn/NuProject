package com.rdzero.nuproject.beans;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NuLineItemsObjBean {

    @SerializedName("post_date")
    private Date postDate;
    @SerializedName("amount")
    private Long amount;
    @SerializedName("title")
    private String title;
    @SerializedName("index")
    private Long index;
    @SerializedName("charges")
    private Long charges;
    @SerializedName("href")
    private String href;

    public NuLineItemsObjBean(Date postDate, Long amount, String title, Long index, Long charges, String href) {
        this.postDate = postDate;
        this.amount = amount;
        this.title = title;
        this.index = index;
        this.charges = charges;
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getCharges() {
        return charges;
    }

    public void setCharges(Long charges) {
        this.charges = charges;
    }

}