package com.example.ninjamoney.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ninjamoney.Balance;
import com.example.ninjamoney.Budget;
import com.example.ninjamoney.Donate;
import com.example.ninjamoney.Expense;
import com.example.ninjamoney.Income;
import com.example.ninjamoney.MainActivity;
import com.example.ninjamoney.R;
import com.example.ninjamoney.Report;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    TextInputLayout edit_username_til;
    TextInputLayout edit_pass_til;
    TextInputLayout confirm_pass_til;
    TextView username_tv;
    TextView email_tv;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button logout_btn;
    Button update_btn;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference dRef;
    private FirebaseUser firebaseUser;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setup();
        drawer();
        firebase();
    }

    private void setup() {
        edit_username_til = findViewById(R.id.edit_username_til);
        edit_pass_til = findViewById(R.id.edit_pass_til);
        confirm_pass_til = findViewById(R.id.confirm_pass_til);
        logout_btn = findViewById(R.id.logout_btn);
        update_btn = findViewById(R.id.update_btn);
        username_tv = findViewById(R.id.username_tv);
        email_tv = findViewById(R.id.email_tv);

        logout_btn.setOnClickListener(this);
        update_btn.setOnClickListener(this);
        edit_username_til.setOnClickListener(this);
        edit_pass_til.setOnClickListener(this);
        confirm_pass_til.setOnClickListener(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
    }

    private void firebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();
        dRef = database.getReference().child("Users").child(uid);

        Query query = dRef.orderByChild("email");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                if (snapshot.exists()) {
                    String name = snapshot.child("username").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    username_tv.setText(name);
                    email_tv.setText(email);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void drawer() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent intentHome = new Intent(this, MainActivity.class);
            startActivity(intentHome);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intentHome = new Intent(this, MainActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_income:
                Intent intentInc = new Intent(this, Income.class);
                startActivity(intentInc);
                break;
            case R.id.nav_expense:
                Intent intentBud = new Intent(this, Expense.class);
                startActivity(intentBud);
                break;
            case R.id.nav_balance:
                Intent intentStat = new Intent(this, Balance.class);
                startActivity(intentStat);
                break;
            case R.id.nav_report:
                Intent intentRep = new Intent(this, Report.class);
                startActivity(intentRep);
                break;
            case R.id.nav_donate:
                Intent intentDon = new Intent(this, Donate.class);
                startActivity(intentDon);
                break;
            case R.id.nav_budget:
                Intent intentProf = new Intent(this, Budget.class);
                startActivity(intentProf);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intentOut = new Intent(this, Login.class);
                startActivity(intentOut);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean checkIfValidPassword() {
        String password = edit_pass_til.getEditText().getText().toString().trim();
        String checkSpace = "\\A\\w{1,20}\\z";
        if (password.isEmpty()) {
            edit_pass_til.setError("Field can't be empty");
            return false;
        } else if (password.length() < 5 || password.length() > 10) {
            edit_pass_til.setError("Password can't be less than 5 or more than 10 characters");
            return false;
        } else if (!password.matches(checkSpace)) {
            edit_pass_til.setError("Password can't have spaces");
            return false;
        } else {
            edit_pass_til.setError(null);
            edit_pass_til.setErrorEnabled(false);
            return true;
        }
    }

    private boolean checkIfValidUsername() {
        String username = edit_username_til.getEditText().getText().toString().trim();
        String checkSpace = "\\A\\w{1,20}\\z";
        if (username.length() > 20) {
            edit_username_til.setError("Username can't be more than 20 characters");
            return false;
        } else if (!username.matches(checkSpace)) {
            edit_username_til.setError("Username can't have spaces");
            return false;
        } else {
            edit_username_til.setError(null);
            edit_username_til.setErrorEnabled(false);
            return true;
        }
    }

    public void editUsername() {
        String username = edit_username_til.getEditText().getText().toString().trim();
        if (!username.isEmpty()) {
            if (checkIfValidUsername()) {
                Query query = dRef.orderByChild("username");
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                        if (snapshot.exists()) {
                            String jinish = snapshot.getKey().toString();
                            String email = snapshot.child("email").getValue().toString().trim();
                            User data = new User(username, email);
                            dRef.child(jinish).setValue(data);
                            Toast.makeText(Profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            username_tv.setText(username);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            } else {
                Toast.makeText(Profile.this, "New Username is not valid", Toast.LENGTH_SHORT).show();
            }
        } else {
            /*Toast.makeText(Profile.this, "Username Updated", Toast.LENGTH_SHORT).show();*/
        }
    }

    private void editPass() {
        String newPass = edit_pass_til.getEditText().getText().toString().trim();
        String confirmPass = confirm_pass_til.getEditText().getText().toString().trim();
        if (!newPass.isEmpty()) {
            if (newPass.equals(confirmPass)) {
                if (checkIfValidPassword()) {
                    firebaseUser.updatePassword(newPass).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Profile.this, "Password Updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(Profile.this, "Password Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    edit_pass_til.setError("Invalid Password");
                }
            } else {
                confirm_pass_til.setError("Password don't match. Try Again");
            }
        }else {
            /*Toast.makeText(Profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();*/
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_btn:
                FirebaseAuth.getInstance().signOut();
                Intent intentOut = new Intent(this, Login.class);
                startActivity(intentOut);
                break;
            case R.id.update_btn:
                editUsername();
                editPass();
        }
    }


}