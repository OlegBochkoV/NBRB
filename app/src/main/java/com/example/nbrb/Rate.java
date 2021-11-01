package com.example.nbrb;

public class Rate {

    private boolean visible;

    private String date;

    private String charCode;

    private String scale;

    private String name;

    private String rate;

    public void setVisible(boolean state){
        visible = state;
    }

    public boolean getVisible(){
        return visible;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getCharCode() {
        return this.charCode;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getScale() {
        return this.scale;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return this.rate;
    }


}
