package com.example.whichcoffeeapp.coffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.whichcoffeeapp.R;

public class CoffeeObjectFragment extends Fragment {
    public static final String CNAME = "name";
    public static final String ORIGIN = "origin";
    public static final String RDATE = "rDate";
    public static final String PROCESS = "process";
    public static final String POSITION = "position";
    public static final String COLORID = "colorId";


    TextView coffeeName,coffeeChart,coffeeOrigin,coffeeProcess,roastDate;
    int colorId;
    private int[] color_list;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.coffee_collection_object, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String cName = args.getString(CNAME);
        String origin = args.getString(ORIGIN);
        String process = args.getString(PROCESS);
        String rDate = args.getString(RDATE);
        int position = args.getInt(POSITION);
        int colorId = args.getInt(COLORID);


        coffeeName = view.findViewById(R.id.coffeeName);
        coffeeChart = view.findViewById(R.id.coffeeChart);
        coffeeOrigin = view.findViewById(R.id.coffeeOrigin);
        coffeeProcess = view.findViewById(R.id.coffeeProcess);
        roastDate = view.findViewById(R.id.roastDate);

        coffeeName.setText(cName);
        coffeeOrigin.setText(origin);
        coffeeProcess.setText(process);
        roastDate.setText(rDate);

        color_list = getResources().getIntArray(R.array.color_list);

        coffeeName.setBackgroundColor(color_list[colorId]);


        int finalPos = position;
        coffeeName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCoffee(v, (finalPos));
            }
        });
        coffeeChart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCoffee(v, (finalPos));
            }
        });


    }

    public void openCoffee(View v,int id){
        Intent intent = new Intent(v.getContext(), OpenCoffee.class);
        intent.putExtra("COFFEE_ID", String.valueOf(id));
        v.getContext().startActivity(intent);
    }

}