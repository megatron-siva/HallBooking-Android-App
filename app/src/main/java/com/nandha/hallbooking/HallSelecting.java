package com.nandha.hallbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HallSelecting extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    LinearLayout Scrollable1;
    int col;
    private String btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,hallidkey;
    private Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10;
    private TextView nec_conference_hall,nec_placement_auditorium,npc_conference_hall,nasc_auditorium,
            nct_conference_hall,cbse_main_multi_purpose_hall,cbse_city_campus_multi_purpose_hall,matric_seminar_hall,bed_multi_purpose_hall,phar_nur_phy_conference_hall;
    static String hall_id,syear,smonth,sday,hall_name;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_selecting);
        ImageButton button = (ImageButton) findViewById(R.id.dater);
        Scrollable1 = (LinearLayout) findViewById(R.id.screen1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
    }

    public void button1(View view) {
        hall_id="nec_conference_hall";
        hall_name="CONFERENCE HALL NEC";
        startActivity(new Intent(getApplicationContext(),BookingForm.class));



    }
    public  int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void button2(View view) {
        hall_id="nec_placement_auditorium";
        hall_name="PLACEMENT AUDITORIUM NEC";
        startActivity(new Intent(getApplicationContext(),BookingForm.class));

    }

    public void button3(View view) {
        hall_id="npc_conference_hall";
        hall_name="CONFERENCE HALL NPC";
        startActivity(new Intent(getApplicationContext(),BookingForm.class));
    }

    public void button4(View view) {
        hall_id="nasc_auditorium";
        hall_name="AUDITORIUM NASC";
        startActivity(new Intent(getApplicationContext(),BookingForm.class));
    }

    public void button5(View view) {
        hall_id="nct_conference_hall";
        hall_name="CONFERENCE HALL NCT";
        startActivity(new Intent(getApplicationContext(),BookingForm.class));
    }

    public void button6(View view) {
        hall_id="cbse_main_multi_purpose_hall";
        hall_name="MULTI PURPOSE HALL CBSE - MAIN";
        startActivity(new Intent(getApplicationContext(),BookingForm.class));
    }

    public void button7(View view) {
        hall_id="cbse_city_campus_multi_purpose_hall";
        hall_name="MULTI PURPOSE HALL CBSE - CITY CAMPUS";
        startActivity(new Intent(getApplicationContext(),BookingForm.class));
    }

    public void button8(View view) {
        hall_id="matric_seminar_hall";
        hall_name="SEMINAR HALL MATRIC";
        startActivity(new Intent(getApplicationContext(),BookingForm.class));
    }

    public void button9(View view) {
        hall_id="bed_multi_purpose_hall";
        hall_name="MULTI PURPOSE HALL B.ED";
        startActivity(new Intent(getApplicationContext(),BookingForm.class));
    }

    public void button10(View view) {
        hall_id="phar_nur_phy_conference_hall";
        hall_name="CONFERENCE HALL PHARMACY,NURSING,PHYSIOTHERAPY";
        startActivity(new Intent(getApplicationContext(),BookingForm.class));
        }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        sday=Integer.toString(dayOfMonth);
        smonth=Integer.toString(month+1);
        syear=Integer.toString(year);
        Scrollable1.setVisibility(View.INVISIBLE);
        String URLline=Login.ip +"/on_change_date";
        Map<String, Integer> params = new HashMap<>();
        params.put("year",year);
        params.put("month",month+1);
        params.put("day",dayOfMonth);


        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLline,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String [] hallid = new String[10];
                            hallid[0]="nec_conference_hall";
                            hallid[1]="nec_placement_auditorium";
                            hallid[2]="npc_conference_hall";
                            hallid[3]="nasc_auditorium";
                            hallid[4]="nct_conference_hall";
                            hallid[5]="cbse_main_multi_purpose_hall";
                            hallid[6]="cbse_city_campus_multi_purpose_hall";
                            hallid[7]="matric_seminar_hall";
                            hallid[8]="bed_multi_purpose_hall";
                            hallid[9]="phar_nur_phy_conference_hall";


                            String [] textid = new String[10];
                            textid[0]="nec_conference_halltv";
                            textid[1]="nec_placement_auditoriumtv";
                            textid[2]="npc_conference_halltv";
                            textid[3]="nasc_auditoriumtv";
                            textid[4]="nct_conference_halltv";
                            textid[5]="cbse_main_multi_purpose_halltv";
                            textid[6]="cbse_city_campus_multi_purpose_halltv";
                            textid[7]="matric_seminar_halltv";
                            textid[8]="bed_multi_purpose_halltv";
                            textid[9]="phar_nur_phy_conference_halltv";



                            for(int i=0;i<10;i++){
                                hallidkey=hallid[i];
                                int lines;
                                String text;
                                JSONArray hall = response.getJSONArray(hallid[i]);
                                if (hall.length()==0) {
                                    lines = 1;
                                    text="Booking Available";
                                    col=Color.parseColor("#008000");
                                }
                                else{
                                lines=Integer.parseInt(hall.getString(0));
                                text=hall.getString(1);
                                col=Color.parseColor("#ff2300");
                                }
                                if(hallid[i].equals("nec_conference_hall")) {

                                button1 = (Button) findViewById(R.id.nec_conference_hall);

                                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) button1.getLayoutParams();
                                layoutParams.height = dpToPx(150+(lines-1)*20);
                                button1.setPadding(0, dpToPx(5), 0, dpToPx((lines-1)*20));
                                button1.setLayoutParams(layoutParams);
                                nec_conference_hall = (TextView) findViewById(R.id.nec_conference_halltv);
                                nec_conference_hall.setText(text);
                                nec_conference_hall.setTextColor(col);
                                }
                                if(hallid[i].equals("nec_placement_auditorium")) {
                                    button2 = (Button) findViewById(R.id.nec_placement_auditorium);
                                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) button2.getLayoutParams();
                                    layoutParams.height = dpToPx(150+(lines-1)*20);
                                    button2.setPadding(0, dpToPx(5), 0, dpToPx((lines-1)*20));
                                    button2.setLayoutParams(layoutParams);
                                    nec_placement_auditorium = (TextView) findViewById(R.id.nec_placement_auditoriumtv);
                                    nec_placement_auditorium.setText(text);
                                    nec_placement_auditorium.setTextColor(col);
                                }
                                if(hallid[i].equals("npc_conference_hall")) {
                                    button3 = (Button) findViewById(R.id.npc_conference_hall);
                                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) button3.getLayoutParams();
                                    layoutParams.height = dpToPx(150+(lines-1)*20);
                                    button3.setPadding(0, dpToPx(5), 0, dpToPx((lines-1)*20));
                                    button3.setLayoutParams(layoutParams);
                                    npc_conference_hall = (TextView) findViewById(R.id.npc_conference_halltv);
                                    npc_conference_hall.setText(text);
                                    nec_conference_hall.setTextColor(col);
                                }

                                if(hallid[i].equals("nasc_auditorium")) {
                                    button4 = (Button) findViewById(R.id.nasc_auditorium);
                                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) button4.getLayoutParams();
                                    layoutParams.height = dpToPx(150+(lines-1)*20);
                                    button4.setPadding(0, dpToPx(5), 0, dpToPx((lines-1)*20));
                                    button4.setLayoutParams(layoutParams);
                                    nasc_auditorium = (TextView) findViewById(R.id.nasc_auditoriumtv);
                                    nasc_auditorium.setText(text);
                                    nasc_auditorium.setTextColor(col);
                                }
                                if(hallid[i].equals("nct_conference_hall")) {
                                    button5 = (Button) findViewById(R.id.nct_conference_hall);
                                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) button5.getLayoutParams();
                                    layoutParams.height = dpToPx(150+((lines-1)*20));
                                    button5.setPadding(0, dpToPx(5), 0, dpToPx((lines-1)*20));
                                    button5.setLayoutParams(layoutParams);
                                    nct_conference_hall = (TextView) findViewById(R.id.nct_conference_halltv);
                                    nct_conference_hall.setText(text);
                                    nct_conference_hall.setTextColor(col);
                                }
                                if(hallid[i].equals("cbse_main_multi_purpose_hall")) {
                                    button6 = (Button) findViewById(R.id.cbse_main_multi_purpose_hall);
                                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) button6.getLayoutParams();
                                    layoutParams.height = dpToPx(150+((lines-1)*20));
                                    button6.setPadding(0, dpToPx(5), 0, dpToPx((lines-1)*20));
                                    button6.setLayoutParams(layoutParams);
                                    cbse_main_multi_purpose_hall = (TextView) findViewById(R.id.cbse_main_multi_purpose_halltv);
                                    cbse_main_multi_purpose_hall.setText(text);
                                    cbse_main_multi_purpose_hall.setTextColor(col);

                                }
                                if(hallid[i].equals("cbse_city_campus_multi_purpose_hall")) {
                                    button7 = (Button) findViewById(R.id.cbse_main_multi_purpose_hall);
                                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) button7.getLayoutParams();
                                    layoutParams.height = dpToPx(150+((lines-1)*20));
                                    button7.setPadding(0, dpToPx(5), 0, dpToPx((lines-1)*20));
                                    button7.setLayoutParams(layoutParams);
                                    cbse_city_campus_multi_purpose_hall = (TextView) findViewById(R.id.cbse_city_campus_multi_purpose_halltv);
                                    cbse_city_campus_multi_purpose_hall.setText(text);
                                }
                                if(hallid[i].equals("matric_seminar_hall")) {
                                    button8 = (Button) findViewById(R.id.matric_seminar_hall);
                                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) button8.getLayoutParams();
                                    layoutParams.height = dpToPx(150+((lines-1)*20));
                                    button8.setPadding(0, dpToPx(5), 0, dpToPx((lines-1)*20));
                                    button8.setLayoutParams(layoutParams);
                                    matric_seminar_hall = (TextView) findViewById(R.id.matric_seminar_halltv);
                                    matric_seminar_hall.setText(text);
                                    matric_seminar_hall.setTextColor(col);
                                }
                                if(hallid[i].equals("bed_multi_purpose_hall")) {
                                    button9 = (Button) findViewById(R.id.bed_multi_purpose_hall);
                                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) button9.getLayoutParams();
                                    layoutParams.height = dpToPx(150+((lines-1)*20));
                                    button9.setPadding(0, dpToPx(5), 0, dpToPx((lines-1)*20));
                                    button9.setLayoutParams(layoutParams);
                                    bed_multi_purpose_hall = (TextView) findViewById(R.id.bed_multi_purpose_halltv);
                                    bed_multi_purpose_hall.setText(text);
                                    bed_multi_purpose_hall.setTextColor(col);
                                }
                                if(hallid[i].equals("phar_nur_phy_conference_hall")) {
                                    button10 = (Button) findViewById(R.id.phar_nur_phy_conference_hall);
                                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) button10.getLayoutParams();
                                    layoutParams.height = dpToPx(150+((lines-1)*20));
                                    button10.setPadding(0, dpToPx(5), 0, dpToPx(20+((lines-1)*20)));
                                    button10.setLayoutParams(layoutParams);
                                    phar_nur_phy_conference_hall = (TextView) findViewById(R.id.phar_nur_phy_conference_halltv);
                                    phar_nur_phy_conference_hall.setText(text);
                                    phar_nur_phy_conference_hall.setTextColor(col);
                                }
                                }







                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Scrollable1.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HallSelecting.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView =(TextView) findViewById(R.id.date);
        textView.setText(currentDateString);
        textView.setText(currentDateString);


    }

}
