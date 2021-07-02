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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PracticeActivity extends AppCompatActivity {

    private int index = 0;
    ArrayList<String> wordList = new ArrayList<>();
    ArrayList<String> jyutList = new ArrayList<>();

    ArrayList<String> vocabList = new ArrayList<>();

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
        } else if ("Time".equals(d)) {
            readData(R.raw.time);
        } else if ("Animal and plants".equals(d)) {
            readData(R.raw.animal);
        } else if ("Transportation".equals(d)) {
            readData(R.raw.transportation);
        }
        else if ("Work".equals(d)){
            readData(R.raw.work);
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

                //Add data into vocab set for further reference
                vocabList.add(wordList.get(index));
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

    public void writeData() throws IOException {
        File f = new File(getFilesDir(), "data.txt");

        if (!f.exists()){
            f.createNewFile();
        }

        FileOutputStream fis = openFileOutput(f.getName(), MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fis, StandardCharsets.UTF_8);

        for (String s: vocabList
             ) {
            osw.write(s + "\n");
        }
        osw.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Activity ", "Activity destroyed");
        try {
            writeData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}