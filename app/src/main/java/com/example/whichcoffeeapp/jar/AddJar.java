package com.example.whichcoffeeapp.jar;



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

import com.example.whichcoffeeapp.DatabaseHelper;
import com.example.whichcoffeeapp.R;


public class AddJar extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editCoffeeJar, editCoffeeAmount;
    TextView coffeeName;

    Button btnAddData,btnCancel,btnDelete;
    private int[] colorArray = new int[]{R.color.one, R.color.two, R.color.three ,R.color.four,R.color.five};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jar);
        int coffeeId = Integer.parseInt(getIntent().getStringExtra("COFFEE_ID"));
        myDb = new DatabaseHelper(this);

        editCoffeeJar = findViewById(R.id.editText_coffeeJar);
        editCoffeeAmount = findViewById(R.id.editText_coffeeAmount);
        coffeeName = findViewById(R.id.coffeeName);

        btnAddData = findViewById(R.id.button_add);
        btnCancel = findViewById(R.id.button_cancel);
        btnDelete = findViewById(R.id.button_delete);



        fillInfo(coffeeId);
        cancelEdit();
        addData(coffeeId);
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

    public void addData(int coffeeId) {
        String id = Integer.toString(coffeeId);
        btnAddData.setOnClickListener(
                new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        boolean isInserted = myDb.insertJarValues(id, editCoffeeJar.getText().toString(), editCoffeeAmount.getText().toString());
                        if (!isInserted) {
                            Toast.makeText(AddJar.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (isInserted) {
                            Toast.makeText(AddJar.this, "Jar: "+ editCoffeeJar.getText().toString() + " filled!", Toast.LENGTH_SHORT).show();

                            finish();

                        }
                    }
                }
        );
    }

    public void fillInfo(int pos){
        String [] names = getTitles(viewLog(pos));
        int colorPos = (int)(Math.random() * 5);

        coffeeName.setText(names[0]);
        coffeeName.setBackgroundResource(colorArray[colorPos]);
    }

    public Cursor viewLog(int pos) {
        Cursor res = myDb.getCoffeeById(pos);
        res.moveToFirst();
        return res;
    }

    public String[] getTitles(Cursor res) {
        String cName="",cOrigin="",cProcess="",cRoastDate="";

        if( res != null && res.moveToFirst() ){
            cName = res.getString(0);
            cOrigin = res.getString(1);
            cProcess = res.getString(2);
            cRoastDate = res.getString(3);
        }
        res.close();
        String [] names = {cName,cOrigin,cProcess,cRoastDate};
        return names;
    }
}

