package com.xpg.cantonesedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TranslateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        EditText resultTV = findViewById(R.id.resultTV);

        //Set exchange listeners
        EditText translateTV = findViewById(R.id.translateTV);
        translateTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tv = translateTV.getText().toString();

                tv = tv.replace('的', '嘅');
                tv = tv.replace('他', '佢');
                tv = tv.replace('她', '佢');
                tv = tv.replace('找', '揾');
                tv = tv.replace('是', '係');
                tv = tv.replace('吃', '食');
                tv = tv.replace('给', '畀');
                tv = tv.replace('骗', '呃');
                tv = tv.replace('对', '啱');

                //Re set the text
                resultTV.setText(tv);
            }
        });
    }



}