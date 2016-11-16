package com.sky.pm.model;

/**
 * Created by 李彬 on 2016/11/13.
 */
public class PieModel {
    private String text;
    private int corlor;

    public PieModel(String text, int corlor) {
        this.text = text;
        this.corlor = corlor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCorlor() {
        return corlor;
    }

    public void setCorlor(int corlor) {
        this.corlor = corlor;
    }
}
