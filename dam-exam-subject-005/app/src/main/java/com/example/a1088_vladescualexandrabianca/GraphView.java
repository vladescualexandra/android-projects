package com.example.a1088_vladescualexandrabianca;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GraphView extends View {
    Context context;
    Paint paint = new Paint();
    private Map<String, Integer> source;

    public GraphView(Context context, List<Book> books) {
        super(context);
        this.context = context;
        this.source = new HashMap<>();
        for (Book book : books) {
            source.put(book.getName(), book.getGrade());
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        for (Map.Entry<String, Integer> entry : source.entrySet()) {
            generateColor();


            canvas.drawCircle(100, 100, 100, paint);
            canvas.drawText(entry.getKey() + " - " + entry.getValue(), 150, 150, paint);

        }
    }

    private void generateColor() {
        Random random = new Random();
        int color = Color.argb(100,
                1 + random.nextInt(254),
                1 + random.nextInt(254),
                1 + random.nextInt(254)
                );

        paint.setColor(color);
    }
}
