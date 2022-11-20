package com.example.pet_care;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DBActivity  extends AppCompatActivity {
    protected void ExecSQL(String SQL, Object[] args, OnQuerySuccess success)
            throws Exception {
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(
                getFilesDir().getPath() + "/vetpet.db",
                null
        );
        Toast.makeText(getApplicationContext(), getFilesDir().getPath() + "/vetpet.db",
                Toast.LENGTH_LONG).show();
        Log.d("DIRLOCATION", getFilesDir().getPath() + "/vetpet.db");
        if (args == null) {
            db.execSQL(SQL);
        } else {
            db.execSQL(SQL, args);
        }
        db.close();
        if (success != null)
            success.OnSuccess();
    }

    protected void InitDB() throws Exception {
        ExecSQL(
                "CREATE TABLE if not exists VETPET( " +
                        "ID integer PRIMARY KEY AUTOINCREMENT, " +
                        "Name text not null, " +
                        "Phone text not null, " +
                        "NameOfTheAnimal text not null, " +
                        "unique(Phone, Name) " +
                        "); ",
                null,
                () -> Toast.makeText(getApplicationContext(),
                        "CREATE TABLE SUCCESS",
                        Toast.LENGTH_LONG
                ).show()
        );



    }
    protected void SelectSQL(String SelectQ, String[] args, OnSelectSuccess
            success)
            throws Exception
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(
                getFilesDir().getPath() + "/vetpet.db",
                null
        );
        Toast.makeText(getApplicationContext(), getFilesDir().getPath() + "/vetpet.db",
                Toast.LENGTH_LONG).show();
        Log.d("DIRLOCATION", getFilesDir().getPath() + "/vetpet.db");
        Cursor cursor = db.rawQuery(SelectQ, args);
        while (cursor.moveToNext()){
            @SuppressLint("Range")
            String ID= cursor.getString(cursor.getColumnIndex("ID"));
            @SuppressLint("Range")
            String Name= cursor.getString(cursor.getColumnIndex("Name"));
            @SuppressLint("Range")
            String Phone= cursor.getString(cursor.getColumnIndex("Phone"));
            @SuppressLint("Range")
            String NameOfTheAnimal= cursor.getString(cursor.getColumnIndex("NameOfTheAnimal"));
            success.OnElementSelected(ID, Name, Phone, NameOfTheAnimal);
        }
        db.close();
    }

    protected interface OnQuerySuccess {
        public void OnSuccess();
    }
    protected interface OnSelectSuccess{
        public void
        OnElementSelected(String ID, String Name, String Phone, String NameOfTheAnimal);

    }
}
