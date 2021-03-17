package com.example.whichcoffeeapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditReview extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editReviewMethod, editReviewDescription;
    TextView coffeeName;

    Button btnAddData,btnCancel,btnDelete;
    private int[] colorArray = new int[]{R.color.one, R.color.two, R.color.three ,R.color.four,R.color.five};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        int coffeeId = getIntent().getIntExtra("COFFEE_ID",0);
        int reviewId = getIntent().getIntExtra("REVIEW_ID",0);

        myDb = new DatabaseHelper(this);

        editReviewMethod = findViewById(R.id.editText_reviewMethod);
        editReviewDescription = findViewById(R.id.editText_reviewDescription);
        coffeeName = findViewById(R.id.coffeeName);

        btnAddData = findViewById(R.id.button_add);
        btnCancel = findViewById(R.id.button_cancel);
        btnDelete = findViewById(R.id.button_delete);

        btnDelete.setVisibility(View.VISIBLE);
        btnAddData.setText("Save");




        fillInfo(coffeeId,reviewId);
        cancelEdit();
        updateReview(reviewId);
        deleteReview(reviewId);
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

    public void updateReview(int reviewId) {

        btnAddData.setOnClickListener(
                new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        boolean isInserted = myDb.updateReviewValues(String.valueOf(reviewId),editReviewMethod.getText().toString(), editReviewDescription.getText().toString());
                        if (!isInserted) {
                            Toast.makeText(EditReview.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (isInserted) {
                            Toast.makeText(EditReview.this, editReviewMethod.getText().toString() + " review Saved", Toast.LENGTH_SHORT).show();

                            finish();

                        }
                    }
                }
        );
    }
    private void deleteReview(int reviewId) {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        confirmDelete(reviewId);
                    }
                }
        );
    }

    public void confirmDelete(int reviewId) {
        String title ="Delete Review";
        String msg = "This will delete this Review, there is no going back";

        AlertDialog.Builder builder = new AlertDialog.Builder(EditReview.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("Delete Review", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDb.deleteReviewById(reviewId);
                Toast.makeText(EditReview.this, "Review Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }});
        builder.show();

    }

    public void fillInfo(int coffeId, int reviewId){
        String cName = getCoffeeName(viewCoffeeLog(coffeId));
        int colorPos = (int)(Math.random() * 5);
        String[] review = getReviewInfo(getReview(reviewId));

        editReviewMethod.setText(review[1]);
        editReviewMethod.setEnabled(false);

        editReviewDescription.setText(review[2]);

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
    public Cursor getReview(int reviewId) {
        Cursor res = myDb.getReviewFromReviewId(reviewId);
        res.moveToFirst();
        return res;
    }
    public String[] getReviewInfo(Cursor res) {
        int rId = 0;
        String rMethod = "";
        String rDescription= "";
        if( res != null && res.moveToFirst() ){
            rId = res.getInt(0);
            rMethod = res.getString(1);
            rDescription = res.getString(2);
        }
        res.close();
        String[] review = {String.valueOf(rId),rMethod,rDescription};
        return review;
    }
}
