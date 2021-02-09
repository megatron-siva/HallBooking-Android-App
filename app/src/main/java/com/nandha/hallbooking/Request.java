package com.nandha.hallbooking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nandha.hallbooking.Login.category;
import static com.nandha.hallbooking.Login.ip;
import static com.nandha.hallbooking.Login.mailid;

public class Request extends AppCompatActivity {
    private ArrayList<ExampleItem> mExampleList;
    private static Request instance;
    private RecyclerView mRecyclerView;
    private ExampleAdapterRequest mAdapter;
    private List<String> req;
    String id,decision;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;
    TextView top;
    JSONArray user_name,user_dept,user_clg,hall_name,function_nature,date,start_time,end_time,additional_requirements,user_mobile,request_id,user_designation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        top=findViewById(R.id.textView);
        top.setText("REQUESTS");
        instance=this;
        String URLline=ip+"/requests";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mailid",mailid);
        params.put("category",category);

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.POST, URLline,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                             user_name=response.getJSONArray("user_name");
                             user_dept=response.getJSONArray("user_dept");
                             user_clg=response.getJSONArray("user_clg");
                             hall_name=response.getJSONArray("hall_name");
                             function_nature=response.getJSONArray("function_nature");
                             date=response.getJSONArray("date");
                             start_time=response.getJSONArray("start_time");
                             end_time=response.getJSONArray("end_time");
                             additional_requirements=response.getJSONArray("additional_requirements");
                             user_mobile=response.getJSONArray("user_mobile");
                             request_id=response.getJSONArray("request_id");
                             user_designation=response.getJSONArray("user_designation");
                            createExampleList();
                            buildRecyclerView();
                            setButtons();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Request.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
    public void insertItem(int position) {
        mAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text) {
        mExampleList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }

    public void createExampleList() {

        mExampleList= new ArrayList<>();
        req=new ArrayList<>();
        try {
            for (int i = 0; i < user_name.length(); i++) {
                req.add(request_id.getString(i));
                mExampleList.add(new ExampleItem(R.drawable.ic_person_outline_black_24dp,
                        user_name.getString(i) +"("+user_designation.getString(i)+")",
                        user_dept.getString(i),
                        user_clg.getString(i),
                        hall_name.getString(i),
                        function_nature.getString(i),
                        date.getString(i),
                        start_time.getString(i)+"-"+end_time.getString(i),
                        additional_requirements.getString(i),user_mobile.getString(i),"No"));

                        }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }}



    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapterRequest(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapterRequest.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // changeItem(position, "Clicked");
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }
    public void request(final String position,final ExampleAdapterRequest.OnItemClickListener listener){
        String URLline=ip+"/decision";
        Map<String, String> params = new HashMap<String, String>();
        params.put("request_id",id);
        params.put("decision",decision);
        params.put("category",category);

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.POST, URLline,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String text=response.getString("text");
                            Toast.makeText(Request.this,text,Toast.LENGTH_LONG).show();
                            if(!text.equals("Already booked in this time")){
                            req.remove(id);
                            listener.onDeleteClick(Integer.parseInt(position));}


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Request.this,error.toString(),Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);}

    public void setButtons() {
        buttonInsert = findViewById(R.id.button_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem(0);
            }
        });


    }
    public static Request getInstance() {
        return instance;
    }

    public void RejectMethod(final String position,final ExampleAdapterRequest.OnItemClickListener listener) {
       /*Dialog  mview = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mview.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mview.setCancelable(true);
        mview.setContentView(R.layout.layout_dialog);
        TextView tv=(TextView)mview.findViewById(R.id.poptv);
        tv.setText("Do you want to reject the request?");
        Button ok = (Button) mview.findViewById(R.id.ok);
        Button cancel = (Button) mview.findViewById(R.id.cancel);*/
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(Request.this);
        final View mview = getLayoutInflater().inflate(R.layout.layout_dialog, null);
        TextView tv=(TextView)mview.findViewById(R.id.poptv);
        tv.setText("Do you want to accept the request?");
        Button ok = (Button) mview.findViewById(R.id.ok);
        Button cancel = (Button) mview.findViewById(R.id.cancel);
        mbuilder.setView(mview);


        final AlertDialog dialog = mbuilder.create();
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=Integer.parseInt(position);
                id=req.get(pos);
                decision="no";
                request(position,listener);
                dialog.cancel();


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });
    }
    public void AcceptMethod(final String position,final ExampleAdapterRequest.OnItemClickListener listener) {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(Request.this);
        final View mview = getLayoutInflater().inflate(R.layout.layout_dialog, null);
        TextView tv=(TextView)mview.findViewById(R.id.poptv);
        tv.setText("Do you want to accept the request?");
        Button ok = (Button) mview.findViewById(R.id.ok);
        Button cancel = (Button) mview.findViewById(R.id.cancel);
        mbuilder.setView(mview);


        final AlertDialog dialog = mbuilder.create();
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=Integer.parseInt(position);
                id=req.get(pos);
                decision="yes";
                request(position,listener);
                dialog.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


}}
