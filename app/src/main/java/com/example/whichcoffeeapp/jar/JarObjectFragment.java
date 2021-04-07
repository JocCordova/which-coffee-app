package com.example.whichcoffeeapp.jar;

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

public class JarObjectFragment extends Fragment {
    public static final String JAR_NUM = "jarNum";
    public static final String AMOUNT = "amount";
    public static final String COFFEE_ID = "coffeeId";
    TextView coffeeJar,amount,coffeeChart;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.jar_collection_object, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int jarId = args.getInt(JAR_NUM);
        int grams = args.getInt(AMOUNT);
        int coffeeId = args.getInt(COFFEE_ID);

        coffeeJar = view.findViewById(R.id.coffeeJarNum);
        amount = view.findViewById(R.id.jarAmount);
        coffeeChart = view.findViewById(R.id.coffeeChart);

        coffeeJar.setText(Integer.toString(jarId));
        amount.setText(Integer.toString(grams) + " g");

        coffeeChart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCoffee(v,coffeeId,jarId );
            }
        });
    }

    public void openCoffee(View v,int coffeeId,int jarId){
        Intent intent = new Intent(v.getContext(), EditJar.class);

        intent.putExtra("COFFEE_ID",coffeeId);
        intent.putExtra("JAR_ID",jarId );
        v.getContext().startActivity(intent);
    }
}
