package com.example.nbrb;

import static java.lang.String.valueOf;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.MyViewHolder>  {
    Rate[] rates1;
    Rate[] rates2;

    SettingAdapter(Rate[] rates1, Rate[] rates2) {
        this.rates1 = rates1;
        this.rates2 = rates2;
    }

    @NonNull
    @Override
    public SettingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting, parent, false);
        SettingAdapter.MyViewHolder rt = new SettingAdapter.MyViewHolder(v);
        return rt;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.view_charcode.setText(rates1[position].getCharCode());
        holder.view_scale_name.setText(rates1[position].getScale() + " " + rates1[position].getName());
        holder.switchCompat.setChecked(rates1[position].getVisible());
        holder.switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.switchCompat.isChecked()) {
                    rates1[position].setVisible(true);
                } else {
                    rates1[position].setVisible(false);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return rates1.length;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView view_charcode, view_scale_name;
        SwitchCompat switchCompat;
        ImageButton imageButton;
        View tableLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            tableLayout = itemView;
            view_charcode = itemView.findViewById(R.id.textview_charcode);
            view_scale_name = itemView.findViewById(R.id.textview_scale_name);
            switchCompat = itemView.findViewById(R.id.switch_id);
            imageButton = itemView.findViewById(R.id.button_menu);
        }
    }
}
