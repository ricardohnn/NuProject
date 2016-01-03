package com.rdzero.nuproject.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rdzero.nuproject.BuildConfig;
import com.rdzero.nuproject.R;
import com.rdzero.nuproject.beans.NuBillObj;
import com.rdzero.nuproject.db.DbHelper;
import com.rdzero.nuproject.net.CustomJSONArrayRequest;
import com.rdzero.nuproject.net.CustomVolleyRequestQueue;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SplashScreenActivity extends Activity implements Response.Listener, Response.ErrorListener {

    private static final String TAG = SplashScreenActivity.class.getName();

    public static final String PREF = "NuPrefs" ;
    private static final String LAST_UPDATE = "LastUpdate";

    private SharedPreferences sharedpreferences;
    private DateTime today;
    private Period period;
    private TextView mTextView;
    private RequestQueue mQueue;
    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mTextView = (TextView) findViewById(R.id.teste);

        sharedpreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);

        today = new DateTime();
        DateTime lastUpdate = DateTime.parse(sharedpreferences.getString(LAST_UPDATE, today.toString()));
        period = new Period(today,lastUpdate);

        if(BuildConfig.DEBUG) {
            Log.d(TAG, "today : " + today.toString() + "\n" +
                    "shared : " + lastUpdate.toString() + "\n" +
                    "period : " + period.normalizedStandard(PeriodType.days()).getDays() + "\n");
        }

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(isConnected){
            if(!sharedpreferences.contains(LAST_UPDATE) || period.normalizedStandard(PeriodType.days()).getDays() > 10){
                mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                        .getRequestQueue();

                final CustomJSONArrayRequest jsonRequest = new CustomJSONArrayRequest(Request.Method
                        .GET, getResources().getString(R.string.request_URL),
                        new JSONArray(), this, this);
                jsonRequest.setTag(getResources().getString(R.string.REQUEST_TAG));
                mQueue.add(jsonRequest);
            } else{
                //TODO Some better policy for fetching should be done, but for now, since the json won't be changing, do not refetch
                Log.d(TAG,"NOT REFETCH THE JSON LESS THAN 10 DAYS : " + period.normalizedStandard(PeriodType.days()).getDays());
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        } else {
            //TODO Add message for without connection
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
        ArrayList<NuBillObj> nuBillArrayList = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd")
                .create()
                .fromJson(response.toString(), listType);

        mTextView.setText("GOT THE JSON");

        DbHelper dbHelper = new DbHelper();

        //TODO Only for testing purposes, i will drop the db to recreate it since i won't be creating an update policy
        dbHelper.dropTable(this);

        if (dbHelper.saveJsonResult(nuBillArrayList)){
            Log.d(TAG, "Saved to DB correctly");
            sharedpreferences.edit().putString(LAST_UPDATE,today.toString()).apply();
        }

        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);

//        List<NuBillContract> nuBillContracts = dbHelper.getBillList();
//        for (NuBillContract bill : nuBillContracts) {
//            NuSummaryContract nuSummaryContract = dbHelper.getSummaryContractFromBill(bill.getId());
//            NuLinksContract nuLinksContract = dbHelper.getLinksContractFromBill(bill.getId());
//            List<NuLineItemsContract> nuLineItemsContracts = dbHelper.getLineItemsContractList(bill.getId());
//        }
    }

}
