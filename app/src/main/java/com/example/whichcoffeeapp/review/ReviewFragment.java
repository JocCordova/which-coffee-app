package com.example.whichcoffeeapp.review;

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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ReviewFragment extends Fragment {
    DatabaseHelper myDb;
    ViewPager2 viewPager;
    ReviewAdapter reviewAdapter;
    TextView coffeeName;

    public ReviewFragment() {
        super(R.layout.review_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


        int coffeeId = requireArguments().getInt("id");


        myDb = new DatabaseHelper(view.getContext());
        coffeeName = view.findViewById(R.id.coffeeName);
        reviewAdapter = new ReviewAdapter(this, coffeeId);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(reviewAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);


        int finalCoffeeId = coffeeId;
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText((getReviewMethod(viewReviews(finalCoffeeId), position)))
        ).attach();


        addReview(view, coffeeId);


    }

    public void addReview(View view, int id) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddReview.class);
                intent.putExtra("COFFEE_ID", String.valueOf(id));
                startActivity(intent);
            }
        });
    }

    public Cursor viewReviews(int coffeeId) {
        Cursor res = myDb.getReviewsFromCoffeeId(coffeeId);
        res.moveToFirst();
        return res;
    }

    public String getReviewMethod(Cursor res, int pos) {
        String rMethod = "";
        if (res != null && res.moveToFirst()) {
            res.moveToPosition(pos);
            rMethod = res.getString(1);
        }
        res.close();

        return rMethod;
    }
}
