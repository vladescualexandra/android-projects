package com.example.dam_seminar_012.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.dam_seminar_012.R;

import java.util.Map;
import java.util.Random;

public class ChartView extends View {

    private Context context;
    private Map<String, Integer> source;
    private Paint paint;
    private Random random;

    private final int color1 = Color.rgb(186, 215, 242);
    private final int color2 = Color.rgb(186, 242, 187);
    private final int color3 = Color.rgb(186, 242, 216);
    private final int color4 = Color.rgb(242, 226, 186);
    private final int color5 = Color.rgb(242, 186, 201);

    private final int[] colors = {color1, color2, color3, color4, color5};

    public ChartView(Context context,
                     Map<String, Integer> source) {
        super(context);
        this.context = context;
        this.source = source;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.random = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (source == null || source.isEmpty()) {
            return;
        } else {
            // calculam latimea unei bare
            float widthBar = (float) getWidth() / source.size();

            // identificam bara cu cea mai mare valoare
            int maxValue = 0;
            for (Integer value : source.values()) {
                if (maxValue < value) {
                    maxValue = value;
                }
            }

            drawValues(canvas, widthBar, maxValue);
        }
    }

    private void drawValues(Canvas canvas, float widthBar, int maxValue) {
        int currentBarPosition = 0;
        for (String label : source.keySet()) {
            // valoare bara
            int value = source.get(label);

            generateColor(currentBarPosition);
            drawBar(canvas, widthBar, maxValue, currentBarPosition, value);
            drawLegend(canvas, widthBar, currentBarPosition, label, value);

            currentBarPosition++;
        }
    }

    private void generateColor(int i) {
        paint.setColor(colors[i]);
    }

    private void drawBar(Canvas canvas, float widthBar, int maxValue, int currentBarPosition, int value) {
        float x1 = currentBarPosition * widthBar;
        float y1 = ((1 - (float) value / maxValue) * getHeight());

        float x2 = x1 + widthBar;
        float y2 = getHeight();

        canvas.drawRect(x1, y1, x2, y2, paint);
    }

    private void drawLegend(Canvas canvas, float widthBar, int currentBarPosition, String label, int value) {
        // trasare legenda
        paint.setColor(Color.BLACK);
        paint.setTextSize((float) (0.1 * getWidth()));


        float x = (float) (currentBarPosition + 0.5) * widthBar;
        float y = (float) 0.95 * getHeight();
        canvas.rotate(270, x, y);
        canvas.drawText(this.context.getString(R.string.chart_label_format, label, value), x, y, paint);
        canvas.rotate(-270, x, y);
    }
}
