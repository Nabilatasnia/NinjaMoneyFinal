package com.example.ninjamoney.BalanceCalculation;

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
import android.widget.Toast;

import com.example.ninjamoney.Budget;
import com.example.ninjamoney.Donate;
import com.example.ninjamoney.Expense;
import com.example.ninjamoney.Income;
import com.example.ninjamoney.LoginSignUp.Login;
import com.example.ninjamoney.LoginSignUp.Profile;
import com.example.ninjamoney.MainActivity;
import com.example.ninjamoney.R;
import com.example.ninjamoney.Report;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Balance extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    TextView balance_tv;
    TextView cash_balance_tv;
    TextView bank_balance_tv;
    TextView mobile_balance_tv;
    TextView username_tv;
    TextView email_tv;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference dRef;
    private DatabaseReference dRefBalance;
    private FirebaseUser firebaseUser;
    String uid;

    int incomeCash, incomeBank, incomeMobile, incomeTotal;
    int expenseCash, expenseBank, expenseMobile, expenseTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        setup();
        drawer();
        firebase();
        update();
    }

    private void firebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();
        dRef = database.getReference().child("Users").child(uid);
        Query query = dRef.orderByChild("email");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                if (snapshot.exists()) {
                    String name = snapshot.child("username").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    username_tv.setText(name);
                    email_tv.setText(email);
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

    private void update() {
        dRefBalance = database.getReference().child("BalanceData").child(uid);

        dRefBalance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                incomeCash= Integer.parseInt(snapshot.child("incomeCash").getValue().toString().trim());
                incomeBank = Integer.parseInt(snapshot.child("incomeBank").getValue().toString().trim());
                incomeMobile = Integer.parseInt(snapshot.child("incomeMobile").getValue().toString().trim());
                incomeTotal = Integer.parseInt(snapshot.child("incomeTotal").getValue().toString().trim());
                expenseCash = Integer.parseInt(snapshot.child("expenseCash").getValue().toString().trim());
                expenseBank = Integer.parseInt(snapshot.child("expenseBank").getValue().toString().trim());
                expenseMobile = Integer.parseInt(snapshot.child("expenseMobile").getValue().toString().trim());
                expenseTotal = Integer.parseInt(snapshot.child("expenseTotal").getValue().toString().trim());

                int total = incomeTotal - expenseTotal;
                int cash = incomeCash - expenseCash;
                int bank = incomeBank - expenseBank;
                int mobile = incomeMobile - expenseMobile;
                balance_tv.setText(total+" ৳");
                cash_balance_tv.setText(cash+" ৳");
                bank_balance_tv.setText(bank+" ৳");
                mobile_balance_tv.setText(mobile+" ৳");
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }

    private void setup() {
        balance_tv = findViewById(R.id.balance_tv);
        cash_balance_tv = findViewById(R.id.cash_balance_tv);
        bank_balance_tv = findViewById(R.id.bank_balance_tv);
        mobile_balance_tv = findViewById(R.id.mobile_balance_tv);
        username_tv = findViewById(R.id.username_tv);
        email_tv = findViewById(R.id.email_tv);

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