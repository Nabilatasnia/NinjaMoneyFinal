package com.example.ninjamoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

public class Report extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeReport;
    private DatabaseReference mExpenseReport;
    private FirebaseUser muser;

    TextView janinc,febinc,marinc,aprinc,mayinc,juninc,julinc,auginc,sepinc,octinc,novinc,decinc;
    TextView janexp,febexp,marexp,aprexp,mayexp,junexp,julexp,augexp,sepexp,octexp,novexp,decexp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setup();
        setReport();
        drawer();
    }
    private void setup() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAuth = FirebaseAuth.getInstance();
        muser = mAuth.getCurrentUser();
        String uid = muser.getUid();
        mIncomeReport = FirebaseDatabase.getInstance().getReference().child("IncomeReport").child(uid);
        mExpenseReport = FirebaseDatabase.getInstance().getReference().child("ExpenseReport").child(uid);
        toolbar = findViewById(R.id.toolbar);
        janinc = findViewById(R.id.janincome);
        febinc = findViewById(R.id.febincome);
        marinc = findViewById(R.id.marincome);
        aprinc = findViewById(R.id.aprincome);
        mayinc = findViewById(R.id.mayincome);
        juninc = findViewById(R.id.junincome);
        julinc = findViewById(R.id.julincome);
        auginc = findViewById(R.id.augincome);
        sepinc = findViewById(R.id.sepincome);
        octinc = findViewById(R.id.octincome);
        novinc = findViewById(R.id.novincome);
        decinc = findViewById(R.id.decincome);
        janexp = findViewById(R.id.janexpense);
        febexp = findViewById(R.id.febexpense);
        marexp = findViewById(R.id.marexpense);
        aprexp = findViewById(R.id.aprexpense);
        mayexp = findViewById(R.id.mayexpense);
        junexp = findViewById(R.id.junexpense);
        julexp = findViewById(R.id.julexpense);
        augexp = findViewById(R.id.augexpense);
        sepexp = findViewById(R.id.sepexpense);
        octexp = findViewById(R.id.octexpense);
        novexp = findViewById(R.id.novexpense);
        decexp = findViewById(R.id.decexpense);
    }
    private void setReport()
    {
            mIncomeReport.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        String janincs = snapshot.child("janinc").getValue().toString();
                    String febincs = snapshot.child("febinc").getValue().toString();
                    String marincs = snapshot.child("marinc").getValue().toString();
                    String aprincs = snapshot.child("aprinc").getValue().toString();
                    String mayincs = snapshot.child("mayinc").getValue().toString();
                    String junincs = snapshot.child("juninc").getValue().toString();
                    String julincs = snapshot.child("julinc").getValue().toString();
                    String augincs = snapshot.child("auginc").getValue().toString();
                    String sepincs = snapshot.child("sepinc").getValue().toString();
                    String octincs = snapshot.child("octinc").getValue().toString();
                    String novincs = snapshot.child("novinc").getValue().toString();
                    String decincs = snapshot.child("decinc").getValue().toString();
                    janinc.setText(janincs);
                    febinc.setText(febincs);
                    marinc.setText(marincs);
                    aprinc.setText(aprincs);
                    mayinc.setText(mayincs);
                    juninc.setText(junincs);
                    julinc.setText(julincs);
                    auginc.setText(augincs);
                    sepinc.setText(sepincs);
                    octinc.setText(octincs);
                    novinc.setText(novincs);
                    decinc.setText(decincs);
                }
            }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        mExpenseReport.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String janexps = snapshot.child("janexp").getValue().toString();
                    String febexps = snapshot.child("febexp").getValue().toString();
                    String marexps = snapshot.child("marexp").getValue().toString();
                    String aprexps = snapshot.child("aprexp").getValue().toString();
                    String mayexps = snapshot.child("mayexp").getValue().toString();
                    String junexps = snapshot.child("junexp").getValue().toString();
                    String julexps = snapshot.child("julexp").getValue().toString();
                    String augexps = snapshot.child("augexp").getValue().toString();
                    String sepexps = snapshot.child("sepexp").getValue().toString();
                    String octexps = snapshot.child("octexp").getValue().toString();
                    String novexps = snapshot.child("novexp").getValue().toString();
                    String decexps = snapshot.child("decexp").getValue().toString();
                    janexp.setText(janexps);
                    febexp.setText(febexps);
                    marexp.setText(marexps);
                    aprexp.setText(aprexps);
                    mayexp.setText(mayexps);
                    junexp.setText(junexps);
                    julexp.setText(julexps);
                    augexp.setText(augexps);
                    sepexp.setText(sepexps);
                    octexp.setText(octexps);
                    novexp.setText(novexps);
                    decexp.setText(decexps);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void drawer() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_report);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent intentHome = new Intent(this, MainActivity.class);
            startActivity(intentHome);
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
            case R.id.nav_budget:
                Intent intentRep = new Intent(this, Budget.class);
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