package com.example.whichcoffeeapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {
    ViewPager2 vPager;

    DatabaseHelper myDb;
    ViewPagerAdapter adapter;
    TextView noCoffee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        //vPager = findViewById(R.id.ViewPager);
        noCoffee = findViewById(R.id.noCoffee);
       // adapter = new ViewPagerAdapter();
      //  vPager.setAdapter(adapter);
        Bundle bundle = new Bundle();
        bundle.putInt("id", 0);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.fragment_container_view_coffee, CoffeeFragment.class,bundle)
                .commit();
        if(countCoffee()>0){
            noCoffee.setVisibility(View.INVISIBLE);
        }
        addCoffee();


    }
    @Override
    public void onResume()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("id", 0);
        super.onResume();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.fragment_container_view_coffee, CoffeeFragment.class,bundle)
                .commit();
        if(countCoffee()>0){
            noCoffee.setVisibility(View.INVISIBLE);
        }
    }

    public void addCoffee(){
      /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCoffee.class);
                startActivity(intent);
            }
        });

       */
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
}
