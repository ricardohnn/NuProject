package com.rdzero.nuproject.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rdzero.nuproject.R;
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
        postList=new StringBuffer();
        for(NuBillObj post: nuBillArrayList){
            String selfHref;
            String boleto_emailHref;
            String barcodeHref;

            if(post.getBill().get_links().getSelf() != null)
                selfHref = post.getBill().get_links().getSelf().getHref();
            else
                selfHref = "";

            if(post.getBill().get_links().getBoleto_email() != null)
                boleto_emailHref = post.getBill().get_links().getBoleto_email().getHref();
            else
                boleto_emailHref = "";

            if(post.getBill().get_links().getBarcode() != null)
                barcodeHref = post.getBill().get_links().getBarcode().getHref();
            else
                barcodeHref = "";


            postList.append("bill:\n" +
                            "\tstate: " + post.getBill().getState() + "\n" +
                            "\tid: " + post.getBill().getId() + "\n" +
                            "\tsummary:\n" +
                            "\t\tdue_date: " + android.text.format.DateFormat.format("yyyy-MM-dd", post.getBill().getSummary().getDue_date()) + "\n" +
                            "\t\tclose_date: " + android.text.format.DateFormat.format("yyyy-MM-dd", post.getBill().getSummary().getClose_date()) + "\n" +
                            "\t\tpast_balance: " + post.getBill().getSummary().getPast_balance() + "\n" +
                            "\t\ttotal_balance: " + post.getBill().getSummary().getTotal_balance() + "\n" +
                            "\t\tinterest: " + post.getBill().getSummary().getInterest() + "\n" +
                            "\t\ttotal_cumulative: " + post.getBill().getSummary().getTotal_cumulative() + "\n" +
                            "\t\tpaid: " + post.getBill().getSummary().getPaid() + "\n" +
                            "\t\tminimum_payment: " + post.getBill().getSummary().getMinimum_payment() + "\n" +
                            "\t\topen_date: " + android.text.format.DateFormat.format("yyyy-MM-dd", post.getBill().getSummary().getOpen_date()) + "\n" +
                            "\t_links:\n" +
                            "\t\tself:\n" +
                            "\t\t\thref: " + selfHref + "\n" +
                            "\t\tboleto_email:\n" +
                            "\t\t\thref: " + boleto_emailHref + "\n" +
                            "\t\tbarcode:\n" +
                            "\t\t\thref: " + barcodeHref + "\n" +
                            "\tbarcode: " + post.getBill().getBarcode() + "\n" +
                            "\tlinha_digitavel: " + post.getBill().getLinha_digitavel() + "\n"
            );
            for(NuLineItemsObjBean lineItems: post.getBill().getLine_items()){
                postList.append("\tline_items:\n" +
                                "\t\tpost_date: " + android.text.format.DateFormat.format("yyyy-MM-dd", lineItems.getPost_date()) + "\n" +
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
