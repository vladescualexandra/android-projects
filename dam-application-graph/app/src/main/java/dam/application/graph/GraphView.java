package dam.application.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;

import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GraphView extends View {

    Context context;
    Paint paint = new Paint();

    private Map<String, Integer> map = new HashMap<>();

    // Gruparea informațiilor: date from date picker + guests
    // G1 = ziua din DatePicker
    // G2 = G1 + 1 zi
    // G3 = G2 + 1 zi
    // G4 = G3 + 1 zi
    // G5 = G4 + 1 zi


    public GraphView(Context context, Map<String, Integer> source) {
        super(context);
        this.context = context;
        this.map = source;
    }

    private void getRandomColor() {
        Random rand = new Random();
        int color = Color.rgb(
                rand.nextInt(254) + 1,
                rand.nextInt(254) + 1,
                rand.nextInt(254) + 1
        );

        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (map == null || map.isEmpty()) {
            return;
        } else {

            float widthBar = (float) getWidth() / map.size();

            int maxValue = 0;
            for (Integer value : map.values()) {
                if (maxValue < value) {
                    maxValue = value;
                }
            }

            drawValues(canvas, widthBar, maxValue);
        }
    }

    private void drawValues(Canvas canvas, float widthBar, int maxValue) {
        int currentBarPosition = 0;
        for (String label : map.keySet()) {

            int value = map.get(label);

            drawBar(canvas, widthBar, maxValue, currentBarPosition, value);
            drawLegend(canvas, widthBar, currentBarPosition, label, value);

            currentBarPosition++;
        }
    }


    private void drawBar(Canvas canvas, float widthBar, int maxValue, int currentBarPosition,
                         int value) {
        float x1 = currentBarPosition * widthBar;
        float y1 = ((1 - (float) value / maxValue) * getHeight());

        float x2 = x1 + widthBar;
        float y2 = getHeight();

        canvas.drawRect(x1, y1, x2, y2, paint);
    }

    private void drawLegend(Canvas canvas, float widthBar, int currentBarPosition, String label,
                            int value) {
        // trasare legenda
        paint.setColor(Color.BLACK);
        paint.setTextSize((float) (0.1 * getWidth()));


        float x = (float) (currentBarPosition + 0.5) * widthBar;
        float y = (float) 0.95 * getHeight();
        canvas.rotate(270, x, y);
        canvas.drawText(label, x, y, paint);
        canvas.rotate(-270, x, y);
    }
}
