package com.rdzero.nuproject.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.rdzero.nuproject.R;
import com.rdzero.nuproject.net.CustomJSONObjectRequest;
import com.rdzero.nuproject.net.CustomVolleyRequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivityold extends Activity implements Response.Listener, Response.ErrorListener {

    public static final String REQUEST_TAG = "MainVolleyActivity";

    private String url = "http://my-json-feed";
    private TextView mTextView;
    private Button mButton;
    private RequestQueue mQueue;
    private ArrayList<NNTaxiBean> nnTaxiBeanPostArrayList;
    private StringBuffer postList;
    private ETaxiObjBean eTaxiObjBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.teste);
        mButton = (Button) findViewById(R.id.button);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        //For NNTaxi
//        String url = "https://api.99taxis.com/lastLocations?sw=-23.612474,-46.702746&ne=-23.589548,-46.673392";
//        final CustomJSONArrayRequest jsonRequest = new CustomJSONArrayRequest(Request.Method
//                .GET, url,
//                new JSONArray(), this, this);

        //For EZTaxi
        String url = "http://pastebin.com/raw.php?i=M9e1vpTd";
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url,
                new JSONObject(), this, this);
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
    public void onErrorResponse(VolleyError error) {
        mTextView.setText(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {

        //For NNTaxi
//        Type listType = new TypeToken<ArrayList<NNTaxiBean>>(){}.getType();
//        nnTaxiBeanPostArrayList = new GsonBuilder().create().fromJson(response.toString(), listType);
//        postList=new StringBuffer();
//        for(NNTaxiBean post: nnTaxiBeanPostArrayList){
//            postList.append("\n latitude: " + post.getLatitude().toString() + "\n longitude: " + post.getLongitude().toString() + "\n driverId: "
//                    + String.valueOf(post.getDriverId()) + "\n description: " + post.getDriverAvailable() + "\n\n");
//        }

        //For EZTaxi
        eTaxiObjBean = (ETaxiObjBean) new GsonBuilder().create().fromJson(response.toString(), ETaxiObjBean.class);
        postList=new StringBuffer();
        for(ETaxiBean post: eTaxiObjBean.getFavorite()){
            postList.append("\n latitude: " + post.getLatitude().toString() + "\n longitude: " + post.getLongitude().toString() + "\n name: "
                    + post.getname() + "\n\n");
        }
        mTextView.setText(postList);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
