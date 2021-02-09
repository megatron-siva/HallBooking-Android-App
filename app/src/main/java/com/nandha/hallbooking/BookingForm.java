package com.nandha.hallbooking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.nandha.hallbooking.HallSelecting.hall_id;
import static com.nandha.hallbooking.HallSelecting.hall_name;
import static com.nandha.hallbooking.HallSelecting.sday;
import static com.nandha.hallbooking.HallSelecting.smonth;
import static com.nandha.hallbooking.HallSelecting.syear;
import static com.nandha.hallbooking.Login.category;
import static com.nandha.hallbooking.Login.ip;
import static com.nandha.hallbooking.Login.mailid;

public class BookingForm extends AppCompatActivity implements TimePickerFragment.TimePickerListener {
    private CheckBox extensioncheck, generatorcheck, waterbottlecheck, accheck, projectorcheck;
    public EditText extensionEt, bottleEt, functionname, mobile;
    public TextView start,end;
    //for storing values in String ,which are get from edittext
    public String Showstart, Showend, Requirements, Functionname, phone;
    public RadioGroup genratorgroup;
    public RadioButton onlyNeeded, fullday;
    private Boolean a;
    int s_hour, e_hour, s_minute, e_minute;
    public static String result = "null";
    TextView h_name;
    DialogFragment timePickerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);
        extensioncheck = findViewById(R.id.extensioncheck);
        generatorcheck = findViewById(R.id.generatorcheck);
        waterbottlecheck = findViewById(R.id.warerbottlecheck);
        h_name=findViewById(R.id.textView8);
        h_name.setText(hall_name);
        extensionEt = findViewById(R.id.extensionEt);
        extensionEt.setVisibility(View.GONE);
        bottleEt = findViewById(R.id.bottleEt);
        bottleEt.setVisibility(View.GONE);
        genratorgroup = findViewById(R.id.generatorradio);
        genratorgroup.setVisibility(View.GONE);
        waterbottlecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (waterbottlecheck.isChecked()) {
                    bottleEt.setVisibility(View.VISIBLE);
                } else if (!waterbottlecheck.isChecked()) {
                    bottleEt.setVisibility(View.GONE);
                }
            }
        });
        extensioncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (extensioncheck.isChecked()) {
                    extensionEt.setVisibility(View.VISIBLE);
                } else if (!extensioncheck.isChecked()) {
                    extensionEt.setVisibility(View.GONE);
                }
            }
        });
        generatorcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (generatorcheck.isChecked()) {
                    genratorgroup.setVisibility(View.VISIBLE);
                } else if (!generatorcheck.isChecked()) {
                    genratorgroup.setVisibility(View.GONE);
                }
            }
        });
        TextView btnShowTimePicker = findViewById(R.id.btnShowTimePicker);
        btnShowTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerFragment = new TimePickerFragment();
                timePickerFragment.setCancelable(false);
                timePickerFragment.show(getSupportFragmentManager(), "timePicker");
                a = true;
            }
        });

        TextView btnEndtimePicker = findViewById(R.id.btnEndTimePicker);
        btnEndtimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerFragment = new TimePickerFragment();
                timePickerFragment.setCancelable(false);
                timePickerFragment.show(getSupportFragmentManager(), "timePicker");
                a = false;
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

        TextView showstart = findViewById(R.id.btnShowTimePicker);
        TextView showend = findViewById(R.id.btnEndTimePicker);
        if (a) {
            if(Showend==null){
            showstart.setText("Starting Time - " + hour + ":" + minute);
            s_hour = hour;
            s_minute = minute;
            Showstart = showstart.getText().toString();
            return;
            }
            Calendar collected_end_time = Calendar.getInstance();
            Calendar collected_start_time = Calendar.getInstance();
            collected_start_time.set(Calendar.HOUR_OF_DAY, hour);
            collected_start_time.set(Calendar.MINUTE,minute);
            collected_end_time.set(Calendar.HOUR_OF_DAY,e_hour );
            collected_end_time.set(Calendar.MINUTE, e_minute);
            if (collected_end_time.getTimeInMillis() > collected_start_time.getTimeInMillis()) {
                showstart.setText("Starting Time - " + hour + ":" + minute);
                s_hour = hour;
                s_minute = minute;
                Showstart = showstart.getText().toString();
            } else {
                //it's before current'
                timePickerFragment = new TimePickerFragment();
                timePickerFragment.setCancelable(false);
                timePickerFragment.show(getSupportFragmentManager(), "TIME");
                Toast.makeText(getApplicationContext(), "Invalid Time", Toast.LENGTH_LONG).show();
            }

        } else {
            if(Showstart==null){
                showend.setText("Ending   Time - " + hour + ":" + minute);
                e_hour = hour;
                e_minute = minute;
                Showend = showend.getText().toString();
                return;
            }
            Calendar collected_end_time = Calendar.getInstance();
            Calendar collected_start_time = Calendar.getInstance();
            collected_start_time.set(Calendar.HOUR_OF_DAY, s_hour);
            collected_start_time.set(Calendar.MINUTE,s_minute);
            collected_end_time.set(Calendar.HOUR_OF_DAY, hour);
            collected_end_time.set(Calendar.MINUTE, minute);
            if (collected_end_time.getTimeInMillis() > collected_start_time.getTimeInMillis()) {
                showend.setText("Ending   Time - " + hour + ":" + minute);
                e_hour = hour;
                e_minute = minute;
                Showend = showend.getText().toString();
            } else {
                //it's before current'
                timePickerFragment = new TimePickerFragment();
                timePickerFragment.setCancelable(false);
                timePickerFragment.show(getSupportFragmentManager(), "TIME");
                Toast.makeText(getApplicationContext(), "Invalid Time", Toast.LENGTH_LONG).show();
            }


        }
    }

    @SuppressLint("WrongViewCast")
    public void sumbit(View view) {
        start = findViewById(R.id.btnShowTimePicker);
        end= findViewById(R.id.btnEndTimePicker);
        mobile =findViewById(R.id.mobilenumberEt);
        functionname = findViewById(R.id.eventnameEt);
        extensioncheck = findViewById(R.id.extensioncheck);
        generatorcheck = findViewById(R.id.generatorcheck);
        waterbottlecheck = findViewById(R.id.warerbottlecheck);
        h_name=findViewById(R.id.textView8);
        h_name.setText(hall_name);
        extensionEt = findViewById(R.id.extensionEt);
        extensionEt.setVisibility(View.GONE);
        bottleEt = findViewById(R.id.bottleEt);
        bottleEt.setVisibility(View.GONE);
        genratorgroup = findViewById(R.id.generatorradio);
        genratorgroup.setVisibility(View.GONE);
String Functionname=functionname.getText().toString();
String Start=start.getText().toString();
String End =end.getText().toString();
String Mobile =mobile.getText().toString();
String extension=extensionEt.getText().toString();
String bottle=bottleEt.getText().toString();
boolean a=Functionname.isEmpty();
boolean b=Start.isEmpty();
boolean c=End.isEmpty();
boolean d=Mobile.isEmpty();
boolean e=extension.isEmpty();
boolean f=bottle.isEmpty();
boolean g=false;
boolean h=false;
    if (extensioncheck.isChecked()){
        if (e){//Toast.makeText(this,"Please Enter the no of the extension need",Toast.LENGTH_LONG).show();
            g=true;
             }
        else { g=false; }
    }
    if (waterbottlecheck.isChecked()){
        if (f){ //Toast.makeText(this,"Please Enter the no of the Water Bottle need",Toast.LENGTH_LONG).show();
            h=true;
        }
        else { h=false;}
    }
    if (a||b||c||d||g||h){
        Toast.makeText(this,"Please Enter the details correctly",Toast.LENGTH_LONG).show();
    }else {
        openDialog();
    }
    }

    /* private void openDialog() {
         new Thread(new Runnable() {
             @Override
             public void run() {
                 System.out.println("starting..");
                 while (result.equals("null")){continue;}

                     if(result.equals("true")){
                         BookingForm.this.runOnUiThread(new Runnable()
                         {@Override
                          public void run()
                             {
                                 onrequest();
                                 //Do your UI operations like dialog opening or Toast here
                             }
                         });

                     }

                     if(result.equals("false")){
                         result="null";
                         System.out.println("unsuccessfull");
                         return;
                     }
                 }

         }).start();

         ExampleDialog exampleDialog = new ExampleDialog();
         exampleDialog.show(getSupportFragmentManager(), "");


     }*/
    private void openDialog() {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(BookingForm.this);
        final View mview = getLayoutInflater().inflate(R.layout.layout_dialog, null);
        Button ok = (Button) mview.findViewById(R.id.ok);
        Button cancel = (Button) mview.findViewById(R.id.cancel);
        mbuilder.setView(mview);


        final AlertDialog dialog = mbuilder.create();
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                request();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });


    }

    public void request() {
        extensioncheck = findViewById(R.id.extensioncheck);
        generatorcheck = findViewById(R.id.generatorcheck);
        waterbottlecheck = findViewById(R.id.warerbottlecheck);
        accheck = findViewById(R.id.accheck);
        projectorcheck = findViewById(R.id.projectorcheck);
        functionname = findViewById(R.id.eventnameEt);
        Functionname = functionname.getText().toString();
        extensionEt = findViewById(R.id.extensionEt);
        bottleEt = findViewById(R.id.bottleEt);
        genratorgroup = findViewById(R.id.generatorradio);
        onlyNeeded = findViewById(R.id.onlyWhenNeeded);
        fullday = findViewById(R.id.fullday);
        mobile = findViewById(R.id.mobilenumberEt);
        Requirements="";

        if (projectorcheck.isChecked()) {
            Requirements += "Projector is required"+ " , ";
        }
        if (accheck.isChecked()) {
            Requirements += "AC is required"+ " , ";
        }
        if (generatorcheck.isChecked()) {
            if (onlyNeeded.isChecked()) {
                Requirements +=  "Generator is required only during power cut" +" , ";
            }
            if (fullday.isChecked()) {
                Requirements +="Generator is required all the time"+ " , ";
            }

        }
        if (waterbottlecheck.isChecked()) {
            Requirements +=  "Required Water Bottles: " + bottleEt.getText().toString() + " , ";
        }
        if (extensioncheck.isChecked()) {
            Requirements += "Required Extension Boxes: " + extensionEt.getText().toString() + " , ";
        }
        //Toast.makeText(getApplicationContext(),Requirements,Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),Integer.toString(s_hour)+':'+Integer.toString(s_minute)+" to "+Integer.toString(e_hour)+':'+Integer.toString(e_minute),Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),Functionname,Toast.LENGTH_LONG).show();
        phone = mobile.getText().toString();

        String URLline=ip+"/hallbooking";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mailid",mailid);
        params.put("category",category);
        params.put("s_hour",Integer.toString(s_hour));
        params.put("s_minute",Integer.toString(s_minute));
        params.put("e_hour",Integer.toString(e_hour));
        params.put("e_minute",Integer.toString(e_minute));
        params.put("additional_requirements",Requirements);
        params.put("function_nature",Functionname);
        params.put("mobile",phone);
        params.put("year",syear);
        params.put("month",smonth);
        params.put("day",sday);
        params.put("hall_id",hall_id);

        final JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLline,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String text=response.getString("text");
                            Toast.makeText(BookingForm.this,text,Toast.LENGTH_LONG).show();
                            if(text.equals("Request placed successfully")) {
                                Intent desti = new Intent(BookingForm.this, Navigation.class);
                                desti.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                desti.putExtra("position", category);
                                startActivity(desti);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookingForm.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


}
}
