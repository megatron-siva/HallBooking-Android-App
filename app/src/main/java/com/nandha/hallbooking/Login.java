package com.nandha.hallbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 1;
    String personGmail;

    EditText UserID;
    Button Login;
    int hod =1;
    String a,b;
    static  String mailid;
    static String category;
    static String ip="https://hall-booking.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient=GoogleSignIn.getClient(this,gso);
        signInButton=findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            if (acct != null) {
                personGmail = acct.getEmail();
                mailid=personGmail;
                gcal(mailid);
            }

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }


    public void login(View view) {
        findViewById(R.id.btnlogin).setEnabled(false);
        UserID=findViewById(R.id.userid);
        mailid=UserID.getText().toString().trim();

        String URLline=ip+"/login";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mailid",mailid);

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLline,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            category = response.getString("category");

                           if(category.equals("staff") ){
                                startActivity(new Intent(getApplicationContext(),Navigation.class).putExtra("position","staff"));
                               findViewById(R.id.btnlogin).setEnabled(true);
                               finish();
                            }
                           else if (category.equals("hod") || category.equals("dean")){
                               startActivity(new Intent(getApplicationContext(), Navigation.class).putExtra("position","hod"));
                               findViewById(R.id.btnlogin).setEnabled(true);
                               finish();
                           }
                           else if ( category.equals("dean")){
                               startActivity(new Intent(getApplicationContext(), Navigation.class).putExtra("position","dean"));
                               findViewById(R.id.btnlogin).setEnabled(true);
                               finish();
                           }
                            else if(category.equals("hall_incharge") ){
                                startActivity(new Intent(getApplicationContext(),Navigation.class).putExtra("position","hall_incharge"));
                               findViewById(R.id.btnlogin).setEnabled(true);
                                finish();
                            }
                            else if (category.equals("principal") ){
                                startActivity(new Intent(getApplicationContext(), Navigation.class).putExtra("position","principal"));
                               findViewById(R.id.btnlogin).setEnabled(true);
                                finish();
                            }else {
                               findViewById(R.id.btnlogin).setEnabled(true);
                               Toast.makeText(Login.this, "Sign in with Registered college id", Toast.LENGTH_LONG).show();
                           }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                findViewById(R.id.btnlogin).setEnabled(true);
                Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG).show();
            }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

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
            Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG).show();
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
            Toast.makeText(Login.this,"Sign in with Registered college id",Toast.LENGTH_LONG).show();
        }
    });
}

    @Override
    public void onBackPressed() {
        finish();
    }
}

