package com.example.ninjamoney.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.example.ninjamoney.Input;
import com.example.ninjamoney.MainActivity;
import com.example.ninjamoney.R;
import com.google.firebase.auth.FirebaseAuth;

public class StartScreen extends AppCompatActivity implements View.OnClickListener {

    private Button login_btn;
    private Button signup_btn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        setup();
        firebaseAuth = FirebaseAuth.getInstance();
        checkCurrentUser();
    }

    private void checkCurrentUser() {
        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setup() {
        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);

        login_btn.setOnClickListener(this);
        signup_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        open(v);
    }

    private void open(View v) {
//        Pair[] pairs = new Pair[2];
//        pairs[0] = new Pair<View, String>(login_btn, "transition_login");
//        pairs[1] = new Pair<View, String>(signup_btn, "transition_signup");
        switch (v.getId()) {
            case R.id.login_btn:
                Intent intentLog = new Intent(this, Login.class);
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartScreen.this, pairs);
//                    startActivity(intentLog, options.toBundle());
//                } else {
                startActivity(intentLog);
//                }
                break;
            case R.id.signup_btn:
                Intent intentSignup = new Intent(this, Signup.class);
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartScreen.this, pairs);
//                    startActivity(intentSignup, options.toBundle());
//                } else {
                startActivity(intentSignup);
//                }
                break;
        }
    }
}