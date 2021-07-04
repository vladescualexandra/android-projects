package dam.application.graph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphFragment extends Fragment {

    public GraphFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        FrameLayout fl = view.findViewById(R.id.main_fragment);

        List<Wedding> weddingList = new ArrayList<>();
        String date = "00/00/0000";
        if (getArguments() != null) {
           weddingList = getArguments().getParcelableArrayList(MainActivity.LIST_KEY);
           date = getArguments().getString(MainActivity.DATE_KEY);
        }

        Map<String, Integer> source = buildMap(weddingList, date);

        view.setPadding(100, 100, 100,100);
        fl.addView(new GraphView(getActivity(), source));


        return view;
    }

    private Map<String, Integer> buildMap(List<Wedding> weddingList, String date) {
        Map<String, Integer> map = new HashMap<>();

        if (date != null || !date.isEmpty()) {
            String[] pieces = date.split("/");

            int day = Integer.parseInt(pieces[0]);
            int month = Integer.parseInt(pieces[1]);
            int year = Integer.parseInt(pieces[2]);

            Log.e("test", pieces[0] + "-" + pieces[1] + "-" + pieces[2]);

            String g1 = day + "/" + month + "/" + year;
            String g2 = (day + 1) + "/" + month + "/" + year;
            String g3 = (day + 2) + "/" + month + "/" + year;
            String g4 = (day + 3) + "/" + month + "/" + year;
            String g5 = (day + 4) + "/" + month + "/" + year;

            map.put(g1, 0);
            map.put(g2, 0);
            map.put(g3, 0);
            map.put(g4, 0);
            map.put(g5, 0);

            if (!weddingList.isEmpty()) {
                for (Wedding wedding : weddingList) {
                    int old_val = map.get(wedding.getDate());
                    int new_val = old_val + wedding.getGuests();
                    map.put(wedding.getDate(), new_val);
                }
            }
        }
        return map;
    }
}