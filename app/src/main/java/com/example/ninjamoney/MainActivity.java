package com.example.ninjamoney;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ninjamoney.LoginSignUp.Login;
import com.example.ninjamoney.LoginSignUp.Profile;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private Button income;
    private Button expense;
    private Button budget;
    private Button status;
    private Button report;
    private Button donate;
    private TextView text2;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference dRef;
    private FirebaseUser firebaseUser;
    String uid;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        firebase();
        drawer();
    }

    private void drawer() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }

    private void firebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();
        dRef = database.getReference().child("Users").child(uid);
        //Toast.makeText(MainActivity.this, uid, Toast.LENGTH_SHORT).show();

        Query query = dRef.orderByChild("email");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                if (snapshot.exists()) {
                    String name = snapshot.child("username").getValue().toString();
                    text2.setText(name);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void setup() {
        income = findViewById(R.id.income);
        expense = findViewById(R.id.expense);
        budget = findViewById(R.id.budget);
        status = findViewById(R.id.status);
        report = findViewById(R.id.report);
        donate = findViewById(R.id.donate);
        text2 = findViewById(R.id.text2);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        income.setOnClickListener(this);
        expense.setOnClickListener(this);
        budget.setOnClickListener(this);
        status.setOnClickListener(this);
        report.setOnClickListener(this);
        donate.setOnClickListener(this);
        text2.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (isPressed) {
                finish();
                moveTaskToBack(true);

            } else {
                Toast.makeText(this, "Press again to close app", Toast.LENGTH_SHORT).show();
                isPressed = true;
            }
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    isPressed = false;
                }
            };
            new Handler().postDelayed(runnable, 2000);
        }
    }

    @Override
    public void onClick(View v) {
        open(v);
    }

    private void open(View v) {
        switch (v.getId()) {
            case R.id.income:
                Intent intentInc = new Intent(this, Income.class);
                startActivity(intentInc);
                break;
            case R.id.expense:
                Intent intentExp = new Intent(this, Expense.class);
                startActivity(intentExp);
                break;
            case R.id.budget:
                Intent intentBud = new Intent(this, Budget.class);
                startActivity(intentBud);
                break;
            case R.id.status:
                Intent intentStat = new Intent(this, Balance.class);
                startActivity(intentStat);
                break;
            case R.id.report:
                Intent intentRep = new Intent(this, Report.class);
                startActivity(intentRep);
                break;
            case R.id.donate:
                Intent intentDon = new Intent(this, Donate.class);
                startActivity(intentDon);
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_income:
                Intent intentInc = new Intent(this, Income.class);
                startActivity(intentInc);
                break;
            case R.id.nav_expense:
                Intent intentExp = new Intent(this, Expense.class);
                startActivity(intentExp);
                break;
            case R.id.nav_budget:
                Intent intentBud = new Intent(this, Budget.class);
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
            case R.id.nav_donate:
                Intent intentDon = new Intent(this, Donate.class);
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
}