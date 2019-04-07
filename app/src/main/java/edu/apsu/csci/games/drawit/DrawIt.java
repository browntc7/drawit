package edu.apsu.csci.games.drawit;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DrawIt extends View implements Serializable {

    public static final int PENCIL_EVENT = 0;
    public static final int SQUARE_EVENT = 1;
    public static final int CIRCLE_EVENT = 2;
    public static final int DEFAULT_STROKE = 8;

    private int currentWidth;
    private int currentHeight;
    private int currentEvent = PENCIL_EVENT;
    private int currentColor = Color.RED;
    private int currentStrokeWidth = DEFAULT_STROKE;
    private Paint paint;
    private Path path;
    private Rectangle rectangle;
    //private Circle circle;
    private List<Path> paths = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();
    private List<Integer> strokes = new ArrayList<>();
    private List<Rectangle> rectangles = new ArrayList<>();

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
        paint.setColor(currentColor);
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

        Log.i("Rect size", Integer.toString(rectangles.size()));
        for(int i = 0; i < rectangles.size(); i ++){
            Rectangle rect = rectangles.get(i);
            if (rect != null) {
                paint.setColor(rect.getColor());
                paint.setStrokeWidth(rect.getStroke());
                if (rect.getType() == Rectangle.RECTANGLE) {
                    canvas.drawRect(rect.getLeft(), rect.getTop(), rect.getRight(), rect.getBottom(), paint);
                    Log.i("Type", Integer.toString(rect.getType()));
                } else if (rect.getType() == Rectangle.CIRCLE) {
                    canvas.drawOval(rect.getLeft(), rect.getTop(), rect.getRight(), rect.getBottom(), paint);
                }
            }
        }

    }

    /**
     * Gets the x and y coordinates from the users touch events and updates the path object accordingly.
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

            int action = event.getAction();

            if(action == MotionEvent.ACTION_DOWN) {
                if(currentEvent == PENCIL_EVENT) {
                    path = new Path();
                    path.moveTo(event.getX(), event.getY());
                } else if (currentEvent == SQUARE_EVENT) {
                    rectangle = new Rectangle(event.getY(), event.getX(), currentColor, Rectangle.RECTANGLE, currentStrokeWidth);
                } else if (currentEvent == CIRCLE_EVENT){
                    rectangle = new Rectangle(event.getY(), event.getX(), currentColor, Rectangle.CIRCLE, currentStrokeWidth);
                }
            } else if (action == MotionEvent.ACTION_MOVE){
                if(currentEvent == PENCIL_EVENT) {
                    path.lineTo(event.getX(), event.getY());
                    invalidate();
                } else  if (currentEvent == SQUARE_EVENT || currentEvent == CIRCLE_EVENT){
                    Log.i("OnTouchEvent", "MOVE + Square/Circle");
                    rectangle.setBottom(event.getY());
                    rectangle.setRight(event.getX());
                    invalidate();
                }

            } else if (action == MotionEvent.ACTION_UP){
                if(currentEvent == PENCIL_EVENT) {
                    paths.add(path);
                } else if (currentEvent == SQUARE_EVENT || currentEvent == CIRCLE_EVENT){
                    rectangles.add(rectangle);
                }
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

    public void setImage (Drawable drawable){
        setBackground(drawable);
    }

    public void setPaintColor(int color){
        invalidate();
        currentColor = color;
        //paint.setColor(color);
        //path = new Path();
        //invalidate();
//        if(currentEvent == SQUARE_EVENT){
//            rectangle.setColor(color);
//        }
    }

    public void setStroke(int stroke){
        invalidate();
        currentStrokeWidth = stroke;
        //path = new Path();
        //invalidate();
    }

    public void setEvent(int event){
        currentEvent = event;
    }


    public Serializable getArrayColors (){
        return (Serializable) colors;
    }

    public Serializable getArrayStrokes (){
        return (Serializable) strokes;
    }

    public Serializable getArrayPaths (){
        return (Serializable) paths;
    }

    public Serializable getArrayShapes (){
        return (Serializable) rectangles;
    }

    public void setArrayColors (Serializable colors) {
        this.colors = (ArrayList) colors;
    }

    public void setArrayStrokes (Serializable strokes) {
        this.strokes = (ArrayList) strokes;
    }

    public void setArrayPaths(Serializable paths) {
        this.paths = (ArrayList) paths;
    }

    public void setArrayShapes (Serializable shapes) {
        Log.i("Rotation", "Setting up Shapes");
        this.rectangles = (ArrayList) shapes;
    }


}

class Rectangle {

    public static final int RECTANGLE = 0;
    public static final int CIRCLE = 1;

    private float top;
    private float right;
    private float left;
    private float bottom;
    private int color;
    private int stroke;
    private int type;

    Rectangle(float top, float left, int color, int type, int stroke){
        this.top = top;
        this.left = left;
        this.color = color;
        this.type = type;
        this.stroke = stroke;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getType(){
        return type;
    }

    public int getStroke(){
        return stroke;
    }
}

