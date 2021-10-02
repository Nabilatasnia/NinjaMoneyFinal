package com.example.ninjamoney;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class expenseRecyclerAdapter extends RecyclerView.Adapter<expenseRecyclerAdapter.ExpenseViewHolder> {
    private ArrayList<DataExpense> data=new ArrayList<>();

    public expenseRecyclerAdapter()
    {
    }

    public void setData(ArrayList<DataExpense> data) {
        this.data = data;
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_recycler_data,parent,false);
        expenseRecyclerAdapter.ExpenseViewHolder holder =new expenseRecyclerAdapter.ExpenseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(expenseRecyclerAdapter.ExpenseViewHolder holder, int position) {
        holder.mAmount.setText(Integer.toString(data.get(position).getAmount()));
        holder.mDate.setText(data.get(position).getDate());
        holder.mTitle.setText(data.get(position).getTitle());
        holder.mCategory.setText(data.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle,mCategory,mDate,mAmount;
        public ExpenseViewHolder(View itemView) {
            super(itemView);
            mTitle=itemView.findViewById(R.id.type_txt_expense);
            mCategory=itemView.findViewById(R.id.category_txt_expense);
            mDate=itemView.findViewById(R.id.date_txt_expense);
            mAmount=itemView.findViewById(R.id.total_txt_expense);
        }
    }
}
