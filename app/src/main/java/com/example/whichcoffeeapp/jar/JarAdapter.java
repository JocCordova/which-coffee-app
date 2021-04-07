package com.example.whichcoffeeapp.jar;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.whichcoffeeapp.DatabaseHelper;

public class JarAdapter extends FragmentStateAdapter {
    DatabaseHelper myDb;
    int coffeeId;
    public JarAdapter(Fragment fragment, int id) {
        super(fragment);
        this.coffeeId = id;
        myDb = new DatabaseHelper(fragment.getActivity());


    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {



        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new JarObjectFragment();
        Bundle args = new Bundle();

        int values[] = getJarInfo(viewJars(coffeeId),position);

        Bundle bundle = new Bundle();
        bundle.putInt("jarNum", values[0]);
        bundle.putInt("amount", values[1]);
        bundle.putInt("coffeeId", coffeeId);

        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getItemCount() {
        return countJars();
    }

    public int countJars() {
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
    public int[] getJarInfo(Cursor res,int pos) {

        int cJar=0,cAmount=0;

        if( res != null && res.moveToFirst() ){
            res.moveToPosition(pos);
            cJar = res.getInt(0);
            cAmount = res.getInt(1);
        }
        res.close();
        int [] values = {cJar,cAmount};
        return values;
    }
}
