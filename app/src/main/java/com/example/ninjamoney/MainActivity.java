package com.example.ninjamoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ninjamoney.LoginSignUp.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
    //Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        firebase();
    }

    private void firebase(){
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();
        dRef = database.getReference().child("Users").child(uid);
        Toast.makeText(MainActivity.this, uid, Toast.LENGTH_SHORT).show();

        Query query = dRef.orderByChild("email");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                if(snapshot.exists()){
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

    private void setup(){
        income = findViewById(R.id.income);
        expense = findViewById(R.id.expense);
        budget = findViewById(R.id.budget);
        status = findViewById(R.id.status);
        report = findViewById(R.id.report);
        donate = findViewById(R.id.donate);
        text2 = findViewById(R.id.text2);

        income.setOnClickListener(this);
        expense.setOnClickListener(this);
        budget.setOnClickListener(this);
        status.setOnClickListener(this);
        report.setOnClickListener(this);
        donate.setOnClickListener(this);
        text2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        open(v);
    }

    private void open(View v) {
        switch (v.getId()){
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
                Intent intentStat = new Intent(this, Status.class);
                startActivity(intentStat);
                break;
            case R.id.report:
                FirebaseAuth.getInstance().signOut();
                Intent intentRep = new Intent(this, Login.class);
                startActivity(intentRep);
                break;
            case R.id.donate:
                Intent intentDon = new Intent(this, Donate.class);
                startActivity(intentDon);
                break;

        }
    }
}