package com.example.dam_exam_subject_002;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dam_exam_subject_002.util.MType;
import com.example.dam_exam_subject_002.util.Marriage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphView extends View {
    Context context;
    Paint paint = new Paint();
    private List<Integer> guests;
    private Map<MType, Integer> types;

    private final int color1 = Color.rgb(186, 215, 242);
    private final int color2 = Color.rgb(186, 242, 187);

    public GraphView(Context context, List<Marriage> marriagesList) {
        super(context);
        this.context = context;
        this.guests = new ArrayList<>();
        this.types = new HashMap<>();
        if (marriagesList != null) {
            for (Marriage marriage : marriagesList) {
                guests.add(marriage.getGuests());
                if (types.containsKey(marriage.getType())) {
                    Integer currentValue = types.get(marriage.getType());
                    Integer newValue = (currentValue != null ? currentValue : 0) + 1;
                    types.put(marriage.getType(), newValue);
                } else {
                    types.put(marriage.getType(), 1);
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawLegend(canvas,
                color1, "guests",
                color2, "type");

        /******* FIECARE NR. DE GUESTS IN PARTE IN GRAFIC CU LINII *******/
        if (guests.size() > 0) {
            int maxPoint = 0;
            for (int i = 0; i < guests.size(); i++) {
                if (guests.get(i) > maxPoint) {
                    maxPoint = guests.get(i);
                }
            }

            int no_of_elements = guests.size();
            int dist_between_points = getWidth() / no_of_elements;

            for (int position = 0; position < guests.size(); position++) {
                drawPoint(canvas,
                        guests.get(position),
                        String.valueOf(position),
                        maxPoint, dist_between_points,
                        position, color1);
            }

            for (int i = 1; i < guests.size(); i++) {
                drawLine(canvas, i - 1, dist_between_points,
                        maxPoint, guests.get(i - 1), guests.get(i),
                        color1);
            }
        }

        /*************** GRUPRE DE TIPURI IN GRAFIC CU LINII ******************/
        if (types.size() > 0) {
            int maxPoint = 0;
            for (Integer i : types.values()) {
                if (i > maxPoint) {
                    maxPoint = i;
                }
            }

            int no_of_elements = types.size();
            int dist_between_points = getWidth() / no_of_elements;

            for (MType label : types.keySet()) {
                int position = 0;
                drawPoint(canvas,
                        types.get(label),
                        label.name(),
                        maxPoint,
                        dist_between_points,
                        position,
                        color2);
                position++;
            }
        }
    }

    private void drawLegend(Canvas canvas,
                            int color1, String label1,
                            int color2, String label2) {

        paint.setTextSize(50.0f);

        float x = 0.8f * getWidth();
        float y = 0.8f * getHeight();
        float delta = 1.1f;


        paint.setColor(color1);
        canvas.drawRect(x, y, delta * x, delta * y, paint);
        paint.setColor(color2);
        canvas.drawRect(x, delta * y, delta * x, 2 * delta * y, paint);

        paint.setColor(Color.BLACK);
        canvas.drawText(label1,delta * x, 1.1f * y, paint);
        canvas.drawText(label2,delta * x, 1.2f * y, paint);

    }

    private void drawPoint(Canvas canvas,
                           float value,
                           String label,
                           int max,
                           int dist_between_points,
                           int position,
                           int color) {
        float x = position * dist_between_points;
        float y = (1 - value / max) * getHeight();

        // Drawing the point
        paint.setColor(color);
        paint.setStrokeWidth(50.0f);
        canvas.drawPoint(x, y, paint);

        // Drawing the label
        paint.setColor(Color.BLACK);
        paint.setTextSize(50.0f);
        canvas.drawText(label, x, 1.2f * y, paint);

    }


    private void drawLine(Canvas canvas,
                          int position,
                          float distanceBetweenPoints,
                          float max,
                          float start,
                          float end,
                          int color) {

        float x1 = position * distanceBetweenPoints;
        float y1 = (1 - start / max) * getHeight();
        float x2 = (position + 1) * distanceBetweenPoints;
        float y2 = (1 - end / max) * getHeight();

        paint.setColor(color);
        paint.setStrokeWidth(5f);
        canvas.drawLine(x1, y1, x2, y2, paint);
    }
}

