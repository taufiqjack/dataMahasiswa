package com.cahyonoz.mysqlcrud;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cahyonoz.mysqlcrud.adapter.AdapterMhs;
import com.cahyonoz.mysqlcrud.model.ModelMhs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterMhs rvAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context = MainActivity.this;
    private static final int REQUEST_CODE_ADD =1;
    private static final int REQUEST_CODE_EDIT=2;
    private List<ModelMhs> mhsList = new ArrayList<ModelMhs>();

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , MhsActivity.class);
                startActivityForResult(intent , REQUEST_CODE_ADD);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view); //my_recycler_view
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        pDialog = new ProgressDialog(this);
        loadDataServerVolley();
    }

    private void gambarDatakeRecyclerView(){
        rvAdapter = new AdapterMhs(mhsList);
        mRecyclerView.setAdapter(rvAdapter);
        mRecyclerView.addOnItemTouchListener(

                new RecyclerItemListener(context, new RecyclerItemListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ModelMhs mhs = rvAdapter.getItem(position);
                        Intent intent = new Intent(MainActivity.this , MhsActivity.class);
                        //intent.putExtra("mhs", mhs);
                        //intent.putExtra("mhs", mhs);
                        intent.putExtra("mhs",mhs);
                        startActivityForResult(intent , REQUEST_CODE_EDIT);
                    }
                })
        );
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , Intent data){
        super.onActivityResult(requestCode , resultCode,data);
        switch (requestCode){
            case REQUEST_CODE_ADD:{
                if(resultCode == RESULT_OK && null != data){
                    if(data.getStringExtra("refreshflag").equals("1")){
                        loadDataServerVolley();
                    }
                }
                break;
            }
            case REQUEST_CODE_EDIT:{
                if(resultCode == RESULT_OK && null != data){
                    if(data.getStringExtra("refreshflag").equals("1")){
                        loadDataServerVolley();
                    }
                }
                break;
            }
        }
    }
    private void loadDataServerVolley(){
        String url = "http://192.168.43.4:8282/kuliah/listdata.php";
        pDialog.setMessage("Retieve Data mhs...");
        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("MainActivity", "response:" + response);
                        hideDialog();
                        processReponse(response);
                        gambarDatakeRecyclerView();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String , String> getParams(){
                Map<String , String > params = new HashMap<>();

                //the POST parameters;
                //params.put("id","1");
                return  params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private  void processReponse(String response){
        try{
            JSONObject jsonObj = new JSONObject(response);
            JSONArray jsonArray = jsonObj.getJSONArray("data");
            Log.d("TAG" , "data length : " + jsonArray.length());
            ModelMhs objectmhs =null;
            mhsList.clear();
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                objectmhs = new ModelMhs();
                objectmhs.setId(obj.getString("id"));
                objectmhs.setNama(obj.getString("nama"));
                objectmhs.setNim(obj.getString("nim"));
                objectmhs.setAlamat(obj.getString("alamat"));

                mhsList.add(objectmhs);
            }
        }catch (JSONException e){
            Log.d("MainActivity ", "errorJSON");
        }
    }

    private  void showDialog(){
        if(!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog(){
        if(pDialog.isShowing())
            pDialog.dismiss();
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
//        if (id == R.id.action_save) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
