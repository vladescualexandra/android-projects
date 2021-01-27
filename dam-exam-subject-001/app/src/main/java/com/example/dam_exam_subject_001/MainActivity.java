package com.example.dam_exam_subject_001;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.dam_exam_subject_001.async.AsyncTaskRunner;
import com.example.dam_exam_subject_001.async.Callback;
import com.example.dam_exam_subject_001.network.HttpManager;
import com.example.dam_exam_subject_001.util.Bid;
import com.example.dam_exam_subject_001.util.BidJsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    private static final String BIDS_URL_ADDRESS = "https://jsonkeeper.com/b/Q9YB";
    public static final int ADD_BID_REQUEST_CODE = 201;
    public static final int UPDATE_BID_REQUEST_CODE = 202;
    public static final String BIDS = "BIDS";
    Button btn_add_bid;
    ListView lv_bids;
    List<Bid> bidList;

    ToggleButton tb_sort;
    Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bidList = new ArrayList<>();
        btn_add_bid = findViewById(R.id.main_add_bid);
        btn_add_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BidActivity.class);
                startActivityForResult(intent, ADD_BID_REQUEST_CODE);
            }
        });

        tb_sort = findViewById(R.id.main_sort);
        tb_sort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bidList.sort(Bid::compareTo);
                    notifyAdapter();
                }
            }
        });
        setAdapter();
        getBidsFromUrl();

        btn_save = findViewById(R.id.main_save_prefs);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(BIDS, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                for (int i=0; i<bidList.size(); i++) {
                    String tag = "bid_" + i;
                    editor.putString(tag, bidList.get(i).toString());
                }
                editor.apply();
                Toast.makeText(getApplicationContext(),
                        "Saved to prefs",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setAdapter() {
        lv_bids = findViewById(R.id.main_list);
        ArrayAdapter<Bid> adapter = new ArrayAdapter<Bid>
                (getApplicationContext(), android.R.layout.simple_list_item_1, bidList);
        lv_bids.setAdapter(adapter);
        lv_bids.setOnItemClickListener(updateBidEvent());
    }

    private AdapterView.OnItemClickListener updateBidEvent() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bid bid = bidList.get(position);
                Intent intent = new Intent(getApplicationContext(), BidActivity.class);
                intent.putExtra(BidActivity.BID_KEY, bid);
                startActivityForResult(intent, UPDATE_BID_REQUEST_CODE);
            }
        };
    }

    private void notifyAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) lv_bids.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ADD_BID_REQUEST_CODE) {
                Bid bid = (Bid) data.getSerializableExtra(BidActivity.BID_KEY);
                bidList.add(bid);
                notifyAdapter();
            } else if (requestCode == UPDATE_BID_REQUEST_CODE) {
                Bid bid = (Bid) data.getSerializableExtra(BidActivity.BID_KEY);
                for (Bid b : bidList) {
                    if (b.getName().equals(bid.getName())) {
                        b.setName(bid.getName());
                        b.setBid(bid.getBid());
                        b.setDate(bid.getDate());
                        b.setTime(bid.getTime());
                        b.setObject(bid.getObject());
                        b.setRegistered(bid.isRegistered());
                        b.setState(bid.getState());
                        break;
                    }
                }
            }
        }
    }

    /******************* GET BIDS FROM JSON URL *******************/
    private void getBidsFromUrl() {
        Callable<String> asyncOperation = new HttpManager(BIDS_URL_ADDRESS);
        Callback<String> mainThreadOperation = mainThreadOpFromUrl();
        AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
        asyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);
    }

    private Callback<String> mainThreadOpFromUrl() {
        return new Callback<String>() {
            @Override
            public void runResultOnUIThread(String result) {
                bidList.addAll(BidJsonParser.fromJson(result));
                notifyAdapter();
            }
        };
    }
}