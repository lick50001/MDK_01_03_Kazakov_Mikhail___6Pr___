package com.example.notes_kazakov.datas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbContext extends SQLiteOpenHelper {
    public static SQLiteDatabase sql;
    public DbContext(Context context){
        super(context, "DbNotes", null, 1);
        sql = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Notes (" +
                "Id integer primary key autoincrement," +
                "Title text," +
                "Text text," +
                "Color text)");
    }

    @Override
    public void onUpdate(SQLiteDatabase db, int oldVer, int newVer){
        //dsad
    }
}
