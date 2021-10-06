package com.example.ninjamoney;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ninjamoney.BalanceCalculation.Balance;
import com.example.ninjamoney.LoginSignUp.Login;
import com.example.ninjamoney.LoginSignUp.Profile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Month;

public class Budget extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private FirebaseAuth mAuth;
    private DatabaseReference mCategoryDatabase;
    private DatabaseReference mBudgetDatabase;
    Toolbar toolbar;
    FloatingActionButton fab_budget_btn;
    ProgressBar foodprog,livingprog,cloprog,eduprog,treatprog,investprog,otherprog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        setup();
        categoryupdate();
        drawer();
    }
    private void categoryupdate()
    {
        mBudgetDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int food=Integer.parseInt(snapshot.child("food").getValue().toString());
                    int clothing=Integer.parseInt(snapshot.child("clothing").getValue().toString());
                    int living=Integer.parseInt(snapshot.child("living").getValue().toString());
                    int education=Integer.parseInt(snapshot.child("education").getValue().toString());
                    int treatment=Integer.parseInt(snapshot.child("treatment").getValue().toString());
                    int investment=Integer.parseInt(snapshot.child("investment").getValue().toString());
                    int other=Integer.parseInt(snapshot.child("other").getValue().toString());
                    foodprog.setMax(food);

                    livingprog.setMax(living);

                    cloprog.setMax(clothing);

                    eduprog.setMax(education);

                    treatprog.setMax(treatment);

                    investprog.setMax(investment);

                    otherprog.setMax(other);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        mCategoryDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    int foodspent=Integer.parseInt(snapshot.child("food").getValue().toString().trim());
                    int livingspent=Integer.parseInt(snapshot.child("living").getValue().toString().trim());
                    int clothingspent=Integer.parseInt(snapshot.child("clothing").getValue().toString().trim());
                    int educationspent=Integer.parseInt(snapshot.child("education").getValue().toString().trim());
                    int treatmentspent=Integer.parseInt(snapshot.child("treatment").getValue().toString().trim());
                    int investmentspent=Integer.parseInt(snapshot.child("investment").getValue().toString().trim());
                    int otherspent=Integer.parseInt(snapshot.child("other").getValue().toString().trim());
                 //   Toast.makeText(Budget.this, "CAtegory Change", Toast.LENGTH_SHORT).show();
                    foodprog.setProgress(foodspent);
                    livingprog.setProgress(livingspent);
                    cloprog.setProgress(clothingspent);
                    treatprog.setProgress(treatmentspent);
                    investprog.setProgress(investmentspent);
                    eduprog.setProgress(educationspent);
                    otherprog.setProgress(otherspent);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
    private void setup() {
        fab_budget_btn = findViewById(R.id.add);
        fab_budget_btn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        String uid = muser.getUid();
        mCategoryDatabase = FirebaseDatabase.getInstance().getReference().child("CategoryData").child(uid);
        mBudgetDatabase=FirebaseDatabase.getInstance().getReference().child("Budget").child(uid);
        foodprog=findViewById(R.id.food_progressbar);
        livingprog=findViewById(R.id.living_progressbar);
        cloprog=findViewById(R.id.clothing_progressbar);
        eduprog=findViewById(R.id.education_progressbar);
        treatprog=findViewById(R.id.treatment_progressbar);
        investprog=findViewById(R.id.investment_progressbar);
        otherprog=findViewById(R.id.other_progressbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
    }
    @Override
    public void onClick(View v) {
        open(v);
    }

    private void open(View v) {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(Budget.this);
        LayoutInflater inflater = LayoutInflater.from(Budget.this);
        View myview = inflater.inflate(R.layout.budget_input, null);
        mydialog.setView(myview);


        final AlertDialog dialog = mydialog.create();
        dialog.setCancelable(false);
        final EditText food_budget = myview.findViewById(R.id.food_budget);
        final EditText clothing_budget = myview.findViewById(R.id.clothing_budget);
        final EditText living_budget = myview.findViewById(R.id.living_budget);
        final EditText education_budget = myview.findViewById(R.id.education_budget);
        final EditText treatment_budget = myview.findViewById(R.id.treatment_budget);
        final EditText investment_budget = myview.findViewById(R.id.investment_budget);
        final EditText other_budget = myview.findViewById(R.id.other_budget);
        Button btnSave = myview.findViewById(R.id.button_save);
        Button btnCancel = myview.findViewById(R.id.button_cancel);
        final int[] foodint = new int[1];
        final int[] clothingint = new int[1];
        final int[] livingint = new int[1];
        final int[] educationint = new int[1];
        final int[] treatmentint = new int[1];
        final int[] investmentint = new int[1];
        final int[] otherint = new int[1];



        btnSave.setOnClickListener(v1 -> {
            BudgetData budgetData;
            String food = food_budget.getText().toString().trim();
            String clothing = clothing_budget.getText().toString().trim();
            String living = living_budget.getText().toString().trim();
            String education = education_budget.getText().toString().trim();
            String treatment= treatment_budget.getText().toString().trim();
            String investment = investment_budget.getText().toString().trim();
            String other = other_budget.getText().toString().trim();

            if (TextUtils.isEmpty(food))
                foodint[0]=0;
            else
                foodint[0] =Integer.parseInt(food);

            if (TextUtils.isEmpty(clothing))
                clothingint[0]=0;
            else
                clothingint[0] =Integer.parseInt(clothing);

            if (TextUtils.isEmpty(living))
                livingint[0]=0;
            else
                livingint[0] =Integer.parseInt(living);

            if (TextUtils.isEmpty(education))
                educationint[0]=0;
            else
                educationint[0] =Integer.parseInt(education);

            if (TextUtils.isEmpty(treatment))
                treatmentint[0]=0;
            else
                treatmentint[0] =Integer.parseInt(treatment);

            if (TextUtils.isEmpty(investment))
                investmentint[0]=0;
            else
                investmentint[0] =Integer.parseInt(investment);

            if (TextUtils.isEmpty(other))
                otherint[0]=0;
            else
                otherint[0] =Integer.parseInt(other);


            budgetData=new BudgetData(foodint[0],clothingint[0],livingint[0],educationint[0],treatmentint[0],investmentint[0],otherint[0]);
            mBudgetDatabase.setValue(budgetData);
            //Toast.makeText(Budget.this,"Data ADDED", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        btnCancel.setOnClickListener(v12 -> dialog.dismiss());



        dialog.show();
    }

    private void drawer() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_budget);
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

