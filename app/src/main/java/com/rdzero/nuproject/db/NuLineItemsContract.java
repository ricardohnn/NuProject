package com.rdzero.nuproject.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.Date;

@Table(database = NuProjDatabase.class)
public class NuLineItemsContract extends BaseModel{
    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    @ForeignKey(saveForeignKeyModel = false)
    ForeignKeyContainer<NuBillContract> nuBillForeignKeyContainer;

    @Column(typeConverter = DateConverter.class)
    private Date postDate;
    @Column
    private Long amount;
    @Column
    private String title;
    @Column
    private Long index;
    @Column
    private Long charges;
    @Column
    private String href;

    public NuLineItemsContract() {
    }

    public NuLineItemsContract(Date postDate, Long amount, String title, Long index, Long charges, String href) {
        this.postDate = postDate;
        this.amount = amount;
        this.title = title;
        this.index = index;
        this.charges = charges;
        this.href = href;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void associateNuBill(NuBillContract nuBillContract) {
        nuBillForeignKeyContainer = FlowManager.getContainerAdapter(NuBillContract.class).toForeignKeyContainer(nuBillContract);
    }
}