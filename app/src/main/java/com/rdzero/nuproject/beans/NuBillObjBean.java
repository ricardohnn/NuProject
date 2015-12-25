package com.rdzero.nuproject.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NuBillObjBean {

    @SerializedName("state")
    private String state;
    @SerializedName("id")
    private String id;
    @SerializedName("summary")
    private NuSummaryObjBean summary;
    @SerializedName("_links")
    private NuLinksObjBean links;
    @SerializedName("barcode")
    private String barcode;
    @SerializedName("linha_digitavel")
    private String linhaDigitavel;
    @SerializedName("line_items")
    private List<NuLineItemsObjBean> lineItems = new ArrayList<NuLineItemsObjBean>();

    public NuBillObjBean(String state, String id, NuSummaryObjBean summary, NuLinksObjBean links, String barcode, String linhaDigitavel, List<NuLineItemsObjBean> lineItems) {
        this.state = state;
        this.id = id;
        this.summary = summary;
        this.links = links;
        this.barcode = barcode;
        this.linhaDigitavel = linhaDigitavel;
        this.lineItems = lineItems;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NuSummaryObjBean getSummary() {
        return summary;
    }

    public void setSummary(NuSummaryObjBean summary) {
        this.summary = summary;
    }

    public NuLinksObjBean getLinks() {
        return links;
    }

    public void setLinks(NuLinksObjBean links) {
        this.links = links;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getLinhaDigitavel() {
        return linhaDigitavel;
    }

    public void setLinhaDigitavel(String linhaDigitavel) {
        this.linhaDigitavel = linhaDigitavel;
    }

    public List<NuLineItemsObjBean> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<NuLineItemsObjBean> lineItems) {
        this.lineItems = lineItems;
    }

}
