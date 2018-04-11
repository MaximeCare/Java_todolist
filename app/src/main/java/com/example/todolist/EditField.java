package com.example.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditField extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_layout);
    }

    public void saveTask(View view)
    {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

        String name = ((EditText)findViewById(R.id.taskName)).getText().toString();
        String description = ((EditText) findViewById(R.id.taskDefinition)).getText().toString();
        DatePicker datePick = (DatePicker) findViewById(R.id.taskDate);
        String status = "ToDo";

        String dateFinal = dateformat.format(new Date(datePick.getYear() - 1900, datePick.getMonth(), datePick.getDayOfMonth()));

        if (!name.equals("") && !description.equals("") && !dateFinal.equals("") && !status.equals(""))
        {

            Intent intent = new Intent();
            intent.putExtra(Intent_Constants.INTENT_NAME_FIELD, name);
            intent.putExtra(Intent_Constants.INTENT_DATE_FIELD, dateFinal);
            intent.putExtra(Intent_Constants.INTENT_DESC_FIELD, description);
            intent.putExtra(Intent_Constants.INTENT_STATUS_FIELD, status);

            setResult(Intent_Constants.INTENT_RESULT_CODE, intent);
            finish();
        }
    }
}
