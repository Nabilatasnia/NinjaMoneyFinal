package com.example.ninjamoney;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ninjamoney.LoginSignUp.Login;
import com.example.ninjamoney.LoginSignUp.Profile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Income extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private FloatingActionButton fab_income_btn;
    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private RecyclerView recyclerView;
    recyclerAdapter adapter;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        setup();
        Load();
        drawer();
    }

    private void setup() {
        fab_income_btn = findViewById(R.id.add);
        fab_income_btn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        String uid = muser.getUid();
        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

    }

    private void Load() {
        recyclerView = findViewById(R.id.recycler_id_income);
        ArrayList<Data> data = new ArrayList<>();
        adapter = new recyclerAdapter();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        String uid = muser.getUid();
        DatabaseReference dref = db.getReference().child("IncomeData").child(uid);
        Query checkIncome = dref.orderByChild("date");
        checkIncome.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Data dataobj;
                    int amount = Integer.parseInt(dataSnapshot.child("amount").getValue().toString());

                    String date = dataSnapshot.child("date").getValue().toString();
                    String title = dataSnapshot.child("title").getValue().toString();
                    String note = dataSnapshot.child("note").getValue().toString();
                    dataobj = new Data(amount, date, note, title);

                    // Toast.makeText(this,data.getNote(),Toast.LENGTH_SHORT).show();
                    // Toast.makeText(Income.this, dataobj.getNote(), Toast.LENGTH_SHORT).show();

                    data.add(dataobj);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        //data.add(new Data(100,"19 sep 2020","sdcnfoaw","iwdbw"));
        //data.add(new Data(100,"19 sep 2020","sdcnfoaw","iwdbw"));
        //data.add(new Data(100,"19 sep 2020","sdcnfoaw","iwdbw"));
        //data.add(new Data(100,"19 sep 2020","sdcnfoaw","iwdbw"));


        adapter.setData(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        open(v);
    }

    private void open(View v) {

        AlertDialog.Builder mydialog = new AlertDialog.Builder(Income.this);
        LayoutInflater inflater = LayoutInflater.from(Income.this);
        View myview = inflater.inflate(R.layout.activity_input, null);
        mydialog.setView(myview);
        final AlertDialog dialog = mydialog.create();
        dialog.setCancelable(false);
        final EditText edtAmount = myview.findViewById(R.id.amount);
        final EditText edtTitle = myview.findViewById(R.id.title);
        final EditText edtNote = myview.findViewById(R.id.note);


        Button btnSave = myview.findViewById(R.id.button_save);
        Button btnCancel = myview.findViewById(R.id.button_cancel);

        btnSave.setOnClickListener(v1 -> {
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

            String id = mIncomeDatabase.push().getKey();
            String mDate = DateFormat.getDateInstance().format(new Date());

            Data data = new Data(amountint, mDate, note, title);
            mIncomeDatabase.child(id).setValue(data);

            Toast.makeText(Income.this, "Data ADDED", Toast.LENGTH_SHORT).show();
            // Load();

            dialog.dismiss();
        });
        btnCancel.setOnClickListener(v12 -> dialog.dismiss());
        dialog.show();
    }

    private void drawer() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_income);
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
            case R.id.nav_expense:
                Intent intentExp = new Intent(this, Expense.class);
                startActivity(intentExp);
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


