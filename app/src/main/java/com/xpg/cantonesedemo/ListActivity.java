package com.xpg.cantonesedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    final ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        data.add("Most used words");
        data.add("Colors");
        data.add("Numbers");
        data.add("Household items");

        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter(this, R.layout.list_item, R.id.idlayu, data));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, PracticeActivity.class);
                intent.putExtra("practiceList", data.get(position));
                startActivity(intent);
            }
        });
    }

    public void backHome(View view){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}