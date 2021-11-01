package com.example.nbrb;

import static java.lang.String.valueOf;

import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

public class DataNBRB {
    TextView first_date, second_date;

    DataNBRB(TextView first_date, TextView second_date) {
        this.first_date = first_date;
        this.second_date = second_date;
    }

    // 0 - ошибок нет
    // 1 - не удалось подключиться к серверу
    // 2 - ошибка запроса на сайт
    // 3 - на заданное число еще нет курса валют
    private int state = 0;

    public int getState() {
        return this.state;
    }

    private String adress = "https://www.nbrb.by/services/xmlexrates.aspx?ondate=";

    synchronized public Rate[] getData(String date, String visrates) {
        adress = "https://www.nbrb.by/services/xmlexrates.aspx?ondate=" + date;
        int NUMBER_RATE = 27;

        Rate[] rates = new Rate[NUMBER_RATE];
        for (int i = 0; i < NUMBER_RATE; i++)
            rates[i] = new Rate();

        try {
            Document document = Jsoup.connect(adress).get();

            Elements charCode = document.select("CharCode");
            Elements scale = document.select("Scale");
            Elements name = document.select("Name");
            Elements rate = document.select("Rate");

            for (int i = 0; i < NUMBER_RATE; i++) {
                rates[i].setDate(date);
                rates[i].setCharCode(charCode.get(i).text());
                rates[i].setScale(scale.get(i).text());
                rates[i].setName(name.get(i).text());
                rates[i].setRate(rate.get(i).text());
                if (visrates.contains(rates[i].getCharCode())) {
                    rates[i].setVisible(true);
                } else {
                    rates[i].setVisible(false);
                }
            }
            rates[5].setVisible(true);
            rates[6].setVisible(true);
            rates[17].setVisible(true);

        } catch (UnknownHostException e) {
            state = 1;
        } catch (IOException e) {
            state = 2;
        } catch (IndexOutOfBoundsException e) {
            state = 3;
        } finally {
            notifyAll();
        }
        return rates;
    }

    synchronized public void checkRate() {
        Calendar calendar = Calendar.getInstance();
        String today_date = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR);

        String date = date_yesterday_tomorrow("tomorrow");

        Rate rates = new Rate();

        adress = "https://www.nbrb.by/services/xmlexrates.aspx?ondate=" + date;

        try {
            Document document = Jsoup.connect(adress).get();

            Elements charCode = document.select("CharCode");
            Elements scale = document.select("Scale");
            Elements name = document.select("Name");
            Elements rate = document.select("Rate");

            rates.setCharCode(charCode.get(0).text());
            rates.setScale(scale.get(0).text());
            rates.setName(name.get(0).text());
            rates.setRate(rate.get(0).text());
        } catch (UnknownHostException e) {
            state = 1;
        } catch (IOException e) {
            state = 2;
        } catch (IndexOutOfBoundsException e) {
            state = 3;
        } finally {
            notifyAll();
            if (rates.getCharCode() != null) {
                first_date.post(() -> first_date.setText(today_date));
                second_date.post(() -> second_date.setText(date));
            } else {
                second_date.post(() -> second_date.setText(today_date));
                first_date.post(() -> first_date.setText(date_yesterday_tomorrow("yesterday")));
            }
        }
    }

    synchronized private String date_yesterday_tomorrow(String when) {
        Calendar calendar = Calendar.getInstance();
        final int oneDayToMilliseconds = 86400000; // Столько милисекунд в одном дне.

        switch (when) {
            case "tomorrow":
                //Увлеличиваем дату на один день.
                calendar.setTimeInMillis(calendar.getTimeInMillis() + oneDayToMilliseconds);
                break;
            case "yesterday":
                //уменьшаем дату на один день.
                calendar.setTimeInMillis(calendar.getTimeInMillis() - oneDayToMilliseconds);
                break;
        }

        int nextYear = calendar.get(Calendar.YEAR);
        int nextMonth = calendar.get(Calendar.MONTH) + 1;
        int nextDay = calendar.get(Calendar.DAY_OF_MONTH);

        notifyAll();
        return nextMonth + "/" + nextDay + "/" + nextYear;
    }

}
