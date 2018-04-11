package com.example.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TaskEdit extends AppCompatActivity {

    Task my = new Task();
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_edit_task);
        Intent intent = getIntent();



        my._desc = intent.getStringExtra(Intent_Constants.INTENT_TASK_DATA_DESC);
        my._status = intent.getStringExtra(Intent_Constants.INTENT_TASK_DATA_STATUS);
        my._name = intent.getStringExtra(Intent_Constants.INTENT_TASK_DATA_NAME);
        my._date = intent.getStringExtra(Intent_Constants.INTENT_TASK_DATA_DATE);
        position = intent.getIntExtra(Intent_Constants.INTENT_TASK_POSITION, -1);


        EditText taskDesc = (EditText) findViewById(R.id.taskDefinition);
        EditText taskStatus = (EditText) findViewById(R.id.taskStatus);
        EditText taskName = (EditText) findViewById(R.id.taskName);
        EditText taskDate = (EditText) findViewById(R.id.taskDate);


        taskName.setText(my._name);
        taskDate.setText(my._date);
        taskDesc.setText(my._desc);
        taskStatus.setText(my._status);
    }

    public void saveChangedTask(View v)
    {

        String new_name = ((EditText)findViewById(R.id.taskName)).getText().toString();
        String new_desc = ((EditText) findViewById(R.id.taskDefinition)).getText().toString();
        String new_date = ((EditText) findViewById(R.id.taskDate)).getText().toString();
        String new_status = ((EditText) findViewById(R.id.taskStatus)).getText().toString();
        Intent intent = new Intent();

        intent.putExtra(Intent_Constants.INTENT_TASK_DESC_CHANGED, new_desc);
        intent.putExtra(Intent_Constants.INTENT_TASK_STATUS_CHANGED, new_status);
        intent.putExtra(Intent_Constants.INTENT_TASK_NAME_CHANGED, new_name);
        intent.putExtra(Intent_Constants.INTENT_TASK_DATE_CHANGED, new_date);

        intent.putExtra(Intent_Constants.INTENT_TASK_POSITION, position);

        setResult(Intent_Constants.INTENT_RESULT_CODE_EDIT, intent);
        finish();
    }

    public void deleteTask(View v)
    {
        Intent intent = new Intent();

        intent.putExtra(Intent_Constants.INTENT_TASK_POSITION, position);

        setResult(Intent_Constants.INTENT_RESULT_CODE_DELETE, intent);
        finish();
    }
}
