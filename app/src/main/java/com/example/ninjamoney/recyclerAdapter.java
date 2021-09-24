package com.example.ninjamoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.IncomeViewHolder>{

    ArrayList<Data> incomelist;
    Context context;
    public recyclerAdapter(ArrayList<Data> incomelist, Context context) {
        this.incomelist=incomelist;
        this.context=context;
    }
    @Override
    public IncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.income_reclycler_data, parent, false);
        return new recyclerAdapter.IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(recyclerAdapter.IncomeViewHolder holder, int position) {
            Data datainstance=incomelist.get(position);
            holder.mDate.setText(datainstance.getDate());
            holder.mNote.setText(datainstance.getNote());
            holder.mTitle.setText(datainstance.getTitle());
            holder.mAmount.setText(String.valueOf(datainstance.getAmount()));

    }

    @Override
    public int getItemCount() {
        return incomelist.size();
    }


    public class IncomeViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle,mNote,mDate,mAmount;
        public IncomeViewHolder(View itemView) {
            super(itemView);

            TextView mTitle=itemView.findViewById(R.id.type_txt_income);
            TextView mNote=itemView.findViewById(R.id.note_txt_income);
            TextView mDate=itemView.findViewById(R.id.date_txt_income);
            TextView mAmount=itemView.findViewById(R.id.amount_txt_income);

        }




    }
}
