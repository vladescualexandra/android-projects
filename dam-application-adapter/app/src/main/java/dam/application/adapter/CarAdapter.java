package dam.application.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import examen.g1088.stefan.codruta.R;

public class CarAdapter extends ArrayAdapter<Car> {

    private Context context;
    private int resource;
    private List<Car> list;
    private LayoutInflater layoutInflater;

    public CarAdapter(@NonNull Context context,
                      int resource,
                      @NonNull List<Car> list,
                      LayoutInflater layoutInflater) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
        this.layoutInflater = layoutInflater;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);

        if (list != null && list.size() > 0) {
            Car car = list.get(position);

            if (car != null) {
                build(view, car);
            }
            view.setOnClickListener(deleteEventListener(car));
        }

        return view;
    }


    private void build(View view, Car car) {
        TextView tv_model = view.findViewById(R.id.row_model);
        tv_model.setText(car.getModel());

        TextView tv_year = view.findViewById(R.id.row_year);
        tv_year.setText(String.valueOf(car.getYear()));

        TextView tv_rating = view.findViewById(R.id.row_rating);
        tv_rating.setText(String.valueOf(car.getRating()));

        RatingBar rb_text_size = view.findViewById(R.id.row_rating_bar);
        rb_text_size.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float size = 15 * rating;
                tv_model.setTextSize(size);
            }
        });
    }

    private View.OnClickListener deleteEventListener(Car car) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you wanna delete " + car.getModel() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(car);
                                notifyAdapter();
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
                dialog.show();
            }
        };
    }

    private void notifyAdapter() {
        this.notifyDataSetChanged();
    }
}
