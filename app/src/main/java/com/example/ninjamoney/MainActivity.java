package com.example.ninjamoney;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ninjamoney.LoginSignUp.Login;
import com.example.ninjamoney.LoginSignUp.StartScreen;
import com.example.ninjamoney.Model.Data;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button income;
    private Button expense;
    private Button budget;
    private Button status;
    private Button report;
    private Button donate;

    Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }


    private void setup(){
        income = findViewById(R.id.income);
        expense = findViewById(R.id.expense);
        budget = findViewById(R.id.budget);
        status = findViewById(R.id.status);
        report = findViewById(R.id.report);
        donate = findViewById(R.id.donate);

        income.setOnClickListener(this);
        expense.setOnClickListener(this);
        budget.setOnClickListener(this);
        status.setOnClickListener(this);
        report.setOnClickListener(this);
        donate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        open(v);
    }

    private void open(View v) {
        switch (v.getId()){
            case R.id.income:
                Intent intentInc = new Intent(this, Input.class);
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
                Intent intentDon = new Intent(this, StartScreen.class);
                startActivity(intentDon);
                break;

        }
    }
}