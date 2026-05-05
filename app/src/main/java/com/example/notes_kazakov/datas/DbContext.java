package com.example.notes_kazakov.datas;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbContext extends SQLiteOpenHelper {
    public static SQLiteDatabase sql;
    public DbContext(Context context){
        super(context, "DbNotes", null, 2);
        sql = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Notes (" +
                "Id integer primary key autoincrement," +
                "Title text," +
                "Text text," +
                "Date text," +
                "Color text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        Log.d(TAG, "Upgrading database from " + oldVer + " to " + newVer);
        db.execSQL("DROP TABLE IF EXISTS Notes");
        onCreate(db);
    }
}
