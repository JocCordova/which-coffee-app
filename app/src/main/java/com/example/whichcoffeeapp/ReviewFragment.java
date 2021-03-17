package com.example.whichcoffeeapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;



public class ReviewFragment extends Fragment {
    DatabaseHelper myDb;
    TextView coffeeName;

    public ReviewFragment() {
        super(R.layout.review_fragment);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        int coffeeId = requireArguments().getInt("id");
        myDb = new DatabaseHelper(view.getContext());
        coffeeName = view.findViewById(R.id.coffeeName);

        coffeeName.setText("Review");


    }
    public Cursor viewLog(int pos) {
        Cursor res = myDb.getCoffeeById(pos+1);
        res.moveToFirst();
        return res;
    }
    public int countCoffee() {
        Cursor res = myDb.countCoffee();
        res.moveToFirst();
        int sum = Integer.parseInt(res.getString(0));
        res.close();
        return sum;
    }

    public String[] getTitles(Cursor res) {
        String cName="",cOrigin="",cProcess="",cRoastDate="";

        if( res != null && res.moveToFirst() ){
            cName = res.getString(0);
            cOrigin = res.getString(1);
            cProcess = res.getString(2);
            cRoastDate = res.getString(3);
        }
        res.close();
        String [] names = {cName,cOrigin,cProcess,cRoastDate};
        return names;
    }
}