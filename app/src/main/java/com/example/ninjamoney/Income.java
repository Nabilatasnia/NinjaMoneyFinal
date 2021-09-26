package com.example.ninjamoney;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class Income extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fab_income_btn;
private FirebaseAuth mAuth;
private DatabaseReference mIncomeDatabase;
private RecyclerView recyclerView;
recyclerAdapter adapter;

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
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        String uid = muser.getUid();
        mIncomeDatabase= FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);

    }
    private void Load()
    {
        recyclerView=findViewById(R.id.recycler_id_income);
        ArrayList<Data> data=new ArrayList<>();
        adapter= new recyclerAdapter();
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        String uid = muser.getUid();
        DatabaseReference dref= db.getReference().child("IncomeData").child(uid);
        Query checkIncome=dref.orderByChild("date");
        checkIncome.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                data.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {

                    Data dataobj ;
                    int amount=Integer.parseInt(dataSnapshot.child("amount").getValue().toString());

                    String date=dataSnapshot.child("date").getValue().toString();
                    String title=dataSnapshot.child("title").getValue().toString();
                    String note=dataSnapshot.child("note").getValue().toString();
                    dataobj=new Data(amount,date,note,title);

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
        final EditText edtAmount=myview.findViewById(R.id.amount);
        final EditText edtTitle=myview.findViewById(R.id.title);
        final EditText edtNote=myview.findViewById(R.id.note);


        Button btnSave=myview.findViewById(R.id.button_save);
        Button btnCancel=myview.findViewById(R.id.button_cancel);

        btnSave.setOnClickListener(v1 -> {
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

            Data data=new Data(amountint, mDate,  note, title);
            mIncomeDatabase.child(id).setValue(data);

            Toast.makeText(Income.this,"Data ADDED", Toast.LENGTH_SHORT).show();
           // Load();

            dialog.dismiss();
        });
        btnCancel.setOnClickListener(v12 -> dialog.dismiss());
        dialog.show();
    }

      /*private void Load() {
          Query query = FirebaseDatabase.getInstance().getReference().child("IncomeData");

        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(query, snapshot -> new Data(
                        Integer.parseInt(snapshot.child("amount").getValue().toString()),
                        snapshot.child("title").getValue().toString(),
                        snapshot.child("id").getValue().toString(),
                        snapshot.child("date").getValue().toString(),
                        snapshot.child("note").getValue().toString()))
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
      }*/



    }


