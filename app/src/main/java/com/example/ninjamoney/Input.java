package com.example.ninjamoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Input extends AppCompatActivity {

    private EditText amount;
    private EditText title;
    private EditText source;
    private Spinner category;
    private Button cancel;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        setup();
        Spinner();
    }

    private void setup(){
        amount = findViewById(R.id.amount);
        title = findViewById(R.id.title);
        source = findViewById(R.id.source);
        category = findViewById(R.id.spinner);
        cancel = findViewById(R.id.button_cancel);
        save = findViewById(R.id.button_save);
    }
    private void Spinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}