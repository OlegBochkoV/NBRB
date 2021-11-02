package com.example.nbrb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView textView_toolbar, textView_error, first_date, second_date;
    ImageButton imageButton_setting, imageButton_back;
    RecyclerView recyclerView;
    final int NUMBER_RATE = 27;
    LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
    Rate[] rates1;
    Rate[] rates2;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        init();
        imageButton_setting.setVisibility(View.INVISIBLE);
        DataNBRB nbrb = new DataNBRB(first_date, second_date);

        Thread thread = new Thread(() -> {

            ArrayList<String> templist = loadArrayList("myrates");
            String str = "";
            for (int i = 0; i < templist.size(); i++) {
                str += templist.get(i) + " ";
            }

            nbrb.checkRate();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            rates1 = nbrb.getData(first_date.getText().toString(), str);
            rates2 = nbrb.getData(second_date.getText().toString(), str);

            if (rates1[0].getName() == null) {
                setTextError("Не удалось получить курсы валют.");
                return;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageButton_setting.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(llm);
                    MyAdapter adapter = new MyAdapter(rates1, rates2);
                    recyclerView.setAdapter(adapter);
                }
            });
        });
        thread.start();

        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imageButton_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setLayoutManager(llm);
                            SettingAdapter settingAdapter = new SettingAdapter(rates1, rates2);
                            recyclerView.setAdapter(settingAdapter);


                            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                            itemTouchHelper.attachToRecyclerView(recyclerView);

                        }
                    });
                    flag = false;
                    textView_toolbar.setText(R.string.setting_rate);
                    imageButton_setting.setImageResource(R.drawable.image_confirm);

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            for(int i=0;i<rates1.length;i++){
//                                Log.d("TAG",i + "-" + rates1[i].getVisible());
//                            }
                            saveArrayList("myrates");
                            recyclerView.setLayoutManager(llm);
                            MyAdapter adapter = new MyAdapter(rates1, rates2);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                    flag = true;
                    textView_toolbar.setText(R.string.currency_rate);
                    imageButton_setting.setImageResource(R.drawable.image_setting);
                }
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPos = viewHolder.getAdapterPosition();
            int toPos = target.getAdapterPosition();
            swap(fromPos, toPos);

            recyclerView.getAdapter().notifyItemMoved(fromPos, toPos);


            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    private void swap(int fromPos, int toPos) {
        Rate temp_rate1 = new Rate();
        temp_rate1.setRate(rates1[fromPos].getRate());
        temp_rate1.setName(rates1[fromPos].getName());
        temp_rate1.setScale(rates1[fromPos].getScale());
        temp_rate1.setVisible(rates1[fromPos].getVisible());
        temp_rate1.setDate(rates1[fromPos].getDate());
        temp_rate1.setCharCode(rates1[fromPos].getCharCode());

        Rate temp_rate2 = new Rate();
        temp_rate2.setRate(rates2[fromPos].getRate());
        temp_rate2.setName(rates2[fromPos].getName());
        temp_rate2.setScale(rates2[fromPos].getScale());
        temp_rate2.setVisible(rates2[fromPos].getVisible());
        temp_rate2.setDate(rates2[fromPos].getDate());
        temp_rate2.setCharCode(rates2[fromPos].getCharCode());

        rates1[fromPos].setRate(rates1[toPos].getRate());
        rates1[fromPos].setCharCode(rates1[toPos].getCharCode());
        rates1[fromPos].setDate(rates1[toPos].getDate());
        rates1[fromPos].setVisible(rates1[toPos].getVisible());
        rates1[fromPos].setName(rates1[toPos].getName());
        rates1[fromPos].setScale(rates1[toPos].getScale());

        rates2[fromPos].setRate(rates2[toPos].getRate());
        rates2[fromPos].setCharCode(rates2[toPos].getCharCode());
        rates2[fromPos].setDate(rates2[toPos].getDate());
        rates2[fromPos].setVisible(rates2[toPos].getVisible());
        rates2[fromPos].setName(rates2[toPos].getName());
        rates2[fromPos].setScale(rates2[toPos].getScale());

        rates1[toPos].setScale(temp_rate1.getScale());
        rates1[toPos].setRate(temp_rate1.getRate());
        rates1[toPos].setVisible(temp_rate1.getVisible());
        rates1[toPos].setName(temp_rate1.getName());
        rates1[toPos].setCharCode(temp_rate1.getCharCode());
        rates1[toPos].setDate(temp_rate1.getDate());

        rates2[toPos].setScale(temp_rate2.getScale());
        rates2[toPos].setRate(temp_rate2.getRate());
        rates2[toPos].setVisible(temp_rate2.getVisible());
        rates2[toPos].setName(temp_rate2.getName());
        rates2[toPos].setCharCode(temp_rate2.getCharCode());
        rates2[toPos].setDate(temp_rate2.getDate());
    }

    public void init() {
        textView_toolbar = findViewById(R.id.text_toolbar_id);
        textView_error = findViewById(R.id.error_message);
        first_date = findViewById(R.id.first_date);
        second_date = findViewById(R.id.second_date);
        imageButton_setting = findViewById(R.id.setting_button_id);
        imageButton_back = findViewById(R.id.back_button_id);
        recyclerView = findViewById(R.id.recycler_list);
    }

    public void setTextError(String message_error) {
        textView_error.post(() -> textView_error.setText(message_error));
        imageButton_setting.post(() -> imageButton_setting.setVisibility(View.INVISIBLE));
        textView_error.post(() -> textView_error.setVisibility(View.VISIBLE));
    }

    private void saveArrayList(String name) {
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < rates1.length; i++) {
            if (rates1[i].getVisible()) {
                String temp = rates1[i].getCharCode();
                arrayList.add(temp);
            }
        }

        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        StringBuilder sb = new StringBuilder();

        for (String s : arrayList)
            sb.append(s).append("<s>");

        sb.delete(sb.length() - 3, sb.length());
        editor.putString(name, sb.toString()).apply();
    }

    private ArrayList<String> loadArrayList(String name) {
        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String[] strings = prefs.getString(name, "").split("<s>");
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(strings));
        return list;
    }
}