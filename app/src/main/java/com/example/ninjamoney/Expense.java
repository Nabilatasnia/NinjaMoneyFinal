package com.example.ninjamoney;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class Expense extends AppCompatActivity  implements View.OnClickListener {
    private FloatingActionButton fab_expense_btn;
    private FirebaseAuth mAuth;
    private LinearLayoutManager layoutManager;
    private DatabaseReference mExpenseDatabase;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        setup();
    }
    private void setup(){
        fab_expense_btn = findViewById(R.id.add);
        fab_expense_btn.setOnClickListener(this);
        recyclerView=findViewById(R.id.recycler_id_expense);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser muser=mAuth.getCurrentUser();
        String uid=muser.getUid();
        mExpenseDatabase= FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);
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
        final EditText edtAmount=myview.findViewById(R.id.amount);
        final EditText edtTitle=myview.findViewById(R.id.title);
        final EditText edtFrom=myview.findViewById(R.id.source);
        final EditText edtNote=myview.findViewById(R.id.note);


        Button btnSave=myview.findViewById(R.id.button_save);
        Button btnCancel=myview.findViewById(R.id.button_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=edtTitle.getText().toString().trim();
                String amount=edtAmount.getText().toString().trim();
                //String source=edtSource.getText().toString().trim();
                String note=edtNote.getText().toString().trim();

                if (TextUtils.isEmpty(title)){
                    edtTitle.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(amount)){
                    edtAmount.setError("Required Field..");
                    return;
                }

                int amountint=Integer.parseInt(amount);

                if (TextUtils.isEmpty(note)){
                    edtNote.setError("Required Field..");
                    return;
                }

                String id=mExpenseDatabase.push().getKey();
                String mDate = DateFormat.getDateInstance().format(new Date());

                Data data=new Data(amountint,title, mDate,note);
                mExpenseDatabase.child(id).setValue(data);

                Toast.makeText(Expense.this,"Data ADDED", Toast.LENGTH_SHORT).show();


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
}