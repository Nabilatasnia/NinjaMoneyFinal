package com.example.ninjamoney;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ninjamoney.LoginSignUp.Login;
import com.example.ninjamoney.LoginSignUp.Profile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class Expense extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private FloatingActionButton fab_expense_btn;
    private FirebaseAuth mAuth;
    private LinearLayoutManager layoutManager;
    private DatabaseReference mExpenseDatabase;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        /*ArrayList<Data> hospitals = new ArrayList<>();
        hospitals.add(new Data(99, "9/9/99","ddd","sfsfs"));
        hospitals.add(new Data(99, "9/9/99","ddd","sfsfs"));
        hospitals.add(new Data(99, "9/9/99","ddd","sfsfs"));
        hospitals.add(new Data(99, "9/9/99","ddd","sfsfs"));
        hospitals.add(new Data(99, "9/9/99","ddd","sfsfs"));
        hospitals.add(new Data(99, "9/9/99","ddd","sfsfs"));
        hospitals.add(new Data(99, "9/9/99","ddd","sfsfs"));
        recyclerAdapter adapter = new recyclerAdapter(this);
        adapter.setHospitals(hospitals);
        recyclerView=findViewById(R.id.recycler_id_expense);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));*/
        setup();
        drawer();
    }

    private void setup() {
        fab_expense_btn = findViewById(R.id.add);
        fab_expense_btn.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_id_expense);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        String uid = muser.getUid();
        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    public void onClick(View v) {
        open(v);
    }

    private void open(View v) {

        AlertDialog.Builder mydialog = new AlertDialog.Builder(Expense.this);
        LayoutInflater inflater = LayoutInflater.from(Expense.this);
        View myview = inflater.inflate(R.layout.activity_input_expense, null);
        mydialog.setView(myview);
        final AlertDialog dialog = mydialog.create();
        dialog.setCancelable(false);
        final EditText edtAmount = myview.findViewById(R.id.amount);
        final EditText edtTitle = myview.findViewById(R.id.title);
        final EditText edtFrom = myview.findViewById(R.id.source);
        final EditText edtNote = myview.findViewById(R.id.note);


        Button btnSave = myview.findViewById(R.id.button_save);
        Button btnCancel = myview.findViewById(R.id.button_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString().trim();
                String amount = edtAmount.getText().toString().trim();
                //String source=edtSource.getText().toString().trim();
                String note = edtNote.getText().toString().trim();

                if (TextUtils.isEmpty(title)) {
                    edtTitle.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(amount)) {
                    edtAmount.setError("Required Field..");
                    return;
                }

                int amountint = Integer.parseInt(amount);

                if (TextUtils.isEmpty(note)) {
                    edtNote.setError("Required Field..");
                    return;
                }

                String id = mExpenseDatabase.push().getKey();
                String mDate = DateFormat.getDateInstance().format(new Date());

                Data data = new Data(amountint, mDate, note, title);
                mExpenseDatabase.child(id).setValue(data);

                Toast.makeText(Expense.this, "Data ADDED", Toast.LENGTH_SHORT).show();


                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void drawer() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_expense);
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
            case R.id.nav_budget:
                Intent intentBud = new Intent(this, Budget.class);
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
            case R.id.nav_profile:
                Intent intentProf = new Intent(this, Profile.class);
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
}