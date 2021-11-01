package com.example.nbrb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RateViewHolder> {
    Rate[] rates1;
    Rate[] rates2;

    Rate[] temp_rates1;
    Rate[] temp_rates2;

    MyAdapter(Rate[] rates1, Rate[] rates2) {
        this.rates1 = rates1;
        this.rates2 = rates2;
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        RateViewHolder rt = new RateViewHolder(v);
        return rt;
    }

    @Override
    public void onBindViewHolder(@NonNull RateViewHolder holder, int position) {
        holder.charcode.setText(temp_rates1[position].getCharCode());
        holder.rate_first.setText(temp_rates1[position].getRate());
        holder.rate_second.setText(temp_rates2[position].getRate());
        holder.scale_name.setText(temp_rates1[position].getScale() + " " + temp_rates1[position].getName());


//        if (rates1[position].getVisible()) {
//            holder.charcode.setText(rates1[position].getCharCode());
//            holder.rate_first.setText(rates1[position].getRate());
//            holder.rate_second.setText(rates2[position].getRate());
//            holder.scale_name.setText(rates1[position].getScale() + " " + rates1[position].getName());
//        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (int i = 0; i < rates1.length; i++) {
            if (rates1[i].getVisible())
                ++count;
        }
        temp_rates1 = new Rate[count];
        temp_rates2 = new Rate[count];

        for (int i = 0; i < count; i++) {
            temp_rates1[i] = new Rate();
            temp_rates2[i] = new Rate();
        }

        int j = 0;
        for (int i = 0; i < rates1.length; i++) {
            if (rates1[i].getVisible()){
                temp_rates1[j].setVisible(rates1[i].getVisible());
                temp_rates1[j].setDate(rates1[i].getDate());
                temp_rates1[j].setCharCode(rates1[i].getCharCode());
                temp_rates1[j].setScale(rates1[i].getScale());
                temp_rates1[j].setName(rates1[i].getName());
                temp_rates1[j].setRate(rates1[i].getRate());

                temp_rates2[j].setVisible(rates2[i].getVisible());
                temp_rates2[j].setDate(rates2[i].getDate());
                temp_rates2[j].setCharCode(rates2[i].getCharCode());
                temp_rates2[j].setScale(rates2[i].getScale());
                temp_rates2[j].setName(rates2[i].getName());
                temp_rates2[j].setRate(rates2[i].getRate());

                ++j;
            }
        }
        return count;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class RateViewHolder extends RecyclerView.ViewHolder {
        TextView charcode, rate_first, rate_second, scale_name;

        public RateViewHolder(View itemView) {
            super(itemView);

            charcode = itemView.findViewById(R.id.textview_charcode);
            rate_first = itemView.findViewById(R.id.textview_firstdate);
            rate_second = itemView.findViewById(R.id.textview_seconddate);
            scale_name = itemView.findViewById(R.id.textview_scale_name);
        }
    }

//    private boolean checkDate(String first_date, String second_date) {
//        boolean state = true;
//
//        ArrayList<Integer> date1 = new ArrayList<>();
//        for (String part : first_date.split("\\/")) {
//            date1.add(Integer.parseInt(part));
//        }
//        ArrayList<Integer> date2 = new ArrayList<>();
//        for (String part : second_date.split("\\/")) {
//            date2.add(Integer.parseInt(part));
//        }
//
//        if (date1.get(2).equals(date2.get(2))) {
//            // если года равные
//            if(date1.get(0).equals(date2.get(0))){
//                // если месяца равны
//                if(date1.get(1) > (date2.get(1))){
//                    state = false;
//                }
//            }
//            else if(date1.get(0) > date2.get(0)){
//                state = false;
//            }
//        } else if (date1.get(2) > date2.get(2)) {
//            // если год у первого больше чем у второго
//            state = false;
//        }
//
//        return state;
//    }
}
