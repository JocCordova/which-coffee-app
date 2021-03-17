package com.example.whichcoffeeapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class OpenCoffee extends AppCompatActivity {
    ViewPager2 vPager;
    DatabaseHelper myDb;
    ViewPagerAdapter adapter;
    TextView coffeeName,coffeeChart,coffeeOrigin,coffeeProcess,roastDate;
    ImageView editCoffee;
    Button btnJar, btnReview;
    int CoffeeId;
    private int[] colorArray = new int[]{R.color.one, R.color.two, R.color.three ,R.color.four,R.color.five};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CoffeeId = Integer.parseInt(getIntent().getStringExtra("COFFEE_ID"));
        setContentView(R.layout.activity_coffee);
        myDb = new DatabaseHelper(this);

        coffeeName = findViewById(R.id.coffeeName);
        coffeeChart = findViewById(R.id.coffeeChart);
        coffeeOrigin = findViewById(R.id.coffeeOrigin);
        coffeeProcess = findViewById(R.id.coffeeProcess);
        roastDate = findViewById(R.id.roastDate);
        editCoffee = findViewById(R.id.editBtn);
        btnJar = findViewById(R.id.btnJar);
        btnReview = findViewById(R.id.btnReview);

        fillInfo(CoffeeId);
        editCoffee(CoffeeId);
        jarClick();
        reviewClick();


    }

    @Override
    public void onResume()
    {
        super.onResume();
        createJarFragment();

    }

    private void jarClick() {
        btnJar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                createJarFragment();
            }
        });
    }
    private void reviewClick() {
        btnReview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                createReviewFragment();

            }
        });
    }



    private void createJarFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("id", CoffeeId);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.fragment_container_view, JarFragment.class, bundle)
                .commit();
    }
    private void createReviewFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("id", CoffeeId);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.fragment_container_view, ReviewFragment.class, bundle)
                .commit();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                fillInfo(CoffeeId);
            }
            if (resultCode == RESULT_FIRST_USER) {
                finish();
            }
        }
    }

    public void fillInfo(int pos){
        String [] names = getTitles(viewLog(pos));
        int colorPos = pos % colorArray.length;

        coffeeName.setText(names[0]);
        coffeeOrigin.setText(names[1]);
        coffeeProcess.setText(names[2]);
        roastDate.setText(names[3]);
        coffeeName.setBackgroundResource(colorArray[colorPos]);
    }

    public Cursor viewLog(int pos) {
        Cursor res = myDb.getCoffeeById(pos);
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

    public void editCoffee(int id){
        editCoffee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                openEditCoffee(v,id);
            }
        });

    }
    public void openEditCoffee(View view, int id){
        Intent intent = new Intent(view.getContext(), EditCoffee.class);
        intent.putExtra("COFFEE_ID", String.valueOf(id));
        startActivityForResult(intent, 1);

    }
}
