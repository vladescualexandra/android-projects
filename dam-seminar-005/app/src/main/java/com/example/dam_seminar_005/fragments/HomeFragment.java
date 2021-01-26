package com.example.dam_seminar_005.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dam_seminar_005.R;
import com.example.dam_seminar_005.util.Expense;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public static String EXPENSES_KEY = "expenses_key";
    private ListView lv_expenses;
    private List<Expense> expenses = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initComponents(view);
        return view;
    }

    public HomeFragment() {

    }

    private void initComponents(View view) {
        lv_expenses = view.findViewById(R.id.lv_home_expenses);
        if (getArguments() != null) {
            expenses = getArguments().getParcelableArrayList(EXPENSES_KEY);
        }
        if (getContext() != null) {
            ArrayAdapter<Expense> adapter = new ArrayAdapter<Expense>(
                    getContext().getApplicationContext(),
                    android.R.layout.simple_list_item_1, expenses);
            lv_expenses.setAdapter(adapter);
        }
    }

    public void notifyAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) lv_expenses.getAdapter();
        adapter.notifyDataSetChanged();
    }


    public static HomeFragment newInstance(ArrayList<Expense> expenses) {
        HomeFragment fragment = new HomeFragment();
        /*
        * Bundle este o clasă asemănătoare cu intentul, doar că nu poate deschide
        * activități. Este utilizată pentru transmiterea de informații între
        * activități/fragmente.
        */
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(HomeFragment.EXPENSES_KEY, expenses);
        fragment.setArguments(bundle);
        return fragment;
    }


}
