package com.xpg.cantonesedemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xpg.cantonesedemo.ListActivity;
import com.xpg.cantonesedemo.PracticeActivity;
import com.xpg.cantonesedemo.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VocabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VocabFragment extends Fragment {

    ArrayList<String> data = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VocabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VocabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VocabFragment newInstance(String param1, String param2) {
        VocabFragment fragment = new VocabFragment();
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
        View view = inflater.inflate(R.layout.fragment_vocab, container, false);

        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListView listView = view.findViewById(R.id.vocabList);
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), R.layout.vocab_list_item, R.id.vocabTV, data);
        listView.setAdapter(adapter);

        return view;
    }

    public void readData() throws IOException {
        File f = new File(getActivity().getFilesDir(), "data.txt");

        if (!f.exists()){
            f.createNewFile();
        }

        FileInputStream fis = getContext().openFileInput(f.getName());
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                data.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}