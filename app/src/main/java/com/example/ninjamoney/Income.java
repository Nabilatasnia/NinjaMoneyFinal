package com.example.ninjamoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ninjamoney.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.DateFormat;
import java.util.Date;

public class Income extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fab_income_btn;
private FirebaseAuth mAuth;
private LinearLayoutManager layoutManager;
private DatabaseReference mIncomeDatabase;
private RecyclerView recyclerView;
private FirebaseRecyclerAdapter adapter;
    @Override
  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        setup();
        Load();
    }

    private void setup(){
        fab_income_btn = findViewById(R.id.add);
        fab_income_btn.setOnClickListener(this);
        recyclerView=findViewById(R.id.recycler_id_income);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        String uid = muser.getUid();
        mIncomeDatabase= FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
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
        final EditText edtAmount=myview.findViewById(R.id.amount);
        final EditText edtTitle=myview.findViewById(R.id.title);
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

                String id = mIncomeDatabase.push().getKey();
                String mDate = DateFormat.getDateInstance().format(new Date());

                Data data=new Data(amountint, title, id, mDate, note);
                mIncomeDatabase.child(id).setValue(data);

                Toast.makeText(Income.this,"Data ADDED", Toast.LENGTH_SHORT).show();


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

      private void Load(){
        Query query = FirebaseDatabase.getInstance().getReference().child("IncomeData");

        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(query, new SnapshotParser<Data>() {
                    @NonNull
                    @Override
                    public Data parseSnapshot(@NonNull DataSnapshot snapshot) {

                        return new Data(
                                Integer.parseInt(snapshot.child("amount").getValue().toString()),
                                snapshot.child("title").getValue().toString(),
                                snapshot.child("id").getValue().toString(),
                                snapshot.child("date").getValue().toString(),
                                snapshot.child("note").getValue().toString());
                    }
                })
                .build();


        adapter = new FirebaseRecyclerAdapter<Data, ViewHolder>(options) {
       @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.income_reclycler_data, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder,final int position, Data model) {

                    holder.setTitle(model.getTitle());
                    holder.setNote(model.getNote());
                    holder.setDate(model.getDate());
                    holder.setAmount(model.getAmount());
                 //   holder.root.setOnClickListener(new View.OnClickListener() {
            }


        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }



    }


