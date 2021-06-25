package com.xpg.cantonesedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PracticeActivity extends AppCompatActivity {

    private int index = 0;
    ArrayList<String> wordList = new ArrayList<>();
    ArrayList<String> jyutList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        Intent intent = getIntent();
        String practiceList = intent.getStringExtra("practiceList");
        inflatePracticeData(practiceList);

        //Set the start word
        TextView tv = findViewById(R.id.txt);
        tv.setText(wordList.get(0));
    }

    public void backPractice(View view){
        startActivity(new Intent(this, ListActivity.class));
        finish();
    }

    public void inflatePracticeData(String d){
        if ("Most used words".equals(d)) {
            readData(R.raw.most_used);
        } else if ("Colors".equals(d)) {
            readData(R.raw.colors);
        } else if ("Numbers".equals(d)) {
            readData(R.raw.numbers);
        } else if ("Household items".equals(d)) {
            readData(R.raw.household_items);
        }
    }

    public void nextItem(View view){
        Button prevBtn = findViewById(R.id.PrevBtn);
        Button nextBtn = findViewById(R.id.nextBtn);
        if (index < wordList.size()-1){
            prevBtn.setEnabled(true);
            TextView tv = findViewById(R.id.txt);
            index++;
            tv.setText(wordList.get(index));
        }
        else{
            nextBtn.setEnabled(false);
        }

        EditText ansBox = findViewById(R.id.ansBox);
        ansBox.setText("");
        ansBox.setBackground(ContextCompat.getDrawable(this,R.drawable.round));
    }

    public void prevItem(View view){
        Button prevBtn = findViewById(R.id.PrevBtn);
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setEnabled(true);
        if(index==0){
            prevBtn.setEnabled(false);
        }
        else{
            prevBtn.setEnabled(true);
            TextView tv = findViewById(R.id.txt);
            index--;
            tv.setText(wordList.get(index));
        }

        EditText ansBox = findViewById(R.id.ansBox);
        ansBox.setText("");
        ansBox.setBackground(ContextCompat.getDrawable(this,R.drawable.round));
    }

    public void checkAns(View view){
        EditText ansBox = findViewById(R.id.ansBox);
        String answer = ansBox.getText().toString();

        //Infer empty
        if (answer.equals("")){
            ansBox.setError("Empty!");
        }
        else{
            Log.i("User input", answer);
            Log.i("Get", jyutList.get(index));
            if(answer.contains(jyutList.get(index))){
                ansBox.setBackground(ContextCompat.getDrawable(this,R.drawable.round_green));
            }
            else{
                ansBox.setBackground(ContextCompat.getDrawable(this,R.drawable.round_red));
            }
        }
    }

    public void readData(int choice){
        InputStream inputStream = getResources().openRawResource(choice);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;

        try {
            while (( line = buffreader.readLine()) != null) {
                //Standard line 个.ge3&的
                String wd = line.substring(0,line.indexOf("."));
                String jp = line.substring(line.indexOf(".")+1);

                wordList.add(wd);
                jyutList.add(jp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}