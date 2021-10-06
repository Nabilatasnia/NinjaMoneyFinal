package com.example.ninjamoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ninjamoney.BalanceCalculation.Balance;
import com.example.ninjamoney.LoginSignUp.Login;
import com.example.ninjamoney.LoginSignUp.Profile;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Donate extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    CardView brac_cv;
    CardView alter_cv;
    CardView kidney_cv;
    CardView bidyanondo_cv;
    CardView thalassemia_cv;
    CardView as_sunnah_cv;
    CardView friendship_cv;
    CardView jaago_cv;

    ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dRefCat;
    private DatabaseReference dRefExp;
    private FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        setup();
        drawer();
        firebase();
    }

    private void firebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();
        dRefCat = firebaseDatabase.getReference().child("CategoryData").child(uid);
        dRefExp = firebaseDatabase.getReference().child("Budget").child(uid);

        dRefExp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int donationGoal = Integer.parseInt(snapshot.child("other").getValue().toString().trim());
                    progressBar.setMax(donationGoal);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        dRefCat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int progress = Integer.parseInt(snapshot.child("other").getValue().toString().trim());
                    progressBar.setProgress(progress);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    private void setup() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        brac_cv = findViewById(R.id.brac_cv);
        alter_cv = findViewById(R.id.alter_cv);
        kidney_cv = findViewById(R.id.kidney_cv);
        bidyanondo_cv = findViewById(R.id.bidyanondo_cv);
        thalassemia_cv = findViewById(R.id.thalassemia_cv);
        as_sunnah_cv = findViewById(R.id.as_sunnah_cv);
        friendship_cv = findViewById(R.id.friendship_cv);
        jaago_cv = findViewById(R.id.jaago_cv);
        progressBar = findViewById(R.id.progressBar);

        brac_cv.setOnClickListener(this);
        alter_cv.setOnClickListener(this);
        kidney_cv.setOnClickListener(this);
        bidyanondo_cv.setOnClickListener(this);
        thalassemia_cv.setOnClickListener(this);
        as_sunnah_cv.setOnClickListener(this);
        friendship_cv.setOnClickListener(this);
        jaago_cv.setOnClickListener(this);
    }

    private void drawer() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_donate);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            /*Intent intentHome = new Intent(this, MainActivity.class);
            startActivity(intentHome);*/
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intentHome = new Intent(this, MainActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_income:
                Intent intentInc = new Intent(this, Income.class);
                startActivity(intentInc);
                break;
            case R.id.nav_expense:
                Intent intentBud = new Intent(this, Expense.class);
                startActivity(intentBud);
                break;
            case R.id.nav_balance:
                Intent intentStat = new Intent(this, Balance.class);
                startActivity(intentStat);
                break;
            case R.id.nav_report:
                Intent intentRep = new Intent(this, Report.class);
                startActivity(intentRep);
                break;
            case R.id.nav_budget:
                Intent intentDon = new Intent(this, Budget.class);
                startActivity(intentDon);
                break;
            case R.id.nav_profile:
                Intent intentProf = new Intent(this, Profile.class);
                startActivity(intentProf);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intentOut = new Intent(this, Login.class);
                startActivity(intentOut);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        open(v);
    }

    private void open(View v) {
        switch (v.getId()) {
            case R.id.brac_cv:
                gotoUrl("https://brac.net/dakcheabardesh/en/");
                break;
            case R.id.alter_cv:
                gotoUrl("https://alteryouth.com/");
                break;
            case R.id.kidney_cv:
                gotoUrl("https://www.kidneyfoundationbd.com/donation");
                break;
            case R.id.bidyanondo_cv:
                gotoUrl("https://www.bidyanondo.org/donate");
                break;
            case R.id.thalassemia_cv:
                gotoUrl("https://www.thals.org/zakat");
                break;
            case R.id.as_sunnah_cv:
                gotoUrl("https://assunnahfoundation.org/donation");
                break;
            case R.id.friendship_cv:
                gotoUrl("https://friendship.ngo/donate/");
                break;
            case R.id.jaago_cv:
                gotoUrl("https://www.jaago.com.bd/donate-bd/");
                break;
        }
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
