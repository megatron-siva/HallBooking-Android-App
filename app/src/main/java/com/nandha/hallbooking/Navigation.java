package com.nandha.hallbooking;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import static com.nandha.hallbooking.Login.category;

public class Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    String position;
    DrawerLayout drawer;
    AppBarLayout appBar;
    NavigationView navigationView;

    @Override
    public void onBackPressed() {
        if(navigationView.getMenu().findItem(R.id.nav_home).isChecked()){
            finish();
            //System.exit(0);
        }
        else{
            navigationView.getMenu().getItem(0).setChecked(true);
            if(position.equals("staff")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Faculty_Menu()).commit();}
            else if(position.equals("dean")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Hod_Menu()).commit();}
            else if(position.equals("hod")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Hod_Menu()).commit();}
            else if(position.equals("hall_incharge")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HallInCharge_Menu()).commit();}
            else if(position.equals("principal")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Principal_Menu()).commit();}
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        appBar=findViewById(R.id.appbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBar.setOutlineProvider(null);
        }
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        position=getIntent().getExtras().getString("position");
        navigationView.getMenu().getItem(0).setChecked(true);
        if(position.equals("staff")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Faculty_Menu()).commit();}
        else if(position.equals("dean")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Hod_Menu()).commit();}
        else if(position.equals("hod")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Hod_Menu()).commit();}
        else if(position.equals("hall_incharge")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HallInCharge_Menu()).commit();}
        else if(position.equals("principal")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Principal_Menu()).commit();}
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                if(position.equals("staff")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Faculty_Menu()).commit(); }
                else if(position.equals("hod")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Hod_Menu()).commit(); }
                else if(position.equals("dean")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Hod_Menu()).commit(); }
                else if(position.equals("hall_incharge")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HallInCharge_Menu()).commit(); }
                else if(position.equals("principal")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Principal_Menu()).commit();}
                break;
            case R.id.nav_logout:
                signout();
                break;
            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Help()).commit();
                break;
            case R.id.nav_about_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new About_us()).commit();
                break;
        }
        drawer.closeDrawers();
        return true;
    }
    void signout(){
        GoogleSignInClient mGoogleSignInClient;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent desti = new Intent(Navigation.this, Login.class);
                desti.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(desti);
                finish();
                Toast.makeText(Navigation.this,"Logged out successfully",Toast.LENGTH_LONG).show();
            }
        });
    }
}