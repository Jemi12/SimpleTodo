package com.task.todo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.task.todo.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static int EDIT_REQUEST_CODE=20;
    public final static String ITEM_TEXT="itemText";
    public final static String ITEM_POSITION="itemPosition";

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(itemsAdapter);

//        items.add("Item 1");
//        items.add("Items 2");
        setupListviewListener();
    }

    public void onAddItem(View view) {
        EditText et = (EditText) findViewById(R.id.et);
        String itemText = et.getText().toString().trim();
        itemsAdapter.add(itemText);
        et.setText("");
        writeItems();
        Toast.makeText(this, "Item added to list", Toast.LENGTH_SHORT).show();
    }

    public void setupListviewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==EDIT_REQUEST_CODE){
            String updatedItem=data.getExtras().getString(ITEM_TEXT);
            int position=data.getExtras().getInt(ITEM_POSITION);
            items.set(position, updatedItem);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            Toast.makeText(this, "Items updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }

    private void readItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading file" + e, Toast.LENGTH_SHORT).show();
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading file" + e, Toast.LENGTH_SHORT).show();
            items = new ArrayList<>();
        }

    }
}
