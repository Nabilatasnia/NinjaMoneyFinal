package com.example.ninjamoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private EditText mPass;
    private Button btnReg;
    private TextView mSignin;
    private ProgressDialog mDialog;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setup();
        mDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
    }
    private void setup(){
        mEmail = findViewById(R.id.email_reg);
        mPass = findViewById(R.id.password_reg);
        btnReg = findViewById(R.id.btn_reg);
        mSignin = findViewById(R.id.signin);

        btnReg.setOnClickListener(this);
        mSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        open(v);
    }

    private void open(View v) {
        switch (v.getId()){
            case R.id.btn_reg:
                String email = mEmail.getText().toString().trim();
                String pass = mPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email Required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    mPass.setError("Password Required");
                    return;
                }
                mDialog.setMessage("Registering");
                 mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()){
                             Toast.makeText(getApplicationContext(), "Registration Complete", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(getApplicationContext(), Login.class));
                         }
                         else{
                             Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                         }
                         mDialog.dismiss();
                     }
                 });
                break;
            case R.id.signin:
                Intent intentLog = new Intent(this, Login.class);
                startActivity(intentLog);
                break;
        }
    }
}