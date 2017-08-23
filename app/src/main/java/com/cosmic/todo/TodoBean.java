package com.cosmic.todo;

import java.io.Serializable;

/**
 * Created by anushree on 8/16/2017.
 * Todo Bean class which is of the form Java Bean class
 * This is made serializable since this involves data base operations
 */


public class TodoBean implements Serializable {

    private int _id;
    private String title_todo;
    private String notes;
    private String date;
    private String priority;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    TodoBean(String title){
        title_todo = title;
    }

    TodoBean(int id, String title, String note, String dt, String mpriority){
        title_todo = title;
        _id = id;
        date = dt;
        notes = note;
        priority = mpriority;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle_todo() {
        return title_todo;
    }

    public void setTitle_todo(String title_todo) {
        this.title_todo = title_todo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        TodoBean myTodo = (TodoBean) obj;
        return this.getTitle_todo().equals(myTodo.getTitle_todo());
    }

    @Override
    public String toString() {
        return this.getTitle_todo();
    }
}
