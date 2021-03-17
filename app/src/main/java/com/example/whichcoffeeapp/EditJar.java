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

public class EditJar extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editCoffeeJar, editCoffeeAmount;
    TextView coffeeName;

    Button btnAddData,btnCancel,btnDelete;
    private int[] colorArray = new int[]{R.color.one, R.color.two, R.color.three ,R.color.four,R.color.five};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jar);

        int coffeeId = getIntent().getIntExtra("COFFEE_ID",0);
        int jarId = getIntent().getIntExtra("JAR_ID",0);

        myDb = new DatabaseHelper(this);

        editCoffeeJar = findViewById(R.id.editText_coffeeJar);
        editCoffeeAmount = findViewById(R.id.editText_coffeeAmount);
        coffeeName = findViewById(R.id.coffeeName);

        btnAddData = findViewById(R.id.button_add);
        btnCancel = findViewById(R.id.button_cancel);
        btnDelete = findViewById(R.id.button_delete);

        btnDelete.setVisibility(View.VISIBLE);
        btnAddData.setText("Save");




        fillInfo(coffeeId,jarId);
        cancelEdit();
        updateJar();
        deleteJar(jarId);
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

    public void updateJar() {

        btnAddData.setOnClickListener(
                new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        boolean isInserted = myDb.updateJarValues(editCoffeeJar.getText().toString(), editCoffeeAmount.getText().toString());
                        if (!isInserted) {
                            Toast.makeText(EditJar.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (isInserted) {
                            Toast.makeText(EditJar.this, "Jar: "+ editCoffeeJar.getText().toString() + " Saved", Toast.LENGTH_SHORT).show();

                            finish();

                        }
                    }
                }
        );
    }
    private void deleteJar(int jarId) {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        confirmDelete(jarId);
                    }
                }
        );
    }

    public void confirmDelete(int jarId) {
        String title ="Empty Jar: "+ jarId;
        String msg = "This will empty this Jar, there is no going back";

        AlertDialog.Builder builder = new AlertDialog.Builder(EditJar.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("Empty Jar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDb.deleteJarById(jarId);
                Toast.makeText(EditJar.this, jarId + " is Now Empty", Toast.LENGTH_SHORT).show();
                finish();
            }});
        builder.show();

    }

    public void fillInfo(int coffeId, int jarId){
        String cName = getCoffeeName(viewCoffeeLog(coffeId));
        int colorPos = (coffeId-1) % colorArray.length;
        int[] jar = getJarInfo(getJar(jarId));

        editCoffeeJar.setText(Integer.toString(jar[0]));
        editCoffeeJar.setEnabled(false);

        editCoffeeAmount.setText(Integer.toString(jar[1]));

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
    public Cursor getJar(int jarId) {
        Cursor res = myDb.getJarFromJarId(jarId);
        res.moveToFirst();
        return res;
    }
    public int[] getJarInfo(Cursor res) {
        int cJar=0, cAmount=0;
        if( res != null && res.moveToFirst() ){
            cJar = res.getInt(0);
            cAmount = res.getInt(1);
        }
        res.close();
        int[] jar = {cJar,cAmount};
        return jar;
    }
}
