package com.example.ninjamoney;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ninjamoney.LoginSignUp.GlobalVar;
import com.example.ninjamoney.LoginSignUp.Login;
import com.example.ninjamoney.LoginSignUp.StartScreen;
import com.example.ninjamoney.LoginSignUp.User;
import com.example.ninjamoney.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button income;
    private Button expense;
    private Button budget;
    private Button status;
    private Button report;
    private Button donate;
    private TextView text2;

    private FirebaseAuth firebaseAuth;
//    DatabaseReference database;
//    DatabaseReference dRef;
//    FirebaseUser mUser;
//    User user;
    Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
//        text2.setText(GlobalVar.currentUser.getUsername());
//        showUsername();
//        currentUsername();
    }

//    private void showUsername() {
//        Intent intent = getIntent();
//        String User_username = intent.getStringExtra("username");
//        Toast.makeText(MainActivity.this, User_username, Toast.LENGTH_SHORT).show();
//        text2.setText(User_username);
//    }

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