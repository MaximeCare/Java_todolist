package com.example.todolist;

import java.util.Date;

/**
 * Created by Carem on 03/02/2018.
 */

public class Task {
    public String _name;
    public String _desc;
    public String _date;
    public String _status;


    public String toString() {
            return this._name + " | " + this._date + " | " + this._status;
    }
}
