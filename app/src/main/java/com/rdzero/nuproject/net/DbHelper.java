package com.rdzero.nuproject.net;

import com.rdzero.nuproject.beans.NuBillObj;
import com.rdzero.nuproject.beans.NuLineItemsObjBean;
import com.rdzero.nuproject.db.NuBillContract;
import com.rdzero.nuproject.db.NuLineItemsContract;
import com.rdzero.nuproject.db.NuLinksContract;
import com.rdzero.nuproject.db.NuSummaryContract;

import java.util.ArrayList;

/**
 * Created by ricardohnn on 2015-12-27.
 */
public class DbHelper {

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
}
