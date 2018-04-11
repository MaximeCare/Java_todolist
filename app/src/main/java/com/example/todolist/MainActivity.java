package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Task> taskList;
    ArrayAdapter<Task> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.mainListview);
        taskList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, taskList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TaskEdit.class);
                intent.putExtra(Intent_Constants.INTENT_TASK_DATA_DESC, taskList.get(position)._desc);
                intent.putExtra(Intent_Constants.INTENT_TASK_DATA_STATUS, taskList.get(position)._status);
                intent.putExtra(Intent_Constants.INTENT_TASK_DATA_NAME, taskList.get(position)._name);
                intent.putExtra(Intent_Constants.INTENT_TASK_DATA_DATE, taskList.get(position)._date);
                intent.putExtra(Intent_Constants.INTENT_TASK_POSITION, position);
                startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE_EDIT);
            }
        });

        try {
            Scanner s = new Scanner(openFileInput("todo.txt"));
            Task newTask = new Task();
            while (s.hasNextLine())
            {
                String tmp = s.nextLine();
                String[] separated = tmp.split("-");
                newTask._name = separated[0];
                newTask._date = separated[1];
                newTask._desc = separated[2];
                newTask._status = separated[3];
                arrayAdapter.add(newTask);
            }
            s.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void onClick(View v)
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, EditField.class);
        startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Task my = new Task();
        if (resultCode == Intent_Constants.INTENT_REQUEST_CODE){
            my._name = data.getStringExtra(Intent_Constants.INTENT_NAME_FIELD);
            my._date = data.getStringExtra(Intent_Constants.INTENT_DATE_FIELD);
            my._desc = data.getStringExtra(Intent_Constants.INTENT_DESC_FIELD);
            my._status = data.getStringExtra(Intent_Constants.INTENT_STATUS_FIELD);
            taskList.add(my);
            arrayAdapter.notifyDataSetChanged();
        }
        else if (resultCode == Intent_Constants.INTENT_REQUEST_CODE_EDIT)
        {
            my._name = data.getStringExtra(Intent_Constants.INTENT_TASK_NAME_CHANGED);
            my._date = data.getStringExtra(Intent_Constants.INTENT_TASK_DATE_CHANGED);
            my._desc = data.getStringExtra(Intent_Constants.INTENT_TASK_DESC_CHANGED);
            my._status = data.getStringExtra(Intent_Constants.INTENT_TASK_STATUS_CHANGED);
            taskList.remove(data.getIntExtra(Intent_Constants.INTENT_TASK_POSITION, -1));
            taskList.add(data.getIntExtra(Intent_Constants.INTENT_TASK_POSITION, -1), my);
            arrayAdapter.notifyDataSetChanged();
        }
        else if (resultCode == Intent_Constants.INTENT_RESULT_CODE_DELETE)
        {
            taskList.remove(data.getIntExtra(Intent_Constants.INTENT_TASK_POSITION, -1));
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            PrintWriter p = new PrintWriter(openFileOutput("todo.txt", Context.MODE_PRIVATE));
            String status;
            for (Task data : taskList)
            {
                    p.println(data._name + "-" + data._date + "-" + data._desc + "-" + data._status);
            }
            p.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finish();
    }
}
