package com.example.ninjamoney;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ninjamoney.LoginSignUp.Login;
import com.example.ninjamoney.LoginSignUp.Profile;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Balance extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        setup();
        drawer();
    }

    private void setup() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
    }

    private void drawer() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_balance);
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
            case R.id.nav_budget:
                Intent intentStat = new Intent(this, Budget.class);
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