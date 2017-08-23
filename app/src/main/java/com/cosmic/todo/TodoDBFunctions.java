package com.cosmic.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by anushree on 8/16/2017.
 */

public class TodoDBFunctions {

    public static final String TODO_TABLE = "TodoTable";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String NOTES = "notes";
    public static final String PRIORITY = "priority";
    public static final String PRIORITY_INDEX = "priority_index";
    TodoHelper helper;
    Context context;

    TodoDBFunctions(Context ctx) {
        helper = TodoHelper.getInstance(ctx);
        context = ctx;
    }


    TodoDBFunctions(){

    }

    public Boolean addTodo(TodoBean tb){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE,tb.getTitle_todo());
        cv.put(DATE,tb.getDate());
        cv.put(NOTES,tb.getNotes());
        cv.put(PRIORITY,tb.getPriority());
        cv.put(PRIORITY_INDEX,tb.getPriority_index());
        long id = db.insert(TODO_TABLE,null,cv);

        if(id==-1)
            return false;
        else
        {
            tb.set_id((int)id);
            return true;
        }
    }


    public List<TodoBean> getTodoList(){

        List<TodoBean> todoList = new ArrayList<TodoBean>();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(TODO_TABLE,null,null,null,null,null,PRIORITY_INDEX+" ASC");
        TodoBean x ;
        if(c.getCount()>0) {
            c.moveToFirst();
            do {

                x = new TodoBean(c.getInt(c.getColumnIndex(ID)), c.getString(c.getColumnIndex(TITLE)), c.getString(c.getColumnIndex(NOTES)),
                        c.getString(c.getColumnIndex(DATE)), c.getString(c.getColumnIndex(PRIORITY)));
                //Log.i("DBFunctions"," cursor priority "+c.getString(c.getColumnIndex(PRIORITY))+ " "+c.getString(c.getColumnIndex(PRIORITY_INDEX)));
                todoList.add(x);
            } while (c.moveToNext());
        }

        c.close();

        return todoList;
    }


    public Boolean updateTodo(TodoBean tb){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE,tb.getTitle_todo());
        cv.put(NOTES,tb.getNotes());
        cv.put(DATE,tb.getDate());
        cv.put(PRIORITY,tb.getPriority());
        cv.put(PRIORITY_INDEX,tb.getPriority_index());
        int noofrows = db.update(TODO_TABLE,cv,ID+"=?",new String[]{String.valueOf(tb.get_id())});
        if(noofrows>0)
            return true;
        else
            return false;
    }


    public Boolean deleteTodo(TodoBean tb){
        SQLiteDatabase db = helper.getWritableDatabase();
        String [] whereArgs = new String[]{String.valueOf(tb.get_id())};
        int noofrows = db.delete(TODO_TABLE,ID+"=?",whereArgs);
        if(noofrows>0)
            return true;
        return false;
    }



}
