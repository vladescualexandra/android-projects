package eu.ase.ro.seminar11.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import eu.ase.ro.seminar11.R;

public class CoachAdapter extends ArrayAdapter<Coach> {
    private Context context;
    private int resource;
    private List<Coach> coaches;
    private LayoutInflater inflater;

    public CoachAdapter(@NonNull Context context, int resource, @NonNull List<Coach> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.coaches = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Coach coach = coaches.get(position);
        if (coach != null) {
            addName(view, coach.getName());
            addTeam(view, coach.getTeam());
            addRole(view, coach.getRole());
        }
        return view;
    }

    private void addName(View view, String name) {
        TextView textView = view.findViewById(R.id.tv_row_name);
        populateTextViewContent(textView, name);
    }

    private void addTeam(View view, String team) {
        TextView textView = view.findViewById(R.id.tv_row_team);
        populateTextViewContent(textView, team);
    }

    private void addRole(View view, String role) {
        TextView textView = view.findViewById(R.id.tv_row_role);
        populateTextViewContent(textView, role);
    }

    private void populateTextViewContent(TextView textView, String value) {
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.lv_row_default_value);
        }
    }
}
