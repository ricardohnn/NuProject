package com.rdzero.nuproject.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@ModelContainer
@Table(database = NuProjDatabase.class)
public class NuBillContract extends BaseModel{

    @Column
    @PrimaryKey(autoincrement = false)
    String id;

    @Column
    private String state;
    @Column
    private String barcode;
    @Column
    private String linhaDigitavel;

    List<NuLineItemsContract> nuLineItemsContractList;

    public NuBillContract() {
    }

    public NuBillContract(String id, String state, String barcode, String linhaDigitavel) {
        this.id = id;
        this.state = state;
        this.barcode = barcode;
        this.linhaDigitavel = linhaDigitavel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE}, variableName = "nuLineItemsContractList")
    public List<NuLineItemsContract> getNuLineItems() {
        if (nuLineItemsContractList == null || nuLineItemsContractList.isEmpty()) {
            nuLineItemsContractList =
                    SQLite.select()
                    .from(NuLineItemsContract.class)
                    .where(NuLineItemsContract_Table.nuBillForeignKeyContainer_id.eq(id))
                    .queryList();
        }
        return nuLineItemsContractList;
    }
}
