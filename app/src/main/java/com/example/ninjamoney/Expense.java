package com.example.ninjamoney;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Expense extends AppCompatActivity  implements View.OnClickListener {
    private FloatingActionButton fab_expense_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        setup();
    }
    private void setup(){
        fab_expense_btn = findViewById(R.id.add);
        fab_expense_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        open(v);
    }
    private void open(View v) {

        Dialog dialog=new Dialog(Expense.this);
        dialog.setContentView(R.layout.activity_input_expense);
        dialog.setTitle("Title...");
        dialog.show();
        Button dialogButton = (Button) dialog.findViewById(R.id.button_cancel);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}