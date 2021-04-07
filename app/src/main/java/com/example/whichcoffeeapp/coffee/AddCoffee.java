package com.example.whichcoffeeapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class AddCoffee extends AppCompatActivity {

    DatabaseHelper myDb;
    TextView liveCoffeeName;
    EditText editCoffeeName, editOrigin, editRoastDate, editText_roastedBy;
    AutoCompleteTextView editProcess;
    DatePickerDialog picker;
    Button btnAddData;
    int colorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coffee);

        myDb = new DatabaseHelper(this);

        editCoffeeName = findViewById(R.id.editText_coffeeName);
        editOrigin = findViewById(R.id.editText_coffeeOrigin);
        editProcess = findViewById(R.id.editText_process);
        editRoastDate = findViewById(R.id.editText_roastDate);
        editText_roastedBy = findViewById(R.id.editText_roastedBy);
        liveCoffeeName = findViewById(R.id.liveCoffeeName);

        btnAddData = findViewById(R.id.button_add);



        setLiveTitle();
        colorPicker();
        startAutocomplete();
        datePicker();
        AddData();
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

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        boolean isInserted = myDb.insertCoffeeValues(editOrigin.getText().toString(), editCoffeeName.getText().toString(), editProcess.getText().toString(), editRoastDate.getText().toString(),editText_roastedBy.getText().toString());
                        if (!isInserted) {
                            Toast.makeText(AddCoffee.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (isInserted) {
                            Toast.makeText(AddCoffee.this, editCoffeeName.getText().toString() + " Inserted", Toast.LENGTH_LONG).show();
                            editOrigin.setText(null);
                            editCoffeeName.setText(null);
                            editProcess.setText(null);
                            editRoastDate.setText(null);
                            finish();

                        boolean colorIsInserted = myDb.insertColor(myDb.getLastInsertedCoffeeId(),colorId);

                        }
                    }
                }
        );
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
                                            picker = new DatePickerDialog(AddCoffee.this,
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
}
