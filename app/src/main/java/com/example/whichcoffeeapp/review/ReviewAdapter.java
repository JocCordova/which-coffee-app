package com.example.whichcoffeeapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ReviewAdapter extends FragmentStateAdapter {
    DatabaseHelper myDb;
    int coffeeId;
    public ReviewAdapter(Fragment fragment, int id) {
        super(fragment);
        this.coffeeId = id;
        myDb = new DatabaseHelper(fragment.getActivity());


    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {



        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new ReviewObjectFragment();
        Bundle args = new Bundle();

        String values[] = getReviewInfo(getReview(coffeeId),position);

        Bundle bundle = new Bundle();
        bundle.putInt("reviewId", Integer.parseInt(values[0]));
        bundle.putString("reviewMethod", values[1]);
        bundle.putString("reviewDescription", values[2]);
        bundle.putInt("coffeeId", coffeeId);

        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getItemCount() {
        return countReviews();
    }

    public int countReviews() {
        Cursor res = myDb.countReviewOfCoffeeId(coffeeId);
        res.moveToFirst();
        int sum = res.getInt(0);
        res.close();
        return sum;
    }
    public Cursor getReview(int coffeeId) {
        Cursor res = myDb.getReviewsFromCoffeeId(coffeeId);
        res.moveToFirst();
        return res;
    }
    public String[] getReviewInfo(Cursor res,int pos) {
        int rId = 0;
        String rMethod = "";
        String rDescription= "";
        if( res != null && res.moveToFirst() ){
            res.moveToPosition(pos);
            rId = res.getInt(0);
            rMethod = res.getString(1);
            rDescription = res.getString(2);
        }
        res.close();
        String[] review = {String.valueOf(rId),rMethod,rDescription};
        return review;
    }
}
