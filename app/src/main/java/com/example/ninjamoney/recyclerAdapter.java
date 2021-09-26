package com.example.ninjamoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.IncomeViewHolder>{
    private ArrayList<Data> data=new ArrayList<>();

    public recyclerAdapter() {
    }

    @Override
    public IncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.income_reclycler_data,parent,false);
        IncomeViewHolder holder =new IncomeViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(recyclerAdapter.IncomeViewHolder holder, int position) {
        holder.mAmount.setText(Integer.toString(data.get(position).getAmount()));
        holder.mDate.setText(data.get(position).getDate());
        holder.mTitle.setText(data.get(position).getTitle());
        holder.mNote.setText(data.get(position).getNote());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
    public class IncomeViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle,mNote,mDate,mAmount;
        public IncomeViewHolder(View itemView) {
            super(itemView);

            mTitle=itemView.findViewById(R.id.type_txt_income);
            mNote=itemView.findViewById(R.id.note_txt_income);
            mDate=itemView.findViewById(R.id.date_txt_income);
            mAmount=itemView.findViewById(R.id.amount_txt_income);

        }

    }
}
