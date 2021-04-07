package com.example.whichcoffeeapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class EditCoffee extends AppCompatActivity {

    DatabaseHelper myDb;
    TextView liveCoffeeName;
    EditText editCoffeeName, editOrigin, editRoastDate;
    AutoCompleteTextView editProcess;
    DatePickerDialog picker;
    Button btnAddData,btnCancel, btnDelete;
    String coffeeName;
    int colorId;
    private int[] colorArray = new int[]{R.color.one, R.color.two, R.color.three ,R.color.four,R.color.five};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coffee);
        int CoffeeId = Integer.parseInt(getIntent().getStringExtra("COFFEE_ID"));


        myDb = new DatabaseHelper(this);

        editCoffeeName = findViewById(R.id.editText_coffeeName);
        editOrigin = findViewById(R.id.editText_coffeeOrigin);
        editProcess = findViewById(R.id.editText_process);
        editRoastDate = findViewById(R.id.editText_roastDate);
        liveCoffeeName = findViewById(R.id.liveCoffeeName);

        btnAddData = findViewById(R.id.button_add);
        btnCancel = findViewById(R.id.button_cancel);
        btnDelete = findViewById(R.id.button_delete);
        btnDelete.setVisibility(View.VISIBLE);
        btnAddData.setText("Save");



        setLiveTitle();
        colorPicker();
        startAutocomplete();
        fillInfo(CoffeeId);
        datePicker();

        cancelEdit();
        updateData(CoffeeId);
        deleteCoffee(CoffeeId);
    }

    private void colorPicker() {
        liveCoffeeName.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        pickColor();
                    }
                }

        );
    }

    private void startAutocomplete() {
        String[] process_list = getResources().getStringArray(R.array.process_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, process_list);
        editProcess.setAdapter(adapter);
    }

    private void pickColor(){
        CustomColorDialog colorPicker = new CustomColorDialog(this);

        colorPicker.show();
        colorPicker.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        colorPicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                colorId = colorPicker.getColor();
                colorPicked();
            }
        });

    }

    private void colorPicked(){
        int[] color_list = getResources().getIntArray(R.array.color_list);
        liveCoffeeName.setBackgroundColor(color_list[colorId]);
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

    public void updateData(int coffeeId) {
        String id = Integer.toString(coffeeId);
        btnAddData.setOnClickListener(
                new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        boolean isInserted = myDb.updateCoffeeValues(id,editOrigin.getText().toString(), editCoffeeName.getText().toString(), editProcess.getText().toString(), editRoastDate.getText().toString());
                        if (!isInserted) {
                            Toast.makeText(EditCoffee.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (isInserted) {
                            Toast.makeText(EditCoffee.this, editCoffeeName.getText().toString() + " Updated", Toast.LENGTH_LONG).show();


                            boolean isColorChanged = myDb.updateColorValues(id,colorId);

                            Intent returnIntent = new Intent();
                            setResult(RESULT_OK,returnIntent);
                            finish();


                        }
                    }
                }
        );
    }

    private void deleteCoffee(int coffeeId) {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        confirmDelete(coffeeId);
                    }
                }
        );
    }
    public void confirmDelete(int coffeeId) {
        String title ="Delete Coffee: "+ coffeeName;
        String msg = "This will delete this Coffee, empty all it's Jars and delete all Reviews, there is no going back";

        AlertDialog.Builder builder = new AlertDialog.Builder(EditCoffee.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("Delete Coffee", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDb.deleteCoffeeById(coffeeId);
                Toast.makeText(EditCoffee.this, coffeeName + " is Now Empty", Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                setResult(RESULT_FIRST_USER,returnIntent);
                finish();
            }});
        builder.show();

    }
    private void datePicker() {
        editRoastDate.setFocusable(false);
        editRoastDate.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 final Calendar cldr = Calendar.getInstance();
                                                 int day = cldr.get(Calendar.DAY_OF_MONTH);
                                                 int month = cldr.get(Calendar.MONTH);
                                                 int year = cldr.get(Calendar.YEAR);
                                                 // date picker dialog
                                                 picker = new DatePickerDialog(EditCoffee.this,
                                                         new DatePickerDialog.OnDateSetListener() {
                                                             @Override
                                                             public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                                 String month = String.valueOf(monthOfYear + 1);
                                                                 String day = String.valueOf(dayOfMonth);
                                                                 if ((monthOfYear + 1)<10){month = "0" + month;}
                                                                 if ((dayOfMonth)<10){day = "0" + day;}


                                                                 editRoastDate.setText(year+ "-" +month + "-" + day);
                                                             }
                                                         }, year, month, day);
                                                 picker.show();
                                             }
                                         }

        );

    }

    private void setLiveTitle() {

        editCoffeeName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                liveCoffeeName.setText(s);
            }
        });
    }

    public void fillInfo(int pos){
        String [] names = getTitles(viewLog(pos));
        int colorPos = pos-1 % colorArray.length;

        coffeeName=names[0];

        editCoffeeName.setText(names[0]);
        editOrigin.setText(names[1]);
        editProcess.setText(names[2]);
        editRoastDate.setText(names[3]);
        colorPos = (int)(Math.random() * 5);
        liveCoffeeName.setBackgroundResource(colorArray[colorPos]);
    }

    public Cursor viewLog(int pos) {
        Cursor res = myDb.getCoffeeById(pos);
        res.moveToFirst();
        return res;
    }
    public int countCoffee() {
        Cursor res = myDb.countCoffee();
        res.moveToFirst();
        int sum = Integer.parseInt(res.getString(0));
        res.close();
        return sum;
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
