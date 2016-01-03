package com.rdzero.nuproject.db;

import android.content.Context;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;
import com.rdzero.nuproject.BuildConfig;
import com.rdzero.nuproject.beans.NuBillObj;
import com.rdzero.nuproject.beans.NuLineItemsObjBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricardohnn on 2015-12-27.
 */
public class DbHelper {
    private static final String TAG = DbHelper.class.getName();

    public boolean saveJsonResult(ArrayList<NuBillObj> billObjList){
        if (billObjList != null){
            for(NuBillObj billObj : billObjList){

                NuBillContract nuBillContract = new NuBillContract(
                        billObj.getBill().getId() != null ? billObj.getBill().getId() : "",
                        billObj.getBill().getState() != null ? billObj.getBill().getState() : "",
                        billObj.getBill().getBarcode() != null ? billObj.getBill().getBarcode() : "",
                        billObj.getBill().getLinhaDigitavel() != null ? billObj.getBill().getLinhaDigitavel() : ""
                );
                nuBillContract.save();

                NuSummaryContract nuSummaryContract = new NuSummaryContract(
                        nuBillContract,
                        billObj.getBill().getSummary().getDueDate(),
                        billObj.getBill().getSummary().getCloseDate(),
                        billObj.getBill().getSummary().getPastBalance() != null ? billObj.getBill().getSummary().getPastBalance() : 0,
                        billObj.getBill().getSummary().getTotalBalance() != null ? billObj.getBill().getSummary().getTotalBalance() : 0,
                        billObj.getBill().getSummary().getInterest() != null ? billObj.getBill().getSummary().getInterest() : 0,
                        billObj.getBill().getSummary().getTotalCumulative() != null ? billObj.getBill().getSummary().getTotalCumulative() : 0,
                        billObj.getBill().getSummary().getPaid() != null ? billObj.getBill().getSummary().getPaid() : 0,
                        billObj.getBill().getSummary().getMinimumPayment() != null ? billObj.getBill().getSummary().getMinimumPayment() : 0,
                        billObj.getBill().getSummary().getOpenDate()
                );
                nuSummaryContract.save();

                NuLinksContract nuLinksContract = new NuLinksContract(
                        nuBillContract,
                        billObj.getBill().getLinks().getSelf() != null ? billObj.getBill().getLinks().getSelf().getHref() : "",
                        billObj.getBill().getLinks().getBoletoEmail() != null ? billObj.getBill().getLinks().getBoletoEmail().getHref() : "",
                        billObj.getBill().getLinks().getBarcode() != null ? billObj.getBill().getLinks().getBarcode().getHref() : ""
                );

                nuLinksContract.save();

                for(NuLineItemsObjBean nuLineItems: billObj.getBill().getLineItems()){
                    NuLineItemsContract nuLineItemsContract = new NuLineItemsContract(
                            nuLineItems.getPostDate(),
                            nuLineItems.getAmount() != null ? nuLineItems.getAmount() : 0,
                            nuLineItems.getTitle() != null ? nuLineItems.getTitle() : "",
                            nuLineItems.getIndex() != null ? nuLineItems.getIndex() : 0,
                            nuLineItems.getCharges() != null ? nuLineItems.getCharges() : 0,
                            nuLineItems.getHref() != null ? nuLineItems.getHref() : ""
                    );
                    nuLineItemsContract.associateNuBill(nuBillContract);
                    nuLineItemsContract.save();
                    nuBillContract.save();
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean updateTable(){
        // Native SQL wrapper
        Where<NuBillContract> update = SQLite.update(NuBillContract.class)
                .set(Ant_Table.type.eq("other"))
                .where(Ant_Table.type.is("worker"))
                .and(Ant_Table.isMale.is(true));
        update.queryClose();

// TransactionManager (more methods similar to this one)
        TransactionManager.getInstance().addTransaction(new QueryTransaction(DBTransactionInfo.create(BaseTransaction.PRIORITY_UI), update);
    }

    public void dropTable(Context context){
        context.getApplicationContext().deleteDatabase(NuProjDatabase.NAME + ".db");
    }

    public List<NuBillContract> getBillList(){
        List<NuBillContract> nuBillContracts = SQLite
                .select()
                .from(NuBillContract.class)
                .orderBy(NuBillContract_Table.id, true)
                .queryList();

        if (BuildConfig.DEBUG) {
            for(NuBillContract bill: nuBillContracts){
                Log.d(TAG,
                        "Bill:\n" +
                                "\tbarcode: " + bill.getBarcode() + "\n" +
                                "\tbillId: " + bill.getBillId() + "\n" +
                                "\tlinha digitavel: " + bill.getLinhaDigitavel() + "\n" +
                                "\tstate: " + bill.getState() + "\n");
            }
        }

        return nuBillContracts;
    }

    public NuSummaryContract getSummaryContractFromBill (long id){
        NuSummaryContract nuSummaryContract = SQLite
                .select()
                .from(NuSummaryContract.class)
                .where(NuSummaryContract_Table.nuBillContract_id.eq(id))
                .querySingle();

        if (BuildConfig.DEBUG) {
            Log.d(TAG,
                    "Summary:\n" +
                            "\tdue_date: " + android.text.format.DateFormat.format("yyyy-MM-dd", nuSummaryContract.getDueDate()) + "\n" +
                            "\tclose_date: " + android.text.format.DateFormat.format("yyyy-MM-dd", nuSummaryContract.getCloseDate()) + "\n" +
                            "\tpast_balance: " + nuSummaryContract.getPastBalance() + "\n" +
                            "\ttotal_balance: " + nuSummaryContract.getTotalBalance() + "\n" +
                            "\tinterest: " + nuSummaryContract.getInterest() + "\n" +
                            "\ttotal_cumulative: " + nuSummaryContract.getTotalCumulative() + "\n" +
                            "\tpaid: " + nuSummaryContract.getPaid() + "\n" +
                            "\tminimum_payment: " + nuSummaryContract.getMinimumPayment() + "\n" +
                            "\topen_date: " + android.text.format.DateFormat.format("yyyy-MM-dd", nuSummaryContract.getOpenDate()) + "\n");
        }

        return nuSummaryContract;
    }

    public NuLinksContract getLinksContractFromBill (long id){
        NuLinksContract nuLinksContract = SQLite
                .select()
                .from(NuLinksContract.class)
                .where(NuLinksContract_Table.nuBillContract_id.eq(id))
                .querySingle();

        if (BuildConfig.DEBUG) {
            Log.d(TAG,
                    "_links:\n" +
                            "\tself href: " + nuLinksContract.getSelf() + "\n" +
                            "\tboleto_email href: " + nuLinksContract.getBoletoEmail() + "\n" +
                            "\tbarcode href: " + nuLinksContract.getBarcode() + "\n");
        }

        return nuLinksContract;
    }

    public List<NuLineItemsContract> getLineItemsContractList(long id){
        List<NuLineItemsContract> nuLineItemsContracts = SQLite
                .select()
                .from(NuLineItemsContract.class)
                .where(NuLineItemsContract_Table.nuBillForeignKeyContainer_id.eq(id))
                .queryList();

        if (BuildConfig.DEBUG) {
            for(NuLineItemsContract nuLine: nuLineItemsContracts){
                Log.d(TAG,
                        "LineItems:\n" +
                                "\tpost_date: " + android.text.format.DateFormat.format("yyyy-MM-dd", nuLine.getPostDate()) + "\n" +
                                "\tamount: " + nuLine.getAmount() + "\n" +
                                "\ttitle: " + nuLine.getTitle() + "\n" +
                                "\tindex: " + nuLine.getIndex() + "\n" +
                                "\tcharges: " + nuLine.getCharges() + "\n" +
                                "\thref: " + nuLine.getHref() + "\n");
            }
        }
        return nuLineItemsContracts;
    }
}
