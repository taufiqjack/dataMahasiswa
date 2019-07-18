package com.cahyonoz.mysqlcrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cahyonoz.mysqlcrud.R;
import com.cahyonoz.mysqlcrud.model.ModelMhs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MhsActivity extends AppCompatActivity {

    private EditText editTextNim, editTextNama, editTextAlamat;
    private ModelMhs mhs;

    private String action_flag="add";
    private String refreshFlag="0";
    private static final String TAG="MhsActivity";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataVolley();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mhs = new ModelMhs();
        initUI();

        Intent intent = getIntent();
        if(intent.hasExtra("mhs")){
            mhs = (ModelMhs) intent.getSerializableExtra("mhs");
            Log.d(TAG , "Mahasiswa : " + mhs.toString() );
            setData(mhs);
            action_flag="edit";
            editTextNim.setEnabled(false);
        }
        else {
            mhs = new ModelMhs();
        }
    }

    private  void setData(ModelMhs mhs){
        editTextNim.setText(mhs.getNim());
        editTextNama.setText(mhs.getNama());
        editTextAlamat.setText(mhs.getAlamat());
    }

    private void initUI(){
        pDialog = new ProgressDialog(MhsActivity.this);

        editTextNim = (EditText) findViewById(R.id.editTextNim);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextAlamat = (EditText)findViewById(R.id.editTextAlamat);
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
        if (id == R.id.action_save) {
            saveDataVolley();

            Intent intent = new Intent(MhsActivity.this,MainActivity.class);

            startActivity(intent);
        }
        else{

        }

        return super.onOptionsItemSelected(item);
    }


    public void saveDataVolley(){
        refreshFlag = "1";

        final String nama = editTextNama.getText().toString();
        final String nim = editTextNim.getText().toString();
        final String alamat = editTextAlamat.getText().toString();

        String url = "http://10.0.2.2:8282/kuliah/savedata.php";
        pDialog.setMessage("Save Data Mahasiswa...");
        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        Log.d("MhsActivity", "response :" + response);

                        processResponse("Save Data", response);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String , String> getParams(){
                Map<String , String> params = new HashMap<>();

                params.put("nama",nama);
                params.put("nim", nim);
                params.put("alamat",alamat);
                if(action_flag.equals("add")){
                    params.put("id","0");

                }else
                {
                    params.put("id",mhs.getId());
                }
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);

    }
    private void processResponse(String paction , String response){

        try{
            JSONObject jsonObj = new JSONObject(response);
            String errormsg = jsonObj.getString("errormsg");
            Toast.makeText(getBaseContext(),paction + " " +errormsg,Toast.LENGTH_SHORT).show();

        }catch (JSONException e){
            Log.d("MhsActivity","errorJSON");
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


}
