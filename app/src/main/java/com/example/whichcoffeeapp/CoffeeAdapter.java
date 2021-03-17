package com.example.whichcoffeeapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CoffeeAdapter extends FragmentStateAdapter {
    DatabaseHelper myDb;
    int lastadded=-1;
    int skips=0;
    private FragmentManager mFragmentManager;


    public CoffeeAdapter(Fragment fragment) {
        super(fragment);
        myDb = new DatabaseHelper(fragment.getActivity());




    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {



        int coffeeId = (int)getItemId(position);
        Fragment fragment = new CoffeeObjectFragment();

        String values[] = getCoffeeInfo(viewCoffee(coffeeId));

        Bundle bundle = new Bundle();
        bundle.putString("name", values[0]);
        bundle.putString("origin", values[1]);
        bundle.putString("process", values[2]);
        bundle.putString("rDate", values[3]);
        bundle.putInt("position",coffeeId);

        fragment.setArguments(bundle);
        return fragment;
    }





    public Cursor viewCoffee(int id) {
        Cursor res = myDb.getCoffeeById(id);
        res.moveToFirst();
        return res;
    }
    public Cursor getCoffee(){
        Cursor res = myDb.getAllCoffee();
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

    public String[] getCoffeeInfo(Cursor res) {
        String cName="",cOrigin="",cProcess="",cRoastDate="",cId="";

        if( res != null && res.moveToFirst() ){
            cName = res.getString(0);
            cOrigin = res.getString(1);
            cProcess = res.getString(2);
            cRoastDate = res.getString(3);
        }
        res.close();
        String [] names = {cName,cOrigin,cProcess,cRoastDate,cId};
        return names;
    }

    @Override
    public int getItemCount() {
        int itemTotal = countCoffee();
        return itemTotal;
    }

    @Override
    public long getItemId(int position) {
        Cursor res = getCoffee();
            if (res != null && res.moveToFirst()) {
                res.moveToPosition(position);
                int cId = res.getInt(0);
                return (long) (cId);
            }
        return(-1);

    }
    @Override
    public boolean containsItem(long itemId){
        return myDb.coffeeExists((int)itemId);
    }
}
