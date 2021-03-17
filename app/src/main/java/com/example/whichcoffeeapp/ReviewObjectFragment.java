package com.example.whichcoffeeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ReviewObjectFragment extends Fragment {
    public static final String REVIEW_ID = "reviewId";
    public static final String REVIEW_METHOD = "reviewMethod";
    public static final String REVIEW_DECRIPTION = "reviewDescription";
    public static final String COFFEE_ID = "coffeeId";
    TextView  reviewMethod,reviewDescription,coffeeChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.review_collection_object, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int reviewId = args.getInt(REVIEW_ID);
        String rMethod = args.getString(REVIEW_METHOD);
        String rDescription = args.getString(REVIEW_DECRIPTION);
        int coffeeId = args.getInt(COFFEE_ID);


        reviewMethod = view.findViewById(R.id.reviewMethod);
        reviewDescription = view.findViewById(R.id.reviewDescription);
        coffeeChart = view.findViewById(R.id.coffeeChart);


        reviewMethod.setText(rMethod);
        
        reviewDescription.setText("• "+rDescription);

        coffeeChart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openReview(v,coffeeId,reviewId );
            }
        });
    }

    public void openReview(View v,int coffeeId,int reviewId){
        Intent intent = new Intent(v.getContext(), EditReview.class);

        intent.putExtra("COFFEE_ID",coffeeId);
        intent.putExtra("REVIEW_ID",reviewId );
        v.getContext().startActivity(intent);
    }
}
