package com.example.whichcoffeeapp.coffee;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.whichcoffeeapp.DatabaseHelper;
import com.example.whichcoffeeapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CoffeeFragment extends Fragment {
    DatabaseHelper myDb;
    TextView coffeeName;
    ViewPager2 viewPager;
    ViewPager2 viewPagerTabs;
    CoffeeAdapter coffeeAdapter;

    public CoffeeFragment() {
        super(R.layout.coffee_fragment);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        myDb = new DatabaseHelper(view.getContext());
        //coffeeName = view.findViewById(R.id.coffeeName);
        coffeeAdapter = new CoffeeAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(coffeeAdapter);





        addCoffee(view);



    }

    private String getCoffeeName(int pos) {
        String[] coffee = getTitles(viewLog(pos));

        return coffee[0];
    }

    public void addCoffee(View view){
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddCoffee.class);
                startActivity(intent);
            }
        });
    }
    public Cursor viewLog(int id) {
        Cursor res = myDb.getCoffeeById(id);
        res.moveToFirst();
        return res;
    }
    public int countCoffee() {
        Cursor res;
        try{res = myDb.countCoffee();
        }catch(Exception e){return 0;}


        if( res != null && res.moveToFirst() ) {
            int sum = Integer.parseInt(res.getString(0));
            res.close();
            return sum;
        }
        return 0;
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
    public void openCoffee(View v,int id){
        Intent intent = new Intent(v.getContext(), OpenCoffee.class);
        intent.putExtra("COFFEE_ID", String.valueOf(id));
        v.getContext().startActivity(intent);
    }


}