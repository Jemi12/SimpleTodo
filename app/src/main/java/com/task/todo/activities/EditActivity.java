package com.task.todo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.task.todo.R;

import static com.task.todo.activities.Main2Activity.ITEM_POSITION;
import static com.task.todo.activities.Main2Activity.ITEM_TEXT;

public class EditActivity extends AppCompatActivity {
    EditText editText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editText=(EditText)findViewById(R.id.edit_text);
        editText.setText(getIntent().getStringExtra(ITEM_TEXT));
        position=getIntent().getIntExtra(ITEM_POSITION, 0);
        getSupportActionBar().setTitle("Edit Item");

    }
    public void OnSaveItem(View v){
        Intent i =new Intent();
        i.putExtra(ITEM_TEXT, editText.getText().toString());
        i.putExtra(ITEM_POSITION, position);
        setResult(RESULT_OK,i);
        finish();
    }
}
