package com.example.ninjamoney;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ninjamoney.BalanceCalculation.Balance;
import com.example.ninjamoney.BalanceCalculation.BalanceDataExpense;
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

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;

public class Expense extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private FloatingActionButton fab_expense_btn;
    private FirebaseAuth mAuth;
    private DatabaseReference mExpenseDatabase;
    private FirebaseUser muser;
    private DatabaseReference dRef;
    private RecyclerView recyclerView;
    private Button datebutton;
    DatePickerDialog datePickerDialog;
    private Spinner from_spinner;
    private Spinner category_spinner;
    expenseRecyclerAdapter adapter;
    TextView expense_txt_result;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    int monthtotalexpense, totalexpense;
    private TextView totalexpense_text;
    int food, clothing, living, education, treatment, investment, other;
    int monthtotal, banktotal, cashtotal, total, bkashtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        setup();
        Load();
        drawer();
        String uid = muser.getUid();
        dRef = FirebaseDatabase.getInstance().getReference().child("BalanceData").child(uid);
    }

    private void setup() {
        fab_expense_btn = findViewById(R.id.add);
        fab_expense_btn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        muser = mAuth.getCurrentUser();
        String uid = muser.getUid();
        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);
        totalexpense_text = findViewById(R.id.expense_txt_result);
        drawerLayout = findViewById(R.id.drawer_layout);
        expense_txt_result = findViewById(R.id.expense_txt_result);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year; //OCT 1 2021
    }

    private String getMonthFormat(int month) {
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
        }
        return "JAN";
    }

    private void Load() {
        recyclerView = findViewById(R.id.recycler_id_expense);

        ArrayList<DataExpense> data = new ArrayList<>();
        adapter = new expenseRecyclerAdapter();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        String uid = muser.getUid();
        DatabaseReference dref = db.getReference().child("ExpenseData").child(uid);
        Query checkIncome = dref.orderByChild("date");
        checkIncome.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                data.clear();
                monthtotalexpense = 0;
                food = 0;
                clothing = 0;
                living = 0;
                education = 0;
                treatment = 0;
                investment = 0;
                other = 0;
                monthtotal = 0;
                banktotal = 0;
                cashtotal = 0;
                bkashtotal = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    LocalDate currentdate = LocalDate.now();
                    Month currentMonth = currentdate.getMonth(); //OCTOBER

                    //jodi recussing thake add korbe
                    DataExpense dataobj;
                    int amount = Integer.parseInt(dataSnapshot.child("amount").getValue().toString());
                    String from = dataSnapshot.child("from").getValue().toString();
                    String date = dataSnapshot.child("date").getValue().toString();
                    String category = dataSnapshot.child("category").getValue().toString();
                    String title = dataSnapshot.child("title").getValue().toString();
                    String note = dataSnapshot.child("note").getValue().toString();
                    if (from.equals("Bank")) {
                        banktotal += amount;
                    } else if (from.equals("Cash")) {
                        cashtotal += amount;
                    } else {
                        bkashtotal += amount;
                    }
                    if (category.equals("Food")) {
                        food += amount;
                    } else if (category.equals("Clothing")) {
                        clothing += amount;
                    } else if (category.equals("Living")) {
                        living += amount;
                    } else if (category.equals("Education")) {
                        education += amount;
                    } else if (category.equals("Treatment")) {
                        treatment += amount;
                    } else if (category.equals("Investment")) {
                        investment += amount;
                    } else {
                        other += amount;
                    }
                            dataobj = new DataExpense(amount, category, from, date, title, note);
                    if (String.valueOf(currentMonth).startsWith(date.substring(0, 2))) {
                        monthtotalexpense += amount;
                        data.add(dataobj);
                    }
                    total = bkashtotal + cashtotal + banktotal;
                    dRef.child("expenseTotal").setValue(total);
                    dRef.child("expenseCash").setValue(cashtotal);
                    dRef.child("expenseBank").setValue(banktotal);
                    dRef.child("expenseMobile").setValue(bkashtotal);
                }
                adapter.notifyDataSetChanged();
                totalexpense_text.setText(String.valueOf(monthtotalexpense+" ৳"));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        totalexpense = food + clothing + living + education + treatment + investment + other;
        adapter.setData(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        final EditText edtAmount = myview.findViewById(R.id.expense_amount);
        final EditText edtTitle = myview.findViewById(R.id.expense_title);
        final EditText edtNote = myview.findViewById(R.id.expense_note);
        // initDatePicker();

        from_spinner = myview.findViewById(R.id.from_spinner);
        category_spinner = myview.findViewById(R.id.category_spinner);
        final String[] from = new String[1];
        from[0] = "Bank";
        final String[] category = new String[1];
        category[0] = "Other";
        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category[0] = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), category[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        from_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                from[0] = parent.getItemAtPosition(position).toString();
               /* Toast.makeText(parent.getContext(), from[0], Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        final String[] date = new String[1];
        date[0] = getTodaysDate();
        DatePickerDialog.OnDateSetListener datesetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                date[0] = makeDateString(day, month, year);
                datebutton.setText(date[0]);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, datesetListener, year, month, day);
        datebutton = myview.findViewById(R.id.datepicker);
        datebutton.setText(getTodaysDate());


        Button btnSave = myview.findViewById(R.id.button_save);
        Button btnCancel = myview.findViewById(R.id.button_cancel);
        datebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });


        btnSave.setOnClickListener(v1 -> {
            String title = edtTitle.getText().toString().trim();
            String amount = edtAmount.getText().toString().trim();
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
            //String mDate = DateFormat.getDateInstance().format(new Date());

            DataExpense data = new DataExpense(amountint, category[0], from[0], date[0], title, note);

            mExpenseDatabase.child(id).setValue(data);

            Toast.makeText(Expense.this, "Data ADDED", Toast.LENGTH_SHORT).show();
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