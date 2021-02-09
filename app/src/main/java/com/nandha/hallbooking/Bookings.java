package com.nandha.hallbooking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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

public class Bookings extends AppCompatActivity {
    private ArrayList<ExampleItem> mExampleList;
    private static Bookings instance;
    private RecyclerView mRecyclerView;
    private ExampleAdapterBookings mAdapter;
    private List<String> req,cat;
    String id,cancel_category;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;
    TextView top;
    JSONArray rcurrent_stage,rtotal_stage,ruser_name,ruser_dept,ruser_clg,rhall_name,rfunction_nature,rdate,rstart_time,rend_time,radditional_requirements,ruser_mobile,rrequest_id,ruser_designation;
    JSONArray buser_name,buser_dept,buser_clg,bhall_name,bfunction_nature,bdate,bstart_time,bend_time,badditional_requirements,buser_mobile,bbooking_id,buser_designation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        top=findViewById(R.id.textView);
        top.setText("MY BOOKINGS");
        instance=this;
        String URLline=ip+"/mybookings";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mailid",mailid);
        params.put("category",category);

        JSONObject jsonObj = new JSONObject(params);

        final JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.POST, URLline,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            ruser_name=response.getJSONObject("request").getJSONArray("user_name");
                            ruser_dept=response.getJSONObject("request").getJSONArray("user_dept");
                            ruser_clg=response.getJSONObject("request").getJSONArray("user_clg");
                            rhall_name=response.getJSONObject("request").getJSONArray("hall_name");
                            rfunction_nature=response.getJSONObject("request").getJSONArray("function_nature");
                            rdate=response.getJSONObject("request").getJSONArray("date");
                            rstart_time=response.getJSONObject("request").getJSONArray("start_time");
                            rend_time=response.getJSONObject("request").getJSONArray("end_time");
                            radditional_requirements=response.getJSONObject("request").getJSONArray("additional_requirements");
                            ruser_mobile=response.getJSONObject("request").getJSONArray("user_mobile");
                            rrequest_id=response.getJSONObject("request").getJSONArray("request_id");
                            ruser_designation=response.getJSONObject("request").getJSONArray("user_designation");
                            rcurrent_stage=response.getJSONObject("request").getJSONArray("current_stage");
                            rtotal_stage=response.getJSONObject("request").getJSONArray("total_stage");
                            buser_name=response.getJSONObject("booked").getJSONArray("user_name");
                            buser_dept=response.getJSONObject("booked").getJSONArray("user_dept");
                            buser_clg=response.getJSONObject("booked").getJSONArray("user_clg");
                            bhall_name=response.getJSONObject("booked").getJSONArray("hall_name");
                            bfunction_nature=response.getJSONObject("booked").getJSONArray("function_nature");
                            bdate=response.getJSONObject("booked").getJSONArray("date");
                            bstart_time=response.getJSONObject("booked").getJSONArray("start_time");
                            bend_time=response.getJSONObject("booked").getJSONArray("end_time");
                            badditional_requirements=response.getJSONObject("booked").getJSONArray("additional_requirements");
                            buser_mobile=response.getJSONObject("booked").getJSONArray("user_mobile");
                            bbooking_id=response.getJSONObject("booked").getJSONArray("booking_id");
                            buser_designation=response.getJSONObject("booked").getJSONArray("user_designation");
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
                Toast.makeText(Bookings.this,error.toString(),Toast.LENGTH_LONG).show();
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
        cat=new ArrayList<>();
        try {
            for (int i = 0; i < buser_name.length(); i++) {
                cat.add("booked");
                req.add(bbooking_id.getString(i));
                mExampleList.add(new ExampleItem(R.drawable.ic_person_outline_black_24dp,
                        buser_name.getString(i) +"("+buser_designation.getString(i)+")",
                        buser_dept.getString(i),
                        buser_clg.getString(i),
                        bhall_name.getString(i),
                        bfunction_nature.getString(i),
                        bdate.getString(i),
                        bstart_time.getString(i)+"-"+bend_time.getString(i),
                        badditional_requirements.getString(i),buser_mobile.getString(i),"booked"));


            }
            for (int i = 0; i < ruser_name.length(); i++) {
                cat.add("request");
                req.add(rrequest_id.getString(i));
                mExampleList.add(new ExampleItem(R.drawable.ic_person_outline_black_24dp,
                        ruser_name.getString(i) +"("+ruser_designation.getString(i)+")",
                        ruser_dept.getString(i),
                        ruser_clg.getString(i),
                        rhall_name.getString(i),
                        rfunction_nature.getString(i),
                        rdate.getString(i),
                        rstart_time.getString(i)+"-"+rend_time.getString(i),
                        radditional_requirements.getString(i),ruser_mobile.getString(i),rcurrent_stage.getString(i)));

            }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }}



    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapterBookings(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapterBookings.OnItemClickListener() {
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
    public void request(final String position,final ExampleAdapterBookings.OnItemClickListener listener){
        String URLline=ip+"/cancel";
        Map<String, String> params = new HashMap<String, String>();
        params.put("id",id);
        params.put("cancel_category",cancel_category);

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.POST, URLline,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String text=response.getString("text");
                            Toast.makeText(Bookings.this,text,Toast.LENGTH_LONG).show();
                            cat.remove(cancel_category);
                            req.remove(id);
                            listener.onDeleteClick(Integer.parseInt(position));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Bookings.this,error.toString(),Toast.LENGTH_LONG).show();

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
    public static Bookings getInstance() {
        return instance;
    }

    public void RejectMethod(final String position,final ExampleAdapterBookings.OnItemClickListener listener) {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(Bookings.this);
        final View mview = getLayoutInflater().inflate(R.layout.layout_dialog, null);
        TextView tv=(TextView)mview.findViewById(R.id.poptv);
        tv.setText("Do you want to cancel the request?");
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
                cancel_category=cat.get(pos);
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
    public void AcceptMethod(final String position,final ExampleAdapterBookings.OnItemClickListener listener) {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(Bookings.this);
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
                cancel_category=cat.get(pos);
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
