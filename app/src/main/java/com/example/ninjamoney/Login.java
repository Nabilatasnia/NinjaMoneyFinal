package com.example.ninjamoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private EditText mPass;
    private Button btnLogin;
    private TextView mForgotPassword;
    private TextView mSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setup();
    }

    private void setup(){
        mEmail = findViewById(R.id.email_login);
        mPass = findViewById(R.id.pasword_login);
        btnLogin = findViewById(R.id.btn_login);
        mForgotPassword = findViewById(R.id.forgot_password);
        mSignup = findViewById(R.id.signup_reg);

        btnLogin.setOnClickListener(this);
        mSignup.setOnClickListener(this);
        mForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        open(v);
    }

    private void open(View v) {
        switch (v.getId()){
            case R.id.btn_login:
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
                break;
            case R.id.signup_reg:
                startActivity(new Intent(getApplicationContext(), Registration.class));
                break;
            case R.id.forgot_password:
                Intent intentForPass = new Intent(this, ResetPassword.class);
                startActivity(intentForPass);
                break;
        }
    }
} 