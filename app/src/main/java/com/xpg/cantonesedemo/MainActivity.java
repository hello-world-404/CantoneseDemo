package com.xpg.cantonesedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.xpg.cantonesedemo.fragment.MyFragment;
import com.xpg.cantonesedemo.fragment.PracticeFragment;
import com.xpg.cantonesedemo.fragment.VocabFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loading the default fragment
        loadFragment(new PracticeFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(this);

        //Get current timestamp
        String stTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Log.i("Current time", stTime);
        editor.putString("stTime", stTime);
        editor.apply();
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.vocab:
                fragment = new VocabFragment();
                break;

            case R.id.practice:
                fragment = new PracticeFragment();
                break;

            case R.id.my:
                fragment = new MyFragment();
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            if (data!= null && data.getData() != null){
                final Uri imageUri = data.getData();
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    //Copy file to storage
                    File imgf = new File(getFilesDir(), "avatar.png");

                    if(!imgf.exists()){
                        imgf.createNewFile();
                    }

                    FileOutputStream fos = new FileOutputStream(imgf);
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 90, fos);
                    fos.close();

                    //Set file to image
                    ImageView imageView = findViewById(R.id.imageView);
                    imageView.setImageBitmap(selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}