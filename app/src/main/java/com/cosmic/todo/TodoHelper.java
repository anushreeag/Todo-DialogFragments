package com.cosmic.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anushree on 8/16/2017.
 */

public class TodoHelper extends SQLiteOpenHelper{
    public static final String DATABASE = "TodoDatabase";
    public static final int VERSION = 1;
    private static TodoHelper todohelper ;
    public static final String TODO_TABLE = "TodoTable";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String NOTES = "notes";
    public static final String PRIORITY = "priority";
    public static final String CREATE_TABLE = "create table "+TODO_TABLE+"( "+ ID +" integer primary key autoincrement, "+TITLE+" text, " +
            NOTES+" text, "+ DATE+" text, "+PRIORITY+" text)";
    private TodoHelper(Context context){
        super(context,DATABASE,null,VERSION);
    }


    public static TodoHelper getInstance(Context ctx){
        if(todohelper==null){
            todohelper = new TodoHelper(ctx);
        }
        return todohelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop TodoTable if exists");
        onCreate(db);
    }
}
