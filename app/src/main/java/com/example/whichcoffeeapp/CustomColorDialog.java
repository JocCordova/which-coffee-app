package com.example.whichcoffeeapp;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;

public class CustomColorDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity act;

    public TextView one,two,three,four,five,six;
    private int color;



    public CustomColorDialog(@NonNull Context context) {
        super(context);
        this.act = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = act.getWindow();
        window.setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        setContentView(R.layout.color_picker_dialog);
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);
        five = (TextView) findViewById(R.id.five);
        six = (TextView) findViewById(R.id.six);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one:
                this.color = 0;
                break;
            case R.id.two:
                this.color = 1;
                break;
            case R.id.three:
                this.color = 2;
                break;
            case R.id.four:
                this.color = 3;
                break;
            case R.id.five:
                this.color = 4;
                break;
            case R.id.six:
                this.color = 5;
                break;
            default:
                break;
        }
        this.dismiss();

    }

    public int getColor() {
        return this.color;
    }


}