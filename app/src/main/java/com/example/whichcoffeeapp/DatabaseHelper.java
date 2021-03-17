package com.example.whichcoffeeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE = "CoffeeJar.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA auto_vacuum = ON");

        db.execSQL("CREATE TABLE COFFEE(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Origin VARCHAR(50) NOT NULL CHECK(length(Origin)>0)," +
                "CoffeeName VARCHAR(50) NOT NULL CHECK(length(CoffeeName)>0)," +
                "Process VARCHAR(50) NOT NULL CHECK(length(Process)>0)," +
                "RoastDate DATE NOT NULL CHECK(length(RoastDate)>0))");

        db.execSQL("CREATE TABLE REVIEW(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CoffeeID," +
                "Description VARCHAR(500)," +
                "Method VARCHAR(50)," +
                "FOREIGN KEY(CoffeeID) REFERENCES COFFEE(ID)" +
                "ON DELETE CASCADE" +
                " ON UPDATE CASCADE )");

        db.execSQL("CREATE TABLE JAR(ID INTEGER PRIMARY KEY NOT NULL CHECK(length(ID)>0) ," +
                "Amount INTEGER NOT NULL CHECK(length(Amount)>0)," +
                "CoffeeID," +
                "FOREIGN KEY(CoffeeID) REFERENCES COFFEE(ID)" +
                "ON DELETE CASCADE" +
                " ON UPDATE CASCADE )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS COFFEE");
        db.execSQL("DROP TABLE IF EXISTS REVIEW");
        db.execSQL("DROP TABLE IF EXISTS JAR");
        onCreate(db);
    }

    public void onConfigure (SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean insertCoffeeValues(String origin, String coffeeName, String process, String roastDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("origin", origin);
        contentValues.put("coffeeName", coffeeName);
        contentValues.put("process", process);
        contentValues.put("roastDate", roastDate);

        Long result = db.insert("COFFEE", null, contentValues);
        if (result == -1) {
            return false;
        }

        return true;

    }

    public Cursor getAllCoffee() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT id,coffeeName,origin,process,roastDate FROM COFFEE ORDER BY Id ASC", null);
        return res;
    }
    public Cursor getCoffeeById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String []args = {Integer.toString(id)};
        Cursor res = db.rawQuery("SELECT coffeeName,origin,process,roastDate FROM COFFEE WHERE ID = ? ", args);
        return res;
    }

    public Cursor countCoffee() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(ID) FROM COFFEE ", null);
        return res;
    }
    public boolean coffeeExists(int coffeeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String []args = {Integer.toString(coffeeId)};
        Cursor res = db.rawQuery("SELECT COUNT(ID) FROM COFFEE WHERE ID = ? ", args);
        res.moveToFirst();

        if(res.getString(0).equals("0")){
            return false;
        }
        if(!res.getString(0).equals("0")){
            return true;
        }
        return true;
    }


    public boolean updateCoffeeValues(String id, String origin, String coffeeName, String process, String roastDate) {
        String [] args= {id};
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("origin", origin);
        contentValues.put("coffeeName", coffeeName);
        contentValues.put("Process", process);
        contentValues.put("roastDate", roastDate);

        int result = db.update("COFFEE", contentValues,"ID = ?",args );
        if (result == -1||result == 0) {
            return false;
        }

        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean insertJarValues(String coffeeId, String jarNum, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("ID", jarNum);
        contentValues.put("Amount", amount);
        contentValues.put("CoffeeID", coffeeId);

        Long result = db.insert("JAR", null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean updateJarValues(String jarNum, String amount) {
        String [] args= {jarNum};
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("ID", jarNum);
        contentValues.put("Amount", amount);


        int result = db.update("JAR", contentValues,"ID = ?",args );
        if (result == -1||result == 0) {
            return false;
        }
        return true;
    }

    public Cursor getJarsFromCoffeeId(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String []args = {Integer.toString(id)};
        Cursor res = db.rawQuery("SELECT ID,amount FROM JAR WHERE CoffeeID = ? ORDER BY ID ASC ", args);
        return res;
    }

    public Cursor countJarsOfCoffeeId(int coffeeId) {
        String []args = {Integer.toString(coffeeId)};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(ID) FROM JAR j WHERE CoffeeID = ? ", args);
        return res;
    }

    public Cursor getJarFromJarId(int jarId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String []args = {Integer.toString(jarId)};
        Cursor res = db.rawQuery("SELECT ID,amount FROM JAR WHERE ID = ? ORDER BY ID ASC ", args);
        return res;
    }

    public void deleteJarById(int jarId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String []args = {Integer.toString(jarId)};
        db.delete("JAR", "ID = ?",args);
    }

    public void deleteCoffeeById(int coffeeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String []args = {Integer.toString(coffeeId)};
        db.delete("COFFEE", "ID = ?",args);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean insertReviewValues(String coffeeId, String rMethod, String rDescription) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("METHOD", rMethod);
        contentValues.put("DESCRIPTION", rDescription);
        contentValues.put("CoffeeID", coffeeId);

        Long result = db.insert("REVIEW", null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }
    public boolean updateReviewValues(String rId,String rMethod, String rDescription) {
        String [] args= {rId};
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("METHOD", rMethod);
        contentValues.put("DESCRIPTION", rDescription);


        int result = db.update("REVIEW", contentValues,"ID = ?",args );
        if (result == -1||result == 0) {
            return false;
        }
        return true;
    }

    public Cursor getReviewsFromCoffeeId(int coffeeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String []args = {Integer.toString(coffeeId)};
        Cursor res = db.rawQuery("SELECT ID,Method,Description FROM REVIEW WHERE CoffeeID = ? ORDER BY ID ASC ", args);
        return res;
    }
    public Cursor countReviewOfCoffeeId(int coffeeId) {
        String []args = {Integer.toString(coffeeId)};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(ID) FROM REVIEW WHERE CoffeeID = ? ", args);
        return res;
    }

    public Cursor getReviewFromReviewId(int reviewId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String []args = {Integer.toString(reviewId)};
        Cursor res = db.rawQuery("SELECT ID,Method,Description FROM REVIEW WHERE ID = ? ORDER BY ID ASC ", args);
        return res;
    }

    public void deleteReviewById(int reviewId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String []args = {Integer.toString(reviewId)};
        db.delete("REVIEW", "ID = ?",args);
    }
}
