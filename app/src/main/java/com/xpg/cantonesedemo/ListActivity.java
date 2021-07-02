package com.xpg.cantonesedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    final ArrayList<String> data = new ArrayList<>();
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        mode = intent.getIntExtra("mode",0);

        //If mode = 0, then list word
        //Else, list sentence section
        if (mode == 0){
            data.add("Most used words");
            data.add("Time");
            data.add("Animal and plants");
            data.add("Transportation");
            data.add("Work");
        }
        else{
            data.add("Translator");
            data.add("Conversation practice");
        }



        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter(this, R.layout.list_item, R.id.idlayu, data));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mode == 0){
                    Intent intent = new Intent(ListActivity.this, PracticeActivity.class);
                    intent.putExtra("practiceList", data.get(position));
                    startActivity(intent);
                }
                else{
                    if (position==0){
                        startActivity(new Intent(ListActivity.this, TranslateActivity.class));
                    }
                    else if (position==1){
                        //startActivity(new Intent(ListActivity.this, ConversationActivity.class));
                    }
                }

            }
        });
    }

    public void backHome(View view){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}