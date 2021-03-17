package com.example.whichcoffeeapp;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AddReview extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editReviewMethod, editReviewDescription;
    TextView coffeeName;

    Button btnAddData,btnCancel;
    private int[] colorArray = new int[]{R.color.one, R.color.two, R.color.three ,R.color.four,R.color.five};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        int coffeeId = Integer.parseInt(getIntent().getStringExtra("COFFEE_ID"));


        myDb = new DatabaseHelper(this);

        editReviewMethod = findViewById(R.id.editText_reviewMethod);
        editReviewDescription = findViewById(R.id.editText_reviewDescription);
        coffeeName = findViewById(R.id.coffeeName);

        btnAddData = findViewById(R.id.button_add);
        btnCancel = findViewById(R.id.button_cancel);







        fillInfo(coffeeId);
        cancelEdit();
        addReview(coffeeId);

    }


    private void cancelEdit() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             finish();
                                         }
                                     }
        );
    }

    public void addReview(int coffeeId) {
        String id = Integer.toString(coffeeId);

        btnAddData.setOnClickListener(
                new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        boolean isInserted = myDb.insertReviewValues(id,editReviewMethod.getText().toString(), editReviewDescription.getText().toString());
                        if (!isInserted) {
                            Toast.makeText(AddReview.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (isInserted) {
                            Toast.makeText(AddReview.this, editReviewMethod.getText().toString() + " review Saved", Toast.LENGTH_SHORT).show();

                            finish();

                        }
                    }
                }
        );
    }

    public void fillInfo(int coffeId){
        String cName = getCoffeeName(viewCoffeeLog(coffeId));
        int colorPos = (int)(Math.random() * 5);

        coffeeName.setText(cName);
        coffeeName.setBackgroundResource(colorArray[colorPos]);
    }

    public Cursor viewCoffeeLog(int pos) {
        Cursor res = myDb.getCoffeeById(pos);
        res.moveToFirst();
        return res;
    }

    public String getCoffeeName(Cursor res) {
        String cName="";
        if( res != null && res.moveToFirst() ){
            cName = res.getString(0);
        }
        res.close();
        return cName;
    }

}
