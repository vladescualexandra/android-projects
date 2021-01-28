package com.example.dam_exam_subject_002;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dam_exam_subject_002.util.MType;
import com.example.dam_exam_subject_002.util.Marriage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Color;
import android.widget.FrameLayout;
import android.widget.Toast;

public class GraphFragment extends Fragment {
    public GraphFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        FrameLayout fl = view.findViewById(R.id.fragment_graph);

        List<Marriage> marriageList = new ArrayList<>();
        if (getArguments() != null) {
            marriageList = getArguments().getParcelableArrayList(MainActivity.MARRIAGE_LIST_KEY);
        }

        view.setPadding(100, 100, 100, 100);
        fl.addView(new GraphView(getActivity(), marriageList));
        return view;
    }





}