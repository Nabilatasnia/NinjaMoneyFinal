package com.example.ninjamoney.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ninjamoney.MainActivity;
import com.example.ninjamoney.R;
import com.example.ninjamoney.ResetPassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private ImageView login_back_btn;
    private TextInputLayout email_til;
    private TextInputLayout password_til;
    private Button login_btn;
    private Button signup_btn;
    private CheckBox rememberMe_cb;
    private Button forgotPass_btn;

    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference dRef;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();
        firebaseAuth = FirebaseAuth.getInstance();
        dRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    private void setup() {
        login_back_btn = findViewById(R.id.login_back_btn);
        email_til = findViewById(R.id.email_til);
        password_til = findViewById(R.id.password_til);
        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);
        rememberMe_cb = findViewById((R.id.rememberMe_cb));
        forgotPass_btn = findViewById(R.id.forgotPass_btn);

        login_back_btn.setOnClickListener(this);
        email_til.setOnClickListener(this);
        password_til.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        signup_btn.setOnClickListener(this);
        rememberMe_cb.setOnClickListener(this);
        forgotPass_btn.setOnClickListener(this);
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

//    private void userProfile(){
//        String email = email_til.getEditText().getText().toString().trim();
//        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("Users");
//        Query checkUser = dRef.orderByChild("email").equalTo(email);
//
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    String usernameFromDB = snapshot.child(email).child("username").getValue(String.class);
//                    Toast.makeText(Login.this, usernameFromDB, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//
//                    intent.putExtra("username", usernameFromDB);
//                    startActivity(intent);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
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
            case R.id.login_btn:
                String email = email_til.getEditText().getText().toString().trim();
                String password = password_til.getEditText().getText().toString().trim();

                if (!checkIfValidEmail() | !checkIfValidPassword()) {
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    GlobalVar.currentUser = snapshot.getValue(User.class);
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//                            database = FirebaseDatabase.getInstance();
//                            mUser = firebaseAuth.getCurrentUser();
//                            String uid = mUser.getUid();
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
//                            userProfile();

                        } else {
                            Toast.makeText(Login.this, "Incorrect email address or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.signup_btn:
                Intent signup = new Intent(this, Signup.class);
                startActivity(signup);
        }
    }
} 