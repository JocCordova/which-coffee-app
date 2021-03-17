package com.example.whichcoffeeapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class JarFragment extends Fragment {
    DatabaseHelper myDb;
    TextView coffeeName;
    ViewPager2 viewPager;
    ViewPager viewPagerTabs;
    JarAdapter jarAdapter;

    public JarFragment() {
        super(R.layout.jar_fragment);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


        int coffeeId = requireArguments().getInt("id");


        myDb = new DatabaseHelper(view.getContext());
        //coffeeName = view.findViewById(R.id.coffeeName);
        jarAdapter = new JarAdapter(this,coffeeId);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(jarAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);


        int finalCoffeeId = coffeeId;
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("Jar " + (getJarNumber(viewJars(finalCoffeeId),position)))
        ).attach();


        addJar(view,coffeeId);



    }
    public void addJar(View view,int id){
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddJar.class);
                intent.putExtra("COFFEE_ID", String.valueOf(id));
                startActivity(intent);
            }
        });
    }
    public int countJars(int coffeeId) {
        Cursor res = myDb.countJarsOfCoffeeId(coffeeId);
        res.moveToFirst();
        int sum = res.getInt(0);
        res.close();
        return sum;
    }
    public Cursor viewJars(int pos) {
        Cursor res = myDb.getJarsFromCoffeeId(pos);
        res.moveToFirst();
        return res;
    }
    public int getJarNumber(Cursor res,int pos) {
        int cJar=0;
        if( res != null && res.moveToFirst() ){
            res.moveToPosition(pos);
            cJar = res.getInt(0);
        }
        res.close();

        return cJar;
    }

}

