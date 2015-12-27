package com.rdzero.nuproject.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rdzero.nuproject.R;
import com.rdzero.nuproject.beans.NuBillObj;
import com.rdzero.nuproject.beans.NuLineItemsObjBean;
import com.rdzero.nuproject.db.NuBillContract;
import com.rdzero.nuproject.db.NuLineItemsContract;
import com.rdzero.nuproject.db.NuLinksContract;
import com.rdzero.nuproject.db.NuSummaryContract;
import com.rdzero.nuproject.net.CustomJSONArrayRequest;
import com.rdzero.nuproject.net.CustomVolleyRequestQueue;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SplashScreenActivity extends Activity implements Response.Listener, Response.ErrorListener  {

    public static final String REQUEST_TAG = "MainVolleyActivity";
    private TextView mTextView;
    private Button mButton;
    private String url = "https://s3-sa-east-1.amazonaws.com/mobile-challenge/bill/bill_new.json";
    private RequestQueue mQueue;
    private ArrayList<NuBillObj> nuBillArrayList;
    private StringBuffer postList;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mTextView = (TextView) findViewById(R.id.teste);
        mButton = (Button) findViewById(R.id.button);
//
//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
//                startActivity(i);
//
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

        final CustomJSONArrayRequest jsonRequest = new CustomJSONArrayRequest(Request.Method
                .GET, url,
                new JSONArray(), this, this);
        jsonRequest.setTag(REQUEST_TAG);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.add(jsonRequest);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mTextView.setText(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {

        Type listType = new TypeToken<ArrayList<NuBillObj>>(){}.getType();
        nuBillArrayList = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create().fromJson(response.toString(), listType);
        for(NuBillObj billObj : nuBillArrayList){
            String self;
            String boleto_email;
            String barcode;

            if(billObj.getBill().getLinks().getSelf() != null)
                self = billObj.getBill().getLinks().getSelf().getHref();
            else
                self = "";

            if(billObj.getBill().getLinks().getBoletoEmail() != null)
                boleto_email = billObj.getBill().getLinks().getBoletoEmail().getHref();
            else
                boleto_email = "";

            if(billObj.getBill().getLinks().getBarcode() != null)
                barcode = billObj.getBill().getLinks().getBarcode().getHref();
            else
                barcode = "";

            NuBillContract nuBillContract = new NuBillContract(
                    billObj.getBill().getId(),
                    billObj.getBill().getState(),
                    billObj.getBill().getBarcode(),
                    billObj.getBill().getLinhaDigitavel()
            );
            nuBillContract.save();

            NuSummaryContract nuSummaryContract = new NuSummaryContract(
                    nuBillContract,
                    billObj.getBill().getSummary().getDueDate(),
                    billObj.getBill().getSummary().getCloseDate(),
                    billObj.getBill().getSummary().getPastBalance(),
                    billObj.getBill().getSummary().getTotalBalance(),
                    billObj.getBill().getSummary().getInterest(),
                    billObj.getBill().getSummary().getTotalCumulative(),
                    billObj.getBill().getSummary().getPaid(),
                    billObj.getBill().getSummary().getMinimumPayment(),
                    billObj.getBill().getSummary().getOpenDate()
            );
            nuSummaryContract.save();

            NuLinksContract nuLinksContract = new NuLinksContract(
                    nuBillContract,
                    self,
                    boleto_email,
                    barcode
            );

            nuLinksContract.save();

            for(NuLineItemsObjBean nuLineItems: billObj.getBill().getLineItems()){
                NuLineItemsContract nuLineItemsContract = new NuLineItemsContract(
                        nuLineItems.getPostDate(),
                        nuLineItems.getAmount(),
                        nuLineItems.getTitle(),
                        nuLineItems.getIndex(),
                        nuLineItems.getCharges(),
                        nuLineItems.getHref()
                );
                nuLineItemsContract.associateNuBill(nuBillContract);
                nuLineItemsContract.save();
                nuBillContract.save();
            }
        }

        postList=new StringBuffer();
        for(NuBillObj post: nuBillArrayList){
            String selfHref;
            String boleto_emailHref;
            String barcodeHref;

            if(post.getBill().getLinks().getSelf() != null)
                selfHref = post.getBill().getLinks().getSelf().getHref();
            else
                selfHref = "";

            if(post.getBill().getLinks().getBoletoEmail() != null)
                boleto_emailHref = post.getBill().getLinks().getBoletoEmail().getHref();
            else
                boleto_emailHref = "";

            if(post.getBill().getLinks().getBarcode() != null)
                barcodeHref = post.getBill().getLinks().getBarcode().getHref();
            else
                barcodeHref = "";


            postList.append("bill:\n" +
                            "\tstate: " + post.getBill().getState() + "\n" +
                            "\tid: " + post.getBill().getId() + "\n" +
                            "\tsummary:\n" +
                            "\t\tdue_date: " + android.text.format.DateFormat.format("yyyy-MM-dd", post.getBill().getSummary().getDueDate()) + "\n" +
                            "\t\tclose_date: " + android.text.format.DateFormat.format("yyyy-MM-dd", post.getBill().getSummary().getCloseDate()) + "\n" +
                            "\t\tpast_balance: " + post.getBill().getSummary().getPastBalance() + "\n" +
                            "\t\ttotal_balance: " + post.getBill().getSummary().getTotalBalance() + "\n" +
                            "\t\tinterest: " + post.getBill().getSummary().getInterest() + "\n" +
                            "\t\ttotal_cumulative: " + post.getBill().getSummary().getTotalCumulative() + "\n" +
                            "\t\tpaid: " + post.getBill().getSummary().getPaid() + "\n" +
                            "\t\tminimum_payment: " + post.getBill().getSummary().getMinimumPayment() + "\n" +
                            "\t\topen_date: " + android.text.format.DateFormat.format("yyyy-MM-dd", post.getBill().getSummary().getOpenDate()) + "\n" +
                            "\t_links:\n" +
                            "\t\tself:\n" +
                            "\t\t\thref: " + selfHref + "\n" +
                            "\t\tboleto_email:\n" +
                            "\t\t\thref: " + boleto_emailHref + "\n" +
                            "\t\tbarcode:\n" +
                            "\t\t\thref: " + barcodeHref + "\n" +
                            "\tbarcode: " + post.getBill().getBarcode() + "\n" +
                            "\tlinha_digitavel: " + post.getBill().getLinhaDigitavel() + "\n"
            );
            for(NuLineItemsObjBean lineItems: post.getBill().getLineItems()){
                postList.append("\tlineItems:\n" +
                                "\t\tpost_date: " + android.text.format.DateFormat.format("yyyy-MM-dd", lineItems.getPostDate()) + "\n" +
                                "\t\tamount: " + lineItems.getAmount() + "\n" +
                                "\t\ttitle: " + lineItems.getTitle() + "\n" +
                                "\t\tindex: " + lineItems.getIndex() + "\n" +
                                "\t\tcharges: " + lineItems.getCharges() + "\n" +
                                "\t\thref: " + lineItems.getHref() + "\n"
                );
            }
        }

        mTextView.setText(postList);
    }

}
