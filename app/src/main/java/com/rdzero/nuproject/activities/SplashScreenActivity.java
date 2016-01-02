package com.rdzero.nuproject.activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
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
import com.rdzero.nuproject.db.NuBillContract;
import com.rdzero.nuproject.db.NuLineItemsContract;
import com.rdzero.nuproject.db.NuLinksContract;
import com.rdzero.nuproject.db.NuSummaryContract;
import com.rdzero.nuproject.net.CustomJSONArrayRequest;
import com.rdzero.nuproject.net.CustomVolleyRequestQueue;
import com.rdzero.nuproject.net.DbHelper;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends Activity implements Response.Listener, Response.ErrorListener {

    private static final String TAG = SplashScreenActivity.class.getName();

    private TextView mTextView;
    private Button mButton;
    private RequestQueue mQueue;
    private ArrayList<NuBillObj> nuBillArrayList;
    private boolean isConnected;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mTextView = (TextView) findViewById(R.id.teste);
        mButton = (Button) findViewById(R.id.button);

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

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
        if(isConnected){
            mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                    .getRequestQueue();

            final CustomJSONArrayRequest jsonRequest = new CustomJSONArrayRequest(Request.Method
                    .GET, getResources().getString(R.string.request_URL),
                    new JSONArray(), this, this);
            jsonRequest.setTag(getResources().getString(R.string.REQUEST_TAG));

            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mQueue.add(jsonRequest);
                }
            });
        } else {
            Log.d(TAG,"NOT CONNECTED");
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(getResources().getString(R.string.REQUEST_TAG));
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

        Type listType = new TypeToken<ArrayList<NuBillObj>>() {
        }.getType();
        nuBillArrayList = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create().fromJson(response.toString(), listType);

        mTextView.setText("GOT THE JSON");

        DbHelper dbHelper = new DbHelper();

        if (dbHelper.saveJsonResult(nuBillArrayList))
            Log.d(TAG, "Saved to DB correctly");

        List<NuBillContract> nuBillContracts = dbHelper.getBillList();
        for (NuBillContract bill : nuBillContracts) {
            NuSummaryContract nuSummaryContract = dbHelper.getSummaryContractFromBill(bill.getId());
            NuLinksContract nuLinksContract = dbHelper.getLinksContractFromBill(bill.getId());
            List<NuLineItemsContract> nuLineItemsContracts = dbHelper.getLineItemsContractList(bill.getId());
        }
    }

}
