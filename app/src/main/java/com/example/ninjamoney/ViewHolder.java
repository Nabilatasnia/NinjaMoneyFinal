package com.example.ninjamoney;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ninjamoney.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mview;
    public ViewHolder(View itemView) {
        super(itemView);
        mview=itemView;
    }



    public void setTitle(String title)
    {
        TextView mTitle=mview.findViewById(R.id.type_txt_income);
        mTitle.setText(title);
    }
    public void setNote(String note)
    {
        TextView mNote=mview.findViewById(R.id.type_txt_income);
        mNote.setText(note);
    }
    public void setDate(String date)
    {
        TextView mDate=mview.findViewById(R.id.date_txt_income);
        mDate.setText(date);
    }
    public void setAmount(int amount)
    {
        TextView mAmount=mview.findViewById(R.id.amount_txt_income);
        String stamount=String.valueOf(amount);
        mAmount.setText(stamount);
    }
}
