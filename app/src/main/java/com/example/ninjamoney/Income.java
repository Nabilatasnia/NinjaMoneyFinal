package com.example.ninjamoney;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Income extends AppCompatActivity  implements View.OnClickListener {
    private FloatingActionButton fab_income_btn;

    @Override
  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        setup();
    }

    private void setup(){
        fab_income_btn = findViewById(R.id.add);
        fab_income_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        open(v);
    }
    private void open(View v) {

                Dialog dialog=new Dialog(Income.this);
                dialog.setContentView(R.layout.activity_input);
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

