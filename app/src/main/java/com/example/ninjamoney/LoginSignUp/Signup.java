package com.example.ninjamoney.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ninjamoney.MainActivity;
import com.example.ninjamoney.Model.Data;
import com.example.ninjamoney.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    private ImageView login_back_btn;
    private TextInputLayout username_til;
    private TextInputLayout email_til;
    private TextInputLayout password_til;
    private Button signup_btn;
    private Button goto_login_btn;

    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference dRef;
//    FirebaseUser mUser;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setup();
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
//        mUser = firebaseAuth.getCurrentUser();
//        String uid = mUser.getUid();
        dRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    private void setup() {
        login_back_btn = findViewById(R.id.login_back_btn);
        username_til = findViewById(R.id.username_til);
        email_til = findViewById(R.id.email_til);
        password_til = findViewById(R.id.password_til);
        signup_btn = findViewById(R.id.signup_btn);
        goto_login_btn = findViewById(R.id.goto_login_btn);

        login_back_btn.setOnClickListener(this);
        username_til.setOnClickListener(this);
        email_til.setOnClickListener(this);
        password_til.setOnClickListener(this);
        signup_btn.setOnClickListener(this);
        goto_login_btn.setOnClickListener(this);
    }

    private boolean checkIfValidUsername() {
        String username = username_til.getEditText().getText().toString().trim();
        String checkSpace = "\\A\\w{1,20}\\z";
        if (username.isEmpty()) {
            username_til.setError("Field can't be empty");
            return false;
        } else if (username.length() > 20) {
            username_til.setError("Username can't be more than 20 characters");
            return false;
        } else if (!username.matches(checkSpace)) {
            username_til.setError("Username can't have spaces");
            return false;
        } else {
            username_til.setError(null);
            username_til.setErrorEnabled(false);
            return true;
        }
    }

    private boolean checkIfValidEmail() {
        String email = email_til.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (email.isEmpty()) {
            email_til.setError("Field can't be empty");
            return false;
        } else if (!email.matches(checkEmail)) {
            email_til.setError("Invalid email address");
            return false;
        } else {
            email_til.setError(null);
            email_til.setErrorEnabled(false);
            return true;
        }
    }

    private boolean checkIfValidPassword() {
        String password = password_til.getEditText().getText().toString().trim();
        String checkSpace = "\\A\\w{1,20}\\z";
        if (password.isEmpty()) {
            password_til.setError("Field can't be empty");
            return false;
        } else if (password.length() < 5 || password.length() > 10) {
            password_til.setError("Password can't be less than 5 or more than 10 characters");
            return false;
        } else if (!password.matches(checkSpace)) {
            password_til.setError("Password can't have spaces");
            return false;
        } else {
            password_til.setError(null);
            password_til.setErrorEnabled(false);
            return true;
        }
    }


    @Override
    public void onClick(View v) {
        open(v);
    }

    private void open(View v) {

        switch (v.getId()) {

            case R.id.login_back_btn:
                Intent starScreen = new Intent(this, StartScreen.class);
                startActivity(starScreen);
                break;
            case R.id.signup_btn:
                String email = email_til.getEditText().getText().toString().trim();
                String password = password_til.getEditText().getText().toString().trim();

                if (!checkIfValidUsername() | !checkIfValidEmail() | !checkIfValidPassword()) {
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Signup.this, "Signup Complete", Toast.LENGTH_SHORT).show();
                            String username = username_til.getEditText().getText().toString().trim();
                            String id = dRef.push().getKey();
                            user = new User(username, email);
                            dRef.child(id).setValue(user);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Signup.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.goto_login_btn:
                Intent login = new Intent(this, Login.class);
                startActivity(login);
        }
    }
} 