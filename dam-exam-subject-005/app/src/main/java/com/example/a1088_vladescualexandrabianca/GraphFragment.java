package com.example.a1088_vladescualexandrabianca;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;


public class GraphFragment extends Fragment {


    public GraphFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        FrameLayout fl = view.findViewById(R.id.fragment_graph);

        List<Book> bookList = new ArrayList<>();
        if (getArguments() != null) {
            bookList = getArguments().getParcelableArrayList(MainActivity.BOOKS_LIST);
        }

        view.setPadding(100, 100, 100, 100);
        fl.addView(new GraphView(getActivity(), bookList));
        return view;
    }
}