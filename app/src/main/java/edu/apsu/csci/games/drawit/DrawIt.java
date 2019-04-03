package edu.apsu.csci.games.drawit;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DrawIt extends View {
    private int currentWidth;
    private int currentHeight;
    private int currentColor = Color.RED;
    private int currentStrokeWidth = 10;
    private Paint paint;
    private Path path;
    private List<Path> paths = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();
    private List<Integer> strokes = new ArrayList<>();

    public DrawIt(Context context) {
        super(context);
        setup(null);
    }

    public DrawIt(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public DrawIt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    private void setup(AttributeSet attrs) {
        paint = new Paint();
        paint.setColor(currentColor); //default paint color
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(currentStrokeWidth);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < paths.size(); i ++) {
            //canvas.drawPath(path, paint);
            paint.setColor(colors.get(i));
            paint.setStrokeWidth(strokes.get(i));
            canvas.drawPath(paths.get(i), paint);
        }

    }

    /**
     * Gets the x and y coordinates from the users touch events and updates the path object accordingly.
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

            int action = event.getAction();

            if(action == MotionEvent.ACTION_DOWN) {
                path.moveTo(event.getX(), event.getY());
            } else if (action == MotionEvent.ACTION_MOVE){
                path.lineTo(event.getX(), event.getY());
                invalidate();
            } else if (action == MotionEvent.ACTION_UP){
                paths.add(path);
                colors.add(currentColor);
                strokes.add(currentStrokeWidth);
                invalidate();
            }
            return true;

    }

    /**
        * From ShapeView.java created by Dr. John Nicholson, APSU on 3/9/16
        * Allows screen to rotate without affecting canvas.
        */

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        currentWidth = w;
        currentHeight = h;
    }

    /**
     * Source and explanation
     * http://stackoverflow.com/questions/12266899/onmeasure-custom-view-explanation
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = 100;
        int desiredHeight = 100;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    /**
     * using a ContentResolver and an URI of the selected image create a bitmapdrawable and set it as the background
    * */
    public void setImage(ContentResolver cr, Uri uri)  {
        Bitmap selectedImage = null;
        try {
            selectedImage = MediaStore.Images.Media.getBitmap(cr, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), selectedImage);
        setBackground(bitmapDrawable);
        invalidate();
    }

    public void setPaintColor(int color){
        currentColor = color;
        //paint.setColor(color);
        path = new Path();
    }

    public void setStroke(int stroke){
        currentStrokeWidth = stroke;
        path = new Path();
    }

}
