package com.xpg.cantonesedemo.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.xpg.cantonesedemo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my, container, false);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        //TODO: Proficiency, Words Learned
        //TODO: Words learned logic, not right. 

        //Read total time
        String curTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String stTime = sharedPref.getString("stTime", "");

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        try {
            Date st = sdf.parse(stTime);
            Date cur = sdf.parse(curTime);

            long dTime = cur.getTime() - st.getTime();

            long diffhour = (dTime / (1000 * 60 * 60)) % 24;
            long diffMin = (dTime / (1000 * 60)) % 60;
            long diffSec = (dTime / 1000) % 60;

            String res = "Time " + diffhour + " Hours " + diffMin + " Minutes " + diffSec + " Seconds";

            TextView textView = view.findViewById(R.id.textView7);
            textView.setText(res);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Read username from shared prefs
        String username = sharedPref.getString("username", "username");

        TextView textView = view.findViewById(R.id.textView4);
        textView.setText(username);


        //Set change for username
        CardView usernameCV = view.findViewById(R.id.usernameCard);
        usernameCV.setOnClickListener(v -> {
            LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
            View customView = layoutInflater.inflate(R.layout.username_dialog, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setView(customView);

            final EditText usernameET = customView.findViewById(R.id.usernameET);

            builder.setTitle("Set username")
                    .setCancelable(false)
                    .setPositiveButton("Set", (dialog, which) -> {
                        String newUsername = usernameET.getText().toString();
                        if (newUsername.equals("")) {
                            usernameET.setError("Empty");
                        } else {
                            //Put new username in shared pref
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("username", newUsername);
                            editor.apply();

                            textView.setText(newUsername);
                        }
                    })
                    .setNegativeButton("Dismiss", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        });
        return view;
    }
}