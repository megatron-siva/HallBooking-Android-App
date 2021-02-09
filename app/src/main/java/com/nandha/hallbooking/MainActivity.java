package com.nandha.hallbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.nandha.hallbooking.Login.category;
import static com.nandha.hallbooking.Login.ip;
import static com.nandha.hallbooking.Login.mailid;

public class MainActivity extends AppCompatActivity {
    //SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 1;
    String personGmail;

    private static int TIME_OUT = 4000;
    ImageView imgfade;
    //TextView textview,textview1;
    Animation animfadein;

    //Button button;
    //int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgfade = (ImageView) findViewById(R.id.imgf);
        animfadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        animfadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                google();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imgfade.startAnimation(animfadein);




        ////////////////////////////////////////////////////////////

    }
    void google(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personGmail = acct.getEmail();
            mailid=personGmail;
            gcal(mailid);
        }
        else{

            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
    }

    void gcal(String gmailid){
        String URLline=ip+"/login";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mailid",gmailid);

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLline,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            category = response.getString("category");

                            if(category.equals("staff") ){
                                startActivity(new Intent(getApplicationContext(),Navigation.class).putExtra("position","staff"));
                                finish();
                            }
                            else if (category.equals("hod") || category.equals("dean")){
                                startActivity(new Intent(getApplicationContext(), Navigation.class).putExtra("position","hod"));
                                finish();
                            }
                            else if ( category.equals("dean")){
                                startActivity(new Intent(getApplicationContext(), Navigation.class).putExtra("position","dean"));
                                finish();
                            }
                            else if(category.equals("hall_incharge") ){
                                startActivity(new Intent(getApplicationContext(),Navigation.class).putExtra("position","hall_incharge"));
                                finish();
                            }
                            else if (category.equals("principal") ){
                                startActivity(new Intent(getApplicationContext(), Navigation.class).putExtra("position","principal"));
                                finish();
                            }
                            else {
                                signout();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                findViewById(R.id.btnlogin).setEnabled(true);
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                if(error instanceof TimeoutError) {
                    gcal(personGmail);
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
    void signout(){
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this,"Sign in with Registered college id",Toast.LENGTH_LONG).show();
            }
        });
    }


    /*void touch(){
        if(i<1){
            animdownup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up);
            textview.setVisibility(View.INVISIBLE);
            textview.startAnimation(animdownup);
            imgmove.setVisibility(View.INVISIBLE);
            animfadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
            textview1.setVisibility(View.VISIBLE);
            textview1.startAnimation(animfadein);
            animfadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
            imgfade.setVisibility(View.VISIBLE);
            imgfade.startAnimation(animfadein);
            animMoveUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_up);
            button.setVisibility(View.VISIBLE);
            button.startAnimation(animMoveUp);
        }}*/


}