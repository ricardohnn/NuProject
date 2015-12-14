package com.rdzero.nuproject.activities;

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
    private NuLinksObjBean _links;
    @SerializedName("barcode")
    private String barcode;
    @SerializedName("linha_digitavel")
    private String linha_digitavel;
    @SerializedName("line_items")
    public List<NuLineItemsObjBean> line_items = new ArrayList<NuLineItemsObjBean>();

    public NuBillObjBean(String state, String id, NuSummaryObjBean summary, NuLinksObjBean _links, String barcode, String linha_digitavel, List<NuLineItemsObjBean> line_items) {
        this.state = state;
        this.id = id;
        this.summary = summary;
        this._links = _links;
        this.barcode = barcode;
        this.linha_digitavel = linha_digitavel;
        this.line_items = line_items;
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

    public NuLinksObjBean get_links() {
        return _links;
    }

    public void set_links(NuLinksObjBean _links) {
        this._links = _links;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getLinha_digitavel() {
        return linha_digitavel;
    }

    public void setLinha_digitavel(String linha_digitavel) {
        this.linha_digitavel = linha_digitavel;
    }

    public List<NuLineItemsObjBean> getLine_items() {
        return line_items;
    }

    public void setLine_items(List<NuLineItemsObjBean> line_items) {
        this.line_items = line_items;
    }

}
